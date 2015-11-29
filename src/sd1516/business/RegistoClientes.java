package sd1516.business;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import sd1516.utils.Posicao;

public class RegistoClientes {
  
  private HashMap<String, Cliente> clientes; 
  // <nome do cliente, objeto cliente>
  private HashMap<Posicao, String> taxistas; 
  // <posicao do taxista, nome do taxista> Este hashMaps
  // inclui apenas os taxistas que pretendem encontrar
  // cliente e existe para se poderem calcular as
  // distancias entre posicoes.
  
  
  
  public RegistoClientes() {
    super();
    this.clientes = new HashMap<>();
    this.taxistas = new HashMap<>();
  }
  
  
  public int sizeClientes() {
    return clientes.size();
  }
  public boolean isEmptyClientes() {
    return clientes.isEmpty();
  }
  public Cliente getCliente(Object key) {
    return clientes.get(key);
  }
  public boolean containsCliente(Object key) {
    return clientes.containsKey(key);
  }
  public Cliente putCliente(String key, Cliente value) {
    return clientes.put(key, value);
  }
  public Cliente removeCliente(Object key) {
    return clientes.remove(key);
  }
 
  
  
  
  
  
  
  


}
