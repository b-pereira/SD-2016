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
public class Cliente {
    
           /** Variáveis de Classe ****************************/
          /**/ private String nome;     /*Nome do cliente*****/
	 /**/ private String contacto; /*Contacto do cliente*/
        /***************************************************/
        
         
	
        /***************Nome***********/
         
        public Cliente (String nome, String contacto) {
		this.nome     = nome;
		this.contacto = contacto;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String name) {
		this.nome = nome;
	}
	
        /*****************************/
        
        /**********Contacto***********/
        
        public String getContacto() {
		return contacto;
	}
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
        
        /****************************/
        
}
