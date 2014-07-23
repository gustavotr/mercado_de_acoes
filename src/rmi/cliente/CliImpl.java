/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.cliente;

import java.awt.Dimension;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import rmi.InterfaceCli;
import rmi.InterfaceServ;

/**
 *
 * @author a1097075
 */
public class CliImpl extends UnicastRemoteObject implements InterfaceCli {

    private JFrame jFrame;
    private Object[][] monitor;
    private InterfaceServ servidor;
    private JTable tabelaDoCliente;
    private JTable tabelaDeAcoesMonitoradas;
    private JTable tabelaDoServidor;
    private InterfaceCli cliente;
    private ArrayList<Empresa> mercadoDeAcoes;

    public CliImpl(InterfaceServ servidor) throws RemoteException {
        this.cliente = this;
        this.servidor = servidor;        
        
        mercadoDeAcoes = new ArrayList<>();

        mercadoDeAcoes.add(new Empresa("Tim"));
        mercadoDeAcoes.add(new Empresa("Claro"));
        mercadoDeAcoes.add(new Empresa("Vivo"));
        mercadoDeAcoes.add(new Empresa("Oi"));
        
        for(Empresa emp : mercadoDeAcoes){
            String nome = emp.getNome();
            servidor.cadastrarEmpresa(nome);
        }

        initComponents();

    }

    public void initComponents() {
        jFrame = new JFrame("Cliente");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setBounds(0, 0, 800, 600);
        jFrame.setLayout(new GridLayout(1, 2));
        jFrame.setVisible(true);

        /* ------------ Area do Servidor ---------------- */

        JPanel painelDoServidor = new JPanel();
        painelDoServidor.setLayout(new BoxLayout(painelDoServidor, BoxLayout.Y_AXIS));

        JLabel acoesMonitoradas = new JLabel("Ações Monitoradas");
        painelDoServidor.add(acoesMonitoradas);

        String[] nomeDasColunas = {"Empresa", "Interece", "Valor", "Quantidade"};

        TableModel tabelaDeAcoesMonitoradasModel =  new DefaultTableModel(monitor, nomeDasColunas);
        tabelaDeAcoesMonitoradas = new JTable(tabelaDeAcoesMonitoradasModel);
        JScrollPane scrollPaneDeAcoesMonitoradas = new JScrollPane(tabelaDeAcoesMonitoradas);
        scrollPaneDeAcoesMonitoradas.setPreferredSize(new Dimension(200, 200));
        tabelaDeAcoesMonitoradas.setFillsViewportHeight(true);

        painelDoServidor.add(scrollPaneDeAcoesMonitoradas);
        
        nomeDasColunas = new String[]{"Ações do Servidor"};

        TableModel tabelaDoServidorModel = new DefaultTableModel(nomeDasColunas, 0);
        tabelaDoServidor = new JTable(tabelaDoServidorModel);
        JScrollPane scrollPaneDoServidor = new JScrollPane(tabelaDoServidor);
        scrollPaneDoServidor.setPreferredSize(new Dimension(200, 200));
        tabelaDoServidor.setFillsViewportHeight(true);

        painelDoServidor.add(scrollPaneDoServidor);
        
        
        

        JButton comprar = new JButton("Comprar");
        comprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tabelaDoServidor.getSelectedRow();
                String empresa = (String) tabelaDoServidor.getValueAt(row, 0);

                int quantidade = 1;

                String temp = JOptionPane.showInputDialog(jFrame,
                        "Quanto deseja pagar por ação da ''" + empresa + "''?", null);

                double preco = Double.parseDouble(temp);
                
                temp = JOptionPane.showInputDialog(jFrame,
                        "Por quanto tempo deseja manter a requisição?", null);
                
                int tempo = Integer.parseInt(temp);

                int resposta = JOptionPane.showConfirmDialog(jFrame, "Comprar " + quantidade + " ações da ''" + empresa + "'' por R$" + preco);

                if (resposta == JOptionPane.YES_OPTION) {
                    try {
                        servidor.comprar(cliente, empresa, quantidade, tempo, preco);
                    } catch (RemoteException ex) {
                        Logger.getLogger(CliImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        JButton monitorar = new JButton("Monitorar");
        monitorar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tabelaDoServidor.getSelectedRow();
                String empresa = (String) tabelaDoServidor.getValueAt(row, 0);

                int resposta = JOptionPane.showConfirmDialog(jFrame, "Deseja acompanhar as ações da ''" + empresa + "'' ?");

                if (resposta == JOptionPane.YES_OPTION) {

                    try {
                        servidor.monitorar(cliente, empresa);
                    } catch (RemoteException ex) {
                        Logger.getLogger(CliImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        JButton atualizar = new JButton("Atualizar ações do Servidor");
        atualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Object[][] obj = servidor.listarEmpresas();
                    tabelaDoServidor.setModel(new DefaultTableModel(obj, new Object[]{"Ações do Servidor"}));
                } catch (RemoteException ex) {
                    Logger.getLogger(CliImpl.class.getName()).log(Level.SEVERE, null, ex);
                }                
            }
        });

        JPanel botoesDoServidor = new JPanel();
        botoesDoServidor.add(comprar);
        botoesDoServidor.add(monitorar);
        botoesDoServidor.add(atualizar);

        painelDoServidor.add(botoesDoServidor);


        /* ------------ Area do Cliente ---------------- */

        JPanel painelDoCliente = new JPanel();
        painelDoCliente.setLayout(new BoxLayout(painelDoCliente, BoxLayout.Y_AXIS));

        JLabel areaDoCliente = new JLabel("Sua Ações");
        painelDoCliente.add(areaDoCliente);

        String[] nomeDasColunasCliente = {"Minhas ações", "Número de ações", "Valor"};

        TableModel tabelaDoClienteModel = new DefaultTableModel(listar(), nomeDasColunasCliente);
        tabelaDoCliente = new JTable(tabelaDoClienteModel);
        JScrollPane scrollPaneDoCliente = new JScrollPane(tabelaDoCliente);
        scrollPaneDoCliente.setPreferredSize(new Dimension(200, 400));
        tabelaDoCliente.setFillsViewportHeight(true);

        painelDoCliente.add(scrollPaneDoCliente);

        JButton vender = new JButton("Vender");
        vender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tabelaDoCliente.getSelectedRow();
                String empresa = (String) tabelaDoCliente.getValueAt(row, 0);
                
                int quantidade = 1;

                String temp = JOptionPane.showInputDialog(jFrame,
                        "Por quanto deseja vender uma ação da ''" + empresa + "'' ?", null);

                double preco = Double.parseDouble(temp);
                
                temp = JOptionPane.showInputDialog(jFrame,
                        "Por quanto tempo deseja manter a requisição?", null);
                
                int tempo = Integer.parseInt(temp);

                int resposta = JOptionPane.showConfirmDialog(jFrame, quantidade + "  ação da " + empresa + " para serem vendidas por R$" + preco);

                if (resposta == JOptionPane.YES_OPTION) {
                    try {
                        servidor.vender(cliente, empresa, quantidade, tempo, preco);
                    } catch (RemoteException ex) {
                        Logger.getLogger(CliImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
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
    
     public Object[][] listar(){
        Object[][] acoes = new Object[mercadoDeAcoes.size()][3];

        for (int i = 0; i < mercadoDeAcoes.size(); i++) {
            Empresa emp = mercadoDeAcoes.get(i);
            acoes[i][0] = emp.getNome();
            acoes[i][1] = emp.getQuantidade();
            acoes[i][2] = emp.getPrecoMedio();
        }

        return acoes;
    }

    @Override
    public void notificar(String notificacao, Object[] obj) throws RemoteException {
        if(notificacao.equals("monitorar")){            
            String[] nomeDasColunas = {"Empresa", "Interece", "Valor", "Quantidade"};
            DefaultTableModel model = new DefaultTableModel(nomeDasColunas,0);
            for(Object row: obj){
                model.addRow((Object[]) row);
            }
            tabelaDeAcoesMonitoradas.setModel(model);
        }  
        if(notificacao.equals("compra efetuada")){
            String empresa = (String) obj[0];
            double valor = (double) obj[1];
            int quantidade = (int) obj[2];
            
            JOptionPane.showMessageDialog(jFrame, "Ação da empresa ''"+empresa+"'' comprada por R$"+valor);
            DefaultTableModel model = (DefaultTableModel) tabelaDoCliente.getModel();
            
            for(int i = 0; i < model.getRowCount(); i++){
                if(model.getValueAt(i, 0).equals(empresa)){
                    int temp = (int) model.getValueAt(i, 1);
                    temp = temp + quantidade;
                    model.setValueAt(temp, i, 1);
                    return;
                }
            }
        }
        if(notificacao.equals("venda efetuada")){
            String empresa = (String) obj[0];
            double valor = (double) obj[1];
            int quantidade = (int) obj[2];
            
            JOptionPane.showMessageDialog(jFrame, "Ação da empresa ''"+empresa+"'' vendida por R$"+valor);
            DefaultTableModel model = (DefaultTableModel) tabelaDoCliente.getModel();
            
            for(int i = 0; i < model.getRowCount(); i++){
                if(model.getValueAt(i, 0).equals(empresa)){
                    int temp = (int) model.getValueAt(i, 1);
                    temp = temp - quantidade;
                    model.setValueAt(temp, i, 1);
                    return;
                }
            }
        }
        
        System.out.println(notificacao);
        
    }
}
