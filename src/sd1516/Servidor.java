/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package sd1516;

import java.io.IOException;
import java.net.ServerSocket;
import sd1516.business.Dados;
import sd1516.threads.EscutaPedido;

/**
 *
 * @author ASUS
 */
public class Servidor {

    /**
     * O port onde o servidar irá escutar.
     */
    private static final int PORT = 7229;

    /**
     * O conjunto de todos os inscritos (passageiros e taxistas)
     * e dos passageiros/taxistas em espera.  
     */
    private Dados db;
     
    
    public Servidor (String ficheiro) {
        //preenche db com os dados no ficheiro.
        db = new Dados();
    }

    public Dados getDados (){
        return db;
    }
    
    /**
     * O método main irá ouvir o port definido e lançar threads para cada utilizador aceite.
     * @param args
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        Servidor servidor = new Servidor ("dados");
        System.out.println("O programa de Taxis foi começado com sucesso.");
        ServerSocket listener;
        listener = new ServerSocket(PORT);
        
        try {
            while (true) {
                new EscutaPedido(listener.accept(), servidor.getDados()).start();
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            listener.close();
        }
    }
}
