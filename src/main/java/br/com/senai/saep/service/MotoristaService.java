package br.com.senai.saep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.google.common.base.Preconditions;

import br.com.senai.saep.entity.Motorista;
import br.com.senai.saep.repository.MotoristasRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Service @Validated
public class MotoristaService {
	
	@Autowired
	private MotoristasRepository repository;
	
	public Motorista salvar(
			@Valid
			@NotNull(message = "O motorista é obrigatório")
			Motorista motorista) {
		return repository.save(motorista);
	}
	
	 public List<Motorista> buscarPorTransportadora(Integer idDaTransportadora) {
		 return (List<Motorista>) repository.buscarPorTransportadora(idDaTransportadora);
    }
	 
	 public List<Motorista> listarPorNome(Integer idDaTransportadora, String nome) {
		 return (List<Motorista>) repository.ListarPorNome(idDaTransportadora, "%" + nome + "%");
    }
	 
	public void excluirPor(	@NotNull(message = "O id da transportadora é obrigatório")
			Integer idDaTransportadora,
			@NotNull(message = "O id do motorista é obrigatória")
			Integer idDoMotorista) {
		Motorista motoristaEncontrado = repository.buscarPor(idDaTransportadora, idDoMotorista);
		Preconditions.checkNotNull(motoristaEncontrado, 
				"Não foi encontrado motorista com o id informado.");
	    repository.deleteById(idDoMotorista);
	}
	 
}
