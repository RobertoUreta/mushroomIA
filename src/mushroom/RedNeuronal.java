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
}
