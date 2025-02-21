package com.mycompany.trabalho.service;

/**
 *
 * @author autistictiger
 */

import com.mycompany.trabalho.dao.PartidaDAO;
import com.mycompany.trabalho.dao.JogadorDAO;
import com.mycompany.trabalho.model.Partida;
import com.mycompany.trabalho.model.Time;
import com.mycompany.trabalho.model.Jogador;
import jakarta.persistence.EntityManager;
import java.util.List;

public class PartidaService {

    private PartidaDAO partidaDAO;
    private JogadorDAO jogadorDAO;

    public PartidaService(EntityManager em) {
        this.partidaDAO = new PartidaDAO(em);
        this.jogadorDAO = new JogadorDAO(em);
    }

    public void adicionarPartida(Time timeCasa, Time timeVisitante, Integer golsCasa, Integer golsVisitante,
                                 Time vencedor, List<Jogador> jogadoresGols, List<Jogador> jogadoresEmCampo) {
        Partida partida = new Partida(timeCasa, timeVisitante, golsCasa, golsVisitante, vencedor, jogadoresGols, jogadoresEmCampo);
        partidaDAO.salvar(partida);
    }

    public Partida buscarPartidaPorId(Long id) {
        return partidaDAO.buscarPorId(id);
    }

    public List<Partida> listarTodasPartidas() {
        return partidaDAO.listarTodas();
    }

    public void atualizarPartida(Long id, Integer golsCasa, Integer golsVisitante, Time vencedor) {
        Partida partida = partidaDAO.buscarPorId(id);
        if (partida != null) {
            partida.setGolsCasa(golsCasa);
            partida.setGolsVisitante(golsVisitante);
            partida.setVencedor(vencedor);
            partidaDAO.atualizar(partida);
        } else {
            throw new RuntimeException("Partida nao encontrada");
        }
    }

    public void removerPartida(Long id) {
        Partida partida = partidaDAO.buscarPorId(id);
        List<Jogador> jogadores = partida.getJogadoresGols();
        for(int i =0; i < jogadores.size();i++){
            jogadores.get(i).setGols(jogadores.get(i).getGols()-1);
            jogadorDAO.atualizar(jogadores.get(i));
        }
        partidaDAO.remover(id);
    }
}