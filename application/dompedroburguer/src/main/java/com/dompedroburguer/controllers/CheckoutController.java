package com.dompedroburguer.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dompedroburguer.model.Checkout;
import com.dompedroburguer.model.CheckoutCardapio;
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
        } catch (Exception e) {
            System.err.println("ERRO ao parsear data: " + e.getMessage());
            e.printStackTrace();
            ctx.status(400);
            ctx.result("Erro ao processar data e hora: " + e.getMessage());
            return;
        }
        
        String servico = ctx.formParam("tipoServico");
        boolean tipoServico = Boolean.parseBoolean(servico);

        String clienteIdStr = ctx.formParam("clienteId");
        int clienteId = Integer.parseInt(clienteIdStr);
        Long clienteLong = Long.valueOf(clienteId);
        // Buscar cliente completo no banco
        Cliente clienteBanco = clienteRepo.buscar(clienteLong);
        if (clienteBanco == null) {
            ctx.status(400);
            ctx.result("Erro: Cliente não encontrado");
            return;
        }
        
        String obs = ctx.formParam("observacaoEntrega");

        Cliente cliente = null;
        if (tipoServico){
            cliente = new Cliente(clienteId, clienteBanco.getNome(), clienteBanco.getTelefone(), clienteBanco.getEndereco(), clienteBanco.getComplemento());
        } else {
            cliente = new Cliente(clienteId, clienteBanco.getNome(), clienteBanco.getTelefone());
        }
        
        List<CheckoutCardapio> itensParaSalvar = new ArrayList<>();
        
        int index = 0;
        while (true) {
            String produtoIdStr = ctx.formParam("itens[" + index + "].produtoId");
            if (produtoIdStr == null || produtoIdStr.isEmpty()) {
                break;
            }
            
            String quantidadeStr = ctx.formParam("itens[" + index + "].quantidade");
            String valorUnitarioStr = ctx.formParam("itens[" + index + "].valorUnitario");
            String nomeProduto = ctx.formParam("itens[" + index + "].nome");
            String obsProd = ctx.formParam("itens[" + index + "].observacao");
            
            try {
                int produtoId = Integer.parseInt(produtoIdStr);
                int quantidade = Integer.parseInt(quantidadeStr);
                // Converte vírgula para ponto para aceitar formato brasileiro
                String valorUnitarioNormalizado = valorUnitarioStr.replace(',', '.');
                double valorUnitario = Double.parseDouble(valorUnitarioNormalizado);
                
                Produto p = new Produto();
                p.setId(produtoId);
                p.setNome(nomeProduto);
                p.setValor(valorUnitario);
                
                CheckoutCardapio chk = new CheckoutCardapio(p, obsProd, quantidade, (quantidade * valorUnitario));
                itensParaSalvar.add(chk);
            } catch (NumberFormatException e) {
                System.err.println("Erro ao parsear produto no índice " + index + ": " + e.getMessage());
            }
            
            index++;
        }
        
        String formaPagamentoId = ctx.formParam("formaPagamento");
        int pagamentoId = Integer.parseInt(formaPagamentoId);
        FormasPagamento formaPagamento = pagRepo.buscar(pagamentoId);

        FormasPagamento fPagamento = new FormasPagamento(pagamentoId, formaPagamento.getDescricao());

        double valorTotal = 0.0;
        String valTot = ctx.formParam("valorTotal");
        if (valTot != null && !valTot.isEmpty()) {
            try {
                String vt = valTot.replace(',', '.');
                valorTotal = Double.parseDouble(vt);
            } catch (NumberFormatException e) {
                System.err.println("Erro ao parsear valor total: " + valTot);
                System.err.println(e.getMessage());
            }
        }
        double valorFinal = 0.0;
        String valFin = ctx.formParam("valorFinal");
        if (valFin != null && !valFin.isEmpty()) {
            try {
                String vf = valFin.replace(',', '.');
                valorFinal = Double.parseDouble(vf);
            } catch (NumberFormatException e) {
                System.err.println("Erro ao parsear valor final: " + valFin);
                System.err.println(e.getMessage());
            }
        }

        Checkout check = new Checkout(dataHora, tipoServico, cliente, obs, fPagamento, valorTotal, valorFinal);

        Checkout check2 = repositorio.inserir(check, itensParaSalvar);
        
        Map<String, Object> dados = new HashMap<>();
        dados.put("check", check2);
        dados.put("listaItens", itensParaSalvar);

        ctx.render("/pages/comanda.html", dados);
    };
}

