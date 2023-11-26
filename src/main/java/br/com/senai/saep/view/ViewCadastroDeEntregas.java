package br.com.senai.saep.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
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

import br.com.senai.saep.entity.Entrega;
import br.com.senai.saep.entity.Motorista;
import br.com.senai.saep.entity.Transportadora;
import br.com.senai.saep.service.EntregaService;
import jakarta.annotation.PostConstruct;


@Component
@Lazy
public class ViewCadastroDeEntregas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtDescricao;
	private JComboBox<Motorista> cbMotoristas;
	private  String nomeTransportadora; 	
		
	@Autowired
	private EntregaService entregaService;
	
	@Autowired
	private ViewLogin viewLogin;	
	
	private List<Motorista> motoristas;
	
	public void pegarTransportadora(Transportadora transportadora, List<Motorista> motoristas) {
		Preconditions.checkNotNull(transportadora, "A transportadora não pode ser nula");
		this.nomeTransportadora = transportadora.getNome().toUpperCase();
		this.motoristas = motoristas;
		this.carregarCombo();
		setTitle(nomeTransportadora);	
		this.setVisible(true);
	}	
	
	@PostConstruct
	public void carregarCombo() {
		
		if (motoristas != null) {
			
			for (Motorista motorista : motoristas) {
				cbMotoristas.addItem(motorista);
			}
		}
	}
	
	public void limparCombo() {
	    cbMotoristas.removeAllItems();
	}
	
	public ViewCadastroDeEntregas() {
		setResizable(false);		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel txtMotoristas = new JLabel("Motoristas");
		txtMotoristas.setBounds(10, 62, 68, 14);
		contentPane.add(txtMotoristas);
		
		cbMotoristas = new JComboBox<Motorista>();

		cbMotoristas.setBounds(78, 58, 277, 22);
		contentPane.add(cbMotoristas);
		
		edtDescricao = new JTextField();
		edtDescricao.setBounds(78, 132, 277, 20);
		contentPane.add(edtDescricao);
		edtDescricao.setColumns(10);
		
		JLabel txtDescricao = new JLabel("Descrição");
		txtDescricao.setBounds(10, 135, 59, 14);
		contentPane.add(txtDescricao);
		
		JButton btnInserir = new JButton("Inserir");
		btnInserir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					Motorista motorista = (Motorista) cbMotoristas.getSelectedItem();
					String descricao = edtDescricao.getText();
					
					Entrega entrega = new Entrega();
					
					if (entrega != null) {
						entrega.setDescricao(descricao);
						entrega.setMotorista(motorista);
						
						entregaService.salvar(entrega);
						
						JOptionPane.showInternalMessageDialog(null, "Entrega salva com sucesso!");
						edtDescricao.setText("");
						
					}					
					
				} catch (Exception e2) {
					JOptionPane.showInternalMessageDialog(null, "Erro ao tentar salvar a Entrega");
				}
				
			}
		});
		btnInserir.setBounds(172, 170, 89, 23);
		contentPane.add(btnInserir);
		
		JButton btnSair = new JButton("Logout");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewLogin.setVisible(true);
				limparCombo();
				dispose();
			}
		});
		btnSair.setBounds(345, 0, 89, 23);
		contentPane.add(btnSair);
		
		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnListar.setBounds(335, 227, 89, 23);
		contentPane.add(btnListar);
	}
	
}
