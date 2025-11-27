package com.dompedroburguer.model;

public class Produto {
    private int id;
    private String nome;
    private String imagem; //imagem está sendo guardada como link no banco de dados.
    private String descricao;
    private Double valor;

    public Produto(){

    }
    public Produto (String nome, String imagem, String descricao, Double valor){
        this.nome = nome;
        this.imagem = imagem;
        this.descricao = descricao;
        this.valor = valor;
    }
    
    public Produto (int id, String nome, String imagem, String descricao, Double valor){
        this(nome, imagem, descricao, valor);
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getImagem() {
        return imagem;
    }
    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Double getValor() {
        return valor;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }
}
