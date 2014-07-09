package servidor.rmi;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NotificacaoPush extends Remote {
    
    public void notificar(String retorno) throws RemoteException;
}