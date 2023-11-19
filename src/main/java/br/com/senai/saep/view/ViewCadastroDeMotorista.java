package br.com.senai.saep.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

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

import br.com.senai.saep.entity.Motorista;
import br.com.senai.saep.service.MotoristaService;

@Component
public class ViewCadastroDeMotorista extends JFrame implements Serializable {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextField txtNome;
	private JTextField txtCnh;
	private JPanel contentPane;
	
	@Autowired
	private MotoristaService service;
	
	@Autowired @Lazy
	private ViewListagemMotorista view;
	
	private JTextField textField;
	
	public ViewCadastroDeMotorista() {
		frame = new JFrame();
		setBounds(100, 100, 400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(32, 56, 46, 14);
		contentPane.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setColumns(10);
		txtNome.setBounds(32, 75, 269, 25);
		contentPane.add(txtNome);
		
		txtCnh = new JTextField();
		txtCnh.setColumns(10);
		txtCnh.setBounds(32, 147, 269, 25);
		contentPane.add(txtCnh);
		
		JLabel lblCnh = new JLabel("CNH");
		lblCnh.setBounds(32, 132, 46, 14);
		contentPane.add(lblCnh);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = txtNome.getText();
				String cnh = txtCnh.getText();
				
				if (nome == null || cnh == null) {
					JOptionPane.showMessageDialog(null, "Todos os campos são obrigatórios.");
				} else {
					Motorista motorista = new Motorista();
					motorista.setNome_completo(nome);
					motorista.setCnh(cnh);
					Motorista motoristaCadastrado = service.salvar(motorista);
					if (motoristaCadastrado != null) {
						JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
					}
				}
			}
		});
		btnSalvar.setBounds(112, 204, 112, 25);
		contentPane.add(btnSalvar);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view.setVisible(true);
				dispose();
			}
		});
		btnConsultar.setBounds(248, 21, 112, 25);
		contentPane.add(btnConsultar);
		
		this.setLocationRelativeTo(null);
	}
}
