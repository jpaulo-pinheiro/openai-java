package br.com.travessoaoavesso.ia;

import static br.com.travessoaoavesso.ia.IntegracaoOpenAI.enviarRequisicao;
import static br.com.travessoaoavesso.util.CarregadorDeDados.carregarClientesDoArquivo;
import static br.com.travessoaoavesso.util.ContadorDeTokens.contarTokens;
import static br.com.travessoaoavesso.util.ContadorDeTokens.mostrarQtdTokens;

public class IdentificadorDePerfil {

    public static void main(String[] args) throws InterruptedException {
        var promptSistema = """
                Identifique o perfil de compra de cada cliente.

                A resposta deve ser:

                Cliente - descreva o perfil do cliente em três palavras
                """;

        var clientes = carregarClientesDoArquivo();

        var quantidadeTokens = contarTokens(clientes);
        mostrarQtdTokens(clientes);

        var tamanhoRespostaEsperada = 2048;
        var modelo = "gpt-3.5-turbo";
        if (quantidadeTokens > 4096 - tamanhoRespostaEsperada) {
            modelo = "gpt-3.5-turbo-16k";
        }

        System.out.println("Modelo escolhido: " + modelo);

        System.out.println(enviarRequisicao(clientes, promptSistema));
    }

}
