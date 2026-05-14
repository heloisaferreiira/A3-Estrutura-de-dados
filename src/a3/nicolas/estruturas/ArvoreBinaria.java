package a3.nicolas.estruturas;

import a3.nicolas.model.Chamado;

/*
 * Arvore binaria de busca para chamados.
 *
 * Cada chamado e organizado pelo id:
 * ids menores ficam a esquerda, ids maiores ficam a direita.
 */
public class ArvoreBinaria {
    private No raiz;
    private int tamanho;

    private static class No {
        private Chamado chamado;
        private No esquerda;
        private No direita;

        private No(Chamado chamado) {
            this.chamado = chamado;
        }
    }

    public void inserir(Chamado chamado) {
        raiz = inserirRecursivo(raiz, chamado);
    }

    private No inserirRecursivo(No atual, Chamado chamado) {
        if (atual == null) {
            tamanho++;
            return new No(chamado);
        }

        if (chamado.getId() < atual.chamado.getId()) {
            atual.esquerda = inserirRecursivo(atual.esquerda, chamado);
        } else if (chamado.getId() > atual.chamado.getId()) {
            atual.direita = inserirRecursivo(atual.direita, chamado);
        }

        return atual;
    }

    public Chamado buscar(int id) {
        No atual = raiz;

        while (atual != null) {
            if (id == atual.chamado.getId()) {
                return atual.chamado;
            }

            if (id < atual.chamado.getId()) {
                atual = atual.esquerda;
            } else {
                atual = atual.direita;
            }
        }

        return null;
    }

    public Object[] listarEmOrdem() {
        Object[] itens = new Object[tamanho];
        preencherEmOrdem(raiz, itens, new int[] { 0 });
        return itens;
    }

    private void preencherEmOrdem(No atual, Object[] itens, int[] indice) {
        if (atual == null) {
            return;
        }

        preencherEmOrdem(atual.esquerda, itens, indice);
        itens[indice[0]] = atual.chamado;
        indice[0]++;
        preencherEmOrdem(atual.direita, itens, indice);
    }

    public int tamanho() {
        return tamanho;
    }
}
