package br.com.senai.cardapiosmktplaceview.view.opcao;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.com.senai.cardapiosmktplaceview.client.OpcaoClient;
import br.com.senai.cardapiosmktplaceview.dto.Categoria;
import br.com.senai.cardapiosmktplaceview.dto.Opcao;
import br.com.senai.cardapiosmktplaceview.dto.Restaurante;
import br.com.senai.cardapiosmktplaceview.enums.Confirmacao;
import br.com.senai.cardapiosmktplaceview.enums.Status;
import br.com.senai.cardapiosmktplaceview.enums.TipoDeCategoria;
import br.com.senai.cardapiosmktplaceview.util.EditDocDecimal;
import br.com.senai.cardapiosmktplaceview.view.restaurante.ViewListagemRestaurante;
import br.com.senai.cardapiosmktplaceview.view.selecao.categoria.IReceptorDeCategoriaSelecionada;
import br.com.senai.cardapiosmktplaceview.view.selecao.categoria.ViewSelecaoCategoria;
import br.com.senai.cardapiosmktplaceview.view.selecao.restaurante.IReceptorDeRestauranteSelecionado;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Component
public class ViewCadastroOpcao extends 
	JFrame implements IReceptorDeRestauranteSelecionado, IReceptorDeCategoriaSelecionada{

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private String tokenDeAcesso;
	
	private JComboBox<String> cbPromocao;
	
	private JComboBox<String> cbStatus;
	
	private JLabel lblStatus;
	
	private JTextArea taDescricao;
	
	private JTextField edtNome;
	
	private JTextField edtRestaurante;
	
	private JTextField edtCategoria;
	
	private Opcao opcao;
	
	private Categoria categoriaSelecionada;
	
	private Restaurante restauranteSelecionado;
	
	private IReceptorDeCategoriaSelecionada receptorDeCategoria;
	
	private IReceptorDeRestauranteSelecionado receptorDeRestaurante;
	
	@Lazy
	@Autowired
	private ViewSelecaoCategoria viewSelecaoCategoria;
	
	@Lazy
	@Autowired
	private ViewListagemRestaurante viewListagemRestaurante;
	
	@Autowired
	private ViewListagemOpcao viewListagemOpcao;
	
	@Autowired
	private OpcaoClient opcaoClient;
	
	private JTextField edtPerc;
	
	@PostConstruct
	private void inicializar() {		
		this.carregarOpcoesNosCombos();	
		this.receptorDeCategoria = this;
		this.receptorDeRestaurante = this;
	}
	
	public void colocarEmModoDeInsercao(
			@NotBlank(message = "O token de acesso é obrigatório")
			String tokenDeAcesso) {
		this.setVisible(true);
		this.tokenDeAcesso = tokenDeAcesso;
		this.opcaoClient.setTokenDeAcesso(tokenDeAcesso);
		this.mostrarStatus(false);
		this.limparCampos();
	}
	
	public void colocarEmModoDeEdicao(
			@NotBlank(message = "O token de acesso é obrigatório")
			String tokenDeAcesso,
			@NotNull(message = "A opção não pode ser nula")
			Opcao opcao) {
		this.setVisible(true);
		this.tokenDeAcesso = tokenDeAcesso;
		this.opcaoClient.setTokenDeAcesso(tokenDeAcesso);
		this.mostrarStatus(true);
		this.preencherFormularioCom(opcao);
	}
	
	private void mostrarStatus(boolean isMostrar) {
		this.lblStatus.setVisible(isMostrar);
		this.cbStatus.setVisible(isMostrar);
	}
	
	private void preencherFormularioCom(Opcao opcao) {
		this.opcao = opcao;
		this.edtNome.setText(opcao.getNome());
		this.taDescricao.setText(opcao.getDescricao());		
		this.cbPromocao.setSelectedIndex(opcao.getPromocao().ordinal() + 1);
		this.cbStatus.setSelectedIndex(opcao.getStatus().ordinal() + 1);
		this.restauranteSelecionado = opcao.getRestaurante();
		this.edtRestaurante.setText(opcao.getRestaurante().getNome());
		this.categoriaSelecionada = opcao.getCategoria();
		this.edtCategoria.setText(opcao.getCategoria().getNome());		
	}
	
	private void limparCampos() {
		this.opcao = new Opcao();
		this.edtNome.setText("");
		this.taDescricao.setText("");
		this.restauranteSelecionado = null;
		this.edtRestaurante.setText("");
		this.categoriaSelecionada = null;
		this.edtCategoria.setText("");
		this.cbPromocao.setSelectedIndex(0);
		this.cbStatus.setSelectedIndex(0);
	}

	private void carregarOpcoesNosCombos() {
		
		this.cbStatus.addItem("Selecione...");
		this.cbStatus.addItem("Ativo");
		this.cbStatus.addItem("Inativo");
		
		this.cbPromocao.addItem("Selecione...");
		this.cbPromocao.addItem("Sim");
		this.cbPromocao.addItem("Não");
		
	}
	
	private Status getStatusSelecionado() {
		if (cbStatus.getSelectedIndex() > 0) {
			return Status.values()[cbStatus.getSelectedIndex() - 1];
		}
		return null;
	}
	
	private Confirmacao getFlagDePromocaoSelecionada() {
		if (cbPromocao.getSelectedIndex() > 0) {
			return Confirmacao.values()[cbPromocao.getSelectedIndex() - 1];
		}
		return null;
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
	
	public ViewCadastroOpcao() {
		setTitle("Gerenciar Opções do Cardápio - Inserção/Alteração");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 755, 473);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewListagemOpcao.mostrarTela(tokenDeAcesso);
				dispose();
			}
		});
		btnPesquisar.setBounds(628, 12, 98, 26);
		contentPane.add(btnPesquisar);
		
		JPanel pnlDadosGerais = new JPanel();
		pnlDadosGerais.setLayout(null);
		pnlDadosGerais.setBorder(new TitledBorder(null, "Dados Gerais", 
						TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlDadosGerais.setBounds(12, 50, 714, 299);
		contentPane.add(pnlDadosGerais);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(12, 99, 55, 16);
		pnlDadosGerais.add(lblNome);
		
		edtNome = new JTextField();
		edtNome.setColumns(10);
		edtNome.setBounds(12, 122, 408, 25);
		pnlDadosGerais.add(edtNome);
		
		cbPromocao = new JComboBox<String>();
		cbPromocao.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (getFlagDePromocaoSelecionada() == Confirmacao.S) {
					edtPerc.setEditable(true);					
				}else {
					edtPerc.setEditable(false);
					edtPerc.setText("");
				}
			}
		});
		cbPromocao.setBounds(432, 122, 92, 25);
		pnlDadosGerais.add(cbPromocao);
		
		JLabel lblPromocao = new JLabel("Promoção");
		lblPromocao.setBounds(430, 102, 68, 16);
		pnlDadosGerais.add(lblPromocao);
		
		cbStatus = new JComboBox<String>();
		cbStatus.setBounds(610, 122, 92, 25);
		pnlDadosGerais.add(cbStatus);
		
		lblStatus = new JLabel("Status");
		lblStatus.setBounds(608, 102, 55, 16);
		pnlDadosGerais.add(lblStatus);
		
		JLabel lblDescricao = new JLabel("Descrição");
		lblDescricao.setBounds(12, 159, 118, 16);
		pnlDadosGerais.add(lblDescricao);
		
		taDescricao = new JTextArea();
		taDescricao.setBorder(new LineBorder(new Color(0, 0, 0)));
		taDescricao.setBounds(12, 182, 690, 94);
		pnlDadosGerais.add(taDescricao);
		
		edtRestaurante = new JTextField();
		edtRestaurante.setEditable(false);
		edtRestaurante.setColumns(10);
		edtRestaurante.setBounds(12, 44, 382, 25);
		pnlDadosGerais.add(edtRestaurante);
		
		JButton btnSelecionarRestaurante = new JButton("...");
		btnSelecionarRestaurante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewListagemRestaurante.colocarEmModoDeSelecao(receptorDeRestaurante, tokenDeAcesso);
			}
		});
		btnSelecionarRestaurante.setBounds(397, 43, 31, 26);
		pnlDadosGerais.add(btnSelecionarRestaurante);
		
		JLabel lblRestaurante = new JLabel("Restaurante");
		lblRestaurante.setBounds(12, 24, 92, 16);
		pnlDadosGerais.add(lblRestaurante);
		
		edtCategoria = new JTextField();
		edtCategoria.setEditable(false);
		edtCategoria.setColumns(10);
		edtCategoria.setBounds(442, 44, 228, 25);
		pnlDadosGerais.add(edtCategoria);
		
		JButton btnSelecionarCategoria = new JButton("...");
		btnSelecionarCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewSelecaoCategoria.apresentarSelecao(receptorDeCategoria, TipoDeCategoria.OPCAO, tokenDeAcesso);
			}
		});
		btnSelecionarCategoria.setBounds(671, 43, 31, 26);
		pnlDadosGerais.add(btnSelecionarCategoria);
		
		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setBounds(442, 24, 55, 16);
		pnlDadosGerais.add(lblCategoria);
		
		JLabel lblPerc = new JLabel("%");
		lblPerc.setBounds(536, 102, 55, 16);
		pnlDadosGerais.add(lblPerc);
		
		edtPerc = new JTextField();
		edtPerc.setDocument(new EditDocDecimal());
		edtPerc.setBounds(536, 124, 68, 25);
		pnlDadosGerais.add(edtPerc);
		edtPerc.setColumns(10);		
		
		JPanel pnlAcoes = new JPanel();
		pnlAcoes.setLayout(null);
		pnlAcoes.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), 
								"A\u00E7\u00F5es", TitledBorder.LEADING, TitledBorder.TOP, 
								null, new Color(51, 51, 51)));
		pnlAcoes.setBounds(487, 361, 240, 65);
		contentPane.add(pnlAcoes);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				opcao.setNome(edtNome.getText());
				opcao.setDescricao(taDescricao.getText());
				opcao.setPromocao(getFlagDePromocaoSelecionada());
				opcao.setStatus(getStatusSelecionado());
				opcao.setRestaurante(restauranteSelecionado);
				opcao.setCategoria(categoriaSelecionada);
				
				if (getFlagDePromocaoSelecionada() == Confirmacao.S) {
					EditDocDecimal decimalDoc = (EditDocDecimal)edtPerc.getDocument();
					opcao.setPercentualDeDesconto(decimalDoc.getBigDecimalValue());					
				}

				try {

					if (opcao.isPersistida()) {
						Opcao opcaoAtualizada = opcaoClient.atualizar(opcao);
						preencherFormularioCom(opcaoAtualizada);
					}else {
						Opcao opcaoSalva = opcaoClient.inserir(opcao);
						preencherFormularioCom(opcaoSalva);
						mostrarStatus(true);
					}

					JOptionPane.showMessageDialog(contentPane, "Opção salva com sucesso", 
							"Sucesso na Gravação", JOptionPane.INFORMATION_MESSAGE);

				}catch (ConstraintViolationException cve) {
					StringBuilder msgErro = new StringBuilder("Os seguintes erros ocorreram:\n");			
					for (ConstraintViolation<?> cv : cve.getConstraintViolations()) {
						msgErro.append("  -").append(cv.getMessage()).append("\n");
					}
					JOptionPane.showMessageDialog(contentPane, msgErro, 
							"Falha na Listagem", JOptionPane.ERROR_MESSAGE);
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage(),
							"Erro na Gravação", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSalvar.setBounds(18, 27, 98, 26);
		pnlAcoes.add(btnSalvar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opcao = JOptionPane.showConfirmDialog(contentPane, 
						"Deseja cancelar a gravação da opção?", 
						"Cancelamento", JOptionPane.YES_NO_OPTION);
				boolean isConfirmacaoRealizada = opcao == 0;
				if (isConfirmacaoRealizada) {
					limparCampos();
				}
			}
		});
		btnCancelar.setBounds(128, 27, 98, 26);
		pnlAcoes.add(btnCancelar);
		this.setLocationRelativeTo(null);
	}
}
