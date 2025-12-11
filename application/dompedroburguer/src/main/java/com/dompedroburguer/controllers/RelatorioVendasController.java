package com.dompedroburguer.controllers;

import java.util.HashMap;
import java.util.Map;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class RelatorioVendasController {
    public Handler get = (Context ctx) -> {
        // Lógica para gerar o relatório de vendas
        Map<String, Object> dados = new HashMap<>();
        // Adicionar dados ao mapa conforme necessário
        ctx.render("/pages/relatorio-vendas.html", dados);
    };
}
