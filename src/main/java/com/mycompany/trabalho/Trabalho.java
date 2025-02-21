package com.mycompany.trabalho;

/**
 *
 * @author autistictiger
 */
import jakarta.persistence.*;
import com.mycompany.trabalho.service.*;
import com.mycompany.trabalho.model.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Trabalho{

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static TimeService timeService;
    private static JogadorService jogadorService;
    private static PartidaService partidaService;
    private static ExpulsaoService expulsaoService;
    private static ColocacaoService colocacaoService;
    private static Scanner scanner;

    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("PostgresPU");
        em = emf.createEntityManager();
        timeService = new TimeService(em);
        jogadorService = new JogadorService(em);
        partidaService = new PartidaService(em);
        expulsaoService = new ExpulsaoService(em);
        colocacaoService = new ColocacaoService(em);
        scanner = new Scanner(System.in);

        boolean rodando = true;
        while (rodando) {
            System.out.println("Escolha uma area:");
            System.out.println("1 - Time");
            System.out.println("2 - Jogadores");
            System.out.println("3 - Colocacao");
            System.out.println("4 - Partidas");
            System.out.println("5 - Expulsao");
            System.out.println("0 - Sair");
            int escolha = scanner.nextInt();
            scanner.nextLine(); 

            switch (escolha) {
                case 1:
                    areaTime();
                    break;
                case 2:
                    areaJogadores();
                    break;
                case 3:
                    areaColocacao();
                    break;
                case 4:
                    areaPartidas();
                    break;
                case 5:
                    areaExpulsao();
                    break;
                case 0:
                    rodando = false;
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        }

        em.close();
        emf.close();
        scanner.close();
    }

    private static void areaTime() {
        boolean continua = false;
        while (!continua) {
            System.out.println("Escolha uma opcao:");
            System.out.println("1 - Criar Time");
            System.out.println("2 - Remover Time");
            System.out.println("3 - Buscar Time");
            System.out.println("4 - Ativar Time");
            System.out.println("0 - Voltar");
            int escolha = scanner.nextInt();
            scanner.nextLine(); 

            switch (escolha) {
                case 1:
                    criarTime();
                    break;
                case 2:
                    removerTime();
                    break;
                case 3:
                    buscarTime();
                    break;
                case 4:
                    ativarTime();
                case 0:
                    continua = true;
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        }
    }

    private static void criarTime() {
        System.out.println("Digite o nome do time:");
        String nome = scanner.nextLine();
        System.out.println("Confirmar nome? (s/n)");
        String confirma = scanner.nextLine();
        if (confirma.equalsIgnoreCase("s")) {
            timeService.adicionarTime(nome);
            System.out.println("Time criado com sucesso!");
        } else {
            System.out.println("Criacao cancelada.");
        }
    }

    private static void removerTime() {
        System.out.println("Digite o nome do time:");
        String nome = scanner.nextLine();
        List<Time> times = timeService.buscarTimesPorNome(nome);
        if (times.isEmpty()) {
            System.out.println("Nenhum time encontrado com esse nome.");
        } else {
            System.out.println("Escolha o time para remover:");
            for (int i = 0; i < times.size(); i++) {
                System.out.println((i + 1) + " - " + times.get(i).getNome());
            }
            int escolha = scanner.nextInt();
            scanner.nextLine(); 
            if (escolha > 0 && escolha <= times.size()) {
                System.out.println("Confirmar remocao? (s/n)");
                String confirma = scanner.nextLine();
                if (confirma.equalsIgnoreCase("s")) {
                    timeService.removerTime(times.get(escolha - 1).getId());
                    colocacaoService.atualizarClassificacao();
                    System.out.println("Time removido com sucesso!");
                } else {
                    System.out.println("Remocao cancelada.");
                }
            } else {
                System.out.println("Escolha invalida!");
            }
        }
    }

    private static void buscarTime() {
        System.out.println("Digite o nome do time:");
        String nome = scanner.nextLine();
        List<Time> times = timeService.buscarTimesPorNomeAtivo(nome);
        if (times.isEmpty()) {
            System.out.println("Nenhum time encontrado com esse nome.");
        } else {
            System.out.println("Escolha o time para ver detalhes:");
            for (int i = 0; i < times.size(); i++) {
                System.out.println((i + 1) + " - " + times.get(i).getNome());
            }
            int escolha = scanner.nextInt();
            scanner.nextLine(); 
            if (escolha > 0 && escolha <= times.size()) {
                Time time = times.get(escolha - 1);
                System.out.println("Nome: " + time.getNome());
                System.out.println("Pontos: " + time.getPontos());
                System.out.println("Vitórias: " + time.getVitorias());
                System.out.println("Gols: " + time.getNGols());
                System.out.println("Jogos: " + time.getNJogos());

                boolean continua = true;
                while (continua) {
                    System.out.println("Escolha uma opcao:");
                    System.out.println("1 - Excluir Time");
                    System.out.println("2 - Ver Jogadores");
                    System.out.println("3 - Desativar Time");
                    System.out.println("0 - Voltar");
                    int opcao = scanner.nextInt();
                    scanner.nextLine(); 

                    switch (opcao) {
                        case 1:
                            System.out.println("Confirmar exclusao? (s/n)");
                            String confirma = scanner.nextLine();
                            if (confirma.equalsIgnoreCase("s")) {
                                timeService.removerTime(time.getId());
                                System.out.println("Time excluido com sucesso!");
                                continua = false;
                            } else {
                                System.out.println("Exclusao cancelada.");
                            }
                            break;
                        case 2:
                            timeService.mostrarJogadoresDoTime(time.getId());
                            break;
                        case 3:
                            System.out.println("Confirmar desativacao? (s/n)");
                            String desativa = scanner.nextLine();
                            if (desativa.equalsIgnoreCase("s")) {
                                timeService.desativarTime(time.getId());
                                System.out.println("Time desativado com sucesso!");
                                colocacaoService.atualizarClassificacao();
                                continua = false;
                            } else {
                                System.out.println("Desativacao cancelada.");
                            }
                        case 0:
                            continua = false;
                            break;
                        default:
                            System.out.println("Opcao invalida!");
                    }
                }
            } else {
                System.out.println("Escolha invalida!");
            }
        }
    }
    
    private static void ativarTime() {
        System.out.println("Digite o nome do time:");
        String nome = scanner.nextLine();
        List<Time> times = timeService.buscarTimesPorNomeDesativo(nome);
        if (times.isEmpty()) {
            System.out.println("Nenhum time encontrado com esse nome.");
        } else {
            System.out.println("Escolha o time para ativar:");
            for (int i = 0; i < times.size(); i++) {
                System.out.println((i + 1) + " - " + times.get(i).getNome());
            }
            int escolha = scanner.nextInt();
            scanner.nextLine(); 
            if (escolha > 0 && escolha <= times.size()) {
                System.out.println("Confirmar ativacao? (s/n)");
                String confirma = scanner.nextLine();
                if (confirma.equalsIgnoreCase("s")) {
                    timeService.ativarTime(times.get(escolha - 1).getId());
                    System.out.println("Time ativado com sucesso!");
                    colocacaoService.atualizarClassificacao();
                } else {
                    System.out.println("Ativacao cancelada.");
                }
            } else {
                System.out.println("Escolha invalida!");
            }
        }
    }

    private static void areaJogadores() {
        boolean continua = true;
        while (continua) {
            System.out.println("Escolha uma opcao:");
            System.out.println("1 - Adicionar Jogador");
            System.out.println("2 - Remover Jogador");
            System.out.println("3 - Buscar Jogador");
            System.out.println("4 - Ativar Jogador");
            System.out.println("0 - Voltar");
            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1:
                    adicionarJogador();
                    break;
                case 2:
                    removerJogador();
                    break;
                case 3:
                    buscarJogador();
                    break;
                case 4:
                    ativarJogador();
                    break;    
                case 0:
                    continua = false;
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        }
    }

    
    private static void adicionarJogador() {
        System.out.println("Digite o nome do jogador:");
        String nome = scanner.nextLine();
        System.out.println("Digite o número da camisa:");
        int numeroCamisa = scanner.nextInt();
        scanner.nextLine(); 
        System.out.println("Digite a data de nascimento (Padrao SQL - AAAA-MM-DD):");
        LocalDate dataNascimento = LocalDate.parse(scanner.nextLine());
        System.out.println("Digite o nome do time:");
        String nomeTime = scanner.nextLine();

        
        
        List<Time> times = timeService.buscarTimesPorNome(nomeTime);
        if (times.isEmpty()) {
            System.out.println("Nenhum time encontrado com esse nome.");
        } else {
            System.out.println("Escolha o time:");
            for (int i = 0; i < times.size(); i++) {
                System.out.println((i + 1) + " - " + times.get(i).getNome());
            }
            int escolha = scanner.nextInt();
            scanner.nextLine(); 

            if (escolha > 0 && escolha <= times.size()) {
                Time time = times.get(escolha - 1);

                
                jogadorService.adicionarJogador(nome, numeroCamisa, dataNascimento, time.getId());
                
                
                
                /*Jogador jogador = new Jogador();
                jogador.setNome(nome);
                jogador.setNumeroCamisa(numeroCamisa);
                jogador.setDataNascimento(dataNascimento);

                
                time.getJogadores().add(jogador); 
                jogador.setTime(time);

                
                jogadorService.salvar(jogador);*/

                System.out.println("Jogador adicionado com sucesso ao time " + time.getNome() + "!");
            } else {
                System.out.println("Escolha invalida!");
            }
        }
    }

    private static void removerJogador() {
        System.out.println("Digite o nome do jogador:");
        String nome = scanner.nextLine();
        List<Jogador> jogadores = jogadorService.buscarJogadorPorNome(nome);
        if (jogadores.isEmpty()) {
            System.out.println("Nenhum jogador encontrado com esse nome.");
        } else {
            System.out.println("Escolha o jogador para remover:");
            for (int i = 0; i < jogadores.size(); i++) {
                System.out.println((i + 1) + " - " + jogadores.get(i).getNome());
            }
            int escolha = scanner.nextInt();
            scanner.nextLine(); 
            if (escolha > 0 && escolha <= jogadores.size()) {
                System.out.println("Confirmar remocao? (s/n)");
                String confirma = scanner.nextLine();
                if (confirma.equalsIgnoreCase("s")) {
                    jogadorService.removerJogador(jogadores.get(escolha - 1).getId());
                    System.out.println("Jogador removido com sucesso!");
                } else {
                    System.out.println("Remocao cancelada.");
                }
            } else {
                System.out.println("Escolha invalida!");
            }
        }
    }

    private static void buscarJogador() {
        System.out.println("Digite o nome do jogador:");
        String nome = scanner.nextLine();
        List<Jogador> jogadores = jogadorService.buscarJogadorPorNomeAtivo(nome);
        if (jogadores.isEmpty()) {
            System.out.println("Nenhum jogador encontrado com esse nome.");
        } else {
            System.out.println("Escolha o jogador para ver detalhes:");
            for (int i = 0; i < jogadores.size(); i++) {
                System.out.println((i + 1) + " - " + jogadores.get(i).getNome());
            }
            int escolha = scanner.nextInt();
            scanner.nextLine(); 
            if (escolha > 0 && escolha <= jogadores.size()) {
                Jogador jogador = jogadores.get(escolha - 1);
                System.out.println("Nome: " + jogador.getNome());
                System.out.println("Número da Camisa: " + jogador.getNumeroCamisa());
                System.out.println("Data de Nascimento: " + jogador.getDataNascimento());
                System.out.println("Time: " + jogador.getTime().getNome());
                System.out.println("Gols: " + jogador.getGols());
                System.out.println("Suspensao: " + jogador.getCartoesVermelhos());

                boolean continua = true;
                while (continua) {
                    System.out.println("Escolha uma opcao:");
                    System.out.println("1 - Excluir Jogador");
                    System.out.println("2 - Desativar Jogador");
                    System.out.println("0 - Voltar");
                    int opcao = scanner.nextInt();
                    scanner.nextLine(); 

                    switch (opcao) {
                        case 1:
                            System.out.println("Confirmar exclusao? (s/n)");
                            String excluir = scanner.nextLine();
                            if (excluir.equalsIgnoreCase("s")) {
                                jogadorService.removerJogador(jogador.getId());
                                System.out.println("Jogador excluido com sucesso!");
                                continua = false;
                            } else {
                                System.out.println("Exclusao cancelada.");
                            }
                            break;
                        case 2:
                            System.out.println("Confirmar desativacao? (s/n)");
                            String desativar = scanner.nextLine();
                            if (desativar.equalsIgnoreCase("s")) {
                                jogadorService.desativarJogador(jogador.getId());
                                System.out.println("Jogador desativado com sucesso!");
                                continua = false;
                            } else {
                                System.out.println("Desativacao cancelada.");
                            }
                            break;
                        case 3:
                            System.out.println("Confirmar ativacao? (s/n)");
                            String ativar = scanner.nextLine();
                            if (ativar.equalsIgnoreCase("s")) {
                                jogadorService.ativarJogador(jogador.getId());
                                System.out.println("Jogador ativado com sucesso!");
                                continua = false;
                            } else {
                                System.out.println("ativacao cancelada.");
                            }
                            break;
                        case 0:
                            continua = false;
                            break;
                        default:
                            System.out.println("Opcao invalida!");
                    }
                }
            } else {
                System.out.println("Escolha invalida!");
            }
        }
    }
    
    
    private static void ativarJogador() {
        System.out.println("Digite o nome do jogador:");
        String nome = scanner.nextLine();
        List<Jogador> jogadores = jogadorService.buscarJogadorPorNomeDesativo(nome);
        if (jogadores.isEmpty()) {
            System.out.println("Nenhum jogador encontrado com esse nome.");
        } else {
            System.out.println("Escolha o jogador para ativar:");
            for (int i = 0; i < jogadores.size(); i++) {
                System.out.println((i + 1) + " - " + jogadores.get(i).getNome());
            }
            int escolha = scanner.nextInt();
            scanner.nextLine(); 
            if (escolha > 0 && escolha <= jogadores.size()) {
                System.out.println("Confirmar ativacao? (s/n)");
                String confirma = scanner.nextLine();
                if (confirma.equalsIgnoreCase("s")) {
                    jogadorService.ativarJogador(jogadores.get(escolha - 1).getId());
                    System.out.println("Jogador ativado com sucesso!");
                } else {
                    System.out.println("Ativacao cancelada.");
                }
            } else {
                System.out.println("Escolha invalida!");
            }
        }
    }
    
    

    private static void areaColocacao() {
        boolean continua = true;
        while (continua) {
            System.out.println("Escolha uma opcao:");
            System.out.println("1 - Atualizar Colocacao");
            System.out.println("2 - Ver Colocacao");
            System.out.println("0 - Voltar");
            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1:
                    colocacaoService.atualizarClassificacao();
                    System.out.println("Colocacao atualizada com sucesso!");
                    break;
                case 2:
                    colocacaoService.exibirTabela();
                    break;
                case 0:
                    continua = false;
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        }
    }

    private static void areaPartidas() {
        boolean continua = true;
        while (continua) {
            System.out.println("Escolha uma opcao:");
            System.out.println("1 - Ver Todas as Partidas");
            System.out.println("2 - Ver Partidas de um Time");
            System.out.println("3 - Excluir Partida");
            System.out.println("4 - Adicionar Partida");
            System.out.println("5 - Atualizar Partida");
            System.out.println("0 - Voltar");
            int escolha = scanner.nextInt();
            scanner.nextLine(); 

            switch (escolha) {
                case 1:
                    verTodasPartidas();
                    break;
                case 2:
                    verPartidasTime();
                    break;
                case 3:
                    excluirPartida();
                    break;
                case 4:
                    adicionarPartida();
                    break;
                case 5:
                    atualizarPartida();
                    break;
                case 0:
                    continua = false;
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        }
    }

    private static void verTodasPartidas() {
        List<Partida> partidas = partidaService.listarTodasPartidas();
        if (partidas.isEmpty()) {
            System.out.println("Nenhuma partida encontrada.");
        } else {
            for (Partida partida : partidas) {
                System.out.println("Partida ID: " + partida.getId());
                System.out.println("Time Casa: " + partida.getTimeCasa().getNome());
                System.out.println("Time Visitante: " + partida.getTimeVisitante().getNome());
                System.out.println("Gols Casa: " + partida.getGolsCasa());
                System.out.println("Gols Visitante: " + partida.getGolsVisitante());
                System.out.println("Vencedor: " + (partida.getVencedor() != null ? partida.getVencedor().getNome() : "Empate"));
                System.out.println("-------------------------");
            }
        }
    }

    private static void verPartidasTime() {
        System.out.println("Digite o nome do time:");
        String nome = scanner.nextLine();
        List<Time> times = timeService.buscarTimesPorNome(nome);
        if (times.isEmpty()) {
            System.out.println("Nenhum time encontrado com esse nome.");
        } else {
            System.out.println("Escolha o time:");
            for (int i = 0; i < times.size(); i++) {
                System.out.println((i + 1) + " - " + times.get(i).getNome());
            }
            int escolha = scanner.nextInt();
            scanner.nextLine(); 
            if (escolha > 0 && escolha <= times.size()) {
                Time time = times.get(escolha - 1);
                List<Partida> partidas = partidaService.listarTodasPartidas();
                for (Partida partida : partidas) {
                    if (partida.getTimeCasa().equals(time) || partida.getTimeVisitante().equals(time)) {
                        System.out.println("Partida ID: " + partida.getId());
                        System.out.println("Time Casa: " + partida.getTimeCasa().getNome());
                        System.out.println("Time Visitante: " + partida.getTimeVisitante().getNome());
                        System.out.println("Gols Casa: " + partida.getGolsCasa());
                        System.out.println("Gols Visitante: " + partida.getGolsVisitante());
                        System.out.println("Vencedor: " + (partida.getVencedor() != null ? partida.getVencedor().getNome() : "Empate"));
                        System.out.println("\n\n");
                    }
                }
            } else {
                System.out.println("Escolha invalida!");
            }
        }
    }

    private static void excluirPartida() {
        System.out.println("Digite o nome do time:");
        String nomeTime = scanner.nextLine();

        
        List<Partida> partidas = partidaService.listarTodasPartidas().stream()
                .filter(p -> p.getTimeCasa().getNome().equalsIgnoreCase(nomeTime) ||
                             p.getTimeVisitante().getNome().equalsIgnoreCase(nomeTime))
                .collect(Collectors.toList());

        if (partidas.isEmpty()) {
            System.out.println("Nenhuma partida encontrada para o time " + nomeTime + ".");
        } else {
            System.out.println("Partidas encontradas para o time " + nomeTime + ":");
            for (int i = 0; i < partidas.size(); i++) {
                Partida partida = partidas.get(i);
                System.out.println((i+1)+"   Time Casa: " + partida.getTimeCasa().getNome());
                System.out.println("   Time Visitante: " + partida.getTimeVisitante().getNome());
                System.out.println("   Gols Casa: " + partida.getGolsCasa());
                System.out.println("   Gols Visitante: " + partida.getGolsVisitante());
                System.out.println("   Vencedor: " + (partida.getVencedor() != null ? partida.getVencedor().getNome() : "Empate"));
                System.out.println("/n/n/n/n");
            }

            System.out.println("Escolha a partida para excluir:");
            int escolha = scanner.nextInt();
            scanner.nextLine();

            if (escolha > 0 && escolha <= partidas.size()) {
                Partida partida = partidas.get(escolha - 1);
                System.out.println("Confirmar exclusao da partida ID " + partida.getId() + "? (s/n)");
                String confirma = scanner.nextLine();
                if (confirma.equalsIgnoreCase("s")) {
                    partidaService.removerPartida(partida.getId());
                    System.out.println("Partida excluída com sucesso!");
                    colocacaoService.atualizarClassificacao();
                    Time timeCasa = partida.getTimeCasa();
                    Time timeVisitante = partida.getTimeVisitante();
                    int golsCasa = partida.getGolsCasa();
                    int golsVisitante = partida.getGolsVisitante();
                    boolean empate = (golsCasa == golsVisitante);
                    boolean vitoriaCasa = (golsCasa > golsVisitante);
                    boolean vitoriaVisitante = (golsVisitante > golsCasa);

                    timeService.reverterEstatisticasTime(timeCasa, golsCasa, golsVisitante, vitoriaCasa, empate);

                    timeService.reverterEstatisticasTime(timeVisitante, golsVisitante, golsCasa, vitoriaVisitante, empate);    
                    
                } else {
                    System.out.println("Exclusao cancelada.");
                }
            } else {
                System.out.println("Escolha invalida!");
            }
        }
    }

    private static void adicionarPartida() {
        System.out.println("Digite o nome do time da casa:");
        String nomeCasa = scanner.nextLine();
        List<Time> timesCasa = timeService.buscarTimesPorNome(nomeCasa);
        if (timesCasa.isEmpty()) {
            System.out.println("Nenhum time encontrado com esse nome.");
            return;
        }
        System.out.println("Escolha o time da casa:");
        for (int i = 0; i < timesCasa.size(); i++) {
            System.out.println((i + 1) + " - " + timesCasa.get(i).getNome());
        }
        int escolhaCasa = scanner.nextInt();
        scanner.nextLine(); 
        if (escolhaCasa <= 0 || escolhaCasa > timesCasa.size()) {
            System.out.println("Escolha invalida!");
            return;
        }
        Time timeCasa = timesCasa.get(escolhaCasa - 1);

        System.out.println("Digite o nome do time visitante:");
        String nomeVisitante = scanner.nextLine();
        List<Time> timesVisitante = timeService.buscarTimesPorNome(nomeVisitante);
        if (timesVisitante.isEmpty()) {
            System.out.println("Nenhum time encontrado com esse nome.");
            return;
        }
        System.out.println("Escolha o time visitante:");
        for (int i = 0; i < timesVisitante.size(); i++) {
            System.out.println((i + 1) + " - " + timesVisitante.get(i).getNome());
        }
        int escolhaVisitante = scanner.nextInt();
        scanner.nextLine(); 
        if (escolhaVisitante <= 0 || escolhaVisitante > timesVisitante.size()) {
            System.out.println("Escolha invalida!");
            return;
        }
        Time timeVisitante = timesVisitante.get(escolhaVisitante - 1);

        System.out.println("Digite os gols do time da casa:");
        int golsCasa = scanner.nextInt();
        scanner.nextLine(); 
        System.out.println("Digite os gols do time visitante:");
        int golsVisitante = scanner.nextInt();
        scanner.nextLine(); 

        Time vencedor = null;
        if (golsCasa > golsVisitante) {
            vencedor = timeCasa;
        } else if (golsVisitante > golsCasa) {
            vencedor = timeVisitante;
        }
        
        boolean empate = false;
        if(vencedor == null){
            empate = true;
        }

        
        List<Jogador> jogadoresGols = new ArrayList<>();

        
        for (int i = 0; i < golsCasa + golsVisitante; i++) {
            System.out.println("Digite o nome do jogador que marcou o gol " + (i + 1) + ":");
            String nomeJogador = scanner.nextLine();

            if(!nomeJogador.equalsIgnoreCase("contra")){
                List<Jogador> jogadoresEncontrados = new ArrayList<>();
                jogadoresEncontrados.addAll(jogadorService.buscarJogadorPorNome(nomeJogador).stream()
                        .filter(j -> j.getTime().equals(timeCasa) || j.getTime().equals(timeVisitante))
                        .collect(Collectors.toList()));

                if (jogadoresEncontrados.isEmpty()) {
                    System.out.println("Nenhum jogador encontrado com esse nome nos times envolvidos.");
                    i--; 
                } else {
                    System.out.println("Escolha o jogador:");
                    for (int j = 0; j < jogadoresEncontrados.size(); j++) {
                        System.out.println((j + 1) + " - " + jogadoresEncontrados.get(j).getNome() + " (" + jogadoresEncontrados.get(j).getTime().getNome() + ")");
                    }
                    int escolha = scanner.nextInt();
                    scanner.nextLine();

                    if (escolha > 0 && escolha <= jogadoresEncontrados.size()) {
                        Jogador jogador = jogadoresEncontrados.get(escolha - 1);
                        jogadoresGols.add(jogador);


                        jogador.setGols(jogador.getGols() + 1);
                        jogadorService.adicionarGolJogador(jogador);
                        System.out.println("Gol adicionado para " + jogador.getNome() + "!");
                    } else {
                        System.out.println("Escolha invalida!");
                        i--;
                    }
                }
            }
        }

        
        List<Jogador> jogadoresEmCampo = new ArrayList<>();

        

        
        timeService.atualizarEstatisticasTime(timeCasa, golsCasa, golsVisitante, vencedor == timeCasa, empate);

            
        timeService.atualizarEstatisticasTime(timeVisitante, golsVisitante, golsCasa, vencedor == timeVisitante, empate);
        
        partidaService.adicionarPartida(timeCasa, timeVisitante, golsCasa, golsVisitante, vencedor, jogadoresGols, jogadoresEmCampo);
        System.out.println("Partida adicionada com sucesso!");
        colocacaoService.atualizarClassificacao();
    }

    private static void atualizarPartida() {
        System.out.println("Digite o ID da partida:");
        Long id = scanner.nextLong();
        scanner.nextLine();
        Partida partida = partidaService.buscarPartidaPorId(id);
        if (partida != null) {
            Time timeCasa = partida.getTimeCasa();
            Time timeVisitante = partida.getTimeVisitante();
            int golsCasaAntigos = partida.getGolsCasa();
            int golsVisitanteAntigos = partida.getGolsVisitante();
            boolean empateAntigo = (golsCasaAntigos == golsVisitanteAntigos);
            boolean vitoriaCasaAntiga = (golsCasaAntigos > golsVisitanteAntigos);
            boolean vitoriaVisitanteAntiga = (golsVisitanteAntigos > golsCasaAntigos);

            
            timeService.reverterEstatisticasTime(timeCasa, golsCasaAntigos, golsVisitanteAntigos, vitoriaCasaAntiga, empateAntigo);
            timeService.reverterEstatisticasTime(timeVisitante, golsVisitanteAntigos, golsCasaAntigos, vitoriaVisitanteAntiga, empateAntigo);

            System.out.println("Digite os novos gols do time da casa:");
            int golsCasa = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Digite os novos gols do time visitante:");
            int golsVisitante = scanner.nextInt();
            scanner.nextLine();

            Time vencedor = null;
            boolean empate = (golsCasa == golsVisitante);

            if (golsCasa > golsVisitante) {
                vencedor = timeCasa;
            } else if (golsVisitante > golsCasa) {
                vencedor = timeVisitante;
            }

            
            timeService.atualizarEstatisticasTime(timeCasa, golsCasa, golsVisitante, vencedor == timeCasa, empate);
            timeService.atualizarEstatisticasTime(timeVisitante, golsVisitante, golsCasa, vencedor == timeVisitante, empate);

            partidaService.atualizarPartida(id, golsCasa, golsVisitante, vencedor);
            System.out.println("Partida atualizada com sucesso!");
            colocacaoService.atualizarClassificacao();
        } else {
            System.out.println("Partida nao encontrada.");
        }
    }

    private static void areaExpulsao() {
        boolean continua = true;
        while (continua) {
            System.out.println("Escolha uma opcao:");
            System.out.println("1 - Adicionar Expulsao");
            System.out.println("2 - Remover Expulsao");
            System.out.println("3 - Ver Todas as Expulsões");
            System.out.println("4 - Ver Expulsões de um Jogador");
            System.out.println("0 - Voltar");
            int escolha = scanner.nextInt();
            scanner.nextLine(); 

            switch (escolha) {
                case 1:
                    adicionarExpulsao();
                    break;
                case 2:
                    removerExpulsao();
                    break;
                case 3:
                    verTodasExpulsoes();
                    break;
                case 4:
                    verExpulsoesJogador();
                    break;
                case 0:
                    continua = false;
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        }
    }

    private static void adicionarExpulsao() {
        System.out.println("Digite o nome do time:");
        String nomeTime = scanner.nextLine();
        List<Time> times = timeService.buscarTimesPorNomeAtivo(nomeTime);
        if (times.isEmpty()) {
            System.out.println("Nenhum time encontrado com esse nome.");
            return;
        }
        System.out.println("Escolha o time:");
        for (int i = 0; i < times.size(); i++) {
            System.out.println((i + 1) + " - " + times.get(i).getNome());
        }
        int escolhaTime = scanner.nextInt();
        scanner.nextLine(); 
        if (escolhaTime <= 0 || escolhaTime > times.size()) {
            System.out.println("Escolha invalida!");
            return;
        }
        Time time = times.get(escolhaTime - 1);
        
        System.out.println("Digite o nome do jogador:");
        String nomeJogador = scanner.nextLine();
        List<Jogador> jogadores = jogadorService.buscarJogadorPorNomeAtivo(nomeJogador);
        if (jogadores.isEmpty()) {
            System.out.println("Nenhum jogador encontrado com esse nome.");
            return;
        }
        System.out.println("Escolha o jogador:");
        for (int i = 0; i < jogadores.size(); i++) {
            System.out.println((i + 1) + " - " + jogadores.get(i).getNome());
        }
        int escolhaJogador = scanner.nextInt();
        scanner.nextLine(); 
        if (escolhaJogador <= 0 || escolhaJogador > jogadores.size()) {
            System.out.println("Escolha invalida!");
            return;
        }
        Jogador jogador = jogadores.get(escolhaJogador - 1);

        System.out.println("Digite o ID da partida:");
        Long partidaId = scanner.nextLong();
        scanner.nextLine(); 
        Partida partida = partidaService.buscarPartidaPorId(partidaId);
        if (partida == null) {
            System.out.println("Partida nao encontrada.");
            return;
        }

        System.out.println("Digite o número de jogos de suspensao:");
        int jogosSuspensao = scanner.nextInt();
        scanner.nextLine(); 

        expulsaoService.adicionarExpulsao(jogador, time, partida, jogosSuspensao);
        System.out.println("Expulsao adicionada com sucesso!");
    }

    private static void removerExpulsao() {
        System.out.println("Digite o ID da expulsao:");
        Long id = scanner.nextLong();
        scanner.nextLine(); 
        Expulsao expulsao = expulsaoService.buscarExpulsaoPorId(id);
        if (expulsao != null) {
            System.out.println("Confirmar remocao? (s/n)");
            String confirma = scanner.nextLine();
            if (confirma.equalsIgnoreCase("s")) {
                expulsaoService.removerExpulsao(id);
                System.out.println("Expulsao removida com sucesso!");
            } else {
                System.out.println("Remocao cancelada.");
            }
        } else {
            System.out.println("Expulsao nao encontrada.");
        }
    }

    private static void verTodasExpulsoes() {
        List<Expulsao> expulsoes = expulsaoService.listarTodasExpulsoes();
        if (expulsoes.isEmpty()) {
            System.out.println("Nenhuma expulsao encontrada.");
        } else {
            for (Expulsao expulsao : expulsoes) {
                System.out.println("Expulsao ID: " + expulsao.getId());
                System.out.println("Jogador: " + expulsao.getJogador().getNome());
                System.out.println("Time: " + expulsao.getTime().getNome());
                System.out.println("Partida: " + expulsao.getPartida().getId());
                System.out.println("Jogos de Suspensao: " + expulsao.getJogosSuspensao());
                System.out.println("-------------------------");
            }
        }
    }

    private static void verExpulsoesJogador() {
        System.out.println("Digite o nome do jogador:");
        String nome = scanner.nextLine();
        List<Jogador> jogadores = jogadorService.buscarJogadorPorNome(nome);
        if (jogadores.isEmpty()) {
            System.out.println("Nenhum jogador encontrado com esse nome.");
        } else {
            System.out.println("Escolha o jogador:");
            for (int i = 0; i < jogadores.size(); i++) {
                System.out.println((i + 1) + " - " + jogadores.get(i).getNome());
            }
            int escolha = scanner.nextInt();
            scanner.nextLine(); 
            if (escolha > 0 && escolha <= jogadores.size()) {
                Jogador jogador = jogadores.get(escolha - 1);
                List<Expulsao> expulsoes = expulsaoService.buscarExpulsoesPorJogador(jogador.getId());
                if (expulsoes.isEmpty()) {
                    System.out.println("Nenhuma expulsao encontrada para este jogador.");
                } else {
                    for (Expulsao expulsao : expulsoes) {
                        System.out.println("Expulsao ID: " + expulsao.getId());
                        System.out.println("Jogador: " + expulsao.getJogador().getNome());
                        System.out.println("Time: " + expulsao.getTime().getNome());
                        System.out.println("Partida: " + expulsao.getPartida().getId());
                        System.out.println("Jogos de Suspensao: " + expulsao.getJogosSuspensao());
                        System.out.println("-------------------------");
                    }
                }
            } else {
                System.out.println("Escolha invalida!");
            }
        }
    }
}
