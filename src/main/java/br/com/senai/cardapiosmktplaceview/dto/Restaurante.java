package br.com.senai.cardapiosmktplaceview.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.senai.cardapiosmktplaceview.enums.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(Include.NON_NULL)
public class Restaurante {
	
	@EqualsAndHashCode.Include
	private Integer id;
		
	@NotBlank(message = "O nome do restaurante é obrigatório")
	private String nome;
	
	@NotBlank(message = "A descrição do restaurante é obrigatória")	
	private String descricao;
		
	@Valid
	@ToString.Exclude
	private Endereco endereco;
	
	@ToString.Exclude
	@NotNull(message = "A categoria é obrigatória")
	private Categoria categoria;
	
	@NotNull(message = "O status do endereço não deve ser nulo")	
	private Status status;
	
	public Restaurante() {
		this.status = Status.A;
		this.endereco = new Endereco();
	}
		
	public boolean isPersistido() {
		return getId() != null && getId() > 0;
	}
		
	public boolean isAtivo() {
		return getStatus() == Status.A;
	}
	
}
