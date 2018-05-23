/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Rafael Macedo
 */


public class Cliente {
    
    private Socket cliente;
    private ObjectInputStream in;
    private ObjectOutputStream out; 
    
    public Cliente(String ip, int porta) throws IOException{
        cliente = new Socket(ip, porta);
        in = new ObjectInputStream(cliente.getInputStream());
        out = new ObjectOutputStream(cliente.getOutputStream());
    }
    
    public Socket getCliente() {
        return cliente;
    }

    public void setCliente(Socket cliente) {
        this.cliente = cliente;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    
    
    public static void main(String args[]) throws IOException{
        new Cliente("127.0.0.1", 1234);
    }

}
