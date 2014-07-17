/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author a1097075
 */
public interface InterfaceServ extends Remote{ 
    
    public void comprar(InterfaceCli interfaceCliente, String empresa, int quantidade, double precoDeCompra) throws RemoteException;
    public void vender(InterfaceCli interfaceCliente, String empresa, int quantidade, double precoDeVenda) throws RemoteException;
    public void monitorar(InterfaceCli interfaceCliente, String empresa) throws RemoteException;
    public void listar(InterfaceCli interfaceCliente) throws RemoteException;
    
}
