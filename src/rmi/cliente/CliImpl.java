/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi.cliente;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import rmi.InterfaceCli;
import rmi.InterfaceServ;

/**
 *
 * @author a1097075
 */
public class CliImpl extends UnicastRemoteObject implements InterfaceCli{
    
    private JFrame jFrame;
    private Object [] [] monitor;
    private InterfaceServ servidor;
        
    public CliImpl(InterfaceServ servidor) throws RemoteException {
        
        this.servidor = servidor;
        monitor = servidor.listar();
        
        initComponents();
        
    }      
       
    public void initComponents(){
        jFrame = new JFrame("Cliente");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setBounds(0, 0, 800, 600);
        jFrame.setLayout(new GridLayout(1, 2));
        jFrame.setVisible(true);
        
        JPanel painelDoCliente = new JPanel();      
        
        JPanel painelDoServidor = new JPanel();
        
        String [] nomeDasColunas = {"Empresa", "Número de ações", "Valor"};
        
        JTable tabelaDoServidor = new JTable(monitor, nomeDasColunas);
        
        JScrollPane scrollPaneDoServidor = new JScrollPane(tabelaDoServidor);
        tabelaDoServidor.setFillsViewportHeight(true);
        
        painelDoServidor.add(scrollPaneDoServidor);
        
        String [] nomeDasColunasCliente = {"Minhas ações", "Número de ações", "Valor"};
        JTable tabelaDoCliente = new JTable(monitor, nomeDasColunasCliente);
        JScrollPane scrollPaneDoCliente = new JScrollPane(tabelaDoCliente);
        tabelaDoCliente.setFillsViewportHeight(true);
        
        painelDoCliente.add(scrollPaneDoCliente);       
        
                        
        JButton comprar = new JButton("Comprar");
        comprar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {                
               
            }
        });
        
        JButton vender = new JButton("Vender");
        vender.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {                
               
            }
        });
        
        JButton monitorar = new JButton("Monitorar");
        monitorar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {                
               
            }
        });
      
        
        painelDoServidor.add(comprar);
        painelDoCliente.add(vender);
        painelDoServidor.add(monitorar);
        
        jFrame.add(painelDoCliente);
        jFrame.add(painelDoServidor);
        jFrame.pack();
    }

    @Override
    public void gerarMonitor(ArrayList<String> dados) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notificar(String empresa, double valorDaAcao, int quantidade) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
