package com.dompedroburguer.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dompedroburguer.model.Cliente;
import com.dompedroburguer.model.FormasPagamento;
import com.dompedroburguer.model.Produto;
import com.dompedroburguer.model.repositories.CheckoutRepository;
import com.dompedroburguer.model.repositories.ClienteRepository;
import com.dompedroburguer.model.repositories.FormasPagamentoRepository;
import com.dompedroburguer.model.repositories.ProdutoRepository;
import com.github.hugoperlin.results.Resultado;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class CheckoutController {
    private CheckoutRepository repositorio;
    private ClienteRepository clienteRepo;
    private ProdutoRepository produtoRepo;
    private FormasPagamentoRepository pagRepo;

    public CheckoutController(CheckoutRepository repositorio, ClienteRepository clienteRepo, ProdutoRepository produtoRepo, FormasPagamentoRepository pagRepo){
        this.repositorio = repositorio; 
        this.clienteRepo = clienteRepo;
        this.produtoRepo = produtoRepo;
        this.pagRepo = pagRepo;
    }

    public Handler get = (Context ctx) -> {
        List<Cliente> clientes = clienteRepo.listar();
        Resultado<List<Produto>> produtos = produtoRepo.mostrar();
        List<FormasPagamento> pag = pagRepo.listar();
        Map<String, Object> dados = new HashMap<>();
        dados.put("clientes", clientes);
        dados.put("produtos", produtos.comoSucesso().getObj());
        dados.put("pagamento", pag);
        ctx.render("/pages/cad-pedido.html", dados);
    };

    public Handler inserir = (Context ctx)->{
        String dataHoraString = ctx.formParam("dataHoraPedido");
        DateTimeFormatter formatador = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraString, formatador);
        String servico = ctx.formParam("tipoServico");
        boolean tipoServico = Boolean.parseBoolean(servico);

        // Atributos para construir cliente.
        String id = ctx.formParam("clienteId");
        int idInt = Integer.parseInt(id);
        String nomeCliente = ctx.formParam("nomeCliente");
        String telefone = ctx.formParam("telefone");
        String endereco = ctx.formParam("enderecoEntrega");
        String complemento = ctx.formParam("complemento");
        if (tipoServico){
            Cliente cliente = new Cliente(idInt, nomeCliente, telefone, endereco, complemento);
        } else {
            Cliente cliente = new Cliente(idInt, nomeCliente, telefone);
        }
        String obs = ctx.formParam("observacaoEntrega");
        List<Produto> produtos = new ArrayList<>();
        
    };
}

