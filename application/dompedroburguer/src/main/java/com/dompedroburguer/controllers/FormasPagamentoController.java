package com.dompedroburguer.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dompedroburguer.model.FormasPagamento;
import com.dompedroburguer.model.repositories.FormasPagamentoRepository;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class FormasPagamentoController {
    private FormasPagamentoRepository repositorio;
    public FormasPagamentoController (FormasPagamentoRepository repositorio){
        this.repositorio = repositorio;
    }

    public Handler listar = (Context ctx)->{
        List<FormasPagamento> formasPagamento = repositorio.listar();
        
        Map<String, Object> dados = new HashMap<>();

        dados.put("tipo", formasPagamento);

        ctx.render("/pages/tipos-pagamento.html", dados);
    };
}
