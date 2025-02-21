package com.mycompany.trabalho.service;

/**
 *
 * @author autistictiger
 */
import com.mycompany.trabalho.dao.ColocacaoDAO;
import com.mycompany.trabalho.dao.TimeDAO;
import com.mycompany.trabalho.model.Colocacao;
import com.mycompany.trabalho.model.Time;
import jakarta.persistence.EntityManager;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ColocacaoService {

    private ColocacaoDAO colocacaoDAO;
    private TimeDAO timeDAO;

    public ColocacaoService(EntityManager em) {
        this.colocacaoDAO = new ColocacaoDAO(em);
        this.timeDAO = new TimeDAO(em);
    }

    //Criterio pontos, vitórias e gols dos times.
    public void atualizarClassificacao() {
        colocacaoDAO.limparTabela();
        List<Time> times = timeDAO.listarTodos().stream()
                .sorted(Comparator
                        .comparingInt(Time::getPontos).reversed()
                        .thenComparingInt(Time::getVitorias).reversed()
                        .thenComparingInt(Time::getNGols).reversed()
                ).filter(Time::getAtivo).collect(Collectors.toList());

        
        int posicao = 1;
        for (int i = 0; i < times.size(); i++) {
            Time time = times.get(i);
            if(i > 0 && isEmpate(times.get(i - 1), time)) {
                posicao = i;
            }else {
                posicao = i + 1;
            }
            Colocacao colocacao = new Colocacao(time, posicao);
            colocacaoDAO.salvar(colocacao);
        }
    }

    private boolean isEmpate(Time time1, Time time2) {
        return time1.getPontos() == time2.getPontos() &&
               time1.getVitorias() == time2.getVitorias() &&
               time1.getNGols() == time2.getNGols();
    }

    
    public void exibirTabela() {
        List<Colocacao> colocacoes = colocacaoDAO.listarTodas();

        System.out.println("Posição | Nome do Time | Pontos | Vitorias | Gols | Jogos");
        for (Colocacao colocacao : colocacoes) {
            Time time = colocacao.getTime();
            System.out.printf("%d | %s | %d | %d | %d| %d%n",
                    colocacao.getPosicao(),
                    time.getNome(),
                    time.getPontos(),
                    time.getVitorias(),
                    time.getNGols(),
                    time.getNJogos());
        }
    }
}
