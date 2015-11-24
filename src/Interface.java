import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class Interface {

	static int port = 7229;
  	HashMap<String, String> clientes;





	

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		String s1,s2,s3,s4, pedido, resposta;
		
		try {

			while (1) {

				int i = 0;

				System.out.println("Bem vindo ao serviço de taxis!\n\n Opções disponíveis:\n -> Log In (prima 1)\n -> Sign In (prima 2)\n -> Sair (prima 3)");
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
						InputStreamreader ir = new InputStreamreader(is);
						BufferedReader    br = new BufferedReader(ir);
						PrintWriter       pw = new PrintWriter(os,true); 
            
						// Detetar pedido do cliente
						System.out.println("\nInsira o local de partida da viagem:");
						s3 = scan.next();
						
						System.out.println("\nInsira o local de chegada da viagem:");
						s4 = scan.next();
							
           					// Enviar pedido do cliente
           					pedido    = s1+ " " +s2+ " " +s3+ " " +s4+ " ";
						pedido    = pedido + pedido.length(); //esta length será usada como um checksum que irá servir para verificar se o servidor recebeu de facto a mensagem toda.
						System.out.println ("A enviar pedido..");
            					pw.write(pedido);         
                        			System.out.println("Sucesso!");

            					// Receber a resposta
						System.out.println ("À espera de uma resposta..");            	
						resposta = br.read(); 
            					System.out.println("Sucesso!");

            					System.out.println("O taxi número: " + resposta + "respondeu ao seu pedido e em breve virá ao seu encontro.");
         
            					br.close();  
            					pw.close(); 
            					socket.close();
				
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