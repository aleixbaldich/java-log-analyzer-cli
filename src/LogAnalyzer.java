import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    public static Map<String, Integer> countFailedLoginsByIp(String filePath){
        Map<String,Integer> failedLoginsByIp = new HashMap<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = reader.readLine()) != null){
                if(line.contains("Failed login")){
                    String ip = line.substring(line.lastIndexOf("from ")+5);
                    failedLoginsByIp.put(ip, failedLoginsByIp.getOrDefault(ip, 0)+1);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }


        return failedLoginsByIp;

    }

    public static Map<String, Integer> findSuspiciousIp(Map<String,Integer> failedLoginsByIp, int threshold){
        Map<String,Integer> suspiciousIps = new HashMap<>();
        

        for(Map.Entry<String,Integer> entry : failedLoginsByIp.entrySet()){
            if(entry.getValue()>= threshold){
                suspiciousIps.put(entry.getKey(), entry.getValue());
            }
        }
        return suspiciousIps;
    }



    public static void main(String[] args) {
        String filePath = "sample_logs/auth.log";
        int totalFailedLogins = countFailedLogins(filePath);

        Map<String, Integer> failedLoginsByIp = countFailedLoginsByIp(filePath);
        Map<String, Integer> suspiciousIps = findSuspiciousIp(failedLoginsByIp, 3);

        System.out.println("Failed login attempts: " + totalFailedLogins);
        System.out.println("================================");
        System.out.println("");

        System.out.println("Failed login attempts by IP:");
        for (Map.Entry<String, Integer> entry : failedLoginsByIp.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        System.out.println("================================");
        System.out.println("");
        System.out.println("Suspicious IPs (3 or more attempts):");
        for(Map.Entry<String,Integer> entry : suspiciousIps.entrySet()){
            System.out.println(entry.getKey() + " IS A SUSPICIOUS IP");
        }
    }   

}
