package com.dompedroburguer.model;

public class FormasPagamento{
    private int id;
    private String descricao;
    public FormasPagamento(int id, String descricao){
        this.id = id;
        this.descricao = descricao;
    }
    public FormasPagamento(){
        
    }
    public int getId() {
        return id;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}