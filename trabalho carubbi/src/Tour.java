/* *****************************************************************************
 * Grupo:
 * Alunos integrantes:
 *
 * Descrição: Esta classe define o tipo de dado Tour implementando uma
 * Lista Encadeada Circular e definindo métodos para permitir a implementação
 * de duas heurísticas para encontrar boas soluções para o TSP.
 **************************************************************************** */

import algs4.StdDraw;
import algs4.StdOut;

public class Tour {
    private class Node {
        private Point p;  // ponto armazenado no nó
        private Node next; // ponteiro para o próximo nó
    }

    private Node start; // nó inicial da lista circular

    // Cria um ciclo vazio
    public Tour() {
        start = new Node(); // nó inicial com p = null
        start.next = start; // aponta para si mesmo
    }

    // Cria o ciclo de 4 pontos: a -> b -> c -> d -> a
    public Tour(Point a, Point b, Point c, Point d) {
        Node aNode = new Node(); aNode.p = a;
        Node bNode = new Node(); bNode.p = b;
        Node cNode = new Node(); cNode.p = c;
        Node dNode = new Node(); dNode.p = d;

        aNode.next = bNode;
        bNode.next = cNode;
        cNode.next = dNode;
        dNode.next = aNode;

        start = aNode;
    }

    // Retorna o número de pontos no ciclo
    public int size() {
        if (start.p == null) return 0;

        int count = 0;
        Node current = start;
        do {
            count++;
            current = current.next;
        } while (current != start);
        return count;
    }

    // Retorna o comprimento total do ciclo
    public double length() {
        if (start.p == null) return 0.0;

        double total = 0.0;
        Node current = start;
        do {
            total += current.p.distanceTo(current.next.p);
            current = current.next;
        } while (current != start);
        return total;
    }

    // Representação textual do ciclo
    public String toString() {
        if (start.p == null) return "";

        StringBuilder sb = new StringBuilder();
        Node current = start;
        do {
            sb.append(current.p.toString()).append("\n");
            current = current.next;
        } while (current != start);
        return sb.toString();
    }

    // Desenha o ciclo na tela
    public void draw() {
        if (start.p == null) return;

        Node current = start;
        do {
            current.p.drawTo(current.next.p);
            current = current.next;
        } while (current != start);
    }

    // Insere p usando a heurística do vizinho mais próximo
    public void insertNearest(Point p) {
        if (start.p == null) {
            // Se o ciclo estiver vazio
            start.p = p;
            start.next = start;
            return;
        }

        Node closest = start;
        double minDistance = start.p.distanceTo(p);

        Node current = start.next;
        do {
            double distance = current.p.distanceTo(p);
            if (distance < minDistance) {
                minDistance = distance;
                closest = current;
            }
            current = current.next;
        } while (current != start);

        // Insere p após o nó mais próximo
        Node newNode = new Node();
        newNode.p = p;
        newNode.next = closest.next;
        closest.next = newNode;
    }

    // Insere p usando a heurística do menor aumento
    public void insertSmallest(Point p) {
        if (start.p == null) {
            // Se o ciclo estiver vazio
            start.p = p;
            start.next = start;
            return;
        }

        Node best = start;
        double minIncrease = calculateIncrease(start, p);

        Node current = start.next;
        do {
            double increase = calculateIncrease(current, p);
            if (increase < minIncrease) {
                minIncrease = increase;
                best = current;
            }
            current = current.next;
        } while (current != start);

        // Insere p após o melhor nó
        Node newNode = new Node();
        newNode.p = p;
        newNode.next = best.next;
        best.next = newNode;
    }

    // Calcula o aumento de comprimento ao inserir p entre current e current.next
    private double calculateIncrease(Node current, Point p) {
        Node nextNode = current.next;
        return current.p.distanceTo(p) + p.distanceTo(nextNode.p) - current.p.distanceTo(nextNode.p);
    }

    // Testa a classe Tour
    public static void main(String[] args) {
        Point a = new Point(1.0, 1.0);
        Point b = new Point(1.0, 4.0);
        Point c = new Point(4.0, 4.0);
        Point d = new Point(4.0, 1.0);

        Tour squareTour = new Tour(a, b, c, d);

        StdOut.println("# de pontos = " + squareTour.size());
        StdOut.println("Comprimento do ciclo = " + squareTour.length());
        StdOut.println(squareTour);

        StdDraw.setXscale(0, 6);
        StdDraw.setYscale(0, 6);

        Point e = new Point(5.0, 6.0);
        squareTour.insertNearest(e);
        squareTour.insertSmallest(e); // Apenas para teste; normalmente não insere o mesmo ponto duas vezes
        squareTour.draw();
    }
}