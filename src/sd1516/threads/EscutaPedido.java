/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package sd1516.threads;


import java.io.*;
import java.net.Socket;

import sd1516.business.Dados;
import sd1516.business.Taxista;
import sd1516.utils.DadosTransito;

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
  private final Dados db;

  public EscutaPedido(Socket socket, Dados db) {
    this.db         = db;
    this.socket     = socket;
  }

  public void enviaMensagem(String tipo) throws IOException{
       String mensagem = br.readLine();
       db.enviaMensagem(mensagem, nome, tipo);
  }
  /**
   * Interpreta o pedido do cliente desta thread.
   */
  @Override
  public void run() {
      int xp, yp, xc, yc;
       boolean chat = false;
       try {

            InputStream       is = socket.getInputStream();  
            OutputStream      os = socket.getOutputStream();  
            InputStreamReader ir = new InputStreamReader(is);
            br = new BufferedReader(ir);
            pw = new PrintWriter(os,true); // Este true serve para fazer auto flush.
            int opcao;
            String linha;
            
            while (true) {
                    pw.println("INICIO");
                    
                    linha = br.readLine();
                    if (linha == null) continue;
                    
                    opcao = Integer.parseInt(linha);

                    if (opcao == 1) { //login
                        while (true) {
                            
        		    pw.println("LOGIN");
                            
			    nome     = br.readLine();	
			    password = br.readLine();

                            opcao = db.logIn(nome, password, pw, br);
                            
                            if (opcao == 0) { //É um passageiro
                                
                                while (true) {
                                    pw.println ("Bem vindo passageiro "+nome);
                                    opcao = Integer.parseInt(br.readLine());
                                    
                                    if (opcao == 1) { //chat
                                        pw.println("CHAT");
                                        /**
                                         * Adicionar o print writer do scoket deste cliente ao set 
                                         * com todos os print writers para assim ele receber mensagens do chat.
                                         */
                                        db.adicionaEscritor(pw);
                                        chat = true;
                                        /**
                                         * Aceitar mensagens deste cliente e enviá-las para o chat.
                                         */
                                        while (true) {
                                            enviaMensagem("Passageiro");
                                        }
                                    }
                                    
                                                                        
                                    if (opcao == 2) {
                                        
                                        pw.println("PEDIDO_P");
                                        
                                        xp = Integer.parseInt(br.readLine());
                                        yp = Integer.parseInt(br.readLine());
                                        
                                        xc = Integer.parseInt(br.readLine());
                                        yc = Integer.parseInt(br.readLine());
                                        
                                        System.out.println("caralho!!!!!!");
                                        Taxista tax = db.getObjetoTaxista(db.getTaxista(xp, yp, xc, yc, pw));
                                        System.out.println("caralho2!!!!!!");
                                        pw.println("DADOS_P Taxista encontrado!\nNome: "+tax.getNome()+"\nModelo: "+tax.getModelo()+"\nMatricula: "+tax.getMatricula());
                                       
                                    }
                                    
                                    if (opcao == 3) {return;}
                                }
                            }
                        
                            if (opcao == 1) { //É um taxista
                                          
                                while (true) {
                                    pw.println ("Bem vindo taxista "+nome);
                                    opcao = Integer.parseInt(br.readLine());
                                    
                                    if (opcao == 1) { //chat
                                        pw.println("CHAT");
                                        /**
                                         * Adicionar o print writer do scoket deste cliente ao set 
                                         * com todos os print writers para assim ele receber mensagens.
                                         */
                                        db.adicionaEscritor(pw);
                                        chat = true;
                                        /**
                                         * Aceitar mensagens deste cliente e enviá-las.
                                         */
                                        while (true) {
                                            enviaMensagem("Taxista");
                                        }
                                    }
                                    
                                    if (opcao == 2) {
                                        pw.println("PEDIDO_T");
                                        
                                        xp = Integer.parseInt(br.readLine());
                                        yp = Integer.parseInt(br.readLine());
                                        db.putTaxista(nome,xp,yp);
                                        
                                        DadosTransito transito = db.getDadosTransito(nome);
                                        xp = transito.getPartida().getX();
                                        yp = transito.getPartida().getY();
                                        pw.println("DADOS_T Um passageiro necessita do seu servico na posicao: ("+xp+","+yp+")");
                                        
                                        xc = transito.getChegada().getX();
                                        yc = transito.getChegada().getY();
                                        int tempo = (db.calcularTemp(xp, yp, xc, yc));
                                        try {
                                            Thread.sleep(tempo*1000); // Esperar o tempo suficiente para fazer a viagem.
                                        } catch (InterruptedException ie) {
                                            ie.printStackTrace();
                                        } 
                                        
                                        transito.getPassageiroPW().println("CHEGADA_P "+(tempo*0.05)); //Avisar o passageiro que chegou ao destino. 0,05 euros por segundo.
                                        pw.println("CHEGADA_T "+(tempo*0.05)); //Avisar o taxista que chegou ao destino. 0,05 euros por segundo.
                                        
                                    }
                                    
                                    if (opcao == 3) {return;}
                                }
                            }    
                            
                            if (opcao == -1) { // Log in inválido
                                pw.println("LogInKO");
                                break;
                            }
                        }
                    }
                  
                    if (opcao == 2) { //signin
                        db.signIn(pw,br);
                    }
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (NumberFormatException nfe) {
            pw.println("Opção inválida.");
            nfe.printStackTrace();
        }finally {
            if (pw != null && chat) db.removeEscritor(pw); //Tira do chat para o caso de ter lá entrado
            try {
                socket.shutdownOutput();
                socket.close();
         
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
             
      