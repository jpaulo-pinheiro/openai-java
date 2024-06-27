package br.com.travessoaoavesso.ia;

import static br.com.travessoaoavesso.ia.IntegracaoOpenAI.enviarRequisicao;

import java.util.Scanner;

public class CategorizacaoDeProdutos {

        public static void main(String[] args) throws InterruptedException {

                System.out.println("Categorizador de Produtos\n");

                var leitor = new Scanner(System.in);

                System.out.println("Digite as categorias válidas:");
                var categorias = leitor.nextLine();

                while (true) {

                        System.out.println("Digite o nome do produto:");
                        var user = leitor.nextLine();

                        var system = """
                                        Voce e um categorizador de produtos de um ecommerce e deve gerar apenas os nome da categoria do produto solicitados
                                        Escolha uma categoria dentro da lista abaixo:
                                        %s

                                        ##### Exemplo de uso:
                                        Pergunta: Bola de futebol
                                        Resposta: Esportes

                                        ##### Regras a sereme seguidas:
                                        Caso o usuario pergunte algo que não seja sobre categorização de produtos voce deve responder que não pode ajudar pois o seu papel e apenas responder as categorias do produtos
                                        """
                                        .formatted(categorias);

                        System.out.println(enviarRequisicao(user, system));
                }
        }

}