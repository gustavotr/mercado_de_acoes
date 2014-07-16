/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi.cliente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import rmi.InterfaceCli;
import rmi.InterfaceServ;

/**
 *
 * @author a1097075
 */
public class CliImpl extends UnicastRemoteObject implements InterfaceCli{
        
    public CliImpl() throws RemoteException {
        
    }   

    @Override
    public void echo(String str) throws RemoteException {
        System.out.println(str);
    }
}
