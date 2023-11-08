package br.com.senai.cardapiosmktplaceview.view.categoria;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.senai.cardapiosmktplaceview.client.CategoriaClient;
import br.com.senai.cardapiosmktplaceview.dto.Categoria;
import br.com.senai.cardapiosmktplaceview.enums.Status;
import br.com.senai.cardapiosmktplaceview.enums.TipoDeCategoria;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Component
public class ViewCadastroCategoria extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private String tokenDeAcesso;
	
	private JComboBox<String> cbTipo;
	
	private JComboBox<String> cbStatus;
	
	private JTextField edtNome;
	
	private JLabel lblStatus;
	
	private Categoria categoria;
	
	@Autowired
	private ViewListagemCategoria viewListagem;
	
	@Autowired
	private CategoriaClient categoriaClient;
	
	@PostConstruct
	private void inicializar() {		
		this.carregarOpcoesNosCombos();	
	}
	
	public void colocarEmModoDeInsercao(
			@NotBlank(message = "O token de acesso é obrigatório")
			String tokenDeAcesso) {
		this.setVisible(true);
		this.tokenDeAcesso = tokenDeAcesso;
		this.categoriaClient.setTokenDeAcesso(tokenDeAcesso);
		this.mostrarStatus(false);
		this.limparCampos();
	}
	
	public void colocarEmModoDeEdicao(
			@NotBlank(message = "O token de acesso é obrigatório")
			String tokenDeAcesso,
			@NotNull(message = "A categoria não pode ser nula")
			Categoria categoria) {
		this.setVisible(true);
		this.tokenDeAcesso = tokenDeAcesso;
		this.categoriaClient.setTokenDeAcesso(tokenDeAcesso);
		this.mostrarStatus(true);
		this.preencherFormularioCom(categoria);
	}
	
	private void mostrarStatus(boolean isMostrar) {
		this.lblStatus.setVisible(isMostrar);
		this.cbStatus.setVisible(isMostrar);
	}
	
	private void preencherFormularioCom(Categoria categoria) {
		this.categoria = categoria;
		this.edtNome.setText(categoria.getNome());
		this.cbTipo.setSelectedIndex(categoria.getTipo().ordinal() + 1);
		this.cbStatus.setSelectedIndex(categoria.getStatus().ordinal() + 1);
	}
	
	private void limparCampos() {
		this.categoria = new Categoria();
		this.edtNome.setText("");
		this.cbTipo.setSelectedIndex(0);
		this.cbStatus.setSelectedIndex(0);
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

	public ViewCadastroCategoria() {
		setTitle("Gerenciar Categorias - Inserção/Alteração");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 750, 266);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pnlDadosGerais = new JPanel();
		pnlDadosGerais.setBorder(new TitledBorder(null, "Dados Gerais", 
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlDadosGerais.setBounds(10, 50, 714, 93);
		contentPane.add(pnlDadosGerais);
		pnlDadosGerais.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nome");
		lblNewLabel.setBounds(12, 23, 55, 16);
		pnlDadosGerais.add(lblNewLabel);
		
		edtNome = new JTextField();
		edtNome.setBounds(12, 46, 418, 25);
		pnlDadosGerais.add(edtNome);
		edtNome.setColumns(10);
		
		cbTipo = new JComboBox<String>();
		cbTipo.setBounds(442, 46, 123, 25);
		pnlDadosGerais.add(cbTipo);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(440, 26, 55, 16);
		pnlDadosGerais.add(lblTipo);
		
		cbStatus = new JComboBox<String>();
		cbStatus.setBounds(579, 46, 123, 25);
		pnlDadosGerais.add(cbStatus);
		
		lblStatus = new JLabel("Status");
		lblStatus.setBounds(577, 26, 55, 16);
		pnlDadosGerais.add(lblStatus);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewListagem.mostrarTela(tokenDeAcesso);
				dispose();
			}
		});
		btnPesquisar.setBounds(626, 12, 98, 26);
		contentPane.add(btnPesquisar);
		
		JPanel pnlAcoes = new JPanel();
		pnlAcoes.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), 
				"A\u00E7\u00F5es", TitledBorder.LEADING, TitledBorder.TOP, 
				null, new Color(51, 51, 51)));
		pnlAcoes.setBounds(482, 155, 240, 65);
		contentPane.add(pnlAcoes);
		pnlAcoes.setLayout(null);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				categoria.setNome(edtNome.getText());
				categoria.setTipo(getTipoSelecionado());				

				try {

					if (categoria.isPersistida()) {
						categoria.setStatus(getStatusSelecionado());
						Categoria categoriaAtualizada = categoriaClient.atualizar(categoria);
						preencherFormularioCom(categoriaAtualizada);
					}else {
						Categoria categoriaSalva = categoriaClient.inserir(categoria);
						preencherFormularioCom(categoriaSalva);
						mostrarStatus(true);
					}

					JOptionPane.showMessageDialog(contentPane, "Categoria salva com sucesso", 
							"Sucesso na Gravação", JOptionPane.INFORMATION_MESSAGE);

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
						"Deseja cancelar a gravação da categoria?", 
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
