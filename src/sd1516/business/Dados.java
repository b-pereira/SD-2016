/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd1516.business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import sd1516.utils.Posicao;


/**
 *
 * @author Grupo 
 */
public class Dados {
    
    /**
     * <nome do inscrito, objeto passageiro> Este HashMap inclui todos os inscritos no sistema, passageiros ou taxistas.
     * Serve para fazer LogIn e SignIn.
     */
    private final HashMap<String, Passageiro> inscritos;
    
    private final HashMap<Posicao, String> taxistas;  // <posicao do taxista, nome do taxista> Este HashMap inclui apenas os taxistas livres que pretendem encontrar passageiro.
    private final HashSet<String> passageiros; // Esta queue inclui os nomes dos passageiros em espera por um taxista.
    
    private final HashMap<String, Posicao> emTransito;
    
     /**
     * Set com os printwiters de todos os inscritos activos.
     * Irá servir para se manter uma fácil comunicação com o servidor para o chat.
     */
    private static final HashSet<PrintWriter> pws = new HashSet<PrintWriter>();
    
    private final Lock lockPassageiros = new ReentrantLock();
    private final Lock lockTaxistas    = new ReentrantLock();
    private final Lock lockChat        = new ReentrantLock();
    private final Lock lockInscritos   = new ReentrantLock();
    
    public Dados () {
         //Implementar busca a um ficheiro para encher o HashMap
         inscritos   = new HashMap<>();
         passageiros = new HashSet<>();
         taxistas    = new HashMap<>();
         emTransito  = new HashMap<>();
         
    }
     
     
    /***Operacoes do chat***/
    
    public void removeEscritor(PrintWriter pw) {
        lockChat.lock();
        try {
            pws.remove(pw);
        }
        finally {
            lockChat.unlock();
        }
    } 
    
    public void adicionaEscritor(PrintWriter pw) {
        lockChat.lock();
        try {
            pws.add(pw);
        }
        finally {
            lockChat.unlock();
        }
    }
    
    public void enviaMensagem(String mensagem, String nome, String tipo) {
        lockChat.lock();
        try {
            for (PrintWriter escritor : pws) {
                escritor.println( "-> " + tipo + " " + nome + " diz: " + mensagem);
            }
        }
        finally {
            lockChat.unlock();
        }
    }
    
    /************************/
    

     
    public Passageiro getPassageiro (String nome) {
        lockPassageiros.lock();
        try {
            return inscritos.get(nome);
        }
        finally {
            lockPassageiros.unlock();
        }
    }
     
    public String getMatricula(String s1){
        lockTaxistas.lock();
        try {
            Taxista tax = (Taxista) inscritos.get(s1);
            return tax.getMatricula();
        }
        finally {
            lockTaxistas.unlock();
        }
    }
    
    public String getModelo(String s1){
        lockTaxistas.lock();
        try {
            Taxista tax = (Taxista) inscritos.get(s1);
            return tax.getModelo();
        }
        finally {
            lockTaxistas.unlock();
        }
    }
    
    public int logIn (String s1, String s2, PrintWriter pw, BufferedReader br) throws IOException{
        int i;
        lockInscritos.lock();
        try {
            if (!(inscritos.containsKey(s1))) return -1; // não existe
            
            Passageiro z = inscritos.get(s1);
            
            if (!(z.getPassword().equals(s2))) return -1; // password errada
            
            if (z.getClass().getName().equals("sd1516.business.Passageiro")) return 0; // existe e é passageiro
        
            while (true){
           
                pw.println("TAXISTA");
                i = Integer.parseInt(br.readLine());
                if (i == 1) return 0;
                if (i == 2) break;
                else pw.println("Opção inválida.");
            }
            
            return 1; // existe e é taxista
        }
        finally {
            lockInscritos.unlock();
        }
    
    }

    /**********************sign in***********************/
        
    public void signIn (PrintWriter pw, BufferedReader br) throws IOException{
        String s1,s2,s3,s4,s5;
        int i;
        lockInscritos.lock();
        try {
            while (true) {
                pw.println("Pretende inscrever-se como passageiro(1) ou taxista(2)?");
                i = Integer.parseInt(br.readLine());
                
                if (i == 1 || i == 2) break;
                else pw.println("Opção inválida.");
            }
            
            pw.println("Insira o nome de utilizador a usar:");
	    s1 = br.readLine();
            pw.println("Insira a password a usar:");
	    s2 = br.readLine();
            pw.println("Insira o seu contacto:");
	    s3 = br.readLine();
            
            if (inscritos.containsKey(s1)) {
                
                pw.println("SignInKO");
                return;
            }
            
            if (i == 2) {
                pw.println("Insira o modelo do seu carro:");
                s4 = br.readLine();

                pw.println("Insira a matricula do seu carro:");
                s5 = br.readLine();
                
                pw.println("A registar..");
                inscritos.put(s1, new Taxista(new Posicao(0,0),s4,s5,s1,s3,s2)); //Todos os taxistas irão começar na posição x=0 e y=0, assuma-se que esta é a posicao da central dos taxistas.
                pw.println("Registado com sucesso!");
                return;
            }
            
            pw.println("A registar..");
            inscritos.put(s1, new Passageiro(s1,s3,s2, new Posicao(0,0)));
            pw.println("Registado com sucesso!");
        }
        finally {
            lockInscritos.unlock();
        }
    }
        
    /****************************************************/
    
    
    public void putTaxista (String nome, int x, int y) {
        lockTaxistas.lock();
        try {
            taxistas.put(new Posicao(x,y), nome); // coloca taxista
        } finally {
            lockTaxistas.unlock();
        }
    }
    
    public void putPassageiro (String nome) {
        lockPassageiros.lock();
        try {
            passageiros.add(nome); // coloca passageiro
        } finally {
            lockPassageiros.lock();
        }
    }
     
    public String getTaxista (int x, int y) {
        Posicao pos;
        String nome = null;
        int i, j=0;
        boolean primeiro = true;
      
        lockTaxistas.lock();
        try {
            for(Entry<Posicao, String> e : taxistas.entrySet()) {
                pos = e.getKey();
            
                i = calcularTemp (pos.getX(), pos.getY(), x,y);
            
                if (primeiro){
                    j = i; 
                    primeiro = false; 
                }
            
                if (i<j){
                    j=i;
                    nome = e.getValue();
                }
            }
            
            synchronized (emTransito) {
                emTransito.put(nome, new Posicao(x,y));
                emTransito.notifyAll(); // Avisar todos os taxistas
            }

            return nome;
            
        } finally {
            lockTaxistas.unlock();
        }
    }    
     
    public Posicao getDestino(String nome){
       synchronized (emTransito) { 
           while (emTransito.get(nome) == null) {
               try {
                   emTransito.wait();
               } catch (InterruptedException ie) {
                   ie.printStackTrace();
               }
           }
           return emTransito.get(nome);
       }
    }
    
    public Taxista getObjetoTaxista (String nome) {
        lockInscritos.lock();
        try {
            return (Taxista) inscritos.get(nome);
        } finally {
            lockInscritos.unlock();
        }
    }
    
    public Passageiro getObjetoPassageiro (String nome) {
        lockInscritos.lock();
        try {
            return inscritos.get(nome);
        } finally {
            lockInscritos.unlock();
        }
    }
    
    public int calcularTemp (int x, int y, int x1, int y1) {
            
         //Assumiremos que demora um minuto a passar de uma posição para outra consecutiva.
        //Calcula o tempo usando a distancia Manhattam
        return Math.abs(x-x1) + Math.abs(y-y1);
    }
}
