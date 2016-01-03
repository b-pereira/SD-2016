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

import java.awt.event.ActionEvent;
import java.net.Socket;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Cliente {

    
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

    
    /**********************************************/
    
     public Cliente() {

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
        
        JOptionPane.showMessageDialog(null, "Bem vindo ao serviço de Taxis!");
        // Conectar ao servidor e definir o escritor e o leitor.
        String serverIP = mensagem("Indique o endereço IP do servidor.(exemplo: localHost)","Taxis");
        Socket socket = new Socket(serverIP, 65000);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pw = new PrintWriter(socket.getOutputStream(), true); // true serve para fazer auto flush
        String saudacoes;
        
        while (true) {
            String line;
            if ((line=br.readLine())==null) continue;
            
            /*************INICIO***************/
            else if ("INICIO".equals(line)) { 
                pw.println((mensagem("Pretende fazer LogIn(1) ou SignIn(2)?", "Taxis")));
            } 
            /**********************************/
            
            
            /*************CHAT*****************/
            else if (line.startsWith("CHAT")) { // O cliente mostrou interesse em participar no chat.
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                textField.setEditable(true); 
            } else if (line.startsWith("->")) { //Recebemos uma mensagem do chat.
                messageArea.append(line + "\n"); 
            } 
            /***********************************/
            
            
            /************LOGIN******************/
            else if ("LOGIN".equals(line)){
                pw.println((mensagem("Insira o seu nome de utilizador:", "LogIn")));
                pw.println((mensagem("Insira a sua password:", "LogIn")));
            } 
            else if (line.startsWith("TAXISTA")){
                pw.println((mensagem("Foi detetado que esta conta possui tanto um passageiro associado como um taxista.\nPretende aceder ao sistema como passageiro(1) ou como taxista(2)?", "SignIn")));
            }
            else if (line.startsWith("LogInKO")){
                JOptionPane.showMessageDialog(null, "LogIn inválido, por favor tente novamente.");
            }
            /**********************************/
            
            
            /****************SIGNIN*************/
            else if (line.startsWith("Pretende inscrever-se como passageiro(1) ou taxista(2)?")){
                pw.println((mensagem(line, "SignIn")));
            }
            else if (line.startsWith("Insira o nome de utilizador a usar:")){
                pw.println((mensagem(line, "SignIn")));
            }
            else if (line.startsWith("Insira a password a usar:")){
                pw.println((mensagem(line, "SignIn")));
            }
            else if (line.startsWith("Insira o seu contacto:")){
                pw.println((mensagem(line, "SignIn")));
            }
            else if (line.startsWith("SignInKO")){
                JOptionPane.showMessageDialog(null, "Já existe um passageiro com este nome, por favor tente novamente.");
            }
            else if (line.startsWith("Insira o seu contacto:")){
                pw.println((mensagem(line, "SignIn")));
            }
            else if (line.startsWith("Insira o modelo do seu carro:")){
                pw.println((mensagem(line, "SignIn")));
            }
            else if (line.startsWith("Insira a matricula do seu carro:")){
                pw.println((mensagem(line, "SignIn")));
            }
            else if (line.startsWith("Registado com sucesso!")){
                JOptionPane.showMessageDialog(null, line);
            }
            else if (line.startsWith("Insira a matricula do seu carro:")){
                pw.println((mensagem(line, "SignIn")));
            }
            /**********************************/
            
            
            /***********SAUDACOES**************/
            else if (line.startsWith("Bem vindo passageiro")){ //Utilizador fez LogIn.
                saudacoes = line;
                JOptionPane.showMessageDialog(null, saudacoes);
                pw.println((mensagem("Pretende entrar no chat(1), procurar um taxista(2) ou sair(3)?", saudacoes)));
            } 
            
            else if (line.startsWith("Bem vindo taxista")){ //Utilizador fez LogIn.
                saudacoes = line;
                JOptionPane.showMessageDialog(null, saudacoes);
                pw.println((mensagem("Pretende entrar no chat(1), procurar um passageiro(2) ou sair(3)?", saudacoes)));
            } 
            /**********************************/
            
         
            /***********PEDIDO_PASSAGEIRO*********/
            else if ("PEDIDO_P".equals(line)){
                pw.println((mensagem("Indique a coordenada X do local\nonde se encontra:", "Procurar taxista")));
                pw.println((mensagem("Indique a coordenada Y do local\nonde se encontra:", "Procurar taxista")));

                pw.println((mensagem("Indique a coordenada X do local\npara onde se pretende deslocar:", "Procurar taxista")));
                pw.println((mensagem("Indique a coordenada Y do local\npara onde se pretende deslocar:", "Procurar taxista")));
                
                JOptionPane.showMessageDialog(null, "Por favor espere enquanto procuramos um taxista\npara realizar o seu pedido.");
            }      
            else if (line.startsWith("DADOS_P")) {
                JOptionPane.showMessageDialog(null, "Taxista encontrado.\n"+line.substring(7));
            }
            else if (line.startsWith("TEMPO")) {
                JOptionPane.showMessageDialog(null, "O táxista irá demorar "+line.substring(6)+" segundos\na chegar até si.");
            }
            else if (line.startsWith("CHEGADA_Pi")) {
                JOptionPane.showMessageDialog(null, "O taxista chegou até si.");
            }
            else if (line.startsWith("CHEGADA_Pf")) {
                JOptionPane.showMessageDialog(null, "Alcançou o seu destino.\nObrigado por usar este serviço de taxis.\nSer-lhe-ão cobrados: " + line.substring(10) + " euros.\nVolte sempre.");
                pw.println("fim");
            }
            /**********************************/

            
            /***********PEDIDO_TAXISTA*********/
            else if (line.startsWith("PEDIDO_T")){
                JOptionPane.showMessageDialog(null, line.substring(8));
            }
            else if (line.startsWith("CHEGADA_Ti")) {
                JOptionPane.showMessageDialog(null, "Chegou até ao cliente.");
            }
            else if (line.startsWith("DADOS_T")) {
                JOptionPane.showMessageDialog(null, line.substring(7));
            }
            else if (line.startsWith("CHEGADA_Tf")) {
                JOptionPane.showMessageDialog(null, "Levou o passageiro até ao seu destino.\nIrá ter que cobrar : " + line.substring(10) + " euros.\nObrigado.");
            }
            /**********************************/
 
            
            else if (line.startsWith("Opção inválida.")){
                JOptionPane.showMessageDialog(null, line);
                break;
            }
            
            else if ("TERMINAR".equals(line)) { 
                JOptionPane.showMessageDialog(null, "Obrigado por usar o nosso serviço.");
                break;
            } 
        }
    }


    public static void main(String[] args) throws Exception {
        Cliente i = new Cliente();
        i.iniciar();
    }
     
}
        
      
     
