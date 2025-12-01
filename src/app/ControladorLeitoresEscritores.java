package app;

public class ControladorLeitoresEscritores implements ControladorAcesso {

    private int leitoresAtivos = 0;
    private boolean escritorPresente = false;

    private boolean podeLer() {
        return !escritorPresente;
    }

    private boolean podeEscrever() {
        return !escritorPresente && leitoresAtivos == 0;
    }

    @Override
    public synchronized void entrarLeitura() {
        while (!podeLer()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        leitoresAtivos++;
    }

    @Override
    public synchronized void sairLeitura() {
        leitoresAtivos--;
        notifyAll();
    }

    @Override
    public synchronized void entrarEscrita() {
        while (!podeEscrever()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        escritorPresente = true;
    }

    @Override
    public synchronized void sairEscrita() {
        escritorPresente = false;
        notifyAll();
    }
}


