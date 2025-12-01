package app;

public class Leitor implements Runnable {

    private final BaseDeDados baseDeDados;
    private final ControladorAcesso controladorAcesso;

    public Leitor(BaseDeDados baseDeDados, ControladorAcesso controladorAcesso) {
        this.baseDeDados = baseDeDados;
        this.controladorAcesso = controladorAcesso;
    }

    @Override
    public void run() {
        controladorAcesso.entrarLeitura();
        try {
            executarLeitura();
        } finally {
            controladorAcesso.sairLeitura();
        }
    }

    private void executarLeitura() {
        baseDeDados.acessosAleatoriosLeitor();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


