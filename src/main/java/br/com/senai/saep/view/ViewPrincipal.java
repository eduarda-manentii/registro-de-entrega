package br.com.senai.saep.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ViewPrincipal extends JFrame implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private int idDaTransportadora;
	
	@Autowired @Lazy
	private ViewListagemMotorista viewListagemMotorista;
	
	@Autowired @Lazy
	private ViewListagemEntregas viewListagemEntrega;
	
	public ViewPrincipal() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setTitle("Gestor de Cardápios - Meu Senai - v1.0.0");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 784, 22);
		contentPane.add(menuBar);
		
		JMenu mnNewMenu = new JMenu("Cadastros");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Motorista");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewListagemMotorista.mostrarTela(idDaTransportadora);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem miRestaurante = new JMenuItem("Entregas");
		miRestaurante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewListagemEntrega.mostrarTela(idDaTransportadora);
			}
		});
		mnNewMenu.add(miRestaurante);
		
		JMenuItem mnitSair = new JMenuItem("Sair");
		mnitSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		menuBar.add(mnitSair);
		this.setLocationRelativeTo(null);
	}
	
	public void mostrarTela(int idDaTransportadora) {
		this.setVisible(true);
		this.idDaTransportadora = idDaTransportadora;
	}
}
