/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd1516;


import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author ASUS
 */
public class EscutaPedido implements Runnable{
    Socket socket;
    
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
    
    private DataBase db;
    private Scanner scan = new Scanner(System.in);
    
    public EscutaPedido (Socket socket, DataBase db){
        this.db     = db;
        this.socket = socket;
    }
    
        public void run(){
                String nome, password; 
                try {
                int opcao = Integer.parseInt(br.readLine());
                
		switch(opcao) {
		case LOGIN:
			nome     = br.readLine();
                        password = br.readLine();
			pw.write(db.logIn(nome,password));
			break;
		case SIGNIN:
			db.signIn();
                        pw.write(OK);
			break;
		case GET_MATRICULA:
                        nome = br.readLine();
			pw.write(db.getMatricula(nome));
			break;
		case GET_MODELO:
			nome = br.readLine();
			pw.write(db.getModelo(nome));
			break;
		case PEDIDO_C:
                        nome   = br.readLine();
                        
                        int xp = Integer.parseInt(br.readLine());
                        int yp = Integer.parseInt(br.readLine());
                        
                        int xc = Integer.parseInt(br.readLine());
                        int yc = Integer.parseInt(br.readLine());
                        
                        Taxista tax = (Taxista) db.getCliente(db.procurarTaxista(xp,yp));
                        
                        pw.write(tax.getNome());
                        pw.write(tax.getMatricula());
                        pw.write(tax.getModelo());
                        pw.write(tax.getPos().getX());
                        pw.write(tax.getPos().getY());
                        
			break;
                case PEDIDO_T:
                    
                        break;
		default:
                        System.out.println("Erro...");
			pw.write(KO);
		}
                } catch (IOException e) {
                    e.printStackTrace();
                }
	}
}
