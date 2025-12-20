package com.dompedroburguer.model;

public class CheckoutCardapio {
    private Checkout ck;
    private Produto produto;
    private String obs;
    private int qtd;
    private double valor;

    public CheckoutCardapio (Checkout ck, Produto produto, String obs, int qtd, double valor){
        this.ck = ck;
        this.produto = produto;
        this.obs = obs;
        this.qtd = qtd;
        this.valor = valor;
    }
    public CheckoutCardapio (Produto produto, String obs, int qtd, double valor){
        this.produto = produto;
        this.obs = obs;
        this.qtd = qtd;
        this.valor = valor;
    }
    public Checkout getCk() {
        return ck;
    }
    public void setCk(Checkout ck) {
        this.ck = ck;
    }
    public String getObs() {
        return obs;
    }
    public void setObs(String obs) {
        this.obs = obs;
    }
    public int getQtd() {
        return qtd;
    }
    public void setQtd(int qtd) {
        this.qtd = qtd;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public Produto getProduto() {
        return produto;
    }
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
