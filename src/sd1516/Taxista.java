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
public class Taxista extends Cliente{
    
           /** Variáveis de Classe ******************************/
          /**/ private String modelo;    /*Modelo do veículo****/
	 /**/ private String matricula; /*Matrícula do veículo*/
        /*****************************************************/
        
         
	
        /***************Construtor***********/
         
        public Taxista (String modelo, String matricula, String nome, String contacto) {
            
                super(nome,contacto);
		
                this.modelo    = modelo;
		this.matricula = matricula;
                
	}
        
        /***********************************/
        
        
        /***************Nome***********/
	
	public String getNome() {
		return super.getNome();
	}
	public void setNome(String nome) {
		super.setNome(nome);
	}
	
        /*****************************/
        
        /**********Contacto***********/
        
        public String getContacto() {
		return super.getContacto();
	}
	public void setContacto(String contacto) {
		super.setContacto(contacto);
	}
        
        /****************************/
        
        
	/***************Modelo***********/
	
        public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
        /******************************/
        
        /**********Matriculo***********/
        
        public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
        
        /****************************/
        
}
