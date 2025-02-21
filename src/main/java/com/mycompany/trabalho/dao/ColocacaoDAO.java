package com.mycompany.trabalho.dao;

/**
 *
 * @author autistictiger
 */

import com.mycompany.trabalho.model.Colocacao;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ColocacaoDAO {

    private EntityManager em;

    public ColocacaoDAO(EntityManager em) {
        this.em = em;
    }

    public void salvar(Colocacao colocacao) {
        em.getTransaction().begin();
        em.persist(colocacao);
        em.getTransaction().commit();
    }

    public Colocacao buscarPorId(Long id) {
        return em.find(Colocacao.class, id);
    }

    public List<Colocacao> listarTodas() {
        return em.createQuery("SELECT c FROM Colocacao c ORDER BY c.posicao", Colocacao.class).getResultList();
    }

    public void atualizar(Colocacao colocacao) {
        em.getTransaction().begin();
        em.merge(colocacao);
        em.getTransaction().commit();
    }

    public void remover(Long id) {
        Colocacao colocacao = em.find(Colocacao.class, id);
        if (colocacao != null) {
            em.getTransaction().begin();
            em.remove(colocacao);
            em.getTransaction().commit();
        }
    }

    public void limparTabela() {
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Colocacao").executeUpdate();
        em.getTransaction().commit();
    }
}