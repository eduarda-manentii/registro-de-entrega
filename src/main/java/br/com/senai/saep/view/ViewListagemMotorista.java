package br.com.senai.saep.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.com.senai.saep.entity.Motorista;
import br.com.senai.saep.service.MotoristaService;
import br.com.senai.saep.view.componentes.table.MotoristaTableModel;

@Component
public class ViewListagemMotorista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTable tableMotorista;
	private JPanel contentPane;
	private JTextField txtFiltro;
	
	@Autowired @Lazy
	private ViewCadastroDeMotorista viewCadastroMotorista;
	
	@Autowired
	private MotoristaService service;
	
	private int idDaTransportadora;

	public ViewListagemMotorista() {
		MotoristaTableModel model = new MotoristaTableModel(new ArrayList<Motorista>());
	    this.tableMotorista = new JTable(model);
	    frame = new JFrame();
	    setBounds(100, 100, 500, 300);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    List<Motorista> motoristasEncontrados = service.buscarPorTransportadora(idDaTransportadora);
	    tableMotorista.setModel((TableModel) motoristasEncontrados);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnCadastrarMotorista = new JButton("Cadastrar Motorista");
		btnCadastrarMotorista.setBounds(158, 219, 194, 32);
		btnCadastrarMotorista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewCadastroMotorista.mostrarTela(idDaTransportadora);
				dispose();
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnCadastrarMotorista);
		
		JScrollPane scrollPane = new JScrollPane(tableMotorista);
		scrollPane.setBounds(20, 97, 465, 110);
		contentPane.add(scrollPane);
		
		JButton btnPesquisar = new JButton("Listar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  try {
			            String filtro = txtFiltro.getText();
			            List<Motorista> motoristasEncontrados;

			            if (filtro.isEmpty()) {
			                motoristasEncontrados = service.buscarPorTransportadora(idDaTransportadora);
			            } else {
			                motoristasEncontrados = service.listarPorNome(idDaTransportadora, filtro);
			            }
			            MotoristaTableModel model = new MotoristaTableModel(motoristasEncontrados);
			            tableMotorista.setModel(model);

			            if (motoristasEncontrados.isEmpty()) {
			                JOptionPane.showMessageDialog(contentPane, "Não foi encontrado nenhum motorista com esse nome.");
			            }
			        } catch (Exception ex) {
			            JOptionPane.showMessageDialog(contentPane, ex.getMessage());
			        }
				
			}
		});
		btnPesquisar.setBounds(341, 43, 127, 32);
		contentPane.add(btnPesquisar);
		
		txtFiltro = new JTextField();
		txtFiltro.setBounds(20, 43, 299, 32);
		contentPane.add(txtFiltro);
		txtFiltro.setColumns(10);
		
		JLabel lblListar = new JLabel("Listar por nome");
		lblListar.setBounds(20, 29, 188, 15);
		contentPane.add(lblListar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tableMotorista.getSelectedRow();
				MotoristaTableModel model = (MotoristaTableModel) tableMotorista.getModel();
				if(linhaSelecionada >= 0 && !model.isVazio()) {
					int opcao = JOptionPane.showConfirmDialog(contentPane, "Deseja excluir o motorista?",
							"Remoção", JOptionPane.YES_NO_OPTION);
					if(opcao == 0) {
						Motorista motoristaSelecionado = model.getPor(linhaSelecionada);
						try {
							service.excluirPor(idDaTransportadora, motoristaSelecionado.getId());
							model.removePor(linhaSelecionada);
							JOptionPane.showMessageDialog(contentPane, "Motorista excluido.");
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(contentPane, ex.getMessage());
						}
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma linha para remoção.");
				}
				
			}
		});
		btnExcluir.setBounds(359, 219, 109, 32);
		contentPane.add(btnExcluir);
		
		JButton btnVisualizar = new JButton("Visualizar");
		btnVisualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//nao consegui implementar isso, nunca tinha feito e nao pude testar
				//por causa do erro do spring, entao nao sei dizer se funciona
				int linhaSelecionada = tableMotorista.getSelectedRow();
				if(linhaSelecionada >= 0) {
					MotoristaTableModel model = (MotoristaTableModel) tableMotorista.getModel();
					Motorista motoristaSelecionado = model.getPor(linhaSelecionada);
					viewCadastroMotorista.setMotorista(motoristaSelecionado);
					viewCadastroMotorista.setVisible(true);
					dispose();
				} else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma linha para visualização.");
				}
			}
		});
		btnVisualizar.setBounds(46, 219, 109, 32);
		contentPane.add(btnVisualizar);
		
		this.setLocationRelativeTo(null);
	}
	
	public void mostrarTela(int idDaTransportadora) {
		this.setVisible(true);
		this.idDaTransportadora = idDaTransportadora;
	}
	
	
}

