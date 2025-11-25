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

    // Chama a página de listagem de gerenciamento de produtos.
    public Handler get = (Context ctx)->{
        Resultado<List<Produto>> resultado = repositorio.mostrar();
        
        Map<String, Object> dados = new HashMap<>();
        
        if (resultado.foiSucesso()){
            dados.put("produtos", resultado.comoSucesso().getObj());
            dados.put("totalProdutos", resultado.comoSucesso().getObj().size());
        } else {
            dados.put("erro", resultado.getMsg());
        }

        ctx.render("/pages/gerenciar-produtos.html", dados);
    };

    // Chama a página de listagem de cardápio.
    public Handler get2 = (Context ctx)->{
        Resultado<List<Produto>> resultado = repositorio.mostrar();
        
        Map<String, Object> dados = new HashMap<>();
        
        if (resultado.foiSucesso()){
            dados.put("produtos", resultado.comoSucesso().getObj());
            dados.put("totalProdutos", resultado.comoSucesso().getObj().size());
        } else {
            dados.put("erro", resultado.getMsg());
        }

        ctx.render("/pages/cardapio.html", dados);
    };
}
