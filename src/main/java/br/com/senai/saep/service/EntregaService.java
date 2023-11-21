package br.com.senai.saep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.senai.saep.entity.Entrega;
import br.com.senai.saep.repository.EntregasRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Validated
@Service
public class EntregaService {
	
	@Autowired
	private EntregasRepository repository;
	
	public Entrega salvar(
			@Valid
			@NotNull(message = "A entrega é obrigatória")
			Entrega entrega) {
		return repository.save(entrega);
	}
	
	public List<Entrega> listarTodas(){
		return repository.listarTodas();
	}
	
}
