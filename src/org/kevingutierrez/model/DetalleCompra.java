/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kevingutierrez.model;

/**
 *
 * @author informatica
 */
public class DetalleCompra {
    private int detalleCompraId;
    private int cantidadCompra;
    private int productoId;
    private int compraId;
   
    public DetalleCompra(){
   
    }

    public DetalleCompra(int detalleCompraId, int cantidadCompra, int productoId, int compraId) {
       this.detalleCompraId = detalleCompraId;
       this.cantidadCompra = cantidadCompra;
       this.productoId = productoId;
       this.compraId = compraId;
   }

    public int getDetalleCompraId() {
        return detalleCompraId;
    }

    public int getCantidadCompra() {
        return cantidadCompra;
    }

    public int getProductoId() {
        return productoId;
    }

    public int getCompraId() {
        return compraId;
    }

    public void setDetalleCompraId(int detalleCompraId) {
        this.detalleCompraId = detalleCompraId;
    }

    public void setCantidadCompra(int cantidadCompra) {
        this.cantidadCompra = cantidadCompra;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }

    @Override
    public String toString() {
        return "DetalleCompras{" + "detalleCompraId=" + detalleCompraId + ", cantidadCompra=" + cantidadCompra + ", productoId=" + productoId + ", compraId=" + compraId + '}';
    }
}
