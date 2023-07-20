
import io.EventRepository;
import ui.GraphWindow;

/* Event Grapher
 * Christopher Cruzen
 * 01.01.2021
 *
 * Main is a simple container that launches the GraphWindow interface.
 */

public class Main {

    public static void main(String[] args) {
        //EventRepository eventRepository = new EventRepository();
        GraphWindow graphWindow = new GraphWindow();
        graphWindow.show();
    }
}
