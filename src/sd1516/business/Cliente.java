/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package sd1516.business;

import sd1516.utils.Posicao;

/**
 *
 * @author ASUS
 */
public class Cliente {

       /** Variáveis de Classe *****************************/
      /**/private String nome; /* Nome do cliente**********/
     /**/private String contacto; /* Contacto do cliente */
    /**/private String password; /* Password do cliente */
   /**/private Posicao pos; /* Posição do cliente ******/
  /****************************************************/



  /************* Construtor *********/

  public Cliente(String nome, String contacto, String password, Posicao pos) {
    this.nome = nome;
    this.contacto = contacto;
    this.password = password;
    this.pos = pos;
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

  
  public Posicao getPos () {
      return pos;
  } 
  
  public void setPos(Posicao pos) {
      this.pos = pos;
  }
  
  
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
