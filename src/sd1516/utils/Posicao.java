/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd1516.utils;

/**
 *
 * @author ASUS
 */
public class Posicao {
    int x;
    int y;
    
    public Posicao (int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    
    public int getX () {
        return x;
    }
    
    public int getY () {
        return y;
    }
    
    public void setX (int x) {
         this.x = x;
    }
    
    public void setY (int y) {
         this.y = y;
    }
    
    public String toString(){
        return "("+x+","+y+")";
    }
    
    
}
