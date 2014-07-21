/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.servidor;

import java.awt.GridLayout;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
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
public class ServImpl extends UnicastRemoteObject implements InterfaceServ, Runnable {

    private ArrayList<Solicitacao> solicitacoesCompra;
    private ArrayList<Solicitacao> solicitacoesVenda;
    private ArrayList<String> empresas;
    private JTable mercadoDeVendas;
    private JTable mercadoDeCompras;

    public ServImpl() throws RemoteException {

        solicitacoesCompra = new ArrayList<>();
        solicitacoesVenda = new ArrayList<>();
        empresas = new ArrayList<>();               
        
        initComponents();
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            if (solicitacoesCompra.size() == 0 || solicitacoesVenda.size() == 0) {
                try {
                    //System.out.print(".");
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                for (int auxCompra = 0; auxCompra <= solicitacoesCompra.size(); auxCompra++) {
                    boolean aux = true;
                    int auxVenda = 0;
                    while (aux) {
                        if(solicitacoesCompra.get(auxCompra).getNome().equals(solicitacoesVenda.get(auxVenda).getNome())){
                            try {
                                
                                System.out.println("Houve uma venda");
                                double valorMedio = (solicitacoesCompra.get(auxCompra).getPreco() + solicitacoesVenda.get(auxVenda).getPreco())/2;
                                String compra = "compra efetuada";                                
                                Object [] obj = new Object[]{solicitacoesVenda.get(auxVenda).getNome(), valorMedio, 1};
                                solicitacoesCompra.get(auxCompra).getSolicitante().notificar(compra, obj);
                                String venda = "venda efetuada";
                                solicitacoesVenda.get(auxVenda).getSolicitante().notificar(venda, obj);
                                System.out.println("Avisar Comprador");
                                aux = false;
                                solicitacoesCompra.remove(auxCompra);
                                solicitacoesVenda.remove(auxVenda);
                                DefaultTableModel modeloDeCompra = (DefaultTableModel) mercadoDeCompras.getModel();                                
                                modeloDeCompra.removeRow(auxCompra);
                                DefaultTableModel modeloDeVenda = (DefaultTableModel) mercadoDeVendas.getModel();
                                modeloDeVenda.removeRow(auxVenda);
                                
                            } catch (RemoteException ex) {
                                Logger.getLogger(ServImpl.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            auxVenda++;
                        }
                    }
                }
            }
        }
    }

    private void initComponents() throws RemoteException {

        JFrame j = new JFrame("Servidor");
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setBounds(0, 0, 800, 600);
        j.setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,2));

        String[] nomeDasColunas = {"Empresa", "Interesse", "Quantidade", "Valor"};
        TableModel model = new DefaultTableModel(nomeDasColunas,0);        

        mercadoDeVendas = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(mercadoDeVendas);
        mercadoDeVendas.setFillsViewportHeight(true);
        
        mercadoDeCompras = new JTable(new DefaultTableModel(nomeDasColunas,0));
        JScrollPane scrollPane2 = new JScrollPane(mercadoDeCompras);
        mercadoDeCompras.setFillsViewportHeight(true);

        panel.add(scrollPane);
        panel.add(scrollPane2);

        j.add(panel);
        j.pack();

    }
    
     private Object[] getInfo(String empresa) {
        ArrayList<Object> transacoes = new ArrayList<>();   
         
        for(Solicitacao solicitacao : solicitacoesVenda){
            if(solicitacao.getNome().equals(empresa)){
                Object[] obj = new Object[]{empresa, "Venda", solicitacao.getPreco(), solicitacao.getQuantidade()};
                transacoes.add(obj);
            }
        }
        for(Solicitacao solicitacao : solicitacoesCompra){
            if(solicitacao.getNome().equals(empresa)){
                Object[] obj = new Object[]{empresa, "Compra", solicitacao.getPreco(), solicitacao.getQuantidade()};
                transacoes.add(obj);
            }
        }
        
        Object[] trans = new Object[transacoes.size()];
        for(int i = 0; i < transacoes.size(); i++){
            trans[i] = transacoes.get(i);
        }
        
        return trans;
    }
    
    @Override
    public void comprar(InterfaceCli interfaceCliente, String empresa, int quantidade, int tempo, double precoDeCompra) throws RemoteException {
        solicitacoesCompra.add(new Solicitacao(interfaceCliente, empresa, quantidade, tempo, precoDeCompra));
        DefaultTableModel model = (DefaultTableModel) mercadoDeCompras.getModel();
        model.addRow(new Object[]{empresa, "Compra", quantidade, precoDeCompra});
    }

    @Override
    public void vender(InterfaceCli interfaceCliente, String empresa, int quantidade, int tempo, double precoDeVenda) throws RemoteException {
        solicitacoesVenda.add(new Solicitacao(interfaceCliente, empresa, quantidade, tempo, precoDeVenda));
        DefaultTableModel model = (DefaultTableModel) mercadoDeVendas.getModel();
        model.addRow(new Object[]{empresa, "Venda", quantidade, precoDeVenda});
    }

    @Override
    public void monitorar(InterfaceCli interfaceCliente, String empresa) throws RemoteException {
        String notificacao = "monitorar";        
        Object[] obj = getInfo(empresa);
        interfaceCliente.notificar(notificacao, obj);
    }
    
    @Override
    public void cadastrarEmpresa(String nome) throws RemoteException {
        for(String emp : empresas){
            if(emp.equals(nome)){
                return;
            }
        }
        
        empresas.add(nome);
        System.out.println(nome + " cadastrada!");
    }

    @Override
    public Object[][] listarEmpresas() throws RemoteException {
        Object[][] obj = new Object[empresas.size()][1];
        for(int i = 0; i < empresas.size(); i++){
            obj[i][0] = empresas.get(i);
        }
        
        return obj;
    }
}
