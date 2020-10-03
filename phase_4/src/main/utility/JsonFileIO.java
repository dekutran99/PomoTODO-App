package utility;

import model.Task;
import parsers.TaskParser;
import persistence.Jsonifier;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// File input/output operations
public class JsonFileIO {
    public static final File jsonDataFile = new File("./resources/json/tasks.json");
    
    // EFFECTS: attempts to read jsonDataFile and parse it
    //           returns a list of tasks from the content of jsonDataFile
    public static List<Task> read() {
        TaskParser taskParser = new TaskParser();
        String input = jsonFileToString();
        List<Task> tasks = taskParser.parse(jsonFileToString());
        return tasks;
    }

    private static String jsonFileToString() {
        String jsonString = "";
        try {
            FileReader file = new FileReader(jsonDataFile);
            BufferedReader reader = new BufferedReader(file);
            String line = reader.readLine();
            while (line != null) {
                jsonString += line;
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File error!");
            e.printStackTrace();
        }
        return jsonString;
    }
    
    // EFFECTS: saves the tasks to jsonDataFile
    public static void write(List<Task> tasks) {
        try {
            if (!jsonDataFile.exists()) {
                jsonDataFile.createNewFile();
            }
            FileWriter file = new FileWriter(jsonDataFile);
            BufferedWriter writer = new BufferedWriter(file);
            writer.write(Jsonifier.taskListToJson(tasks).toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("File error!");
            e.printStackTrace();
        }
    }

    /*
    public static void main(String[] args) {
        Task task1 = new Task("Project. ## today; important; cpsc210");
        Task task2 = new Task("CPSC 121 Mideterm. ## important; urgent; cpsc 121");
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        write(tasks);
        System.out.println(read());
    }
    */
}
