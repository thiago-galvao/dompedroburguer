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

        app.get("/index", indexController.get);
        app.get("/add", produtoController.get);
        app.post("/add", produtoController.post);
        app.get("/gerenciar-produtos", listaProdutoController.get);
    }
}
