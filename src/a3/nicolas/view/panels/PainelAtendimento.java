package a3.nicolas.view.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


import a3.nicolas.controller.ChamadoController;
import a3.nicolas.model.Chamado;

/*
 * Painel que retira o proximo chamado da fila e permite fecha-lo.
 */
public class PainelAtendimento extends JPanel {
    private static final long serialVersionUID = 1L;

    private transient ChamadoController chamadoController;
    private transient Runnable aoAtualizar;
    private JTextArea areaDados;
    private JTextArea txtDescricaoFechamento;
    private transient Chamado chamadoAtual;

    public PainelAtendimento(ChamadoController chamadoController, Runnable aoAtualizar) {
        this.chamadoController = chamadoController;
        this.aoAtualizar = aoAtualizar;
        montarTela();
    }

    private void montarTela() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JLabel titulo = new JLabel("Atendimento");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        areaDados = new JTextArea();
        areaDados.setEditable(false);
        areaDados.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(areaDados), BorderLayout.CENTER);

        JPanel painelFechamento = new JPanel(new BorderLayout(8, 8));
        painelFechamento.setBorder(BorderFactory.createTitledBorder("Descricao do fechamento"));

        txtDescricaoFechamento = new JTextArea(4, 30);
        txtDescricaoFechamento.setLineWrap(true);
        txtDescricaoFechamento.setWrapStyleWord(true);
        painelFechamento.add(new JScrollPane(txtDescricaoFechamento), BorderLayout.CENTER);

        JPanel botoes = new JPanel(new GridLayout(1, 2, 12, 12));
        JButton btnAtender = new JButton("Atender proximo");
        JButton btnFechar = new JButton("Fechar chamado");

        btnAtender.addActionListener(e -> atenderProximo());
        btnFechar.addActionListener(e -> fecharChamado());

        botoes.add(btnAtender);
        botoes.add(btnFechar);
        painelFechamento.add(botoes, BorderLayout.SOUTH);
        add(painelFechamento, BorderLayout.SOUTH);
    }

    private void atenderProximo() {
        if (chamadoAtual != null) {
            JOptionPane.showMessageDialog(this, "Feche o chamado atual antes de atender outro.");
            return;
        }

        chamadoAtual = chamadoController.atenderProximo();

        if (chamadoAtual == null) {
            areaDados.setText("");
            txtDescricaoFechamento.setText("");
            JOptionPane.showMessageDialog(this, "Nao ha chamados na fila.");
            return;
        }

        areaDados.setText(formatarChamado(chamadoAtual));
        txtDescricaoFechamento.setText("");
        aoAtualizar.run();
    }

    private void fecharChamado() {
        if (chamadoAtual == null) {
            JOptionPane.showMessageDialog(this, "Atenda um chamado antes de fechar.");
            return;
        }

        String descricaoFechamento = txtDescricaoFechamento.getText().trim();

        if (descricaoFechamento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a descricao do fechamento do chamado.");
            return;
        }

        boolean fechado = chamadoController.fecharChamado(chamadoAtual, descricaoFechamento);

        if (!fechado) {
            JOptionPane.showMessageDialog(this, "Nao foi possivel fechar este chamado.");
            return;
        }

        areaDados.setText(formatarChamado(chamadoAtual));
        JOptionPane.showMessageDialog(this, "Chamado #" + chamadoAtual.getId() + " fechado com sucesso!");
        txtDescricaoFechamento.setText("");
        chamadoAtual = null;
        aoAtualizar.run();
    }

    private String formatarChamado(Chamado chamado) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return "ID: " + chamado.getId()
                + "\nStatus: " + chamado.getStatus()
                + "\nPrioridade: " + chamado.getPrioridade()
                + "\nUsuario: " + chamado.getUsuario().getNome()
                + "\nSetor: " + chamado.getUsuario().getSetor()
                + "\nEquipamento: " + chamado.getUsuario().getEquipamento()
                + "\nData de abertura: " + chamado.getDataAbertura().format(formato)
                + "\n\nDescricao de abertura:\n" + chamado.getDescricao()
                + "\n\nDescricao de fechamento:\n" + textoFechamento(chamado);
    }

    private String textoFechamento(Chamado chamado) {
        if (chamado.getDescricaoFechamento() == null || chamado.getDescricaoFechamento().isEmpty()) {
            return "Ainda nao informado.";
        }

        return chamado.getDescricaoFechamento();
    }
}
