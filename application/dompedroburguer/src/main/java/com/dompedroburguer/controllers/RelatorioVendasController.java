package com.dompedroburguer.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dompedroburguer.model.Cliente;
import com.dompedroburguer.model.repositories.RelatorioRepository;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class RelatorioVendasController {

    private RelatorioRepository repositorio;

    public RelatorioVendasController(RelatorioRepository repositorio){
        this.repositorio = repositorio;
    }

    public Handler get = (Context ctx) -> {
        ctx.render("/pages/relatorio-vendas.html");
    };

    public Handler puxarRelatorio = (Context ctx) -> {

        String dataString = ctx.pathParam("dataAtual");
        SimpleDateFormat formato = new SimpleDateFormat("ddMMyyyy");
        Date dataAtual = formato.parse(dataString);

        String dataInicioString = ctx.queryParam("dataInicio");
        String dataFimString = ctx.queryParam("dataFim");
        SimpleDateFormat formato2 = new SimpleDateFormat("yyyy-MM-dd");
        
        Date dataInicio = formato2.parse(dataInicioString);
        Date dataFim = formato2.parse(dataFimString);

        List<Cliente> clientes = new ArrayList<>();

        clientes = repositorio.puxarRelatorioCliente(dataInicio, dataFim);

        Map<String, Object> dados = new HashMap<>();

        dados.put("clientes", clientes);

        ctx.render("/pages/relatorio-vendas.html", dados);
        
    };
}
