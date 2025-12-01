package app;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MainExperimento {

    private static final int TOTAL_THREADS = 100;
    private static final int TOTAL_PROPORCOES = 101; 
    private static final int TOTAL_REPETICOES = 50;

    public static void main(String[] args) {
        try {
            BaseDeDados baseDeDados = new BaseDeDados("bd.txt");
            executarExperimentos(baseDeDados);
        } catch (IOException e) {
            System.err.println("Erro de E/S: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Execucao interrompida: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private static void executarExperimentos(BaseDeDados baseDeDados) throws InterruptedException, IOException {
        try (BufferedWriter escritorCsv = new BufferedWriter(new FileWriter("resultados_medias.csv"))) {
            escritorCsv.write("implementacao,leitores,escritores,mediaMs");
            escritorCsv.newLine();

            for (int tipoImplementacao = 1; tipoImplementacao <= 2; tipoImplementacao++) {
                long inicioGlobal = System.currentTimeMillis();
                String descricaoImplementacao = tipoImplementacao == 1
                        ? "Implementacao 1 (Readers/Writers)"
                        : "Implementacao 2 (Exclusivo Simples)";

                System.out.println(descricaoImplementacao);

                for (int quantidadeLeitores = 0; quantidadeLeitores <= TOTAL_THREADS; quantidadeLeitores++) {
                    int quantidadeEscritores = TOTAL_THREADS - quantidadeLeitores;
                    long somaTempos = 0L;

                    for (int repeticao = 0; repeticao < TOTAL_REPETICOES; repeticao++) {
                        baseDeDados.reiniciarBase();

                        ControladorAcesso controlador;
                        if (tipoImplementacao == 1) {
                            controlador = new ControladorLeitoresEscritores();
                        } else {
                            controlador = new ControleExclusivoSimples();
                        }

                        Thread[] threads = criarThreads(quantidadeLeitores, quantidadeEscritores, baseDeDados, controlador);

                        long inicio = System.currentTimeMillis();
                        iniciarThreads(threads);
                        aguardarThreads(threads);
                        long fim = System.currentTimeMillis();

                        somaTempos += (fim - inicio);
                    }

                    long media = somaTempos / TOTAL_REPETICOES;
                    System.out.println(
                            descricaoImplementacao +
                                    " - Leitores: " + quantidadeLeitores +
                                    ", Escritores: " + quantidadeEscritores +
                                    ", mediaMs: " + media
                    );

                    String linhaCsv = (tipoImplementacao == 1 ? "ReadersWriters" : "ExclusivoSimples")
                            + "," + quantidadeLeitores
                            + "," + quantidadeEscritores
                            + "," + media;
                    escritorCsv.write(linhaCsv);
                    escritorCsv.newLine();
                }

                long fimGlobal = System.currentTimeMillis();
                long duracaoMinutos = (fimGlobal - inicioGlobal) / 60000;
                System.out.println(descricaoImplementacao + " - tempo total: " + duracaoMinutos + " min");
            }
        }
    }

    private static Thread[] criarThreads(int quantidadeLeitores,
                                         int quantidadeEscritores,
                                         BaseDeDados baseDeDados,
                                         ControladorAcesso controlador) {
        Thread[] threads = new Thread[TOTAL_THREADS];
        GeradorAleatorio geradorAleatorio = new GeradorAleatorio(TOTAL_THREADS);

        for (int i = 0; i < quantidadeLeitores; i++) {
            int indice = geradorAleatorio.proximoIndiceThread();
            threads[indice] = new Thread(new Leitor(baseDeDados, controlador));
        }

        for (int i = 0; i < quantidadeEscritores; i++) {
            int indice = geradorAleatorio.proximoIndiceThread();
            threads[indice] = new Thread(new Escritor(baseDeDados, controlador));
        }

        return threads;
    }

    private static void iniciarThreads(Thread[] threads) {
        for (Thread thread : threads) {
            if (thread != null) {
                thread.start();
            }
        }
    }

    private static void aguardarThreads(Thread[] threads) throws InterruptedException {
        for (Thread thread : threads) {
            if (thread != null) {
                thread.join();
            }
        }
    }
}



