package br.com.senai.saep.view;

import java.io.Serializable;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.senai.saep.entity.Motorista;
import br.com.senai.saep.service.MotoristaService;

@Component
public class ViewCadastroDeEntregas extends JFrame implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	private JTextArea taDescricao;
	JComboBox<Motorista> cbMotoristas;
	private JPanel contentPane;
	
	@Autowired
	private MotoristaService motoristaService;
	
	@Autowired
 	private final SessionManager sessionManager;
 	
	public void carregarComboMotorista() {
		Integer idTransportadora = sessionManager.getIdTransportadoraLogada();
		List<Motorista> categorias = motoristaService.buscarPorTransportadora(idTransportadora);
		for (Motorista ca : categorias) {
			cbMotoristas.addItem(ca);
		}
	}
	
	public ViewCadastroDeEntregas(SessionManager sessionManager, MotoristaService motoristaService) {
        this.sessionManager = sessionManager;
        this.motoristaService = motoristaService;
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		cbMotoristas = new JComboBox<Motorista>();
		cbMotoristas.setBounds(36, 62, 333, 32);
		contentPane.add(cbMotoristas);
		
		JLabel lblEntregador = new JLabel("Entregador");
		lblEntregador.setBounds(36, 40, 129, 15);
		contentPane.add(lblEntregador);
		
		taDescricao = new JTextArea();
		taDescricao.setBounds(36, 133, 333, 81);
		taDescricao.setColumns(10);
		contentPane.add(taDescricao);
		
		JLabel lblDescricao = new JLabel("Descricao");
		lblDescricao.setBounds(36, 116, 70, 15);
		contentPane.add(lblDescricao);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.setBounds(306, 25, 117, 25);
		contentPane.add(btnConsultar);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(149, 226, 117, 25);
		contentPane.add(btnSalvar);
		
		this.carregarComboMotorista();
	}

}
