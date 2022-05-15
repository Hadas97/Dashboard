package bwrobotics.entity;

import org.springframework.data.redis.core.RedisHash;

import lombok.Data;

@Data
@RedisHash("Tractor")
public class Tractor {
	Integer id;
	String tractorName;
}
