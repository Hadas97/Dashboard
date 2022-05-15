package bwrobotics.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bwrobotics.entity.Tractor;

@Repository
public interface TractorRepository extends CrudRepository<Tractor, Integer> {

}
