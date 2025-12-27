package com.dompedroburguer.model.repositories;

import java.util.Date;
import java.util.List;

import com.dompedroburguer.model.Cliente;
import com.dompedroburguer.model.dao.RelatorioDAO;

public class RelatorioRepository {
    private RelatorioDAO dao;

    public RelatorioRepository(RelatorioDAO dao){
        this.dao = dao;
    }

    public List<Cliente> puxarRelatorioCliente(Date inicio, Date fim){
        return dao.puxarRelatorioCliente(inicio, fim);
    }
}
