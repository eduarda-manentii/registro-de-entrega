package br.com.senai.saep.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.senai.saep.entity.Motorista;
import br.com.senai.saep.entity.Transportadora;
import br.com.senai.saep.service.MotoristaService;

@Component
public class ViewCadastroMotorista extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField edtNome;
	private JTextField edtCnh;
	
	private Transportadora transportadoraLogada;
	
	@Autowired
	private MotoristaService service;

	@Autowired
	private ViewPrincipal viewPrincipal;
	
	public void apresentarTela(Transportadora transportadoraLogada) {
		this.transportadoraLogada = transportadoraLogada;
		this.setVisible(true);
	}

	public ViewCadastroMotorista() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				viewPrincipal.atualizarListagens();
			}
		});
		setTitle("Cadastro de Motorista");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 137);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nome");
		lblNewLabel.setBounds(10, 11, 46, 14);
		contentPane.add(lblNewLabel);
		
		edtNome = new JTextField();
		edtNome.setBounds(10, 28, 228, 20);
		contentPane.add(edtNome);
		edtNome.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("CNH");
		lblNewLabel_1.setBounds(248, 11, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		edtCnh = new JTextField();
		edtCnh.setBounds(248, 28, 176, 20);
		contentPane.add(edtCnh);
		edtCnh.setColumns(10);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Motorista motorista = new Motorista();
					motorista.setNome(edtNome.getText());
					motorista.setCnh(edtCnh.getText());
					motorista.setTransportadora(transportadoraLogada);
					service.salvar(motorista);
					JOptionPane.showMessageDialog(contentPane, "Motorista salvo com sucesso");
					edtNome.setText("");
					edtCnh.setText("");
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}
			}
		});
		btnCadastrar.setBounds(311, 59, 113, 23);
		contentPane.add(btnCadastrar);
		setLocationRelativeTo(null);
	}

}

