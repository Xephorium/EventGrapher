package io;

import model.Event;
import model.types.EventType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/* Event Grapher
 * Christopher Cruzen
 * 07.19.2023
 *
 *   EventRepository is the root level data management class of Event Grapher.
 * After delegating a read of the input file, it parses and stores the returned
 * data in usable object lists that can be returned to its calling class.
 */

public class EventRepository {


    /*--- Variables ---*/

    // Constants
    public static final SimpleDateFormat EVENT_STRING_FORMAT = new SimpleDateFormat("MM.dd.yyyy hh:mma");
    public static final SimpleDateFormat EVENT_DAY_FORMAT = new SimpleDateFormat("MM.dd.yyyy");

    // Variables
    private final InputManager inputManager;
    private List<String> rawInput;
    private List<Event> eventList;


    /*--- Constructor ---*/

    public EventRepository() {

        // Read Input File
        inputManager = new InputManager();
        rawInput = inputManager.readInputFile();

        // Build Event List From Raw Input
        buildEventList();
    }


    /*--- Private Initialization Methods ---*/

    private void buildEventList() {
        eventList = new ArrayList<>();

        for (String line: rawInput) {
            Event event = parseInputLine(line);
            if (event != null) {
                eventList.add(event);
            }
        }
    }

    private Event parseInputLine(String line) {

        // Check for comma separated list with 2+ items
        if (line.isEmpty()) return null;
        if (!line.contains(",")) return null;
        List<String> elements = Arrays.asList(line.split(","));
        if (elements.size() < 2) return null;

        // Check first item for date
        Date eventDate = null;
        try {
            eventDate = EVENT_STRING_FORMAT.parse(elements.get(0));
        } catch (ParseException e) { /* Do Nothing */ }
        if (eventDate == null) return null;

        // Check second item for type
        String typeString =  elements.get(1).trim();
        if (typeString.length() < 1 || typeString.length() > 3) return null;
        EventType eventType = EventType.values()[typeString.length() - 1];

        // Build Event
        Event event = new Event();
        event.date = eventDate;
        event.type = eventType;

        return event;
    }


    /*--- Public Data Access Methods ---*/

    public List<Event> getFullEventList() {
        return eventList;
    }

    public List<Event> getSoloEventList() {
        return eventList.stream().filter(event -> event.type == EventType.SOLO).collect(Collectors.toList());
    }

    public List<Event> getVirtualEventList() {
        return eventList.stream().filter(event -> event.type == EventType.VIRTUAL).collect(Collectors.toList());
    }

    public List<Event> getSharedEventList() {
        return eventList.stream().filter(event -> event.type == EventType.SHARED).collect(Collectors.toList());
    }
}
