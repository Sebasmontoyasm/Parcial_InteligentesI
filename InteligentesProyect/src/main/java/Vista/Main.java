/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;
import Modelo.*;
import java.util.LinkedList;

/**
 *
 * @author Remote
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        /**
         * Crear los contenedores.
         * Opcion 1: Iniciar Algoritmo.
         * Opcion 2: Obtener profundidad.
         * 
         * Container(identificador,Tamaño,Capacidad)
         * OptionContainers(Contenedores,Objetivo,Opcion).
         * 
         * Restriccion de funcionamiento:
         * Los contenedores deben ingresarse en orden de Tamaño Mayor a Menor.
         */
        
        Logic L1 = new Logic();
                                    //id,den,cap
        Container c1 = new Container(1, 13, 0);
        Container c2 = new Container(2, 5, 0);
        Container c3 = new Container(3, 3, 0);
       
        LinkedList<Container> containers = new LinkedList<>();
        containers.add(c1);
        containers.add(c2);
        containers.add(c3);
                                    
        L1.OptionsContainers(containers,4,1);
        L1.OptionsContainers(containers,4,2);
        // Codigo: 1701410810 <- numero mas cercano el 2
        Logic L2 = new Logic();
        int matriz[][] = new int[3][3];
                 // x y heuristica  
        L2.juego3x3(0,0,1);
    }
    
}
