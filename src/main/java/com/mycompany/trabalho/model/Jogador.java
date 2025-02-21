package com.mycompany.trabalho.model;

/**
 *
 * @author autistictiger
 */

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Jogador{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    boolean ativo = true;
    
    @Column(nullable = false)
    private String nome;

    private int numeroCamisa;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    private int gols = 0;

    private int assistencias = 0;

    private int cartoesVermelhos = 0;

    private int cartoesAmarelos = 0;

    private boolean suspensao = false;

    private int jogosSuspensos = 0;

    private int numeroJogosFeitos = 0;

    @ManyToOne
    @JoinColumn(name = "time_id", nullable = false)
    private Time time;
    
    @ManyToMany(mappedBy = "jogadoresGols", cascade = CascadeType.ALL)
    private List<Partida> partidasComGols;

    @ManyToMany(mappedBy = "jogadoresEmCampo", cascade = CascadeType.ALL)
    private List<Partida> partidasEmCampo;


    
    public Jogador() {
    }

    public Jogador(String nome, int numeroCamisa, LocalDate dataNascimento, Time time) {
        this.nome = nome;
        this.numeroCamisa = numeroCamisa;
        this.dataNascimento = dataNascimento;
        this.time = time;
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

    public int getNumeroCamisa() {
        return numeroCamisa;
    }

    public void setNumeroCamisa(int numeroCamisa) {
        this.numeroCamisa = numeroCamisa;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getGols() {
        return gols;
    }

    public void setGols(int gols) {
        this.gols = gols;
    }

    public int getAssistencias() {
        return assistencias;
    }

    public void setAssistencias(int assistencias) {
        this.assistencias = assistencias;
    }

    public int getCartoesVermelhos() {
        return cartoesVermelhos;
    }

    public void setCartoesVermelhos(int cartoesVermelhos) {
        this.cartoesVermelhos = cartoesVermelhos;
    }

    public int getCartoesAmarelos() {
        return cartoesAmarelos;
    }

    public void setCartoesAmarelos(int cartoesAmarelos) {
        this.cartoesAmarelos = cartoesAmarelos;
    }

    public boolean isSuspensao() {
        return suspensao;
    }

    public void setSuspensao(boolean suspensao) {
        this.suspensao = suspensao;
    }

    public int getJogosSuspensos() {
        return jogosSuspensos;
    }

    public void setJogosSuspensos(int jogosSuspensos) {
        this.jogosSuspensos = jogosSuspensos;
    }

    public int getNumeroJogosFeitos() {
        return numeroJogosFeitos;
    }

    public void setNumeroJogosFeitos(int numeroJogosFeitos) {
        this.numeroJogosFeitos = numeroJogosFeitos;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
    
    public boolean getAtivo(){
        return ativo;
    }
    
    public void setAtivo(boolean ativo){
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Jogadores{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", numeroCamisa=" + numeroCamisa +
                ", dataNascimento=" + dataNascimento +
                ", gols=" + gols +
                ", assistencias=" + assistencias +
                ", cartoesVermelhos=" + cartoesVermelhos +
                ", cartoesAmarelos=" + cartoesAmarelos +
                ", suspensao=" + suspensao +
                ", jogosSuspensos=" + jogosSuspensos +
                ", numeroJogosFeitos=" + numeroJogosFeitos +
                ", time=" + time +
                '}';
    }
}