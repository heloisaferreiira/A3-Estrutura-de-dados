package a3.nicolas.view.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import a3.nicolas.controller.ChamadoController;
import a3.nicolas.model.Chamado;

/*
 * Painel de busca por id usando a ArvoreBinaria.
 */
public class PainelBusca extends JPanel {
    private static final long serialVersionUID = 1L;

    private transient ChamadoController chamadoController;
    private JTextField txtId;
    private JTextArea areaResultado;
    private DefaultTableModel modeloTabela;

    public PainelBusca(ChamadoController chamadoController) {
        this.chamadoController = chamadoController;
        montarTela();
    }

    private void montarTela() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JLabel titulo = new JLabel("Busca");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        JPanel topo = criarBusca();
        add(topo, BorderLayout.WEST);

        modeloTabela = new DefaultTableModel(
                new String[] { "ID", "Descricao", "Fechamento", "Prioridade", "Status", "Usuario", "Data" }, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JLabel lblTabela = new JLabel("Todos os chamados ordenados por ID (Arvore Binaria)");
        lblTabela.setFont(new Font("Arial", Font.ITALIC, 12));

        JTable tabela = new JTable(modeloTabela);
        JPanel painelTabela = new JPanel(new BorderLayout(6, 6));
        painelTabela.add(lblTabela, BorderLayout.NORTH);
        painelTabela.add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(painelTabela, BorderLayout.CENTER);
    }

    private JPanel criarBusca() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Buscar por ID"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        painel.add(new JLabel("ID do chamado:"), gbc);
        gbc.gridy++;
        txtId = new JTextField(16);
        painel.add(txtId, gbc);

        gbc.gridy++;
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscar());
        painel.add(btnBuscar, gbc);

        gbc.gridy++;
        areaResultado = new JTextArea(10, 24);
        areaResultado.setEditable(false);
        areaResultado.setLineWrap(true);
        areaResultado.setWrapStyleWord(true);
        painel.add(new JScrollPane(areaResultado), gbc);

        return painel;
    }

    private void buscar() {
        int id;

        try {
            id = Integer.parseInt(txtId.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Informe um ID numerico.");
            return;
        }

        Chamado chamado = chamadoController.buscarPorId(id);

        if (chamado == null) {
            areaResultado.setText("Chamado nao encontrado.");
        } else {
            areaResultado.setText(formatarChamado(chamado));
        }
    }

    public void atualizar() {
        modeloTabela.setRowCount(0);
        Chamado[] chamados = chamadoController.listarTodos();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (int i = 0; i < chamados.length; i++) {
            Chamado chamado = chamados[i];
            modeloTabela.addRow(new Object[] {
                    chamado.getId(),
                    chamado.getDescricao(),
                    textoFechamento(chamado),
                    chamado.getPrioridade(),
                    chamado.getStatus(),
                    chamado.getUsuario().getNome(),
                    chamado.getDataAbertura().format(formato)
            });
        }
    }

    private String formatarChamado(Chamado chamado) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return "ID: " + chamado.getId()
                + "\nStatus: " + chamado.getStatus()
                + "\nPrioridade: " + chamado.getPrioridade()
                + "\nUsuario: " + chamado.getUsuario().getNome()
                + "\nData: " + chamado.getDataAbertura().format(formato)
                + "\nDescricao de abertura: " + chamado.getDescricao()
                + "\nDescricao de fechamento: " + textoFechamento(chamado);
    }

    private String textoFechamento(Chamado chamado) {
        if (chamado.getDescricaoFechamento() == null || chamado.getDescricaoFechamento().isEmpty()) {
            return "Ainda nao informado.";
        }

        return chamado.getDescricaoFechamento();
    }
}
