package io;

/* Event Grapher
 * Christopher Cruzen
 * 01.01.2021
 *
 *   InputReader is the root level input manager for Event Grapher. It reads from the source
 * data file, parses events, and returns usable object lists to its calling class.
 */

import java.text.SimpleDateFormat;

public class InputReader {

    // Constants
    public static final SimpleDateFormat EVENT_STRING_FORMAT = new SimpleDateFormat("MM.dd.yyyy hh:mma");
    public static final SimpleDateFormat EVENT_DAY_PROSE_FORMAT = new SimpleDateFormat("MMM d");
    public static final SimpleDateFormat EVENT_TIME_FORMAT = new SimpleDateFormat("h:mma");
    
}
