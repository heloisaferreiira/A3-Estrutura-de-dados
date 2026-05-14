package a3.nicolas.estruturas;

/*
 * Fila generica implementada manualmente com nos.
 *
 * FIFO significa First In, First Out:
 * o primeiro elemento que entra e o primeiro que sai.
 */
public class Fila<T> {
    private No<T> inicio;
    private No<T> fim;
    private int tamanho;

    private static class No<T> {
        private T valor;
        private No<T> proximo;

        private No(T valor) {
            this.valor = valor;
        }
    }

    public void enqueue(T valor) {
        No<T> novo = new No<T>(valor);

        if (isEmpty()) {
            inicio = novo;
            fim = novo;
        } else {
            fim.proximo = novo;
            fim = novo;
        }

        tamanho++;
    }

    public T dequeue() {
        if (isEmpty()) {
            return null;
        }

        T valor = inicio.valor;
        inicio = inicio.proximo;
        tamanho--;

        if (inicio == null) {
            fim = null;
        }

        return valor;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }

        return inicio.valor;
    }

    public boolean isEmpty() {
        return tamanho == 0;
    }

    public int tamanho() {
        return tamanho;
    }

    /*
     * Retorna os elementos na ordem da fila, do primeiro ao ultimo.
     * Object[] evita usar colecoes prontas do Java.
     */
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
}
