package com.dompedroburguer.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dompedroburguer.model.FormasPagamento;
import com.dompedroburguer.model.repositories.FormasPagamentoRepository;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.NotFoundResponse;

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

    public Handler buscar = (Context ctx)->{
        int id = ctx.pathParamAsClass("id", int.class).get();
        FormasPagamento formasPagamento = repositorio.buscar(id);
        if (formasPagamento != null) {
            Map<String, Object> dados = new HashMap<>();
            dados.put("tipo", formasPagamento);
            
            ctx.render("pages/edit-forma-pagamento.html", dados);
        } else {
            throw new NotFoundResponse("Tipo pagamento não encontrado.");
        }
    };

    public Handler excluir = (Context ctx)->{
        String id = ctx.formParam("id");
        int idInt = Integer.valueOf(id);
        FormasPagamento formasPagamento = repositorio.buscar(idInt);
        repositorio.excluir(formasPagamento);

        ctx.redirect("/pages/tipos-pagamento");
    };

    public Handler atualizar = (Context ctx)->{
        String descricao = ctx.formParam("descricao");
        String id = ctx.formParam("id");
        int idInt = Integer.parseInt(id);
        FormasPagamento formasPagamento = repositorio.buscar(idInt);
        formasPagamento.setDescricao(descricao);

        try {
            repositorio.atualizar(formasPagamento);
            ctx.redirect("/pages/tipos-pagamento");
        } catch (Exception e){
            e.getMessage();
        }
    };

    public Handler inserir = (Context ctx)->{
        String descricao = ctx.formParam("descricao");
        try {
            repositorio.inserir(descricao);
            ctx.redirect("/pages/tipos-pagamento");
        } catch (Exception e) {
            e.getMessage();
        }
    };
}
