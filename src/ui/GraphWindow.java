package ui;

import ui.utility.DisplayUtility;

import javax.swing.*;
import java.awt.*;

/* Event Grapher
 * Christopher Cruzen
 * 01.01.2021
 *
 * GraphWindow is the base UI class of EventGrapher.
 */

public class GraphWindow {


    /*--- Variables ---*/

    private JFrame frame;
    private GraphPanel graphPanel;


    /*--- Constructor ---*/

    public GraphWindow() {
        setGlobalLookAndFeel();
        initializeFrameAttributes();

        initializeViewClasses();
        addViewClasses();
    }


    /*--- Public Methods ---*/

    public void show() {
        frame.setVisible(true);
    }


    /*--- Private Methods --*/

    private void setGlobalLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // A Whole Lot of Nothin'
        }
    }

    private void initializeFrameAttributes() {
        frame = new JFrame("Event Grapher");
        frame.setSize(InterfaceConstants.WINDOW_WIDTH, InterfaceConstants.WINDOW_HEIGHT);
        frame.setLocation(DisplayUtility.getWindowStartX(), DisplayUtility.getWindowStartY());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    private void initializeViewClasses() {
        graphPanel = new GraphPanel();
    }

    private void addViewClasses() {
        JScrollPane scrollPane = new JScrollPane(graphPanel);
        frame.add(scrollPane, BorderLayout.CENTER);
    }
}
