package br.com.senai.cardapiosmktplaceview.view.categoria;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
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

import br.com.senai.cardapiosmktplaceview.client.CategoriaClient;
import br.com.senai.cardapiosmktplaceview.dto.Categoria;
import br.com.senai.cardapiosmktplaceview.dto.Paginacao;
import br.com.senai.cardapiosmktplaceview.enums.Status;
import br.com.senai.cardapiosmktplaceview.enums.TipoDeCategoria;
import br.com.senai.cardapiosmktplaceview.view.componentes.table.CategoriaTableModel;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotBlank;

@Component
public class ViewListagemCategoria extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private final int PRIMEIRA_PAGINA = 0;
	
	private JPanel contentPane;
	
	private String tokenDeAcesso;	
	
	private JTextField edtNome;
	
	private JLabel lblTipo;
	
	private JComboBox<String> cbTipo;
	
	private JComboBox<String> cbStatus;
	
	private JButton btnStatus;
	
	private JScrollPane scrollPane;
	
	private JTable tbCategorias;
	
	private Paginacao<Categoria> paginacao;
	
	private int paginaAtual;
	
	@Autowired
	private CategoriaClient categoriaClient;
		
	@Lazy
	@Autowired
	private ViewCadastroCategoria viewCadastro;
	
	@PostConstruct
	private void inicializar() {		
		this.carregarOpcoesNosCombos();
		this.configurarTabela();
	}
	
	public void mostrarTela(
			@NotBlank(message = "O token de acesso é obrigatório")
			String tokenDeAcesso) {
		this.setVisible(true);
		this.tokenDeAcesso = tokenDeAcesso;
		this.categoriaClient.setTokenDeAcesso(tokenDeAcesso);
	}
	
	private void carregarOpcoesNosCombos() {
		
		this.cbStatus.addItem("Selecione...");
		this.cbStatus.addItem("Ativo");
		this.cbStatus.addItem("Inativo");
		
		this.cbTipo.addItem("Selecione...");
		this.cbTipo.addItem("Restaurante");
		this.cbTipo.addItem("Opção");
		
	}
	
	private Status getStatusSelecionado() {
		if (cbStatus.getSelectedIndex() > 0) {
			return Status.values()[cbStatus.getSelectedIndex() - 1];
		}
		return null;
	}
	
	private TipoDeCategoria getTipoSelecionado() {
		if (cbTipo.getSelectedIndex() > 0) {
			return TipoDeCategoria.values()[cbTipo.getSelectedIndex() - 1];
		}
		return null;
	}
	
	private void listarCategoriasDa(int pagina) {
		try {
			paginacao = categoriaClient.listarPor(edtNome.getText(), 
					getStatusSelecionado(), getTipoSelecionado(), pagina);			
			CategoriaTableModel model = new CategoriaTableModel(paginacao.getListagem());
			tbCategorias.setModel(model);			
			configurarTabela();
			btnStatus.setText(getStatusSelecionado() == Status.A ? "Inativar" : "Ativar");
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
		this.configurarColuna(COLUNA_NOME, 550);
	}
	
	public ViewListagemCategoria() {
		setTitle("Gerenciar Categorias - Listagem");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 694, 581);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pnlFiltro = new JPanel();
		pnlFiltro.setBorder(new TitledBorder(null, "Filtros", 
				TitledBorder.LEADING, TitledBorder.TOP, 
				null, null));
		pnlFiltro.setBounds(10, 50, 660, 89);
		contentPane.add(pnlFiltro);
		pnlFiltro.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(12, 22, 55, 16);
		pnlFiltro.add(lblNome);
		
		edtNome = new JTextField();
		edtNome.setBounds(12, 42, 254, 25);
		pnlFiltro.add(edtNome);
		edtNome.setColumns(10);
		
		lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(276, 22, 55, 16);
		pnlFiltro.add(lblTipo);
		
		cbTipo = new JComboBox<>();
		cbTipo.setBounds(278, 42, 123, 25);
		pnlFiltro.add(cbTipo);
		
		cbStatus = new JComboBox<>();
		cbStatus.setBounds(415, 42, 123, 25);
		pnlFiltro.add(cbStatus);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(413, 22, 55, 16);
		pnlFiltro.add(lblStatus);
		
		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				listarCategoriasDa(PRIMEIRA_PAGINA);
			}
		});
		btnListar.setBounds(550, 41, 98, 26);
		pnlFiltro.add(btnListar);
		
		JPanel pnlRegistros = new JPanel();
		pnlRegistros.setBorder(new TitledBorder(null, "Registros Encontrados", 
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlRegistros.setBounds(10, 151, 660, 306);
		contentPane.add(pnlRegistros);
		pnlRegistros.setLayout(null);
		
		JButton btnPrimeiro = new JButton("<<");
		btnPrimeiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual = PRIMEIRA_PAGINA;
				listarCategoriasDa(paginaAtual);
			}
		});
		btnPrimeiro.setBounds(191, 268, 61, 26);
		pnlRegistros.add(btnPrimeiro);
		
		JButton btnAnterior = new JButton("<");
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (paginaAtual > 0) {
					paginaAtual--;
					listarCategoriasDa(paginaAtual);
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
					listarCategoriasDa(paginaAtual);
				}
			}
		});
		btnProximo.setBounds(337, 268, 61, 26);
		pnlRegistros.add(btnProximo);
		
		JButton btnUltimo = new JButton(">>");
		btnUltimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual = paginacao.getTotalDePaginas();
				listarCategoriasDa(paginaAtual);
			}
		});
		btnUltimo.setBounds(410, 268, 61, 26);
		pnlRegistros.add(btnUltimo);
		
		tbCategorias = new JTable(new CategoriaTableModel());
		scrollPane = new JScrollPane(tbCategorias);
		scrollPane.setBounds(12, 25, 636, 231);
		pnlRegistros.add(scrollPane);
		
		
		JPanel pnlAcoes = new JPanel();
		pnlAcoes.setBorder(new TitledBorder(null, "A\u00E7\u00F5es do Registro Selecionado", 
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlAcoes.setBounds(312, 469, 358, 67);
		contentPane.add(pnlAcoes);
		pnlAcoes.setLayout(null);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tbCategorias.getSelectedRow();
				if (linhaSelecionada >= 0) {
					CategoriaTableModel model = (CategoriaTableModel)tbCategorias.getModel();
					Categoria categoriaSelecionada = model.getPor(linhaSelecionada);
					viewCadastro.colocarEmModoDeEdicao(tokenDeAcesso, categoriaSelecionada);
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
				int linhaSelecionada = tbCategorias.getSelectedRow();
				if (linhaSelecionada >= 0) {
					int opcao = JOptionPane.showConfirmDialog(contentPane, 
							"Deseja remover a categoria selecionada?", 
							"Remoção", JOptionPane.YES_NO_OPTION);
					boolean isConfirmacaoRealizada = opcao == 0;
					if (isConfirmacaoRealizada) {
						CategoriaTableModel model = (CategoriaTableModel)tbCategorias.getModel();
						Categoria categoriaSelecionada = model.getPor(linhaSelecionada);						
						try {
							categoriaClient.removerPor(categoriaSelecionada.getId());
							listarCategoriasDa(paginaAtual);
							JOptionPane.showMessageDialog(contentPane, "Categoria removida com sucesso", 
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
				
				int linhaSelecionada = tbCategorias.getSelectedRow();
				
				if (linhaSelecionada >= 0) {

					int opcao = JOptionPane.showConfirmDialog(contentPane, 
							"Deseja atualizar o status da categoria selecionada?", 
							"Atualização", JOptionPane.YES_NO_OPTION);

					boolean isConfirmacaoRealizada = opcao == 0;

					if (isConfirmacaoRealizada) {
						
						try {
							CategoriaTableModel model = (CategoriaTableModel)tbCategorias.getModel();
							Categoria categoriaSelecionada = model.getPor(linhaSelecionada);
							Status novoStatus = categoriaSelecionada.isAtiva() ? Status.I : Status.A;
							categoriaClient.atualizarPor(categoriaSelecionada.getId(), novoStatus);
							listarCategoriasDa(paginaAtual);
							JOptionPane.showMessageDialog(contentPane, "O status da categoria foi atualizado com sucesso", 
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
		
		JButton btnNovo = new JButton("Novo");
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewCadastro.colocarEmModoDeInsercao(tokenDeAcesso);
				dispose();
			}
		});
		btnNovo.setBounds(568, 12, 98, 26);
		contentPane.add(btnNovo);
		this.setLocationRelativeTo(null);		
	}
}
