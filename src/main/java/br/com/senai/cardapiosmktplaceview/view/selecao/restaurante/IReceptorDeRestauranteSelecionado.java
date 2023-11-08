package br.com.senai.cardapiosmktplaceview.view.selecao.restaurante;

import br.com.senai.cardapiosmktplaceview.dto.Restaurante;
import jakarta.validation.constraints.NotNull;

public interface IReceptorDeRestauranteSelecionado {

	public void usar(
			@NotNull(message = "O restaurante selecionado é obrigatório")			
			Restaurante restauranteSelecionado);
	
}
