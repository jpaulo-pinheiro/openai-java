package br.com.travessoaoavesso;

import java.util.Arrays;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

public class TestaIntegracao {

        public static void main(String[] args) {

                System.out.println("Integração Java x OpenAI\n");

                var user = "gere 5 produtos";
                var system = "voce e um gerador de produtos ficticios para um ecommerce e deve gerar apenas os nomes dos produtos solicitados";

                var chave = System.getenv("OPENAI_API_KEY");
                OpenAiService service = new OpenAiService(chave);
                ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                                .model("gpt-4")
                                .messages(Arrays.asList(new ChatMessage(ChatMessageRole.USER.value(), user),
                                                new ChatMessage(ChatMessageRole.SYSTEM.value(), system)))
                                // .n(5) // numero de respostas
                                .build();

                service.createChatCompletion(completionRequest).getChoices()
                                .forEach(c -> System.out.println(c.getMessage().getContent()));

        }
}
