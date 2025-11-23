package com.dompedroburguer.model.dao;

import java.util.List;

import com.dompedroburguer.model.Produto;
import com.github.hugoperlin.results.Resultado;

public interface ProdutoDAO {
    public Resultado<Produto> salvar(Produto produto);
    public List<Produto> mostrar();
}
