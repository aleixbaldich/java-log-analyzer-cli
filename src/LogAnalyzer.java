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

    public static void detectAfterHoursLogins(String filePath){
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = reader.readLine()) != null){
                if(line.contains("Successful login")){
                    String time = line.substring(11,19);
                    int hour = Integer.parseInt(time.substring(0,2));

                    if(hour>=20 || hour < 8 ){
                        System.out.println(line);
                    }
                }
            }
        }catch (IOException e) {
        System.out.println("Error reading file: " + e.getMessage());
        }
    }



    public static void main(String[] args) {
        String filePath;
        int threshold;
        
        if(args.length > 0){
            filePath = args[0];
            
        }
        else{
            filePath = "sample_logs/auth.log";
        }

        if(args.length > 1)threshold = Integer.parseInt(args[1]);
        else threshold = 3;

        System.out.println("Analyzing file: " + filePath);

        int totalFailedLogins = countFailedLogins(filePath);

        Map<String, Integer> failedLoginsByIp = countFailedLoginsByIp(filePath);
        Map<String, Integer> suspiciousIps = findSuspiciousIp(failedLoginsByIp, threshold);        

        System.out.println("Failed login attempts: " + totalFailedLogins);
        System.out.println("================================");
        System.out.println("");

        
        System.out.println("Failed login attempts by IP:");
        for (Map.Entry<String, Integer> entry : failedLoginsByIp.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        System.out.println("================================");
        System.out.println("");
        System.out.println("Suspicious IPs (" + threshold + " or more attempts):");

        if (suspiciousIps.isEmpty()) {
            System.out.println("No suspicious IPs found.");
        } else {
            for (Map.Entry<String, Integer> entry : suspiciousIps.entrySet()) {
                System.out.println(entry.getKey() + " -> " + entry.getValue() + " failed attempts");
            }
        }
        
        System.out.println("");
        System.out.println("================================");
        System.out.println("After-hours successful logins:");
        detectAfterHoursLogins(filePath);
    }   

}
