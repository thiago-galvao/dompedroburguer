package com.dompedroburguer.model.dao;

import java.util.List;

import com.dompedroburguer.model.FormasPagamento;

public interface FormasPagamentoDAO {
    public List<FormasPagamento> listar();
    public FormasPagamento buscar(int id);
    public boolean inserir(String descricao);
    public boolean excluir(FormasPagamento formaPagamento);
    public boolean atualizar(FormasPagamento formaPagamento);
}
