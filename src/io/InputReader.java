package io;

/* Event Grapher
 * Christopher Cruzen
 * 01.01.2021
 *
 *   InputReader is the root level input manager for Event Grapher. It reads from the source
 * data file, parses events, and returns usable object lists to its calling class.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InputReader {


    /*--- Variable Declarations ---*/

    // Constants
    public static final SimpleDateFormat EVENT_STRING_FORMAT = new SimpleDateFormat("MM.dd.yyyy hh:mma");
    public static final SimpleDateFormat EVENT_DAY_FORMAT = new SimpleDateFormat("MM.dd.yyyy");
    public static final SimpleDateFormat EVENT_DAY_PROSE_FORMAT = new SimpleDateFormat("MMM d");
    public static final SimpleDateFormat EVENT_TIME_FORMAT = new SimpleDateFormat("h:mma");
    private static final String INPUT_FILENAME = "input\\input.txt";

    // Variables
    private final File inputFile;
    private List<String> inputContents;


    /*--- Public Methods ---*/

    public InputReader() {

        // Set Up Variables
        inputFile = new File(INPUT_FILENAME);

        // Read Input File
        readInputFile();
    }

    public List<Date> getFullEventList() {

        // Set Up Local Variables
        List<Date> eventList = new ArrayList<>();

        // Parse Events
        try {
            for (String eventString : inputContents) {
                eventList.add(EVENT_STRING_FORMAT.parse(eventString));
            }
        } catch (ParseException exception) {
            exception.printStackTrace();
        }

        return eventList;
    }

    public List<Date> getSoloEventList() {

        // Set Up Local Variables
        List<Date> eventList = new ArrayList<>();

        // Parse Events
        try {
            for (String eventString : inputContents) {
                if (!eventString.contains("*")) {
                    eventList.add(EVENT_STRING_FORMAT.parse(eventString));
                }
            }
        } catch (ParseException exception) {
            exception.printStackTrace();
        }

        return eventList;
    }

    public List<Date> getSharedEventList() {

        // Set Up Local Variables
        List<Date> eventList = new ArrayList<>();

        // Parse Events
        try {
            for (String eventString : inputContents) {
                if (eventString.contains("**")) {
                    eventList.add(EVENT_STRING_FORMAT.parse(eventString));
                }
            }
        } catch (ParseException exception) {
            exception.printStackTrace();
        }

        return eventList;
    }

    public List<Date> getVirtualEventList() {

        // Set Up Local Variables
        List<Date> eventList = new ArrayList<>();

        // Parse Events
        try {
            for (String eventString : inputContents) {
                if (eventString.contains("*") && !eventString.contains("**")) {
                    eventList.add(EVENT_STRING_FORMAT.parse(eventString));
                }
            }
        } catch (ParseException exception) {
            exception.printStackTrace();
        }

        return eventList;
    }


    /*--- Private I/O Methods ---*/

    private void readInputFile() {
        if (inputFile == null) {
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
        } catch (Exception exception) {
            System.out.println("Error: Issue reading input file.");
            System.exit(1);
        }
    }

}
