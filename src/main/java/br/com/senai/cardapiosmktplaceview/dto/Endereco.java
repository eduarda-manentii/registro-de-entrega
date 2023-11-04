package br.com.senai.cardapiosmktplaceview.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Endereco {
	
	@NotBlank(message = "O nome da cidade é obrigatório")
	private String cidade;
	
	@NotBlank(message = "O logradouro é obrigatório")
	private String logradouro;
		
	@NotBlank(message = "O bairro é obrigatório")	
	private String bairro;
		
	private String complemento;
	
}
