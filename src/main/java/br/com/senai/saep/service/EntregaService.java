package br.com.senai.saep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.senai.saep.entity.Entrega;
import br.com.senai.saep.repository.EntregasRepository;
import jakarta.validation.constraints.NotNull;


@Service @Validated
public class EntregaService {
	
	@Autowired
	private EntregasRepository repository;
	
	public Entrega salvar(Entrega entrega) {
		return repository.save(entrega);
	}
	
	public List<Entrega> listarPor(
			@NotNull(message = "O id da transportadora é obrigatório para listar o motorista") 
				Integer id){
		return repository.listarPor(id);
	}

}
