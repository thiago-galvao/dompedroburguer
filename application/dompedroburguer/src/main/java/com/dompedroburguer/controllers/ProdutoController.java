package com.dompedroburguer.controllers;

import java.util.HashMap;
import java.util.Map;

import com.dompedroburguer.model.Produto;
import com.dompedroburguer.model.repositories.ProdutoRepository;
import com.github.hugoperlin.results.Resultado;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.NotFoundResponse;

public class ProdutoController {

    private ProdutoRepository repositorio;
    
    public ProdutoController(ProdutoRepository repositorio){
        this.repositorio = repositorio;
    }

    public Handler get = (Context ctx)->{
        ctx.render("/pages/add.html");
    };

    // Adiciona um produto
    public Handler post = (Context ctx)->{
        String nome = ctx.formParam("nome");
        String imagem = ctx.formParam("imagem");
        String descricao = ctx.formParam("descricao");
        Double valor = ctx.formParamAsClass("valor", Double.class).get();
        Produto produto = new Produto(nome, imagem, descricao, valor);

        Resultado<Produto> resultado = repositorio.salvar(produto);

        Map<String, Object> dados = new HashMap<>();

        if (resultado.foiSucesso()){
            dados.put("mensagem", resultado.getMsg());
        } else {
            dados.put("erro", resultado.getMsg());
        }

        ctx.render("/pages/add.html", dados);
    };

    // Edita produto
    public Handler post2 = (Context ctx)->{

        Long id = ctx.pathParamAsClass("id", Long.class).get();
        Produto produto = repositorio.buscar(id);

        String nome = ctx.formParam("nome");
        String imagem = ctx.formParam("imagem");
        String descricao = ctx.formParam("descricao");
        Double valor = ctx.formParamAsClass("valor", Double.class).get();
        
        produto.setNome(nome);
        produto.setImagem(imagem);
        produto.setDescricao(descricao);
        produto.setValor(valor);

        repositorio.atualizar(produto);

        Map<String, Object> dados = new HashMap<>();
        dados.put("produto", produto);
        dados.put("mensagem", "Produto atualizado com sucesso!");

        ctx.render("/pages/edit-produto.html", dados);
    };

    public Handler buscarProdutoPorId = (Context ctx) -> {
        
        Long id = ctx.pathParamAsClass("id", Long.class).get();

        Produto produto = repositorio.buscar(id);

        if (produto != null) {
            Map<String, Object> dados = new HashMap<>();
            dados.put("produto", produto);
            
            ctx.render("pages/edit-produto.html", dados);
        } else {
            throw new NotFoundResponse("Produto não encontrado.");
        }
    };

    // Exclui produto
    public Handler post3 = (Context ctx)->{

        Long id = ctx.pathParamAsClass("id", Long.class).get();
        
        Map<String, Object> dados = new HashMap<>();

        if (repositorio.excluir(id)){ 
            dados.put("mensagem", "Produto excluído com sucesso!");
            ctx.render("/pages/excluir-prod.html", dados);
        } else {
            dados.put("erro", "Erro ao excluir o produto.");
            ctx.render("/pages/excluir-prod.html", dados);
        }

    };
}
