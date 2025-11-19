package com.dompedroburguer.controllers;

import java.util.HashMap;
import java.util.Map;

import com.dompedroburguer.model.Produto;
import com.dompedroburguer.model.repositories.ProdutoRepository;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class ProdutoController {

    private ProdutoRepository repositorio;
    
    public ProdutoController(ProdutoRepository repositorio){
        this.repositorio = repositorio;
    }

    public Handler get = (Context ctx)->{
        ctx.render("add.html");
    };

    public Handler post = (Context ctx)->{
        String nome = ctx.formParam("nome");
        String imagem = ctx.formParam("imagem");
        String descricao = ctx.formParam("descricao");
        Double valor = ctx.formParamAsClass("valor", Double.class).get();
        
        Produto contato = new Produto(nome, imagem, descricao, valor);

        boolean resultado = repositorio.cadastrar(contato);
        Map<String, Object> dados = new HashMap<>();

        if (resultado == true){
            dados.put("mensagem", "Produto cadastrado com sucesso!");
        } else {
            dados.put("erro", "Erro ao cadastrar o produto.");
        }

        ctx.render("add.html", dados);
    };
}
