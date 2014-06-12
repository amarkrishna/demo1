/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magpie;

import java.util.List;
import magpie.user.CommandHandler;
import magpie.user.InputFileParser;

/**
 * Main program for text-based interface. Command line arguments are input files
 *  that you would like to read. After those are parsed, the program will switch to
 *  interactive mode.
 * 
 * @author Logan Ward
 * @version 0.1
 */
public class Magpie {
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        // Prepare to parse input files
        InputFileParser Parser = new InputFileParser();
        CommandHandler Commander = new CommandHandler();
        
        // Read all provided input files
        for (String arg : args)
            Commander.readFile(arg);
        
        // Get additional commands from standard in
        List<String> Command;
        do {
            Command = Parser.getCommands();
            if (Command != null) 
                Commander.runCommand(Command);
        } while (Command != null);
        
        // Close whatever we are reading from standard in
        Parser.closeFile();
    }
}
