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
    private ArrayList<Monitor> monitoramento;
    private JTable mercadoDeVendas;
    private JTable mercadoDeCompras;

    public ServImpl() throws RemoteException {

        solicitacoesCompra = new ArrayList<>();
        solicitacoesVenda = new ArrayList<>();
        empresas = new ArrayList<>();
        monitoramento = new ArrayList<>();

        initComponents();
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                //System.out.print(".");
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for(Monitor m: monitoramento){
                try {
                    String notificacao = "monitorar";
                    Object[] obj = getInfo(m.getEmpresa());
                    m.getCliente().notificar(notificacao, obj);
                } catch (RemoteException ex) {
                    Logger.getLogger(ServImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            

            if (solicitacoesCompra.size() > 0) {
                for (int auxCompra = 0; auxCompra < solicitacoesCompra.size(); auxCompra++) {
                    long tempoCompra;
                    tempoCompra = solicitacoesCompra.get(auxCompra).getTempo();
                    if (tempoCompra <= System.currentTimeMillis()) {
                        solicitacoesCompra.remove(auxCompra);
                        DefaultTableModel model = (DefaultTableModel) mercadoDeCompras.getModel();
                        model.removeRow(auxCompra);
                        System.out.println("removida acao");
                    }
                }
            }
            if (solicitacoesVenda.size() > 0) {
                for (int auxVenda = 0; auxVenda < solicitacoesVenda.size(); auxVenda++) {                    
                    long tempoVenda;
                    tempoVenda = solicitacoesVenda.get(auxVenda).getTempo();
                    //System.out.println(System.currentTimeMillis() + " -> " + tempoVenda);
                    if (tempoVenda <= System.currentTimeMillis()) {
                        solicitacoesVenda.remove(auxVenda);
                        DefaultTableModel model = (DefaultTableModel) mercadoDeVendas.getModel();
                        model.removeRow(auxVenda);
                        System.out.println("removida acao");
                    }
                }
            }
            int auxCompra = 0;
            for (Solicitacao compra : solicitacoesCompra) {
                int auxVenda = 0;
                for (Solicitacao venda : solicitacoesVenda) {

                    String nomeAcaoCompra = compra.getNome();
                    String nomeAcaoVenda = venda.getNome();
                    double precoCompra = compra.getPreco();
                    double precoVenda = venda.getPreco();
                    if (nomeAcaoCompra.equals(nomeAcaoVenda) && precoCompra >= precoVenda) {
                        try {
                            System.out.println("Houve uma venda");
                            double valorMedio = (compra.getPreco() + venda.getPreco()) / 2;
                            String compraStr = "compra efetuada";
                            Object[] obj = new Object[]{venda.getNome(), valorMedio, 1};
                            compra.getSolicitante().notificar(compraStr, obj);
                            String vendaStr = "venda efetuada";
                            venda.getSolicitante().notificar(vendaStr, obj);
                            System.out.println("Avisar Comprador");
                            solicitacoesCompra.remove(compra);
                            solicitacoesVenda.remove(venda);
                            DefaultTableModel modeloDeCompra = (DefaultTableModel) mercadoDeCompras.getModel();
                            modeloDeCompra.removeRow(auxCompra);
                            auxCompra++;
                            DefaultTableModel modeloDeVenda = (DefaultTableModel) mercadoDeVendas.getModel();
                            modeloDeVenda.removeRow(auxVenda);
                            auxVenda++;

                            return;

                        } catch (RemoteException ex) {
                            Logger.getLogger(ServImpl.class.getName()).log(Level.SEVERE, null, ex);
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
        panel.setLayout(new GridLayout(1, 2));

        String[] nomeDasColunas = {"Empresa", "Interesse", "Quantidade", "Valor"};
        TableModel model = new DefaultTableModel(nomeDasColunas, 0);

        mercadoDeVendas = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(mercadoDeVendas);
        mercadoDeVendas.setFillsViewportHeight(true);

        mercadoDeCompras = new JTable(new DefaultTableModel(nomeDasColunas, 0));
        JScrollPane scrollPane2 = new JScrollPane(mercadoDeCompras);
        mercadoDeCompras.setFillsViewportHeight(true);

        panel.add(scrollPane);
        panel.add(scrollPane2);

        j.add(panel);
        j.pack();

    }

    private Object[] getInfo(ArrayList empresas) {
        ArrayList<Object> transacoes = new ArrayList<>();
        
        for(Object empresa: empresas){
            for (Solicitacao solicitacao : solicitacoesVenda) {
                if (solicitacao.getNome().equals(empresa)) {
                    Object[] obj = new Object[]{empresa, "Venda", solicitacao.getPreco(), solicitacao.getQuantidade()};
                    transacoes.add(obj);
                }
            }
            for (Solicitacao solicitacao : solicitacoesCompra) {
                if (solicitacao.getNome().equals(empresa)) {
                    Object[] obj = new Object[]{empresa, "Compra", solicitacao.getPreco(), solicitacao.getQuantidade()};
                    transacoes.add(obj);
                }
            }
        }

        Object[] trans = new Object[transacoes.size()];
        for (int i = 0; i < transacoes.size(); i++) {
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
        int id = acharCliente(interfaceCliente, monitoramento);
        if(id == -1){
            ArrayList<String> emps = new ArrayList<>();
            emps.add(empresa);
            monitoramento.add(new Monitor(interfaceCliente, emps));       
        }
        else{
            Monitor m = monitoramento.get(id);
            m.getEmpresa().add(empresa);
        }
        
    }

    @Override
    public void cadastrarEmpresa(String nome) throws RemoteException {
        for (String emp : empresas) {
            if (emp.equals(nome)) {
                return;
            }
        }

        empresas.add(nome);
        System.out.println(nome + " cadastrada!");
    }

    @Override
    public Object[][] listarEmpresas() throws RemoteException {
        Object[][] obj = new Object[empresas.size()][1];
        for (int i = 0; i < empresas.size(); i++) {
            obj[i][0] = empresas.get(i);
        }

        return obj;
    }
    
    /**
     * Procura por uma cliente na lista de monitoramento e retorna seu indice. Caso nao encontre retorna -1.
     * @param interfaceCliente
     * @param monitoramento
     * @return 
     */
    private int acharCliente(InterfaceCli interfaceCliente, ArrayList<Monitor> monitoramento) {
        for(int i = 0; i < monitoramento.size(); i++){
            Monitor m = monitoramento.get(i);
            if(m.getCliente().equals(interfaceCliente)){
                return i;
            }
        }
        return -1;
    }
}
