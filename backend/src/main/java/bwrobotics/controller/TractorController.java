package bwrobotics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bwrobotics.dto.TractorExecutedTasksPercentDTO;
import bwrobotics.entity.Tractor;
import bwrobotics.service.TractorService;

@RequestMapping("/tractor")
@RestController
public class TractorController {

	@Autowired
	TractorService tractorTasksService;

	@RequestMapping(value = "/getAllTractors")
	@CrossOrigin
	public List<Tractor> getAllTractors() {
		return tractorTasksService.getAllTractors();
	}

	@RequestMapping(value = "/getTractorExecutedTaksksPercent")
	@CrossOrigin
	public List<TractorExecutedTasksPercentDTO> getTractorExecutedTaksksPercent() {
		return tractorTasksService.getTractorExecutedTaksksPercent();
	}
}
