package com.dompedroburguer.model;

import java.util.Date;

public class Cliente{
    private int id;
    private String nome;
    private String telefone;
    private String endereco;
    private boolean ativo;
    private Date dataCadastro;
    private String latitudeLongitude;
    private String complemento;
    private Date dataNasc;
    private char sexo;
    private String email;
    public Cliente(){

    }
    public Cliente(int id, String nome, String telefone, String endereco, boolean ativo, Date dataCadastro, String latitudeLongitude,
    String complemento, Date dataNasc, char sexo, String email){
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.ativo = ativo;
        this.dataCadastro = dataCadastro;
        this.latitudeLongitude = latitudeLongitude;
        this.complemento = complemento;
        this.dataNasc = dataNasc;
        this.sexo = sexo;
        this.email = email;
    }
    // Construtor de atualizar cliente
    public Cliente(int  id, String nome, String telefone, String endereco, boolean ativo, String latLong, String complemento, Date dataNasc, char sexo, String email){
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.ativo = ativo;
        this.latitudeLongitude = latLong;
        this.complemento = complemento;
        this.dataNasc = dataNasc;
        this.sexo = sexo;
        this.email = email;
    }
    // Construtor para inseirior novo cliente.
    public Cliente(String nome, String telefone, String endereco, boolean ativo, Date dataCadastro, String latLong, String complemento, Date dataNasc, char sexo, String email){
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.ativo = ativo;
        this.dataCadastro = dataCadastro;
        this.latitudeLongitude = latLong;
        this.complemento = complemento;
        this.dataNasc = dataNasc;
        this.sexo = sexo;
        this.email = email;
    }
    // Construtor para inserir novo pedido em caso de entrega.
    public Cliente(int id, String nome, String telefone, String endereco, String complemento){
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.complemento = complemento;
    }
    // Construtor para inserir novo pedido em caso de retirada balcão.
    public Cliente(int id, String nome, String telefone){
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public boolean isAtivo() {
        return ativo;
    }
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    public Date getDataCadastro() {
        return dataCadastro;
    }
    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    public String getLatitudeLongitude() {
        return latitudeLongitude;
    }
    public void setLatitudeLongitude(String latitudeLongitude) {
        this.latitudeLongitude = latitudeLongitude;
    }
    public String getComplemento() {
        return complemento;
    }
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    public Date getDataNasc() {
        return dataNasc;
    }
    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }
    public char getSexo() {
        return sexo;
    }
    public void setSexo(char sexo) {
        this.sexo = sexo;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}