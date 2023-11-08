package br.com.senai.cardapiosmktplaceview.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.senai.cardapiosmktplaceview.enums.Confirmacao;
import br.com.senai.cardapiosmktplaceview.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(Include.NON_NULL)
public class Opcao {
	
	@EqualsAndHashCode.Include
	private Integer id;
	
	@NotBlank(message = "O nome da opção é obrigatório")
	private String nome;
		
	private String descricao;
		
	@NotNull(message = "O status da opção não deve ser nulo")	
	private Status status;
		
	@NotNull(message = "O indicador de promoção da opção não deve ser nulo")	
	private Confirmacao promocao;
			
	private BigDecimal percentualDeDesconto;
	
	@ToString.Exclude
	@NotNull(message = "A categoria é obrigatória")
	private Categoria categoria;
	
	@ToString.Exclude	
	@NotNull(message = "O restaurante é obrigatório")
	private Restaurante restaurante;
	
	public Opcao() {
		this.status = Status.A;
		this.categoria = new Categoria();
		this.restaurante = new Restaurante();
	}
	
	public boolean isPersistida() {
		return getId() != null && getId() > 0;
	}
		
	public boolean isAtiva() {
		return getStatus() == Status.A;
	}
	
}
