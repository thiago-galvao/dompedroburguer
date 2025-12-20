package com.dompedroburguer.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class RelatorioVendasController {
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
        
        
        ctx.render("/pages/relatorio-vendas.html");
        
    };
}
