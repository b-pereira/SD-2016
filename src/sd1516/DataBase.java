/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package sd1516;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import sd1516.business.Cliente;
import sd1516.business.Taxista;
import sd1516.utils.Posicao;

/**
 *
 * @author ASUS
 */
public class DataBase {

  private HashMap<String, Cliente> clientes; // <nome do cliente, objeto cliente>
  private Scanner scan = new Scanner(System.in);

  private HashMap<Posicao, String> taxistas; // <posicao do taxista, nome do taxista> Este hashMaps
                                             // inclui apenas os taxistas que pretendem encontrar
                                             // cliente e existe para se poderem calcular as
                                             // distancias entre posicoes.

  public DataBase() {
    // Implementar busca a um ficheiro para encher o HashMap
    clientes = new HashMap<>();

  }



  public void atualizarPos(Posicao pos, String nome) {
    Taxista tax = (Taxista) clientes.get(nome);
    tax.setPos(pos);
  }

  public Cliente getCliente(String nome) {
    return clientes.get(nome);
  }

  public String getMatricula(String matricula) {

    Taxista tax = (Taxista) clientes.get(matricula);
    return tax.getMatricula();
  }

  public String getModelo(String modelo) {

    Taxista tax = (Taxista) clientes.get(modelo);
    return tax.getModelo();
  }

  public int logIn(String nome, String password) {

    if (!(clientes.containsKey(nome)))
      return -1; // não existe

    Cliente cliente = clientes.get(nome);

    if (!(cliente.getPassword().equals(password)))
      return -1; // password errada

    if (cliente instanceof Cliente)
      return 0; // existe e é cliente

    return 1; // existe e é taxista
  }

  /********************** sign in ***********************/

  public void signIn() {
    String nomeUtilizador, password, contato, modelo, matricula;
    int i;

    System.out.println("\nPretende inscrever-se como cliente(1) ou taxista(2)?");
    i = scan.nextInt();

    System.out.println("\nInsira o nome de utilizador a usar:");
    nomeUtilizador = scan.next();
    System.out.println("\nInsira a password a usar:");
    password = scan.next();
    System.out.println("\nInsira o seu contacto:");
    contato = scan.next();

    if (clientes.containsKey(nomeUtilizador)) {

      System.out.println("Já existe um cliente com este nome, por favor tente novamente.");
      return;
    }

    if (i == 2) {
      System.out.println("\nInsira o modelo do seu carro:");
      modelo = scan.next();

      System.out.println("\nInsira a matricula do seu carro:");
      matricula = scan.next();

      System.out.println("A registar..");
      clientes.put(nomeUtilizador, new Taxista(new Posicao(0, 0), modelo, matricula,
          nomeUtilizador, contato, password)); // Todos os taxistas
      // irão começar na
      // posição x=0 e y=0,
      // assuma-se que esta é
      // a posicao da central
      // dos taxistas.
      System.out.println("\nRegistado com sucesso!");
      return;
    }

    System.out.println("A registar..");
    clientes.put(nomeUtilizador, new Cliente(nomeUtilizador, contato, password));
    System.out.println("\nRegistado com sucesso!");
  }

  /****************************************************/

  public String procurarTaxista(int xp, int yp) { // devolve nome do taxista mais perto da posicao
                                                  // argumento
    Posicao pos;
    String nome = null;
    int i, j = 0;
    boolean primeiro = true;

    for (Entry<Posicao, String> e : taxistas.entrySet()) {
      pos = e.getKey();

      i = calcularTemp(pos.getX(), pos.getY(), xp, yp);

      if (primeiro) {
        j = i;
        primeiro = false;
      }

      if (i < j) {
        j = i;
        nome = e.getValue();
      }
    }

    return nome;
  }


  public void taxistaEspera(String nome, int x, int y) {
    taxistas.put(new Posicao(x, y), nome);
  }


  public int calcularTemp(int x, int y, int x1, int y1) {

    // Assumiremos que demora um minuto a passar de uma posição para outra consecutiva.
    // Calcula o tempo usando a distancia Manhattam
    return Math.abs(x - x1) + Math.abs(y - y1);
  }
}
