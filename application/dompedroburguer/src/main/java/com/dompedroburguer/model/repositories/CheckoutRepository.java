package com.dompedroburguer.model.repositories;

import java.util.List;

import com.dompedroburguer.model.Checkout;
import com.dompedroburguer.model.CheckoutCardapio;
import com.dompedroburguer.model.dao.CheckoutDAO;

public class CheckoutRepository {
    private final CheckoutDAO dao;

    public CheckoutRepository(CheckoutDAO dao) {
        this.dao = dao;
    }

    public Checkout inserir(Checkout check, List<CheckoutCardapio> checkCad){
        return dao.inserir(check, checkCad);
    }
    public Checkout buscar(int id){
        return dao.buscar(id);
    }
}
