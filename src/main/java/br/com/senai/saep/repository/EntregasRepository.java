package br.com.senai.saep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.saep.entity.Entrega;

@Repository
public interface EntregasRepository extends JpaRepository<Entrega, Integer>  {
	
	  @Query(value = "SELECT m "
	  		+ "FROM Motorista m "
	  		+ "WHERE m.motorista.id = :idDoMotorista "
	  		+ "AND m.id = :idDaEntrega")
	   public Entrega buscarPor(Integer idDoMotorista, Integer idDaEntrega);

}
