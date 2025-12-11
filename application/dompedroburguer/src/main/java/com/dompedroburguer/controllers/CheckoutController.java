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
        System.out.println("=== CHECKOUT INSERIR - INICIANDO ===");
        System.out.println("Todos os parâmetros: " + ctx.formParamMap());
        
        String dataHoraString = ctx.formParam("dataHoraPedido");
        System.out.println("dataHoraPedido recebido: '" + dataHoraString + "'");
        
        if (dataHoraString == null || dataHoraString.isEmpty()) {
            System.err.println("ERRO: dataHoraPedido é null ou vazio!");
            ctx.status(400);
            ctx.result("Erro: Data e hora do pedido são obrigatórios");
            return;
        }
        
        LocalDateTime dataHora;
        try {
            DateTimeFormatter formatador = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            dataHora = LocalDateTime.parse(dataHoraString, formatador);
            System.out.println("Data parseada com sucesso: " + dataHora);
        } catch (Exception e) {
            System.err.println("ERRO ao parsear data: " + e.getMessage());
            e.printStackTrace();
            ctx.status(400);
            ctx.result("Erro ao processar data e hora: " + e.getMessage());
            return;
        }
        
        String servico = ctx.formParam("tipoServico");
        boolean tipoServico = Boolean.parseBoolean(servico);
        System.out.println("Tipo de serviço (entrega=true): " + tipoServico);

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
        
        // Ler os produtos adicionados dinamicamente
        List<Produto> produtos = new ArrayList<>();
        int index = 0;
        while (true) {
            String produtoIdStr = ctx.formParam("itens[" + index + "].produtoId");
            if (produtoIdStr == null || produtoIdStr.isEmpty()) {
                break; // Não há mais produtos
            }
            
            String quantidadeStr = ctx.formParam("itens[" + index + "].quantidade");
            String valorUnitarioStr = ctx.formParam("itens[" + index + "].valorUnitario");
            String nomeProduto = ctx.formParam("itens[" + index + "].nome");
            
            try {
                int produtoId = Integer.parseInt(produtoIdStr);
                int quantidade = Integer.parseInt(quantidadeStr);
                    // Converte vírgula para ponto para aceitar formato brasileiro
                    String valorUnitarioNormalizado = valorUnitarioStr.replace(',', '.');
                    double valorUnitario = Double.parseDouble(valorUnitarioNormalizado);
                
                // Criar produto com os dados do formulário
                Produto p = new Produto();
                p.setId(produtoId);
                p.setNome(nomeProduto);
                p.setValor(valorUnitario);
                // Se tiver campo de quantidade na classe Produto, atribua aqui
                // p.setQuantidade(quantidade);
                
                produtos.add(p);
            } catch (NumberFormatException e) {
                System.err.println("Erro ao parsear produto no índice " + index + ": " + e.getMessage());
            }
            
            index++;
        }
        
        // Agora você tem:
        // - dataHora (LocalDateTime)
        // - tipoServico (boolean)
        // - cliente (Cliente)
        // - produtos (List<Produto>)
        // - obs (String)
        // Você pode salvar no repositório
        System.out.println("Pedido recebido com " + produtos.size() + " produtos");
        ctx.render("/index.html");
    };
}

