package io;

import model.*;
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
        Date eventDate = null;
        EventType eventType = null;
        List<String> headliners = new ArrayList<>();
        List<String> sites = new ArrayList<>();
        List<Performer> performers = new ArrayList<>();
        String style = "";
        String platform = "";
        String format = "";
        List<String> labels = new ArrayList<>();
        boolean isIndependent = false;

        // Check for comma separated list with 2+ items
        if (line.isEmpty()) return null;
        if (!line.contains(",")) return null;
        List<String> elements = Arrays.asList(line.split(","));
        if (elements.size() < 2) return null;

        // Parse first item as date
        try {
            eventDate = EVENT_STRING_FORMAT.parse(elements.get(0));
        } catch (ParseException e) { return null; }
        if (eventDate == null) return null;

        // Parse second item as type
        String typeString =  elements.get(1).trim();
        if (typeString.length() < 1 || typeString.length() > 3) return null;
        eventType = EventType.values()[typeString.length() - 1];

        // 2020 Event - All info gathered, build & return
        if (elements.size() == 2) {
            Event event = new Event();
            event.date = eventDate;
            event.type = eventType;
            return event;

        // 2021+ Event - Continue parsing fields
        } else {

            // Parse third item as headliners/independent flag
            if (eventType == EventType.VIRTUAL || eventType == EventType.SHARED) {
                headliners = Arrays.asList(elements.get(2).trim().split("\\|"));
            } else if (elements.get(2).equals("Independent")) {
                isIndependent = true;
            }

            // Parse fourth item as sites
            if (!elements.get(3).trim().isEmpty()) {
                sites = Arrays.asList(elements.get(3).trim().split("\\|"));
            }

            // Parse fifth item as performers
            if (!elements.get(4).trim().isEmpty()) {
                String[] performerStrings = elements.get(4).trim().split("\\|");
                for (String string: performerStrings) {
                    String name = "";
                    String site = "";
                    if (string.contains("{")) {
                        name = string.split("\\{")[0].trim();
                        site = string.split("\\{")[1].substring(0, string.split("\\{")[1].length() - 1).trim();
                    } else {
                        name = string.trim();
                    }
                    performers.add(new Performer(name, site));
                }
            }

            // Parse sixth item as style
            if (!elements.get(5).trim().isEmpty()) {
                style = elements.get(5).trim();
            }

            // Parse seventh item as platform
            if (!elements.get(6).trim().isEmpty()) {
                platform = elements.get(6).trim();
            }

            // Parse eighth item as format
            if (!elements.get(7).trim().isEmpty()) {
                format = elements.get(7).trim();
            }

            // Parse ninth element as labels
            if (!elements.get(8).trim().equals("[]") && !elements.get(8).trim().isEmpty()) {
                String[] labs = elements.get(8).trim().substring(1, elements.get(8).trim().length() - 1).split("\\|");
                for (String label: labs) {
                    labels.add(label.toLowerCase());
                }
            }

            // Build Event
            if (eventType == EventType.SHARED || isIndependent) {

                // Shared Event
                Event event = new Event();
                event.date = eventDate;
                event.type = eventType;
                event.headliners = headliners;
                // TODO - Add gear
                // TODO - Add notes
                return event;

            } else if (eventType == EventType.VIRTUAL && sites.isEmpty()) {

                // Virtual Event
                VirtualEvent event = new VirtualEvent();
                event.date = eventDate;
                event.type = eventType;
                event.headliners = headliners;
                event.platform = platform;
                return event;

            } else {
                if (labels.contains("anime") || labels.contains("comic") || labels.contains("fanart")) {

                    // Art Event
                    ArtEvent event = new ArtEvent();
                    event.date = eventDate;
                    event.type = eventType;
                    event.headliners = headliners;
                    event.labels = labels;
                    event.platform = platform;
                    event.genre = labels.get(0);
                    return event;

                } else {

                    // Live Event
                    LiveEvent event = new LiveEvent();
                    event.date = eventDate;
                    event.type = eventType;
                    event.headliners = headliners;
                    event.labels = labels;
                    event.platform = platform;
                    event.sites = sites;
                    event.performers = performers;
                    event.style = style;
                    event.format = format;
                    return event;
                }
            }
        }
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
