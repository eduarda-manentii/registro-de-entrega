package br.com.senai.cardapiosmktplaceview.view;

import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.senai.cardapiosmktplaceview.view.categoria.ViewListagemCategoria;
import br.com.senai.cardapiosmktplaceview.view.opcao.ViewListagemOpcao;
import br.com.senai.cardapiosmktplaceview.view.restaurante.ViewListagemRestaurante;
import jakarta.validation.constraints.NotBlank;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@Component
public class ViewPrincipal extends JFrame implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private String tokenDeAcesso;
	
	@Autowired
	private ViewListagemCategoria viewListagemCategoria;
	
	@Autowired
	private ViewListagemRestaurante viewListagemRestaurante;
	
	@Autowired
	private ViewListagemOpcao viewListagemOpcao;

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
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Categorias");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewListagemCategoria.mostrarTela(tokenDeAcesso);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem miRestaurante = new JMenuItem("Restaurantes");
		miRestaurante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewListagemRestaurante.mostrarTela(tokenDeAcesso);				
			}
		});
		mnNewMenu.add(miRestaurante);
		
		JMenuItem miOpcao = new JMenuItem("Opções");
		miOpcao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewListagemOpcao.mostrarTela(tokenDeAcesso);				
			}
		});
		mnNewMenu.add(miOpcao);
		
		JMenu mnNewMenu_1 = new JMenu("Gestão");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Cardápios");
		mnNewMenu_1.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu_2 = new JMenu("Sistema");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Sair");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_2);
		this.setLocationRelativeTo(null);
	}
	
	public void mostrarTela(
			@NotBlank(message = "O token de acesso é obrigatório")
			String tokenDeAcesso) {
		this.setVisible(true);
		this.tokenDeAcesso = tokenDeAcesso;
	}
}
