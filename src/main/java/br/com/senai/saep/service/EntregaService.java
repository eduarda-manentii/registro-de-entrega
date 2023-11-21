package br.com.senai.saep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.google.common.base.Preconditions;

import br.com.senai.saep.entity.Entrega;
import br.com.senai.saep.repository.EntregasRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


@Service @Validated
public class EntregaService {
	
	@Autowired
	private EntregasRepository repository;
	
	public Entrega salvar(
			@Valid
			@NotNull(message = "A entrega é obrigatória")
			Entrega entrega) {
		return repository.save(entrega);
	}
	
	public void excluirPor(
			@NotNull(message = "O id do motorista é obrigatório")
			Integer idDoMotorista,
			@NotNull(message = "O id da entrega é obrigatória")
			Integer idDaEntrega) {
		Entrega entregaEncontrada = repository.buscarPor(idDoMotorista, idDaEntrega);
		Preconditions.checkNotNull(entregaEncontrada, 
				"Não foi encontrada entrega com o id informado.");
	    repository.deleteById(idDaEntrega);
	}

}
