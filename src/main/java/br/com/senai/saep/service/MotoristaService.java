package br.com.senai.saep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senai.saep.entity.Motorista;
import br.com.senai.saep.repository.MotoristasRepository;

@Service
public class MotoristaService {
	
	@Autowired
	private MotoristasRepository repository;
	
	public Motorista salvar(Motorista motorista) {
		return repository.save(motorista);
	}

}
