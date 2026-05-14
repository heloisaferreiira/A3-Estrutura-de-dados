package a3.nicolas.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import a3.nicolas.controller.ChamadoController;
import a3.nicolas.controller.UsuarioController;
import a3.nicolas.view.panels.PainelAtendimento;
import a3.nicolas.view.panels.PainelBusca;
import a3.nicolas.view.panels.PainelChamados;
import a3.nicolas.view.panels.PainelDashboard;
import a3.nicolas.view.panels.PainelHistorico;
import a3.nicolas.view.panels.PainelUsuarios;

/*
 * JFrame principal da aplicacao.
 *
 * Ele possui um menu lateral e uma area central que troca os paineis usando
 * CardLayout.
 */
public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private CardLayout cardLayout;
    private JPanel painelConteudo;

    private transient UsuarioController usuarioController;
    private transient ChamadoController chamadoController;

    private PainelDashboard painelDashboard;
    private PainelChamados painelChamados;
    private PainelAtendimento painelAtendimento;
    private PainelHistorico painelHistorico;
    private PainelUsuarios painelUsuarios;
    private PainelBusca painelBusca;

    public MainFrame() {
        usuarioController = new UsuarioController();
        chamadoController = new ChamadoController();

        configurarJanela();
        montarMenuLateral();
        montarConteudo();
        atualizarTodos();
    }

    private void configurarJanela() {
        setTitle("Sistema de Suporte Tecnico - A3 Nicolas");
        setSize(1000, 650);
        setMinimumSize(new Dimension(900, 560));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void montarMenuLateral() {
        JPanel menu = new JPanel();
        menu.setPreferredSize(new Dimension(180, 0));
        menu.setBackground(new Color(35, 45, 63));
        menu.setLayout(new java.awt.GridLayout(7, 1, 8, 8));
        menu.setBorder(BorderFactory.createEmptyBorder(16, 12, 16, 12));

        JLabel titulo = new JLabel("Suporte TI");
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        menu.add(titulo);
        adicionarBotaoMenu(menu, "Dashboard", "dashboard");
        adicionarBotaoMenu(menu, "Chamados", "chamados");
        adicionarBotaoMenu(menu, "Atender", "atendimento");
        adicionarBotaoMenu(menu, "Historico", "historico");
        adicionarBotaoMenu(menu, "Usuarios", "usuarios");
        adicionarBotaoMenu(menu, "Busca", "busca");

        add(menu, BorderLayout.WEST);
    }

    private void adicionarBotaoMenu(JPanel menu, String texto, final String card) {
        JButton botao = new JButton(texto);
        botao.setFocusPainted(false);
        botao.setBackground(new Color(55, 70, 100));
        botao.setForeground(Color.WHITE);
        botao.setBorderPainted(false);
        botao.setOpaque(true);
        botao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        botao.addActionListener(e -> {
            cardLayout.show(painelConteudo, card);
            atualizarTodos();
        });

        menu.add(botao);
    }

    private void montarConteudo() {
        cardLayout = new CardLayout();
        painelConteudo = new JPanel(cardLayout);

        painelDashboard = new PainelDashboard(chamadoController);
        painelChamados = new PainelChamados(chamadoController, usuarioController, this::atualizarTodos);
        painelAtendimento = new PainelAtendimento(chamadoController, this::atualizarTodos);
        painelHistorico = new PainelHistorico(chamadoController);
        painelUsuarios = new PainelUsuarios(usuarioController, this::atualizarTodos);
        painelBusca = new PainelBusca(chamadoController);

        painelConteudo.add(painelDashboard, "dashboard");
        painelConteudo.add(painelChamados, "chamados");
        painelConteudo.add(painelAtendimento, "atendimento");
        painelConteudo.add(painelHistorico, "historico");
        painelConteudo.add(painelUsuarios, "usuarios");
        painelConteudo.add(painelBusca, "busca");

        add(painelConteudo, BorderLayout.CENTER);
    }

    public void atualizarTodos() {
        if (painelDashboard != null) {
            painelDashboard.atualizar();
            painelChamados.atualizar();
            painelHistorico.atualizar();
            painelUsuarios.atualizar();
            painelBusca.atualizar();
        }
    }

    public UsuarioController getUsuarioController() {
        return usuarioController;
    }

    public ChamadoController getChamadoController() {
        return chamadoController;
    }
}
