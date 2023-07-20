package model;

import java.util.ArrayList;
import java.util.List;

public class CreditedEvent extends Event {


    /*--- Variables ---*/

    public List<String> sites;
    public String format;
    public String platform;


    /*--- Constructor ---*/

    public CreditedEvent() {
        super();

        sites = new ArrayList<>();
        format = "";
        platform = "";
    }
}
