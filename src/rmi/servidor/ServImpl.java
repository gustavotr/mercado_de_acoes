/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import rmi.InterfaceCli;
import rmi.InterfaceServ;

/**
 *
 * @author a1097075
 */
public class ServImpl extends UnicastRemoteObject implements InterfaceServ {

    private ArrayList<Empresa> mercadoDeAcoes;

    public ServImpl() throws RemoteException {

        mercadoDeAcoes = new ArrayList<>();

        mercadoDeAcoes.add(new Empresa("Tim"));
        mercadoDeAcoes.add(new Empresa("Claro"));
        mercadoDeAcoes.add(new Empresa("Vivo"));
        mercadoDeAcoes.add(new Empresa("Oi"));

        initComponents();

    }

    @Override
    public void comprar(InterfaceCli interfaceCliente, String empresa, int quantidade, double precoDeCompra) throws RemoteException {
        for (Empresa aux : mercadoDeAcoes) {
            Empresa emp = aux;
            if (emp.getNome().equals(empresa)) {
                if (emp.getQuantidade() >= quantidade) {

                    emp.setQuantidade(emp.getQuantidade() - quantidade);
                    for (int i = 0; i >= quantidade; i++) {
                        emp.compraAcao(interfaceCliente, precoDeCompra);
                    }
                    System.out.println(quantidade + " ações compradas da " + empresa + " por R$" + precoDeCompra + "cada");
                    Object[] obj = new Object[]{empresa, quantidade, precoDeCompra};
                    interfaceCliente.notificar("Ação comprada!", obj);
                }
            }
        }
    }

    @Override
    public void vender(InterfaceCli interfaceCliente, String empresa, int quantidade, double precoDeVenda) throws RemoteException {

        for (Empresa aux : mercadoDeAcoes) {
            Empresa emp = aux;
            if (emp.getNome().equals(empresa)) {
                if (emp.getQuantidade() >= quantidade) {
                    emp.setQuantidade(emp.getQuantidade() + quantidade);
                    for (int i = 0; i >= quantidade; i++) {
                        emp.vendeAcao(interfaceCliente, precoDeVenda);
                    }
                }
            }
        }
                System.out.println(quantidade + " ações da " + empresa + " colocadas a venda por R$" + precoDeVenda);
              //interfaceCliente.notificar("Ação vendida!", null);
    }

    @Override
    public void monitorar(InterfaceCli interfaceCliente, String empresa) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object[][] listar() throws RemoteException {
        Object[][] acoes = new Object[mercadoDeAcoes.size()][3];

        for (int i = 0; i < mercadoDeAcoes.size(); i++) {
            Empresa emp = mercadoDeAcoes.get(i);
            acoes[i][0] = emp.getNome();
            acoes[i][1] = emp.getQuantidade();
            acoes[i][2] = emp.getPrecoMedio();
        }

        return acoes;
    }

    private void initComponents() throws RemoteException {

        JFrame j = new JFrame("Servidor");
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setBounds(0, 0, 800, 600);
        j.setVisible(true);

        JPanel panel = new JPanel();

        String[] nomeDasColunas = {"Empresa", "Número de ações", "Valor"};
        Object[][] dados = listar();

        JTable mercado = new JTable(dados, nomeDasColunas);

        JScrollPane scrollPane = new JScrollPane(mercado);
        mercado.setFillsViewportHeight(true);

        panel.add(scrollPane);

        j.add(panel);
        j.pack();

    }
}
