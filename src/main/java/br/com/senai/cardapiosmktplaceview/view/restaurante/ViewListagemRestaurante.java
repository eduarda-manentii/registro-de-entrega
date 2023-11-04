package br.com.senai.cardapiosmktplaceview.view.restaurante;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.com.senai.cardapiosmktplaceview.client.RestauranteClient;
import br.com.senai.cardapiosmktplaceview.dto.Categoria;
import br.com.senai.cardapiosmktplaceview.dto.Paginacao;
import br.com.senai.cardapiosmktplaceview.dto.Restaurante;
import br.com.senai.cardapiosmktplaceview.enums.Status;
import br.com.senai.cardapiosmktplaceview.enums.TipoDeCategoria;
import br.com.senai.cardapiosmktplaceview.view.componentes.table.RestauranteTableModel;
import br.com.senai.cardapiosmktplaceview.view.selecao.categoria.IReceptorDeCategoriaSelecionada;
import br.com.senai.cardapiosmktplaceview.view.selecao.categoria.ViewSelecaoCategoria;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;

@Component
public class ViewListagemRestaurante extends JFrame implements IReceptorDeCategoriaSelecionada {

	private static final long serialVersionUID = 1L;
	
	private final int PRIMEIRA_PAGINA = 0;
	
	private JPanel contentPane;
	
	private String tokenDeAcesso;
	
	private JTable tbRestaurantes;	
	
	private JTextField edtNome;
	
	private JTextField edtCategoria;
	
	private JButton btnStatus;
	
	private Categoria categoriaSelecionada;
	
	private IReceptorDeCategoriaSelecionada receptor;
	
	private Paginacao<Restaurante> paginacao;
	
	private int paginaAtual;	
	
	@Lazy
	@Autowired
	private ViewSelecaoCategoria viewSelecao;
	
	@Autowired
	private RestauranteClient restauranteClient;
	
	@Lazy
	@Autowired
	private ViewCadastroRestaurante viewCadastro;
	
	@PostConstruct
	private void inicializar() {	
		this.configurarTabela();
		this.receptor = this;
	}

	@Override
	public void usar(Categoria categoriaSelecionada) {	
		this.categoriaSelecionada = categoriaSelecionada;
		this.edtCategoria.setText(categoriaSelecionada.getId() + " - " + categoriaSelecionada.getNome());
	}
	
	public void mostrarTela(
			@NotBlank(message = "O token de acesso é obrigatório")
			String tokenDeAcesso) {
		this.setVisible(true);
		this.tokenDeAcesso = tokenDeAcesso;
		this.restauranteClient.setTokenDeAcesso(tokenDeAcesso);
	}
	
	private void configurarColuna(int indice, int largura) {
		this.tbRestaurantes.getColumnModel().getColumn(indice).setResizable(true);
		this.tbRestaurantes.getColumnModel().getColumn(indice).setPreferredWidth(largura);
	}
	
	private void configurarTabela() {
		final int COLUNA_ID = 0;
		final int COLUNA_NOME = 1;
		final int COLUNA_STATUS = 2;
		this.tbRestaurantes.getTableHeader().setReorderingAllowed(false);
		this.tbRestaurantes.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.configurarColuna(COLUNA_ID, 50);
		this.configurarColuna(COLUNA_NOME, 520);
		this.configurarColuna(COLUNA_STATUS, 80);
	}
	
	private void listarRestaurantesDa(int pagina) {
		try {
			paginacao = restauranteClient.listarPor(edtNome.getText(), categoriaSelecionada, pagina);			
			RestauranteTableModel model = new RestauranteTableModel(paginacao.getListagem());
			tbRestaurantes.setModel(model);			
			configurarTabela();			
		}catch (Exception ex) {
			JOptionPane.showMessageDialog(contentPane, ex.getMessage(), 
					"Falha na Listagem", JOptionPane.ERROR_MESSAGE);
		}

	}
	
	public ViewListagemRestaurante() {
		setTitle("Gerenciar Restaurantes - Listagem");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 694, 581);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNovo = new JButton("Novo");
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewCadastro.colocarEmModoDeInsercao(tokenDeAcesso);
				dispose();
			}
		});
		btnNovo.setBounds(570, 11, 98, 26);
		contentPane.add(btnNovo);
		
		JPanel pnlFiltro = new JPanel();
		pnlFiltro.setLayout(null);
		pnlFiltro.setBorder(new TitledBorder(null, "Filtros",
						TitledBorder.LEADING, TitledBorder.TOP, 
						null, null));
		pnlFiltro.setBounds(10, 48, 660, 89);
		contentPane.add(pnlFiltro);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(12, 22, 55, 16);
		pnlFiltro.add(lblNome);
		
		edtNome = new JTextField();
		edtNome.setColumns(10);
		edtNome.setBounds(12, 42, 254, 25);
		pnlFiltro.add(edtNome);
		
		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listarRestaurantesDa(PRIMEIRA_PAGINA);
			}
		});
		btnListar.setBounds(550, 41, 98, 26);
		pnlFiltro.add(btnListar);
		
		edtCategoria = new JTextField();
		edtCategoria.setEditable(false);
		edtCategoria.setColumns(10);
		edtCategoria.setBounds(278, 42, 228, 25);
		pnlFiltro.add(edtCategoria);
		
		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setBounds(278, 22, 55, 16);
		pnlFiltro.add(lblCategoria);
		
		JButton btnSelecionar = new JButton("...");
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewSelecao.apresentarSelecao(receptor, TipoDeCategoria.RESTAURANTE, tokenDeAcesso);
			}
		});
		btnSelecionar.setBounds(507, 41, 31, 26);
		pnlFiltro.add(btnSelecionar);
		
		JPanel pnlRegistros = new JPanel();
		pnlRegistros.setLayout(null);
		pnlRegistros.setBorder(new TitledBorder(null, "Registros Encontrados", 
						TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlRegistros.setBounds(10, 149, 660, 306);
		contentPane.add(pnlRegistros);
		
		JButton btnPrimeiro = new JButton("<<");
		btnPrimeiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual = PRIMEIRA_PAGINA;
				listarRestaurantesDa(PRIMEIRA_PAGINA);
			}
		});
		btnPrimeiro.setBounds(191, 268, 61, 26);
		pnlRegistros.add(btnPrimeiro);
		
		JButton btnAnterior = new JButton("<");
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (paginaAtual > 0) {
					paginaAtual--;
					listarRestaurantesDa(paginaAtual);
				}
			}
		});
		btnAnterior.setBounds(264, 268, 61, 26);
		pnlRegistros.add(btnAnterior);
		
		JButton btnProximo = new JButton(">");
		btnProximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (paginaAtual < paginacao.getTotalDePaginas()) {
					paginaAtual++;
					listarRestaurantesDa(paginaAtual);
				}
			}
		});
		btnProximo.setBounds(337, 268, 61, 26);
		pnlRegistros.add(btnProximo);
		
		JButton btnUltimo = new JButton(">>");
		btnUltimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual = paginacao.getTotalDePaginas();
				listarRestaurantesDa(paginaAtual);
			}
		});
		btnUltimo.setBounds(410, 268, 61, 26);
		pnlRegistros.add(btnUltimo);
		
		tbRestaurantes = new JTable(new RestauranteTableModel());
		tbRestaurantes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int linhaSelecionada = tbRestaurantes.getSelectedRow();
				if (linhaSelecionada >= 0) {
					RestauranteTableModel model = (RestauranteTableModel)tbRestaurantes.getModel();
					Restaurante restauranteSelecionado = model.getPor(linhaSelecionada);
					btnStatus.setText(restauranteSelecionado.isAtivo() ? "Inativar" : "Ativar");
				}
			}
		});
		JScrollPane scrollPane = new JScrollPane(tbRestaurantes);
		scrollPane.setBounds(12, 25, 636, 231);
		pnlRegistros.add(scrollPane);
		
		JPanel pnlAcoes = new JPanel();
		pnlAcoes.setLayout(null);
		pnlAcoes.setBorder(new TitledBorder(null, "A\u00E7\u00F5es do Registro Selecionado",
						TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlAcoes.setBounds(308, 467, 358, 67);
		contentPane.add(pnlAcoes);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tbRestaurantes.getSelectedRow();
				if (linhaSelecionada >= 0) {
					RestauranteTableModel model = (RestauranteTableModel)tbRestaurantes.getModel();
					Restaurante restauranteSelecionado = model.getPor(linhaSelecionada);
					viewCadastro.colocarEmModoDeEdicao(tokenDeAcesso, restauranteSelecionado);
					dispose();
				}else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma categoria para edição");
				}
			}
		});
		btnEditar.setBounds(21, 28, 98, 26);
		pnlAcoes.add(btnEditar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tbRestaurantes.getSelectedRow();
				if (linhaSelecionada >= 0) {
					int opcao = JOptionPane.showConfirmDialog(contentPane, 
							"Deseja remover o restaurante selecionada?", 
							"Remoção", JOptionPane.YES_NO_OPTION);
					boolean isConfirmacaoRealizada = opcao == 0;
					if (isConfirmacaoRealizada) {
						RestauranteTableModel model = (RestauranteTableModel)tbRestaurantes.getModel();
						Restaurante restauranteSelecionado = model.getPor(linhaSelecionada);
						try {
							restauranteClient.removerPor(restauranteSelecionado.getId());
							listarRestaurantesDa(paginaAtual);
							JOptionPane.showMessageDialog(contentPane, "Restaurante removido com sucesso", 
									"Sucesso na Remoção", JOptionPane.INFORMATION_MESSAGE);
						}catch (Exception ex) {
							JOptionPane.showMessageDialog(contentPane, ex.getMessage(),
									"Erro na Remoção", JOptionPane.ERROR_MESSAGE);
						}						
					}
				}else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma linha para remoção");
				}
			}
		});
		btnExcluir.setBounds(131, 28, 98, 26);
		pnlAcoes.add(btnExcluir);
		
		btnStatus = new JButton("Inativar");
		btnStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int linhaSelecionada = tbRestaurantes.getSelectedRow();
				
				if (linhaSelecionada >= 0) {

					int opcao = JOptionPane.showConfirmDialog(contentPane, 
							"Deseja atualizar o status do restaurante selecionado?", 
							"Atualização", JOptionPane.YES_NO_OPTION);

					boolean isConfirmacaoRealizada = opcao == 0;

					if (isConfirmacaoRealizada) {
						
						try {
							RestauranteTableModel model = (RestauranteTableModel)tbRestaurantes.getModel();
							Restaurante restauranteSelecionado = model.getPor(linhaSelecionada);
							
							Status novoStatus = restauranteSelecionado.isAtivo() ? Status.I : Status.A;
							restauranteClient.atualizarPor(restauranteSelecionado.getId(), novoStatus);
							listarRestaurantesDa(paginaAtual);
							btnStatus.setText(novoStatus == Status.A ? "Inativar" : "Ativar");
							JOptionPane.showMessageDialog(contentPane, "O status do restaurante foi atualizado com sucesso", 
									"Sucesso na Remoção", JOptionPane.INFORMATION_MESSAGE);
						}catch (Exception ex) {
							JOptionPane.showMessageDialog(contentPane, ex.getMessage(),
									"Erro na Atualização", JOptionPane.ERROR_MESSAGE);
						}
						
					}

				}else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma linha para remoção");
				}

			}
		});
		btnStatus.setBounds(241, 28, 98, 26);
		pnlAcoes.add(btnStatus);
		this.setLocationRelativeTo(null);
	}
}
