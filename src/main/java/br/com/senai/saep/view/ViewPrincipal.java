package br.com.senai.saep.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
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
public class ViewPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;	

private  String nomeTransportadora; 	
	
	@Autowired
	private ViewListagemMotorista viewMotorista;
	
	@Autowired
	private ViewListagemEntregas viewEntregas;
	
	@Autowired
	private Transportadora transportadora;
	
	@Autowired
	private MotoristaService motoristaService;
	
	private List<Motorista> motoristas = new ArrayList<Motorista>();
	
	public void pegarTransportadora(Transportadora transportadora) {
		Preconditions.checkNotNull(transportadora, "A transportadora não pode ser nula");
		this.nomeTransportadora = transportadora.getNome().toUpperCase();
		this.transportadora = transportadora;
		setTitle(nomeTransportadora);
		motoristas = null;
		setVisible(true);
	}

	public ViewPrincipal() {
		setResizable(false);
			
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		System.out.println(this.nomeTransportadora);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Cadastros");
		menuBar.add(mnNewMenu);
		
		JMenuItem btnMotorista = new JMenuItem("Motorista");
		btnMotorista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewMotorista.pegarTransportadora(transportadora);				
				dispose();
			}
		});
		mnNewMenu.add(btnMotorista);
		
		JMenuItem btnEntrega = new JMenuItem("Entrega");
		btnEntrega.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				motoristas = motoristaService.listarPor(transportadora.getId());
				System.out.println(motoristas);
				viewEntregas.pegarTransportadora(transportadora, motoristas);
				dispose();
			}
		});
		mnNewMenu.add(btnEntrega);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
	}
	
}