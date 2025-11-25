package com.dompedroburguer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dompedroburguer.model.FabricaConexoes;
import com.dompedroburguer.model.Produto;
import com.github.hugoperlin.results.Resultado;

public class JDBCProdutoDAO implements ProdutoDAO{

    private FabricaConexoes fabrica;

    public JDBCProdutoDAO(FabricaConexoes fabrica){
        this.fabrica = fabrica;
    }

    @Override
    public Resultado<Produto> salvar(Produto produto){
        
		String sql = "INSERT INTO pi_cardapio(nome, imagem, descricao, valor) VALUES (?,?,?,?)";
		try (Connection con = fabrica.getConnection();
            PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, produto.getNome());
			pstm.setString(2, produto.getImagem());
			pstm.setString(3, produto.getDescricao());
            pstm.setDouble(4, produto.getValor());
            int rows = pstm.executeUpdate();
			if (rows == 1){
				return Resultado.sucesso("Produto cadastrado!", produto);
			} else {
				return Resultado.erro("Problema ao cadastrar!");
			}
		} catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar o produto no banco de dados.", e);
        } 
    }

    @Override
    public Resultado<List<Produto>> mostrar(){
        List<Produto> lista = new ArrayList<>();
        lista.clear();

        String sql = "SELECT * FROM pi_cardapio";
        try (Connection con = fabrica.getInstance().getConnection();
            PreparedStatement pstm = con.prepareCall(sql)){

            ResultSet result = pstm.executeQuery();
            while(result.next()){
                int id = result.getInt("id");
                String nome = result.getString("nome");
                String imagem = result.getString("imagem");
                String descricao = result.getString("descricao");
                Double valor = result.getDouble("valor");

                Produto produto = new Produto(nome, imagem, descricao, valor);
                lista.add(produto);
            }
            return Resultado.sucesso("Lista", Collections.unmodifiableList(lista));
        } catch(SQLException e){
            return Resultado.erro(e.getMessage());
        }
    }
}