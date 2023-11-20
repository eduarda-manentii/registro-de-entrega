package br.com.senai.saep.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
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
public class ViewListagemMotorista extends JFrame implements Serializable {

	private static final long serialVersionUID = 1L;
	private JFrame frame;

	private JTable tableMotorista;
	private JPanel contentPane;
	
	@Autowired @Lazy
	private ViewCadastroDeMotorista viewCadastroMotorista;
	
	@Autowired
	private MotoristaService service;
	
	@Autowired
	private SessionManager sessionManager;

	private JTextField txtFiltro;
	

	public ViewListagemMotorista() {
		MotoristaTableModel model = new MotoristaTableModel(new ArrayList<Motorista>());
	    this.tableMotorista = new JTable(model);
	    frame = new JFrame();
	    setBounds(100, 100, 500, 300);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    Integer idTransportadora = sessionManager.getIdTransportadoraLogada();
	    List<Motorista> motoristasEncontrados = service.buscarPorTransportadora(idTransportadora);
	    tableMotorista.setModel((TableModel) motoristasEncontrados);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnCadastrarMotorista = new JButton("Cadastrar Motorista");
		btnCadastrarMotorista.setBounds(158, 219, 194, 32);
		btnCadastrarMotorista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewCadastroMotorista.setVisible(true);
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
			                motoristasEncontrados = service.buscarPorTransportadora(sessionManager.getIdTransportadoraLogada());
			            } else {
			                motoristasEncontrados = service.listarPorNome(filtro);
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
		btnExcluir.setBounds(359, 219, 109, 32);
		contentPane.add(btnExcluir);
		
		JButton btnVisualizar = new JButton("Visualizar");
		btnVisualizar.setBounds(46, 219, 109, 32);
		contentPane.add(btnVisualizar);
		
		this.setLocationRelativeTo(null);
	}
}

