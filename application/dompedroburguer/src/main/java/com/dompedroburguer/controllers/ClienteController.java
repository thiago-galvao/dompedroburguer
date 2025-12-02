package com.dompedroburguer.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dompedroburguer.model.Cliente;
import com.dompedroburguer.model.repositories.ClienteRepository;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.NotFoundResponse;

public class ClienteController {
    private ClienteRepository repositorio;
    public ClienteController (ClienteRepository repositorio){
        this.repositorio = repositorio;
    }

    public Handler get = (Context ctx)->{
        List<Cliente> clientes = repositorio.listar();

        Map<String, Object> dados = new HashMap<>();

        dados.put("clientes", clientes);
        ctx.render("/pages/gerenciar-clientes.html", dados);
    };

    public Handler buscarCliente = (Context ctx)->{
        Long id = ctx.pathParamAsClass("idCliente", Long.class).get();

        Cliente cliente = repositorio.buscar(id);

        if (cliente != null) {
            Map<String, Object> dados = new HashMap<>();
            dados.put("cliente", cliente);
            
            ctx.render("pages/editar-cliente.html", dados);
        } else {
            throw new NotFoundResponse("Cliente não encontrado.");
        }
    };

    public Handler atualizarCliente = (Context ctx) ->{
        String nome = ctx.formParam("nome");
        String telefone = ctx.formParam("telefone");
        String endereco = ctx.formParam("endereco");
        String ativoParam = ctx.formParam("ativo");
        boolean estaAtivo = ativoParam != null;
        String latLong = ctx.formParam("latitudeLongitude");
        String complemento = ctx.formParam("complemento");

        String dataNascString = ctx.formParam("dataNasc");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dataNascUtil = null;
        if (dataNascString != null && !dataNascString.trim().isEmpty()) {
            try {
                dataNascUtil = formatter.parse(dataNascString);
            } catch (ParseException e) {
                System.err.println("Erro ao converter a data: " + dataNascString);
            }
        }
        String sexo = ctx.formParam("sexo");
        char sexoChar = '-';
        if (sexo != null && !sexo.isEmpty()) {
            sexoChar = sexo.toUpperCase().charAt(0); 
        }
        String email = ctx.formParam("email");
        String idString = ctx.formParam("idCliente");
        int id = Integer.parseInt(idString);
        Cliente cliente = new Cliente(id, nome, telefone, endereco, estaAtivo, latLong, complemento, dataNascUtil, sexoChar, email);
        if (repositorio.atualizar(cliente)){
            Map<String, Object> dados = new HashMap<>();
            dados.put("cliente", cliente);
            dados.put("mensagem", "Cliente atualizado com sucesso!");
            ctx.render("/pages/cliente-att.html", dados);
        } else {
            throw new NotFoundResponse("Erro ao atualizar cliente.");
        }
    };

    public Handler inserir = (Context ctx)->{
        String nome = ctx.formParam("nome");
        String telefone = ctx.formParam("telefone");
        String endereco = ctx.formParam("endereco");
        Date dataAtual = new Date();
        String latLong = ctx.formParam("latitude_longitude");
        String complemento = ctx.formParam("complemento");
        String dataNascString = ctx.formParam("data_nasc");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
        Date dataNascUtil = formatter.parse(dataNascString);
        String sexo = ctx.formParam("sexo");
        char sexoChar = '-';
        if (sexo != null && !sexo.isEmpty()) {
            sexoChar = sexo.toUpperCase().charAt(0); 
        }
        String email = ctx.formParam("email");
        Cliente cliente = new Cliente(nome, telefone, endereco, true, dataAtual, latLong, complemento, dataNascUtil, sexoChar, email);

        if(repositorio.inserir(cliente)){
            Map<String, Object> dados = new HashMap<>();
            dados.put("mensagem", "Cliente inserido com sucesso!");
            ctx.render("/pages/cliente-inserido.html", dados);
        } else {
            throw new NotFoundResponse("Erro ao inserir cliente.");
        }

    };
}
