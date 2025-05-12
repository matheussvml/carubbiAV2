/* *****************************************************************************
 *  Grupo:
 *  Alunos integrantes:
 *
 *  Descrição:  Implementa um cliente interativo que constrói um ciclo (Tour)
 *              usando a heurística do vizinho mais próximo (vermelho)
 *              ou a heurística do menor aumento (azul).
 *
 *              Pode ser chamado com ou sem um arquivo de entrada para iniciar:
 *
 *                  java TSPVisualizer data/tsp1000.txt
 *
 *              Comandos do teclado:
 *                  - n   alterna exibição do ciclo usando a heurística do vizinho mais próximo
 *                  - s   alterna exibição do ciclo usando a heurística do menor aumento
 *                  - m   alterna correção do clique do mouse ('modo de desenho')
 *                  - q   sair (não!)
 *
 *  Dependências: Point, Tour, algs4.StdOut, algs4.StdDraw, algs4.In
 **************************************************************************** */

import algs4.In;
import algs4.StdDraw;
import algs4.StdOut;

import java.util.ArrayList;

public class TSPVisualizer {

    public static void main(String[] args) {

        // Define dimensões iniciais da janela gráfica
        int xscale = 512;
        int yscale = 512 - 70;

        StdDraw.setXscale(0, xscale);
        StdDraw.setYscale(-70, yscale);

        // Exibe instruções na tela
        StdDraw.textLeft(50, 400, "Comandos de teclado:");
        StdDraw.textLeft(80, 380, "- n   alterna ciclo da heurística do vizinho mais próximo");
        StdDraw.textLeft(80, 360, "- s   alterna ciclo da heurística do menor aumento");
        StdDraw.textLeft(80, 340, "- m   'modo de desenho'");
        StdDraw.textLeft(80, 320, "- q   sair");

        // Ativa o double buffering para animação suave
        StdDraw.enableDoubleBuffering();

        // Inicializa os dois tours (ciclos)
        Tour nearest = new Tour();
        Tour smallest = new Tour();

        // Lista para armazenar todos os pontos adicionados
        ArrayList<Point> points = new ArrayList<>();

        // Flags de controle
        boolean redraw = false;
        boolean showingNearest = true;
        boolean showingSmallest = true;
        boolean mouseWasUp = true;
        boolean mouseCorrect = true; // modo de desenho do mouse


        // Se for fornecido um arquivo de entrada
        if (args.length > 0) {
            String filename = args[0];
            In in = new In(filename);

            xscale = in.readInt();
            yscale = in.readInt();

            StdDraw.setXscale(0, xscale);
            StdDraw.setYscale(-70, yscale);

            StdOut.println(xscale + " " + yscale);

            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                StdOut.println(x + " " + y);

                Point p = new Point(x, y);
                points.add(p);
                nearest.insertNearest(p);
                smallest.insertSmallest(p);
            }

            redraw = true;
        } else {
            StdOut.println(xscale + " " + yscale);
        }

        // LOOP PRINCIPAL DE EVENTOS
        while (true) {

            // Trata eventos de teclado
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();

                if (key == 'n') showingNearest = !showingNearest;
                if (key == 's') showingSmallest = !showingSmallest;
                if (key == 'm') mouseCorrect = !mouseCorrect;
                if (key == 'q') break;

                redraw = true;
            }

            // Trata cliques do mouse
            if (StdDraw.isMousePressed() && (!mouseCorrect || mouseWasUp)) {
                mouseWasUp = false;

                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();

                Point p = new Point(x, y);
                points.add(p);
                nearest.insertNearest(p);
                smallest.insertSmallest(p);

                StdOut.println(x + " " + y);
                redraw = true;
            } else {
                mouseWasUp = !StdDraw.isMousePressed();
            }

            // Redesenha a tela se necessário
            if (redraw) {
                redraw = false;

                StdDraw.clear();

                // Desenha o ciclo do vizinho mais próximo (vermelho)
                if (showingNearest) {
                    StdDraw.setPenRadius(0.004);
                    StdDraw.setPenColor(StdDraw.RED);
                    nearest.draw();
                }

                // Desenha o ciclo do menor aumento (azul)
                if (showingSmallest) {
                    StdDraw.setPenRadius(0.003);
                    StdDraw.setPenColor(StdDraw.BLUE);
                    smallest.draw();
                }

                // Desenha todos os pontos
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.005);
                for (Point p : points) {
                    p.draw();
                }

                // Exibe estatísticas na tela
                StdDraw.textLeft(10, -10, "número de pontos: " + points.size());
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.textLeft(10, -35, "vizinho mais próximo: " + nearest.length());
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.textLeft(10, -60, "menor aumento: " + smallest.length());
                StdDraw.setPenColor(StdDraw.BLACK);

                // Atualiza a tela
                StdDraw.show();
                StdDraw.pause(50);
            }
        }

        System.exit(0);
    }
}