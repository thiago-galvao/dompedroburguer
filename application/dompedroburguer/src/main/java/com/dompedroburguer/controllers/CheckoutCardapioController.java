package com.dompedroburguer.controllers;

import com.dompedroburguer.model.repositories.CheckoutCardapioRepositoy;

public class CheckoutCardapioController {
    private CheckoutCardapioRepositoy checkRepository;
    public CheckoutCardapioController(CheckoutCardapioRepositoy checkRepositoy){
        this.checkRepository = checkRepositoy;
    }

    
}
