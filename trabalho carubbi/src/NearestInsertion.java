/* *****************************************************************************
 *  VOCÊ NÃO PRECISA MODIFICAR ESTE ARQUIVO
 *
 *  Compilação:  javac NearestInsertion.java
 *  Execução:    java NearestInsertion < file.txt
 *  Dependências: Tour.java Point.java algs4.StdIn.java algs4.StdDraw.java
 *
 *  Executa a heurística de inserção pelo vizinho mais próximo para o problema
 *  do caixeiro viajante e plota os resultados.
 *
 *  % java NearestInsertion < data/tsp1000.txt
 *
 **************************************************************************** */

import algs4.StdDraw;
import algs4.StdIn;
import algs4.StdOut;

public class NearestInsertion {

    public static void main(String[] args) {

        // obter dimensões
        int width = StdIn.readInt();
        int height = StdIn.readInt();
        int border = 20;
        StdDraw.setCanvasSize(width, height + border);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(-border, height);

        // ativar modo de animação
        StdDraw.enableDoubleBuffering();

        // executar a heurística de inserção pelo vizinho mais próximo
        Tour tour = new Tour();
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point p = new Point(x, y);
            tour.insertNearest(p);

            // descomente as 4 linhas abaixo para animar
            // algs4.StdDraw.clear();
            // tour.draw();
            // algs4.StdDraw.textLeft(20, 0, "comprimento = " + tour.length());
            // algs4.StdDraw.show();
            // algs4.StdDraw.pause(50);
        }

        // desenhar no quadro padrão
        tour.draw();
        StdDraw.show();
        
        // imprimir o ciclo no terminal padrão
        StdOut.println(tour);
        StdOut.printf("Comprimento do ciclo = %.4f\n", tour.length());
        StdOut.printf("Número de pontos = %d\n", tour.size());
    }

}