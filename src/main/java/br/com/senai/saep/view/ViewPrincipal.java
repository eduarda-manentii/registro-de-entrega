package br.com.senai.saep.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.com.senai.saep.entity.Entrega;
import br.com.senai.saep.entity.Motorista;
import br.com.senai.saep.entity.Transportadora;
import br.com.senai.saep.service.EntregaService;
import br.com.senai.saep.service.MotoristaService;
import br.com.senai.saep.view.table.EntregaTableModel;
import br.com.senai.saep.view.table.MotoristaTableModel;
import br.com.senai.saep.view.table.PanelDeAcoesDoMotoristaCell;
import jakarta.annotation.PostConstruct;

@Component
public class ViewPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private JLabel lblNomeTransportadora;
	
	private Transportadora transportadoraLogada;
	
	private JTable tbMotoristas;
	
	private JTable tbEntregas;
	
	private JScrollPane scrollPaneMotoristas;
	
	private JScrollPane scrollPaneEntregas;
		
	@Autowired
	private ViewLogin viewLogin;
	
	@Lazy
	@Autowired
	private ViewCadastroMotorista viewCadastroMotorista;
	
	@Lazy
	@Autowired
	private ViewCadastroEntrega viewCadastroEntrega;
	
	@Autowired
	private MotoristaService motoristaService;
	
	@Autowired
	private EntregaService entregaService;
	
	@PostConstruct
	private void inicializar() {
		this.atualizarListagens();
	}
	
	public void atualizarListagens() {
		
		List<Motorista> motoristas = motoristaService.listarTodos();		
		MotoristaTableModel mm = new MotoristaTableModel(motoristas);
		tbMotoristas.setModel(mm);
		this.configurarTabelaDeMotoristas();
		
		List<Entrega> entregas = entregaService.listarTodas();
		EntregaTableModel em = new EntregaTableModel(entregas);
		tbEntregas.setModel(em);
		this.configurarTabelaDeEntregas();
		
	}
	
	public void apresentarTela(Transportadora transportadora) {
		this.transportadoraLogada = transportadora;
		this.lblNomeTransportadora.setText(transportadora.getNome());
		this.setVisible(true);
	}
	
	private void configurarColuna(JTable tabela, int indice, int largura) {
		tabela.getColumnModel().getColumn(indice).setResizable(true);
		tabela.getColumnModel().getColumn(indice).setPreferredWidth(largura);
	}
	
	private void configurarTabelaDeEntregas() {
		this.tbEntregas.getTableHeader().setReorderingAllowed(false);
		this.tbEntregas.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		final int COLUNA_ID = 0;
		final int COLUNA_DESCRICAO = 1;
		final int COLUNA_NOME = 2;
		this.configurarColuna(tbEntregas, COLUNA_ID, 50);
		this.configurarColuna(tbEntregas, COLUNA_DESCRICAO, 500);
		this.configurarColuna(tbEntregas, COLUNA_NOME, 220);
	}
	
	private void configurarTabelaDeMotoristas() {
		this.tbMotoristas.getTableHeader().setReorderingAllowed(false);
		this.tbMotoristas.setDefaultRenderer(Motorista.class, new PanelDeAcoesDoMotoristaCell());
		this.tbMotoristas.setDefaultEditor(Motorista.class, new PanelDeAcoesDoMotoristaCell());
		this.tbMotoristas.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.tbMotoristas.setRowHeight(40);
		final int COLUNA_ID = 0;
		final int COLUNA_NOME = 1;
		final int COLUNA_CNH = 2;
		final int COLUNA_ACOES = 3;
		this.configurarColuna(tbMotoristas, COLUNA_ID, 50);
		this.configurarColuna(tbMotoristas, COLUNA_NOME, 400);
		this.configurarColuna(tbMotoristas, COLUNA_CNH, 100);
		this.configurarColuna(tbMotoristas, COLUNA_ACOES, 240);
	}

	public ViewPrincipal() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 816, 570);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 11, 780, 50);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblNomeTransportadora = new JLabel("Nome");
		lblNomeTransportadora.setBounds(10, 11, 438, 32);
		panel.add(lblNomeTransportadora);
		lblNomeTransportadora.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewLogin.setVisible(true);
				dispose();
			}
		});
		btnLogout.setBounds(681, 11, 89, 28);
		panel.add(btnLogout);
		
		tbMotoristas = new JTable(new MotoristaTableModel());
		scrollPaneMotoristas = new JScrollPane(tbMotoristas);
		scrollPaneMotoristas.setBounds(10, 98, 780, 154);
		contentPane.add(scrollPaneMotoristas);
		
		JButton btnCadastrarMotorista = new JButton("Cadastrar Motorista");
		btnCadastrarMotorista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewCadastroMotorista.apresentarTela(transportadoraLogada);
			}
		});
		btnCadastrarMotorista.setBounds(10, 264, 148, 26);
		contentPane.add(btnCadastrarMotorista);
		
		JLabel lblNewLabel = new JLabel("Motoristas");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel.setBounds(10, 79, 171, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblEntregas = new JLabel("Entregas");
		lblEntregas.setFont(new Font("Dialog", Font.BOLD, 15));
		lblEntregas.setBounds(10, 302, 171, 16);
		contentPane.add(lblEntregas);
		
		tbEntregas = new JTable(new EntregaTableModel());
		scrollPaneEntregas = new JScrollPane(tbEntregas);
		scrollPaneEntregas.setBounds(10, 321, 780, 154);
		contentPane.add(scrollPaneEntregas);
		
		JButton btnCadastrarEntrega = new JButton("Cadastrar Entrega");
		btnCadastrarEntrega.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tbMotoristas.getSelectedRow();
				if (linhaSelecionada >= 0) {
					MotoristaTableModel mm = (MotoristaTableModel)tbMotoristas.getModel();
					Motorista motoristaSelecionado = mm.getPor(linhaSelecionada);
					viewCadastroEntrega.apresentarTela(motoristaSelecionado);
				}else {
					JOptionPane.showMessageDialog(contentPane, "Selecione um motorista para o cadastro");
				}
			}
		});
		btnCadastrarEntrega.setBounds(10, 487, 148, 26);
		contentPane.add(btnCadastrarEntrega);
		setLocationRelativeTo(null);
	}
}
