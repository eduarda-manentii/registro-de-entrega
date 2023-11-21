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
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.com.senai.saep.entity.Transportadora;
import br.com.senai.saep.service.TransportadoraService;

@Component
public class ViewLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	@Autowired
	private TransportadoraService service;
	private JTextField edtLogin;
	private JPasswordField edtSenha;
	
	@Lazy
	@Autowired
	private ViewPrincipal viewPrincipal;

	public ViewLogin() {
		setTitle("Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 294, 183);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setBounds(10, 11, 46, 14);
		contentPane.add(lblNewLabel);
		
		edtLogin = new JTextField();
		edtLogin.setBounds(10, 28, 258, 20);
		contentPane.add(edtLogin);
		edtLogin.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setBounds(10, 59, 46, 14);
		contentPane.add(lblSenha);
		
		edtSenha = new JPasswordField();
		edtSenha.setBounds(10, 75, 258, 20);
		contentPane.add(edtSenha);
		
		JButton btnLogar = new JButton("Logar");
		btnLogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Transportadora transportadora = service.logarPor(
							edtLogin.getText(), new String(edtSenha.getPassword()));
					edtLogin.setText("");
					edtSenha.setText("");
					viewPrincipal.apresentarTela(transportadora);
					dispose();
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}
			
			}
		});
		btnLogar.setBounds(92, 106, 89, 23);
		contentPane.add(btnLogar);
		setLocationRelativeTo(null);
	}
}	
