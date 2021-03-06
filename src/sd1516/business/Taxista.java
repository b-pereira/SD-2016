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
public class Taxista extends Passageiro {

      /** Variáveis de Classe *******************************/
     /**/private String modelo;    /* Modelo do veículo ****/
    /**/private String matricula; /* Matrícula do veículo */
   /**/private Posicao pos;      /* Posicao do veículo ***/
  /******************************************************/



  /*************** Construtor ***********/

  public Taxista(Posicao pos, String modelo, String matricula, String nome, String contacto,
      String password) {

    super(nome, contacto, password);
    
    this.modelo = modelo;
    this.matricula = matricula;
    this.pos = pos;
  }

  /***********************************/


  public void setPos(Posicao pos) {
    this.pos = pos;
  }

  public Posicao getPos() {
    return pos;
  }


  /*************** Nome ***********/

  public String getNome() {
    return super.getNome();
  }

  public void setNome(String nome) {
    super.setNome(nome);
  }

  /*****************************/

  /************ Password ***********/

  public String getPassword() {
    return super.getPassword();
  }

  public void setPassword(String pass) {
    super.setPassword(pass);
  }

  /*****************************/

  /********** Contacto ***********/

  public String getContacto() {
    return super.getContacto();
  }

  public void setContacto(String contacto) {
    super.setContacto(contacto);
  }

  /****************************/


  /*************** Modelo ***********/

  public String getModelo() {
    return modelo;
  }

  public void setModelo(String modelo) {
    this.modelo = modelo;
  }

  /******************************/

  /********** Matriculo ***********/

  public String getMatricula() {
    return matricula;
  }

  public void setMatricula(String matricula) {
    this.matricula = matricula;
  }

  /****************************/

}
