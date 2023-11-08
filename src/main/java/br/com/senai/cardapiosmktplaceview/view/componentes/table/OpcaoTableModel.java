package br.com.senai.cardapiosmktplaceview.view.componentes.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.senai.cardapiosmktplaceview.dto.Opcao;

public class OpcaoTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	
	private final int QTDE_COLUNAS = 3;
	
	private List<Opcao> opcoes;
	
	public OpcaoTableModel() {
		this.opcoes = new ArrayList<>();
	}

	public OpcaoTableModel(List<Opcao> opcoes) {
		this();
		if (opcoes != null && !opcoes.isEmpty()) {		
			this.opcoes = opcoes;
		}
	}
	
	@Override
	public int getRowCount() {
		return opcoes.size();
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
			return opcoes.get(rowIndex).getId();
		}else if (columnIndex == 1) {
			return opcoes.get(rowIndex).getNome();
		}else if (columnIndex == 2) {
			return opcoes.get(rowIndex).getStatus();
		}
		throw new IllegalArgumentException("Índice inválido");
	}
	
	public Opcao getPor(int rowIndex) {
		return opcoes.get(rowIndex);
	}
		
}
