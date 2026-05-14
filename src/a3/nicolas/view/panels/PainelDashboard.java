package a3.nicolas.view.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import a3.nicolas.controller.ChamadoController;
import a3.nicolas.model.Chamado;

/*
 * Painel inicial com resumo rapido do sistema.
 */
public class PainelDashboard extends JPanel {
    private static final long serialVersionUID = 1L;

    private transient ChamadoController chamadoController;
    private JLabel lblFila;
    private JLabel lblHistorico;
    private JLabel lblUltimo;

    public PainelDashboard(ChamadoController chamadoController) {
        this.chamadoController = chamadoController;
        montarTela();
    }

    private void montarTela() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JLabel titulo = new JLabel("Dashboard");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(1, 3, 16, 16));
        lblFila = criarCard("Chamados na fila", "0");
        lblHistorico = criarCard("Chamados no historico", "0");
        lblUltimo = criarCard("Ultimo atendido", "Nenhum");

        cards.add(lblFila);
        cards.add(lblHistorico);
        cards.add(lblUltimo);
        add(cards, BorderLayout.CENTER);
    }

    private JLabel criarCard(String titulo, String valor) {
        JLabel label = new JLabel("<html><center>" + titulo + "<br><br><b>" + valor + "</b></center></html>");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        return label;
    }

    public void atualizar() {
        lblFila.setText("<html><center>Chamados na fila<br><br><b>"
                + chamadoController.quantidadeNaFila() + "</b></center></html>");

        lblHistorico.setText("<html><center>Chamados no historico<br><br><b>"
                + chamadoController.quantidadeNoHistorico() + "</b></center></html>");

        Chamado ultimo = chamadoController.getUltimoAtendido();
        String textoUltimo = "Nenhum";

        if (ultimo != null) {
            textoUltimo = "#" + ultimo.getId() + " - " + ultimo.getStatus();
        }

        lblUltimo.setText("<html><center>Ultimo atendido<br><br><b>"
                + textoUltimo + "</b></center></html>");
    }
}
