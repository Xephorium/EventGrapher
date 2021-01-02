package ui;

import io.InputReader;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/* Event Grapher
 * Christopher Cruzen
 * 01.01.2021
 *
 *   GraphPanel is backbone of Event Grapher. It delegates dataset parsing to a utility IO
 * class, stores the retrieved event lists, and uses that data to draw stats and infographics
 * to the screen.
 */

class GraphPanel extends JPanel {


    /*--- Variable Declarations ---*/

    // Interface Constants
    private static final int POINT_DIAMETER = 11;
    private static final int POINT_DIAMETER_SMALL = 5;
    private static final int POINT_OFFSET = 0;

    private static final Color BACKGROUND_COLOR = new Color(255, 255, 255);
    private static final Color TEXT_COLOR_PRIMARY = new Color(0, 0, 0);
    private static final Color TEXT_COLOR_SECONDARY = new Color(80, 80, 80);
    private static final int WINDOW_PADDING = 15;
    private static final int TEXT_SIZE = 15;
    private static final int TEXT_LINE_SPACING = 2;
    private static final int TEXT_INDENT = 10;
    private static final int TEXT_COLUMN = 30;

    private DecimalFormat DECIMAL_FORMAT_3 = new DecimalFormat("0.00#");
    private DecimalFormat DECIMAL_FORMAT_P = new DecimalFormat("0.#");

    // Variables
    GraphWindow parent;
    InputReader inputReader;
    List<Date> fullEventList;
    List<Date> soloEventList;
    List<Date> sharedEventList;
    List<Date> virtualEventList;


    /*--- Constructor ---*/

    GraphPanel() {

        // Parse & Store Event Data
        inputReader = new InputReader();
        fullEventList = inputReader.getFullEventList();
        soloEventList = inputReader.getSoloEventList();
        sharedEventList = inputReader.getSharedEventList();
        virtualEventList = inputReader.getVirtualEventList();

        // Configure UI
        setBackground(BACKGROUND_COLOR);
    }


    /*--- Draw Method ---*/

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Setup 2D Graphics
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw Interface
        if (fullEventList != null && sharedEventList != null && virtualEventList != null) {
            drawTotals(graphics);
        } else {
            invalidate();
            repaint();
        }
    }


    /*--- Private Draw Methods ---*/

    private void drawTotals(Graphics2D graphics) {

        // Set Up Local Variables
        int currentHeight = WINDOW_PADDING * 2;

        // Title
        graphics.setColor(TEXT_COLOR_PRIMARY);
        graphics.setFont(new Font ("Sanserif", Font.BOLD, TEXT_SIZE));
        graphics.drawString(
                "2020 Events",
                WINDOW_PADDING,
                currentHeight
        );
        currentHeight += graphics.getFontMetrics().getHeight();
        currentHeight += TEXT_LINE_SPACING;

        // Events
        graphics.setColor(TEXT_COLOR_SECONDARY);
        graphics.setFont(new Font ("Sanserif", Font.PLAIN, TEXT_SIZE));
        graphics.drawString(
                "Total: " + fullEventList.size() + " (Avg: " + format3(fullEventList.size() / 366.0) + " / day)",
                WINDOW_PADDING + TEXT_INDENT,
                currentHeight
        );
        currentHeight += graphics.getFontMetrics().getHeight();
        currentHeight += TEXT_LINE_SPACING;

        // Solo Events
        graphics.setColor(TEXT_COLOR_SECONDARY);
        graphics.setFont(new Font ("Sanserif", Font.PLAIN, TEXT_SIZE));
        graphics.drawString(
                "Solo: " + soloEventList.size() + " (" + formatP(soloEventList.size() / (double) fullEventList.size()) + "%)",
                WINDOW_PADDING + TEXT_INDENT,
                currentHeight
        );
        currentHeight += graphics.getFontMetrics().getHeight();
        currentHeight += TEXT_LINE_SPACING;

        // Shared Events
        graphics.setColor(TEXT_COLOR_SECONDARY);
        graphics.setFont(new Font ("Sanserif", Font.PLAIN, TEXT_SIZE));
        graphics.drawString(
                "Shared: " + sharedEventList.size() + " (" + formatP(sharedEventList.size() / (double) fullEventList.size()) + "%)",
                WINDOW_PADDING + TEXT_INDENT,
                currentHeight
        );
        currentHeight += graphics.getFontMetrics().getHeight();
        currentHeight += TEXT_LINE_SPACING;

        // Virtual Events
        graphics.setColor(TEXT_COLOR_SECONDARY);
        graphics.setFont(new Font ("Sanserif", Font.PLAIN, TEXT_SIZE));
        graphics.drawString(
                "Virtual: " + virtualEventList.size() + " (" + formatP(virtualEventList.size() / (double) fullEventList.size()) + "%)",
                WINDOW_PADDING + TEXT_INDENT,
                currentHeight
        );
        currentHeight += graphics.getFontMetrics().getHeight();
        currentHeight += TEXT_LINE_SPACING;
    }


    /*--- Private Formatting Methods ---*/

    private String formatP(double value) {
        return DECIMAL_FORMAT_P.format(value * 100);
    }

    private String format3(double value) {
        return DECIMAL_FORMAT_3.format(value);
    }


    /*--- Private UI Methods ---*/

    private void drawLine(Graphics2D graphics, Point start, Point end) {
        Line2D line = new Line2D.Float(start.x, start.y, end.x, end.y);
        graphics.draw(line);
    }

    private void drawPoint(Graphics2D graphics, Point point) {
        Ellipse2D.Double circle = new Ellipse2D.Double(
                point.x - (POINT_DIAMETER / 2) + POINT_OFFSET,
                point.y - (POINT_DIAMETER / 2) + POINT_OFFSET,
                POINT_DIAMETER,
                POINT_DIAMETER
        );
        graphics.fill(circle);
    }

    private void drawSmallPoint(Graphics2D graphics, Point point) {
        Ellipse2D.Double circle = new Ellipse2D.Double(
                point.x - (POINT_DIAMETER_SMALL / 2) + POINT_OFFSET,
                point.y - (POINT_DIAMETER_SMALL / 2) + POINT_OFFSET,
                POINT_DIAMETER_SMALL,
                POINT_DIAMETER_SMALL
        );
        graphics.fill(circle);
    }

}
