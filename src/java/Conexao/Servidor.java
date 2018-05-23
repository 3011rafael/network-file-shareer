/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexao;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Rafael Macedo
 */
public class Servidor {
	
	
	private static ServerSocket servidor;
	
	public Servidor(){	
	}
	
	public static void main(String[] args){
		
		Servidor s = new Servidor();
		int porta = 1234;
		
		try {
			servidor = new ServerSocket(porta);
			System.out.println("Servidor em funcionamento");
			while(true){
				new ThreadCliente(servidor.accept()).start();
				System.out.println("Mais um cliente conectado");
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
}
