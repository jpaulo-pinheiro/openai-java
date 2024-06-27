package br.com.travessoaoavesso.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class CarregadorDeDados {

    public static String carregarClientesDoArquivo() {
        try {
            var path = Path.of(ClassLoader
                    .getSystemResource("lista_de_compras_100_clientes.csv")
                    .toURI());
            return Files.readAllLines(path).toString();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar o arquivo de compras dos clientes!", e);
        }
    }

    public static String carregarAvaliacoesDoArquivo(Path arquivo) {
        try {
            return Files.readAllLines(arquivo).toString();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar o arquivo de avaliações!", e);
        }
    }

    public static String lerConteudoDoArquivo(Path arquivo) {
        try {
            return Files.readAllLines(arquivo).toString();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Erro ao ler o conteúdo do arquivo %s!", arquivo.getFileName()),
                    e);
        }
    }

    public static List<Path> carregarArquivosDeAvaliacoes() {
        var diretorioAvaliacoes = Path.of("src/main/resources/avaliacoes");
        List<Path> arquivosDeAvaliacoes = null;
        try {
            arquivosDeAvaliacoes = Files.walk(diretorioAvaliacoes, 1)
                    .filter(path -> path.toString().endsWith(".txt"))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar o arquivo de avaliações!", e);
        }
        return arquivosDeAvaliacoes;
    }

}
