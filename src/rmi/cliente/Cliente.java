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
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.InterfaceServ;

/**
 *
 * @author a1097075
 */
public class Cliente {
    
    public static void main(String[] args) {
        try {
        
            
            Registry r = LocateRegistry.getRegistry(2024);        
            CliImpl cli = new CliImpl((InterfaceServ) r.lookup("servidor"));
            cli.chamar("Gustavo");
        
        
        
        } catch (RemoteException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
