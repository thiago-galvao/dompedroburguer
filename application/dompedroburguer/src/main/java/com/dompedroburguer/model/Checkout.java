package com.dompedroburguer.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Checkout {
    private int id;
    private LocalDateTime dataHoraCompra;
    private boolean entregaBalcao; //Se for true é entrega, se false é retirada.
    private Cliente cliente;
    private String obs;
    private List<Produto> produtos = new ArrayList<>();
    private FormasPagamento fPagamento;
    private double valorTotal;
    private String cupom;
    private double valorFinal;
    private String status;
    private String latLong;
    private String enderecoEntrega;
    public Checkout() {
    }

    public Checkout(LocalDateTime dataHoraCompra, boolean entregaBalcao, Cliente cliente, String obs, FormasPagamento fPagamento, double valorTotal, String cupom, double valorFinal, String status) {
        this.dataHoraCompra = dataHoraCompra;
        this.entregaBalcao = entregaBalcao;
        this.cliente = cliente;
        this.obs = obs;
        this.fPagamento = fPagamento;
        this.valorTotal = valorTotal;
        this.cupom = cupom;
        this.valorFinal = valorFinal;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FormasPagamento getfPagamento() {
        return fPagamento;
    }

    public void setfPagamento(FormasPagamento fPagamento) {
        this.fPagamento = fPagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public boolean isEntregaBalcao() {
        return entregaBalcao;
    }

    public void setEntregaBalcao(boolean entregaBalcao) {
        this.entregaBalcao = entregaBalcao;
    }

    public String getCupom() {
        return cupom;
    }

    public void setCupom(String cupom) {
        this.cupom = cupom;
    }

    public LocalDateTime getDataHoraCompra() {
        return dataHoraCompra;
    }

    public void setDataHoraCompra(LocalDateTime dataHoraCompra) {
        this.dataHoraCompra = dataHoraCompra;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

}
