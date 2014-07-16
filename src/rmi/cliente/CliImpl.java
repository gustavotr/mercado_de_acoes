/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi.cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
    private JLabel resposta;
    private JTextField nomeDoCliente;
    private InterfaceServ servidor;
    private JPanel panel;
        
    public CliImpl(InterfaceServ servidor) throws RemoteException {
        
        this.servidor = servidor;
        
        initComponents();
        
    }      
    
    public void call() throws RemoteException{
        servidor.chamar(nomeDoCliente.getText(), this);
    }

    @Override
    public void echo(String str) throws RemoteException {                             
        resposta.setText(str);
    }
    
    public void initComponents(){
        jFrame = new JFrame("Servidor");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setBounds(0, 0, 800, 600);
        jFrame.setResizable(false);               
        jFrame.setVisible(true);
        
        panel = new JPanel();
        panel.setSize(800, 600);        
        panel.setVisible(true);
        
        JLabel label = new JLabel("Digite seu nome:");
        label.setBounds(300, 210, 800, 20);
        panel.add(label);
        
        nomeDoCliente = new JTextField();
        nomeDoCliente.setBounds(300, 250, 200, 20);
        panel.add(nomeDoCliente);
                        
        JButton enviar = new JButton("Enviar");
        enviar.setBounds(510, 250, 100, 20);
        enviar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {                
                try {
                    call();
                } catch (RemoteException ex) {
                    Logger.getLogger(CliImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        panel.add(enviar);
        
        
        
        resposta = new JLabel();
        resposta.setBounds(300, 300, 200, 20);
        panel.add(resposta);
        
        jFrame.add(panel);
        jFrame.repaint();
    }
    
}
