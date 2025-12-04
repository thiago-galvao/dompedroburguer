package com.dompedroburguer.model.repositories;

import java.util.List;

import com.dompedroburguer.model.FormasPagamento;
import com.dompedroburguer.model.dao.FormasPagamentoDAO;
public class FormasPagamentoRepository {
    private final FormasPagamentoDAO dao;

    public FormasPagamentoRepository(FormasPagamentoDAO dao){
        this.dao = dao;
    }
    public List<FormasPagamento> listar(){
        return dao.listar();
    }
    public FormasPagamento buscar(int id){
        return dao.buscar(id);
    }
    public boolean inserir(String descricao){
        return dao.inserir(descricao);
    }
    public boolean excluir(FormasPagamento formaPagamento){
        return dao.excluir(formaPagamento);
    }
    public boolean atualizar(FormasPagamento formasPagamento){
        return dao.atualizar(formasPagamento);
    }
}
