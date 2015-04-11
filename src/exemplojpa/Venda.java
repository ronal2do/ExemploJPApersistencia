package exemplojpa;

/**
 *
 * @author Ronaldo Lima
 */


import static exemplojpa.ExemploJPA.emf;
import exemplojpa.persistencia.ProdutoJpaController;
import javax.persistence.EntityManagerFactory;
import exemplojpa.persistencia.VendaJpaController;
import exemplojpa.persistencia.VendedorJpaController;
import exemplojpa.persistencia.exceptions.NonexistentEntityException;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;

public class Venda {

    private VendaJpaController vendaJC = null;
    private exemplojpa.persistencia.Venda venda = null;
    private Produto produto;
    private Vendedor vendedor;
    private ProdutoJpaController pjc;
    private VendedorJpaController vjc;
    private Object v;

    public Venda(EntityManagerFactory emf) {
        vendaJC = new VendaJpaController(emf);
        produto = new Produto(emf);
        vendedor = new Vendedor(emf);
        pjc = new ProdutoJpaController(emf);
        vjc = new VendedorJpaController(emf);

    }

    public void criarVenda() {
        String listaProdutos = "Selecione um ID destes Produtos:\n" + produto.listaTudo();
        String listaVendedores = "Selecione um ID destes Vendedores:\n" + vendedor.listaTudo();
        String vl = null;
        vl = JOptionPane.showInputDialog(listaProdutos);
        Integer idProduto = Integer.parseInt(vl);
        
        vl = JOptionPane.showInputDialog(listaVendedores);
        Integer idVendedor = Integer.parseInt(vl);
        
        vl = JOptionPane.showInputDialog("Digite um valor");
        Long valor = Long.parseLong(vl);
        
        venda = new exemplojpa.persistencia.Venda();
        
        venda.setProduto(pjc.findProduto(idProduto));
        venda.setVendedor(vjc.findVendedor(idVendedor));
        
        venda.setValorvenda(valor);
        try {
            vendaJC.create(venda);
            JOptionPane.showMessageDialog(null, "Venda " + venda.getId() + "foi gravado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public String listaTudo(){
        String listaParaMensagem = null;
        List<exemplojpa.persistencia.Venda> listaP = vendaJC.findVendaEntities();
        if(listaP==null)
            JOptionPane.showMessageDialog(null, "Não há vendas no banco");
        else{
            listaParaMensagem = "Dados lidos no banco:\n\n";
            Iterator i = listaP.iterator();
            while(i.hasNext()){
                exemplojpa.persistencia.Venda v = new exemplojpa.persistencia.Venda();
                v = (exemplojpa.persistencia.Venda)i.next();
                listaParaMensagem += v.getId() + " - " + v.getValorvenda() + " - " + v.getProduto().getNome() + " - " + v.getVendedor().getNome() +"\n";
            }
               JOptionPane.showMessageDialog(null, listaParaMensagem);
        }
        return listaParaMensagem;
    }
    
    public void editaVenda(){
        String lista = listaTudo();
        Integer id = 0;
        id = Integer.parseInt(JOptionPane.showInputDialog("Selecione um id para editar:\n\n" + lista));
        exemplojpa.persistencia.Venda v = new exemplojpa.persistencia.Venda();
        v = vendaJC.findVenda(id);
        String novaVenda = JOptionPane.showInputDialog("Insira um novo valor de venda para o produto " + v.getProduto().getNome() + " e para o vendedor " + v.getVendedor().getNome());
        
        v.setProduto(v.getProduto());
        v.setVendedor(v.getVendedor());
        v.setValorvenda(Long.parseLong(novaVenda));
        try{
            vendaJC.edit(v);
            JOptionPane.showMessageDialog(null, v.getId() +" atualizado");
        }
        catch(NonexistentEntityException nee){
            JOptionPane.showMessageDialog(null, nee.getMessage());
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
        public void excluirVenda(){
        String lista = listaTudo();
        Integer id = 0;
        id = Integer.parseInt(JOptionPane.showInputDialog("Selecione um id para excluir:\n\n" + lista));
        try{
            vendaJC.destroy(id);
            JOptionPane.showMessageDialog(null, id+" excluido");
        }
        catch(NonexistentEntityException nee){
            JOptionPane.showMessageDialog(null, nee.getMessage());
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
     }
   }
}
