/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HandleData;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;


/**
 *
 * @author eirini
 */
public class DataParser {
    
    private static void doReadWriteTextFile() {

        try {
        
            // input/output file names
            String inputFileName  = "Home/Desktop/LightServer/N-acc-07-16";
            

            // Create FileReader Object
            FileReader inputFileReader   = new FileReader(inputFileName);
           

            // Create Buffered/PrintWriter Objects
            BufferedReader inputStream   = new BufferedReader(inputFileReader);
          

            // Keep in mind that all of the above statements can be combined
            // into the following:
            //BufferedReader inputStream = new BufferedReader(new FileReader("README_InputFile.txt"));
            //PrintWriter outputStream   = new PrintWriter(new FileWriter("ReadWriteTextFile.out"));

           

            String inLine = null;

            while ((inLine = inputStream.readLine()) != null) {
               System.out.print(inLine);
            }

            
            inputStream.close();

        } catch (IOException e) {

            System.out.println("IOException:");
            e.printStackTrace();

        }

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        doReadWriteTextFile();
    }



}
