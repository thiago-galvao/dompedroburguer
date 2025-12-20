package com.dompedroburguer.model.dao;

import java.util.List;

import com.dompedroburguer.model.CheckoutCardapio;

public interface CheckoutCardapioDAO {
    public List<CheckoutCardapio> buscarPorId(int id);
}
