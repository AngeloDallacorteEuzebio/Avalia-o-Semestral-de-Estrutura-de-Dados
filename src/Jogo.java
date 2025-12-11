import java.util.*;

public class Jogo {
    private Tabuleiro tabuleiro;
    private Baralho baralho;
    private List<Propriedade> cadastroPropriedades;
    private List<Jogador> jogadores;
    private Scanner sc;
    private int saldoInicial = 30000;
    private int salarioVolta = 2000;
    private int maxRodadas = 20;

    public Jogo() {
        sc = new Scanner(System.in);
        cadastroPropriedades = new ArrayList<>();
        jogadores = new ArrayList<>();
        tabuleiro = new Tabuleiro();
        baralho = new Baralho();
    }

    public void executar() {
        seedPadrao();
        while (true) {
            mostrarMenuPrincipal();
            String opt = sc.nextLine().trim();
            switch (opt) {
                case "1": gerenciarJogadores(); break;
                case "2": gerenciarPropriedades(); break;
                case "3": configuracoes(); break;
                case "4": if (validarInicio()) iniciarPartida(); break;
                case "0": System.out.println("Fechando..."); return;
                default: System.out.println("Opção inválida.");
            }
        }
    }

    private void seedPadrao() {
        cadastroPropriedades.add(new Propriedade("Rua A", 1000, 100));
        cadastroPropriedades.add(new Propriedade("Avenida B", 2000, 200));
        cadastroPropriedades.add(new Propriedade("Praça C", 3000, 300));
        cadastroPropriedades.add(new Propriedade("Alameda D", 4000, 400));
        // outras serão reutilizadas ao montar o tabuleiro quando iniciar
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n= SIMULADOR DE TABULEIRO =");
        System.out.println("1. Gerenciar Jogadores");
        System.out.println("2. Gerenciar Propriedades");
        System.out.println("3. Configurações");
        System.out.println("4. Iniciar Partida");
        System.out.println("0. Sair");
        System.out.print("Escolha: ");
    }

    private void gerenciarJogadores() {
        while (true) {
            System.out.println("\n- Gerenciar Jogadores -");
            System.out.println("1. Adicionar Jogador");
            System.out.println("2. Listar Jogadores");
            System.out.println("3. Remover Jogador");
            System.out.println("4. Voltar");
            System.out.print("Escolha: ");
            String o = sc.nextLine().trim();
            if (o.equals("1")) {
                if (jogadores.size() >= 6) {
                    System.out.println("Máximo de 6 jogadores.");
                    continue;
                }
                System.out.print("Nome do jogador: ");
                String nome = sc.nextLine().trim();
                jogadores.add(new Jogador(nome, saldoInicial));
                System.out.println("Jogador '" + nome + "' incluído.");
            } else if (o.equals("2")) {
                if (jogadores.isEmpty()) System.out.println("Não existem jogadores cadastrados.");
                else {
                    int i = 1;
                    for (Jogador j : jogadores) {
                        System.out.printf("%d. %s - Saldo: R$ %.2f\n", i++, j.getNome(), j.getSaldo());
                    }
                }
            } else if (o.equals("3")) {
                System.out.print("Nome a remover: ");
                String n = sc.nextLine().trim();
                jogadores.removeIf(j -> j.getNome().equalsIgnoreCase(n));
                System.out.println("Removido.");
            } else if (o.equals("4")) break;
            else System.out.println("Opção inválida.");
        }
    }

    private void gerenciarPropriedades() {
        while (true) {
            System.out.println("\n- Gerenciar Propriedades -");
            System.out.println("1. Cadastrar Propriedade");
            System.out.println("2. Listar Propriedades");
            System.out.println("3. Remover Propriedade");
            System.out.println("4. Voltar");
            System.out.print("Escolha: ");
            String o = sc.nextLine().trim();
            if (o.equals("1")) {
                System.out.print("Nome: ");
                String nome = sc.nextLine().trim();
                System.out.print("Preço de compra: ");
                double preco = Double.parseDouble(sc.nextLine().trim());
                System.out.print("Aluguel: ");
                double aluguel = Double.parseDouble(sc.nextLine().trim());
                cadastroPropriedades.add(new Propriedade(nome, preco, aluguel));
                System.out.println("Propriedade cadastrada.");
            } else if (o.equals("2")) {
                if (cadastroPropriedades.isEmpty()) System.out.println("Nenhuma propriedade cadastrada.");
                else {
                    int i = 1;
                    for (Propriedade p : cadastroPropriedades) {
                        System.out.printf("%d. %s - Preço: R$ %.2f - Aluguel: R$ %.2f\n", i++, p.getNome(), p.getPrecoCompra(), p.getAluguel());
                    }
                }
            } else if (o.equals("3")) {
                System.out.print("Nome a remover: ");
                String n = sc.nextLine().trim();
                cadastroPropriedades.removeIf(p -> p.getNome().equalsIgnoreCase(n));
                System.out.println("Removido (se encontrado).");
            } else if (o.equals("4")) break;
            else System.out.println("Opção inválida.");
        }
    }

    private void configuracoes() {
        while (true) {
            System.out.println("\n--- Configurações ---");
            System.out.println("1. Saldo inicial (Atual: R$ " + saldoInicial + ")");
            System.out.println("2. Salário por volta (Atual: R$ " + salarioVolta + ")");
            System.out.println("3. Máx de rodadas (Atual: " + maxRodadas + ")");
            System.out.println("4. Voltar");
            System.out.print("Escolha: ");
            String o = sc.nextLine().trim();
            if (o.equals("1")) {
                System.out.print("Novo saldo inicial: ");
                saldoInicial = Integer.parseInt(sc.nextLine().trim());
            } else if (o.equals("2")) {
                System.out.print("Novo salário por volta: ");
                salarioVolta = Integer.parseInt(sc.nextLine().trim());
            } else if (o.equals("3")) {
                System.out.print("Novo máximo de rodadas: ");
                maxRodadas = Integer.parseInt(sc.nextLine().trim());
            } else if (o.equals("4")) break;
            else System.out.println("Opção inválida.");
        }
    }

    private boolean validarInicio() {
        if (cadastroPropriedades.size() < 6) {
            System.out.println("Necessário ao menos 6 propriedades para iniciar: " + cadastroPropriedades.size() + ").");
            return false;
        }
        if (jogadores.size() < 2) {
            System.out.println("Necessário ao menos 2 jogadores para iniciar: " + jogadores.size() + ").");
            return false;
        }
        return true;
    }

    private void iniciarPartida() {
        tabuleiro.reset();
        tabuleiro.construirPadrao(cadastroPropriedades);
        baralho.iniciarBaralhoPadrao();
        for (Jogador j : jogadores) {
            j.prepararParaJogo(tabuleiro.getCasaInicial(), saldoInicial);
        }

        int rodada = 1;
        while (rodada <= maxRodadas && jogadoresAtivos() > 1) {
            System.out.println("\n= RODADA " + rodada + " =");
            for (Jogador j : jogadores) {
                if (j.isFalido()) continue;
                System.out.println("\n-- Vez de: " + j.getNome() + " --");
                realizarTurno(j);
                if (jogadoresAtivos() <= 1) break;
            }
            rodada++;
        }
        finalizarPartida();
    }

    private int jogadoresAtivos() {
        int c = 0;
        for (Jogador j : jogadores) if (!j.isFalido()) c++;
        return c;
    }

    private void realizarTurno(Jogador j) {
        if (j.estaPreso()) {
            System.out.println("Você está preso (turnos: " + j.getTurnosPrisao() + "/3).");
            System.out.println("1. Tentar sair (dados duplos)");
            System.out.println("2. Pagar fiança (R$ 500)");
            System.out.println("3. Passar a vez");
            System.out.print("Escolha: ");
            String o = sc.nextLine().trim();
            if (o.equals("1")) {
                int d1 = Dados.rolar(), d2 = Dados.rolar();
                System.out.println("Dados: " + d1 + " e " + d2);
                if (d1 == d2) {
                    j.sairPrisao();
                    moverJogador(j, d1 + d2);
                } else {
                    j.incrementarTurnoPrisao();
                    if (j.getTurnosPrisao() >= 3) {
                        j.sairPrisao();
                        System.out.println("Saiu da prisão após 3 turnos.");
                    }
                }
            } else if (o.equals("2")) {
                if (j.getSaldo() >= 500) {
                    j.creditar(-500);
                    j.sairPrisao();
                    System.out.println("Fiança paga. Você saiu da prisão.");
                    realizarTurno(j);
                } else {
                    System.out.println("Saldo insuficiente para pagar fiança.");
                }
            } else {
                j.incrementarTurnoPrisao();
            }
            return;
        }

        System.out.println("1. Lançar dados e mover");
        System.out.println("2. Ver status");
        System.out.println("3. Gerenciar propriedades");
        System.out.println("0. Desistir (falir)");
        System.out.print("Escolha: ");
        String o = sc.nextLine().trim();
        if (o.equals("1")) {
            int d1 = Dados.rolar(), d2 = Dados.rolar();
            System.out.println("Você tirou " + d1 + " e " + d2 + " (total " + (d1 + d2) + ").");
            moverJogador(j, d1 + d2);
        } else if (o.equals("2")) {
            j.exibirStatus();
        } else if (o.equals("3")) {
            gerenciarPropriedadesJogador(j);
        } else if (o.equals("0")) {
            j.falir();
            System.out.println("Você desistiu e foi considerado falido.");
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private void gerenciarPropriedadesJogador(Jogador j) {
        while (true) {
            System.out.println("\n- Propriedades de " + j.getNome() + " -");
            j.listarPropriedades();
            System.out.println("1. Hipotecar propriedade");
            System.out.println("2. Pagar hipoteca");
            System.out.println("3. Voltar");
            System.out.print("Escolha: ");
            String o = sc.nextLine().trim();
            if (o.equals("1")) {
                System.out.print("Nome da propriedade: ");
                String n = sc.nextLine().trim();
                Propriedade p = j.buscarPropriedadePorNome(n);
                if (p != null && !p.estaHipotecada()) {
                    j.hipotecar(p);
                } else System.out.println("Não pode hipotecar.");
            } else if (o.equals("2")) {
                System.out.print("Nome da propriedade: ");
                String n = sc.nextLine().trim();
                Propriedade p = j.buscarPropriedadePorNome(n);
                if (p != null && p.estaHipotecada()) {
                    j.pagarHipoteca(p);
                } else System.out.println("Operação inválida.");
            } else if (o.equals("3")) break;
            else System.out.println("Opção inválida.");
        }
    }

    private void moverJogador(Jogador j, int passos) {
        Casa destino = tabuleiro.avancarCasa(j.getPosicao(), passos);
        System.out.println("Você chegou em: " + destino.getNome());
        j.setPosicao(destino);
        if (destino.isInicio()) {
            j.creditar(salarioVolta);
            System.out.println("Você passou pela casa inicial e recebeu R$ " + salarioVolta);
        }
        if (destino instanceof CasaPropriedade) {
            CasaPropriedade cp = (CasaPropriedade) destino;
            if (cp.getPropriedade().getProprietario() == null) {
                System.out.printf("Propriedade disponivel: %s - Preco R$ %.2f\n", cp.getPropriedade().getNome(), cp.getPropriedade().getPrecoCompra());
                System.out.println("Deseja comprar? 1-Sim 2-Nao");
                String o = sc.nextLine().trim();
                if (o.equals("1")) {
                    if (j.getSaldo() >= cp.getPropriedade().getPrecoCompra()) {
                        j.comprar(cp.getPropriedade());
                        System.out.println("Propriedade comprada.");
                    } else {
                        System.out.println("Saldo insuficiente para comprar.");
                    }
                }
            } else if (!cp.getPropriedade().getProprietario().equals(j) && !cp.getPropriedade().estaHipotecada()) {
                double valorAluguel = cp.getPropriedade().getAluguel();
                System.out.printf("Propriedade de %s. Pagar aluguel R$ %.2f\n", cp.getPropriedade().getProprietario().getNome(), valorAluguel);
                j.creditar(-valorAluguel);
                cp.getPropriedade().getProprietario().creditar(valorAluguel);
                if (j.getSaldo() < 0) verificarFalencia(j);
            }
        } else {
            if (destino.getTipo().equals("IMPUESTO")) {
                double imposto = 500;
                System.out.println("Casa Imposto: pague R$ " + imposto);
                j.creditar(-imposto);
                if (j.getSaldo() < 0) verificarFalencia(j);
            } else if (destino.getTipo().equals("SORTE")) {
                Carta c = baralho.comprar();
                System.out.println("Carta sorte/azar: " + c.getTexto());
                aplicarCarta(j, c);
            } else if (destino.getTipo().equals("PRISAO")) {
                System.out.println("Você foi preso! Vai para a prisão.");
                j.irParaPrisao(tabuleiro.getCasaPrisao());
            }
        }
    }

    private void aplicarCarta(Jogador j, Carta c) {
        if (c.getTipo().equals("GANHAR")) {
            j.creditar(c.getValor());
            System.out.println("Você recebeu R$ " + c.getValor());
        } else if (c.getTipo().equals("PERDER")) {
            j.creditar(-c.getValor());
            System.out.println("Você pagou R$ " + c.getValor());
            if (j.getSaldo() < 0) verificarFalencia(j);
        } else if (c.getTipo().equals("IRPARA")) {
            Casa alvo = tabuleiro.buscarCasaPorNome(c.getTexto());
            if (alvo != null) {
                j.setPosicao(alvo);
                System.out.println("Movido para " + alvo.getNome());
            }
        }
    }

    private void verificarFalencia(Jogador j) {
        if (j.getSaldo() < 0) {
            System.out.println("Saldo negativo. Você está em risco de falência.");
            if (!j.tentarRecuperar()) {
                j.falir();
                System.out.println("Jogador " + j.getNome() + " foi declarado falido.");
            } else {
                System.out.println("Jogador recuperou saldo via hipotecas/vendas.");
            }
        }
    }

    private void finalizarPartida() {
        System.out.println("\nFIM DA PARTIDA");
        jogadores.sort(Comparator.comparingDouble(Jogador::getSaldo).reversed());
        for (Jogador j : jogadores) {
            System.out.printf("%s - Saldo: R$ %.2f - %s\n", j.getNome(), j.getSaldo(), j.isFalido() ? "FALIDO" : "ATIVO");
        }
        System.out.println("Obrigado por jogar!");
    }
}
