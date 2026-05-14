package a3.nicolas.estruturas;

/*
 * Pilha generica implementada manualmente com nos.
 *
 * LIFO significa Last In, First Out:
 * o ultimo elemento que entra e o primeiro que sai.
 */
public class Pilha<T> {
    private No<T> topo;
    private int tamanho;

    private static class No<T> {
        private T valor;
        private No<T> proximo;

        private No(T valor) {
            this.valor = valor;
        }
    }

    public void push(T valor) {
        No<T> novo = new No<T>(valor);
        novo.proximo = topo;
        topo = novo;
        tamanho++;
    }

    public T pop() {
        if (isEmpty()) {
            return null;
        }

        T valor = topo.valor;
        topo = topo.proximo;
        tamanho--;
        return valor;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }

        return topo.valor;
    }

    public boolean isEmpty() {
        return tamanho == 0;
    }

    public int tamanho() {
        return tamanho;
    }

    /*
     * Retorna os elementos do topo para a base, ou seja,
     * do chamado mais recente para o mais antigo no historico.
     */
    public Object[] listar() {
        Object[] itens = new Object[tamanho];
        No<T> atual = topo;
        int indice = 0;

        while (atual != null) {
            itens[indice] = atual.valor;
            atual = atual.proximo;
            indice++;
        }

        return itens;
    }
}
