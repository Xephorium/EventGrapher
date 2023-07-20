package model;

import model.types.EventType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreditedEvent extends VirtualEvent {


    /*--- Variables ---*/

    public List<String> sites;
    public String format;


    /*--- Constructor ---*/

    public CreditedEvent() {
        super();

        sites = new ArrayList<>();
        format = "";
    }
}
