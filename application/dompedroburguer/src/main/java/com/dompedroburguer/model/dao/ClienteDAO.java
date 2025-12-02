package com.dompedroburguer.model.dao;

import java.util.List;

import com.dompedroburguer.model.Cliente;

public interface ClienteDAO {
    public List<Cliente> listar();
    public boolean desativar(Long id);
    public Cliente buscar(Long id);
    public boolean atualizar(Cliente cliente);
    public boolean inserir(Cliente cliente);
}
