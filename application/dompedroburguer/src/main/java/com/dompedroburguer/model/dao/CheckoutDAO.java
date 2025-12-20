package com.dompedroburguer.model.dao;

import java.util.List;

import com.dompedroburguer.model.Checkout;
import com.dompedroburguer.model.CheckoutCardapio;

public interface CheckoutDAO {
    public Checkout inserir(Checkout checkout, List<CheckoutCardapio> checCad);
    public Checkout buscar(int id);
}
