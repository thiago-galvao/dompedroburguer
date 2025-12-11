package com.dompedroburguer.model.repositories;

import java.time.LocalDateTime;

import com.dompedroburguer.model.Checkout;
import com.dompedroburguer.model.Cliente;
import com.dompedroburguer.model.FormasPagamento;
import com.dompedroburguer.model.Status;
import com.dompedroburguer.model.dao.CheckoutDAO;
import com.dompedroburguer.model.dao.ClienteDAO;
import com.dompedroburguer.model.dao.FormasPagamentoDAO;

public class CheckoutRepository {
    private final CheckoutDAO dao;
    private final ClienteDAO clienteDAO;

    Status status = Status.EM_PRODUCAO;
    String statusString = status.name();

    public CheckoutRepository(CheckoutDAO dao, ClienteDAO clienteDAO) {
        this.dao = dao;
        this.clienteDAO = clienteDAO;
    }
    
    public boolean inserir(LocalDateTime dataHora, boolean entregaBalcao, Cliente cliente, String obs, FormasPagamento fPagamento, double valorTotal, String cupom, double valorFinal){
        Checkout checkout = new Checkout(dataHora, entregaBalcao, cliente, obs, fPagamento, valorTotal, cupom, valorFinal, statusString);

        return dao.inserir(checkout);
    }
}
