/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.LinkedList;

/**
 * @author Sebastian Montoya
 */
public class Logic {

    protected int profundity = 0;

    /**
     * Condiciones para que se llegue a una solucion.
     *
     * @param containers contenedores
     * @param solve solucion
     * @return Si es factible
     */
    public boolean restricciones(LinkedList<Container> containers, int solve) {
        if (containers.get(0).deno <= 0 && containers.get(1).deno <= 0 && containers.get(2).deno <= 0 && solve <= 0) {
            System.out.println("No puedo hacerlo con un contenedor imaginario.");
            return false;
        } else if (containers.get(0).deno < solve && containers.get(1).deno < solve && containers.get(2).deno < solve) {
            System.out.println("No existe un contenedor que pueda almacenar la solucion.");
            return false;
        } else if (containers.get(0).deno == containers.get(1).deno && containers.get(1).deno == containers.get(2).deno) {
            System.out.println("Los contenedores tiene el mismo tamaño no puedo llegar a la respuesta.");
            return false;
        } else if (containers.get(0).deno == solve || containers.get(1).deno == solve || containers.get(0).deno == solve) {
            for (int i = 0; i < containers.size(); i++) {
                if (containers.get(i).deno == solve) {
                    System.out.println("Respuesta Trivial, Llenar contenedor " + (i + 1));
                    return false;
                }
            }
        } else if (solve <= 0) {
            System.out.println("No se pueden tener 0 o menos galones.");
            return false;
        }

        return true;
    }

    /**
     * Solo es para controlar una sub-interfaz de funcionalidades y revisar la
     * restriccion.
     *
     * @param containers contenedores
     * @param solve solucion Objetivo
     * @param option Decision del Usuario.
     */
    public void OptionsContainers(LinkedList<Container> containers, int solve, int option) {
        switch (option) {
            case 1:
                if (restricciones(containers, solve)) {
                    Start(containers.get(0), containers.get(1), containers.get(2), solve, containers.get(0).deno, 0);
                }
                break;
            case 2:
                if (restricciones(containers, solve)) {
                    System.out.println("Profundidad: " + this.profundity);
                }
                break;
            default:
                System.out.println("Option not found.");
                break;
        }
    }

    /**
     * Algoritmo Heuristico permutaciones de 3 contenedores
     *
     * @param ci Contenedor inicial
     * @param cj Contenedor medio.
     * @param ck Contenedor final.
     * @param solve Solucion Objetivo
     * @param heuristic Busqueda informada.
     * @param jumpoints Ambientes
     * @return Encuentra solucion.
     *
     * Disp(i,j) = Disponibilidad de contenedor en (i o j). hij o hik =
     * heuristica entre el contenedor i contra j o k.
     */
    private int Start(Container ci, Container cj, Container ck, int solve, int heuristic, int jumpoints) {
        int dispj = (cj.deno - cj.cap);
        int dispk = (ck.deno - ck.cap);
        int hij = Math.abs(ci.cap - dispj);
        int hik = Math.abs(ci.cap - dispk);

        System.out.println("INICIO ITERACION: " + (jumpoints + 1));
        System.out.println("ci: " + ci.cap + " cj: " + cj.cap + " ck: " + ck.cap);
        System.out.println("heurisitica: " + heuristic);
        System.out.print("hij: " + hij);
        System.out.println(" hik: " + hik);

        System.out.print("dispj: " + dispj);
        System.out.println(" dispk: " + dispk);
        System.out.println("solve: " + solve);

        if (ci.cap == solve || cj.cap == solve || ck.cap == solve) { //Llego a una solucion.
            System.out.println("-----------------------------------");
            System.out.println("Solucion de la operacion: ");
            System.out.println("C" + ci.id + ": " + ci.cap + " C" + cj.id + ": " + cj.cap + " C" + ck.id + ": " + ck.cap);
            this.profundity = jumpoints;
            System.out.println("-----------------------------------");
            return 0;
        } else if (ci.cap == 0) { // Es necesario llegar ci
            System.out.println("Llenar ci");
            System.out.println("ci: " + ci.cap + " cj: " + cj.cap + " ck: " + ck.cap);
            System.out.println("-----------------------------------");
            ci.cap = ci.deno;
            Start(ci, cj, ck, solve, heuristic, jumpoints + 1);
        } //Condicion de Factibilidad 1.Debe reducir el resultado. 2.cj o ck tienen espacio 
        else if (heuristic > solve && (cj.cap < cj.deno || ck.cap < ck.deno)) {
            if (hij >= solve || hik >= solve) { //Condiciones de factibilidad
                System.out.println("Reduccion de agua.");
                if (cj.cap < cj.deno && hij <= hik) { // Es posible cj y llenar cj es mejor.

                    if (ci.cap > dispj) {  //Llenas del todo a cj y quedas con espacio.

                        cj.cap = cj.deno;
                        ci.cap = ci.cap - dispj;
                        System.out.println("ci -> cj");
                        System.out.println("ci: " + ci.cap + " cj: " + cj.cap + " ck: " + ck.cap);
                        System.out.println("-----------------------------------------------");
                        Start(ci, cj, ck, solve, hij, jumpoints + 1);

                    } else if (ci.cap <= dispj) { //Llena completa o parcialmente cj y quedas sin espacio.
                        cj.cap = cj.cap + ci.cap;
                        ci.cap = 0;

                        Start(ci, cj, ck, solve, hij, jumpoints + 1);

                    }
                } else if (ck.cap < ck.deno && hik < hij) {// Es posible ck y llenar ck es mejor.
                    System.out.println("ci -> ck");
                    if (ci.cap > dispk) {  //Llenas del todo a ck y quedas con espacio.
                        ck.cap = ck.deno;
                        ci.cap = ci.cap - dispk;
                        System.out.println("ci: " + ci.cap + "\n ck: " + ck.cap);
                        System.out.println("-----------------------------------------------");
                        Start(ci, cj, ck, solve, hik, jumpoints + 1);
                    } else if (ci.cap <= dispk) { //Llena completa o parcialmente ck y quedas sin espacio.
                        ck.cap = ck.cap + ci.cap;
                        ci.cap = 0;
                        System.out.println("ci: " + ci.cap + "\n ck: " + ck.cap);
                        System.out.println("------------------------------------");
                        Start(ci, cj, ck, solve, hik, jumpoints + 1);
                    }
                }
            }
            //Condicion de Factibilidad 1.Debe reducir el resultado 2. No hay espacio para continuar.
        } else if (heuristic >= solve && (cj.cap == cj.deno && ck.cap == ck.deno)) {
            //Calculando las heuristicas segun su tamaño y no su capacidad ya que esta lleno.
            hij = Math.abs(ci.cap - cj.deno);
            hik = Math.abs(ci.cap - ck.deno);

            System.out.println("Tomando mejor decision para vaciar con valores mayores a la solucion.");
            if (hij > hik) {
                cj.cap = 0;

                System.out.println("Vaciar cj");
                System.out.println("ci: " + ci.cap + "\n cj: " + cj.cap + "\n ck: " + ck.cap);
                System.out.println("-----------------------------------------------");
                Start(ci, cj, ck, solve, heuristic, jumpoints + 1);
            } else if (hij < hik) {
                ck.cap = 0;

                System.out.println("Vaciar ck");
                System.out.println("ci: " + ci.cap + "\n cj: " + cj.cap + "\n ck: " + ck.cap);
                System.out.println("-----------------------------------------------");
                Start(ci, cj, ck, solve, heuristic, jumpoints + 1);
            } else {
                if (cj.cap < ck.cap && cj.cap < cj.deno) {
                    cj.cap = 0;

                    System.out.println("Vaciar cj x su cercania a la respuesta.");
                    System.out.println("-----------------------------------------------");
                    Start(ci, cj, ck, solve, heuristic, jumpoints + 1);
                } else if (cj.cap > ck.cap && ck.cap < ck.deno) {
                    ck.cap = 0;

                    System.out.println("Vaciar ck x su cercania a la respuesta.");
                    System.out.println("ci: " + ci.cap + "\n cj: " + cj.cap + "\n ck: " + ck.cap);
                    System.out.println("-----------------------------------------------");
                    Start(ci, cj, ck, solve, heuristic, jumpoints + 1);
                }
            }
            //Condicion de factibilidad 1.Debe aumentar el resultado y estan llenos ambos.    
        } else if (heuristic < solve && (cj.cap == cj.deno && ck.cap == ck.deno)) { //Estan llenos ambos
            //Calculando heurisitcas segun su tamaño y no su capacidad.
            hij = Math.abs(ci.cap - cj.deno);
            hik = Math.abs(ci.cap - ck.deno);

            System.out.println("Tomando la mejor decision para vaciar con valores menores a la solucion");
            if (hij < hik) {
                cj.cap = 0;

                System.out.println("Vaciar cj");
                System.out.println("ci: " + ci.cap + "\n cj: " + cj.cap + "\n ck: " + ck.cap);
                System.out.println("-----------------------------------------------");
                Start(ci, cj, ck, solve, heuristic, jumpoints + 1);
            } else if (hij > hik) {
                ck.cap = 0;

                System.out.println("Vaciar ck");
                System.out.println("ci: " + ci.cap + "\n cj: " + cj.cap + "\n ck: " + ck.cap);
                System.out.println("-----------------------------------------------");
                Start(ci, cj, ck, solve, heuristic, jumpoints + 1);
            } else {
                if (cj.cap < ck.cap && cj.cap < cj.deno) {
                    cj.cap = 0;

                    System.out.println("Vaciar cj x su cercania a la respuesta.");
                    System.out.println("-----------------------------------------------");
                    Start(ci, cj, ck, solve, heuristic, jumpoints + 1);
                } else if (cj.cap > ck.cap && ck.cap < ck.deno) {
                    ck.cap = 0;

                    System.out.println("Vaciar ck x su cercania a la respuesta.");
                    System.out.println("ci: " + ci.cap + "\n cj: " + cj.cap + "\n ck: " + ck.cap);
                    System.out.println("-----------------------------------------------");
                    Start(ci, cj, ck, solve, heuristic, jumpoints + 1);
                }
            }
            //Condicion de Factibilidad 1.Debe aumentar.     
        } else if (heuristic < solve) {
            System.out.println("Aumentar el agua");
            if (hij < solve || hik < solve) {
                if (cj.cap < cj.deno && hij <= hik) { // Es posible cj y llenar cj es mejor.
                    if (ci.cap > dispj) {  //Llenas del todo a cj y quedas con espacio.

                        cj.cap = cj.deno;
                        ci.cap = ci.cap - dispj;
                        System.out.println("ci -> cj");
                        System.out.println("ci: " + ci.cap + " cj: " + cj.cap + " ck: " + ck.cap);
                        System.out.println("-----------------------------------------------");
                        //Cambiando contenedores.
                        Start(ck, cj, ci, solve, ck.deno, jumpoints + 1);
                    } else if (ci.cap <= dispj) { //Llena completa o parcialmente cj y quedas sin espacio.
                        cj.cap = cj.cap + ci.cap;
                        ci.cap = 0;
                        //Cambiando contenedores.
                        Start(ck, cj, ci, solve, ck.deno, jumpoints + 1);

                    }
                } else if (ck.cap < ck.deno && hik < hij) {// Es posible ck y llenar ck es mejor.
                    System.out.println("ci -> ck");
                    if (ci.cap > dispk) {  //Llenas del todo a ck y quedas con espacio.
                        System.out.println("ci: " + ci.cap + " ck: " + ck.cap);
                        System.out.println("-----------------------------------------------");
                        //Cambiando contenedores. 
                        Start(cj, ci, ck, solve, cj.deno, jumpoints);
                    } else if (ci.cap <= dispk) { //Llena completa o parcialmente ck y quedas sin espacio.
                        ck.cap = ck.cap + ci.cap;
                        ci.cap = 0;
                        System.out.println("ci: " + ci.cap + " ck: " + ck.cap);
                        System.out.println("-----------------------------------------------");
                        //Cambiando contenedores
                        Start(cj, ci, ck, solve, cj.deno, jumpoints + 1);
                    }
                }
            }
            System.out.println("El Algoritmo heurisitico termino.");
        }
        return 0;
    }

    public void juego3x3(int posx, int posy, int heuristica) {
        System.out.println("Iniciando desde: (" + posx + "," + posy + ")");
        int matriz[][] = new int[3][3];
        matriz[posx][posy] = 1;
        int auxx = posx;
        int auxy = posy;
        int bx = posx;
        int by = posy;
        
        System.out.println("ITERACION: " + heuristica);
        System.out.println("posicion:[" + posx + "," + posy + "]");
        while (heuristica != 9) {
            //Diagional superior
            posx = auxx;
            posy = auxy;

            auxx = posx - 1;
            auxy = posy + 1;

            System.out.println("Diagional");
            System.out.println("[" + auxx + "," + auxy + "]");

            if (auxx < 0 && auxy == matriz.length) { //Es esquina superior y derecha.

                auxx = matriz.length - 1;
                auxy = 0;
                
                System.out.println("Es esquina superior y derecha (CASO ESPECIAL)");
                System.out.println("Recalculado ubicacion de: [" + posx + "," + posy + "]: ");
                System.out.println("auxx: "+auxx+" auxy: "+auxy+": "+matriz[auxx][auxy]);
                if (matriz[auxx][auxy] == 0) {
                    posx = auxx;
                    posy = auxy;
                    System.out.println("a este ? ");
                    heuristica++;
                    System.out.println("[" + posx + "," + posy + "]: " + heuristica);
                    System.out.println("-----------------------------------");
                    matriz[posx][posy] = heuristica;

                } else {
                    posx = 1;
                    auxx = posx;
                    auxy = posy;
                    heuristica++;
                    System.out.println("[" + posx + "," + posy + "]: " + heuristica);
                    System.out.println("-----------------------------------");
                    matriz[posx][posy] = heuristica;
                }
            } else if (auxx == 1 && auxy == matriz.length) { // Es esquina inferior derecha
                auxy = 0;
                System.out.println("Es esquina inferior derecha (CASO ESPECIAL)");
                System.out.println("Recalculado a: [" + posx + "," + posy + "]: ");
                if (matriz[auxx][auxy] == 0) {
                    posx = auxx;
                    posy = auxy;
                    heuristica++;
                    System.out.println("[" + posx + "," + posy + "]: " + heuristica);
                    System.out.println("-----------------------------------");
                    matriz[posx][posy] = heuristica;
                } else {
                    posx = 0;
                    auxx = posx;
                    auxy = posy;
                    heuristica++;
                    System.out.println("[" + posx + "," + posy + "]: " + heuristica);
                    System.out.println("-----------------------------------");
                    matriz[posx][posy] = heuristica;
                }
            } else if (auxx < 0) { //Son esquinas superiores.
                auxx = matriz.length - 1;
                System.out.println("Es esquina superior.");
                System.out.println("Recalculado a: [" + posx + "," + posy + "]: ");
                if (matriz[auxx][auxy] == 0) {
                    posx = auxx;
                    posy = auxy;
                    heuristica++;
                    System.out.println("[" + posx + "," + posy + "]: " + heuristica);
                    System.out.println("-----------------------------------");
                    matriz[posx][posy] = heuristica;
                } else {
                    posx = posx + 1;
                    auxx = posx;
                    auxy = posy;
                    heuristica++;
                    System.out.println("[" + posx + "," + posy + "]: " + heuristica);
                    System.out.println("-----------------------------------");
                    matriz[posx][posy] = heuristica;
                }
            } else if (auxy == matriz.length) { // Son esquinas derechas.
                auxx = posx - 1;
                auxy = 0;
                System.out.println("Es esquina derecha.");
                System.out.println("Recalculando a: [" + auxx + "," + auxx + "]");
                if (matriz[auxx][auxx] == 0) {
                    posx = auxx;
                    posy = auxy;
                    heuristica++;
                    System.out.println("[" + posx + "," + posy + "]: " + heuristica);
                    System.out.println("-----------------------------------");
                    matriz[posx][posy] = heuristica;
                } else {
                    posx = posx + 1;
                    auxx = posx;
                    auxy = posy;
                    heuristica++;
                    System.out.println("[" + posx + "," + posy + "]: " + heuristica);
                    System.out.println("-----------------------------------");
                    matriz[posx][posy] = heuristica;
                }
            } else { // Es el cuadro inferior izquierdo de 2x2.
                System.out.println("No son esquinas derechas o superiores.");
                System.out.println("Recalculando a: [" + auxx + "," + auxy + "]");
                if (matriz[auxx][auxy] == 0) {
                    heuristica++;
                    posx = auxx;
                    posy = auxy;
                    System.out.println("[" + posx + "," + posy + "]: " + heuristica);
                    System.out.println("-----------------------------------");
                    matriz[posx][posy] = heuristica;
                    
                } else if (posx == matriz.length - 1) {
                    posx = 0;
                    auxx = posx;
                    auxy = posy;
                    heuristica++;
                    System.out.println("[" + posx + "," + posy + "]: " + heuristica);
                    System.out.println("-----------------------------------");
                    matriz[posx][posy] = heuristica;
                } else {
                    posx = posx + 1;
                    auxx = posx;
                    auxy = posy;
                    heuristica++;
                    System.out.println("[" + posx + "," + posy + "]: " + heuristica);
                    System.out.println("-----------------------------------");
                    matriz[posx][posy] = heuristica;

                }
            }
            System.out.println("ITERACION: " + heuristica);
            System.out.println("posicion:[" + posx + "," + posy + "]");
        }
        //Aca busca el mas cercano a 2 empieza desde (0,0) pero puede ser cualquiera.
        if (matriz[0][0] == 2) {
            System.out.println("El algoritmo heuristico ha finalizado y ha hayado esta posible respuesta.");
            for (int[] matriz1 : matriz) {
                System.out.print("[");
                for (int j = 0; j < matriz.length; j++) {
                    System.out.print(" " + matriz1[j] + " ");
                }
                System.out.println("]");
            }
        } else {
            System.out.println("Aun no lo ha encontrado probando con el siguiente \n");
            for (int[] matriz1 : matriz) {
                System.out.print("[");
                for (int j = 0; j < matriz.length; j++) {
                    System.out.print(" " + matriz1[j] + " ");
                }
                System.out.println("]");
            }
            System.out.println("\n");
            if (by < matriz.length-1) {
                System.out.println("(bx: "+bx+","+"by: "+by+")");
                System.out.println("matriz.lenght: "+matriz);
                by++;
                System.out.println("Limpiando matriz.");
                System.out.println("-----------------------------------");
                juego3x3(bx, by, 1);
            } else if (bx == matriz.length && by == matriz.length) {
                System.out.println("No hemos podido encontrar solucion \n");
            } else {
                by = 0;
                bx = bx+1;
                System.out.println("Limpiando matriz.");
                System.out.println("-----------------------------------");
                juego3x3(bx, by, 1);
            }
        }
    }
}
