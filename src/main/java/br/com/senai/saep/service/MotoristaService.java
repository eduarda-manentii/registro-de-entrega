package br.com.senai.saep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.google.common.base.Preconditions;

import br.com.senai.saep.entity.Motorista;
import br.com.senai.saep.repository.MotoristasRepository;

@Service @Validated
public class MotoristaService {
	
	@Autowired
	private MotoristasRepository repository;
	
	public Motorista salvar(Motorista motorista) {
		return repository.save(motorista);
	}
	
	 public List<Motorista> listarPor(Integer idDaTransportadora) {
		 return (List<Motorista>) repository.listarPor(idDaTransportadora);
    }
	 
	 public List<Motorista> listarPorNome(Integer idDaTransportadora, String nome) {
		 return (List<Motorista>) repository.listarPorNome(idDaTransportadora, "%" + nome + "%");
    }
	 
	public void excluirPor(Integer idDaTransportadora, Integer idDoMotorista) {
		Motorista motoristaEncontrado = repository.buscarPor(idDaTransportadora, idDoMotorista);
		Preconditions.checkNotNull(motoristaEncontrado, 
				"Não foi encontrado motorista com o id informado.");
	    repository.deleteById(idDoMotorista);
	}
	 
}
