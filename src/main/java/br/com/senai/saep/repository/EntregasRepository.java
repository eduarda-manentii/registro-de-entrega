package br.com.senai.saep.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.saep.entity.Entrega;

@Repository
public interface EntregasRepository extends JpaRepository<Entrega, Integer>{

	@Query(value = 
			"SELECT Count(e) "
			+ "FROM Entrega e "
			+ "WHERE e.motorista.id = :idDoMotorista ")
	public Long contarPor(Integer idDoMotorista);
	
	@Query(value = 
			"SELECT e "
			+ "FROM Entrega e "
			+ "JOIN FETCH e.motorista "
			+ "ORDER BY e.id ")
	public List<Entrega> listarTodas();
	
}
