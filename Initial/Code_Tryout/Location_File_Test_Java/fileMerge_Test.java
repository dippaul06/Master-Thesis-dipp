import java.io.*;
//import java.lang.*;
//import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;

public class fileMerge_Test {
    public static void main(String[] args) throws IOException {
        
        try {

            File lowerFilePath = new File("/home/dipp/Code_Tryout/Location_File_Test_Java/Original_Files_lower/");
            File resolvedFilePath = new File("/home/dipp/Code_Tryout/Location_File_Test_Java/Original_Files_Resolved/");

            File [] listOfLowerFiles = lowerFilePath.listFiles();
            File [] listOfResolvedFiles = resolvedFilePath.listFiles();

            for (int i = 0; i<listOfLowerFiles.length; i++){
                File lowerFile = listOfLowerFiles[i];

                for (int j = 0; j<listOfResolvedFiles.length; j++){
                    File resolvedFile = listOfResolvedFiles[j];

                    Path lowerFileToPath = lowerFile.toPath();
                    Path resolvedFileToPath = resolvedFile.toPath();

                    String lowerFileName = lowerFileToPath.getFileName().toString();
                    String resolvedFileName = resolvedFileToPath.getFileName().toString();

                    //System.out.println(lowerFileName);
                    //System.out.println(resolvedFileName);

                    //if(resolvedFileName.startsWith(removeExtension(lowerFileName))){
                        //System.out.println(removeExtension_(lowerFileName));
                        String low = removeExtension_(lowerFileName);
                        String res = removeExtensiondot(removeExtensiondot(resolvedFileName));

                        //System.out.println(low);
                        //System.out.println(res);
                        //System.out.println(removeExtensiondot(removeExtensiondot(resolvedFileName)));
                    //if(removeExtension_(lowerFileName) == removeExtensiondot(removeExtensiondot(resolvedFileName))){
                        System.out.println(low.equals(res));

                    if(low.equals(res)){

                        //System.out.println(low);
                        //System.out.println(res);

                        BufferedReader br1 = new BufferedReader(new FileReader(lowerFile));
                        BufferedReader br2 = new BufferedReader(new FileReader(resolvedFile));

                        PrintWriter pw = new PrintWriter(res + "_Merge.txt");

                        String line1 = br1.readLine();
                        String line2 = br2.readLine();

                        while (line1 != null || line2 !=null) 
                        { 
                        if(line1 != null) 
                        { 
                            pw.println(line1); 
                            line1 = br1.readLine(); 
                        } 
                        
                        if(line2 != null) 
                        { 
                            pw.println(line2); 
                            line2 = br2.readLine(); 
                        } 
                        } 

                            pw.flush();
                            br1.close();
                            br2.close();
                            pw.close();

                    }


                    

                    
                }

            }

        } catch (IOException e) {
            System.out.println("Error");
        }


  
        try {
            File source = new File("/home/dipp/Code_Tryout/Location_File_Test_Java/");
            File dest = new File("/home/dipp/Code_Tryout/Location_File_Test_Java/Files_Merged/");
    
            FileUtils.copyDirectory(source, dest, new FileFilter() {
    
                @Override
                public boolean accept(File pathname) {
                    boolean source = pathname.getName().toLowerCase().endsWith("_merge.txt");
    
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



    public static String removeExtension_(String fileName) {

        int extPos = fileName.lastIndexOf("_");
        if(extPos == -1) {
            return fileName;
        }
        else {
            return fileName.substring(0, extPos);
        }


    }


    public static String removeExtensiondot(String fileName) {

        int extPos = fileName.lastIndexOf(".");
        if(extPos == -1) {
            return fileName;
        }
        else {
            return fileName.substring(0, extPos);
        }

    }



    


    



}
