import java.io.*;
//import java.lang.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.commons.io.FileUtils;

public class filelowerCase {
  public static void main(String[] args) throws IOException{
      try {
          File path = new File("/home/dipp/Code_Tryout/Location_File_Test_Java/Original_Files/");
          
          File [] listOfFiles = path.listFiles();

          for(int i = 0; i<listOfFiles.length; i++){
              File file = listOfFiles[i];
              if(file.isFile() && file.getName().endsWith(".txt")){
                FileReader reader = new FileReader(file);

                Path FileToPath = file.toPath();
                String FileName = FileToPath.getFileName().toString();
                String files = removeExtension(FileName);

                PrintWriter writer = new PrintWriter(files + "_lower.txt");


                //FileWriter writer = new FileWriter(file + "_lower.txt", true);
                int character =' ';
                char m;
                while((character=reader.read())!=-1)
                {
                    m = Character.toLowerCase((char)character);
                    System.out.println(m);
                    writer.write(m);
                }

                
                reader.close();
                writer.close();

              }

        }

      } catch (IOException e) {
          
          System.out.println("File Not found");
      }




      try {
        File source = new File("/home/dipp/Code_Tryout/Location_File_Test_Java/");
        File dest = new File("/home/dipp/Code_Tryout/Location_File_Test_Java/Original_Files_lower/");

        FileUtils.copyDirectory(source, dest, new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                boolean source = pathname.getName().toLowerCase().endsWith("_lower.txt");

                if (source) {
                    pathname.deleteOnExit();
                    return true;
                }
                return false;
            }

        });


      } catch (IOException e) {
            e.printStackTrace();
      }

  }


  public static String removeExtension(String fileName) {


    int extPos = fileName.lastIndexOf(".");
    if(extPos == -1) {
      return fileName;
    }
    else {
      return fileName.substring(0, extPos);
    }



    //String [] filename_split_array = fileName.split(".");
    //System.out.println(filename_split_array[0]);
    
    //String [] filename_split_array_further = filename_split_array[0].split(".");
    //System.out.println(filename_split_array_further[0]);
    //return filename_split_array[0];



}
}
