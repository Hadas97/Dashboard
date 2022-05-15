package bwrobotics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bwrobotics.entity.TractorTasksData;
import bwrobotics.service.TractorTasksDataService;

@RequestMapping("/tractorTasksData")
@RestController
public class TractorTasksDataController {

	@Autowired
	TractorTasksDataService tractorTasksDataService;

	@RequestMapping(value = "/getAllTractorTasksDataSum")
	@CrossOrigin
	public List<TractorTasksData> getAllTractorTasksDataSum() {
		return tractorTasksDataService.getAllTractorTasksDataSum();
	}

	@RequestMapping(value = "/getTractorTasksDataByTractorId")
	@CrossOrigin
	public List<TractorTasksData> getTractorTasksDataByTractorId(@RequestParam Integer tractorId) {
		return tractorTasksDataService.getTractorTasksDataByTractorId(tractorId);
	}
}
