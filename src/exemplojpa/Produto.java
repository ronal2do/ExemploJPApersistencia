package exemplojpa;

import javax.persistence.EntityManagerFactory;
import exemplojpa.persistencia.ProdutoJpaController;
import exemplojpa.persistencia.exceptions.NonexistentEntityException;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;


public class Produto {
    
    private ProdutoJpaController prodJC = null;
    private exemplojpa.persistencia.Produto p = null;
    
    public Produto(EntityManagerFactory emf){
        prodJC = new ProdutoJpaController(emf);
    }
    public void criarNovoProduto(){
        
        p = new exemplojpa.persistencia.Produto();
        String nome = JOptionPane.showInputDialog(null,"Digite um nome para o produto");
        p.setNome(nome);
        try{
            prodJC.create(p);
            JOptionPane.showMessageDialog(null, p.getNome() + " foi gravado");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public String listaTudo(){
        String listaParaMensagem = null;
        List<exemplojpa.persistencia.Produto> listaP = prodJC.findProdutoEntities();
        if(listaP==null)
            JOptionPane.showMessageDialog(null, "Não há produtos no banco");
        else{
            listaParaMensagem = "Dados lidos no banco:\n\n";
            Iterator i = listaP.iterator();
            while(i.hasNext()){
                p = new exemplojpa.persistencia.Produto();
                p = (exemplojpa.persistencia.Produto)i.next();
                listaParaMensagem += p.getId() + " - " + p.getNome() + "\n";
            }
               JOptionPane.showMessageDialog(null, listaParaMensagem);
        }
        return listaParaMensagem;
    }
    public void editaProduto(){
        String lista = listaTudo();
        Integer id = 0;
        id = Integer.parseInt(JOptionPane.showInputDialog("Selecione um id para editar:\n\n" + lista));
        p = new exemplojpa.persistencia.Produto();
        p = prodJC.findProduto(id);
        String novoNome = JOptionPane.showInputDialog("Insira um novo nome para o produto" + p.getNome());
        p.setNome(novoNome);
        try{
            prodJC.edit(p);
            JOptionPane.showMessageDialog(null, p.getId() +" atualizado");
        }
        catch(NonexistentEntityException nee){
            JOptionPane.showMessageDialog(null, nee.getMessage());
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void excluirProduto(){
        String lista = listaTudo();
        Integer id = 0;
        id = Integer.parseInt(JOptionPane.showInputDialog("Selecione um id para excluir:\n\n" + lista));
        try{
            prodJC.destroy(id);
            JOptionPane.showMessageDialog(null, p.getId() +" excluido");
        }
        catch(NonexistentEntityException nee){
            JOptionPane.showMessageDialog(null, nee.getMessage());
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
     }
   }
}
