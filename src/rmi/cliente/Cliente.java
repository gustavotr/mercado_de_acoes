/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi.cliente;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import rmi.InterfaceServ;

/**
 *
 * @author a1097075
 */
public class Cliente{
    
    public static void main(String[] args) throws RemoteException, NotBoundException {     
            
        Registry r = LocateRegistry.getRegistry(1099);        
        InterfaceServ servidor = (InterfaceServ) r.lookup("servidor");
        CliImpl cliente = new CliImpl(servidor);       
    }
    
}
