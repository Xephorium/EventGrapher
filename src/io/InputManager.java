package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/* Event Grapher
 * Christopher Cruzen
 * 07.19.2023
 *
 *   InputManager manages direct file access logic for Event Grapher.
 */

public class InputManager {


    /*--- Variables ---*/

    // Constants
    private static final String INPUT_FILENAME = "input\\input.txt";

    // Variables
    private final File inputFile;
    private List<String> inputContents;


    /*--- Constructor ---*/

    public InputManager() {

        // Set Up Variables
        inputFile = new File(INPUT_FILENAME);
    }


    /*--- Public IO Methods ---*/

    public List<String> readInputFile() {
        if (!inputFile.exists()) {
            System.out.println("Error: No input file.");
            System.exit(1);
        }

        inputContents = new ArrayList<>();
        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new FileReader(inputFile));
            String line = bufferedReader.readLine();

            while (line != null) {
                inputContents.add(line);
                line = bufferedReader.readLine();
            }

            bufferedReader.close();
            return inputContents;

        } catch (Exception exception) {
            System.out.println("Error: Issue reading input file.");
            System.exit(1);
            return new ArrayList<>();
        }
    }

}
