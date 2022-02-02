package br.com.alura.spring.data.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.alura.spring.data.orm.Cargo;

public interface CargoRepository extends CrudRepository<Cargo, Integer> {

}
