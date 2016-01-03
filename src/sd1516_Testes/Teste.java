/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd1516_Testes;

import java.awt.event.ActionEvent;
import java.net.Socket;
import java.io.*;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Teste {

    
    /***************Variáveis de Classe*************/
    
       /***********************************************************************************/
      /**/ private BufferedReader br; /**variável para leitura****************************/
     /**/ private PrintWriter pw;    /**variável para escrita****************************/ 
    /***********************************************************************************/    
   
     /** Janela de chat **/
    JFrame frame          = new JFrame("Chat");
    JTextField textField  = new JTextField(40);
    JTextArea messageArea = new JTextArea(8, 40);     
    /********************/
    
    Scanner in = new Scanner(System.in);

    
    /**********************************************/
    
     public Teste() {

        // Definir GUI
        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.pack();


        textField.addActionListener((ActionEvent e) -> {
            pw.println(textField.getText()); // Enviar a mensagem para o print writer.
            textField.setText(""); //Apagar a mensagem escrita na área de texto.
        } /**
         * Envia o que foi escrito na área de texto.
         * De seguida apaga para que o cliente possa escrever outra mensagem.
         */ );
    }
     
    private String mensagem( String s1, String s2) {
        return JOptionPane.showInputDialog(frame,s1,s2,JOptionPane.PLAIN_MESSAGE);
    }
     
    private void iniciar() throws IOException {
        
        System.out.println("Bem vindo ao serviço de Taxis!");
        // Conectar ao servidor e definir o escritor e o leitor.
        System.out.println("Indique o endereço IP do servidor.(exemplo: localHost)");
        String serverIP = in.nextLine();
        Socket socket = new Socket(serverIP, 65000);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pw = new PrintWriter(socket.getOutputStream(), true); // true serve para fazer auto flush
        String saudacoes;
        
        while (true) {
            String line;
            if ((line=br.readLine())==null) continue;
            
            /*************INICIO***************/
            else if ("INICIO".equals(line)) { 
                System.out.println("Pretende fazer LogIn(1) ou SignIn(2)?");
                pw.println(in.nextLine());
            } 
            /**********************************/
            
            
            /*************CHAT*****************/
            else if (line.startsWith("CHAT")) { // O cliente mostrou interesse em participar no chat.
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                textField.setEditable(true); 
            } else if (line.startsWith("->")) { //Recebemos uma mensagem do chat.
                System.out.println(line); 
            } 
            /***********************************/
            
            
            /************LOGIN******************/
            else if ("LOGIN".equals(line)){
                System.out.println("Insira o seu nome de utilizador:");
                pw.println(in.nextLine());
                System.out.println("Insira a sua password:");
                pw.println(in.nextLine());
            } 
            else if (line.startsWith("TAXISTA")){
                System.out.println("Foi detetado que esta conta possui tanto um passageiro associado como um taxista.\nPretende aceder ao sistema como passageiro(1) ou como taxista(2)?");
                pw.println(in.nextLine());
            }
            else if (line.startsWith("LogInKO")){
                System.out.println("LogIn inválido, por favor tente novamente.");
            }
            /**********************************/
            
            
            /****************SIGNIN*************/
            else if (line.startsWith("Pretende inscrever-se como passageiro(1) ou taxista(2)?")){
                System.out.println(line);
                pw.println(in.nextLine());
            }
            else if (line.startsWith("Insira o nome de utilizador a usar:")){
                System.out.println(line);
                pw.println(in.nextLine());
            }
            else if (line.startsWith("Insira a password a usar:")){
                System.out.println(line);
                pw.println(in.nextLine());
            }
            else if (line.startsWith("Insira o seu contacto:")){
                System.out.println(line);
                pw.println(in.nextLine());
            }
            else if (line.startsWith("SignInKO")){
                System.out.println("Já existe um passageiro com este nome, por favor tente novamente.");
            }
            else if (line.startsWith("Insira o seu contacto:")){
                System.out.println(line);
                pw.println(in.nextLine());
            }
            else if (line.startsWith("Insira o modelo do seu carro:")){
                System.out.println(line);
                pw.println(in.nextLine());
            }
            else if (line.startsWith("Insira a matricula do seu carro:")){
                System.out.println(line);
                pw.println(in.nextLine());
            }
            else if (line.startsWith("Registado com sucesso!")){
                System.out.println(line);
            }
            else if (line.startsWith("Insira a matricula do seu carro:")){
                System.out.println(line);
                pw.println(in.nextLine());
            }
            /**********************************/
            
            
            /***********SAUDACOES**************/
            else if (line.startsWith("Bem vindo passageiro")){ //Utilizador fez LogIn.
                saudacoes = line;
                System.out.println(saudacoes);
                System.out.println("Pretende entrar no chat(1), procurar um taxista(2) ou sair(3)?");
                pw.println(in.nextLine());
                
            } 
            
            else if (line.startsWith("Bem vindo taxista")){ //Utilizador fez LogIn.
                saudacoes = line;
                System.out.println(saudacoes);
                System.out.println("Pretende entrar no chat(1), procurar um passageiro(2) ou sair(3)?");
                pw.println(in.nextLine());
      
            } 
            /**********************************/
            
         
            /***********PEDIDO_PASSAGEIRO*********/
            else if ("PEDIDO_P".equals(line)){
                System.out.println("Indique a coordenada X do local\nonde se encontra:");
                pw.println(in.nextLine());
                System.out.println("Indique a coordenada Y do local\nonde se encontra:");
                pw.println(in.nextLine());

                System.out.println("Indique a coordenada X do local\npara onde se pretende deslocar:");
                pw.println(in.nextLine());
                System.out.println("Indique a coordenada Y do local\npara onde se pretende deslocar:");
                pw.println(in.nextLine());
               
                System.out.println("Por favor espere enquanto procuramos um taxista\npara realizar o seu pedido.");
            }      
            else if (line.startsWith("DADOS_P")) {
                System.out.println("Taxista encontrado.\n"+line.substring(7));
            }
            else if (line.startsWith("TEMPO")) {
                System.out.println("O táxista irá demorar "+line.substring(6)+" segundos\na chegar até si.");
            }
            else if (line.startsWith("CHEGADA_Pi")) {
                System.out.println("O taxista chegou até si.");
            }
            else if (line.startsWith("CHEGADA_Pf")) {
                System.out.println("Alcançou o seu destino.\nObrigado por usar este serviço de taxis.\nSer-lhe-ão cobrados: " + line.substring(10) + " euros.\nVolte sempre.");
                pw.println("fim");
            }
            /**********************************/

            
            /***********PEDIDO_TAXISTA*********/
            else if (line.startsWith("PEDIDO_T")){
                System.out.println(line.substring(8));
            }
            else if (line.startsWith("CHEGADA_Ti")) {
                System.out.println("Chegou até ao cliente.");
            }
            else if (line.startsWith("DADOS_T")) {
                System.out.println(line.substring(7));
            }
            else if (line.startsWith("CHEGADA_Tf")) {
                System.out.println("Levou o passageiro até ao seu destino.\nIrá ter que cobrar : " + line.substring(10) + " euros.\nObrigado.");
            }
            /**********************************/
 
            
            else if (line.startsWith("Opção inválida.")){
                System.out.println(line);
            }
        }
    }


    public static void main(String[] args) throws Exception {
        Teste i = new Teste();
        i.iniciar();
    }
     
}
        
      
     
