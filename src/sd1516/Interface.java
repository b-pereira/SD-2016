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
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.*;
import java.io.*;

public class Interface {

    
    /***************Variáveis de Classe*************/
    
           /***********************************************************************************/
          /**/ private int port;          /**port*********************************************/
         /**/ private String host;       /**IP do host***************************************/
        /**/ private Socket socket;     /**socket*******************************************/
       /**/ private Scanner scan = new Scanner(System.in); /**receber input do utilizador**/
      /**/ private BufferedReader br; /**variável para leitura****************************/
     /**/ private PrintWriter pw;    /**variável para escrita****************************/ 
    /***********************************************************************************/    
     
     
     
     // variáveis estáticas (tipos de pedidos e tipos de respostas)
    
           /***************Pedidos*******************************************/
          /**/ final static int LOGIN         = 1; /**pedir autentificação**/
         /**/ final static int SIGNIN        = 2; /**pedir inscrição*******/
        /**/ final static int PEDIDO_C      = 3; /**pedir cliente*********/
       /**/ final static int PEDIDO_T      = 4; /**pedir taxista*********/
      /**/ final static int GET_MATRICULA = 5; /**pedir matricula*******/
     /**/ final static int GET_MODELO    = 6; /**pedir modelo**********/
    /*****************************************************************/
     
     
       /*********respostas****************************************/
      /**/ final static int OK =  0; /**responder com sucesso****/
     /**/ final static int KO = -1; /**responder com insucesso**/
    /**********************************************************/
     

        
        
        public Interface(int port, String host) throws UnknownHostException, IOException, ConnectException{
            this.port = port;
            this.host = host;
            socket = new Socket(host,port);
            InputStream       is = socket.getInputStream();  
            OutputStream      os = socket.getOutputStream();  
            InputStreamReader ir = new InputStreamReader(is);
            br = new BufferedReader(ir);
            pw = new PrintWriter(os,true); // Este true serve para fazer auto flush.
        }
        
        
        public int logIn (String s1,String s2) throws IOException{
            pw.write(LOGIN);
            pw.write(s1);
            pw.write(s2);
            
            return Integer.parseInt(br.readLine());
        }
        
        public void signIn () throws IOException{
            pw.write(SIGNIN);
            
            if (br.readLine().equals("KO")) System.out.println("Houve um erro a registá-lo como cliente, por favor tente novamente.");
        }
        
        public void pedidoCliente (String s1, String s2){
            String resposta;
            int x,y,xp,yp,xc,yc;
            
            System.out.println ("A enviar pedido..");
            try {
 
                pw.write(PEDIDO_C);
                pw.write(s1); //Enviar nome
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
                resposta = br.readLine();
                System.out.println("Nome do taxista: "+resposta);
                System.out.println("Matricula: "+resposta);
                resposta = br.readLine(); 
                System.out.println("Modelo: " + resposta);
                resposta = br.readLine(); 
                x = Integer.parseInt(resposta);
                resposta = br.readLine();
                y = Integer.parseInt(resposta);
                System.out.println("Está na posicao: ("+x+","+y+")");
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
        
        public String getMatricula (String s1) throws IOException {
            pw.write(GET_MATRICULA);
            
            return br.readLine();
        }
        
        public String getModelo (String s1) throws IOException {
            pw.write (GET_MODELO);
            
            return br.readLine();
        }
        
        public void pedidoTaxista (String s1, String s2) {
            String resposta;
            
            // Enviar pedido do taxista
            System.out.println ("A enviar pedido..");
            try {
                
                pw.write(PEDIDO_T);
                pw.write(s1); //enviar nome
               
                pw.write(getMatricula(s1)); //enviar matrícula
                pw.write(getModelo(s1)); //enviar modelo
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
		
                                else System.out.println("\nOpção inválida");
                                
                    }
            
		} catch (IOException e) {
			
                    //Imprimir o problema.
                    e.printStackTrace();
                  }
        }
        
                
}