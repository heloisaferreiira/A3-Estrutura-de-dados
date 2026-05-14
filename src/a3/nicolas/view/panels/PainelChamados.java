package a3.nicolas.view.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import a3.nicolas.controller.ChamadoController;
import a3.nicolas.controller.UsuarioController;
import a3.nicolas.model.Chamado;
import a3.nicolas.model.Usuario;
import a3.nicolas.model.enums.Prioridade;

/*
 * Painel para abertura de chamados e visualizacao da fila atual.
 */
public class PainelChamados extends JPanel {
    private static final long serialVersionUID = 1L;

    private transient ChamadoController chamadoController;
    private transient UsuarioController usuarioController;
    private transient Runnable aoAtualizar;

    private JTextArea txtDescricao;
    private JComboBox<Prioridade> comboPrioridade;
    private JComboBox<Usuario> comboUsuario;
    private DefaultTableModel modeloTabela;

    public PainelChamados(ChamadoController chamadoController, UsuarioController usuarioController,
            Runnable aoAtualizar) {
        this.chamadoController = chamadoController;
        this.usuarioController = usuarioController;
        this.aoAtualizar = aoAtualizar;
        montarTela();
    }

    private void montarTela() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JLabel titulo = new JLabel("Chamados");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        JPanel formulario = criarFormulario();
        add(formulario, BorderLayout.WEST);

        modeloTabela = new DefaultTableModel(
                new String[] { "ID", "Descricao", "Prioridade", "Status", "Usuario", "Data" }, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }

    private JPanel criarFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Abrir chamado"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        painel.add(new JLabel("Usuario:"), gbc);
        gbc.gridy++;
        comboUsuario = new JComboBox<Usuario>();
        painel.add(comboUsuario, gbc);

        gbc.gridy++;
        painel.add(new JLabel("Prioridade:"), gbc);
        gbc.gridy++;
        comboPrioridade = new JComboBox<Prioridade>(Prioridade.values());
        painel.add(comboPrioridade, gbc);

        gbc.gridy++;
        painel.add(new JLabel("Descricao:"), gbc);
        gbc.gridy++;
        txtDescricao = new JTextArea(5, 24);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        painel.add(new JScrollPane(txtDescricao), gbc);

        gbc.gridy++;
        JButton btnAbrir = new JButton("Abrir chamado");
        btnAbrir.addActionListener(e -> abrirChamado());
        painel.add(btnAbrir, gbc);

        return painel;
    }

    private void abrirChamado() {
        Usuario usuario = (Usuario) comboUsuario.getSelectedItem();
        Prioridade prioridade = (Prioridade) comboPrioridade.getSelectedItem();
        String descricao = txtDescricao.getText().trim();

        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Cadastre um usuario antes de abrir chamados.");
            return;
        }

        if (descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a descricao do problema.");
            return;
        }

        chamadoController.abrirChamado(descricao, prioridade, usuario);
        txtDescricao.setText("");
        aoAtualizar.run();
    }

    public void atualizar() {
        Usuario selecionado = (Usuario) comboUsuario.getSelectedItem();
        comboUsuario.removeAllItems();

        Usuario[] usuarios = usuarioController.listarUsuarios();
        for (int i = 0; i < usuarios.length; i++) {
            comboUsuario.addItem(usuarios[i]);
            if (usuarios[i].equals(selecionado)) {
                comboUsuario.setSelectedItem(usuarios[i]);
            }
        }

        modeloTabela.setRowCount(0);
        Chamado[] fila = chamadoController.listarFila();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (int i = 0; i < fila.length; i++) {
            Chamado chamado = fila[i];
            modeloTabela.addRow(new Object[] {
                    chamado.getId(),
                    chamado.getDescricao(),
                    chamado.getPrioridade(),
                    chamado.getStatus(),
                    chamado.getUsuario().getNome(),
                    chamado.getDataAbertura().format(formato)
            });
        }
    }
}
