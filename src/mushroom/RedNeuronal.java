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
    int totalDataEntrenamiento = 6093;
    int totalDataValidacion=812;
    double tasaAprendizaje = 0.01;
    double momentum = 0.5;
    int totalDataPrueba = totalData - totalDataEntrenamiento - totalDataValidacion;//1219
    double[][] data = new double[totalData][totalAtributos];//todos los datos codificados
    double[][] entrenamiento = new double[totalDataEntrenamiento][totalAtributos];//75% de los datos
    double[][] prueba = new double[totalDataPrueba][totalAtributos];//15 % de los datos
    double[][] validacion = new double[totalDataValidacion][totalAtributos];//10 % de los datos
    //Pesos de RedNeuronal
    double[][] pesos1 = new double[22][15]; //22 neuronas de entrada que se conectan a 5 neuronas.
    double[][] pesos1_1 = new double[15][10];
    double[][] pesos1_2 = new double[10][5];
    double[][] pesos2 = new double[5][1]; // 5 neuronas de la capa oculta se conectan a la neurona de salida.
    double porcentajeEntrenamiento = 0 ;
    
    public RedNeuronal() {
        crearDatos();
        List<Integer> lista = crearListaDesordenada();
        boolean train;
        do {
            elegirDataEntrenamiento(lista);
            elegirDataPrueba(lista);
            elegirDataValidacion(lista);
            //this.imprimirData();
            train = comprobarBalanceDatos(entrenamiento);
            if (!train) {
                Collections.shuffle(lista);
            }
        } while (!train);
        this.calcularPesosCapas();
        this.entrenamiento();
    }
    
    private boolean comprobarBalanceDatos(double[][] datos){
        double countVenenoso = 0;
        double countComestible = 0;
        for(int i = 0; i<datos.length; i++){
            if(datos[i][0]==1){
                countComestible++;
            }
            else{
                countVenenoso++;
            }
        }
        System.out.println("Comestible: "+countComestible);
        System.out.println("Venenoso: "+countVenenoso);
        double balance = countComestible/countVenenoso;
        if(balance>1.08){
            System.out.println("Balance: "+balance);
            return true;
        }
        System.out.println("Balance: "+balance);
        return false;
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
     * la cual corresponde al 75% de la data total,
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
        for (int i = totalDataEntrenamiento; i < (totalData - totalDataValidacion); i++) {
            int numRandom = lista.get(i);
            for (int j = 0; j < totalAtributos; j++) {
                prueba[fila][j] = data[numRandom][j];
            }
            fila++;
        }
    }
    /**
     * Se elige la data usada para la validacion,
     * la cual corresponde al 10% de la data total,
     * para esto se usa la lista con los numeros desordenados.
     * @param lista ArrayList con los numeros desordenados.
     */
    public void elegirDataValidacion(List<Integer> lista){
        int fila = 0;
        for (int i = totalDataEntrenamiento + totalDataPrueba; i < totalData; i++) {
            int numRandom = lista.get(i);
            for (int j = 0; j < totalAtributos; j++) {
                validacion[fila][j] = data[numRandom][j];
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
        
        for(int i = 0; i<pesos1_1.length; i++){
            for(int j = 0; j<pesos1_1[i].length; j++){
                pesos1_1[i][j]=this.obtenerPesoAleatorio();
            }
        }
        
        for(int i = 0; i<pesos1_2.length; i++){
            for(int j = 0; j<pesos1_2[i].length; j++){
                pesos1_2[i][j]=this.obtenerPesoAleatorio();
            }
        }
        
        for(int i = 0; i<pesos2.length; i++){
            for(int j = 0; j<pesos2[i].length; j++){
                pesos2[i][j]=this.obtenerPesoAleatorio();
            }
        }
        
        System.out.println("Pesos de la capa 1: " + Arrays.deepToString(pesos1));
        System.out.println("Pesos de la capa 1_1: " + Arrays.deepToString(pesos1_1));
        System.out.println("Pesos de la capa 1_2: " + Arrays.deepToString(pesos1_2));
        System.out.println("Pesos de la capa 2: " + Arrays.deepToString(pesos2));
    }
    
    /**
     * Metodo  utilizado para obtener un pesos aleatorio para la arista que conecta una neurona a otra.
     * @return peso double
     */
    private double obtenerPesoAleatorio(){
        double peso = 0;
        Random rnd = new Random();
        int numero = rnd.nextInt(10)+1;
        int op = rnd.nextInt(2);
        if(op==1){
            numero=numero*-1;
        }
        peso = numero/10.0;
        return peso;
    }        
    
    private double[][] multiplicacionMatrices(double[][] hongo, double[][] peso){
        
        double[][] resultado = new double[hongo.length][peso[0].length];
        
        for(int i = 0; i<hongo.length; i++){
            for(int j = 0; j<peso[0].length; j++){
                for(int k = 0; k<hongo[0].length; k++){
                    resultado[i][j]=resultado[i][j]+(hongo[i][k]*peso[k][j]);
                }
            }
        }
        return resultado;
    }
    
    private void entrenamiento(){
        
        double[][] pesosPrevios1 = new double[22][15]; 
        double[][] pesosPrevios1_1 = new double[15][10]; 
        double[][] pesosPrevios1_2 = new double[10][5]; 
        double[][] pesosPrevios2 = new double[5][1];
        
        // [0]: precision pasada - [1]: precision antespasada 
        double[] precisionesAnteriores = new double[2]; 
        
        for(int epoca =0 ; epoca<2000 ; epoca++){
            double precision = 0;
            for(int k = 0; k<entrenamiento.length; k++){
                double[][]hongo = new double[1][22];//Esta matriz recibira los atributos de un hongo 
                for(int i = 1; i<entrenamiento[k].length; i++){
                    hongo[0][i-1]=entrenamiento[k][i];
                }

                double[][] resultado1 = multiplicacionMatrices(hongo, pesos1);
                double[][] nuevoResultado1 = this.funcionSigmoide(resultado1);
                
                double[][] resultado1_1 = multiplicacionMatrices(nuevoResultado1, pesos1_1);
                double[][] nuevoResultado1_1 = this.funcionSigmoide(resultado1_1);
                
                double[][] resultado1_2 = multiplicacionMatrices(nuevoResultado1_1, pesos1_2);
                double[][] nuevoResultado1_2 = this.funcionSigmoide(resultado1_2);

                double[][] resultado2 = multiplicacionMatrices(nuevoResultado1_2, pesos2);
                double[][] nuevoResultado2 = this.funcionSigmoide(resultado2);

                double valorDeseado = entrenamiento[k][0];
                //System.out.println("Precision: "+precision);
                if( ((nuevoResultado2[0][0] > .5) && (valorDeseado > .5)) || ((nuevoResultado2[0][0] <= .5) && (valorDeseado <= .5)) ){
                    precision++;
                }
                else{
                    //llamar a backpropagation
                    double gradienteSalida = this.gradienteEstocasticoDescendenteSalida(valorDeseado, nuevoResultado2[0][0]);
                    double[] gradienteSalida1 = new double[1];
                    gradienteSalida1[0] = gradienteSalida;

                    for(int col = 0; col<pesos2[0].length; col++){
                        for(int fil = 0; fil<pesos2.length; fil++){
                           pesos2[fil][col]=this.actulizarPesos(pesos2[fil][col], gradienteSalida, nuevoResultado2[0][col], pesosPrevios2, fil, col);
                        }
                    }
                    
                    double[] gradienteOculto1_2 = new double[resultado1_2[0].length];
                    for(int col = 0; col<resultado1_2[0].length; col++){
                        double nuevaGradiente = gradienteEstocasticoDescendienteOculto(
                                nuevoResultado1_2[0][col], gradienteSalida1, pesos2[col]);
                        gradienteOculto1_2[col] = nuevaGradiente;    
                    }

                    for(int col = 0; col<pesos1_2[0].length; col++){
                        for(int fil = 0; fil<pesos1_2.length; fil++){
                           pesos1_2[fil][col]=this.actulizarPesos(pesos1_2[fil][col], gradienteOculto1_2[col], nuevoResultado1_2[0][col], pesosPrevios1_2, fil, col);
                        }
                    }
                    
                    double[] gradienteOculto1_1 = new double[resultado1_1[0].length];
                    for(int col = 0; col<resultado1_1[0].length; col++){
                        double nuevaGradiente = gradienteEstocasticoDescendienteOculto(
                                nuevoResultado1_1[0][col], gradienteOculto1_2, pesos1_2[col]);
                        gradienteOculto1_1[col] = nuevaGradiente;    
                    }

                    for(int col = 0; col<pesos1_1[0].length; col++){
                        for(int fil = 0; fil<pesos1_1.length; fil++){
                           pesos1_1[fil][col]=this.actulizarPesos(pesos1_1[fil][col], gradienteOculto1_1[col], nuevoResultado1_1[0][col], pesosPrevios1_1, fil, col);
                        }
                    }

                    double[] gradienteOculto = new double[resultado1[0].length];
                    for(int col = 0; col<resultado1[0].length; col++){
                        double nuevaGradiente = gradienteEstocasticoDescendienteOculto(
                                nuevoResultado1[0][col], gradienteOculto1_1, pesos1_1[col]);
                        gradienteOculto[col] = nuevaGradiente;    
                    }
                    
                    for(int col = 0; col<pesos1[0].length; col++){
                        for(int fil = 0; fil<pesos1.length; fil++){
                           pesos1[fil][col]=this.actulizarPesos(pesos1[fil][col], gradienteOculto[col], nuevoResultado1[0][col], pesosPrevios1, fil, col);
                        }
                    }
                }
            }
            System.out.println("Precision Epoca "+ (epoca+1)+": "+precision);
            if(epoca%50==0){
                double porcentaje2 = (precision/totalDataEntrenamiento)*100;
                imprimirPesos();
                System.out.println("Porcentaje Aciertos Entrenamiento: "+porcentaje2+"%");
                porcentajeEntrenamiento = porcentaje2;
            }
            
            if(precisionesAnteriores[0] == precisionesAnteriores[1] && precision == precisionesAnteriores[0]){
                break;
            }
            else{
                precisionesAnteriores[0] = precisionesAnteriores[1];
                precisionesAnteriores[1] = precision;
            }
            if(epoca%250==0){
                double porcentajeAciertos = this.ejecutarPrueba();
                System.out.println("Porcentaje de Aciertos en Pruebas: "+ porcentajeAciertos+"%");
                if(porcentajeAciertos>75){
                    break;
               }
            }
            
            
        }
        System.out.println("******** PESOS FINALES ********");
        this.imprimirPesos();
   
         System.out.println("******** PORCENTAJES DE ACIERTOS ********");
         
         System.out.println("Porcentaje de Aciertos en Entrenamiento: "+ porcentajeEntrenamiento +"%");
         
        double porcentajeAciertos = this.ejecutarPrueba();
        System.out.println("Porcentaje de Aciertos en Pruebas: "+ porcentajeAciertos+"%");
        
        double porcentajeAciertosValidacion = this.ejecutarValidacion();
        System.out.println("Porcentaje de Aciertos en Validacion: "+ porcentajeAciertosValidacion+"%");
    }
    
    private void imprimirPesos(){
        System.out.println("******** PESOS CAPA 4 ********");
        for (int i = 0; i < pesos2.length; i++) {
            for (int j = 0; j < pesos2[0].length; j++) {
                System.out.println("w"+i+": "+pesos2[i][j]);
                
            }
            
        }
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
    
    /**
     * Gradiente estocastico para capas ocultas.
     * @param valorAntiguo resultado para el atributo de la capa actual.
     * @param gradientesPrevios valores del gradiente de capa previa.
     * @param pesosPrevios pesos de capa previa.
     * @return nuevo valor de gradiente para capa actual. 
     */
    public double gradienteEstocasticoDescendienteOculto(double valorAntiguo, double[] gradientesPrevios, double[] pesosPrevios){
        //suma de pesos previos + gradientes previos
        double sumaPG = 0;
        for (int i = 0; i < pesosPrevios.length; i++) {
            sumaPG += pesosPrevios[i] * gradientesPrevios[i];
        }
        double nuevoGradiente = valorAntiguo * sumaPG * (1 - valorAntiguo);
        return nuevoGradiente;
    }
    
    
    /**
     * Metodo que ejecuta la data de prueba para la red neuronal.
     * @return procentaje de aciertos que tiene la red neuronal para la data de prueba.
     */
    public double ejecutarPrueba(){
        double precision = 0;
        double porcentaje = 0;
        for (int fil = 0; fil < totalDataPrueba; fil++) {

            double[][] hongo = new double[1][22];
            for (int col = 1; col < prueba[fil].length; col++) {
                hongo[0][col - 1] = prueba[fil][col];
            }

            double[][] resultado1 = multiplicacionMatrices(hongo, pesos1);
            double[][] nuevoResultado1 = this.funcionSigmoide(resultado1);

            double[][] resultado1_1 = multiplicacionMatrices(nuevoResultado1, pesos1_1);
            double[][] nuevoResultado1_1 = this.funcionSigmoide(resultado1_1);

            double[][] resultado1_2 = multiplicacionMatrices(nuevoResultado1_1, pesos1_2);
            double[][] nuevoResultado1_2 = this.funcionSigmoide(resultado1_2);

            double[][] resultado2 = multiplicacionMatrices(nuevoResultado1_2, pesos2);
            double[][] nuevoResultado2 = this.funcionSigmoide(resultado2);
            
            double valorDeseado = prueba[fil][0];

            if (((nuevoResultado2[0][0] > .5) && (valorDeseado > .5))
                || ((nuevoResultado2[0][0] <= .5) && (valorDeseado <= .5))) {
                precision++;
            }
        }
        porcentaje = (precision/totalDataPrueba)*100;
        return porcentaje;
    }
    
    /**
     * Metodo que ejecuta la data de validacion para la red neuronal.
     * @return procentaje de aciertos que tiene la red neuronal para la data de validacion.
     */
    public double ejecutarValidacion(){
        double precision = 0;
        double porcentaje = 0;
        for (int fil = 0; fil < totalDataValidacion; fil++) {

            double[][] hongo = new double[1][22];
            for (int col = 1; col < validacion[fil].length; col++) {
                hongo[0][col - 1] = validacion[fil][col];
            }

            double[][] resultado1 = multiplicacionMatrices(hongo, pesos1);
            double[][] nuevoResultado1 = this.funcionSigmoide(resultado1);

            double[][] resultado1_1 = multiplicacionMatrices(nuevoResultado1, pesos1_1);
            double[][] nuevoResultado1_1 = this.funcionSigmoide(resultado1_1);

            double[][] resultado1_2 = multiplicacionMatrices(nuevoResultado1_1, pesos1_2);
            double[][] nuevoResultado1_2 = this.funcionSigmoide(resultado1_2);

            double[][] resultado2 = multiplicacionMatrices(nuevoResultado1_2, pesos2);
            double[][] nuevoResultado2 = this.funcionSigmoide(resultado2);
            
            double valorDeseado = validacion[fil][0];

            if (((nuevoResultado2[0][0] > .5) && (valorDeseado > .5))
                || ((nuevoResultado2[0][0] <= .5) && (valorDeseado <= .5))) {
                precision++;
            }
        }
        porcentaje = (precision/totalDataValidacion)*100;
        return porcentaje;
    }
    
    
    /**
     * 
     * @param valorEsperado valor esperado por cada hongo que se procesa para entrenar.
     * @param salida valor entregado por la neurona de salida de la red neuronal.
     * @return gradiente. Retorna el gradiente estocastico calculado sobre
     * el valor entregado por la neurona de salida despues del proceso
     * de propagacion
     */
    private double gradienteEstocasticoDescendenteSalida(double valorEsperado, double salida){
        double gradiente;
        gradiente = (valorEsperado-salida)*(1-salida)*salida;
        return gradiente;
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
                double nuevoValor=0;
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
                               nuevoValor = 0.1;
                               break;
                               case 'c' :
                               nuevoValor = 0.2;
                               break;
                               case 'x' :
                               nuevoValor = 0.3;
                               break;
                               case 'f' :
                               nuevoValor = 0.4;
                               break;
                               case 'k' :
                               nuevoValor = 0.5;
                               break;
                               case 's' :
                               nuevoValor = 0.6;
                               break;
                            }
                         } else if (j == 2) {   
                            switch(c) {
                               case 'f' :
                               nuevoValor = 0.1;
                               break;
                               case 'g' :
                               nuevoValor = 0.2;
                               break;
                               case 'y' :
                               nuevoValor = 0.3;
                               break;
                               case 's' :
                               nuevoValor = 0.4;
                               break;
                            }
                         } else if (j == 3) {   
                            switch(c) {
                               case 'n' :
                               nuevoValor = 0.1;
                               break;
                               case 'b' :
                               nuevoValor = 0.2;
                               break;
                               case 'c' :
                               nuevoValor = 0.3;
                               break;
                               case 'g' :
                               nuevoValor = 0.4;
                               break;
                               case 'r' :
                               nuevoValor = 0.5;
                               break;
                               case 'p' :
                               nuevoValor = 0.6;
                               break;
                               case 'u' :
                               nuevoValor = 0.7;
                               break;
                               case 'e' :
                               nuevoValor = 0.8;
                               break;
                               case 'w' :
                               nuevoValor = 0.9;
                               break;
                               case 'y' :
                               nuevoValor = 1.0;                         
                               break;
                            }   
                         } else if (j == 4) {   
                            switch(c) {
                               case 't' :
                               nuevoValor = 0.1;
                               break;
                               case 'f' :
                               nuevoValor = 0.2;
                               break;
                            }
                         } else if (j == 5) {   
                            switch(c) {
                               case 'a' :
                               nuevoValor = 0.1;
                               break;
                               case 'l' :
                               nuevoValor = 0.2;
                               break;
                               case 'c' :
                               nuevoValor = 0.3;
                               break;
                               case 'y' :
                               nuevoValor = 0.4;
                               break;                        
                               case 'f' :
                               nuevoValor = 0.5;
                               break;
                               case 'm' :
                               nuevoValor = 0.6;
                               break;
                               case 'n' :
                               nuevoValor = 0.7;
                               break;
                               case 'p' :
                               nuevoValor = 0.8;
                               break;
                               case 's' :
                               nuevoValor = 0.9;
                               break;
                            }                        
                         } else if (j == 6) {   
                            switch(c) {
                               case 'a' :
                               nuevoValor = 0.1;
                               break;
                               case 'd' :
                               nuevoValor = 0.2;
                               break;
                               case 'f' :
                               nuevoValor = 0.3;
                               break;
                               case 'n' :
                               nuevoValor = 0.4;
                               break;
                            }
                         } else if (j == 7) {   
                            switch(c) {
                               case 'c' :
                               nuevoValor = 0.1;
                               break;
                               case 'w' :
                               nuevoValor = 0.2;
                               break;
                               case 'd' :
                               nuevoValor = 0.3;
                               break;
                            }
                         } else if (j == 8) {   
                            switch(c) {
                               case 'b' :
                               nuevoValor = 0.1;
                               break;
                               case 'n' :
                               nuevoValor = 0.2;
                               break;
                            }
                         } else if (j == 9) {   
                            switch(c) {
                               case 'k' :
                               nuevoValor = 0.05;
                               break;
                               case 'n' :
                               nuevoValor = 0.1;
                               break;
                               case 'b' :
                               nuevoValor = 0.15;
                               break;
                               case 'h' :
                               nuevoValor = 0.2; 
                               break;                       
                               case 'g' :
                               nuevoValor = 0.25;
                               break;
                               case 'r' :
                               nuevoValor = 0.3;
                               break;
                               case 'o' :
                               nuevoValor = 0.35;
                               break;
                               case 'p' :
                               nuevoValor = 0.4;
                               break;
                               case 'u' :
                               nuevoValor = 0.45;
                               break;
                               case 'e' :
                               nuevoValor = 0.5;
                               break;
                               case 'w' :
                               nuevoValor = 0.55;
                               break;
                               case 'y' :
                               nuevoValor = 0.6;                          
                               break;
                            }                                                                        
                         } else if (j == 10) {   
                            switch(c) {
                               case 'e' :
                               nuevoValor = 0.1;
                               break;
                               case 't' :
                               nuevoValor = 0.2;
                               break;
                            }
                         } else if (j == 11) {   
                            switch(c) {
                               case 'b' :
                               nuevoValor = 0.1;
                               break;
                               case 'c' :
                               nuevoValor = 0.2;
                               break;
                               case 'u' :
                               nuevoValor = 0.3;
                               break;
                               case 'e' :
                               nuevoValor = 0.4;  
                               break;                      
                               case 'z' :
                               nuevoValor = 0.5;
                               break;
                               case 'r' :
                               nuevoValor = 0.6;
                               break;
                               case '?' :
                               nuevoValor = 0.7;                         
                               break;
                            }
                         } else if (j == 12) {   
                            switch(c) {
                               case 'f' :
                               nuevoValor = 0.1;
                               break;
                               case 'y' :
                               nuevoValor = 0.2;
                               break;
                               case 'k' :
                               nuevoValor = 0.3;
                               break;
                               case 's' :
                               nuevoValor = 0.4;                                               
                               break;
                            }                        
                         } else if (j == 13) {   
                            switch(c) {
                               case 'f' :
                               nuevoValor = 0.1;
                               break;
                               case 'y' :
                               nuevoValor = 0.2;
                               break;
                               case 'k' :
                               nuevoValor = 0.3;
                               break;
                               case 's' :
                               nuevoValor = 0.4;                                               
                               break;
                            }   
                         } else if (j == 14) {   
                            switch(c) {
                               case 'n' :
                               nuevoValor = 0.1;
                               break;
                               case 'b' :
                               nuevoValor = 0.2;
                               break;
                               case 'c' :
                               nuevoValor = 0.3;
                               break;
                               case 'g' :
                               nuevoValor = 0.4;
                               break;                        
                               case 'o' :
                               nuevoValor = 0.5;
                               break;
                               case 'p' :
                               nuevoValor = 0.6;
                               break;
                               case 'e' :
                               nuevoValor = 0.7;
                               break;
                               case 'w' :
                               nuevoValor = 0.8;
                               break;
                               case 'y' :
                               nuevoValor = 0.9;                        
                               break;
                            }
                         } else if (j == 15) {   
                            switch(c) {
                               case 'n' :
                               nuevoValor = 0.1;
                               break;
                               case 'b' :
                               nuevoValor = 0.2;
                               break;
                               case 'c' :
                               nuevoValor = 0.3;
                               break;
                               case 'g' :
                               nuevoValor = 0.4; 
                               break;                       
                               case 'o' :
                               nuevoValor = 0.5;
                               break;
                               case 'p' :
                               nuevoValor = 0.6;
                               break;
                               case 'e' :
                               nuevoValor = 0.7;
                               break;
                               case 'w' :
                               nuevoValor = 0.8;
                               break;
                               case 'y' :
                               nuevoValor = 0.9;                        
                               break;
                            }                        
                         } else if (j == 16) {   
                            switch(c) {
                               case 'p' :
                               nuevoValor = 0.1;
                               break;
                               case 'u' :
                               nuevoValor = 0.2;                                            
                               break;
                            } 
                          } else if (j == 17) {   
                            switch(c) {
                               case 'n' :
                               nuevoValor = 0.1;
                               break;
                               case 'o' :
                               nuevoValor = 0.2; 
                               break;  
                               case 'w' :
                               nuevoValor = 0.3;
                               break;
                               case 'y' :
                               nuevoValor = 0.4;                                          
                               break;
                            }                        
                          } else if (j == 18) {   
                            switch(c) {
                               case 'n' :
                               nuevoValor = 0.1;
                               break;
                               case 'o' :
                               nuevoValor = 0.2; 
                               break;  
                               case 't' :
                               nuevoValor = 0.3;                                       
                               break;
                            }                        
                         } else if (j == 19) {     
                            switch(c) {
                               case 'c' :
                               nuevoValor = 0.1;
                               break;
                               case 'e' :
                               nuevoValor = 0.2;
                               break;
                               case 'f' :
                               nuevoValor = 0.3;
                               break;
                               case 'l' :
                               nuevoValor = 0.4;  
                               break;                      
                               case 'n' :
                               nuevoValor = 0.5;
                               break;
                               case 'p' :
                               nuevoValor = 0.6;
                               break;
                               case 's' :
                               nuevoValor = 0.7;
                               break;
                               case 'z' :
                               nuevoValor = 0.8;                     
                               break;
                            }  
                         } else if (j == 20) {     
                            switch(c) {
                               case 'k' :
                               nuevoValor = 0.1;
                               break;
                               case 'n' :
                               nuevoValor = 0.2;
                               break;
                               case 'b' :
                               nuevoValor = 0.3;
                               break;
                               case 'h' :
                               nuevoValor = 0.4; 
                               break;                       
                               case 'r' :
                               nuevoValor = 0.5;
                               break;
                               case 'o' :
                               nuevoValor = 0.6;
                               break;
                               case 'u' :
                               nuevoValor = 0.7;
                               break;
                               case 'w' :
                               nuevoValor = 0.8; 
                               break;  
                               case 'y' :
                               nuevoValor = 0.9;                                              
                               break;
                            }
                         } else if (j == 21) {     
                            switch(c) {
                               case 'a' :
                               nuevoValor = 0.1;
                               break;
                               case 'c' :
                               nuevoValor = 0.2;
                               break;
                               case 'n' :
                               nuevoValor = 0.3;
                               break;
                               case 's' :
                               nuevoValor = 0.4;   
                               break;                     
                               case 'v' :
                               nuevoValor = 0.5;
                               break;
                               case 'y' :
                               nuevoValor = 0.6;                                             
                               break;
                            }                                                
                         } else if (j == 22) {     
                            switch(c) {
                               case 'g' :
                               nuevoValor = 0.1;
                               break;
                               case 'l' :
                               nuevoValor = 0.2;
                               break;
                               case 'm' :
                               nuevoValor = 0.3;
                               break;
                               case 'p' :
                               nuevoValor = 0.4; 
                               break;                       
                               case 'u' :
                               nuevoValor = 0.5;
                               break;
                               case 'w' :
                               nuevoValor = 0.6;  
                               break;  
                               case 'd' :
                               nuevoValor = 0.7;                                          
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
    
    public double actulizarPesos(double pesoPrevio, double gradienteAnterior, double gradienteActual, double[][] pesosPrevios
    , int x, int y){
        double nuevoPeso = pesoPrevio + tasaAprendizaje*gradienteActual*gradienteAnterior
                + momentum*pesosPrevios[x][y];
        pesosPrevios[x][y] = tasaAprendizaje*gradienteActual*gradienteAnterior;
        return nuevoPeso;
    }
}
