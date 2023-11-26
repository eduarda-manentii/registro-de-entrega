package br.com.senai.saep.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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
@Lazy
public class ViewListagemEntregas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private  String nomeTransportadora; 	
	
	@Autowired @Lazy
	private ViewCadastroDeEntregas viewCadastroEntregas;
		
	@Autowired
	private Transportadora transportadora;
	
	@Autowired
	private ViewLogin viewLogin;
	
	@Autowired
    private EntregaService entregaService;
	
	private List<Motorista> motoristas;
	
	private JTable tableEntrega;
	private EntregaTableModel entregaTableModel;
	private JTextField edtIdMotorista;
	
	public void pegarTransportadora(Transportadora transportadora, List<Motorista> motoristas) {
		Preconditions.checkNotNull(transportadora, "A transportadora não pode ser nula");
		this.nomeTransportadora = transportadora.getNome().toUpperCase();
		this.transportadora = transportadora;
		this.motoristas = motoristas;
		setTitle(nomeTransportadora);
		this.setVisible(true);
	}
	
	public ViewListagemEntregas() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		entregaTableModel = new EntregaTableModel();
		tableEntrega = new JTable(entregaTableModel);
		JScrollPane scrollPane = new JScrollPane(tableEntrega);
		scrollPane.setBounds(10, 43, 330, 165);
		contentPane.add(scrollPane);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		System.out.println("os motoristas chegaram aqui? " + motoristas);
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
		btnSair.setBounds(345, 0, 89, 23);
		contentPane.add(btnSair);		
		
		JLabel lblNewLabel = new JLabel("Id motorista: ");
		lblNewLabel.setBounds(10, 18, 76, 14);
		contentPane.add(lblNewLabel);
		
		edtIdMotorista = new JTextField();
		edtIdMotorista.setBounds(96, 15, 86, 20);
		contentPane.add(edtIdMotorista);
		edtIdMotorista.setColumns(10);
		
		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Integer idMotorista = Integer.parseInt(edtIdMotorista.getText());
					atualizarTabela(idMotorista);
				} catch (Exception e2) {
				}				
				
			}
		});
		btnListar.setBounds(192, 14, 89, 23);
		contentPane.add(btnListar);
	}
	
	private void atualizarTabela(Integer idMotorista) {
		List<Entrega> entregas = entregaService.listarPor(idMotorista);
		entregaTableModel = new EntregaTableModel(entregas);
        tableEntrega.setModel(entregaTableModel);
    }
	
}
