/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi.servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import rmi.InterfaceCli;
import rmi.InterfaceServ;

/**
 *
 * @author a1097075
 */
public class ServImpl extends UnicastRemoteObject implements InterfaceServ{
    
    private ArrayList<Empresa> mercadoDeAcoes;
    
    
    public ServImpl() throws RemoteException{  
        
        mercadoDeAcoes = new ArrayList<>();
        
        mercadoDeAcoes.add(new Empresa("Tim"));
        mercadoDeAcoes.add(new Empresa("Claro"));
        mercadoDeAcoes.add(new Empresa("Vivo"));
        mercadoDeAcoes.add(new Empresa("Oi"));        
        
    }

    @Override
    public void comprar(InterfaceCli interfaceCliente, String empresa, int quantidade, double precoDeCompra) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void vender(InterfaceCli interfaceCliente, String empresa, int quantidade, double precoDeVenda) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void monitorar(InterfaceCli interfaceCliente, String empresa) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void listar(InterfaceCli interfaceCliente) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
}
