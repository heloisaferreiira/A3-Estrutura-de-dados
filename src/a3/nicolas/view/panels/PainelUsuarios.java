package a3.nicolas.view.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import a3.nicolas.controller.UsuarioController;
import a3.nicolas.model.Usuario;

/*
 * Painel para cadastro e listagem dos usuarios.
 */
public class PainelUsuarios extends JPanel {
    private static final long serialVersionUID = 1L;

    private transient UsuarioController usuarioController;
    private transient Runnable aoAtualizar;

    private JTextField txtNome;
    private JTextField txtSetor;
    private JTextField txtEquipamento;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public PainelUsuarios(UsuarioController usuarioController, Runnable aoAtualizar) {
        this.usuarioController = usuarioController;
        this.aoAtualizar = aoAtualizar;
        montarTela();
    }

    private void montarTela() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JLabel titulo = new JLabel("Usuarios");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        add(criarFormulario(), BorderLayout.WEST);

        modeloTabela = new DefaultTableModel(new String[] { "ID", "Nome", "Setor", "Equipamento" }, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }

    private JPanel criarFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Cadastrar usuario"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        painel.add(new JLabel("Nome:"), gbc);
        gbc.gridy++;
        txtNome = new JTextField(20);
        painel.add(txtNome, gbc);

        gbc.gridy++;
        painel.add(new JLabel("Setor:"), gbc);
        gbc.gridy++;
        txtSetor = new JTextField(20);
        painel.add(txtSetor, gbc);

        gbc.gridy++;
        painel.add(new JLabel("Equipamento:"), gbc);
        gbc.gridy++;
        txtEquipamento = new JTextField(20);
        painel.add(txtEquipamento, gbc);

        gbc.gridy++;
        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(e -> cadastrarUsuario());
        painel.add(btnCadastrar, gbc);

        gbc.gridy++;
        JButton btnRemover = new JButton("Remover selecionado");
        btnRemover.addActionListener(e -> removerSelecionado());
        painel.add(btnRemover, gbc);

        return painel;
    }

    private void cadastrarUsuario() {
        String nome = txtNome.getText().trim();
        String setor = txtSetor.getText().trim();
        String equipamento = txtEquipamento.getText().trim();

        if (nome.isEmpty() || setor.isEmpty() || equipamento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        usuarioController.cadastrarUsuario(nome, setor, equipamento);
        txtNome.setText("");
        txtSetor.setText("");
        txtEquipamento.setText("");
        aoAtualizar.run();
    }

    private void removerSelecionado() {
        int linha = tabela.getSelectedRow();

        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um usuario na tabela.");
            return;
        }

        int id = (Integer) modeloTabela.getValueAt(linha, 0);
        boolean removido = usuarioController.removerUsuario(id);

        if (!removido) {
            JOptionPane.showMessageDialog(this, "Usuario nao encontrado.");
        }

        aoAtualizar.run();
    }

    public void atualizar() {
        modeloTabela.setRowCount(0);
        Usuario[] usuarios = usuarioController.listarUsuarios();

        for (int i = 0; i < usuarios.length; i++) {
            Usuario usuario = usuarios[i];
            modeloTabela.addRow(new Object[] {
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getSetor(),
                    usuario.getEquipamento()
            });
        }
    }
}
