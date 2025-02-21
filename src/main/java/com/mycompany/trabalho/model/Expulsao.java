package com.mycompany.trabalho.model;

/**
 *
 * @author autistictiger
 */
import jakarta.persistence.*;

@Entity
public class Expulsao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jogador_id", nullable = false)
    private Jogador jogador;

    @ManyToOne
    @JoinColumn(name = "time_id", nullable = false)
    private Time time;

    @ManyToOne
    @JoinColumn(name = "partida_id", nullable = false) 
    private Partida partida;

    @Column(name = "jogos_suspensao", nullable = false)
    private int jogosSuspensao;

    
    public Expulsao() {
    }

    public Expulsao(Jogador jogador, Time time, Partida partida, int jogosSuspensao) {
        this.jogador = jogador;
        this.time = time;
        this.partida = partida;
        this.jogosSuspensao = jogosSuspensao;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public int getJogosSuspensao() {
        return jogosSuspensao;
    }

    public void setJogosSuspensao(int jogosSuspensao) {
        this.jogosSuspensao = jogosSuspensao;
    }

    @Override
    public String toString() {
        return "Expulsao{" +
                "id=" + id +
                ", jogador=" + jogador +
                ", time=" + time +
                ", partida=" + partida +
                ", jogosSuspensao=" + jogosSuspensao +
                '}';
    }
}