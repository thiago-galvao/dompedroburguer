package com.dompedroburguer;

import com.dompedroburguer.controllers.IndexController;
import com.dompedroburguer.controllers.ListaProdutoController;
import com.dompedroburguer.controllers.ProdutoController;
import com.dompedroburguer.model.FabricaConexoes;
import com.dompedroburguer.model.dao.JDBCProdutoDAO;
import com.dompedroburguer.model.dao.ProdutoDAO;
import com.dompedroburguer.model.repositories.ProdutoRepository;
import com.dompedroburguer.utils.JavalinUtils;

public class App {
    public static void main(String[] args){
        var app = JavalinUtils.makeApp(7070);

        ProdutoDAO produtoDAO = new JDBCProdutoDAO(FabricaConexoes.getInstance());
        ProdutoRepository repositorioProduto = new ProdutoRepository(produtoDAO);

        ProdutoController produtoController = new ProdutoController(repositorioProduto);

        IndexController indexController = new IndexController();

        ListaProdutoController listaProdutoController = new ListaProdutoController(repositorioProduto);

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

        app.post("/pages/excluir-prod/{id}", produtoController.post3);
    }
}
