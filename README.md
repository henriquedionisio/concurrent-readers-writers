## EP02 - Readers e Writers

Projeto em Java que implementa o experimento de leitores e escritores descrito em `ep02.pdf`.

### Como rodar

- No diretório raiz (`concurrent-readers-writers`), compile:

```bash
javac -d out $(find src -name "*.java")
```

- Garanta que `bd.txt` esteja na raiz do projeto (mesmo nível de `src/`).
- Execute:

```bash
java -cp out app.MainExperimento
```

O programa:

- Carrega `bd.txt` em memória como região crítica.
- Cria 100 threads por execução (leitores e escritores).
- Roda duas implementações (Readers/Writers e lock simples).
- Testa todas as proporções de leitores/escritores, com 50 repetições.
- Gera o arquivo `resultados_medias.csv` com os tempos médios e usa esses dados para o gráfico `grafico_comparacao.png`, que serve de base para o relatório em PDF.

