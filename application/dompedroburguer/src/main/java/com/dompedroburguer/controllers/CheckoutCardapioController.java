package com.dompedroburguer.controllers;

import com.dompedroburguer.model.repositories.CheckoutCardapioRepositoy;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class CheckoutCardapioController {
    private CheckoutCardapioRepositoy checkRepository;
    public CheckoutCardapioController(CheckoutCardapioRepositoy checkRepositoy){
        this.checkRepository = checkRepositoy;
    }

    public Handler buscarPorId = (Context ctx) -> {
        
    };
}
