package br.com.senai.cardapiosmktplaceview.view.restaurante;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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

import br.com.senai.cardapiosmktplaceview.client.RestauranteClient;
import br.com.senai.cardapiosmktplaceview.dto.Categoria;
import br.com.senai.cardapiosmktplaceview.dto.Restaurante;
import br.com.senai.cardapiosmktplaceview.enums.TipoDeCategoria;
import br.com.senai.cardapiosmktplaceview.view.selecao.categoria.IReceptorDeCategoriaSelecionada;
import br.com.senai.cardapiosmktplaceview.view.selecao.categoria.ViewSelecaoCategoria;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Component
public class ViewCadastroRestaurante extends JFrame implements IReceptorDeCategoriaSelecionada {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private String tokenDeAcesso;
	
	private JTextField edtNome;
	
	private JTextField edtCategoria;
	
	private JTextField edtCidade;
	
	private JTextField edtBairro;
	
	private JTextField edtLogradouro;
	
	private JTextField edtComplemento;
	
	private JTextArea taDescricao;
	
	private Restaurante restaurante;
	
	private Categoria categoriaSelecionada;
	
	private IReceptorDeCategoriaSelecionada receptor;
	
	@Lazy
	@Autowired
	private ViewSelecaoCategoria viewSelecao;
	
	@Autowired
	private ViewListagemRestaurante viewListagem;
	
	@Autowired
	private RestauranteClient restauranteClient;
	
	@PostConstruct
	public void inicializar() {
		this.receptor = this;
	}
	
	@Override
	public void usar(Categoria categoriaSelecionada) {	
		this.categoriaSelecionada = categoriaSelecionada;
		this.edtCategoria.setText(categoriaSelecionada.getId() + " - " + categoriaSelecionada.getNome());
	}
	
	public void colocarEmModoDeInsercao(
			@NotBlank(message = "O token de acesso é obrigatório")
			String tokenDeAcesso) {
		this.setVisible(true);
		this.tokenDeAcesso = tokenDeAcesso;
		this.restauranteClient.setTokenDeAcesso(tokenDeAcesso);		
		this.limparCampos();		
		Restaurante restaurante = new Restaurante();
		restaurante.setNome("Burger King");
		restaurante.setDescricao("Hamburguer da hora");
		Categoria categoria = new Categoria();
		categoria.setId(239);
		restaurante.setCategoria(categoria);
		restaurante.getEndereco().setCidade("Tubarão");
		restaurante.getEndereco().setBairro("Centro");
		restaurante.getEndereco().setLogradouro("Rua João Ferreira de Souza, 184");
		restaurante.getEndereco().setComplemento("Residencial Dona Isaura");
		this.preencherFormularioCom(restaurante);
	}
	
	public void colocarEmModoDeEdicao(
			@NotBlank(message = "O token de acesso é obrigatório")
			String tokenDeAcesso,
			@NotNull(message = "A categoria não pode ser nula")
			Restaurante restaurante) {
		this.setVisible(true);
		this.tokenDeAcesso = tokenDeAcesso;
		this.restauranteClient.setTokenDeAcesso(tokenDeAcesso);		
		this.preencherFormularioCom(restaurante);
	}
	
	private void limparCampos() {
		this.restaurante = new Restaurante();
		this.edtNome.setText("");
		this.taDescricao.setText("");
		this.edtCategoria.setText("");
		this.categoriaSelecionada = null;
		this.edtCidade.setText("");
		this.edtBairro.setText("");
		this.edtLogradouro.setText("");
		this.edtComplemento.setText("");		
	}
	
	private void preencherFormularioCom(Restaurante restaurante) {
		this.restaurante = restaurante;
		this.edtNome.setText(restaurante.getNome());
		this.taDescricao.setText(restaurante.getDescricao());
		this.usar(restaurante.getCategoria());
		this.edtCidade.setText(restaurante.getEndereco().getCidade());
		this.edtBairro.setText(restaurante.getEndereco().getBairro());
		this.edtLogradouro.setText(restaurante.getEndereco().getLogradouro());
		this.edtComplemento.setText(restaurante.getEndereco().getComplemento());
	}

	public ViewCadastroRestaurante() {
		setTitle("Gerenciar Restaurantes - Inserção/Alteração");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 694, 555);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewListagem.mostrarTela(tokenDeAcesso);
				dispose();
			}
		});
		btnPesquisar.setBounds(570, 11, 98, 26);
		contentPane.add(btnPesquisar);
		
		JPanel pnlDadosGerais = new JPanel();
		pnlDadosGerais.setLayout(null);
		pnlDadosGerais.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), 
				"Dados Gerais", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		pnlDadosGerais.setBounds(10, 48, 660, 225);
		contentPane.add(pnlDadosGerais);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(12, 22, 55, 16);
		pnlDadosGerais.add(lblNome);
		
		edtNome = new JTextField();
		edtNome.setColumns(10);
		edtNome.setBounds(12, 42, 364, 25);
		pnlDadosGerais.add(edtNome);
		
		edtCategoria = new JTextField();
		edtCategoria.setEditable(false);
		edtCategoria.setColumns(10);
		edtCategoria.setBounds(388, 42, 228, 25);
		pnlDadosGerais.add(edtCategoria);
		
		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setBounds(388, 22, 55, 16);
		pnlDadosGerais.add(lblCategoria);
		
		JButton btnSelecionar = new JButton("...");
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewSelecao.apresentarSelecao(receptor, TipoDeCategoria.RESTAURANTE, tokenDeAcesso);
			}
		});
		btnSelecionar.setBounds(617, 41, 31, 26);
		pnlDadosGerais.add(btnSelecionar);
		
		JLabel lblDescricao = new JLabel("Descrição");
		lblDescricao.setBounds(12, 77, 118, 16);
		pnlDadosGerais.add(lblDescricao);
		
		taDescricao = new JTextArea();
		taDescricao.setBorder(new LineBorder(new Color(0, 0, 0)));
		taDescricao.setBounds(12, 105, 636, 94);
		pnlDadosGerais.add(taDescricao);
		
		JPanel pnlEndereco = new JPanel();
		pnlEndereco.setLayout(null);
		pnlEndereco.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), 
				"Endere\u00E7o do Estabelecimento", TitledBorder.LEADING, TitledBorder.TOP, 
				null, new Color(51, 51, 51)));
		pnlEndereco.setBounds(10, 285, 660, 142);
		contentPane.add(pnlEndereco);
		
		JLabel lblCidade = new JLabel("Cidade");
		lblCidade.setBounds(12, 22, 80, 16);
		pnlEndereco.add(lblCidade);
		
		edtCidade = new JTextField();
		edtCidade.setColumns(10);
		edtCidade.setBounds(12, 42, 164, 25);
		pnlEndereco.add(edtCidade);
		
		edtBairro = new JTextField();
		edtBairro.setColumns(10);
		edtBairro.setBounds(188, 42, 164, 25);
		pnlEndereco.add(edtBairro);
		
		JLabel lblBairro = new JLabel("Bairro");
		lblBairro.setBounds(188, 22, 80, 16);
		pnlEndereco.add(lblBairro);
		
		edtLogradouro = new JTextField();
		edtLogradouro.setColumns(10);
		edtLogradouro.setBounds(364, 42, 284, 25);
		pnlEndereco.add(edtLogradouro);
		
		JLabel lblLogradouro = new JLabel("Logradouro");
		lblLogradouro.setBounds(364, 22, 80, 16);
		pnlEndereco.add(lblLogradouro);
		
		edtComplemento = new JTextField();
		edtComplemento.setColumns(10);
		edtComplemento.setBounds(12, 99, 636, 25);
		pnlEndereco.add(edtComplemento);
		
		JLabel lblComplemento = new JLabel("Logradouro");
		lblComplemento.setBounds(12, 79, 80, 16);
		pnlEndereco.add(lblComplemento);
		
		JPanel pnlAcoes = new JPanel();
		pnlAcoes.setLayout(null);
		pnlAcoes.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), 
						"A\u00E7\u00F5es", TitledBorder.LEADING, TitledBorder.TOP, 
						null, new Color(51, 51, 51)));
		pnlAcoes.setBounds(426, 439, 240, 65);
		contentPane.add(pnlAcoes);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				restaurante.setNome(edtNome.getText());
				restaurante.setDescricao(taDescricao.getText());
				restaurante.setCategoria(categoriaSelecionada);
				restaurante.getEndereco().setCidade(edtCidade.getText());
				restaurante.getEndereco().setBairro(edtBairro.getText());
				restaurante.getEndereco().setLogradouro(edtLogradouro.getText());
				restaurante.getEndereco().setComplemento(edtComplemento.getText());

				try {

					if (restaurante.isPersistido()) {
						Restaurante restauranteAtualizado = restauranteClient.atualizar(restaurante);						
						preencherFormularioCom(restauranteAtualizado);
					}else {						
						Restaurante restauranteSalvo = restauranteClient.inserir(restaurante);
						preencherFormularioCom(restauranteSalvo);						
					}

					JOptionPane.showMessageDialog(contentPane, "Restaurante salvo com sucesso", 
							"Sucesso na Gravação", JOptionPane.INFORMATION_MESSAGE);

				}catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage(),
							"Erro na Remoção", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnSalvar.setBounds(18, 27, 98, 26);
		pnlAcoes.add(btnSalvar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opcao = JOptionPane.showConfirmDialog(contentPane, 
						"Deseja cancelar a gravação do restaurante?", 
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
