package bwrobotics.entity;

import java.time.LocalDate;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;

@Data
@RedisHash("TractorTasksData")
public class TractorTasksData {
	Integer id;
	@Indexed
	Integer tractorId;
	LocalDate tasksDate;
	Integer numTasksPlanned;
	Integer numTasksExecuted;
}
