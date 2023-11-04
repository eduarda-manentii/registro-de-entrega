package br.com.senai.cardapiosmktplaceview.view.selecao.categoria;

import br.com.senai.cardapiosmktplaceview.dto.Categoria;
import jakarta.validation.constraints.NotNull;

public interface IReceptorDeCategoriaSelecionada {
	
	public void usar(
			@NotNull(message = "A categoria selecionada é obrigatória")
			Categoria categoriaSelecionada);

}
