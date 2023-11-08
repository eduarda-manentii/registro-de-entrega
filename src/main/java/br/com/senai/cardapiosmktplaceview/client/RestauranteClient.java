package br.com.senai.cardapiosmktplaceview.client;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.senai.cardapiosmktplaceview.dto.Categoria;
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
public class RestauranteClient {

	private final String RESOURCE = "/restaurantes";
	
	@Value("${url-api}")
	private String urlDaApi;
	
	@Autowired
	private AplicadorDeToken aplicador;
	
	@Autowired
	private RestTemplate httpClient;
	
	@Setter
	private String tokenDeAcesso;
	
	public Restaurante inserir(
			@Valid
			@NotNull(message = "O novo restaurante não pode ser nulo")
			Restaurante novoRestaurante) {
		
		HttpEntity<Restaurante> request = new HttpEntity<Restaurante>(
				novoRestaurante, aplicador.aplicar(tokenDeAcesso));
		
		URI location = httpClient.postForLocation(urlDaApi + RESOURCE, request);		

		request = new HttpEntity<Restaurante>(aplicador.aplicar(tokenDeAcesso));		
		
		ResponseEntity<Restaurante> restauranteSalvo = httpClient.exchange(
				urlDaApi + location, HttpMethod.GET, request, Restaurante.class);
		
		return restauranteSalvo.getBody();
		
	}
	
	public Restaurante atualizar(
			@Valid
			@NotNull(message = "O restaurante salvo não pode ser nulo")
			Restaurante restauranteSalvo) {
		
		HttpEntity<Restaurante> request = new HttpEntity<Restaurante>(
				restauranteSalvo, aplicador.aplicar(tokenDeAcesso));
		
		ResponseEntity<Restaurante> restauranteAtualizado = httpClient.exchange(
				urlDaApi + RESOURCE, HttpMethod.PUT, request, Restaurante.class);
		
		return restauranteAtualizado.getBody();
		
	}
	
	public void atualizarPor(
			@NotNull(message = "O id do restaurante é obrigatório")
			@Positive(message = "O id do restaurante deve positivo")
			Integer id,	
			@NotNull(message = "O status do restaurante é obrigatório")
			Status status) {
		
		HttpEntity<Restaurante> request = new HttpEntity<Restaurante>(aplicador.aplicar(tokenDeAcesso));
		
		this.httpClient.patchForObject(urlDaApi + RESOURCE + "/id/" + id + "/status/" + status, request, Void.class);
		
	}
	
	public Restaurante removerPor(
			@NotNull(message = "O id da categoria é obrigatório")
			@Positive(message = "O id da categoria deve positivo")
			Integer id) {
		
		HttpEntity<Restaurante> request = new HttpEntity<Restaurante>(aplicador.aplicar(tokenDeAcesso));
		
		ResponseEntity<Restaurante> restauranteRemovido = httpClient.exchange(
				urlDaApi + RESOURCE + "/id/" + id, HttpMethod.DELETE, request, Restaurante.class);
		
		return restauranteRemovido.getBody();
		
	}
	
	public Paginacao<Restaurante> listarPor(
			@NotBlank(message = "O nome deve conter mais de 3 caracteres")
			String nome, 
			@NotNull(message = "A categoria é obrigatória")
			Categoria categoria,
			@PositiveOrZero(message = "A página de registros deve ser positiva")
			Integer pagina){
		
		StringBuilder queryParams = new StringBuilder();
		queryParams.append("?nome=").append(nome);
		queryParams.append("&id-categoria=").append(categoria.getId());
		queryParams.append("&pagina=").append(pagina);
		
		HttpEntity<Paginacao<Restaurante>> request = new HttpEntity<Paginacao<Restaurante>>(
				aplicador.aplicar(tokenDeAcesso));
		
		ResponseEntity<Paginacao<Restaurante>> restaurantesEncontrados = httpClient.exchange(
				urlDaApi + RESOURCE + queryParams, HttpMethod.GET, request, 
				new ParameterizedTypeReference<Paginacao<Restaurante>>(){});
		
		return restaurantesEncontrados.getBody();
		
	}
	
}
