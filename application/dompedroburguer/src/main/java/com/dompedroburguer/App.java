package com.dompedroburguer;

import com.dompedroburguer.controllers.CheckoutController;
import com.dompedroburguer.controllers.ClienteController;
import com.dompedroburguer.controllers.FormasPagamentoController;
import com.dompedroburguer.controllers.IndexController;
import com.dompedroburguer.controllers.ListaProdutoController;
import com.dompedroburguer.controllers.ProdutoController;
import com.dompedroburguer.controllers.RelatorioVendasController;
import com.dompedroburguer.model.FabricaConexoes;
import com.dompedroburguer.model.dao.CheckoutDAO;
import com.dompedroburguer.model.dao.ClienteDAO;
import com.dompedroburguer.model.dao.FormasPagamentoDAO;
import com.dompedroburguer.model.dao.JDBCCheckoutDAO;
import com.dompedroburguer.model.dao.JDBCClienteDAO;
import com.dompedroburguer.model.dao.JDBCFormasPagamentoDAO;
import com.dompedroburguer.model.dao.JDBCProdutoDAO;
import com.dompedroburguer.model.dao.ProdutoDAO;
import com.dompedroburguer.model.repositories.CheckoutRepository;
import com.dompedroburguer.model.repositories.ClienteRepository;
import com.dompedroburguer.model.repositories.FormasPagamentoRepository;
import com.dompedroburguer.model.repositories.ProdutoRepository;
import com.dompedroburguer.utils.JavalinUtils;

public class App {
    public static void main(String[] args){
        var app = JavalinUtils.makeApp(8090);

        ProdutoDAO produtoDAO = new JDBCProdutoDAO(FabricaConexoes.getInstance());
        ProdutoRepository repositorioProduto = new ProdutoRepository(produtoDAO);

        ProdutoController produtoController = new ProdutoController(repositorioProduto);

        IndexController indexController = new IndexController();

        ListaProdutoController listaProdutoController = new ListaProdutoController(repositorioProduto);

        ClienteDAO clienteDAO = new JDBCClienteDAO(FabricaConexoes.getInstance());
        ClienteRepository repositorioCliente = new ClienteRepository(clienteDAO);

        ClienteController clienteController = new ClienteController(repositorioCliente);

        FormasPagamentoDAO pagamentoDAO = new JDBCFormasPagamentoDAO(FabricaConexoes.getInstance());
        FormasPagamentoRepository repositorioTipoPagamento = new FormasPagamentoRepository(pagamentoDAO);
        FormasPagamentoController formasPagamentoController = new FormasPagamentoController(repositorioTipoPagamento);

        
        CheckoutDAO checkoutDAO = new JDBCCheckoutDAO(FabricaConexoes.getInstance());
        CheckoutRepository repositorioCheckout = new CheckoutRepository(checkoutDAO);
        CheckoutController checkoutController = new CheckoutController(repositorioCheckout, repositorioCliente, repositorioProduto, repositorioTipoPagamento);
        RelatorioVendasController relatorioController = new RelatorioVendasController();

        // Mostra página inicial.
        app.get("/index", indexController.get);

        // Acessa a página de adicionar produto.
        app.get("/pages/add", produtoController.get);

        // Adiciona um produto.
        app.post("/pages/add", produtoController.post);

        // Acessa a página de gerenciamento de produtos.
        app.get("/pages/gerenciar-produtos", listaProdutoController.get);

        // Acessa a página de cardapio.
        app.get("/pages/cardapio", listaProdutoController.get2);

        // Acessa a página de editar produto.
        app.get("/pages/gerenciar-produtos/{id}", produtoController.buscarProdutoPorId);
        // Edita o produto.
        app.post("/pages/gerenciar-produtos/{id}", produtoController.post2);

        // Exclui o produto.
        app.post("/pages/excluir-prod/{id}", produtoController.post3);

        app.get("/pages/gerenciar-clientes", clienteController.get);

        app.get("/pages/gerenciar-clientes/{idCliente}", clienteController.buscarCliente);

        app.post("/pages/cliente-att", clienteController.atualizarCliente);

        app.post("/pages/cliente-inserido", clienteController.inserir);

        app.get("/pages/tipos-pagamento", formasPagamentoController.listar);

        app.get("/pages/tipo-pagamento/{id}", formasPagamentoController.buscar);

        app.post("/pages/tipos-pagamento", formasPagamentoController.excluir);

        app.post("/pages/edit-forma-pagamento", formasPagamentoController.atualizar);

        app.post("/pages/add-pagamento", formasPagamentoController.inserir);

        app.get("/pages/cad-pedido", checkoutController.get);
        
        app.post("/pages/cad-pedido", checkoutController.inserir);

        app.get("/pages/relatorio-vendas", relatorioController.get);
        
        app.get("/pages/relatorio-vendas/{dataAtual}", relatorioController.puxarRelatorio);
    }
}
