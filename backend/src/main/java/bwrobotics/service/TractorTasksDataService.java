package bwrobotics.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import bwrobotics.entity.Tractor;
import bwrobotics.entity.TractorTasksData;

public interface TractorTasksDataService {

	/**
	 * get summary of executed and planned tasks for total tractors
	 * 
	 * @return
	 */
	List<TractorTasksData> getAllTractorTasksDataSum();

	/**
	 * get number of executed and planned tasks for specific tractors
	 * 
	 * @param tractorId
	 * @return
	 */
	List<TractorTasksData> getTractorTasksDataByTractorId(Integer tractorId);

	/**
	 * sums up total tractors planned and executed tasks
	 * 
	 * @param tasksPerDate
	 * @return
	 */
	List<TractorTasksData> sumUpTractorsTasksData(Map<LocalDate, List<TractorTasksData>> tasksPerDate);

	/**
	 * group tractors tasksData by tasksDate
	 * 
	 * @param allTractorsTasks
	 * @return
	 */
	Map<LocalDate, List<TractorTasksData>> groupTractorsTasksDataByDate(List<TractorTasksData> allTractorsTasks);

	List<Tractor> createMockTractors(int numTractors);

	List<TractorTasksData> createMockTractorsTasksData(List<Tractor> tractors, int numTasks);

}
