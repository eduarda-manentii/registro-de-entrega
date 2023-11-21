package br.com.senai.saep.view.table;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import br.com.senai.saep.entity.Motorista;

public class PanelDeAcoesDoMotoristaCell extends AbstractCellEditor 
		implements TableCellRenderer, TableCellEditor{	
	
	private static final long serialVersionUID = 1L;
	
	private PanelAcoesDoMotorista panelDeAcoes;	
	
	public PanelDeAcoesDoMotoristaCell() {
		this.panelDeAcoes = new PanelAcoesDoMotorista();	
	}
	
	@Override	
	public Component getTableCellRendererComponent(JTable table, Object value,
	        boolean isSelected, boolean hasFocus, int row, int column) {		
		Motorista motorista = (Motorista)value;		
		this.panelDeAcoes.setMotorista(motorista);				
		if (isSelected) {
			this.panelDeAcoes.setBackground(table.getSelectionBackground());
		}else{
			this.panelDeAcoes.setBackground(table.getBackground());
		}
		return panelDeAcoes;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
		      boolean isSelected, int row, int column) {
		Motorista motorista = (Motorista)value;		
		this.panelDeAcoes.setMotorista(motorista);				
		if (isSelected) {
			this.panelDeAcoes.setBackground(table.getSelectionBackground());
		}else{
			this.panelDeAcoes.setBackground(table.getBackground());
		}
		return panelDeAcoes;
	}
	
	@Override
	public Object getCellEditorValue() {
		return null;
	}	
	
}