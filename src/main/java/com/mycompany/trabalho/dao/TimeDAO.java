package com.mycompany.trabalho.dao;

/**
 *
 * @author autistictiger
 */

import com.mycompany.trabalho.model.Time;
import jakarta.persistence.EntityManager;
import java.util.List;

public class TimeDAO {

    private EntityManager em;

    public TimeDAO(EntityManager em) {
        this.em = em;
    }

    public void salvar(Time time) {
        em.getTransaction().begin();
        em.persist(time);
        em.getTransaction().commit();
    }

    public Time buscarPorId(Long id) {
        return em.find(Time.class, id);
    }

    public List<Time> buscarPorNome(String nome) {
        return em.createQuery("SELECT t FROM Time t WHERE t.nome LIKE :nome", Time.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }

    public List<Time> listarTodos() {
        return em.createQuery("SELECT t FROM Time t", Time.class).getResultList();
    }

    public void atualizar(Time time) {
        em.getTransaction().begin();
        em.merge(time);
        em.getTransaction().commit();
        System.err.println("Time atualizado");
    }

    public void remover(Long id) {
        Time time = em.find(Time.class, id);
        if (time != null) {
            em.getTransaction().begin();
            em.remove(time);
            em.getTransaction().commit();
        }
    }
    
    public void ativar(Long id){
        Time time = em.find(Time.class, id);
        time.setAtivo(true);
        em.getTransaction().begin();
        em.merge(time);
        em.getTransaction().commit();
    }
    
    
    public void desativar(Long id){
        Time time = em.find(Time.class, id);
        time.setAtivo(false);
        em.getTransaction().begin();
        em.merge(time);
        em.getTransaction().commit();
    }
}