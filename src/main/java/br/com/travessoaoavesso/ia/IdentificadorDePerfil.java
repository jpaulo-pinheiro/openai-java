package br.com.travessoaoavesso.ia;

import static br.com.travessoaoavesso.util.ContadorDeTokens.*;
import static br.com.travessoaoavesso.util.OperacoesComArquivos.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

public class IdentificadorDePerfil {

    public static void main(String[] args) {
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

        var request = ChatCompletionRequest
                .builder()
                .model(modelo)
                .messages(Arrays.asList(
                        new ChatMessage(
                                ChatMessageRole.SYSTEM.value(),
                                promptSistema),
                        new ChatMessage(
                                ChatMessageRole.USER.value(),
                                clientes)))
                .build();

        var chave = System.getenv("OPENAI_API_KEY");
        var service = new OpenAiService(chave, Duration.ofSeconds(30));

        System.out.println(
                service
                        .createChatCompletion(request)
                        .getChoices().get(0).getMessage().getContent());
    }

}
