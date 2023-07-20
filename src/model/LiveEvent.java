package model;

import java.util.ArrayList;
import java.util.List;

public class LiveEvent extends CreditedEvent {


    /*--- Variables ---*/

    public List<Performer> performers;
    public String style;


    /*--- Constructor ---*/

    public LiveEvent() {
        super();

        performers = new ArrayList<>();
        style = "";
    }
}
