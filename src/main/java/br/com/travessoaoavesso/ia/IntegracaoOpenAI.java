package br.com.travessoaoavesso.ia;

import java.time.Duration;
import java.util.Arrays;

import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

public class IntegracaoOpenAI {

    public static String enviarRequisicao(String user, String system) throws InterruptedException {
        var chave = System.getenv("OPENAI_API_KEY");
        var service = new OpenAiService(chave, Duration.ofSeconds(60));

        var completionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo-0125")
                .messages(Arrays.asList(new ChatMessage(ChatMessageRole.USER.value(), user),
                        new ChatMessage(ChatMessageRole.SYSTEM.value(), system)))
                // .n(5) // numero de respostas
                .build();

        return enviarRequisicao(service, completionRequest);
    }

    private static String enviarRequisicao(OpenAiService service, ChatCompletionRequest completionRequest)
            throws InterruptedException {
        var tentativas = 0;
        var segundosParaNovaTentativa = 5;
        while (tentativas++ < 5) {
            try {
                return service.createChatCompletion(completionRequest)
                        .getChoices().get(0).getMessage().getContent();
            } catch (OpenAiHttpException ex) {
                var errorCode = ex.statusCode;
                switch (errorCode) {
                    case 401 -> throw new RuntimeException("Erro com a chave da API.", ex);
                    case 429 -> {
                        System.out.println("Rate Limit atingido!Nova tentativa em instantes.");
                        Thread.sleep(1000 * segundosParaNovaTentativa);  
                        segundosParaNovaTentativa *= 2;                      
                    }
                    case 500, 503 -> {
                        System.out.println("API fora do ar. Nova tentativa em instantes.");
                        Thread.sleep(1000 * segundosParaNovaTentativa);
                        segundosParaNovaTentativa *= 2;
                    }
                }
            }
        }
        throw new RuntimeException("API fora do ar. Tentativas finalizadas sem sucesso.");
    }

}
