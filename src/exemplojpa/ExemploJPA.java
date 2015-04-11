package exemplojpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

public class ExemploJPA {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ExemploJPAPU");
    public static void main(String[] args) {
        menuPrincipal();
    }

    public static void menuPrincipal(){
        int opcao = 0;
        String listaOpcoes = "1 - Produtos\n";
        listaOpcoes += "2 - Vendedores\n";

        opcao = Integer.parseInt(JOptionPane.showInputDialog(listaOpcoes));
        switch(opcao){
            case 1:
                trabalharProduto();
            break;
            case 2:
                trabalharVendedor();
            break;
        }
    }
    
    public static void trabalharProduto(){
        int opcao = 0;
        String listaOpcoes = "1 - Cadastrar Novo Produto\n";
        listaOpcoes += "2 - Listar todos os Produtos\n";
        listaOpcoes += "3 - Editar um Produto\n";
        listaOpcoes += "4 - Excluir um Produto\n";
        listaOpcoes += "9 - Encerrar\n";
        listaOpcoes += "0 - Voltar\n";
        Produto p = new Produto(emf);
        
        while(1==1){
            opcao = Integer.parseInt(JOptionPane.showInputDialog(listaOpcoes));
            switch(opcao){
                case 1:
                    p.criarNovoProduto();
                    break;
                    
                case 2:
                    p.listaTudo();
                    break;
                 
                case 3:
                    p.editaProduto();
                    break;
                    
                case 4:
                    p.excluirProduto();
                    break;
                
                case 9:
                    return;
                    
                case 0:
                    menuPrincipal();
                    break;
            }
           
            
        }        
    }
    
    public static void trabalharVendedor(){
        int opcao = 0;
        String listaOpcoes = "1 - Cadastrar Novo Vendedor\n";
        listaOpcoes += "2 - Listar todos os Vendedores\n";
        listaOpcoes += "3 - Editar um  Vendedor\n";
        listaOpcoes += "4 - Excluir um Vendedor\n";
        listaOpcoes += "9 - Encerrar\n";
        listaOpcoes += "0 - Voltar\n";
        Vendedor v = new Vendedor(emf);
        
        while(1==1){
            opcao = Integer.parseInt(JOptionPane.showInputDialog(listaOpcoes));
            switch(opcao){
                case 1:
                    v.criarNovoVendedor();
                    break;
                    
                case 2:
                    v.listaTudo();
                    break;
                 
                case 3:
                    v.editaVendedor();
                    break;
                    
                case 4:
                    v.excluirVendedor();
                    break;    
                    
                case 9:
                    return;
                    
                case 0:
                    menuPrincipal();
                    break;    
            }
           
            
        }        
    }
}
