package br.com.senai.saep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senai.saep.entity.Motorista;

@Repository
public interface MotoristasRepository extends JpaRepository<Motorista, Integer> {

}
