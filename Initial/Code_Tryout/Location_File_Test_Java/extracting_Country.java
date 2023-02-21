//import java.lang.*;
import java.io.*;



public class extracting_Country {
    public static void main(String[] args) throws Exception {
        
        try {
            
            BufferedReader reader = new BufferedReader(new FileReader("freq_Files.resolved.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("country_extract.txt"));
            
            


            reader.close();
            writer.close();
            
        } catch (IOException e) {
            System.out.println("File not dound");
        }
    }
    
}
