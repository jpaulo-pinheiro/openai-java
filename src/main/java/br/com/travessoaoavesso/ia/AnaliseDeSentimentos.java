package br.com.travessoaoavesso.ia;

import static br.com.travessoaoavesso.ia.IntegracaoOpenAI.enviarRequisicao;
import static br.com.travessoaoavesso.util.CarregadorDeDados.carregarArquivosDeAvaliacoes;
import static br.com.travessoaoavesso.util.CarregadorDeDados.lerConteudoDoArquivo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class AnaliseDeSentimentos {

    public static void main(String[] args) throws InterruptedException {

        var arquivosDeAvaliacoes = carregarArquivosDeAvaliacoes();
        for (Path arquivo : arquivosDeAvaliacoes) {

            String produto = arquivo.getFileName().toString().replace("avaliacoes-", "").replace(".txt", "");

            var user = lerConteudoDoArquivo(arquivo);
            var system = """
                    Você é um analisador de sentimentos de avaliações de produtos.
                    Escreva um parágrafo com até 50 palavras resumindo as avaliações e depois atribua qual o sentimento geral para o produto.
                    Identifique também 3 pontos fortes e 3 pontos fracos de cada produto a partir das avaliações.

                    #### Formato de saída
                    Nome do produto:
                    Resumo das avaliações: [resuma em até 50 palavras]
                    Sentimento geral: [deve ser: POSITIVO, NEUTRO ou NEGATIVO]
                    Pontos fortes: [3 bullets points]
                    Pontos fracos: [3 bullets points]
                    """;

            System.out.println("Iniciando análise do produto " + produto);
            // var resposta = enviarRequisicao(arquivo);
            var resposta = enviarRequisicao(user, system);
            System.out.println("Análise do produto " + produto + " finalizada.");

            salvarArquivoDeAnaliseDeSentimento(arquivo.getFileName().toString().replace(".txt", ""), resposta);
        }

    }

    private static void salvarArquivoDeAnaliseDeSentimento(String arquivo, String analise) {
        System.out.println("Salvando análise do produto " + arquivo);
        try {
            var path = Path.of("src/main/resources/analises/analise-sentimentos-" + arquivo + ".txt");
            Files.writeString(path, analise, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar o arquivo de análise!", e);
        }
    }

}
