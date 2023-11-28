package br.com.senai.saep.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

import br.com.senai.saep.entity.Motorista;
import br.com.senai.saep.entity.Transportadora;
import br.com.senai.saep.service.MotoristaService;
import br.com.senai.saep.view.componentes.table.MotoristaTableModel;

@Component
public class ViewListagemMotorista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;	
	private  String nomeTransportadora; 	
	private JTable tabelaMotoristas;
	private MotoristaTableModel modeloTabela;
	
	@Autowired
	@Lazy
	private ViewCadastroDeMotorista viewCadastroMotorista;
	
	@Autowired
	@Lazy
	private ViewLogin viewLogin;
	
	@Autowired
	private Transportadora transportadora;	

    @Autowired
    private MotoristaService motoristaService;
    
	void pegarTransportadora(Transportadora transportadora) {
		Preconditions.checkNotNull(transportadora, "A transportadora não pode ser nula");
		this.nomeTransportadora = transportadora.getNome().toUpperCase();
		this.transportadora = transportadora;
		setTitle(nomeTransportadora);		
		setVisible(true);
		atualizarTabela();
	}
	
    public ViewListagemMotorista() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 454, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        modeloTabela = new MotoristaTableModel();
        tabelaMotoristas = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaMotoristas);
        scrollPane.setBounds(10, 45, 419, 167);
        contentPane.add(scrollPane);

        JButton btnCadastrarMotorista = new JButton("Cadastrar Motorista");
        btnCadastrarMotorista.setBounds(10, 227, 157, 23);
        btnCadastrarMotorista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewCadastroMotorista.pegarTransportadora(transportadora);
                viewCadastroMotorista.setVisible(true);
                dispose();
            }
        });
        contentPane.setLayout(null);
        setLocationRelativeTo(null);
        contentPane.add(btnCadastrarMotorista);

        JButton btnSair = new JButton("Logout");
        btnSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewLogin.setVisible(true);
                dispose();
            }
        });
        btnSair.setBounds(340, 11, 89, 23);
        contentPane.add(btnSair);
        
        JButton btnVisualizar = new JButton("Visualizar");
        btnVisualizar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JOptionPane.showMessageDialog(contentPane, "Funcionalidade não específicada.");
        	}
        });
        btnVisualizar.setBounds(177, 227, 145, 23);
        contentPane.add(btnVisualizar);
        
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int linhaSelecionada = tabelaMotoristas.getSelectedRow();
				MotoristaTableModel model = (MotoristaTableModel) tabelaMotoristas.getModel();
				if(linhaSelecionada >= 0 && !model.isVazio()) {
					int opcao = JOptionPane.showConfirmDialog(contentPane, "Deseja excluir o motorista?",
							"Remoção", JOptionPane.YES_NO_OPTION);
					if(opcao == 0) {
						Motorista motoristaSelecionado = model.getPor(linhaSelecionada);
						try {
							motoristaService.excluirPor(motoristaSelecionado.getId(), transportadora.getId());
							model.removePor(linhaSelecionada);
							JOptionPane.showMessageDialog(contentPane, "Restaurante excluido.");
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(contentPane, ex.getMessage());
						}
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma linha para remoção.");
				}
        	}
        });
        btnExcluir.setBounds(326, 227, 103, 23);
        contentPane.add(btnExcluir);
    }

    private void atualizarTabela() {
        modeloTabela = new MotoristaTableModel(obterDadosFicticios());
        tabelaMotoristas.setModel(modeloTabela);
    }

    private List<Motorista> obterDadosFicticios() {
        List<Motorista> motoristas = motoristaService.listarPor(transportadora.getId());        

        return motoristas;
    }
    
}

