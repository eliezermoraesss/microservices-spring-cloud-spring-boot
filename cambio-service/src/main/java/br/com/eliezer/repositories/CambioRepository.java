package br.com.eliezer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.eliezer.models.Cambio;

@Repository
public interface CambioRepository extends JpaRepository<Cambio, Long>{
	
	Cambio findByFromAndTo(String from, String to);
}
