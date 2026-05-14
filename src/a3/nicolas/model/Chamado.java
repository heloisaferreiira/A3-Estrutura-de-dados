package a3.nicolas.model;

import java.time.LocalDateTime;

import a3.nicolas.model.enums.Prioridade;
import a3.nicolas.model.enums.StatusChamado;

/*
 * Classe de modelo que representa um chamado de suporte tecnico.
 *
 * Ela guarda os dados principais do chamado e nao possui codigo de tela.
 */
public class Chamado {
    private int id;
    private String descricao;
    private Prioridade prioridade;
    private StatusChamado status;
    private Usuario usuario;
    private LocalDateTime dataAbertura;
    private String descricaoFechamento;

    public Chamado(int id, String descricao, Prioridade prioridade, StatusChamado status,
            Usuario usuario, LocalDateTime dataAbertura) {
        this.id = id;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.status = status;
        this.usuario = usuario;
        this.dataAbertura = dataAbertura;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public StatusChamado getStatus() {
        return status;
    }

    public void setStatus(StatusChamado status) {
        this.status = status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getDescricaoFechamento() {
        return descricaoFechamento;
    }

    public void setDescricaoFechamento(String descricaoFechamento) {
        this.descricaoFechamento = descricaoFechamento;
    }

    @Override
    public String toString() {
        return "Chamado #" + id + " - " + descricao;
    }
}
