/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi.servidor;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Gustavo
 */
public class Empresa {
    
    private String nome;
    private ArrayList<Acao> acoes;
    private double precoMedio;
    private int quantidade;

    public Empresa(String nome) {
        
        this.nome = nome;
        this.acoes = new ArrayList<>();
        
        Random rnd = new Random();
        
        this.precoMedio = (double) rnd.nextInt(10000) / 100;
        
        this.quantidade = rnd.nextInt(10) + 1;
        
        for(int i = 0; i < quantidade; i++){
            acoes.add(new Acao(precoMedio));
        }
    }

    public String getNome() {
        return nome;
    }

    public double getPrecoMedio() {
        return precoMedio;
    }

    public int getQuantidade() {
        return quantidade;
    }  
    
    
}
