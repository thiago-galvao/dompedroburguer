package com.dompedroburguer.controllers;

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
        
        Produto produto = new Produto(nome, imagem, descricao, valor);

        repositorio.salvar(produto);

        ctx.render("add.html");
    };
}
