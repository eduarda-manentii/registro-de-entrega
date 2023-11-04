package br.com.senai.cardapiosmktplaceview.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credencial {

	@NotBlank(message = "O login é obrigatório")
	private String login;

	@NotBlank(message = "A senha é obrigatória")
	private String senha;

}
