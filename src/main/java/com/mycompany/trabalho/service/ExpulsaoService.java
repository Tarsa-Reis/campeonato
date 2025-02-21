package com.mycompany.trabalho.service;

/**
 *
 * @author autistictiger
 */

import com.mycompany.trabalho.dao.ExpulsaoDAO;
import com.mycompany.trabalho.model.Expulsao;
import com.mycompany.trabalho.model.Jogador;
import com.mycompany.trabalho.model.Time;
import com.mycompany.trabalho.model.Partida;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ExpulsaoService {

    private ExpulsaoDAO expulsaoDAO;

    public ExpulsaoService(EntityManager em) {
        this.expulsaoDAO = new ExpulsaoDAO(em);
    }

    public void adicionarExpulsao(Jogador jogador, Time time, Partida partida, int jogosSuspensao) {
        Expulsao expulsao = new Expulsao(jogador, time, partida, jogosSuspensao);
        expulsaoDAO.salvar(expulsao);
    }

    public Expulsao buscarExpulsaoPorId(Long id) {
        return expulsaoDAO.buscarPorId(id);
    }

    public List<Expulsao> listarTodasExpulsoes() {
        return expulsaoDAO.listarTodas();
    }

    public void atualizarExpulsao(Long id, int jogosSuspensao) {
        Expulsao expulsao = expulsaoDAO.buscarPorId(id);
        if (expulsao != null) {
            expulsao.setJogosSuspensao(jogosSuspensao);
            expulsaoDAO.atualizar(expulsao);
        } else {
            throw new RuntimeException("Expulsão nao encontrada");
        }
    }

    public void removerExpulsao(Long id) {
        expulsaoDAO.remover(id);
    }

    public List<Expulsao> buscarExpulsoesPorJogador(Long jogadorId) {
        return expulsaoDAO.buscarPorJogador(jogadorId);
    }

    public List<Expulsao> buscarExpulsoesPorPartida(Long partidaId) {
        return expulsaoDAO.buscarPorPartida(partidaId);
    }
}