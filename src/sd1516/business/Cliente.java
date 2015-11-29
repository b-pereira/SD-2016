/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package sd1516.business;

/**
 *
 * @author ASUS
 */
public class Cliente {

  /** Variáveis de Classe ****************************/
  /**/private String nome; /* Nome do cliente**** */
  /**/private String contacto; /* Contacto do cliente */
  /**/private String password; /* Password do cliente */

  /***************************************************/



  /************* Construtor *********/

  public Cliente(String nome, String contacto, String password) {
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
