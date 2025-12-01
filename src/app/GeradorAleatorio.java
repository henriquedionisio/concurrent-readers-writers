package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeradorAleatorio {

    private final List<Integer> indicesLivres;
    private final Random gerador;

    public GeradorAleatorio(int quantidadeIndices) {
        this.indicesLivres = new ArrayList<>();
        this.gerador = new Random();
        preencherIndices(quantidadeIndices);
    }

    private void preencherIndices(int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            indicesLivres.add(i);
        }
    }

    public int proximoIndiceThread() {
        if (indicesLivres.isEmpty()) {
            throw new IllegalStateException("Nao ha mais indices disponiveis para threads.");
        }
        int posicaoLista = gerador.nextInt(indicesLivres.size());
        return indicesLivres.remove(posicaoLista);
    }
}


