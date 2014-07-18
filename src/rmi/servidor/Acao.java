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
    protected boolean disponivel;

    public Acao(double precoDeCompra) {
        this.precoDeVenda = 0;
        this.precoDeCompra = precoDeCompra;
        this.dono = null;
        this.disponivel = true;
    }

    public void setDono(InterfaceCli dono) {
        this.dono = dono;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void setPrecoDeVenda(double precoDeVenda) {
        this.precoDeVenda = precoDeVenda;
    }

    public void setPrecoDeCompra(double precoDeCompra) {
        this.precoDeCompra = precoDeCompra;
    }
        
    public double calculaPrecoMedio(){
        double precoMedio = (precoDeVenda + precoDeCompra)/2;
        return precoMedio;
    }
}
