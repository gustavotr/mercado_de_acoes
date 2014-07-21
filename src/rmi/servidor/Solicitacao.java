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
    private int tempo;
    private int quantidade;
    private double preco;

    public Solicitacao(InterfaceCli solicitante, String nome, int quantidade, int tempo, double preco) {
        this.solicitante = solicitante;
        this.nome = nome;
        this.tempo = tempo;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public InterfaceCli getSolicitante() {
        return solicitante;
    }

    public String getNome() {
        return nome;
    }

    public int getTempo() {
        return tempo;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }
    
}
