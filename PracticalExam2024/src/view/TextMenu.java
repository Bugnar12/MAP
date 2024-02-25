package view;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;
    public TextMenu()
    {
        commands = new LinkedHashMap<>();
    }
    public void addCommand(Command c)
    {
        commands.put(c.getKey(), c);
    }
    public void printMenu()
    {
        for(Command c : commands.values())
        {
            String line = String.format("%4s : %s", c.getKey(), c.getDescription());
            System.out.println(line);
        }
    }
    public void show(){
        Scanner scanner = new Scanner(System.in);
        boolean stopLoop = false;
        while(!stopLoop)
        {
            printMenu();
            System.out.println("Input the option: ");
            String key = scanner.nextLine();
            Command c = commands.get(key);
            if(c == null) {
                System.out.println("Invalid option!");
                continue;
            }
            if(key == "0")
                stopLoop = true;
            c.execute();
        }
        scanner.close();

    }
}
