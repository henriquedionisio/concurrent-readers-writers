package app;

public class ControleExclusivoSimples implements ControladorAcesso {

    private boolean bloqueado = false;
    private Thread bloqueadoPor = null;
    private int contagemBloqueios = 0;

    private synchronized void bloquear() {
        Thread threadAtual = Thread.currentThread();
        while (bloqueado && bloqueadoPor != threadAtual) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        bloqueado = true;
        bloqueadoPor = threadAtual;
        contagemBloqueios++;
    }

    private synchronized void desbloquear() {
        if (Thread.currentThread() == bloqueadoPor) {
            contagemBloqueios--;
            if (contagemBloqueios == 0) {
                bloqueado = false;
                bloqueadoPor = null;
                notify();
            }
        }
    }

    @Override
    public void entrarLeitura() {
        bloquear();
    }

    @Override
    public void sairLeitura() {
        desbloquear();
    }

    @Override
    public void entrarEscrita() {
        bloquear();
    }

    @Override
    public void sairEscrita() {
        desbloquear();
    }
}


