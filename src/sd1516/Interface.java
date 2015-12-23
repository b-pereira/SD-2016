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
import java.awt.event.ActionListener;
import java.net.Socket;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Interface {

    
    /***************Variáveis de Classe*************/
    
           /***********************************************************************************/
          /**/ private int port;          /**port*********************************************/
         /**/ private String host;       /**IP do host***************************************/
        /**/ private Socket socket;     /**socket*******************************************/
      /**/ private BufferedReader br; /**variável para leitura****************************/
     /**/ private PrintWriter pw;    /**variável para escrita****************************/ 
    /***********************************************************************************/    
    JFrame frame = new JFrame("Chat");
    JTextField textField = new JTextField(40);
    JTextArea messageArea = new JTextArea(8, 40);     

     public Interface() {

        // Definir GUI
        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.pack();


        textField.addActionListener(new ActionListener() {
            /**
             * Envia o que foi escrito na área de texto.
             * De seguida apaga para que o cliente possa escrever outra mensagem.
             */
            public void actionPerformed(ActionEvent e) {
                pw.println(textField.getText()); // Enviar a mensagem para o print writer.
                textField.setText(""); //Apagar a mensagem escrita na área de texto.
            }
        });
    }
     
    private String mensagem( String s1, String s2) {
        return JOptionPane.showInputDialog(frame,s1,s2,JOptionPane.PLAIN_MESSAGE);
    }
     
    private void iniciar() throws IOException {

        // Conectar ao servidor e definir o escritor e o leitor.
        String serverIP = mensagem("Indique o endereço IP do servidor.(exemplo: localHost)","Taxis");
        Socket socket = new Socket(serverIP, 9001);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pw = new PrintWriter(socket.getOutputStream(), true); // true serve para fazer auto flush
        String saudacoes = "Bem vindo";
        
        while (true) {
            String line = br.readLine();
            
            if (line.startsWith("Pretende fazer LogIn(1) ou SignIn(2)?")) { 
                pw.println((mensagem(line, "Taxis")));
            } 
            
            else if (line.startsWith("chat")) { // O cliente mostrou interesse em participar no chat.
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                textField.setEditable(true); 
            } else if (line.startsWith("->")) { //Recebemos uma mensagem do chat.
                messageArea.append(line + "\n"); 
            } 
            
            else if (line.startsWith("Insira o seu nome de utilizador:")){ //Pedimos nome de utilizador.
                pw.println((mensagem(line, "Autentificação")));
            } 
            
            else if (line.startsWith("Insira a sua password:")){ //Pedimos password.
                pw.println((mensagem(line, "Autentificação")));
            } 
            
            else if (line.startsWith("Bem vindo")){ //Utilizador fez LogIn.
                saudacoes = line;
            } 
            
            else if (line.startsWith("Pretende entrar no chat(1), procurar um taxista(2) ou sair(3)?")){
                pw.println((mensagem(line, saudacoes)));
            }
        }
    }


    public static void main(String[] args) throws Exception {
        Interface i = new Interface();
        i.iniciar();
    }
     
}
        
      
     