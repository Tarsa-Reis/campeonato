package com.mycompany.trabalho.dao;

/**
 *
 * @author autistictiger
 */
import com.mycompany.trabalho.model.Expulsao;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ExpulsaoDAO {

    private EntityManager em;

    public ExpulsaoDAO(EntityManager em) {
        this.em = em;
    }

    public void salvar(Expulsao expulsao) {
        em.getTransaction().begin();
        em.persist(expulsao);
        em.getTransaction().commit();
    }

    public Expulsao buscarPorId(Long id) {
        return em.find(Expulsao.class, id);
    }

    public List<Expulsao> listarTodas() {
        return em.createQuery("SELECT e FROM Expulsao e", Expulsao.class).getResultList();
    }

    public void atualizar(Expulsao expulsao) {
        em.getTransaction().begin();
        em.merge(expulsao);
        em.getTransaction().commit();
    }

    public void remover(Long id) {
        Expulsao expulsao = em.find(Expulsao.class, id);
        if (expulsao != null) {
            em.getTransaction().begin();
            em.remove(expulsao);
            em.getTransaction().commit();
        }
    }

    public List<Expulsao> buscarPorJogador(Long jogadorId) {
        return em.createQuery("SELECT e FROM Expulsao e WHERE e.jogador.id = :jogadorId", Expulsao.class)
                .setParameter("jogadorId", jogadorId)
                .getResultList();
    }

    public List<Expulsao> buscarPorPartida(Long partidaId) {
        return em.createQuery("SELECT e FROM Expulsao e WHERE e.partida.id = :partidaId", Expulsao.class)
                .setParameter("partidaId", partidaId)
                .getResultList();
    }
}