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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

import br.com.senai.saep.entity.Motorista;
import br.com.senai.saep.entity.Transportadora;
import br.com.senai.saep.service.MotoristaService;

@Component
@Lazy
public class ViewCadastroDeMotorista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtNome;
	private JTextField edtCnh;	
	
	private Transportadora transportadora;
	
	private String nomeTransportadora;
	
	@Autowired
	private MotoristaService service;
	
	@Autowired
	private ViewLogin viewLogin;
	
	@Autowired
	private ViewListagemMotorista viewCadastro;
	
	@Autowired
	private MotoristaService motoristaService;
	
	public void pegarTransportadora(Transportadora transportadora) {
		Preconditions.checkNotNull(transportadora, "A transportadora não pode ser nula");
		this.nomeTransportadora = transportadora.getNome().toUpperCase();
		this.transportadora = transportadora;
		setTitle(nomeTransportadora);				
	}
	
	public ViewCadastroDeMotorista() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 468, 254);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel txtNome = new JLabel("Nome:");
		txtNome.setBounds(50, 76, 46, 14);
		contentPane.add(txtNome);
		
		edtNome = new JTextField();
		edtNome.setBounds(106, 73, 252, 20);
		contentPane.add(edtNome);
		edtNome.setColumns(10);
		
		JLabel txtCnh = new JLabel("CNH:");
		txtCnh.setBounds(50, 119, 46, 14);
		contentPane.add(txtCnh);
		
		edtCnh = new JTextField();
		edtCnh.setColumns(10);
		edtCnh.setBounds(106, 116, 252, 20);
		contentPane.add(edtCnh);
		
		JButton btnInserir = new JButton("Inserir");
		btnInserir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				try {
					String nome = edtNome.getText();
					String cnh = edtCnh.getText();
					
					Motorista motorista = new Motorista();
					
					if (motorista != null) {
						motorista.setNomeCompleto(nome);
						motorista.setCnh(cnh);
						
						motorista.setTransportadora(transportadora);
												
						motorista = service.salvar(motorista);
						JOptionPane.showInternalMessageDialog(null, "Motorista salvo com sucesso!");
						edtNome.setText("");
						edtCnh.setText("");
					}					
					
				} catch (Exception e2) {
					JOptionPane.showInternalMessageDialog(null, "Erro ao tentar salvar o Motorista");
				}				
				
			}
		});
		btnInserir.setBounds(143, 173, 89, 23);
		contentPane.add(btnInserir);
		
		JLabel txtTitulo = new JLabel("CADASTRO DE MOTORISTA");
		txtTitulo.setBounds(126, 28, 146, 14);
		contentPane.add(txtTitulo);
		
		JButton btnSair = new JButton("Logout");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewLogin.setVisible(true);
				dispose();
			}
		});
		btnSair.setBounds(326, 11, 89, 23);
		contentPane.add(btnSair);
		
		JButton btnVisualizar = new JButton("Visualizar");
		btnVisualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewCadastro.pegarTransportadora(transportadora);
				dispose();			}
		});
		btnVisualizar.setBounds(242, 173, 89, 23);
		contentPane.add(btnVisualizar);
	}
}
