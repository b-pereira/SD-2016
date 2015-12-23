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
     

     public Interface() {

        // Definir GUI
        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.pack();

        // Add Listeners
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
        return JOptionPane.showInputDialog(
            frame,
            s1,
            s2,
            JOptionPane.PLAIN_MESSAGE);
    }
     
    private void iniciar() throws IOException {

        // Conectar ao servidor e definir o escritor e o leitor.
        String serverIP = mensagem("Indique o endereço IP do servidor.(exemplo: localHost)","Taxis");
        Socket socket = new Socket(serverIP, 7229);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pw = new PrintWriter(socket.getOutputStream(), true); // true serve para fazer auto flush
        
        while (true) {
            String line = br.readLine();
            if (line.startsWith("Pretende fazer LogIn(1) ou SignIn(2)?")) {
                pw.println((mensagem("Pretende fazer LogIn(1) ou SignIn(2)?", "Taxis")));
            } else if (line.startsWith("chat")) { // O cliente mostrou interesse em participar no chat.
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                textField.setEditable(true); 
            } else if (line.startsWith("->")) { //Recebemos uma mensagem do chat.
                messageArea.append(line + "\n"); 
            } 
        }
    }


    public static void main(String[] args) throws Exception {
        Interface i = new Interface();
        i.iniciar();
    }
     
}
        
      
     