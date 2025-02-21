package com.mycompany.trabalho.model;

import jakarta.persistence.*;
import java.util.List;

/**
 *
 * @author autistictiger
 */
@Entity
@Table(name = "partida") 
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "time_casa_id", nullable = false) 
    private Time timeCasa;

    private Integer golsCasa;

    @ManyToOne
    @JoinColumn(name = "time_visitante_id", nullable = false) 
    private Time timeVisitante;

    private Integer golsVisitante;

    @ManyToOne
    @JoinColumn(name = "vencedor_id") 
    private Time vencedor;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "jogadores_gols", joinColumns = @JoinColumn(name = "partida_id"), inverseJoinColumns = @JoinColumn(name = "jogador_id"))
    private List<Jogador> jogadoresGols;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "jogadores_em_campo", joinColumns = @JoinColumn(name = "partida_id"), inverseJoinColumns = @JoinColumn(name = "jogador_id"))
    private List<Jogador> jogadoresEmCampo;

    
    public Partida() {
    }

    public Partida(Time timeCasa, Time timeVisitante, Integer golsCasa, Integer golsVisitante, Time vencedor,
                   List<Jogador> jogadoresGols, List<Jogador> jogadoresEmCampo) {
        this.timeCasa = timeCasa;
        this.timeVisitante = timeVisitante;
        this.golsCasa = golsCasa;
        this.golsVisitante = golsVisitante;
        this.vencedor = vencedor;
        this.jogadoresGols = jogadoresGols;
        this.jogadoresEmCampo = jogadoresEmCampo;
    }
    
    //funcionando

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Time getTimeCasa() {
        return timeCasa;
    }

    public void setTimeCasa(Time timeCasa) {
        this.timeCasa = timeCasa;
    }

    public Integer getGolsCasa() {
        return golsCasa;
    }

    public void setGolsCasa(Integer golsCasa) {
        this.golsCasa = golsCasa;
    }

    public Time getTimeVisitante() {
        return timeVisitante;
    }

    public void setTimeVisitante(Time timeVisitante) {
        this.timeVisitante = timeVisitante;
    }

    public Integer getGolsVisitante() {
        return golsVisitante;
    }

    public void setGolsVisitante(Integer golsVisitante) {
        this.golsVisitante = golsVisitante;
    }

    public Time getVencedor() {
        return vencedor;
    }

    public void setVencedor(Time vencedor) {
        this.vencedor = vencedor;
    }

    public List<Jogador> getJogadoresGols() {
        return jogadoresGols;
    }

    public void setJogadoresGols(List<Jogador> jogadoresGols) {
        this.jogadoresGols = jogadoresGols;
    }

    public List<Jogador> getJogadoresEmCampo() {
        return jogadoresEmCampo;
    }

    public void setJogadoresEmCampo(List<Jogador> jogadoresEmCampo) {
        this.jogadoresEmCampo = jogadoresEmCampo;
    }

    @Override
    public String toString() {
        return "Partida{" +
                "id=" + id +
                ", timeCasa=" + timeCasa +
                ", golsCasa=" + golsCasa +
                ", timeVisitante=" + timeVisitante +
                ", golsVisitante=" + golsVisitante +
                ", vencedor=" + vencedor +
                ", jogadoresGols=" + jogadoresGols +
                ", jogadoresEmCampo=" + jogadoresEmCampo +
                '}';
    }
}