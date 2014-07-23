/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi.servidor;

import java.util.ArrayList;
import rmi.InterfaceCli;

/**
 *
 * @author Saranghae
 */
class Monitor {
    
    private InterfaceCli cliente;
    private ArrayList<String> empresa;

    public Monitor(InterfaceCli cliente, ArrayList<String> empresa) {
        this.cliente = cliente;
        this.empresa = empresa;
    }

    public InterfaceCli getCliente() {
        return cliente;
    }

    public ArrayList<String> getEmpresa() {
        return empresa;
    }
}
