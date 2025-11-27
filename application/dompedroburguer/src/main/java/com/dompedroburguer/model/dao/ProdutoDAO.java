package com.dompedroburguer.model.dao;

import java.util.List;

import com.dompedroburguer.model.Produto;
import com.github.hugoperlin.results.Resultado;

public interface ProdutoDAO {
    public Resultado<Produto> salvar(Produto produto);
    public Resultado<List<Produto>> mostrar();
    public Produto buscar(Long id);
    public Produto atualizar(Produto produto);
    public boolean excluir(Long id);
}
