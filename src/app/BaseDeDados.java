package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BaseDeDados {

    private final List<String> palavrasOriginais;
    private List<String> palavras;
    private final Random geradorAleatorio;

    public BaseDeDados(String caminhoArquivo) throws IOException {
        this.palavrasOriginais = new ArrayList<>();
        this.geradorAleatorio = new Random();
        carregarArquivo(caminhoArquivo);
        reiniciarBase();
    }

    private void carregarArquivo(String caminhoArquivo) throws IOException {
        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                palavrasOriginais.add(linha);
            }
        }
    }

    public synchronized void reiniciarBase() {
        this.palavras = new ArrayList<>(palavrasOriginais);
    }

    public void acessosAleatoriosLeitor() {
        int tamanho = palavras.size();
        for (int i = 0; i < 100; i++) {
            int indice = geradorAleatorio.nextInt(tamanho);
            String palavra = palavras.get(indice);
            if (palavra == null) {
                continue;
            }
        }
    }

    public void acessosAleatoriosEscritor() {
        int tamanho = palavras.size();
        for (int i = 0; i < 100; i++) {
            int indice = geradorAleatorio.nextInt(tamanho);
            palavras.set(indice, "MODIFICADO");
        }
    }
}


