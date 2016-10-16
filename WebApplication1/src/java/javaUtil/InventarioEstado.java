/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaUtil;

import entity.Estado;

/**
 *
 * @author Flever
 */
public class InventarioEstado {
    
    private Estado estado;
    private Integer cantidad;

    public InventarioEstado() {
    }

    public InventarioEstado(Estado estado, Integer cantidad) {
        this.estado = estado;
        this.cantidad = cantidad;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    
    
    
}
