package br.com.senai.saep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.saep.entity.Transportadora;
import br.com.senai.saep.repository.TransportadorasRepository;

@Service
public class TransportadoraService {
	
	@Autowired
	private TransportadorasRepository repository;
	
	public Transportadora buscarPor(String email, String senha) {
		Transportadora transportadoraEncontrada = repository.buscarPor(email, senha);
		Preconditions.checkNotNull(transportadoraEncontrada, 
				"Não foi encontrado transportadora com as credenciais informadas.");
		return transportadoraEncontrada;
	}

}
