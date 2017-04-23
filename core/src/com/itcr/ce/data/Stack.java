package com.itcr.ce.data;

/**
 * Created by Arturo on 20/3/2017.
 */
public class Stack<T> extends LinkedList{
    public Stack(){
        super();
    }

    public void insert(T data){ //Insertar un nuevo elemento
        Node newNode = new Node(data);
        newNode.setNext(this.first);
        this.first = newNode;
        this.size++;
    }

    /**
     * Metodo que extrae un elemento de la cola
     * @return El primer nodo de la pila
     */
    public Node extract(){ //Tomar un elemento de la pila
        Node nodeTop = this.first;
        if(this.first != null){
            this.first = this.first.getNext();
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
        Node current = this.first;
        while(current != null){
            System.out.print(current.getDataT() + " ");
            current = current.getNext();
        }
        System.out.println("");
    }
}

