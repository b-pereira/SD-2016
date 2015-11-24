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
  	static HashMap<String, Cliente> clientes;


        public static int calcularTemp (int x, int y, int x1, int y1) {return 0;}

        public static int logIn (String s1, String s2) { return 0;}
	
        public static void signIn () {}
        
	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		String s1,s2, pedido, resposta;
                int xp,yp,xc,yc,x,y,i;
		
		try {

			while (true) {


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
			
                                                System.out.println ("Bem vindo cliente "+s1);
                                            
						// Iniciar o socket	
						Socket        socket = new Socket("localhost", port);
						InputStream       is = socket.getInputStream();  
            					OutputStream      os = socket.getOutputStream();  
						InputStreamReader ir = new InputStreamReader(is);
						BufferedReader    br = new BufferedReader(ir);
						PrintWriter       pw = new PrintWriter(os,true); // Este true serve para fazer auto flush.
            
						// Enviar pedido do cliente
                                                
                                                System.out.println ("A enviar pedido..");
                                                pw.write(s1);

						System.out.println("\nInsira o local de partida da viagem");
                                                System.out.println ("Indique a posição x: ");
                                                xp = scan.nextInt();
                                                pw.write(xp);
                                                System.out.println ("Indique a posição y: ");
                                                yp = scan.nextInt();
                                                pw.write(yp);
                                                
						System.out.println("\nInsira o local de chegada da viagem");
                                                System.out.println ("Indique a posição x: ");
                                                xc = scan.nextInt();
                                                pw.write(xc);
                                                System.out.println ("Indique a posição y: ");
                                                yc = scan.nextInt();
                                                pw.write(yc);
                                                
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
                                                x = Integer.parseInt(resposta);
                                                resposta = br.readLine();
                                                y = Integer.parseInt(resposta);
                                                System.out.println("Está na posicao: " +x+","+y);
                                                System.out.println("Irá demorar: "+(calcularTemp (xp,yp,x,y))+" minutos a chegar a si"); //calcula tempo entre posicao do taxi e do cliente
                                                resposta = br.readLine(); 
                                                System.out.println("O taxi chegou até si.");
                                                 System.out.println("Irá demorar: "+(calcularTemp (xp,yp,xc,yc))+" minutos a chegar ao destino"); //calcula tempo entre posicao do taxi e do cliente
                                                resposta = br.readLine(); 
                                                System.out.println("O taxi trouxe-o até ao destino.\nObrigado por escolher o nosso serviço.");
            					System.out.println("Sucesso!");
                                                socket.shutdownInput(); // Cliente nãi irá ler mais nada nesta sessão.
          
            					socket.close(); //Importante fechar o socket, já que o cliente não precisa mais
					}

					if (logIn(s1,s2) == 1) { // É um taxista
						
                                                System.out.println ("Bem vindo taxista "+s1);
                                            
                                                // Iniciar o socket	
						Socket        socket = new Socket("localhost", port);
						InputStream       is = socket.getInputStream();  
            					OutputStream      os = socket.getOutputStream();  
						InputStreamReader ir = new InputStreamReader(is);
						BufferedReader    br = new BufferedReader(ir);
						PrintWriter       pw = new PrintWriter(os,true); // Este true serve para fazer auto flush.
            
						// Enviar pedido do taxista
                                                System.out.println ("A enviar pedido..");
                                                pw.write(s1); //enviar nome
                                                pw.write((clientes.get(s2)).getMatricula());
                                                pw.write((clientes.get(s2)).getModelo());
                                                System.out.println ("Indique a sua posição x: ");
                                                pw.write(scan.nextInt());
                                                System.out.println ("Indique a sua posição y: ");
                                                pw.write(scan.nextInt());
                                                
                                                socket.shutdownOutput(); //Taxista já não vai enviar mais nada nesta sessão.
                        			System.out.println("Sucesso!");

            					// Receber a resposta
						System.out.println ("À espera de uma resposta..");            	
						resposta = br.readLine(); 
                                                System.out.println("Um cliente requere o seu serviço.");
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