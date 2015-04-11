package exemplojpa;

import javax.persistence.EntityManagerFactory;
import exemplojpa.persistencia.VendedorJpaController;
import exemplojpa.persistencia.exceptions.NonexistentEntityException;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;


public class Vendedor {
    
    private VendedorJpaController vendJC = null;
    private exemplojpa.persistencia.Vendedor v = null;
    
    public Vendedor(EntityManagerFactory emf){
        vendJC = new VendedorJpaController(emf);
    }
    public void criarNovoVendedor(){
        
        v = new exemplojpa.persistencia.Vendedor();
        String nome = JOptionPane.showInputDialog(null,"Digite um nome para o Vendedor");
        v.setNome(nome);
        try{
            vendJC.create(v);
            JOptionPane.showMessageDialog(null, v.getNome() + " foi gravado");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public String listaTudo(){
        String listaParaMensagem = null;
        List<exemplojpa.persistencia.Vendedor> listaP = vendJC.findVendedorEntities();
        if(listaP==null)
            JOptionPane.showMessageDialog(null, " Não há vendedores no banco");
        else{
            listaParaMensagem = "Dados lidos no banco:\n\n";
            Iterator i = listaP.iterator();
            while(i.hasNext()){
                v = new exemplojpa.persistencia.Vendedor();
                v = (exemplojpa.persistencia.Vendedor)i.next();
                listaParaMensagem += v.getId() + " - " + v.getNome() + "\n";
            }
               JOptionPane.showMessageDialog(null, listaParaMensagem);
        }
        return listaParaMensagem;
    }
    public void editaVendedor(){
        String lista = listaTudo();
        Integer id = 0;
        id = Integer.parseInt(JOptionPane.showInputDialog("Selecione um id para editar:\n\n" + lista));
        v = new exemplojpa.persistencia.Vendedor();
        v = vendJC.findVendedor(id);
        String novoNome = JOptionPane.showInputDialog("Insira um novo nome para o vendedor" + v.getNome());
        v.setNome(novoNome);
        try{
            vendJC.edit(v);
            JOptionPane.showMessageDialog(null, v.getId() +" atualizado");
        }
        catch(NonexistentEntityException nee){
            JOptionPane.showMessageDialog(null, nee.getMessage());
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void excluirVendedor(){
        String lista = listaTudo();
        Integer id = 0;
        id = Integer.parseInt(JOptionPane.showInputDialog("Selecione um id para excluir:\n\n" + lista));
        try{
            vendJC.destroy(id);
            JOptionPane.showMessageDialog(null, v.getId() +" excluido");
        }
        catch(NonexistentEntityException nee){
            JOptionPane.showMessageDialog(null, nee.getMessage());
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
     }
   }
}
