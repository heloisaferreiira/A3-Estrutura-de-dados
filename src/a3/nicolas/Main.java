package a3.nicolas;

import javax.swing.SwingUtilities;

import a3.nicolas.controller.ChamadoController;
import a3.nicolas.controller.UsuarioController;
import a3.nicolas.model.enums.Prioridade;
import a3.nicolas.view.MainFrame;

/*
 * Classe principal do sistema.
 *
 * Execute este arquivo para abrir a interface grafica.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame tela = new MainFrame();
                UsuarioController usuarioController = tela.getUsuarioController();
                ChamadoController chamadoController = tela.getChamadoController();

                usuarioController.cadastrarUsuario("Ana Lima", "RH", "Notebook Dell");
                usuarioController.cadastrarUsuario("Carlos Souza", "TI", "Desktop HP");
                usuarioController.cadastrarUsuario("Mariana Costa", "Financeiro", "Impressora Canon");

                chamadoController.abrirChamado(
                        "Computador nao liga",
                        Prioridade.ALTA,
                        usuarioController.buscarPorId(1));
                chamadoController.abrirChamado(
                        "Email nao envia anexo",
                        Prioridade.MEDIA,
                        usuarioController.buscarPorId(2));
                chamadoController.abrirChamado(
                        "Impressora offline",
                        Prioridade.BAIXA,
                        usuarioController.buscarPorId(3));

                tela.atualizarTodos();
                tela.setVisible(true);
            }
        });
    }
}
