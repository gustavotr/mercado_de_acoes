/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cliente;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Gustavo
 */
public interface Mercado extends Remote{
    
    public void comprar(String empresa, int quantidade, double preco) throws RemoteException;
    
    public void vender(String empresa, int quantidade, double preco) throws RemoteException;
    
    public void monitorar(String acao) throws RemoteException;
        
}
