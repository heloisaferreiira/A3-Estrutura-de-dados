package a3.nicolas.model;

/*
 * Classe de modelo que representa um usuario do sistema.
 *
 * No MVC, o model guarda apenas os dados. Por isso esta classe nao
 * conhece telas, botoes, tabelas ou estruturas de dados.
 */
public class Usuario {
    private int id;
    private String nome;
    private String setor;
    private String equipamento;

    public Usuario(int id, String nome, String setor, String equipamento) {
        this.id = id;
        this.nome = nome;
        this.setor = setor;
        this.equipamento = equipamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    /*
     * O equals compara usuarios pelo id. Isso ajuda a ListaLigada a remover
     * exatamente o usuario encontrado pelo controller.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Usuario)) {
            return false;
        }

        Usuario outro = (Usuario) obj;
        return id == outro.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    /*
     * O JComboBox usa toString para mostrar o texto de cada usuario.
     */
    @Override
    public String toString() {
        return id + " - " + nome;
    }
}
