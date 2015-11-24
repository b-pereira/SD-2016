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
public class Taxista {
    
           /** Variáveis de Classe ******************************/
          /**/ private String modelo;    /*Modelo do veículo****/
	 /**/ private String matricula; /*Matrícula do veículo*/
        /*****************************************************/
        
         
	
        /***************Construtor***********/
         
        public Taxista (String modelo, String matricula) {
		this.modelo    = modelo;
		this.matricula = matricula;
	}
        
        /***********************************/
        
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
