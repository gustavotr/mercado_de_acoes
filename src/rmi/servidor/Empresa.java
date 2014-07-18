/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.servidor;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;
import rmi.InterfaceCli;

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

        for (int i = 0; i < quantidade; i++) {
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

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void compraAcao(InterfaceCli cliente, double precoDeCompra) throws RemoteException {
        boolean aux = true;
        int i = 0;
        InterfaceCli temp = null;
        double preco;

        while (aux) {
            if (!acoes.get(i).disponivel) {
                i++;
            } else {
                temp = acoes.get(i).dono;
                acoes.get(i).setDono(cliente);
                acoes.get(i).setDisponivel(false);
                acoes.get(i).setPrecoDeCompra(precoDeCompra);
                preco = acoes.get(i).calculaPrecoMedio();
                aux = false;
                System.out.println("1 ação vendida da " + nome + " por R$" + preco);
                temp.notificar("Ação vendida!", null);
            }
        }
    }
    
        public void vendeAcao(InterfaceCli cliente, double precoDeVenda) throws RemoteException {
        boolean aux = true;
        int i = 0;

        while (aux) {
            if (!(acoes.get(i).dono == cliente)) {
                i++;
            } else {
                acoes.get(i).setDisponivel(true);
                acoes.get(i).setPrecoDeVenda(precoDeVenda);
                aux = false;
            }
        }
    }
}
