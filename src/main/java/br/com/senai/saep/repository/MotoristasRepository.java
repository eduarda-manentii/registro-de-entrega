package br.com.senai.saep.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.saep.entity.Motorista;

@Repository
public interface MotoristasRepository extends JpaRepository<Motorista, Integer> {
	
	  @Query(value = "SELECT m "
	  		+ "FROM Motorista m "
	  		+ "WHERE m.transportadora.id = :idDaTransportadora")
	    List<Motorista> buscarPorTransportadora(Integer idDaTransportadora);
	  
	  @Query(value = "SELECT m "
	  		+ "FROM Motorista m "
	  		+ "WHERE m.transportadora.id = :idDaTransportadora "
	  		+ "AND m.nome_completo = :nome")
	    List<Motorista> ListarPorNome(Integer idDaTransportadora, String nome);
	  
	  @Query(value = "SELECT m "
	  		+ "FROM Motorista m "
	  		+ "WHERE m.transportadora.id = :idDaTransportadora "
		  	+ "AND m.id = :idDoMotorista")
	   public Motorista buscarPor(Integer idDaTransportadora, Integer idDoMotorista);

}
