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
import br.com.senai.cardapiosmktplaceview.enums.Status;
import br.com.senai.cardapiosmktplaceview.enums.TipoDeCategoria;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Setter;

@Component
public class CategoriaClient {

	private final String RESOURCE = "/categorias";
	
	@Value("${url-api}")
	private String urlDaApi;
	
	@Autowired
	private AplicadorDeToken aplicador;
	
	@Autowired
	private RestTemplate httpClient;
	
	@Setter
	private String tokenDeAcesso;
	
	public Categoria inserir(
			@Valid
			@NotNull(message = "A nova categoria não pode ser nula")
			Categoria novaCategoria) {
		
		HttpEntity<Categoria> request = new HttpEntity<Categoria>(
				novaCategoria, aplicador.aplicar(tokenDeAcesso));
		
		URI location = httpClient.postForLocation(urlDaApi + RESOURCE, request);		

		request = new HttpEntity<Categoria>(aplicador.aplicar(tokenDeAcesso));		
		
		ResponseEntity<Categoria> categoriaSalva = httpClient.exchange(
				urlDaApi + location, HttpMethod.GET, request, Categoria.class);
		
		return categoriaSalva.getBody();

	}
	
	public Categoria atualizar(
			@Valid
			@NotNull(message = "A categoria salva não pode ser nula")
			Categoria categoriaSalva) {
		
		HttpEntity<Categoria> request = new HttpEntity<Categoria>(
				categoriaSalva, aplicador.aplicar(tokenDeAcesso));
		
		ResponseEntity<Categoria> categoriaAtualizada = httpClient.exchange(
				urlDaApi + RESOURCE, HttpMethod.PUT, request, Categoria.class);
		
		return categoriaAtualizada.getBody();
		
	}
	
	public void atualizarPor(
			@NotNull(message = "O id da categoria é obrigatório")
			@Positive(message = "O id da categoria deve positivo")
			Integer id,	
			@NotNull(message = "O status da categoria é obrigatório")
			Status status) {
		
		HttpEntity<Categoria> request = new HttpEntity<Categoria>(aplicador.aplicar(tokenDeAcesso));
		
		this.httpClient.patchForObject(urlDaApi + RESOURCE + "/id/" + id + "/status/" + status, request, Void.class);
		
	}
	
	public Categoria removerPor(
			@NotNull(message = "O id da categoria é obrigatório")
			@Positive(message = "O id da categoria deve positivo")
			Integer id) {			
		
		HttpEntity<Categoria> request = new HttpEntity<Categoria>(aplicador.aplicar(tokenDeAcesso));
		
		ResponseEntity<Categoria> categoriaRemovida = httpClient.exchange(
				urlDaApi + RESOURCE + "/id/" + id, HttpMethod.DELETE, request, Categoria.class);
		
		return categoriaRemovida.getBody();
		
	}
	
	public Paginacao<Categoria> listarPor(
			@NotBlank(message = "O nome deve conter mais de 3 caracteres")
			String nome, 
			@NotNull(message = "O status da categoria é obrigatório")
			Status status, 
			@NotNull(message = "O tipo de categoria é obrigatório")
			TipoDeCategoria tipo, 
			@Positive(message = "A página de registros deve ser positiva")
			Integer pagina){
		
		StringBuilder queryParams = new StringBuilder();
		queryParams.append("?nome=").append(nome);
		queryParams.append("&status=").append(status);
		queryParams.append("&tipo=").append(tipo);
		queryParams.append("&pagina=").append(pagina);
		
		HttpEntity<Paginacao<Categoria>> request = new HttpEntity<Paginacao<Categoria>>(
				aplicador.aplicar(tokenDeAcesso));
		
		ResponseEntity<Paginacao<Categoria>> categoriasEncontradas = httpClient.exchange(
				urlDaApi + RESOURCE + queryParams, HttpMethod.GET, request, 
				new ParameterizedTypeReference<Paginacao<Categoria>>(){});
		
		return categoriasEncontradas.getBody();

	}
	
	public Categoria buscarPor(
			@NotNull(message = "O id da categoria é obrigatório")
			@Positive(message = "O id da categoria deve ser maior que zero")
			Integer id) {
		
		HttpEntity<Categoria> request = new HttpEntity<Categoria>(aplicador.aplicar(tokenDeAcesso));		
		
		ResponseEntity<Categoria> categoriaEncontrada = httpClient.exchange(
				urlDaApi + RESOURCE + "/id/" + id, HttpMethod.GET, request, Categoria.class);
		
		return categoriaEncontrada.getBody();
		
	}
	
}
