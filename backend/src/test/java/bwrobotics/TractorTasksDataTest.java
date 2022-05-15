package bwrobotics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import bwrobotics.dto.TractorExecutedTasksPercentDTO;
import bwrobotics.entity.Tractor;
import bwrobotics.entity.TractorTasksData;
import bwrobotics.service.TractorService;
import bwrobotics.service.TractorTasksDataService;

@SpringBootTest
public class TractorTasksDataTest {

	@Autowired
	TractorTasksDataService tractorTasksDataService;

	@Autowired
	TractorService tractorService;

	@Test
	void testSumUpTractorsTasksData() {
		List<Tractor> tractors = tractorTasksDataService.createMockTractors(2);
		List<TractorTasksData> taskData = tractorTasksDataService.createMockTractorsTasksData(tractors, 2);
		Map<LocalDate, List<TractorTasksData>> tasksByDate = tractorTasksDataService
				.groupTractorsTasksDataByDate(taskData);
		List<TractorTasksData> tractorsTasksSummary = tractorTasksDataService.sumUpTractorsTasksData(tasksByDate);
		for (TractorTasksData tasks : tractorsTasksSummary) {
			List<TractorTasksData> curTasksByDate = tasksByDate.get(tasks.getTasksDate());
			int sumTasksPlanned = curTasksByDate.stream().mapToInt(TractorTasksData::getNumTasksPlanned).sum();
			int sumTasksExecuted = curTasksByDate.stream().mapToInt(TractorTasksData::getNumTasksExecuted).sum();
			assertEquals(sumTasksPlanned, tasks.getNumTasksPlanned());
			assertEquals(sumTasksExecuted, tasks.getNumTasksExecuted());
		}
	}

	@Test
	void testCalculateTractorExecutedTasksPercent() {
		List<Tractor> tractors = tractorTasksDataService.createMockTractors(2);
		List<TractorTasksData> taskData = tractorTasksDataService.createMockTractorsTasksData(tractors, 2);
		Map<Integer, List<TractorTasksData>> tasksByTractorId = tractorService
				.groupTractorsTasksDataByTractorId(taskData);
		List<TractorExecutedTasksPercentDTO> tractorsTasksPercent = tractorService
				.calculateTractorExecutedTasksPercent(tractors, tasksByTractorId);
		for (TractorExecutedTasksPercentDTO tasks : tractorsTasksPercent) {
			List<TractorTasksData> curTasksByTractorId = tasksByTractorId
					.get(Character.getNumericValue(tasks.getTractorName().charAt(tasks.getTractorName().length() - 1)));
			float planned = curTasksByTractorId.stream().mapToInt(TractorTasksData::getNumTasksPlanned).sum();
			float executed = curTasksByTractorId.stream().mapToInt(TractorTasksData::getNumTasksExecuted).sum();
			int percent = Math.round(executed / planned * 100);
			assertEquals(percent, tasks.getTractorPercent());
		}
	}
}
