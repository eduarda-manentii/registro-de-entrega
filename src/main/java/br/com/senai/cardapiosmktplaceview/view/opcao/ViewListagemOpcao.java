package br.com.senai.cardapiosmktplaceview.view.opcao;

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

import br.com.senai.cardapiosmktplaceview.client.OpcaoClient;
import br.com.senai.cardapiosmktplaceview.dto.Categoria;
import br.com.senai.cardapiosmktplaceview.dto.Opcao;
import br.com.senai.cardapiosmktplaceview.dto.Paginacao;
import br.com.senai.cardapiosmktplaceview.dto.Restaurante;
import br.com.senai.cardapiosmktplaceview.enums.Status;
import br.com.senai.cardapiosmktplaceview.enums.TipoDeCategoria;
import br.com.senai.cardapiosmktplaceview.view.componentes.table.OpcaoTableModel;
import br.com.senai.cardapiosmktplaceview.view.componentes.table.RestauranteTableModel;
import br.com.senai.cardapiosmktplaceview.view.restaurante.ViewListagemRestaurante;
import br.com.senai.cardapiosmktplaceview.view.selecao.categoria.IReceptorDeCategoriaSelecionada;
import br.com.senai.cardapiosmktplaceview.view.selecao.categoria.ViewSelecaoCategoria;
import br.com.senai.cardapiosmktplaceview.view.selecao.restaurante.IReceptorDeRestauranteSelecionado;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotBlank;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@Component
public class ViewListagemOpcao extends JFrame 
	implements IReceptorDeRestauranteSelecionado, IReceptorDeCategoriaSelecionada{

	private static final long serialVersionUID = 1L;
	
	private final int PRIMEIRA_PAGINA = 0;
	
	private String tokenDeAcesso;	
	
	private JPanel contentPane;
	
	private JTable tbOpcoes;
	
	private JTextField edtNome;
	
	private JTextField edtCategoria;
	
	private JTextField edtRestaurante;
	
	private JButton btnStatus;
	
	private JButton btnUsar;
	
	private JButton btnEditar;
	
	private JButton btnExcluir;
	
	private JButton btnNovo;
	
	private Categoria categoriaSelecionada;
	
	private Restaurante restauranteSelecionado;
	
	private IReceptorDeCategoriaSelecionada receptorDeCategoria;
	
	private IReceptorDeRestauranteSelecionado receptorDeRestaurante;
	
	private Paginacao<Opcao> paginacao;
	
	private int paginaAtual;
	
	@Lazy
	@Autowired
	private ViewSelecaoCategoria viewSelecaoCategoria;
	
	@Lazy
	@Autowired
	private ViewListagemRestaurante viewListagemRestaurante;
	
	@Lazy
	@Autowired
	private ViewCadastroOpcao viewCadastroOpcao;
	
	@Autowired
	private OpcaoClient opcaoClient;	
	
	@PostConstruct
	private void inicializar() {	
		this.configurarTabela();
		this.receptorDeCategoria = this;
		this.receptorDeRestaurante = this;
		this.btnUsar.setEnabled(false);
	}
	
	private void configurarColuna(int indice, int largura) {
		this.tbOpcoes.getColumnModel().getColumn(indice).setResizable(true);
		this.tbOpcoes.getColumnModel().getColumn(indice).setPreferredWidth(largura);
	}
	
	private void configurarTabela() {
		final int COLUNA_ID = 0;
		final int COLUNA_NOME = 1;
		final int COLUNA_STATUS = 2;
		this.tbOpcoes.getTableHeader().setReorderingAllowed(false);
		this.tbOpcoes.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.configurarColuna(COLUNA_ID, 50);
		this.configurarColuna(COLUNA_NOME, 520);
		this.configurarColuna(COLUNA_STATUS, 80);
	}
	
	public void mostrarTela(
			@NotBlank(message = "O token de acesso é obrigatório")
			String tokenDeAcesso) {
		this.setVisible(true);
		this.tokenDeAcesso = tokenDeAcesso;
		this.opcaoClient.setTokenDeAcesso(tokenDeAcesso);
	}
	
	private void listarOpcoesDa(int pagina) {
		try {
			paginacao = opcaoClient.listarPor(edtNome.getText(), 
					categoriaSelecionada, restauranteSelecionado, pagina);			
			OpcaoTableModel model = new OpcaoTableModel(paginacao.getListagem());
			tbOpcoes.setModel(model);			
			configurarTabela();			
		}catch (ConstraintViolationException cve) {
			StringBuilder msgErro = new StringBuilder("Os seguintes erros ocorreram:\n");			
			for (ConstraintViolation<?> cv : cve.getConstraintViolations()) {
				msgErro.append("  -").append(cv.getMessage()).append("\n");
			}
			JOptionPane.showMessageDialog(contentPane, msgErro, 
					"Falha na Listagem", JOptionPane.ERROR_MESSAGE);
		}catch (Exception ex) {
			JOptionPane.showMessageDialog(contentPane, ex.getMessage(), 
					"Falha na Listagem", JOptionPane.ERROR_MESSAGE);
		}

	}
	

	@Override
	public void usar(Categoria categoriaSelecionada) {
		this.categoriaSelecionada = categoriaSelecionada;
		this.edtCategoria.setText(categoriaSelecionada.getId() + " - " + categoriaSelecionada.getNome());
	}

	@Override
	public void usar(Restaurante restauranteSelecionado) {
		this.restauranteSelecionado = restauranteSelecionado;
		this.edtRestaurante.setText(restauranteSelecionado.getId() + " - " + restauranteSelecionado.getNome());
	}

	public ViewListagemOpcao() {
		setTitle("Gerenciar Opções de Cardápio - Listagem");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 694, 624);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnNovo = new JButton("Novo");
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewCadastroOpcao.colocarEmModoDeInsercao(tokenDeAcesso);
				dispose();
			}
		});
		btnNovo.setBounds(568, 12, 98, 26);
		contentPane.add(btnNovo);
		
		JPanel pnlFiltro = new JPanel();
		pnlFiltro.setLayout(null);
		pnlFiltro.setBorder(new TitledBorder(null, "Filtros", 
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlFiltro.setBounds(6, 50, 660, 144);
		contentPane.add(pnlFiltro);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(12, 81, 55, 16);
		pnlFiltro.add(lblNome);
		
		edtNome = new JTextField();
		edtNome.setColumns(10);
		edtNome.setBounds(12, 101, 254, 25);
		pnlFiltro.add(edtNome);
		
		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listarOpcoesDa(PRIMEIRA_PAGINA);
			}
		});
		btnListar.setBounds(550, 100, 98, 26);
		pnlFiltro.add(btnListar);
		
		edtCategoria = new JTextField();
		edtCategoria.setEditable(false);
		edtCategoria.setColumns(10);
		edtCategoria.setBounds(278, 101, 228, 25);
		pnlFiltro.add(edtCategoria);
		
		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setBounds(278, 81, 55, 16);
		pnlFiltro.add(lblCategoria);
		
		JButton btnSelecionarCategoria = new JButton("...");
		btnSelecionarCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewSelecaoCategoria.apresentarSelecao(receptorDeCategoria, TipoDeCategoria.OPCAO, tokenDeAcesso);
			}
		});
		btnSelecionarCategoria.setBounds(507, 100, 31, 26);
		pnlFiltro.add(btnSelecionarCategoria);
		
		edtRestaurante = new JTextField();
		edtRestaurante.setEditable(false);
		edtRestaurante.setColumns(10);
		edtRestaurante.setBounds(12, 42, 603, 25);
		pnlFiltro.add(edtRestaurante);
		
		JButton btnSelecionarRestaurante = new JButton("...");
		btnSelecionarRestaurante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewListagemRestaurante.colocarEmModoDeSelecao(receptorDeRestaurante, tokenDeAcesso);
			}
		});
		btnSelecionarRestaurante.setBounds(617, 41, 31, 26);
		pnlFiltro.add(btnSelecionarRestaurante);
		
		JLabel lblRestaurante = new JLabel("Restaurante");
		lblRestaurante.setBounds(12, 22, 92, 16);
		pnlFiltro.add(lblRestaurante);
		
		JPanel pnlRegistros = new JPanel();
		pnlRegistros.setLayout(null);
		pnlRegistros.setBorder(new TitledBorder(null, "Registros Encontrados", 
								TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlRegistros.setBounds(6, 199, 660, 306);
		contentPane.add(pnlRegistros);
		
		JButton btnPrimeiro = new JButton("<<");
		btnPrimeiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual = PRIMEIRA_PAGINA;
				listarOpcoesDa(PRIMEIRA_PAGINA);
			}
		});
		btnPrimeiro.setBounds(191, 268, 61, 26);
		pnlRegistros.add(btnPrimeiro);
		
		JButton btnAnterior = new JButton("<");
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (paginaAtual > 0) {
					paginaAtual--;
					listarOpcoesDa(paginaAtual);
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
					listarOpcoesDa(paginaAtual);
				}
			}
		});
		btnProximo.setBounds(337, 268, 61, 26);
		pnlRegistros.add(btnProximo);
		
		JButton btnUltimo = new JButton(">>");
		btnUltimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual = paginacao.getTotalDePaginas();
				listarOpcoesDa(paginaAtual);
			}
		});
		btnUltimo.setBounds(410, 268, 61, 26);
		pnlRegistros.add(btnUltimo);
		
		tbOpcoes = new JTable(new RestauranteTableModel());
		tbOpcoes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int linhaSelecionada = tbOpcoes.getSelectedRow();
				if (linhaSelecionada >= 0) {
					OpcaoTableModel model = (OpcaoTableModel)tbOpcoes.getModel();
					Opcao opcaoSelecionada = model.getPor(linhaSelecionada);
					btnStatus.setText(opcaoSelecionada.isAtiva() ? "Inativar" : "Ativar");
				}
			}
		});
		JScrollPane scrollPane = new JScrollPane(tbOpcoes);
		scrollPane.setBounds(12, 25, 636, 231);
		pnlRegistros.add(scrollPane);
		
		JPanel pnlAcoes = new JPanel();
		pnlAcoes.setLayout(null);
		pnlAcoes.setBorder(new TitledBorder(null, "A\u00E7\u00F5es do Registro Selecionado",
								TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlAcoes.setBounds(209, 512, 457, 67);
		contentPane.add(pnlAcoes);
		
		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tbOpcoes.getSelectedRow();
				if (linhaSelecionada >= 0) {
					OpcaoTableModel model = (OpcaoTableModel)tbOpcoes.getModel();
					Opcao opcaoSelecionada = model.getPor(linhaSelecionada);
					viewCadastroOpcao.colocarEmModoDeEdicao(tokenDeAcesso, opcaoSelecionada);
					dispose();
				}else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma categoria para edição");
				}
			}
		});
		btnEditar.setBounds(21, 28, 98, 26);
		pnlAcoes.add(btnEditar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tbOpcoes.getSelectedRow();
				if (linhaSelecionada >= 0) {
					int opcao = JOptionPane.showConfirmDialog(contentPane, 
							"Deseja remover a opção selecionada?", 
							"Remoção", JOptionPane.YES_NO_OPTION);
					boolean isConfirmacaoRealizada = opcao == 0;
					if (isConfirmacaoRealizada) {
						OpcaoTableModel model = (OpcaoTableModel)tbOpcoes.getModel();
						Opcao opcaoSelecionada = model.getPor(linhaSelecionada);
						try {
							opcaoClient.removerPor(opcaoSelecionada.getId());
							listarOpcoesDa(paginaAtual);
							JOptionPane.showMessageDialog(contentPane, "Opção removida com sucesso", 
									"Sucesso na Remoção", JOptionPane.INFORMATION_MESSAGE);
						}catch (ConstraintViolationException cve) {
							StringBuilder msgErro = new StringBuilder("Os seguintes erros ocorreram:\n");			
							for (ConstraintViolation<?> cv : cve.getConstraintViolations()) {
								msgErro.append("  -").append(cv.getMessage()).append("\n");
							}
							JOptionPane.showMessageDialog(contentPane, msgErro, 
									"Falha na Listagem", JOptionPane.ERROR_MESSAGE);
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
				
				int linhaSelecionada = tbOpcoes.getSelectedRow();
				
				if (linhaSelecionada >= 0) {

					int opcao = JOptionPane.showConfirmDialog(contentPane, 
							"Deseja atualizar o status da opção selecionada?", 
							"Atualização", JOptionPane.YES_NO_OPTION);

					boolean isConfirmacaoRealizada = opcao == 0;

					if (isConfirmacaoRealizada) {
						
						try {
							
							OpcaoTableModel model = (OpcaoTableModel)tbOpcoes.getModel();
							Opcao opcaoSelecionada = model.getPor(linhaSelecionada);
							
							Status novoStatus = opcaoSelecionada.isAtiva() ? Status.I : Status.A;
							opcaoClient.atualizarPor(opcaoSelecionada.getId(), novoStatus);
							listarOpcoesDa(paginaAtual);
							btnStatus.setText(novoStatus == Status.A ? "Inativar" : "Ativar");
							JOptionPane.showMessageDialog(contentPane, "O status da opção foi atualizado com sucesso", 
									"Sucesso na Remoção", JOptionPane.INFORMATION_MESSAGE);
						}catch (ConstraintViolationException cve) {
							StringBuilder msgErro = new StringBuilder("Os seguintes erros ocorreram:\n");			
							for (ConstraintViolation<?> cv : cve.getConstraintViolations()) {
								msgErro.append("  -").append(cv.getMessage()).append("\n");
							}
							JOptionPane.showMessageDialog(contentPane, msgErro, 
									"Falha na Listagem", JOptionPane.ERROR_MESSAGE);
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
		
		btnUsar = new JButton("Usar");
		btnUsar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tbOpcoes.getSelectedRow();
				if (linhaSelecionada >= 0) {
					//OpcaoTableModel model = (OpcaoTableModel)tbOpcoes.getModel();
					//Opcao opcaoSelecionada = model.getPor(linhaSelecionada);
					//TODO Chamar o método usar de quem vai implementar a interface de receptor de opção					
				}else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma linha para seleção");
				}
			}
		});
		btnUsar.setBounds(347, 28, 98, 26);
		pnlAcoes.add(btnUsar);
		this.setLocationRelativeTo(null);
	}

}
