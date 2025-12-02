package com.dompedroburguer.model.repositories;

import java.util.List;

import com.dompedroburguer.model.Cliente;
import com.dompedroburguer.model.dao.ClienteDAO;

public class ClienteRepository {
    private final ClienteDAO dao;
    public ClienteRepository(ClienteDAO dao){
        this.dao = dao;
    }
    public List<Cliente> listar(){
        return dao.listar();
    }
    public boolean desativar(Long id){
        return dao.desativar(id);
    }
    public Cliente buscar(Long id){
        return dao.buscar(id);
    }
    public boolean atualizar(Cliente cliente){
        return dao.atualizar(cliente);
    }
    public boolean inserir(Cliente cliente){
        return dao.inserir(cliente);
    }
}
