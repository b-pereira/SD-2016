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
import java.net.UnknownHostException;
import java.lang.*;
import java.util.*;
import java.io.*;

public class Interface {

	private int port;
        private String host;
        private Socket socket;
        private BufferedReader br;
        private PrintWriter pw;  
        
  	private HashMap<String, Cliente> clientes = new HashMap<String,Cliente>(); // <nome do cliente, objeto cliente>
        private Scanner scan = new Scanner(System.in);

        
        
        public Interface(int port, String host) throws UnknownHostException, IOException{
            this.port = port;
            this.host = host;
            
            socket = new Socket(host, port);
            InputStream       is = socket.getInputStream();  
            OutputStream      os = socket.getOutputStream();  
            InputStreamReader ir = new InputStreamReader(is);
            br = new BufferedReader(ir);
            pw = new PrintWriter(os,true); // Este true serve para fazer auto flush.
        }
        
        public void pedidoCliente (String s1, String s2){
            String resposta;
            int x,y,xp,yp,xc,yc;
            
            System.out.println ("A enviar pedido..");
            try {
             
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
            } catch (IOException e) {
                e.printStackTrace();
              }
            
        }
        
        public void pedidoTaxista (String s1, String s2) {
            String resposta;
            
            // Enviar pedido do taxista
            System.out.println ("A enviar pedido..");
            try {
                pw.write(s1); //enviar nome
                Taxista tax = (Taxista) (clientes.get(s2));
                pw.write(tax.getMatricula()); //enviar matrícula
                pw.write(tax.getModelo()); //enviar modelo
                System.out.println ("Indique a sua posição x: ");
                pw.write(scan.nextInt()); //enviar x
                System.out.println ("Indique a sua posição y: ");
                pw.write(scan.nextInt()); //enviar y
                                                
                socket.shutdownOutput(); //Taxista já não vai enviar mais nada nesta sessão.
                System.out.println("Sucesso!");

            	// Receber a resposta
		System.out.println ("À espera de uma resposta..");            	
		resposta = br.readLine(); 
                System.out.println("Um cliente requere o seu serviço.");
                                                
                socket.shutdownInput(); // Cliente não irá ler mais nada nesta sessão.
          
                socket.close(); //Importante fechar o socket, já que o cliente não precisa mais
            } catch (IOException e) {
                e.printStackTrace();
              }
        }
        
        /***********CalcularTemp*******************************/
        
        public int calcularTemp (int x, int y, int x1, int y1) {
            
             //Assumiremos que demora um minuto a passar de uma posição para outra consecutiva.
            //Calcula o tempo usando a distancia Manhattam
            return Math.abs(x-x1) + Math.abs(y-y1);
        }
        
        /****************************************************/
        
        /*****Log in*****************************************/
        
        public int logIn (String s1, String s2) {
            
            if (!(clientes.containsKey(s1))) return -1; // não existe
            
            Cliente z = clientes.get(s1);
            
            if (!(z.getPassword().equals(s2))) return -1; // password errada
            
            if (z.getClass().getName().equals("sd1516.Cliente")) return 0; // existe e é cliente
            
            return 1; // existe e é taxista
        }
        
        /****************************************************/
	
        /**********************sign in***********************/
        
        public void signIn () {
            String s1,s2,s3,s4,s5;
            int i;
            
            System.out.println("\nPretende inscrever-se como cliente(1) ou taxista(2)?");
            i = scan.nextInt();
            
            System.out.println("\nInsira o nome de utilizador a usar:");
	    s1 = scan.next();
            System.out.println("\nInsira a password a usar:");
	    s2 = scan.next();
            System.out.println("\nInsira o seu contacto:");
	    s3 = scan.next();
            
            if (clientes.containsKey(s1)) {
                
                System.out.println("Já existe um cliente com este nome, por favor tente novamente.");
                return;
            }
            
            if (i == 2) {
                System.out.println("\nInsira o modelo do seu carro:");
                s4 = scan.next();

                System.out.println("\nInsira a matricula do seu carro:");
                s5 = scan.next();
                
                System.out.println("A registar..");
                clientes.put(s1, new Taxista(s4,s5,s1,s3,s2));
                System.out.println("\nRegistado com sucesso!");
                return;
            }
            
            System.out.println("A registar..");
            clientes.put(s1, new Cliente(s1,s3,s2));
            System.out.println("\nRegistado com sucesso!");
        }
        
        /****************************************************/
        
	public static void main(String[] args) {

		String s1,s2;
                int i;
		
		try {
                    
                    Interface client = new Interface(7229,"localhost");
                    
                    while (true) {


				System.out.println("\n*****************************\nBem vindo ao serviço de taxis!\n\n Opções disponíveis:\n -> Log In (prima 1)\n -> Sign In (prima 2)\n -> Sair (prima 3)\n*****************************\n");
				i = client.scan.nextInt();
					
				//Autenticar	
				if (i == 1) {

					System.out.println("\nInsira o seu nome de utilizador:");
					s1 = client.scan.next();

					System.out.println("\nInsira a sua password:");	
					s2 = client.scan.next();

					//Tenta autenticar (log in) se o conseguir então inicia a conexão via socket.

					if (client.logIn(s1,s2) == 0) { // é um cliente		
			
                                                System.out.println ("Bem vindo cliente "+s1);
                                          
                                                client.pedidoCliente(s1,s2);
                                               
					}

					if (client.logIn(s1,s2) == 1) { // É um taxista
						
                                                System.out.println ("Bem vindo taxista "+s1);
                                            
                                                client.pedidoTaxista(s1,s2);
					}
                                        
                                        if (client.logIn(s1,s2) == -1) { // Log in inválido
                                            System.out.println("\nLog in inválido, por favor tente novamente.");
                                            continue;
                                        }
				

				}

				//Registar
				if (i == 2) {

					//Regista cliente (sign in) e volta a apresentar o menu principal.
					client.signIn();
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