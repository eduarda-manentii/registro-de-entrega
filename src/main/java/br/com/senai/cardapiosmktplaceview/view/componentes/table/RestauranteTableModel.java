package br.com.senai.cardapiosmktplaceview.view.componentes.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.senai.cardapiosmktplaceview.dto.Restaurante;

public class RestauranteTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	
	private final int QTDE_COLUNAS = 3;
	
	private List<Restaurante> restaurantes;
	
	public RestauranteTableModel() {
		this.restaurantes = new ArrayList<>();
	}
	
	public RestauranteTableModel(List<Restaurante> restaurantes) {
		this();
		if (restaurantes != null && !restaurantes.isEmpty()) {		
			this.restaurantes = restaurantes;
		}
	}

	@Override
	public int getRowCount() {
		return restaurantes.size();
	}

	@Override
	public int getColumnCount() {
		return QTDE_COLUNAS;
	}
	
	@Override
	public String getColumnName(int column) {
		if (column == 0) {
			return "ID";
		}else if (column == 1) {
			return "Nome";
		}else if (column == 2) {
			return "Status";
		}
		throw new IllegalArgumentException("Indíce inválido");
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return restaurantes.get(rowIndex).getId();
		}else if (columnIndex == 1) {
			return restaurantes.get(rowIndex).getNome();
		}else if (columnIndex == 2) {
			return restaurantes.get(rowIndex).getStatus();
		}
		throw new IllegalArgumentException("Índice inválido");
	}
	
	public Restaurante getPor(int rowIndex) {
		return restaurantes.get(rowIndex);
	}

}
