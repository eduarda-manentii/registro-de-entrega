package br.com.senai.cardapiosmktplaceview.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.senai.cardapiosmktplaceview.enums.Status;
import br.com.senai.cardapiosmktplaceview.enums.TipoDeCategoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Categoria {

	private Integer id;
	
	@NotBlank(message = "O nome da categoria é obrigatório")
	private String nome;
	
	@NotNull(message = "O tipo da categoria é obrigatório")
	private TipoDeCategoria tipo;
	
	private Status status;
	
	public boolean isPersistida() {
		return getId() != null && getId() > 0;
	}
	
	public boolean isAtiva() {
		return getStatus() == Status.A;
	}
	
}
