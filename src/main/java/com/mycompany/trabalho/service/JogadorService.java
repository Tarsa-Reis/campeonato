/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabalho.service;

/**
 *
 * @author autistictiger
 */

import com.mycompany.trabalho.dao.JogadorDAO;
import com.mycompany.trabalho.dao.TimeDAO;
import com.mycompany.trabalho.model.Jogador;
import com.mycompany.trabalho.model.Time;
import jakarta.persistence.EntityManager;
import com.mycompany.trabalho.dao.ExpulsaoDAO;
import com.mycompany.trabalho.model.Expulsao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class JogadorService{

    private JogadorDAO jogadorDAO;
    private TimeDAO timeDAO;
    private TimeService timeService;
    private ExpulsaoDAO expulsaoDAO;

    public JogadorService(EntityManager em) {
        this.jogadorDAO = new JogadorDAO(em);
        this.timeDAO = new TimeDAO(em);
        this.timeService = new TimeService(em);
        this.expulsaoDAO = new ExpulsaoDAO(em);
    }

    public void adicionarJogador(String nome, int numeroCamisa, LocalDate dataNascimento, Long timeId) {
        Time time = timeDAO.buscarPorId(timeId);
        if (time != null) {
            Jogador jogador = new Jogador();
            jogador.setNome(nome);
            jogador.setNumeroCamisa(numeroCamisa);
            jogador.setDataNascimento(dataNascimento);
            jogador.setTime(time);
            
            time.getJogadores().add(jogador);
        
            timeService.adicionarJogadorTime(time);
            
            //jogadorDAO.salvar(jogador);
            
            
        } else {
            throw new RuntimeException("Time nao encontrado");
        }
    }

    public Jogador buscarJogadorPorId(Long id) {
        return jogadorDAO.buscarPorId(id);
    }

    public List<Jogador> buscarJogadorPorNome(String nome) {
        return jogadorDAO.buscarPorNome(nome);
    }

    public void removerJogador(Long id) {
        List<Expulsao> expulsoes = expulsaoDAO.buscarPorJogador(id);
        for(int i = 0; i<expulsoes.size();i++){
            expulsaoDAO.remover(expulsoes.get(i).getId());
        }
        jogadorDAO.remover(id);
    }
    public void desativarJogador(Long id){
        jogadorDAO.desativar(id);
    }
    
    public void ativarJogador(Long id){
        jogadorDAO.ativar(id);
    }

    public void atualizarJogador(Long id, String nome, int numeroCamisa, LocalDate dataNascimento, Long timeId) {
        Jogador jogador = jogadorDAO.buscarPorId(id);
        if (jogador != null) {
            Time time = timeDAO.buscarPorId(timeId);
            if (time != null) {
                jogador.setNome(nome);
                jogador.setNumeroCamisa(numeroCamisa);
                jogador.setDataNascimento(dataNascimento);
                jogador.setTime(time);
                jogadorDAO.atualizar(jogador);
            } else {
                throw new RuntimeException("Time nao encontrado");
            }
        } else {
            throw new RuntimeException("Jogador nao encontrado");
        }
    }
    
    public void salvar(Jogador jogador) {
        jogadorDAO.salvar(jogador);
    }
    
    public void adicionarGolJogador(Jogador jogador){
        jogadorDAO.atualizar(jogador);
    }
    
    public void removerGolJogador(Jogador jogador){
        jogadorDAO.atualizar(jogador);
    }
    
    
    public List<Jogador> buscarJogadorPorNomeAtivo(String nome) {
        return jogadorDAO.buscarPorNome(nome).stream().filter(Jogador::getAtivo).collect(Collectors.toList());
    }
    
    public List<Jogador> buscarJogadorPorNomeDesativo(String nome) {
        return jogadorDAO.buscarPorNome(nome).stream().filter(jogador -> !jogador.getAtivo()).collect(Collectors.toList());
    }
    
    public void expulsarJogador(Long id, int numeroJogos ){
        Jogador jogador = jogadorDAO.buscarPorId(id);
        jogador.setJogosSuspensos(numeroJogos);
        jogador.setCartoesVermelhos(jogador.getCartoesVermelhos()+1);
        jogador.setSuspensao(true);
        jogadorDAO.salvar(jogador);
    }
    
    public void retirarUmJogoSuspenso(Long id){
        Jogador jogador = jogadorDAO.buscarPorId(id);
        jogador.setJogosSuspensos(jogador.getJogosSuspensos()-1);
        if(jogador.getJogosSuspensos()==0){
            jogador.setSuspensao(false);
        }
        jogadorDAO.salvar(jogador);
    }
    
    public void removerExpulsao(Long id){
        Jogador jogador = jogadorDAO.buscarPorId(id);
        jogador.setJogosSuspensos(0);
        jogador.setCartoesVermelhos(jogador.getCartoesVermelhos()-1);
        jogador.setSuspensao(false);
        jogadorDAO.salvar(jogador);
    }
}