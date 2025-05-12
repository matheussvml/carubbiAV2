/* *****************************************************************************
 *  Grupo: C
 *  Alunos integrantes: Matheus Vasconcelos 2315043, Miguel Tacchi 2310356, Diego Henrique 2315108, Edinei Xavier 2310369, Erich Lima 2310362, Lucas De Oliveira Falcao 2315036
 *
 * Descrição: Esta classe define o tipo de dado Tour implementando uma
 * Lista Encadeada Circular e definindo métodos para permitir a implementação
 * de duas heurísticas para encontrar boas soluções para o TSP.
 **************************************************************************** */

import algs4.StdDraw;
import algs4.StdOut;

public class Tour {
    private class Node {
        private Point p; // valor do ponto do nó
        private Node next; // ponteiro para o próximo nó
    }

    private Node start; // primeiro nó na lista encadeada

    // cria um ciclo vazio
    public Tour() {
        start = new Node();
    }

    // cria o ciclo de 4 pontos a->b->c->d->a (para depuração)
    public Tour(Point a, Point b, Point c, Point d) {
        start = new Node();
        Node b1 = new Node();
        Node c1 = new Node();
        Node d1 = new Node();
        start.p = a;
        b1.p = b;
        c1.p = c;
        d1.p = d;
        start.next = b1;
        b1.next = c1;
        c1.next = d1;
        d1.next = start;
    }

    // retorna o número de pontos neste ciclo
    public int size() {
        if (start.p == null) {
            return 0;
        } else {
            int counter = 0;
            Node current = start;
            do {
                current = current.next;
                counter += 1;
            } while (!current.equals(start));
            return counter;
        }
    }

    // retorna o comprimento deste ciclo
    public double length() {
        if (start.p == null) {
            return 0.0;
        } else {
            double distance = 0.0;
            Node current = start;
            do {
                distance += current.p.distanceTo(current.next.p);
                current = current.next;
            } while (!current.equals(start));
            return distance;
        }
    }

    // retorna uma representação em string deste ciclo
    public String toString() {
        if (start.p == null) {
            return "";
        } else {
            Node current = start;
            StringBuilder str = new StringBuilder();
            do {
                str.append(current.p.toString() + "\n");
                current = current.next;
            } while (!current.equals(start));
            return str.toString();
        }
    }

    // desenha este ciclo na tela padrão
    public void draw() {
        if (start.p != null && start.next != null) {
            Node current = start;
            do {
                current.p.drawTo(current.next.p);
                current = current.next;
            } while (!current.equals(start));
        }
    }

    // insere p usando a heurística do vizinho mais próximo
    public void insertNearest(Point p) {
        if (start == null || start.p == null) {
            // Se o tour estiver vazio, cria o primeiro nó
            start = new Node();
            start.p = p;
            start.next = start;
            return;
        }

        Node current = start;
        Node nearest = start;
        double minDistance = p.distanceTo(start.p);

        // Percorre o ciclo para encontrar o ponto mais próximo
        do {
            double distance = p.distanceTo(current.p);
            if (distance < minDistance) {
                minDistance = distance;
                nearest = current;
            }
            current = current.next;
        } while (current != start);

        // Insere após o ponto mais próximo
        Node newNode = new Node();
        newNode.p = p;
        newNode.next = nearest.next;
        nearest.next = newNode;
    }

    // insere p usando a heurística do menor aumento
    public void insertSmallest(Point p) {
        if (start == null || start.p == null) {
            // Se o tour estiver vazio, cria o primeiro nó
            start = new Node();
            start.p = p;
            start.next = start;
            return;
        }

        Node current = start;
        Node bestNode = start;
        double minIncrease = calculateIncrease(start, p);

        // Percorre o ciclo para encontrar onde causará menor aumento
        do {
            double increase = calculateIncrease(current, p);
            if (increase < minIncrease) {
                minIncrease = increase;
                bestNode = current;
            }
            current = current.next;
        } while (current != start);

        // Insere após o melhor nó encontrado
        Node newNode = new Node();
        newNode.p = p;
        newNode.next = bestNode.next;
        bestNode.next = newNode;
    }

    // calcula o aumento no comprimento ao inserir p entre current e current.next
    private double calculateIncrease(Node current, Point p) {
        Node next = current.next;
        double original = current.p.distanceTo(next.p);
        double newSegment1 = current.p.distanceTo(p);
        double newSegment2 = p.distanceTo(next.p);
        return (newSegment1 + newSegment2) - original;
    }

    // testa esta classe chamando todos os construtores e métodos de instância
    public static void main(String[] args) {
        // define 4 pontos, vértices de um quadrado
        Point a = new Point(1.0, 1.0);
        Point b = new Point(1.0, 4.0);
        Point c = new Point(4.0, 4.0);
        Point d = new Point(4.0, 1.0);

        // cria o ciclo a -> b -> c -> d -> a
        Tour squareTour = new Tour(a, b, c, d);

        // imprime o número de pontos na saída padrão
        int size = squareTour.size();
        StdOut.println("# de pontos = " + size);

        // imprime o comprimento do ciclo na saída padrão
        double length = squareTour.length();
        StdOut.println("Comprimento do ciclo = " + length);

        // imprime o ciclo na saída padrão
        StdOut.println(squareTour);

        StdDraw.setXscale(0, 6);
        StdDraw.setYscale(0, 6);

        Point e = new Point(5.0, 6.0);
        squareTour.insertNearest(e);
        squareTour.insertSmallest(e);
        squareTour.draw();
    }
}
