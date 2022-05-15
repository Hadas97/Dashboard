package bwrobotics.service;

import java.util.List;
import java.util.Map;

import bwrobotics.dto.TractorExecutedTasksPercentDTO;
import bwrobotics.entity.Tractor;
import bwrobotics.entity.TractorTasksData;

public interface TractorService {

	/**
	 * get list of tractors
	 * 
	 * @return
	 */
	List<Tractor> getAllTractors();

	/**
	 * get list of tractor names and percentage of executed tasks
	 * 
	 * @return
	 */
	List<TractorExecutedTasksPercentDTO> getTractorExecutedTaksksPercent();

	/**
	 * group tractors tasksData by tractorId
	 * 
	 * @param allTractorsTasks
	 * @return
	 */
	Map<Integer, List<TractorTasksData>> groupTractorsTasksDataByTractorId(List<TractorTasksData> allTractorsTasks);

	/**
	 * calculates the percentage of executed tasks
	 * 
	 * @param tractors
	 * @param tasksByTractorId
	 * @return
	 */
	List<TractorExecutedTasksPercentDTO> calculateTractorExecutedTasksPercent(List<Tractor> tractors,
			Map<Integer, List<TractorTasksData>> tasksByTractorId);
}
