package com.mycompany.trabalho.dao;

/**
 *
 * @author autistictiger
 */
import com.mycompany.trabalho.model.Jogador;
import jakarta.persistence.EntityManager;
import java.util.List;

public class JogadorDAO {

    private EntityManager em;

    public JogadorDAO(EntityManager em) {
        this.em = em;
    }

    public void salvar(Jogador jogador) {
        em.getTransaction().begin();
        em.persist(jogador);
        em.getTransaction().commit();
    }

    public Jogador buscarPorId(Long id) {
        return em.find(Jogador.class, id);
    }

    public List<Jogador> buscarPorNome(String nome) {
        return em.createQuery("SELECT j FROM Jogador j WHERE j.nome LIKE :nome", Jogador.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }

    public void atualizar(Jogador jogador) {
        em.getTransaction().begin();
        em.merge(jogador);
        em.getTransaction().commit();
    }

    public void remover(Long id) {
        Jogador jogador = em.find(Jogador.class, id);
        if (jogador != null) {
            try {
                em.getTransaction().begin();
                if (!em.contains(jogador)) {
                    jogador = em.merge(jogador);
                }
                //em.remove(jogador);
                em.createNativeQuery("DELETE FROM jogadores_gols WHERE jogador_id = :id").setParameter("id", id).executeUpdate();
                em.createNativeQuery("DELETE FROM jogadores_em_campo WHERE jogador_id = :id").setParameter("id", id).executeUpdate();
                em.createNativeQuery("DELETE FROM jogador WHERE id = :id").setParameter("id", id).executeUpdate();
                em.getTransaction().commit();
                
                System.out.println("Transação commitada com sucesso.");
            } catch (Exception e) {
                
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                throw new RuntimeException("Erro ao remover jogador: " + e.getMessage(), e);
            }
        }else{
           throw new RuntimeException("Jogador não encontrado com o ID: " + id);
        }
    }
    
    public void ativar(Long id){
        Jogador jogador = em.find(Jogador.class, id);
        jogador.setAtivo(true);
        em.getTransaction().begin();
        em.merge(jogador);
        em.getTransaction().commit();
    }
    
    
    public void desativar(Long id){
        Jogador jogador = em.find(Jogador.class, id);
        jogador.setAtivo(false);
        em.getTransaction().begin();
        em.merge(jogador);
        em.getTransaction().commit();
    }
}

///teste
