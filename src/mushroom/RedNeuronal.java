/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mushroom;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Diego
 */
public class RedNeuronal {
    
    double[][] data = new double[8124][23];//todos los datos codificados
    double[][] entrenamiento = new double[6905][23];//85% de los datos
    double[][] prueba = new double[1219][23];//15 % de los datos
    public RedNeuronal() {
        
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
    
    private double obtenerPesoAleatorio(){
        double peso = 0;
        Random rnd = new Random();
        int numero = rnd.nextInt(10);
        peso = numero/10.0;
        return peso;
    }
}
