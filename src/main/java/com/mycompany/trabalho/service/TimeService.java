package com.mycompany.trabalho.service;

/**
 *
 * @author autistictiger
 */

import com.mycompany.trabalho.dao.TimeDAO;
import com.mycompany.trabalho.model.Time;
import com.mycompany.trabalho.model.Jogador;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class TimeService {

    private TimeDAO timeDAO;

    public TimeService(EntityManager em) {
        this.timeDAO = new TimeDAO(em);
    }

    public void adicionarTime(String nome) {
        Time time = new Time();
        time.setNome(nome);
        timeDAO.salvar(time);
    }

    public Time buscarTimePorId(Long id) {
        return timeDAO.buscarPorId(id);
    }

    public List<Time> buscarTimesPorNome(String nome) {
        return timeDAO.buscarPorNome(nome);
    }

    public List<Time> listarTodosTimes() {
        return timeDAO.listarTodos();
    }

    public void atualizarTime(Long id, String nome) {
        Time time = timeDAO.buscarPorId(id);
        if (time != null) {
            time.setNome(nome);
            timeDAO.atualizar(time);
        } else {
            throw new RuntimeException("Time nao encontrado");
        }
    }
    
    public void mostrarJogadoresDoTime(Long timeId) {
        Time time = timeDAO.buscarPorId(timeId);
        if (time != null) {
            System.out.println("Jogadores do Time: " + time.getNome());
            List<Jogador> todosJogadores = time.getJogadores();
            
            List<Jogador> jogadores = todosJogadores.stream().filter(Jogador::getAtivo).collect(Collectors.toList()); 

            
            if (jogadores == null || jogadores.isEmpty()) {
                System.out.println("Nenhum jogador encontrado para este time.");
            } else {
                for (Jogador jogador : jogadores) {
                    System.out.println("Nome: " + jogador.getNome());
                    System.out.println("Número da Camisa: " + jogador.getNumeroCamisa());
                    System.out.println("Data de Nascimento: " + jogador.getDataNascimento());
                    System.out.println("-------------------------");
                }
            }
        } else {
            System.out.println("Time não encontrado.");
        }
    }

    public void removerTime(Long id) {
        timeDAO.remover(id);
    }
    
    public void atualizarEstatisticasTime(Time time, int golsMarcados, int golsSofridos, boolean vitoria, boolean empate) {
        time.setNGols(time.getNGols() + golsMarcados); 
        time.setNJogos(time.getNJogos() + 1);

        if (vitoria) {
            time.setPontos(time.getPontos() + 3);
            time.setVitorias(time.getVitorias() + 1); 
        } else if (empate) {
            time.setPontos(time.getPontos() + 1); 
            time.setEmpates(time.getEmpates() + 1); 
        } else {
            time.setDerrotas(time.getDerrotas() + 1);
        }

        timeDAO.atualizar(time);
    }

    public void reverterEstatisticasTime(Time time, int golsMarcados, int golsSofridos, boolean vitoria, boolean empate) {
        time.setNGols(time.getNGols() - golsMarcados);
        time.setNJogos(time.getNJogos() - 1);

        if (vitoria) {
            time.setPontos(time.getPontos() - 3); 
            time.setVitorias(time.getVitorias() - 1);
        } else if (empate) {
            time.setPontos(time.getPontos() - 1);
        }

        timeDAO.atualizar(time); 
    }
    
    public void adicionarJogadorTime(Time time){
        timeDAO.atualizar(time);
    }
    
    public List<Time> buscarTimesPorNomeAtivo(String nome) {
        return timeDAO.buscarPorNome(nome).stream().filter(Time::getAtivo).collect(Collectors.toList());
    }
    
    public List<Time> buscarTimesPorNomeDesativo(String nome) {
        return timeDAO.buscarPorNome(nome).stream().filter(time -> !time.getAtivo()).collect(Collectors.toList());
    }
    
    public void desativarTime(Long id){
        timeDAO.desativar(id);
    }
    
    public void ativarTime(Long id){
        timeDAO.ativar(id);
    }
}
