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
public class ServImpl implements InterfaceServ{
    
    private String nomeCliente;
    private InterfaceCli interfaceCli;

    @Override
    public void chamar(String nomeCliente, InterfaceCli interfaceCli) throws RemoteException {
        this.interfaceCli = interfaceCli;
        this.nomeCliente = nomeCliente;
        interfaceCli.echo("Testandoooooo");        
    }

    
    
}
