package com.mycompany.trabalho.model;

/**
 *
 * @author autistictiger
 */
import jakarta.persistence.*;

@Entity
public class Colocacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "time_id", nullable = false, unique = true)
    private Time time;

    @Column(nullable = false)
    private int posicao;

    
    

    public Colocacao() {
    }

    public Colocacao(Time time, int posicao) {
        this.time = time;
        this.posicao = posicao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    @Override
    public String toString() {
        return "Colocacao{" +
                "id=" + id +
                ", time=" + time +
                ", posicao=" + posicao +
                '}';
    }
}
