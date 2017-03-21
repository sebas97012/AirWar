package com.itcr.ce.data;

/**
 * Created by Arturo on 20/3/2017.
 */
public class Node<T>{

    private T dataT;
    private Node<T> next;

    public Node(T data){
        this.dataT = data;
        this.next = null;
    }

    public T getDataT() {
        return this.dataT;
    }

    public void setDataT(T dataT) {
        this.dataT = dataT;
    }

    public Node<T> getNext() {
        return this.next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}
