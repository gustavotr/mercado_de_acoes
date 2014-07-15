/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi;

import java.rmi.RemoteException;

/**
 *
 * @author a1097075
 */
public class CliImpl implements InterfaceCli{
    
    private InterfaceServ interfaceServ;
    private String stringRecebida;

    @Override
    public void echo(String str) throws RemoteException {
        System.out.println(str);
    }
    
    public void chamar(String nome) throws RemoteException{
        interfaceServ.chamar(nome, this);
    }
    
}
