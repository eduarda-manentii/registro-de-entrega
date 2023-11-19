package br.com.senai.saep.view.componentes.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.senai.saep.entity.Entrega;

public class EntregaTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

private final int QTDE_COLUNAS = 2;
	
	private List<Entrega> entregas;

	public EntregaTableModel() {
		this.entregas = new ArrayList<>();
	}
	
	public EntregaTableModel(List<Entrega> entregas) {
		this();
		if (entregas != null && !entregas.isEmpty()) {		
			this.entregas = entregas;
		}
	}

	@Override
	public int getRowCount() {	
		return entregas.size();
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
			return "Descricao";
		} 
		throw new IllegalArgumentException("Indíce inválido");
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return entregas.get(rowIndex).getId();
		}else if (columnIndex == 1) {
			return entregas.get(rowIndex).getDescricao();
		}
		throw new IllegalArgumentException("Índice inválido");
	}
	
	public Entrega getPor(int rowIndex) {
		return entregas.get(rowIndex);
	}	

}
