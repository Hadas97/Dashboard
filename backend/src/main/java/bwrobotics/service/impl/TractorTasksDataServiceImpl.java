package bwrobotics.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bwrobotics.entity.Tractor;
import bwrobotics.entity.TractorTasksData;
import bwrobotics.repository.TractorRepository;
import bwrobotics.repository.TractorTasksDataRepository;
import bwrobotics.service.TractorTasksDataService;

@Service
public class TractorTasksDataServiceImpl implements TractorTasksDataService {

	@Autowired
	TractorRepository tractorRepository;

	@Autowired
	TractorTasksDataRepository tractorTasksSumRepository;

	@Override
	public List<TractorTasksData> getAllTractorTasksDataSum() {
		List<TractorTasksData> allTractorsTasks = getAllTractorsTasksData();
		Map<LocalDate, List<TractorTasksData>> tasksPerDate = groupTractorsTasksDataByDate(allTractorsTasks);
		return sumUpTractorsTasksData(tasksPerDate);
	}

	@Override
	public List<TractorTasksData> sumUpTractorsTasksData(Map<LocalDate, List<TractorTasksData>> tasksPerDate) {
		List<TractorTasksData> tractorsTasksSummary = new ArrayList<>();
		tasksPerDate.forEach((date, tasks) -> {
			TractorTasksData summaryTasks = new TractorTasksData();
			summaryTasks.setTasksDate(date);
			summaryTasks.setNumTasksPlanned(tasks.stream().mapToInt(TractorTasksData::getNumTasksPlanned).sum());
			summaryTasks.setNumTasksExecuted(tasks.stream().mapToInt(TractorTasksData::getNumTasksExecuted).sum());
			tractorsTasksSummary.add(summaryTasks);
		});
		return tractorsTasksSummary;
	}

	@Override
	public Map<LocalDate, List<TractorTasksData>> groupTractorsTasksDataByDate(
			List<TractorTasksData> allTractorsTasks) {
		return allTractorsTasks.stream().collect(Collectors.groupingBy(TractorTasksData::getTasksDate));
	}

	private List<TractorTasksData> getAllTractorsTasksData() {
		// tractorTasksSumRepository.deleteAll();
		// tractorRepository.deleteAll();
		List<TractorTasksData> tasks = new ArrayList<>();
		tractorTasksSumRepository.findAll().forEach(tasks::add);
		if (tasks.isEmpty()) {
			return creatMockDataInRedis();
		}
		return tasks;
	}

	private List<TractorTasksData> creatMockDataInRedis() {
		List<Tractor> tractors = createMockTractors(10);
		List<TractorTasksData> tasksData = createMockTractorsTasksData(tractors, 10);
		tractorRepository.saveAll(tractors);
		tractorTasksSumRepository.saveAll(tasksData);
		return tasksData;
	}

	@Override
	public List<TractorTasksData> createMockTractorsTasksData(List<Tractor> tractors, int numTasks) {
		List<TractorTasksData> tasksData = new ArrayList<>();
		int range = 50;
		int index = 1;
		for (Tractor tractor : tractors) {
			for (int j = 0; j < numTasks; j++) {
				TractorTasksData taskData = new TractorTasksData();
				taskData.setId(index++);
				taskData.setTractorId(tractor.getId());
				taskData.setTasksDate(LocalDate.now().minusDays(10 - j));
				taskData.setNumTasksExecuted((int) (Math.random() * range));
				taskData.setNumTasksPlanned((int) (Math.random() * 10) + taskData.getNumTasksExecuted());
				tasksData.add(taskData);
			}
		}
		return tasksData;
	}

	@Override
	public List<Tractor> createMockTractors(int numTractors) {
		List<Tractor> tractors = new ArrayList<>();
		for (int i = 1; i <= numTractors; i++) {
			Tractor tractor = new Tractor();
			tractor.setId(i);
			tractor.setTractorName("Tractor number " + i);
			tractors.add(tractor);
		}
		return tractors;
	}

	@Override
	public List<TractorTasksData> getTractorTasksDataByTractorId(Integer tractorId) {
		return tractorTasksSumRepository.findByTractorId(tractorId);
	}

}
