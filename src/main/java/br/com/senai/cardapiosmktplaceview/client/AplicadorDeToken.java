package br.com.senai.cardapiosmktplaceview.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotBlank;

@Component
public class AplicadorDeToken {

	public HttpHeaders aplicar(
			@NotBlank(message = "O token de acesso ao endpoint é obrigatório")
			String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
	
}
