package bwrobotics.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bwrobotics.entity.TractorTasksData;

@Repository
public interface TractorTasksDataRepository extends CrudRepository<TractorTasksData, Integer> {

	List<TractorTasksData> findByTractorId(Integer tractorId);
	
}
