package a3.nicolas.controller;

import java.time.LocalDateTime;

import a3.nicolas.estruturas.ArvoreBinaria;
import a3.nicolas.estruturas.Fila;
import a3.nicolas.estruturas.Pilha;
import a3.nicolas.model.Chamado;
import a3.nicolas.model.Usuario;
import a3.nicolas.model.enums.Prioridade;
import a3.nicolas.model.enums.StatusChamado;

/*
 * Controller responsavel pelas regras dos chamados.
 *
 * Ele manipula Fila, Pilha e ArvoreBinaria, mas a View nao acessa essas
 * estruturas diretamente.
 */
public class ChamadoController {
    private Fila<Chamado> fila;
    private Pilha<Chamado> historico;
    private ArvoreBinaria arvore;
    private int proximoId;
    private Chamado ultimoAtendido;
    private Chamado chamadoEmAtendimento;

    public ChamadoController() {
        fila = new Fila<Chamado>();
        historico = new Pilha<Chamado>();
        arvore = new ArvoreBinaria();
        proximoId = 1;
    }

    public Chamado abrirChamado(String descricao, Prioridade prioridade, Usuario usuario) {
        Chamado chamado = new Chamado(
                proximoId,
                descricao,
                prioridade,
                StatusChamado.ABERTO,
                usuario,
                LocalDateTime.now());

        fila.enqueue(chamado);
        arvore.inserir(chamado);
        proximoId++;
        return chamado;
    }

    public Chamado atenderProximo() {
        if (chamadoEmAtendimento != null) {
            return chamadoEmAtendimento;
        }

        Chamado chamado = fila.dequeue();

        if (chamado == null) {
            return null;
        }

        chamado.setStatus(StatusChamado.EM_ATENDIMENTO);
        chamadoEmAtendimento = chamado;
        return chamado;
    }

    public boolean fecharChamado(Chamado chamado, String descricaoFechamento) {
        if (chamado == null || chamado != chamadoEmAtendimento
                || chamado.getStatus() != StatusChamado.EM_ATENDIMENTO) {
            return false;
        }

        if (descricaoFechamento == null || descricaoFechamento.trim().isEmpty()) {
            return false;
        }

        chamado.setDescricaoFechamento(descricaoFechamento.trim());
        chamado.setStatus(StatusChamado.FECHADO);
        historico.push(chamado);
        ultimoAtendido = chamado;

        chamadoEmAtendimento = null;

        return true;
    }

    public Chamado[] listarFila() {
        Object[] itens = fila.listar();
        Chamado[] resultado = new Chamado[itens.length];

        for (int i = 0; i < itens.length; i++) {
            resultado[i] = (Chamado) itens[i];
        }

        return resultado;
    }

    public Chamado[] listarHistorico() {
        Object[] itens = historico.listar();
        Chamado[] resultado = new Chamado[itens.length];

        for (int i = 0; i < itens.length; i++) {
            resultado[i] = (Chamado) itens[i];
        }

        return resultado;
    }

    public Chamado buscarPorId(int id) {
        return arvore.buscar(id);
    }

    public Chamado[] listarTodos() {
        Object[] itens = arvore.listarEmOrdem();
        Chamado[] resultado = new Chamado[itens.length];

        for (int i = 0; i < itens.length; i++) {
            resultado[i] = (Chamado) itens[i];
        }

        return resultado;
    }

    public int quantidadeNaFila() {
        return fila.tamanho();
    }

    public int quantidadeNoHistorico() {
        return historico.tamanho();
    }

    public Chamado getUltimoAtendido() {
        return ultimoAtendido;
    }

    public Chamado getChamadoEmAtendimento() {
        return chamadoEmAtendimento;
    }
}
