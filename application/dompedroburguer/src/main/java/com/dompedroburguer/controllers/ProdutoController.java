package com.dompedroburguer.controllers;

import java.util.HashMap;
import java.util.Map;

import com.dompedroburguer.model.Produto;
import com.dompedroburguer.model.repositories.ProdutoRepository;
import com.github.hugoperlin.results.Resultado;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class ProdutoController {

    private ProdutoRepository repositorio;
    
    public ProdutoController(ProdutoRepository repositorio){
        this.repositorio = repositorio;
    }

    public Handler get = (Context ctx)->{
        ctx.render("/pages/add.html");
    };

    public Handler post = (Context ctx)->{
        String nome = ctx.formParam("nome");
        String imagem = ctx.formParam("imagem");
        String descricao = ctx.formParam("descricao");
        Double valor = ctx.formParamAsClass("valor", Double.class).get();
        
        Produto produto = new Produto(nome, imagem, descricao, valor);

        Resultado<Produto> resultado = repositorio.salvar(produto);
        Map<String, Object> dados = new HashMap<>();

        if (resultado.foiSucesso()){
            dados.put("mensagem", resultado.getMsg());
        } else {
            dados.put("erro", resultado.getMsg());
        }

        ctx.render("/pages/add.html", dados);
    };
}
