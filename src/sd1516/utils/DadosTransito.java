/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd1516.utils;

import java.io.PrintWriter;

/**
 *
 * @author ASUS
 */
public class DadosTransito {
    
    Posicao partida;
    Posicao chegada;
    
    PrintWriter pw;
    
    public DadosTransito (Posicao partida, Posicao chegada, PrintWriter pw) {
        this.partida = partida;
        this.chegada = chegada;
        
        this.pw = pw;
    }
    
    public Posicao getPartida (){
        return partida;
    }
    
    public Posicao getChegada (){
        return chegada;
    }
    
    public PrintWriter getPassageiroPW(){
        return pw;
    }
}
