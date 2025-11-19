package com.dompedroburguer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.dompedroburguer.model.FabricaConexoes;
import com.dompedroburguer.model.Produto;

public class JDBCProdutoDAO implements ProdutoDAO{

    public JDBCProdutoDAO(FabricaConexoes instance) {
    }

    private FabricaConexoes fabrica;

    public void JDBCContatoDAO(FabricaConexoes fabrica){
        this.fabrica = fabrica;
    }

    @Override
    public boolean salvar(Produto produto){
        Connection con = null;
		try {
			con = fabrica.getConnection();

			String sql = "INSERT INTO pi_cardapio(nome, imagem, descricao, valor) VALUES (?,?,?,?)";

			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, produto.getNome());
			pstm.setString(2, produto.getImagem());
			pstm.setString(3, produto.getDescricao());
            pstm.setDouble(4, produto.getValor());

			int rows = pstm.executeUpdate();
			if (rows == 1){
				return true;
			} else {
				return false;
			}

		} catch (SQLException e){
            e.printStackTrace(); 
            throw new RuntimeException("Erro ao salvar o produto no banco de dados.", e);
        }
    }

    @Override
    public List<Produto> mostrar(){
        return null;
    }
}
