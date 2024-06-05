/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kevingutierrez.model;

import java.sql.Date;


public class Promocion {
    private int promocionId;
    private double precioProm;
    private String descripcionProm;
    private Date fechaInicio;
    private Date fechaFin;
    private int productoId;
    private String producto;

    public Promocion() {
    }

    public Promocion(int promocionId, double precioProm, String descripcionProm, Date fechaInicio, Date fechaFin, String producto) {
        this.promocionId = promocionId;
        this.precioProm = precioProm;
        this.descripcionProm = descripcionProm;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.producto = producto;
    }

    public int getPromocionId() {
        return promocionId;
    }

    public void setPromocionId(int promocionId) {
        this.promocionId = promocionId;
    }

    public double getPrecioProm() {
        return precioProm;
    }

    public void setPrecioProm(double precioProm) {
        this.precioProm = precioProm;
    }

    public String getDescripcionProm() {
        return descripcionProm;
    }

    public void setDescripcionProm(String descripcionProm) {
        this.descripcionProm = descripcionProm;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "Id: " + promocionId + " | " + producto;
    }

    
    
}
