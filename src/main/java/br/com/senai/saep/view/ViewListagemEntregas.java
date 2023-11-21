package br.com.senai.saep.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.senai.saep.entity.Entrega;
import br.com.senai.saep.entity.Motorista;
import br.com.senai.saep.service.EntregaService;
import br.com.senai.saep.service.MotoristaService;
import br.com.senai.saep.view.componentes.table.EntregaTableModel;

@Component
public class ViewListagemEntregas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JComboBox<Motorista> cbMotoristas;
	private JTable tableEntrega;
	
	private int idDaTransportadora;
	
	@Autowired
	private MotoristaService motoristaService;
	
	@Autowired @Lazy
	private ViewCadastroDeEntregas viewCadastroEntregas;
	
	@Autowired
	private EntregaService service;
	
	public void carregarComboMotorista() {
		List<Motorista> categorias = motoristaService.buscarPorTransportadora(idDaTransportadora);
		for (Motorista ca : categorias) {
			cbMotoristas.addItem(ca);
		}
	}

    public ViewListagemEntregas() {
		EntregaTableModel model = new EntregaTableModel(new ArrayList<Entrega>());
	    this.tableEntrega = new JTable(model);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnPesquisar = new JButton("Listar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnPesquisar.setBounds(301, 65, 117, 25);
		contentPane.add(btnPesquisar);
		
		cbMotoristas = new JComboBox<Motorista>();
		cbMotoristas.setBounds(25, 61, 267, 32);
		contentPane.add(cbMotoristas);
		
		JLabel lblEntregador = new JLabel("Entregador");
		lblEntregador.setBounds(25, 38, 158, 15);
		contentPane.add(lblEntregador);
		
		JButton btnVisualizar = new JButton("Visualizar");
		btnVisualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		btnVisualizar.setBounds(37, 214, 117, 25);
		contentPane.add(btnVisualizar);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewCadastroEntregas.mostrarTela(idDaTransportadora);
			}
		});
		btnCadastrar.setBounds(166, 214, 117, 25);
		contentPane.add(btnCadastrar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tableEntrega.getSelectedRow();
				EntregaTableModel model = (EntregaTableModel) tableEntrega.getModel();
				if(linhaSelecionada >= 0 && !model.isVazio()) {
					int opcao = JOptionPane.showConfirmDialog(contentPane, "Deseja excluir a entrega?",
							"Remoção", JOptionPane.YES_NO_OPTION);
					if(opcao == 0) {
						Entrega entregaSelecionada = model.getPor(linhaSelecionada);
						try {
							service.excluirPor(idDaTransportadora, entregaSelecionada.getId());
							model.removePor(linhaSelecionada);
							JOptionPane.showMessageDialog(contentPane, "Entrega excluida.");
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(contentPane, ex.getMessage());
						}
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma linha para remoção.");
				}
				
				
			}
		});
		btnExcluir.setBounds(295, 214, 117, 25);
		contentPane.add(btnExcluir);
		
		JScrollPane scrollPane = new JScrollPane(tableEntrega);
		scrollPane.setBounds(27, 112, 391, 90);
		contentPane.add(scrollPane);
		
		this.carregarComboMotorista();
	}
    
    public void mostrarTela(int idDaTransportadora) {
		this.setVisible(true);
		this.idDaTransportadora = idDaTransportadora;
	}
}
