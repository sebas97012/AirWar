package com.itcr.ce.data;

/**
 * Created by Arturo on 20/3/2017.
 */
public class Stack<T> {
    private Node top;
    private int size;

    public Stack(){
        this.top = null;
        this.size = 0;
    }

    public Node getTop() {
        return top;
    }

    public void setTop(Node top) {
        this.top = top;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void insert(T data){ //Insertar un nuevo elemento
        Node newNode = new Node(data);
        newNode.setNext(this.top);
        this.top = newNode;
        this.size++;
    }

    /**
     * Metodo que extrae un elemento de la cola
     * @return El primer nodo de la pila
     */
    public Node extract(){ //Tomar un elemento de la pila
        Node nodeTop = this.top;
        if(this.top != null){
            this.top = this.top.getNext();
        }
        if(0 < this.size){
            this.size--;
        }
        return nodeTop;
    }

    /**
     * Metodo que determina si la pila esta vacia
     * @return True si la lista esta vacia, false si tiene algun elemento
     */
    public boolean isEmpty(){
        if(0 < this.size) {
            return true;
        } else{
            return false;
        }
    }

    public void printStack(){
        Node current = this.top;
        while(current != null){
            System.out.print(current.getDataT() + " ");
            current = current.getNext();
        }
        System.out.println("");
    }
}

