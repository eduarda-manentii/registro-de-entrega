package br.com.senai.saep.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.senai.saep.entity.Transportadora;
import br.com.senai.saep.service.TransportadoraService;


@Component
public class ViewLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private JTextField edtLogin;
	
	private JPasswordField edtSenha;
	
	@Autowired
	private ViewPrincipal viewPrincipal;
	
	@Autowired
	private TransportadoraService service;
	
	public ViewLogin() {
		setResizable(false);
		setTitle("Acesso ao Sistema");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 288, 221);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Email");
		lblNewLabel.setBounds(10, 21, 46, 14);
		contentPane.add(lblNewLabel);
		
		edtLogin = new JTextField();
		edtLogin.setHorizontalAlignment(SwingConstants.CENTER);
		edtLogin.setBounds(10, 46, 250, 20);
		contentPane.add(edtLogin);
		edtLogin.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setBounds(10, 77, 55, 16);
		contentPane.add(lblNewLabel_1);
		
		edtSenha = new JPasswordField();
		edtSenha.setHorizontalAlignment(SwingConstants.CENTER);
		edtSenha.setBounds(10, 104, 250, 20);
		contentPane.add(edtSenha);
		
		JButton btnLogin = new JButton("Logar");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String login = edtLogin.getText();
					String senha = new String(edtSenha.getPassword());
					Transportadora transportadoraEncontrada = service.buscarPor(login, senha);
					if (transportadoraEncontrada != null) {
						viewPrincipal.pegarTransportadora(transportadoraEncontrada);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Não existe transportadora"
								+ " com as credenciais informadas.");
					}
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage(), 
							"Falha no Login", JOptionPane.ERROR_MESSAGE);
					limparCampos();
				}
			}
		});
		btnLogin.setBounds(10, 144, 250, 26);
		contentPane.add(btnLogin);
		this.setLocationRelativeTo(null);
	}
	
	private void limparCampos() {
		this.edtLogin.setText("");
		this.edtSenha.setText("");
		this.edtLogin.requestFocus();
	}
}
