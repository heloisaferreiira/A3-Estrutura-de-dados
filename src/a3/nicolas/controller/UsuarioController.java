package a3.nicolas.controller;

import a3.nicolas.estruturas.ListaLigada;
import a3.nicolas.model.Usuario;

/*
 * Controller responsavel pelas regras de usuario.
 *
 * A View chama esta classe, e esta classe manipula a ListaLigada.
 */
public class UsuarioController {
    private ListaLigada<Usuario> usuarios;
    private int proximoId;

    public UsuarioController() {
        usuarios = new ListaLigada<Usuario>();
        proximoId = 1;
    }

    public Usuario cadastrarUsuario(String nome, String setor, String equipamento) {
        Usuario usuario = new Usuario(proximoId, nome, setor, equipamento);
        usuarios.adicionar(usuario);
        proximoId++;
        return usuario;
    }

    public Usuario[] listarUsuarios() {
        Object[] itens = usuarios.listar();
        Usuario[] resultado = new Usuario[itens.length];

        for (int i = 0; i < itens.length; i++) {
            resultado[i] = (Usuario) itens[i];
        }

        return resultado;
    }

    public Usuario buscarPorId(int id) {
        Usuario[] todos = listarUsuarios();

        for (int i = 0; i < todos.length; i++) {
            if (todos[i].getId() == id) {
                return todos[i];
            }
        }

        return null;
    }

    public boolean removerUsuario(int id) {
        Usuario usuario = buscarPorId(id);

        if (usuario == null) {
            return false;
        }

        return usuarios.remover(usuario);
    }

    public int quantidadeUsuarios() {
        return usuarios.tamanho();
    }
}
