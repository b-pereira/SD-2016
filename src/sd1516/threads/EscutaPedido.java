/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package sd1516.threads;


import java.io.*;
import java.net.Socket;

import sd1516.DataBase;

/**
 *
 * @author ASUS
 */
public class EscutaPedido extends Thread {
  private final Socket socket;
  private String nome;
  private String password;
  
  private BufferedReader br;
  private PrintWriter pw;
  private DataBase db;

  public EscutaPedido(Socket socket, DataBase db) {
    this.db         = db;
    this.socket     = socket;
  }

  public void enviaMensagem() throws IOException{
       String mensagem = br.readLine();
       db.enviaMensagem(mensagem, nome);
  }
  /**
   * Interpreta o pedido do cliente desta thread.
   */
  public void run() {
      
       try {

            InputStream       is = socket.getInputStream();  
            OutputStream      os = socket.getOutputStream();  
            InputStreamReader ir = new InputStreamReader(is);
            br = new BufferedReader(ir);
            pw = new PrintWriter(os,true); // Este true serve para fazer auto flush.
            int opcao;

            while (true) {
                    pw.println("Pretende fazer LogIn(1) ou SignIn(2)?");
                    opcao = Integer.parseInt(br.readLine());
                    
                    if (opcao == 1) { //login
                        while (true) {
        		    pw.println("Insira o seu nome de utilizador:");
			    nome = br.readLine();

			    pw.println("Insira a sua password:");	
			    password = br.readLine();

                            if (db.logIn(nome, password, pw, br) == 0) { //É um cliente
                                pw.println ("Bem vindo cliente "+nome);
                                          
                                pw.println ("Pretende entrar no chat(1), procurar um taxista(2) ou sair(3)?");
                                opcao = Integer.parseInt(br.readLine());
                                    
                                    if (opcao == 1) { //chat
                                        /**
                                         * Adicionar o print writer do scoket deste cliente ao set 
                                         * com todos os print writers para assim ele receber mensagens do chat.
                                         */
                                        db.adicionaEscritor(pw);
                                    
                                        /**
                                         * Aceitar mensagens deste cliente e enviá-las para o chat.
                                         */
                                        while (true) {
                                            enviaMensagem();
                                        }
                                    }
                                    
                                                                        
                                    if (opcao == 2) {
                                        //pedido
                                    }
                                    
                                    if (opcao == 3) {return;}
                            }
                        
                            if (db.logIn(nome, password,pw,br) == 1) { //É um taxista
                                pw.println ("Bem vindo taxista "+nome);
                                          
                                while (true) {
                                    pw.println ("Pretende entrar no chat(1), procurar um cliente(2) ou sair(3)?");
                                    opcao = Integer.parseInt(br.readLine());
                                    
                                    if (opcao == 1) { //chat
                                    pw.println("chat");
                                        /**
                                         * Adicionar o print writer do scoket deste cliente ao set 
                                         * com todos os print writers para assim ele receber mensagens.
                                         */
                                        db.adicionaEscritor(pw);
                                    
                                        /**
                                         * Aceitar mensagens deste cliente e enviá-las.
                                         */
                                        while (true) {
                                            enviaMensagem();
                                        }
                                    }
                                    
                                    if (opcao == 2) {
                                        //pedido
                                    }
                                    
                                    if (opcao == 3) {return;}
                                }
                            }    
                            
                            if (db.logIn(nome, password,pw,br) == -1) { // Log in inválido
                                pw.println("\nLogIn inválido, por favor tente novamente.");
                            }
                        }
                    }
                  
                    if (opcao == 2) { //signin
                        db.signIn(pw,br);
                    }
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (pw != null) db.removeEscritor(pw); //Tira do chat para o caso de ter lá entrado
            try {
                socket.shutdownOutput();
                socket.close();
         
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
             
      