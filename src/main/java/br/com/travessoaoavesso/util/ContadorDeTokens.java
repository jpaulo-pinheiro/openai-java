package br.com.travessoaoavesso.util;

import java.math.BigDecimal;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.ModelType;

public class ContadorDeTokens {

    public static void main(String[] args) {

        var clientes = OperacoesComArquivos.carregarClientesDoArquivo();
        var qtd = contarTokens(clientes);

        System.out.println("Quantidade de tokens: " + qtd);

        var custo = new BigDecimal(qtd).divide(new BigDecimal(1000)).multiply(new BigDecimal(0.0010));
        System.out.println("Custo aproximado da requisição: R$" + custo);

    }

    public static int contarTokens(String prompt) {
        var registry = Encodings.newDefaultEncodingRegistry();
        var enc = registry.getEncodingForModel(ModelType.GPT_4);
        return enc.countTokens(prompt);
    }

    public static void mostrarQtdTokens(String prompt){
        System.out.println("QTD tokens: " + contarTokens(prompt));
    }

}
