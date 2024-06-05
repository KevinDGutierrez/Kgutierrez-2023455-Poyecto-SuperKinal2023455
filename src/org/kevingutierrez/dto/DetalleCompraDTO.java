/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kevingutierrez.dto;

import org.kevingutierrez.model.DetalleCompra;


public class DetalleCompraDTO {
    private static DetalleCompraDTO instance;
    private DetalleCompra detalleCompra;
    
    private DetalleCompraDTO(){
    
    }
    
    public static DetalleCompraDTO getDetalleCompraDTO(){
        if(instance == null){
            instance = new DetalleCompraDTO();
        }
        
        return instance;
    }

    public DetalleCompra getDetalleCompra() {
        return detalleCompra;
    }

    public void setDetalleCompra(DetalleCompra detalleCompra) {
        this.detalleCompra = detalleCompra;
    }
}
