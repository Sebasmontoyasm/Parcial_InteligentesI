/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Remote
 */
public class Nodo {
    String id;
    int valor;
    Nodo hijoder;
    Nodo hijoizq;
    Nodo raiz;

    public Nodo() {
    }

    public Nodo(String id, int valor) {
        this.id = id;
        this.valor = valor;
        this.hijoder = null;
        this.hijoizq = null;
    }
    
    protected void agregarNodo(String newNodo,int valor){
        if(this.hijoder == null){
            this.hijoder = new Nodo(id, valor);
        }else if(this.hijoizq == null){
             this.hijoder = new Nodo(id, valor);
        }
        else{
            System.out.println("Ya tienes hijos!");
        }   
    }
    protected void removerNodo(String nodohijo){
        if(this.hijoder.id.equalsIgnoreCase(nodohijo)){
            this.hijoder = null;
        }else if(this.id.equalsIgnoreCase(nodohijo)){
             this.hijoizq = null;
        }
        else{
            System.out.println("No es un hijo");
        }   
    }
    
    
}
