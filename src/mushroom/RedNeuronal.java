/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mushroom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    //Pesos de RedNeuronal
    double[][] pesos1 = new double[23][5]; //23 neuronas de entrada que se conectan a 5 neuronas.
    double[][] pesos2 = new double[5][1]; // 5 neuronas de la capa oculta se conectan a la neurona de salida.
    
    public RedNeuronal() {
        crearDatos();
        List<Integer> lista = crearListaDesordenada();
        elegirDataEntrenamiento(lista);
        elegirDataPrueba(lista);
        this.imprimirData();
        this.calcularPesosCapas();
    }
    
    public void imprimirData(){
        int count = 1;
        for(int i = 0; i<data.length; i++){
            System.out.print(count+": ");
            for(int j = 0; j<data[i].length; j++){
                System.out.print(data[i][j] + " ");
            }
            count++;
            System.out.println();
        }
        
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
    /**
     * Metodo que calcula los pesos que se asignan a cada capa de pesos.
     */
    private void calcularPesosCapas(){
        
        
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
     * @return peso double
     */
    private double obtenerPesoAleatorio(){
        double peso = 0;
        Random rnd = new Random();
        int numero = rnd.nextInt(10);
        peso = numero/10.0;
        return peso;
    }
    
    /**
     * Se utiliza la funcion Sigmoide ==> P(t) = 1/(1+e^-t)
     * para mejorar la curva de aprendizaje de la red neuronal.
     * @param capa matriz a la que se aplica funcion sigmoide.
     * @return matriz modifica con la funcion sigmoide.
     */
    private double[][] funcionSigmoide(double[][] capa){
        double [][] resultado = new double[capa.length][capa[0].length];
        for (int i = 0; i < capa.length; i++) {
            for (int j = 0; j < capa[i].length; j++) {
                double tNegativo = capa[i][j] * -1;
                double denominador = 1 + (Math.exp(tNegativo));
                resultado[i][j] = 1 / denominador;
            }
        }
        return resultado;
    }
    
    private void crearDatos(){
        try{
            String linea;
            FileReader f = new FileReader(new File("agaricus-lepiota.data"));
            BufferedReader b = new BufferedReader(f);
            int i =0;
            while((linea = b.readLine())!=null) {
                linea = linea.replace("\t", "");
                linea = linea.trim();
                String[] arregloLinea = linea.split(",");
                int nuevoValor=0;
                for (int j = 0; j < arregloLinea.length; j++) {
                    char c = arregloLinea[j].charAt(0);
                    
                    if(j==0){
                        switch(c){
                            case 'p': nuevoValor  = 0;//Venenoso
                                       break;
                            case 'e': nuevoValor = 1;//Comestible
                        }
                    }
                    else if (j == 1) {
                            switch(c) {
                               case 'b' :
                               nuevoValor = 1;
                               break;
                               case 'c' :
                               nuevoValor = 2;
                               break;
                               case 'x' :
                               nuevoValor = 3;
                               break;
                               case 'f' :
                               nuevoValor = 4;
                               break;
                               case 'k' :
                               nuevoValor = 5;
                               break;
                               case 's' :
                               nuevoValor = 6;
                               break;
                            }
                         } else if (j == 2) {   
                            switch(c) {
                               case 'f' :
                               nuevoValor = 1;
                               break;
                               case 'g' :
                               nuevoValor = 2;
                               break;
                               case 'y' :
                               nuevoValor = 3;
                               break;
                               case 's' :
                               nuevoValor = 4;
                               break;
                            }
                         } else if (j == 3) {   
                            switch(c) {
                               case 'n' :
                               nuevoValor = 1;
                               break;
                               case 'b' :
                               nuevoValor = 2;
                               break;
                               case 'c' :
                               nuevoValor = 3;
                               break;
                               case 'g' :
                               nuevoValor = 4;
                               break;
                               case 'r' :
                               nuevoValor = 5;
                               break;
                               case 'p' :
                               nuevoValor = 6;
                               break;
                               case 'u' :
                               nuevoValor = 7;
                               break;
                               case 'e' :
                               nuevoValor = 8;
                               break;
                               case 'w' :
                               nuevoValor = 9;
                               break;
                               case 'y' :
                               nuevoValor = 10;                         
                               break;
                            }   
                         } else if (j == 4) {   
                            switch(c) {
                               case 't' :
                               nuevoValor = 1;
                               break;
                               case 'f' :
                               nuevoValor = 2;
                               break;
                            }
                         } else if (j == 5) {   
                            switch(c) {
                               case 'a' :
                               nuevoValor = 1;
                               break;
                               case 'l' :
                               nuevoValor = 2;
                               break;
                               case 'c' :
                               nuevoValor = 3;
                               break;
                               case 'y' :
                               nuevoValor = 4;
                               break;                        
                               case 'f' :
                               nuevoValor = 5;
                               break;
                               case 'm' :
                               nuevoValor = 6;
                               break;
                               case 'n' :
                               nuevoValor = 7;
                               break;
                               case 'p' :
                               nuevoValor = 8;
                               break;
                               case 's' :
                               nuevoValor = 9;
                               break;
                            }                        
                         } else if (j == 6) {   
                            switch(c) {
                               case 'a' :
                               nuevoValor = 1;
                               break;
                               case 'd' :
                               nuevoValor = 2;
                               break;
                               case 'f' :
                               nuevoValor = 3;
                               break;
                               case 'n' :
                               nuevoValor = 4;
                               break;
                            }
                         } else if (j == 7) {   
                            switch(c) {
                               case 'c' :
                               nuevoValor = 1;
                               break;
                               case 'w' :
                               nuevoValor = 2;
                               break;
                               case 'd' :
                               nuevoValor = 3;
                               break;
                            }
                         } else if (j == 8) {   
                            switch(c) {
                               case 'b' :
                               nuevoValor = 1;
                               break;
                               case 'n' :
                               nuevoValor = 2;
                               break;
                            }
                         } else if (j == 9) {   
                            switch(c) {
                               case 'k' :
                               nuevoValor = 1;
                               break;
                               case 'n' :
                               nuevoValor = 2;
                               break;
                               case 'b' :
                               nuevoValor = 3;
                               break;
                               case 'h' :
                               nuevoValor = 4; 
                               break;                       
                               case 'g' :
                               nuevoValor = 5;
                               break;
                               case 'r' :
                               nuevoValor = 6;
                               break;
                               case 'o' :
                               nuevoValor = 7;
                               break;
                               case 'p' :
                               nuevoValor = 8;
                               break;
                               case 'u' :
                               nuevoValor = 9;
                               break;
                               case 'e' :
                               nuevoValor = 10;
                               break;
                               case 'w' :
                               nuevoValor = 11;
                               break;
                               case 'y' :
                               nuevoValor = 12;                          
                               break;
                            }                                                                        
                         } else if (j == 10) {   
                            switch(c) {
                               case 'e' :
                               nuevoValor = 1;
                               break;
                               case 't' :
                               nuevoValor = 2;
                               break;
                            }
                         } else if (j == 11) {   
                            switch(c) {
                               case 'b' :
                               nuevoValor = 1;
                               break;
                               case 'c' :
                               nuevoValor = 2;
                               break;
                               case 'u' :
                               nuevoValor = 3;
                               break;
                               case 'e' :
                               nuevoValor = 4;  
                               break;                      
                               case 'z' :
                               nuevoValor = 5;
                               break;
                               case 'r' :
                               nuevoValor = 6;
                               break;
                               case '?' :
                               nuevoValor = 7;                         
                               break;
                            }
                         } else if (j == 12) {   
                            switch(c) {
                               case 'f' :
                               nuevoValor = 1;
                               break;
                               case 'y' :
                               nuevoValor = 2;
                               break;
                               case 'k' :
                               nuevoValor = 3;
                               break;
                               case 's' :
                               nuevoValor = 4;                                               
                               break;
                            }                        
                         } else if (j == 13) {   
                            switch(c) {
                               case 'f' :
                               nuevoValor = 1;
                               break;
                               case 'y' :
                               nuevoValor = 2;
                               break;
                               case 'k' :
                               nuevoValor = 3;
                               break;
                               case 's' :
                               nuevoValor = 4;                                               
                               break;
                            }   
                         } else if (j == 14) {   
                            switch(c) {
                               case 'n' :
                               nuevoValor = 1;
                               break;
                               case 'b' :
                               nuevoValor = 2;
                               case 'c' :
                               nuevoValor = 3;
                               break;
                               case 'g' :
                               nuevoValor = 4;
                               break;                        
                               case 'o' :
                               nuevoValor = 5;
                               break;
                               case 'p' :
                               nuevoValor = 6;
                               break;
                               case 'e' :
                               nuevoValor = 7;
                               break;
                               case 'w' :
                               nuevoValor = 8;
                               break;
                               case 'y' :
                               nuevoValor = 9;                        
                               break;
                            }
                         } else if (j == 15) {   
                            switch(c) {
                               case 'n' :
                               nuevoValor = 1;
                               break;
                               case 'b' :
                               nuevoValor = 2;
                               break;
                               case 'c' :
                               nuevoValor = 3;
                               break;
                               case 'g' :
                               nuevoValor = 4; 
                               break;                       
                               case 'o' :
                               nuevoValor = 5;
                               break;
                               case 'p' :
                               nuevoValor = 6;
                               break;
                               case 'e' :
                               nuevoValor = 7;
                               break;
                               case 'w' :
                               nuevoValor = 8;
                               break;
                               case 'y' :
                               nuevoValor = 9;                        
                               break;
                            }                        
                         } else if (j == 16) {   
                            switch(c) {
                               case 'p' :
                               nuevoValor = 1;
                               break;
                               case 'u' :
                               nuevoValor = 2;                                            
                               break;
                            } 
                          } else if (j == 17) {   
                            switch(c) {
                               case 'n' :
                               nuevoValor = 1;
                               break;
                               case 'o' :
                               nuevoValor = 2; 
                               break;  
                               case 'w' :
                               nuevoValor = 3;
                               break;
                               case 'y' :
                               nuevoValor = 4;                                          
                               break;
                            }                        
                          } else if (j == 18) {   
                            switch(c) {
                               case 'n' :
                               nuevoValor = 1;
                               break;
                               case 'o' :
                               nuevoValor = 2; 
                               break;  
                               case 't' :
                               nuevoValor = 3;                                       
                               break;
                            }                        
                         } else if (j == 19) {     
                            switch(c) {
                               case 'c' :
                               nuevoValor = 1;
                               break;
                               case 'e' :
                               nuevoValor = 2;
                               break;
                               case 'f' :
                               nuevoValor = 3;
                               break;
                               case 'l' :
                               nuevoValor = 4;  
                               break;                      
                               case 'n' :
                               nuevoValor = 5;
                               break;
                               case 'p' :
                               nuevoValor = 6;
                               break;
                               case 's' :
                               nuevoValor = 7;
                               break;
                               case 'z' :
                               nuevoValor = 8;                     
                               break;
                            }  
                         } else if (j == 20) {     
                            switch(c) {
                               case 'k' :
                               nuevoValor = 1;
                               break;
                               case 'n' :
                               nuevoValor = 2;
                               break;
                               case 'b' :
                               nuevoValor = 3;
                               break;
                               case 'h' :
                               nuevoValor = 4; 
                               break;                       
                               case 'r' :
                               nuevoValor = 5;
                               break;
                               case 'o' :
                               nuevoValor = 6;
                               break;
                               case 'u' :
                               nuevoValor = 7;
                               break;
                               case 'w' :
                               nuevoValor = 8; 
                               break;  
                               case 'y' :
                               nuevoValor = 9;                                              
                               break;
                            }
                         } else if (j == 21) {     
                            switch(c) {
                               case 'a' :
                               nuevoValor = 1;
                               break;
                               case 'c' :
                               nuevoValor = 2;
                               break;
                               case 'n' :
                               nuevoValor = 3;
                               break;
                               case 's' :
                               nuevoValor = 4;   
                               break;                     
                               case 'v' :
                               nuevoValor = 5;
                               break;
                               case 'y' :
                               nuevoValor = 6;                                             
                               break;
                            }                                                
                         } else if (j == 22) {     
                            switch(c) {
                               case 'g' :
                               nuevoValor = 1;
                               break;
                               case 'l' :
                               nuevoValor = 2;
                               break;
                               case 'm' :
                               nuevoValor = 3;
                               break;
                               case 'p' :
                               nuevoValor = 4; 
                               break;                       
                               case 'u' :
                               nuevoValor = 5;
                               break;
                               case 'w' :
                               nuevoValor = 6;  
                               break;  
                               case 'd' :
                               nuevoValor = 7;                                          
                               break;
                            }                                                                         
                         }                                                                    
                         data[i][j] = nuevoValor;             
                  }
                  i++;
              }
        } catch (FileNotFoundException e) {
              e.printStackTrace();
        } catch (IOException e) {
              e.printStackTrace();
        } 
                    
    }
}
