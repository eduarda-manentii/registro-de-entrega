package br.com.senai.cardapiosmktplaceview.view.selecao.categoria;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.senai.cardapiosmktplaceview.client.CategoriaClient;
import br.com.senai.cardapiosmktplaceview.dto.Categoria;
import br.com.senai.cardapiosmktplaceview.dto.Paginacao;
import br.com.senai.cardapiosmktplaceview.enums.Status;
import br.com.senai.cardapiosmktplaceview.enums.TipoDeCategoria;
import br.com.senai.cardapiosmktplaceview.view.componentes.table.CategoriaTableModel;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Component
public class ViewSelecaoCategoria extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private final int PRIMEIRA_PAGINA = 0;
	
	private JPanel contentPane;
	
	private JTable tbCategorias;
	
	private Paginacao<Categoria> paginacao;
	
	private int paginaAtual;
	
	private IReceptorDeCategoriaSelecionada receptorDaSelecao;
	
	private TipoDeCategoria tipo;
	
	@Autowired
	private CategoriaClient categoriaClient;
	
	private JTextField edtNome;
	
	@PostConstruct
	private void inicializar() {	
		this.configurarTabela();
	}
	
	public void apresentarSelecao(
			@NotNull(message = "O receptor da seleção de categoria não pode ser nulo")
			IReceptorDeCategoriaSelecionada receptorDaSelecao, 
			@NotNull(message = "O tipo de categoria para seleção não pode ser nulo")
			TipoDeCategoria tipo, 
			@NotBlank(message = "O token de acesso é obrigatório")
			String tokenDeAcesso) {		
		this.categoriaClient.setTokenDeAcesso(tokenDeAcesso);
		this.receptorDaSelecao = receptorDaSelecao;
		this.tipo = tipo;
		this.setVisible(true);
	}
	
	private void configurarColuna(int indice, int largura) {
		this.tbCategorias.getColumnModel().getColumn(indice).setResizable(true);
		this.tbCategorias.getColumnModel().getColumn(indice).setPreferredWidth(largura);
	}
	
	private void configurarTabela() {
		final int COLUNA_ID = 0;
		final int COLUNA_NOME = 1;
		this.tbCategorias.getTableHeader().setReorderingAllowed(false);
		this.tbCategorias.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.configurarColuna(COLUNA_ID, 50);
		this.configurarColuna(COLUNA_NOME, 420);
	}
	
	private void listarCategoriaDa(int pagina) {
		try {
			paginacao = categoriaClient.listarPor(edtNome.getText(), Status.A, tipo, pagina);			
			CategoriaTableModel model = new CategoriaTableModel(paginacao.getListagem());
			tbCategorias.setModel(model);			
			configurarTabela();
		}catch (Exception ex) {
			JOptionPane.showMessageDialog(contentPane, ex.getMessage(), 
					"Falha na Listagem", JOptionPane.ERROR_MESSAGE);
		}

	}

	public ViewSelecaoCategoria() {
		setTitle("Seleção de Categoria");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 538, 542);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Filtros", 
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 503, 85);
		contentPane.add(panel);
		panel.setLayout(null);
		
		edtNome = new JTextField();
		edtNome.setColumns(10);
		edtNome.setBounds(12, 40, 369, 25);
		panel.add(edtNome);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(12, 18, 55, 16);
		panel.add(lblNome);
		
		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				listarCategoriaDa(PRIMEIRA_PAGINA);
			}
		});
		btnListar.setBounds(393, 39, 98, 26);
		panel.add(btnListar);
		
		JPanel pnlRegistros = new JPanel();
		pnlRegistros.setLayout(null);
		pnlRegistros.setBorder(new TitledBorder(null, "Registros Encontrados",
						TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlRegistros.setBounds(10, 108, 503, 306);
		contentPane.add(pnlRegistros);
		
		JButton btnPrimeiro = new JButton("<<");
		btnPrimeiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual = PRIMEIRA_PAGINA;
				listarCategoriaDa(paginaAtual);
			}
		});
		btnPrimeiro.setBounds(111, 268, 61, 26);
		pnlRegistros.add(btnPrimeiro);
		
		JButton btnAnterior = new JButton("<");
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (paginaAtual > 0) {
					paginaAtual--;
					listarCategoriaDa(paginaAtual);
				}
			}
		});
		btnAnterior.setBounds(184, 268, 61, 26);
		pnlRegistros.add(btnAnterior);
		
		JButton btnProximo = new JButton(">");
		btnProximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (paginaAtual < paginacao.getTotalDePaginas()) {
					paginaAtual++;
					listarCategoriaDa(paginaAtual);
				}
			}
		});
		btnProximo.setBounds(257, 268, 61, 26);
		pnlRegistros.add(btnProximo);
		
		JButton btnUltimo = new JButton(">>");
		btnUltimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual = paginacao.getTotalDePaginas();
				listarCategoriaDa(paginaAtual);
			}
		});
		btnUltimo.setBounds(330, 268, 61, 26);
		pnlRegistros.add(btnUltimo);
		
		tbCategorias = new JTable(new CategoriaTableModel());
		JScrollPane scrollPane = new JScrollPane(tbCategorias);
		scrollPane.setBounds(12, 25, 479, 231);
		pnlRegistros.add(scrollPane);
		
		JPanel pnlAcoes = new JPanel();
		pnlAcoes.setLayout(null);
		pnlAcoes.setBorder(new TitledBorder(null, "A\u00E7\u00F5es do Registro Selecionado",
						TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlAcoes.setBounds(288, 426, 225, 67);
		contentPane.add(pnlAcoes);
		
		JButton btnUsar = new JButton("Usar");
		btnUsar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tbCategorias.getSelectedRow();
				if (linhaSelecionada >= 0) {
					CategoriaTableModel model = (CategoriaTableModel)tbCategorias.getModel();
					Categoria categoriaSelecionada = model.getPor(linhaSelecionada);
					receptorDaSelecao.usar(categoriaSelecionada);
					dispose();
				}else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma categoria para seleção");
				}
			}
		});
		btnUsar.setBounds(12, 29, 201, 26);
		pnlAcoes.add(btnUsar);
		this.setLocationRelativeTo(null);
	}
}
