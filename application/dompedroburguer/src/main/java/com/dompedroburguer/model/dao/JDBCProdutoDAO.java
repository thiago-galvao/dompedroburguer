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

public Produto buscar(Long id) {
        String sql = "SELECT * FROM pi_cardapio WHERE id = ?";
        Produto produto = null;
        try (Connection conexao = FabricaConexoes.getInstance().getConnection();
            PreparedStatement pstm = conexao.prepareStatement(sql)) {

            pstm.setLong(1, id);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    produto = new Produto();
                    produto.setId(rs.getInt("id"));
                    produto.setNome(rs.getString("nome"));
                    produto.setImagem(rs.getString("imagem"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setValor(rs.getDouble("valor")); 
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao tentar encontrar o produto " + e.getMessage());
        }
        return produto;
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

                Produto produto = new Produto(id, nome, imagem, descricao, valor);
                lista.add(produto);
            }
            return Resultado.sucesso("Lista", Collections.unmodifiableList(lista));
        } catch(SQLException e){
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Produto atualizar(Produto produto){
        String sql = "UPDATE pi_cardapio SET  nome = ?, imagem = ?, descricao = ?, valor = ? WHERE  id = "+produto.getId();
		try (Connection con = fabrica.getConnection();
            PreparedStatement pstm = con.prepareStatement(sql)) {
			pstm.setString(1, produto.getNome());
			pstm.setString(2, produto.getImagem());
			pstm.setString(3, produto.getDescricao());
            pstm.setDouble(4, produto.getValor());
            pstm.executeUpdate();
		} catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar o produto no banco de dados.", e);
        } 
		return produto;
    }

    @Override
    public boolean excluir(Long id){
        String sql = "DELETE FROM pi_cardapio WHERE id = ?";
        
        try (Connection con = fabrica.getConnection();
            PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setLong(1, id);
            int rows = pstm.executeUpdate();
            if (rows == 1){
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao excluir o produto no banco de dados.", e);
        }
        return false;
    }
}