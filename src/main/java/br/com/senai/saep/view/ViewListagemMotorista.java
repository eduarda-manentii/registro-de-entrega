package br.com.senai.saep.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.senai.saep.entity.Motorista;
import br.com.senai.saep.view.componentes.table.MotoristaTableModel;

@Component
public class ViewListagemMotorista extends JFrame implements Serializable {

	private static final long serialVersionUID = 1L;
	private JFrame frame;

	private JTable tableMotorista;
	private JPanel contentPane;
	
	@Autowired
	private ViewCadastroDeMotorista viewCadastroMotorista;
	
	@Autowired
	private ViewPrincipal viewPrincipal;
	
	public ViewListagemMotorista() {
		MotoristaTableModel model = new MotoristaTableModel(new ArrayList<Motorista>());
		this.tableMotorista = new JTable(model);
		frame = new JFrame();
		setBounds(100, 100, 500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCadastrarMotorista = new JButton("Cadastrar Motorista");
		btnCadastrarMotorista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewCadastroMotorista.setVisible(true);
				dispose();
			}
		});
		btnCadastrarMotorista.setBounds(75, 206, 165, 32);
		contentPane.add(btnCadastrarMotorista);
		
		JScrollPane scrollPane = new JScrollPane(tableMotorista);
		scrollPane.setBounds(20, 47, 465, 136);
		contentPane.add(scrollPane);
		
		JButton btnTelaPrincipal = new JButton("Tela Principal");
		btnTelaPrincipal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewPrincipal.setVisible(true);
			}
		});
		btnTelaPrincipal.setBounds(249, 206, 165, 32);
		contentPane.add(btnTelaPrincipal);
		
		
		this.setLocationRelativeTo(null);
	}
}
