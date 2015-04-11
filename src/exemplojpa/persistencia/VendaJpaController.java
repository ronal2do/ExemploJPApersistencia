/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplojpa.persistencia;

import exemplojpa.persistencia.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Internet
 */
public class VendaJpaController implements Serializable {

    public VendaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venda venda) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produto produto = venda.getProduto();
            if (produto != null) {
                produto = em.getReference(produto.getClass(), produto.getId());
                venda.setProduto(produto);
            }
            Vendedor vendedor = venda.getVendedor();
            if (vendedor != null) {
                vendedor = em.getReference(vendedor.getClass(), vendedor.getId());
                venda.setVendedor(vendedor);
            }
            em.persist(venda);
            if (produto != null) {
                produto.getVendaCollection().add(venda);
                produto = em.merge(produto);
            }
            if (vendedor != null) {
                vendedor.getVendaCollection().add(venda);
                vendedor = em.merge(vendedor);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venda venda) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venda persistentVenda = em.find(Venda.class, venda.getId());
            Produto produtoOld = persistentVenda.getProduto();
            Produto produtoNew = venda.getProduto();
            Vendedor vendedorOld = persistentVenda.getVendedor();
            Vendedor vendedorNew = venda.getVendedor();
            if (produtoNew != null) {
                produtoNew = em.getReference(produtoNew.getClass(), produtoNew.getId());
                venda.setProduto(produtoNew);
            }
            if (vendedorNew != null) {
                vendedorNew = em.getReference(vendedorNew.getClass(), vendedorNew.getId());
                venda.setVendedor(vendedorNew);
            }
            venda = em.merge(venda);
            if (produtoOld != null && !produtoOld.equals(produtoNew)) {
                produtoOld.getVendaCollection().remove(venda);
                produtoOld = em.merge(produtoOld);
            }
            if (produtoNew != null && !produtoNew.equals(produtoOld)) {
                produtoNew.getVendaCollection().add(venda);
                produtoNew = em.merge(produtoNew);
            }
            if (vendedorOld != null && !vendedorOld.equals(vendedorNew)) {
                vendedorOld.getVendaCollection().remove(venda);
                vendedorOld = em.merge(vendedorOld);
            }
            if (vendedorNew != null && !vendedorNew.equals(vendedorOld)) {
                vendedorNew.getVendaCollection().add(venda);
                vendedorNew = em.merge(vendedorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = venda.getId();
                if (findVenda(id) == null) {
                    throw new NonexistentEntityException("The venda with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venda venda;
            try {
                venda = em.getReference(Venda.class, id);
                venda.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venda with id " + id + " no longer exists.", enfe);
            }
            Produto produto = venda.getProduto();
            if (produto != null) {
                produto.getVendaCollection().remove(venda);
                produto = em.merge(produto);
            }
            Vendedor vendedor = venda.getVendedor();
            if (vendedor != null) {
                vendedor.getVendaCollection().remove(venda);
                vendedor = em.merge(vendedor);
            }
            em.remove(venda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venda> findVendaEntities() {
        return findVendaEntities(true, -1, -1);
    }

    public List<Venda> findVendaEntities(int maxResults, int firstResult) {
        return findVendaEntities(false, maxResults, firstResult);
    }

    private List<Venda> findVendaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venda.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Venda findVenda(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venda.class, id);
        } finally {
            em.close();
        }
    }

    public int getVendaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venda> rt = cq.from(Venda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
