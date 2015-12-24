/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd1516;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.util.*;
import java.util.Map.Entry;
import sd1516.business.Cliente;
import sd1516.business.Taxista;
import sd1516.utils.Posicao;
/**
 *
 * @author Grupo 
 */
public class DataBase {
    
    /**
     * <nome do inscrito, objeto cliente> Este HashMap inclui todos os inscritos no sistema, clientes ou taxistas.
     * Serve para fazer LogIn e SignIn.
     */
    private HashMap<String, Cliente> inscritos;
    
    private HashMap<Posicao, String> taxistas;  // <posicao do taxista, nome do taxista> Este HashMap inclui apenas os taxistas que pretendem encontrar cliente.
    private Queue<Cliente> clientes; // Esta queue inclui apenas os clientes que pretendem encontrar taxista.
    
     /**
     * Set com os printwiters de todos os inscritos activos.
     * Irá servir para se manter uma fácil comunicação com o servidor para o chat.
     */
    private static HashSet<PrintWriter> pws = new HashSet<PrintWriter>();
    
    public DataBase () {
         //Implementar busca a um ficheiro para encher o HashMap
         inscritos = new HashMap<>();
         
         taxistas = new HashMap<>();
         
    }
     
     
    /***Operacoes do chat***/
    
    public void removeEscritor(PrintWriter pw) {
        pws.remove(pw);
    } 
    
    public void adicionaEscritor(PrintWriter pw) {
        pws.add(pw);
    }
    
    public void enviaMensagem(String mensagem, String nome) {
        for (PrintWriter escritor : pws) {
            escritor.println( "-> Cliente " + nome + " diz: " + mensagem);
        }
    }
    
    /************************/
    public void atualizarPos (Posicao pos, String nome) {
         Taxista tax = (Taxista) inscritos.get(nome);
         tax.setPos(pos);
    }
     
    public Cliente getCliente (String nome) {
         return inscritos.get(nome);
    }
     
    public String getMatricula(String s1){
        
        Taxista tax = (Taxista) inscritos.get(s1);
        return tax.getMatricula();  
    }
    
    public String getModelo(String s1){
        
        Taxista tax = (Taxista) inscritos.get(s1);
        return tax.getModelo();  
    }
    
    public int logIn (String s1, String s2, PrintWriter pw, BufferedReader br) throws IOException{
        int i;
        
        if (!(inscritos.containsKey(s1))) return -1; // não existe
            
        Cliente z = inscritos.get(s1);
            
        if (!(z.getPassword().equals(s2))) return -1; // password errada
            
        if (z.getClass().getName().equals("sd1516.Cliente")) return 0; // existe e é cliente
        
        while (true){
           
            pw.println("Foi detetado que esta conta possui tanto um cliente associado como um taxista.\nPretende aceder ao sistema como cliente(1) ou como taxista(2)?");
            i = Integer.parseInt(br.readLine());
            if (i == 1) return 0;
            if (i == 2) break;
            else pw.println("Opção inválida.");
        }
            
        return 1; // existe e é taxista
    }

    /**********************sign in***********************/
        
    public void signIn (PrintWriter pw, BufferedReader br) throws IOException{
            String s1,s2,s3,s4,s5;
            int i;

            while (true) {
                pw.println("Pretende inscrever-se como cliente(1) ou taxista(2)?");
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
                
                pw.println("Já existe um cliente com este nome, por favor tente novamente.");
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
            inscritos.put(s1, new Cliente(s1,s3,s2, new Posicao(0,0)));
            pw.println("Registado com sucesso!");
    }
        
    /****************************************************/
     
    public synchronized String procurarTaxista (int xp, int yp) { // devolve nome do taxista mais perto da posicao argumento
        Posicao pos;
        String nome = null;
        int i, j=0;
        boolean primeiro = true;
      
        for(Entry<Posicao, String> e : taxistas.entrySet()) {
            pos = e.getKey();
            
            i = calcularTemp (pos.getX(), pos.getY(), xp,yp);
            
            if (primeiro){
                j = i; 
                primeiro = false; 
            }
            
            if (i<j){
                j=i;
                nome = e.getValue();
            }
        }
        
        return nome;
    }
    
    public void atualizar (String nome, int xp, int yp, int xc, int yc) {
        try {
            sleep(calcularTemp(xp,yp, xc,yc));
        } catch (Exception e) {}
        
        inscritos.get(nome).setPos(new Posicao (xc,yc));
        
        clientes.remove();
        
        
    }
    
    public void taxistaEspera (String nome, int x, int y) {
        taxistas.put(new Posicao(x,y), nome);
    }
    
    public void clienteEspera (String nome) {
        clientes.add(inscritos.get(nome));
    }
     
     
    public int calcularTemp (int x, int y, int x1, int y1) {
            
         //Assumiremos que demora um minuto a passar de uma posição para outra consecutiva.
        //Calcula o tempo usando a distancia Manhattam
        return Math.abs(x-x1) + Math.abs(y-y1);
    }
}
