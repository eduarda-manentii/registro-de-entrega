package br.com.senai.saep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.google.common.base.Preconditions;

import br.com.senai.saep.entity.Transportadora;
import br.com.senai.saep.repository.TransportadorasRepository;
import jakarta.validation.constraints.NotBlank;

@Validated
@Service
public class TransportadoraService {

	@Autowired
	private TransportadorasRepository repository;
	
	public Transportadora logarPor(
			@NotBlank(message = "O e-mail é obrigatório")
			String email,
			@NotBlank(message = "A senha é obrigatória")
			String senha) {
		Transportadora transportadora = repository.buscarPor(email, senha);
		Preconditions.checkNotNull(transportadora, "Login e/ou senha inválidos");
		return transportadora;
	}
	
}
