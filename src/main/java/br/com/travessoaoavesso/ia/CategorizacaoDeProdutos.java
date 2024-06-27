package br.com.travessoaoavesso.ia;

import java.util.Arrays;
import java.util.Scanner;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

public class CategorizacaoDeProdutos {

        public static void main(String[] args) {

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

                        dispararRequisicao(user, system);
                }
        }

        private static void dispararRequisicao(String user, String system) {
                var chave = System.getenv("OPENAI_API_KEY");
                var service = new OpenAiService(chave);
                var completionRequest = ChatCompletionRequest.builder()
                                .model("gpt-4")
                                .messages(Arrays.asList(new ChatMessage(ChatMessageRole.USER.value(), user),
                                                new ChatMessage(ChatMessageRole.SYSTEM.value(), system.toString())))
                                // .n(5) // numero de respostas
                                .build();

                service.createChatCompletion(completionRequest).getChoices()
                                .forEach(c -> System.out.println(c.getMessage().getContent()));
        }
}