package a3.nicolas.view.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import a3.nicolas.controller.ChamadoController;
import a3.nicolas.model.Chamado;

/*
 * Painel que exibe a Pilha de historico.
 *
 * A ordem exibida e do chamado mais recente para o mais antigo.
 */
public class PainelHistorico extends JPanel {
    private static final long serialVersionUID = 1L;

    private transient ChamadoController chamadoController;
    private DefaultTableModel modeloTabela;

    public PainelHistorico(ChamadoController chamadoController) {
        this.chamadoController = chamadoController;
        montarTela();
    }

    private void montarTela() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JLabel titulo = new JLabel("Historico");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(
                new String[] { "ID", "Descricao", "Fechamento", "Prioridade", "Status", "Usuario", "Data" }, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }

    public void atualizar() {
        modeloTabela.setRowCount(0);
        Chamado[] historico = chamadoController.listarHistorico();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (int i = 0; i < historico.length; i++) {
            Chamado chamado = historico[i];
            modeloTabela.addRow(new Object[] {
                    chamado.getId(),
                    chamado.getDescricao(),
                    chamado.getDescricaoFechamento(),
                    chamado.getPrioridade(),
                    chamado.getStatus(),
                    chamado.getUsuario().getNome(),
                    chamado.getDataAbertura().format(formato)
            });
        }
    }
}
