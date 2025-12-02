package com.dompedroburguer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dompedroburguer.model.Cliente;
import com.dompedroburguer.model.FabricaConexoes;

public class JDBCClienteDAO implements ClienteDAO {
    private FabricaConexoes fabrica;

    public JDBCClienteDAO(FabricaConexoes fabrica){
        this.fabrica = fabrica;
    }

    @Override
    public List<Cliente> listar() {
        String sql = "SELECT *, ST_AsText(latitude_longitude) AS lat_long FROM pi_cliente";
        ArrayList<Cliente> lista = new ArrayList<>();
        lista.clear();

        try (Connection con = fabrica.getInstance().getConnection(); 
            PreparedStatement pstm = con.prepareCall(sql)){
            ResultSet result = pstm.executeQuery();
            while (result.next()){
                int id = result.getInt("id");
                String nome = result.getString("nome");
                String telefone = result.getString("telefone");
                String endereco = result.getString("endereco");
                boolean ativo = result.getBoolean("ativo");
                Date dataCadastro = result.getDate("data_cadastro");

                String latitudeLongitude = result.getString("lat_long");
                latitudeLongitude = latitudeLongitude.replace("POINT(", "").replace(")", "");

                String complemento = result.getString("complemento");
                Date dataNasc = result.getDate("data_nasc");

                String sexo = result.getString("sexo");
                char sexoChar = '-';
                if (sexo != null && !sexo.isEmpty()) {
                    sexoChar = sexo.toUpperCase().charAt(0); 
                }
                

                String email = result.getString("email");
                Cliente cliente = new Cliente(id, nome, telefone, endereco, ativo, dataCadastro, latitudeLongitude, complemento, dataNasc, sexoChar, email);
                lista.add(cliente);
            }
            return lista;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean desativar(Long id){
        String sql = "UPDATE pi_cliente SET ativo = 0 WHERE id = ?";

        try (Connection con = fabrica.getConnection();
            PreparedStatement pstm = con.prepareStatement(sql)){
            pstm.setLong(1, id);
            int rows = pstm.executeUpdate();
            if (rows == 1){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao desativar cliente: ", e);
        }
        return false;
    }

    @Override
    public Cliente buscar(Long id){
        String sql = "SELECT *, ST_AsText(latitude_longitude) AS lat_long FROM pi_cliente WHERE id = ?";
        Cliente cliente = null;
        try (Connection con = fabrica.getConnection();
            PreparedStatement pstm = con.prepareCall(sql)){
            pstm.setLong(1, id);
            
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setTelefone(rs.getString("telefone"));
                    cliente.setEndereco(rs.getString("endereco"));
                    cliente.setAtivo(rs.getBoolean("ativo"));
                    cliente.setDataCadastro(rs.getDate("data_cadastro")); 
                    String coordenadas = rs.getString("lat_long");
                    coordenadas = coordenadas.replace("POINT(", "").replace(")", "");
                    cliente.setLatitudeLongitude(coordenadas);
                    
                    cliente.setComplemento(rs.getString("complemento"));
                    cliente.setDataNasc(rs.getDate("data_nasc"));
                    String sexo = rs.getString("sexo");
                    char sexoChar = '-';
                    if (sexo != null && !sexo.isEmpty()) {
                        sexoChar = sexo.toUpperCase().charAt(0); 
                    }
                    cliente.setSexo(sexoChar);
                    cliente.setEmail(rs.getString("email"));
                }
            }
        } catch (SQLException e) {            
            System.err.println("Erro ao tentar encontrar o cliente " + e.getMessage());
        }
        return cliente;
    }

    @Override
    public boolean atualizar(Cliente cliente){
        String sql = "UPDATE pi_cliente SET nome=?, telefone=?, endereco=?, ativo=?, latitude_longitude=ST_GeomFromText(?, 4326), complemento=?, data_nasc=?, sexo=?, email=? WHERE id = "+cliente.getId();
        try(Connection con = fabrica.getConnection();
            PreparedStatement pstm = con.prepareStatement(sql)) {
                pstm.setString(1, cliente.getNome());
                pstm.setString(2, cliente.getTelefone());
                pstm.setString(3, cliente.getEndereco());
                pstm.setBoolean(4, cliente.isAtivo());
                String latLong = cliente.getLatitudeLongitude();
                String inicio = "POINT(";
                String fim = ")";
                String coordenadas = inicio + latLong + fim;
                pstm.setString(5, coordenadas);
                pstm.setString(6, cliente.getComplemento());
                pstm.setObject(7, cliente.getDataNasc());
                char sexo = cliente.getSexo();
                String sexoString = String.valueOf(sexo);
                pstm.setString(8, sexoString);
                pstm.setString(9, cliente.getEmail());
                pstm.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erro ao tentar atualizar cliente " + e.getMessage());
            }
        return false;
    }

    @Override
    public boolean inserir(Cliente cliente){
        String sql = "INSERT INTO pi_cliente (nome, telefone, endereco, ativo, data_cadastro, latitude_longitude, complemento, data_nasc, sexo, email) VALUES (?, ?, ?, ?, ?, ST_GeomFromText(?, 4326), ?, ?, ?, ?)";
        try(Connection con = fabrica.getConnection();
            PreparedStatement pstm = con.prepareStatement(sql)){
            pstm.setString(1, cliente.getNome());
            pstm.setString(2, cliente.getTelefone());
            pstm.setString(3, cliente.getEndereco());
            pstm.setBoolean(4, cliente.isAtivo());
            pstm.setDate(5, new java.sql.Date(cliente.getDataCadastro().getTime()));
            String latLong = cliente.getLatitudeLongitude();
            String inicio = "POINT(";
            String fim = ")";
            String coordenadas = inicio + latLong + fim;
            pstm.setString(6, coordenadas);
            pstm.setString(7, cliente.getComplemento());
            pstm.setObject(8, cliente.getDataNasc());
            char sexo = cliente.getSexo();
            String sexoString = String.valueOf(sexo);
            pstm.setString(9, sexoString);
            pstm.setString(10, cliente.getEmail());
            pstm.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}

