/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author a1097075
 */
public interface InterfaceCli extends Remote{
    
    public void gerarMonitor(ArrayList<String> dados) throws RemoteException;
    public void notificar(String empresa, double valorDaAcao, int quantidade) throws RemoteException;
    public void notificar(String echo) throws RemoteException;
    
}
