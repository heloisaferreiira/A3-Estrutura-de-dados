package a3.nicolas.estruturas;

/*
 * Lista ligada generica implementada manualmente com nos.
 *
 * Cada no aponta para o proximo. A lista cresce conforme os dados sao
 * adicionados, sem usar ArrayList ou LinkedList do Java.
 */
public class ListaLigada<T> {
    private No<T> inicio;
    private int tamanho;

    private static class No<T> {
        private T valor;
        private No<T> proximo;

        private No(T valor) {
            this.valor = valor;
        }
    }

    public void adicionar(T valor) {
        No<T> novo = new No<T>(valor);

        if (inicio == null) {
            inicio = novo;
        } else {
            No<T> atual = inicio;

            while (atual.proximo != null) {
                atual = atual.proximo;
            }

            atual.proximo = novo;
        }

        tamanho++;
    }

    public boolean remover(T valor) {
        if (inicio == null) {
            return false;
        }

        if (inicio.valor.equals(valor)) {
            inicio = inicio.proximo;
            tamanho--;
            return true;
        }

        No<T> anterior = inicio;
        No<T> atual = inicio.proximo;

        while (atual != null) {
            if (atual.valor.equals(valor)) {
                anterior.proximo = atual.proximo;
                tamanho--;
                return true;
            }

            anterior = atual;
            atual = atual.proximo;
        }

        return false;
    }

    public T buscar(T valor) {
        No<T> atual = inicio;

        while (atual != null) {
            if (atual.valor.equals(valor)) {
                return atual.valor;
            }

            atual = atual.proximo;
        }

        return null;
    }

    public Object[] listar() {
        Object[] itens = new Object[tamanho];
        No<T> atual = inicio;
        int indice = 0;

        while (atual != null) {
            itens[indice] = atual.valor;
            atual = atual.proximo;
            indice++;
        }

        return itens;
    }

    public int tamanho() {
        return tamanho;
    }
}
