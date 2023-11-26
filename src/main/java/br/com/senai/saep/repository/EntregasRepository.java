package br.com.senai.saep.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.saep.entity.Entrega;

@Repository
public interface EntregasRepository extends JpaRepository<Entrega, Integer>  {
	
	  @Query(value = "SELECT m "
	  		+ "FROM Entrega e JOIN e.motorista m "
	  		+ "WHERE m.id = :idDoMotorista AND e.id = :idDaEntrega")
	   public Entrega buscarPor(Integer idDoMotorista, Integer idDaEntrega);
	  
	  @Query(value = 
				"SELECT e "
				+ "FROM Entrega e "
				+ "WHERE e.motorista.id = :id")
		public List<Entrega> listarPor(Integer id);

}
