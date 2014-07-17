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
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
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
                 
        /* ------------ Area do Servidor ---------------- */
                       
        JPanel painelDoServidor = new JPanel();
        painelDoServidor.setLayout(new BoxLayout(painelDoServidor, BoxLayout.Y_AXIS));
        
        JLabel areaDoServidor = new JLabel("Ações do Servidor");
        painelDoServidor.add(areaDoServidor);
        
        String [] nomeDasColunas = {"Empresa", "Número de ações", "Valor"};
        
        TableModel tabelaDoServidorModel = new DefaultTableModel(monitor, nomeDasColunas);
        JTable tabelaDoServidor = new JTable(tabelaDoServidorModel);        
        JScrollPane scrollPaneDoServidor = new JScrollPane(tabelaDoServidor);
        tabelaDoServidor.setFillsViewportHeight(true);
        
        painelDoServidor.add(scrollPaneDoServidor);
        
        JButton comprar = new JButton("Comprar");
        comprar.addActionListener(new ActionListener() {

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
       
        JPanel botoesDoServidor = new JPanel();
        botoesDoServidor.add(comprar);
        botoesDoServidor.add(monitorar);
                
        painelDoServidor.add(botoesDoServidor);        
        
        
        /* ------------ Area do Cliente ---------------- */
        
        JPanel painelDoCliente = new JPanel();
        painelDoCliente.setLayout(new BoxLayout(painelDoCliente, BoxLayout.Y_AXIS));
        
        JLabel areaDoCliente = new JLabel("Sua Ações");
        painelDoCliente.add(areaDoCliente);
        
        String [] nomeDasColunasCliente = {"Minhas ações", "Número de ações", "Valor"};
        
        TableModel tabelaDoClienteModel = new DefaultTableModel(nomeDasColunasCliente, 0);        
        JTable tabelaDoCliente = new JTable(tabelaDoClienteModel);
        JScrollPane scrollPaneDoCliente = new JScrollPane(tabelaDoCliente);
        tabelaDoCliente.setFillsViewportHeight(true);
        
        painelDoCliente.add(scrollPaneDoCliente); 
        
         JButton vender = new JButton("Vender");
        vender.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {                
               
            }
        });
        
        JPanel botoesDoCliente = new JPanel();
        botoesDoCliente.add(vender);
        
        painelDoCliente.add(botoesDoCliente);   
        
        
        /* ------------- JFrame ------------ */
        
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
