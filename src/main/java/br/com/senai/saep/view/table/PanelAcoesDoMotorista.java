package br.com.senai.saep.view.table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.com.senai.saep.config.SpringContext;
import br.com.senai.saep.entity.Motorista;
import br.com.senai.saep.service.MotoristaService;
import br.com.senai.saep.view.ViewPrincipal;
import lombok.Setter;

public class PanelAcoesDoMotorista extends JPanel{

	private static final long serialVersionUID = 1L;
	
	@Setter
	private Motorista motorista;
	
	public PanelAcoesDoMotorista() {
		
		setLayout(null);
		
		JButton btnVisualizar = new JButton("Visualizar");
		btnVisualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "A funcionalidade não foi especificada");
			}
		});
		btnVisualizar.setBounds(10, 9, 105, 23);
		add(btnVisualizar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int opcaoSelecionada = JOptionPane.showConfirmDialog(null, 
							"Deseja remover o motorista?", "Exclusão", JOptionPane.YES_NO_OPTION);
					if (opcaoSelecionada == 0) {
						MotoristaService service = SpringContext.getBean(MotoristaService.class);
						service.excluir(motorista);
						ViewPrincipal viewPrincipal = SpringContext.getBean(ViewPrincipal.class);
						viewPrincipal.atualizarListagens();
						JOptionPane.showMessageDialog(null, "Motorista removido com sucesso");
					}
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				
			}
		});
		btnExcluir.setBounds(125, 9, 105, 23);
		add(btnExcluir);	
		
	}

}
