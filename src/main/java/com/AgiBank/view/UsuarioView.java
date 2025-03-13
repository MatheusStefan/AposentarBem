package com.AgiBank.view;

import com.AgiBank.model.Usuario;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UsuarioView {

    private final Scanner scanner = new Scanner(System.in);

    public Usuario coletarDadosUsuario() {
        String nome = coletarNome();
        String dataNascimento = coletarDataNascimento();
        String genero = coletarGenero();
        String profissao = coletarProfissao();
        int idade = calcularIdade(dataNascimento);
        int idadeAposentadoriaDesejada = coletarIdadeAposentadoriaDesejada(genero);

        return new Usuario(nome, dataNascimento, genero, profissao, idadeAposentadoriaDesejada);
    }

    private String coletarNome() {
        while (true) {
            System.out.print("Digite seu nome: ");
            String nome = scanner.nextLine().trim();

            if (nome.isEmpty()) {
                System.out.println("O nome não pode estar vazio. Tente novamente.");
            } else if (!nome.matches("[a-zA-ZÀ-ÿ\\s]+")) {
                System.out.println("O nome não pode conter números ou caracteres especiais inválidos. Tente novamente.");
            } else {
                return nome;
            }
        }
    }

    private String coletarDataNascimento() {
        LocalDate dataAniversario = null;
        LocalDate hoje = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (dataAniversario == null) {
            System.out.print("Digite sua data de nascimento (DD/MM/AAAA): ");
            String dataNascimento = scanner.nextLine();
            try {
                dataAniversario = LocalDate.parse(dataNascimento, formatter);

                if (dataAniversario.isAfter(hoje)) {
                    System.out.println("Data de nascimento não pode ser maior que a data de hoje.");
                    dataAniversario = null;
                } else if (calcularIdade(dataNascimento) < 15) {
                    System.out.println("Você precisa ter pelo menos 15 anos.");
                    dataAniversario = null;
                } else {
                    return dataNascimento;
                }
            } catch (Exception e) {
                System.out.println("Data inválida. Por favor, use o formato DD/MM/AAAA.");
            }
        }
        return null;
    }

    private int calcularIdade(String dataNascimento) {
        LocalDate dataNasc = LocalDate.parse(dataNascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return Period.between(dataNasc, LocalDate.now()).getYears();
    }

    private String coletarGenero() {
        while (true) {
            System.out.print("Qual seu gênero? (Masculino/Feminino): ");
            String genero = scanner.nextLine().trim();
            if (genero.equalsIgnoreCase("Masculino") || genero.equalsIgnoreCase("Feminino")) {
                return genero.substring(0, 1).toUpperCase() + genero.substring(1).toLowerCase();
            } else {
                System.out.println("Opção inválida. Digite novamente (Masculino ou Feminino).");
            }
        }
    }

    private String coletarProfissao() {
        String[] opcoesValidas = {"Geral", "Professor", "Rural"};

        while (true) {
            System.out.print("Qual sua profissão? (Geral, Professor, Rural): ");
            String profissao = scanner.nextLine().trim();
            profissao = profissao.substring(0, 1).toUpperCase() + profissao.substring(1).toLowerCase();

            for (String opcao : opcoesValidas) {
                if (profissao.equals(opcao)) {
                    return profissao;
                }
            }
            System.out.println("Profissão inválida. Escolha entre: Geral, Professor ou Rural.");
        }
    }

    private int coletarIdadeAposentadoriaDesejada(String genero) {
        int idadeMinima = genero.equalsIgnoreCase("Masculino") ? 65 : 62;

        while (true) {
            try {
                System.out.printf("A idade mínima para sua aposentadoria é de %d anos. Digite a idade desejada: ", idadeMinima);
                int idadeAposentadoria = scanner.nextInt();

                if (idadeAposentadoria >= idadeMinima && idadeAposentadoria < 90) {
                    return idadeAposentadoria;
                } else if (idadeAposentadoria >= 90) {
                    System.out.println("Idade muito alta, tente um valor menor que 90 anos.");
                } else {
                    System.out.printf("A idade mínima para aposentadoria é de %d anos. Tente novamente.\n", idadeMinima);
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite apenas números inteiros.");
                scanner.next();
            }
        }
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }
}
