/*
 * Classe Passageiro
 */
package sd1516.business;

import sd1516.utils.Posicao;

/**
 *
 * @author Daniel Malhadas, Bruno Pereira e Alexandre Silva
 */
public class Passageiro {

       /** Variáveis de Classe *****************************/
      /**/private String nome; /* Nome do cliente**********/
     /**/private String contacto; /* Contacto do cliente */
    /**/private String password; /* Password do cliente */
   /****************************************************/



  /************* Construtor *********/

  public Passageiro(String nome, String contacto, String password) {
    this.nome = nome;
    this.contacto = contacto;
    this.password = password;
  }
 
  /********************************/

  /*************** Nome ***********/

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  /*****************************/

  
  
  /************ Password ***********/

  public String getPassword() {
    return password;
  }

  public void setPassword(String pass) {
    this.password = pass;
  }

  /*****************************/

  /********** Contacto ***********/

  public String getContacto() {
    return contacto;
  }

  public void setContacto(String contacto) {
    this.contacto = contacto;
  }

  /****************************/

}
