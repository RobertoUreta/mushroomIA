/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mushroom;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
/**
 *
 * @author Roberto Ureta
 */
public class Mushroom {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            String linea;
            FileReader f = new FileReader(new File("test.txt"));
            BufferedReader b = new BufferedReader(f);
            while((linea = b.readLine())!=null) {
                System.out.println(linea);
                linea = linea.replace("\t", "");
                linea = linea.trim();
                String[] arregloLinea = linea.split(",");
            }

        }catch(Exception e){
            System.out.println("Error al abrir el archivo");
        }
    }
    
}
