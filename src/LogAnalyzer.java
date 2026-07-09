import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LogAnalyzer {

    public static int countFailedLogins(String filePath){
        int failedLogins = 0;

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;

            while((line = reader.readLine())!= null){
                if(line.contains("Failed login")){
                    failedLogins++;
                }
            }
        }catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
         return failedLogins;    
    }



    public static void main(String[] args) {
        String filePath = "sample_logs/auth.log";
        int totalFailedLogins = countFailedLogins(filePath);

        System.out.println("Failed login attempts: " + totalFailedLogins);
    }

}
