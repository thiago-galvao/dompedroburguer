package com.dompedroburguer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dompedroburguer.model.FabricaConexoes;
import com.dompedroburguer.model.FormasPagamento;

public class JDBCFormasPagamentoDAO implements FormasPagamentoDAO{

    private FabricaConexoes fabrica;

    
    public JDBCFormasPagamentoDAO(FabricaConexoes fabrica){
        this.fabrica = fabrica;
    }

    @Override
    public List<FormasPagamento> listar() {
        String sql = "SELECT * FROM pi_tipo_pagamento";

        ArrayList<FormasPagamento> lista = new ArrayList<>();
        lista.clear();

        try(Connection con = fabrica.getConnection();
            PreparedStatement pstm = con.prepareCall(sql)){
                ResultSet result = pstm.executeQuery();
                while(result.next()){
                    int id = result.getInt("id");
                    String descricao = result.getString("descricao");
                    FormasPagamento pagamento = new FormasPagamento(id, descricao);
                    lista.add(pagamento);
                }
            return lista;
            } catch (SQLException e){
                e.getMessage();
            }
            return null;
    }

    @Override
    public FormasPagamento buscar(int id){
        String sql = "SELECT * FROM pi_tipo_pagamento WHERE id=?";
        FormasPagamento formasPagamento = null;
        try (Connection con = fabrica.getConnection();
            PreparedStatement pstm = con.prepareStatement(sql)){
            pstm.setInt(1, id);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    formasPagamento = new FormasPagamento();
                    formasPagamento.setId(rs.getInt("id"));
                    formasPagamento.setDescricao(rs.getString("descricao"));
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return formasPagamento;
    }

    @Override
    public boolean inserir(String descricao){
        String sql = "INSERT INTO pi_tipo_pagamento (descricao) VALUES (?)";

        try (Connection con = fabrica.getConnection();
            PreparedStatement pstm = con.prepareStatement(sql)){
                pstm.setString(1, descricao);
                pstm.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        return false;
    }

    @Override
    public boolean excluir(FormasPagamento formaPagamento){
        String sql = "DELETE FROM pi_tipo_pagamento WHERE id = ?";

        try (Connection con = fabrica.getConnection();
            PreparedStatement pstm = con.prepareStatement(sql)){
            pstm.setInt(1, formaPagamento.getId());
            pstm.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean atualizar(FormasPagamento formaPagamento){
        String sql = "UPDATE pi_tipo_pagamento SET descricao = ? WHERE id = "+formaPagamento.getId();

        try(Connection con = fabrica.getConnection();
            PreparedStatement pstm = con.prepareStatement(sql)){
                pstm.setString(1, formaPagamento.getDescricao());
                pstm.executeUpdate();
                return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
