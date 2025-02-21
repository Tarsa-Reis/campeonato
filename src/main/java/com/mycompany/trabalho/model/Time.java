package com.mycompany.trabalho.model;

/**
 *
 * @author autistictiger
 */

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "time", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Jogador> jogadores;
    
    boolean ativo = true;

    private int nGols = 0;

    private int nAssistencias = 0;

    private int nCVermelhos = 0;

    private int nCAmarelos = 0;

    private int pontos = 0;

    @Transient
    private int colocacao;

    private int nJogos = 0;
    
    private int vitorias = 0;
    
    private int derrotas = 0;
    
    private int empates = 0;
    
    
    
    
    
    

    public Time() {
        this.jogadores = new ArrayList<>(); 
    }

    public Time(String nome) {
        this.nome = nome;
        this.jogadores = new ArrayList<>(); 
    }
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public int getNGols() {
        return nGols;
    }

    public void setNGols(int nGols) {
        this.nGols = nGols;
    }

    public int getNAssistencias() {
        return nAssistencias;
    }

    public void setNAssistencias(int nAssistencias) {
        this.nAssistencias = nAssistencias;
    }

    public int getNCVermelhos() {
        return nCVermelhos;
    }

    public void setNCVermelhos(int nCVermelhos) {
        this.nCVermelhos = nCVermelhos;
    }

    public int getNCAmarelos() {
        return nCAmarelos;
    }

    public void setNCAmarelos(int nCAmarelos) {
        this.nCAmarelos = nCAmarelos;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public int getColocacao() {
        return colocacao;
    }

    public void setColocacao(int colocacao) {
        this.colocacao = colocacao;
    }

    public int getNJogos() {
        return nJogos;
    }

    public void setNJogos(int nJogos) {
        this.nJogos = nJogos;
    }
    
    public int getVitorias() {
        return vitorias;
    }
    
    public void setVitorias(int vitorias) {
        this.vitorias = vitorias;
    }
    
    public int getDerrotas() {
        return derrotas;
    }
    
    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }
    
    public int getEmpates() {
        return empates;
    }
    
    public void setEmpates(int empates) {
        this.empates = empates;
    }
    
    public boolean getAtivo(){
        return ativo;
    }
    
    public void setAtivo(boolean ativo){
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Time{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", nGols=" + nGols +
                ", nAssistencias=" + nAssistencias +
                ", nCVermelhos=" + nCVermelhos +
                ", nCAmarelos=" + nCAmarelos +
                ", pontos=" + pontos +
                ", colocacao=" + colocacao +
                ", nJogos=" + nJogos +
                ", vitorias=" + vitorias +
                ", derrotas=" + derrotas +
                ", empates= " + empates +
                '}';
    }
}