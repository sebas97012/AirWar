package com.itcr.ce.data;

/**
 * Created by Arturo on 20/3/2017.
 */
public class Queue<T> extends LinkedList {
    private Node last;

    /**
     * Constructor
     */
    public Queue(){
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public Node getFirst() {
        return first;
    }

    public void setFirst(Node first) {
        this.first = first;
    }

    public Node getLast() {
        return last;
    }

    public void setLast(Node last) {
        this.last = last;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Metodo para agregar un nuevo elemento a la cola
     * @param data Data del nuevo elemento
     */
    public void enqueue(T data){
        Node newNode = new Node(data);
        if(this.first == null){ //Caso en el que la cola esta vacia
            this.first = newNode;
            this.last = newNode;
        } else{
            this.last.setNext(newNode);
            this.last = newNode;
        }
        this.size++;
    }

    /**
     * Metodo para obtener un elemento de la cola
     * @return Se retorna el primer elemento de la cola
     */
    public Node dequeue() {
        Node firstNode = this.first; //Se guarda la referencia del nodo que va a salir de la cola
        this.first = this.first.getNext(); //Se asigna el nuevo primer elemento

        if(this.size <= 2){
            this.last = this.first;
        }
        if(0 < this.size) {
            this.size--;
        }
        return firstNode;
    }

    public void printQueue(){
        Node current = this.first;
        while (current != null) {
            System.out.print(current.getDataT() + " ");
            current = current.getNext();
        }
        System.out.println("");
    }
}
