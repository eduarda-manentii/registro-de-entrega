package br.com.senai.saep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.google.common.base.Preconditions;

import br.com.senai.saep.entity.Motorista;
import br.com.senai.saep.repository.EntregasRepository;
import br.com.senai.saep.repository.MotoristasRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Validated
@Service
public class MotoristaService {

	@Autowired
	private MotoristasRepository repository;
	
	@Autowired
	private EntregasRepository entregasRepository;
	
	public Motorista salvar(
			@Valid
			@NotNull(message = "O motorista é obrigatório")
			Motorista motorista) {
		return repository.save(motorista);
	}
	
	public void excluir(
			@NotNull(message = "O motorista é obrigatório")
			Motorista motorista) {
		Preconditions.checkArgument(entregasRepository.contarPor(motorista.getId()) == 0, 
				"O motorista não pode ser excluido pois já possui entregas realizadas");
		this.repository.deleteById(motorista.getId());
	}
	
	public List<Motorista> listarTodos(){
		return repository.listarTodos();
	}
	
}
