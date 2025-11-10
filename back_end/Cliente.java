public class Cliente {
    private String nome;
    private String telefone;
    private String enderecoEntrega;
    private String complemento;

    public Cliente(String nome, String telefone, String enderecoEntrega, String complemento) {
        this.nome = nome;
        this.telefone = telefone;
        this.enderecoEntrega = enderecoEntrega;
        this.complemento = complemento;
    }

    public String toString(){
        String str;
        str = "Nome: " + nome;
        str += "\nTelefone: " + telefone;
        str += "\nEndereço: " + enderecoEntrega;
        str += "\nComplemento: " + complemento;
        return str;
    }
    public String getNome(){
        return nome;
    }

    public String getComplemento() {
        return complemento;
    }
    
    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public String getTelefone() {
        return telefone;
    }
}
