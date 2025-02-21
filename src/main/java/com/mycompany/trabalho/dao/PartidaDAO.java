package com.mycompany.trabalho.dao;

/**
 *
 * @author autistictiger
 */

import com.mycompany.trabalho.model.Partida;
import jakarta.persistence.EntityManager;
import java.util.List;

public class PartidaDAO {

    private EntityManager em;

    public PartidaDAO(EntityManager em) {
        this.em = em;
    }

    public void salvar(Partida partida) {
        em.getTransaction().begin();
        em.persist(partida);
        em.getTransaction().commit();
    }

    public Partida buscarPorId(Long id) {
        return em.find(Partida.class, id);
    }

    public List<Partida> listarTodas() {
        return em.createQuery("SELECT p FROM Partida p", Partida.class).getResultList();
    }

    public void atualizar(Partida partida) {
        em.getTransaction().begin();
        em.merge(partida);
        em.getTransaction().commit();
    }

    public void remover(Long id) {
        Partida partida = em.find(Partida.class, id);
        if (partida != null) {
            em.getTransaction().begin();
            em.remove(partida);
            em.getTransaction().commit();
        }
    }
}