/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mushroom;

import com.sun.java.swing.plaf.windows.WindowsTreeUI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Diego
 */
public class RedNeuronal {
    int totalAtributos = 23;
    int totalData = 8124;
    int totalDataEntrenamiento = 6905;
    int totalDataPrueba = totalData - totalDataEntrenamiento;//1219
    double[][] data = new double[totalData][totalAtributos];//todos los datos codificados
    double[][] entrenamiento = new double[totalDataEntrenamiento][totalAtributos];//85% de los datos
    double[][] prueba = new double[totalDataPrueba][totalAtributos];//15 % de los datos
 
    public RedNeuronal() {
        List<Integer> lista = crearListaDesordenada();
        elegirDataEntrenamiento(lista);
        elegirDataPrueba(lista);
    }
    /**
     * Crea una lista desordenada de numeros enteros, para elegir
     * la data de entrenamiento y la de prueba.
     * @return ArrayList que contiene los numeros desorndenados.
     */
    public List<Integer> crearListaDesordenada(){
        List<Integer> lista = new ArrayList<>();
        for (int i = 0; i < totalData; i++) {
            lista.add(i);
        }
        Collections.shuffle(lista);
        return lista;
    }
    
    /**
     * Se elige la data usada para el entrenamiento,
     * la cual corresponde al 85% de la data total,
     * para esto se usa la lista con los numeros desordenados.
     * @param lista ArrayList con los numeros desordenados
     */
    public void elegirDataEntrenamiento(List<Integer> lista){
        int fila = 0;
        for (int i = 0; i < totalDataEntrenamiento; i++) {
            int numRandom = lista.get(i);
            for (int j = 0; j < totalAtributos; j++) {
                entrenamiento[fila][j] = data[numRandom][j];
            }
            fila++;
        }
    }
    
    /**
     * Se elige la data usada para las pruebas,
     * la cual corresponde al 15% de la data total,
     * para esto se usa la lista con los numeros desordenados.
     * @param lista ArrayList con los numeros desordenados.
     */
    public void elegirDataPrueba(List<Integer> lista){
        int fila = 0;
        for (int i = totalDataEntrenamiento; i < totalData; i++) {
            int numRandom = lista.get(i);
            for (int j = 0; j < totalAtributos; j++) {
                prueba[fila][j] = data[numRandom][j];
            }
            fila++;
        }
    }
    
    private void calcularPesosCapas(){
        double[][] pesos1 = new double[23][5]; //23 neuronas de entrada que se conectan a 5 neuronas.
        double[][] pesos2 = new double[5][1]; // 5 neuronas de la capa oculta se conectan a la neurona de salida.
        
        for(int i = 0; i<pesos1.length; i++){
            for(int j = 0; j<pesos1[i].length; j++){
                pesos1[i][j]=this.obtenerPesoAleatorio();
            }
        }
        
        for(int i = 0; i<pesos2.length; i++){
            for(int j = 0; j<pesos2[i].length; j++){
                pesos2[i][j]=this.obtenerPesoAleatorio();
            }
        }
        
        System.out.println("Pesos de la capa 1: " + Arrays.deepToString(pesos1));
        System.out.println("Pesos de la capa 2: " + Arrays.deepToString(pesos2));
    }
    
    /**
     * Metodo  utilizado para obtener un pesos aleatorio para la arista que conecta una neurona a otra.
     * @return double peso
     */
    private double obtenerPesoAleatorio(){
        double peso = 0;
        Random rnd = new Random();
        int numero = rnd.nextInt(10);
        peso = numero/10.0;
        return peso;
    }
}
