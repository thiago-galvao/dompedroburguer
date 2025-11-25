package com.dompedroburguer.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dompedroburguer.model.Produto;
import com.dompedroburguer.model.repositories.ProdutoRepository;
import com.github.hugoperlin.results.Resultado;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class ListaProdutoController {
    private ProdutoRepository repositorio;
    public ListaProdutoController(ProdutoRepository repositorio){
        this.repositorio = repositorio;
    }

    public Handler get = (Context ctx)->{
        Resultado<List<Produto>> resultado = repositorio.mostrar();
        
        Map<String, Object> dados = new HashMap<>();
        
        if (resultado.foiSucesso()){
            dados.put("produtos", resultado.comoSucesso().getObj());
            dados.put("totalProdutos", resultado.comoSucesso().getObj().size());
        } else {
            dados.put("erro", resultado.getMsg());
        }

        ctx.render("gerenciar-produtos.html", dados);
    };
}
