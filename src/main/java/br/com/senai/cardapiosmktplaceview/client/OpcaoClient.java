package br.com.senai.cardapiosmktplaceview.client;

import java.io.File;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import br.com.senai.cardapiosmktplaceview.dto.Categoria;
import br.com.senai.cardapiosmktplaceview.dto.Opcao;
import br.com.senai.cardapiosmktplaceview.dto.Paginacao;
import br.com.senai.cardapiosmktplaceview.dto.Restaurante;
import br.com.senai.cardapiosmktplaceview.enums.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Setter;

@Component
@Validated
public class OpcaoClient {
	
	private final String RESOURCE = "/opcoes";
	
	@Value("${url-api}")
	private String urlDaApi;
	
	@Autowired
	private AplicadorDeToken aplicador;
	
	@Autowired
	private RestTemplate httpClient;
	
	@Setter
	private String tokenDeAcesso;
	
	public Opcao inserir(
			@Valid
			@NotNull(message = "A nova opcao não pode ser nula")
			Opcao novaOpcao) {
		
		HttpEntity<Opcao> request = new HttpEntity<Opcao>(
				novaOpcao, aplicador.aplicar(tokenDeAcesso));
		
		URI location = httpClient.postForLocation(urlDaApi + RESOURCE, request);		

		request = new HttpEntity<Opcao>(aplicador.aplicar(tokenDeAcesso));		
		
		ResponseEntity<Opcao> opcaoSalva = httpClient.exchange(
				urlDaApi + location, HttpMethod.GET, request, Opcao.class);
		
		return opcaoSalva.getBody();
		
	}
	
	public Opcao atualizar(
			@Valid
			@NotNull(message = "A opção salva não pode ser nula")
			Opcao opcaoSalva) {
		
		HttpEntity<Opcao> request = new HttpEntity<Opcao>(
				opcaoSalva, aplicador.aplicar(tokenDeAcesso));
		
		ResponseEntity<Opcao> opcaoAtualizada = httpClient.exchange(
				urlDaApi + RESOURCE, HttpMethod.PUT, request, Opcao.class);
		
		return opcaoAtualizada.getBody();
		
	}
	
	public void atualizarPor(
			@NotNull(message = "O id da opção é obrigatório")
			@Positive(message = "O id da opção deve positivo")
			Integer id,	
			@NotNull(message = "O status da opção é obrigatório")
			Status status) {
		
		HttpEntity<Opcao> request = new HttpEntity<Opcao>(aplicador.aplicar(tokenDeAcesso));
		
		this.httpClient.patchForObject(urlDaApi + RESOURCE 
				+ "/id/" + id + "/status/" + status, request, Void.class);
		
	}
	
	public Opcao removerPor(
			@NotNull(message = "O id da opção é obrigatório")
			@Positive(message = "O id da opção deve positivo")
			Integer id) {
		
		HttpEntity<Opcao> request = new HttpEntity<Opcao>(aplicador.aplicar(tokenDeAcesso));
		
		ResponseEntity<Opcao> opcaoRemovida = httpClient.exchange(
				urlDaApi + RESOURCE + "/id/" + id, HttpMethod.DELETE, request, Opcao.class);
		
		return opcaoRemovida.getBody();
		
	}
	
	public void upload(
			@NotNull(message = "A foto da opção é obrigatória")
			File foto,
			@NotNull(message = "O id da opção é obrigatório")
			@Positive(message = "O id da opção deve positivo")
			Integer id) {
		
		HttpHeaders headers = aplicador.aplicar(tokenDeAcesso);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();        
        body.add("foto", new FileSystemResource(foto));

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        this.httpClient.postForObject(urlDaApi + RESOURCE 
        		+ "/id/" + id + "/upload", request, Void.class);
		
	}
	
	public Paginacao<Opcao> listarPor(
			@NotBlank(message = "O nome deve conter mais de 3 caracteres")
			String nome, 
			@NotNull(message = "A categoria é obrigatória")
			Categoria categoria,
			@NotNull(message = "O restaurante é obrigatório")
			Restaurante restaurante,
			@PositiveOrZero(message = "A página de registros deve ser positiva")
			Integer pagina){
		
		StringBuilder queryParams = new StringBuilder();
		queryParams.append("?nome=").append(nome);
		queryParams.append("&id-categoria=").append(categoria.getId());
		queryParams.append("&id-restaurante=").append(restaurante.getId());
		queryParams.append("&pagina=").append(pagina);

		HttpEntity<Paginacao<Opcao>> request = new HttpEntity<Paginacao<Opcao>>(
				aplicador.aplicar(tokenDeAcesso));

		ResponseEntity<Paginacao<Opcao>> opcoesEncontradas = httpClient.exchange(
				urlDaApi + RESOURCE + queryParams, HttpMethod.GET, request, 
				new ParameterizedTypeReference<Paginacao<Opcao>>(){});

		return opcoesEncontradas.getBody();

	}	

}
