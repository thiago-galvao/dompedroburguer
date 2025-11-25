package com.dompedroburguer.model.repositories;

import java.util.List;

import com.dompedroburguer.model.Produto;
import com.dompedroburguer.model.dao.ProdutoDAO;
import com.github.hugoperlin.results.Resultado;

public class ProdutoRepository {
    private final ProdutoDAO dao;
    
    public ProdutoRepository(ProdutoDAO dao){
        this.dao = dao;
    }
    
    public Resultado<Produto> salvar(Produto produto){
        return dao.salvar(produto);
    }

    public Resultado<List<Produto>> mostrar(){
        return dao.mostrar();
    }
}
