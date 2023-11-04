package br.com.senai.cardapiosmktplaceview.client;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceview.dto.Credencial;
import io.jsonwebtoken.io.Decoders;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Component
public class LoginClient {
	
	private final String RESOURCE = "/auth";
	
	@Value("${url-api}")
	private String urlDaApi;

	@Autowired
	private RestTemplate httpClient;
	
	public String autenticar(
			@Valid
			@NotNull(message = "A credencial não pode ser nula")
			Credencial credencial) {
		
		ResponseEntity<String> response = httpClient.postForEntity(urlDaApi + RESOURCE, 
				credencial, String.class);
		
		JSONObject autenticacaoJson = new JSONObject(response.getBody());
		
		String token = autenticacaoJson.getString("token");
		
		String payload = new String(Decoders.BASE64URL.decode(token.split("\\.")[1]));
		
		JSONObject payloadJson = new JSONObject(payload);
		
		Preconditions.checkArgument(payloadJson.getString("papel").equals("LOJISTA"), 
				"Esse login não possui autorização para acessar esse módulo");
		
		return token;

	}

}
