package com.dompedroburguer.model.dao;

import com.dompedroburguer.model.Checkout;
import com.dompedroburguer.model.FabricaConexoes;

public class JDBCCheckoutDAO implements CheckoutDAO{

    private FabricaConexoes fabrica;

    public JDBCCheckoutDAO(FabricaConexoes fabrica){
        this.fabrica = fabrica;
    }

    @Override
    public boolean inserir(Checkout checkout) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
