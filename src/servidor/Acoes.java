/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servidor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import servidor.rmi.NotificacaoPush;

/**
 *
 * @author Gustavo
 */
public class Acoes implements Serializable {

    private static final long serialVersionUID = 1L;
    private String empresa;
    private int quantidade;
    private int preco;
    private List<NotificacaoPush> interessadosNasAcoes;

    public Acoes(String empresa, int quantidade, int preco) {
        super();
        this.empresa = empresa;
        this.quantidade = quantidade;
        this.preco = preco;
        this.interessadosNasAcoes = new ArrayList<>();
    }
    
    
    
   
    
    
    
}
