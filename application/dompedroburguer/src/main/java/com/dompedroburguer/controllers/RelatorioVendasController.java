package com.dompedroburguer.controllers;

import java.util.HashMap;
import java.util.Map;

import io.javalin.http.Context;
import io.javalin.http.Handler;
// Implementação futura.
public class RelatorioVendasController {
    public Handler get = (Context ctx) -> {
        Map<String, Object> dados = new HashMap<>();
        ctx.render("/pages/relatorio-vendas.html", dados);
    };
}
