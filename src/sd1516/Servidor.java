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
public class Servidor {

    final static int LOGIN         = 1;
    final static int SIGNIN        = 2;
    final static int PEDIDO_C      = 3;
    final static int PEDIDO_T      = 4;
    final static int GET_MATRICULA = 5;
    final static int GET_MODELO    = 6;
    
    final static int OK =  0;
    final static int KO = -1;
    
    private BufferedReader br;
    private PrintWriter pw;
    
    private HashMap<String, Cliente> clientes = new HashMap<String,Cliente>(); // <nome do cliente, objeto cliente>
    private Scanner scan = new Scanner(System.in);
    
    
    
    
    
    
    
    public String getMatricula(String s1){
        
        Taxista tax = (Taxista) clientes.get(s1);
        return tax.getMatricula();  
    }
    
    public String getModelo(String s1){
        
        Taxista tax = (Taxista) clientes.get(s1);
        return tax.getModelo();  
    }
    
    public int logIn (String s1, String s2) {
            
        if (!(clientes.containsKey(s1))) return -1; // não existe
            
        Cliente z = clientes.get(s1);
            
        if (!(z.getPassword().equals(s2))) return -1; // password errada
            
        if (z.getClass().getName().equals("sd1516.Cliente")) return 0; // existe e é cliente
            
        return 1; // existe e é taxista
    }

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

    
    
    public void parse(int opcao) throws IOException{
                String nome, password; 
		switch(opcao) {
		case LOGIN:
			nome     = br.readLine();
                        password = br.readLine();
			pw.write(logIn(nome,password));
			break;
		case SIGNIN:
			signIn();
                        pw.write(OK);
			break;
		case GET_MATRICULA:
                        nome = br.readLine();
			pw.write(getMatricula(nome));
			break;
		case GET_MODELO:
			nome = br.readLine();
			pw.write(getModelo(nome));
			break;
		case PEDIDO_C:
		
			break;
                case PEDIDO_T:
                    
                        break;
		default:
                        System.out.println("Erro...");
			pw.write(KO);
		}
	}
    
    

}
