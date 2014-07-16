/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi.servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import rmi.InterfaceCli;
import rmi.InterfaceServ;

/**
 *
 * @author a1097075
 */
public class ServImpl extends UnicastRemoteObject implements InterfaceServ{
    
    
    public ServImpl() throws RemoteException{        
    }
    
    @Override
    public void chamar(String nomeCliente, InterfaceCli interfaceCli) throws RemoteException {       
        interfaceCli.echo("Olá " + nomeCliente);        
    }   
    
}
