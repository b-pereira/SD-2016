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
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import sd1516.utils.Posicao;
import sd1516.utils.DadosTransito;


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
    
    private final HashMap<String, Posicao> taxistas;  // <posicao do taxista, nome do taxista> Este HashMap inclui apenas os taxistas livres que pretendem encontrar passageiro.
    
    private final HashMap<String, DadosTransito> emTransito;
    
     /**
     * Set com os printwiters de todos os inscritos activos.
     * Irá servir para se manter uma fácil comunicação com o servidor para o chat.
     */
    private static final HashSet<PrintWriter> pws = new HashSet<PrintWriter>();
    
    private final Lock lock                 = new ReentrantLock();
    private final Condition taxiLivre       = lock.newCondition();
    private final Condition transito        = lock.newCondition();
    
    public Dados () {
         //Implementar busca a um ficheiro para encher o HashMap
         inscritos   = new HashMap<>();
         taxistas    = new HashMap<>();
         emTransito  = new HashMap<>();
         
    }
     
     
    /***Operacoes do chat***/
    
    public void removeEscritor(PrintWriter pw) {
        synchronized(pws) {
            pws.remove(pw);
        }
    } 
    
    public void adicionaEscritor(PrintWriter pw) {
        synchronized(pws) {
            pws.add(pw);
        }
    }
    
    public void enviaMensagem(String mensagem, String nome, String tipo) {
        synchronized(pws) {
            for (PrintWriter escritor : pws) {
                escritor.println( "-> " + tipo + " " + nome + " diz: " + mensagem);
            }
        }
    }
    
    /************************/
    

     
    public Passageiro getPassageiro (String nome) {
        synchronized(inscritos) {
            return inscritos.get(nome);
        }
    }
     
    public String getMatricula(String s1){
        synchronized(inscritos){
            Taxista tax = (Taxista) inscritos.get(s1);
            return tax.getMatricula();
        }
    }
    
    public String getModelo(String s1){
        synchronized(inscritos) {
            Taxista tax = (Taxista) inscritos.get(s1);
            return tax.getModelo();
        }
    }
    
    public int logIn (String s1, String s2, PrintWriter pw, BufferedReader br) throws IOException{
        int i;
        synchronized(inscritos) {
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
    }

    /**********************sign in***********************/
        
    public void signIn (PrintWriter pw, BufferedReader br) throws IOException{
        String s1,s2,s3,s4,s5;
        int i;
        synchronized(inscritos) {
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
    }
        
    /****************************************************/
    
    
    public void putTaxista (String nome, int x, int y) {
        lock.lock();
        try {
            taxistas.put(nome, new Posicao(x,y)); // coloca taxista
            taxiLivre.signal();
        } finally {
            lock.unlock();
        }
    }
     
    public void getTaxista (int x, int y, int x2, int y2, PrintWriter pwp) {
        Posicao pos;
        String nome = null;
        int i, j=0;
        boolean primeiro = true;
      
        lock.lock();
        try {
            while (taxistas.isEmpty()) {
                try {
                    taxiLivre.await();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
            for(Entry<String, Posicao> e : taxistas.entrySet()) {
                pos = e.getValue();
            
                i = calcularTemp (pos.getX(), pos.getY(), x,y);
            
                if (primeiro){
                    j = i;
                    nome = e.getKey();
                    primeiro = false; 
                }
            
                if (i<j){ //este taxista encontra-se mais próximo.
                    j=i;
                    nome = e.getKey();
                }
            }
            
            taxistas.remove(nome);
            emTransito.put(nome, new DadosTransito (new Posicao(x,y), new Posicao(x2,y2), pwp));
            transito.signalAll();
        } finally {
            lock.unlock();
        }
    }    
     
    public DadosTransito getDadosTransito(String nome){
        lock.lock();
        try {
           while (emTransito.get(nome) == null) {
               try {
                   transito.await();
               } catch (InterruptedException ie) {
                   ie.printStackTrace();
               }
           }
           DadosTransito dt = emTransito.get(nome);
           emTransito.remove(nome);
           return dt;
        } finally {
            lock.unlock();
        }
    }
    
    public Taxista getObjetoTaxista (String nome) {
        synchronized(inscritos) {
            return (Taxista) inscritos.get(nome);
        }
    }
    
    public Passageiro getObjetoPassageiro (String nome) {
        synchronized(inscritos) {
            return inscritos.get(nome);
        }
    }
    
    public int calcularTemp (int x, int y, int x1, int y1) {
            
         //Assumiremos que demora um segundo a passar de uma posição para outra consecutiva.
        //Calcula o tempo usando a distancia Manhattam
        return Math.abs(x-x1) + Math.abs(y-y1);
    }
}
