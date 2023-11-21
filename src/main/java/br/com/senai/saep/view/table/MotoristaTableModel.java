package br.com.senai.saep.view.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import br.com.senai.saep.entity.Motorista;

public class MotoristaTableModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 1L;
	
	private final int QTDE_COLUNAS = 4;
	
	private List<Motorista> motoristas;
	
	public MotoristaTableModel() {
		this.motoristas = new ArrayList<>();
	}
	
	public MotoristaTableModel(List<Motorista> motoristas) {
		this();
		if (motoristas != null && !motoristas.isEmpty()) {		
			this.motoristas = motoristas;
		}
	}
	
	@Override
	public int getRowCount() {	
		return motoristas.size();
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
			return "CNH";
		}else if (column == 3) {
			return "Ações";
		}
		throw new IllegalArgumentException("Indíce inválido");
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return motoristas.get(rowIndex).getId();
		}else if (columnIndex == 1) {
			return motoristas.get(rowIndex).getNome();
		}else if (columnIndex == 2) {
			return motoristas.get(rowIndex).getCnh();
		}else if (columnIndex == 3){
			return motoristas.get(rowIndex);
		}
		throw new IllegalArgumentException("Índice inválido");
	}
	
	@Override
    public Class<?> getColumnClass(int columnIndex) {
		
		if (columnIndex >= 0 && columnIndex < 3) {
			return JLabel.class;
		}else if(columnIndex == 3) {
			return Motorista.class;
		}
		
		throw new IllegalArgumentException("Índice inválido");
		
    }
	
	public boolean isCellEditable(int rowIndex, int columnIndex) {		
		return columnIndex == 3;		
	}
	
	public Motorista getPor(int rowIndex) {
		return motoristas.get(rowIndex);
	}
	
}