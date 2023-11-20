package br.com.senai.saep.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.senai.saep.entity.Entrega;
import br.com.senai.saep.entity.Motorista;
import br.com.senai.saep.service.MotoristaService;
import br.com.senai.saep.view.componentes.table.EntregaTableModel;

@Component
public class ViewListagemEntregas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JComboBox<Motorista> cbMotoristas;
	private JTable tableEntrega;
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private MotoristaService motoristaService;
	
	@Autowired
	private ViewCadastroDeEntregas viewCadastroEntregas;
	
	public void carregarComboMotorista() {
		Integer idTransportadora = sessionManager.getIdTransportadoraLogada();
		List<Motorista> categorias = motoristaService.buscarPorTransportadora(idTransportadora);
		for (Motorista ca : categorias) {
			cbMotoristas.addItem(ca);
		}
	}

    public ViewListagemEntregas() {
		EntregaTableModel model = new EntregaTableModel(new ArrayList<Entrega>());
	    this.tableEntrega = new JTable(model);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewCadastroEntregas.setVisible(true);
			}
		});
		btnAdicionar.setBounds(301, 12, 117, 25);
		contentPane.add(btnAdicionar);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setBounds(301, 65, 117, 25);
		contentPane.add(btnPesquisar);
		
		cbMotoristas = new JComboBox<Motorista>();
		cbMotoristas.setBounds(25, 61, 267, 32);
		contentPane.add(cbMotoristas);
		
		JLabel lblEntregador = new JLabel("Entregador");
		lblEntregador.setBounds(25, 38, 158, 15);
		contentPane.add(lblEntregador);
		
		JButton btnVisualizar = new JButton("Visualizar");
		btnVisualizar.setBounds(37, 214, 117, 25);
		contentPane.add(btnVisualizar);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(166, 214, 117, 25);
		contentPane.add(btnCadastrar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(295, 214, 117, 25);
		contentPane.add(btnExcluir);
		
		JScrollPane scrollPane = new JScrollPane(tableEntrega);
		scrollPane.setBounds(27, 112, 391, 90);
		contentPane.add(scrollPane);
		
		this.carregarComboMotorista();
	}
}
