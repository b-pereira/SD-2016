/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd1516;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author ASUS
 */
public class Servidor extends Thread{

    final static int LOGIN         = 1;
    final static int SIGNIN        = 2;
    final static int PEDIDO_C      = 3;
    final static int PEDIDO_T      = 4;
    final static int GET_MATRICULA = 5;
    final static int GET_MODELO    = 6;
    
    final static int OK =  0;
    final static int KO = -1;
    
    private int port;
    
    private BufferedReader br;
    private PrintWriter pw;
    
    private DataBase clientes;
    private Scanner scan = new Scanner(System.in);
    
    
    
    
    
    public Servidor (int port){
        this.port     = port;
        this.clientes = new DataBase(); //Iremos implementar uma busca a um ficheiro para por clientes aqui sempre que se reinicia o servidor.
        
    }
    

    
        public static void main(String[] args) {
		Servidor servidor = new Servidor(7262);
                    try {
			ServerSocket sSocket = new ServerSocket(servidor.port);
			while (true) {
                            System.out.println("À espera de clientes...");
                            Socket socket = sSocket.accept();
                            System.out.println("Cliente com o port "+(socket.getPort())+" encontrado!");
                            
                            InputStream       is = socket.getInputStream();  
                            OutputStream      os = socket.getOutputStream();  
                            InputStreamReader ir = new InputStreamReader(is);
                            servidor.br = new BufferedReader(ir);
                            servidor.pw = new PrintWriter(os,true); // Este true serve para fazer auto flush.
                            
                            boolean continuar = true;				
                            while (continuar) {
                                    new Thread (new EscutaPedido(socket,servidor.clientes)).start();
                            }
			
                            System.out.println("Ligação com o cliente terminada.");
                            socket.shutdownInput();
                            socket.shutdownOutput();
                            socket.close();
			}
                    } catch (IOException e) {
			e.printStackTrace();
                    }
	}
    

}
