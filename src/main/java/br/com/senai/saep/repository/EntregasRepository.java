package br.com.senai.saep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senai.saep.entity.Entrega;

@Repository
public interface EntregasRepository extends JpaRepository<Entrega, Integer>  {

}
