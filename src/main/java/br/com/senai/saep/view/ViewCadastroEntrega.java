package br.com.senai.saep.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.senai.saep.entity.Entrega;
import br.com.senai.saep.entity.Motorista;
import br.com.senai.saep.service.EntregaService;

@Component
public class ViewCadastroEntrega extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private Motorista motoristaSelecionado;
	
	@Autowired
	private EntregaService service;
	
	@Autowired
	private ViewPrincipal viewPrincipal;
	
	public void apresentarTela(Motorista motoristaSelecionado) {
		this.motoristaSelecionado = motoristaSelecionado;
		this.setVisible(true);
	}

	public ViewCadastroEntrega() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				viewPrincipal.atualizarListagens();
			}
		});
		setTitle("Cadastro de Entregas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Descrição da Carga");
		lblNewLabel.setBounds(10, 11, 231, 14);
		contentPane.add(lblNewLabel);
		
		JTextArea edtDescricao = new JTextArea();
		edtDescricao.setBorder(new LineBorder(new Color(0, 0, 0)));
		edtDescricao.setBounds(10, 36, 414, 173);
		contentPane.add(edtDescricao);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Entrega entrega = new Entrega();
					entrega.setDescricaoDaCarga(edtDescricao.getText());
					entrega.setMotorista(motoristaSelecionado);
					service.salvar(entrega);
					JOptionPane.showMessageDialog(contentPane, "Entrega salva com sucesso");					
					edtDescricao.setText("");
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}
			}
		});
		btnCadastrar.setBounds(326, 220, 98, 26);
		contentPane.add(btnCadastrar);
		setLocationRelativeTo(null);
	}
}
