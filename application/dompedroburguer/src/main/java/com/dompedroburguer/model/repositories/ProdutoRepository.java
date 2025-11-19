package com.dompedroburguer.model.repositories;

import java.util.List;

import com.dompedroburguer.model.Produto;
import com.dompedroburguer.model.dao.ProdutoDAO;

public class ProdutoRepository {
    private ProdutoDAO dao;
    
    public ProdutoRepository(ProdutoDAO dao){
        this.dao = dao;
    }
    
    public boolean cadastrar(Produto produto){
        return dao.salvar(produto);
    }

    public List<Produto> mostrar(){
        return dao.mostrar();
    }
}
