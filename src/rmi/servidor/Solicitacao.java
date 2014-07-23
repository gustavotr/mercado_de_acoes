/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.servidor;

import rmi.InterfaceCli;
/**
 *
 * @author Casa
 */
public class Solicitacao {
    
    private InterfaceCli solicitante;
    private String nome;
    private long tempo;
    private int quantidade;
    private double preco;

    public Solicitacao(InterfaceCli solicitante, String nome, int quantidade, int tempo, double preco) {
        this.solicitante = solicitante;
        this.nome = nome;
        this.tempo = System.currentTimeMillis() + tempo;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public InterfaceCli getSolicitante() {
        return solicitante;
    }

    public String getNome() {
        return nome;
    }

    public long getTempo() {
        return tempo;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }
    
    
}
