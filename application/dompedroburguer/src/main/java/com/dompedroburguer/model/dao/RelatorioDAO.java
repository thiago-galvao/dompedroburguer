package com.dompedroburguer.model.dao;

import java.util.Date;
import java.util.List;

import com.dompedroburguer.model.Cliente;


public interface RelatorioDAO {
    public List<Cliente> puxarRelatorioCliente(Date inicio, Date fim);
}
