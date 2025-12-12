package com.dompedroburguer.model.repositories;

import com.dompedroburguer.model.Checkout;
import com.dompedroburguer.model.dao.CheckoutDAO;

public class CheckoutRepository {
    private final CheckoutDAO dao;

    public CheckoutRepository(CheckoutDAO dao) {
        this.dao = dao;
    }

    public boolean inserir(Checkout check){

        return dao.inserir(check);
    }
}
