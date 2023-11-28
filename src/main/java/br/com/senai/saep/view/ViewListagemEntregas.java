package br.com.senai.saep.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

import br.com.senai.saep.entity.Entrega;
import br.com.senai.saep.entity.Motorista;
import br.com.senai.saep.entity.Transportadora;
import br.com.senai.saep.service.EntregaService;
import br.com.senai.saep.view.componentes.table.EntregaTableModel;

@Component
public class ViewListagemEntregas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableEntrega;
	private List<Motorista> motoristas;
	private EntregaTableModel entregaTableModel;
	private  String nomeTransportadora; 
	
	@Autowired 
	@Lazy
	private ViewCadastroDeEntregas viewCadastroEntregas;
	
	@Autowired
	@Lazy
	private ViewLogin viewLogin;
		
	@Autowired
	private Transportadora transportadora;
	
	@Autowired
    private EntregaService entregaService;
	
	public void pegarTransportadora(Transportadora transportadora, List<Motorista> motoristas) {
	    Preconditions.checkNotNull(transportadora, "A transportadora não pode ser nula");
	    this.nomeTransportadora = transportadora.getNome().toUpperCase();
	    this.motoristas = motoristas;
	    this.transportadora = transportadora;
	    setTitle(nomeTransportadora);
	    setVisible(true);
	    atualizarTabela();
	}

	public ViewListagemEntregas() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 506, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		entregaTableModel = new EntregaTableModel();
		tableEntrega = new JTable(entregaTableModel);
		JScrollPane scrollPane = new JScrollPane(tableEntrega);
		scrollPane.setBounds(10, 43, 470, 165);
		contentPane.add(scrollPane);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JButton btnCadastroEntregas = new JButton("Cadastrar Entregas");
		btnCadastroEntregas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewCadastroEntregas.pegarTransportadora(transportadora, motoristas);
				dispose();
			}
		});
		btnCadastroEntregas.setBounds(10, 227, 168, 23);
		contentPane.add(btnCadastroEntregas);
		
		JButton btnSair = new JButton("Logout");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewLogin.setVisible(true);
				dispose();
			}
		});
		btnSair.setBounds(391, 14, 89, 23);
		contentPane.add(btnSair);
		
		JButton btnVisualizar = new JButton("Visualizar");
		btnVisualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "Funcionalidade não específicada.");
			}
		});
		btnVisualizar.setBounds(194, 227, 146, 23);
		contentPane.add(btnVisualizar);
	}
	
	  private void atualizarTabela() {
		  entregaTableModel = new EntregaTableModel(obterDadosFicticios());
	        tableEntrega.setModel(entregaTableModel);
	    }
	  
	  private List<Entrega> obterDadosFicticios() {
		    List<Entrega> entregas = new ArrayList<>();
		    for (Motorista motorista : motoristas) {
		        entregas.addAll(entregaService.listarPor(motorista.getId()));
		    }
		    return entregas;
		}


	   
	
}
