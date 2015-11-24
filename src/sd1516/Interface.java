/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd1516;

/**
 *
 * @author ASUS
 */

import java.net.Socket;
import java.util.*;
import java.io.*;

public class Interface {

	static int port = 7229;
  	HashMap<Cliente, String> clientes;




        public static int logIn (String s1, String s2) { return 0;}
	
        public static void signIn () {}
        
	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		String s1,s2, pedido, resposta;
		
		try {

			while (true) {

				int i;

				System.out.println("\n*****************************\nBem vindo ao serviço de taxis!\n\n Opções disponíveis:\n -> Log In (prima 1)\n -> Sign In (prima 2)\n -> Sair (prima 3)\n*****************************\n");
				i = scan.nextInt();
					
				//Autenticar	
				if (i == 1) {

					System.out.println("\nInsira o seu nome de utilizador:");
					s1 = scan.next();

					System.out.println("\nInsira a sua password:");	
					s2 = scan.next();

					//Tenta autenticar (log in) se o conseguir então inicia a conexão via socket.
					if (logIn(s1,s2) == -1) break;

					if (logIn(s1,s2) == 0) { // é um cliente		
			
                                            
						// Iniciar o socket	
						Socket        socket = new Socket("localhost", port);
						InputStream       is = socket.getInputStream();  
            					OutputStream      os = socket.getOutputStream();  
						InputStreamReader ir = new InputStreamReader(is);
						BufferedReader    br = new BufferedReader(ir);
						PrintWriter       pw = new PrintWriter(os,true); 
            
						// Enviar pedido do cliente
                                                
                                                System.out.println ("A enviar pedido..");
                                                pw.write(s1);
                                                pw.write(s2);
						System.out.println("\nInsira o local de partida da viagem:");
						pw.write(scan.next());
						System.out.println("\nInsira o local de chegada da viagem:");
						pw.write(scan.next());
                                                socket.shutdownOutput(); //Cliente já não vai enviar mais nada nesta sessão.
                        			System.out.println("Sucesso!");

            					// Receber a resposta
						System.out.println ("À espera de uma resposta..");            	
						resposta = br.readLine(); 
                                                System.out.println("Um taxi respondeu ao seu pedido e em breve virá ao seu encontro.");
                                                System.out.println("Matricula: "+resposta);
                                                resposta = br.readLine(); 
                                                System.out.println("Modelo: " + resposta);
                                                resposta = br.readLine(); 
                                                System.out.println("Irá demorar: " + resposta + " minutos a chegar até si.");
                                                resposta = br.readLine(); 
                                                System.out.println("O taxi chegou até si.");
                                                resposta = br.readLine(); 
                                                System.out.println("O taxi trouxe-o até ao destino.\nObrigado por escolher o nosso serviço.");
            					System.out.println("Sucesso!");
                                                socket.shutdownInput(); // Cliente nãi irá ler mais nada nesta sessão.
          
            					socket.close(); //Importante fechar o socket, já que o cliente não precisa mais
					}

					if (logIn(s1,s2) == 1) { // É um taxista
						//falta fazer!
					}
				

				}

				//Registar
				if (i == 2) {

					//Regista cliente (sign in) e volta a apresentar o menu principal.
					signIn();
					continue;
				} 

				//Sair
				if (i == 3) break;
		
			}
            
		} catch (IOException e) {
			
			//Imprimir o problema.
			e.printStackTrace();
		}        
	}

	
}
