package br.com.senai.saep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.saep.entity.Transportadora;

@Repository
public interface TransportadorasRepository extends JpaRepository<Transportadora, Integer> {
	
	@Query("SELECT t "
			+ "FROM Transportadora t "
			+ "WHERE t.email = :email "
			+ "AND t.senha = :senha")
	public Transportadora buscarPor(String email, String senha);
	
	@Query("SELECT t "
			+ "FROM Transportadora t "
			+ "WHERE id = :id ")
	public Transportadora buscarPor(Integer id);
	
}
