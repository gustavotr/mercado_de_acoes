/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi.servidor;

import rmi.InterfaceCli;

/**
 *
 * @author Gustavo
 */
public class Acao {
    
    protected double precoDeVenda;
    protected double precoDeCompra;
    protected InterfaceCli dono;

    public Acao(double precoDeCompra) {
        this.precoDeVenda = 0;
        this.precoDeCompra = precoDeCompra;
        this.dono = null;
    }
    
}
