package app;

public class Escritor implements Runnable {

    private final BaseDeDados baseDeDados;
    private final ControladorAcesso controladorAcesso;

    public Escritor(BaseDeDados baseDeDados, ControladorAcesso controladorAcesso) {
        this.baseDeDados = baseDeDados;
        this.controladorAcesso = controladorAcesso;
    }

    @Override
    public void run() {
        controladorAcesso.entrarEscrita();
        try {
            executarEscrita();
        } finally {
            controladorAcesso.sairEscrita();
        }
    }

    private void executarEscrita() {
        baseDeDados.acessosAleatoriosEscritor();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


