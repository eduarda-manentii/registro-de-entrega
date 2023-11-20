package br.com.senai.saep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senai.saep.entity.Entrega;
import br.com.senai.saep.repository.EntregasRepository;


@Service
public class EntregaService {
	
	@Autowired
	private EntregasRepository repository;
	
	public Entrega salvar(Entrega entrega) {
		return repository.save(entrega);
	}

}
