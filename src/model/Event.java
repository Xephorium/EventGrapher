package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {


    /*--- Variables ---*/

    public Date date;
    public String type;
    public List<String> headliners;
    public String location;
    public List<String> gear;
    public List<String> labels;
    public String note;


    /*--- Constructor ---*/

    public Event() {
        date = new Date();
        type = "";
        headliners = new ArrayList<>();
        location = "";
        gear = new ArrayList<>();
        labels = new ArrayList<>();
        note = "";
    }
}
