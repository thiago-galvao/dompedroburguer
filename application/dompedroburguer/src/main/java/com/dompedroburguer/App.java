package com.dompedroburguer;

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

        app.get("/", produtoController.get);
        app.post("/", produtoController.post);
    }
}
