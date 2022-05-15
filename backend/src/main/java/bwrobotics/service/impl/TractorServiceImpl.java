package bwrobotics.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bwrobotics.dto.TractorExecutedTasksPercentDTO;
import bwrobotics.entity.Tractor;
import bwrobotics.entity.TractorTasksData;
import bwrobotics.repository.TractorRepository;
import bwrobotics.repository.TractorTasksDataRepository;
import bwrobotics.service.TractorService;

@Service
public class TractorServiceImpl implements TractorService {

	@Autowired
	TractorRepository tractorRepository;

	@Autowired
	TractorTasksDataRepository tractorTasksDataRepository;

	@Override
	public List<Tractor> getAllTractors() {
		List<Tractor> tractors = new ArrayList<>();
		tractorRepository.findAll().forEach(tractors::add);
		return tractors;
	}

	@Override
	public List<TractorExecutedTasksPercentDTO> getTractorExecutedTaksksPercent() {
		List<TractorTasksData> tractorsTasks = new ArrayList<>();
		tractorTasksDataRepository.findAll().forEach(tractorsTasks::add);
		List<Tractor> tractors = new ArrayList<>();
		tractorRepository.findAll().forEach(tractors::add);
		Map<Integer, List<TractorTasksData>> tasksByTractorId = groupTractorsTasksDataByTractorId(tractorsTasks);
		return calculateTractorExecutedTasksPercent(tractors, tasksByTractorId);
	}

	@Override
	public List<TractorExecutedTasksPercentDTO> calculateTractorExecutedTasksPercent(List<Tractor> tractors,
			Map<Integer, List<TractorTasksData>> tasksByTractorId) {
		List<TractorExecutedTasksPercentDTO> executedTasksPercent = new ArrayList<>();
		tasksByTractorId.forEach((tractorId, tasks) -> {
			TractorExecutedTasksPercentDTO executedPercent = new TractorExecutedTasksPercentDTO();
			Tractor tractor = tractors.stream().filter(t -> tractorId.equals(t.getId())).findAny().orElse(null);
			executedPercent.setTractorName(tractor.getTractorName());
			float executed = tasks.stream().mapToInt(TractorTasksData::getNumTasksExecuted).sum();
			float planned = tasks.stream().mapToInt(TractorTasksData::getNumTasksPlanned).sum();
			executedPercent.setTractorPercent(Math.round(executed / planned * 100));
			executedTasksPercent.add(executedPercent);
		});
		return executedTasksPercent;
	}

	@Override
	public Map<Integer, List<TractorTasksData>> groupTractorsTasksDataByTractorId(
			List<TractorTasksData> allTractorsTasks) {
		return allTractorsTasks.stream().collect(Collectors.groupingBy(TractorTasksData::getTractorId));
	}

}
