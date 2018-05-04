package Objects.FileIO;

import java.io.*;
import java.util.*;

public class StatsFile {
    public static File stats = new File("src/Files/Stats/UserStatistics");
    public static BufferedReader retrieveStats;
    public static BufferedWriter printNewStats;
    public static double averageWPM, averageCPM;
    public static int timesPlayed;

    public StatsFile(){
        retrieveOldStats();
    }

    public static void addNewStats(int WPM, int CPM){
        averageWPM = (averageWPM*timesPlayed + WPM) / (timesPlayed+1);
        averageCPM = (averageCPM*timesPlayed + CPM) / (timesPlayed+1);
        timesPlayed++;
        updateNewStats();
    }

    public static void retrieveOldStats(){
        try {
            retrieveStats = new BufferedReader(new FileReader(stats));
            int i;

            averageWPM = Double.parseDouble(retrieveStats.readLine());
            averageCPM = Double.parseDouble(retrieveStats.readLine());
            timesPlayed = Integer.parseInt(retrieveStats.readLine());

            retrieveStats.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static int getAverageWPM(){
        return (int)averageWPM;
    }

    public static int getAverageCPM(){
        return (int)averageCPM;
    }

    public static int getNumTimesPlayed(){
        return timesPlayed;
    }

    public static void updateNewStats(){
        try {
            printNewStats = new BufferedWriter(new FileWriter(stats));

            printNewStats.write(Double.toString(averageWPM) + "\n");
            printNewStats.write(Double.toString(averageCPM) + "\n");
            printNewStats.write(Integer.toString(timesPlayed) + "\n");

            printNewStats.close();
        }
        catch(Exception e){
            System.out.println("printing error");
        }
    }
}
