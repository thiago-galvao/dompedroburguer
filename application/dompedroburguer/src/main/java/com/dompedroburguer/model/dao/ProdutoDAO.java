package com.dompedroburguer.model.dao;

import java.util.List;

import com.dompedroburguer.model.Produto;

public interface ProdutoDAO {
    public boolean salvar(Produto produto);
    public List<Produto> mostrar();
}
