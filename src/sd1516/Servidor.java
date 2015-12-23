/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package sd1516;

import java.io.*;
import java.util.*;
import java.net.ServerSocket;

import sd1516.threads.EscutaPedido;

/**
 *
 * @author ASUS
 */
public class Servidor {

    /**
     * O port onde o servidar irá escutar.
     */
    private static final int PORT = 9001;

    /**
     * O conjunto de todos os inscritos (clientes e taxistas)
     * e dos clientes/taxistas em espera.  
     */
    private static DataBase db;
     

    /**
     * O método main irá ouvir o port definido e lançar threads para cada utilizador aceite.
     */
    public static void main(String[] args) throws Exception {
        System.out.println("O programa de Taxis foi começado com sucesso.");
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) {
                new EscutaPedido(listener.accept(), db).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            listener.close();
        }
    }
}
