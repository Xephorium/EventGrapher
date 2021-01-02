package ui;

import io.InputReader;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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


    // Paint Constants
    private static final Color BACKGROUND_COLOR = new Color(255, 255, 255);
    private static final Color TEXT_COLOR_PRIMARY = new Color(0, 0, 0);
    private static final Color TEXT_COLOR_SECONDARY = new Color(80, 80, 80);
    private static final int WINDOW_PADDING = 15;
    private static final int TEXT_SIZE = 15;
    private static final int TEXT_LINE_SPACING = 2;
    private static final int TEXT_INDENT = 10;
    private static final int TEXT_COLUMN = 300;

    // Formatting Constants
    private DecimalFormat DECIMAL_FORMAT_3 = new DecimalFormat("0.00#");
    private DecimalFormat DECIMAL_FORMAT_P = new DecimalFormat("0.#");

    // Data Variables
    List<Date> fullEventList;
    List<Date> soloEventList;
    List<Date> sharedEventList;
    List<Date> virtualEventList;

    // Stat Variables
    private double dailyAverageEvents;
    private double weeklyAverageEvents;
    private double soloEventPercent;
    private double sharedEventPercent;
    private double virtualEventPercent;
    private String longestGap;
    private String shortestGap;
    private String peakDay;
    private String peakWeek;


    /*--- Constructor ---*/

    GraphPanel() {

        // Parse & Store Event Data
        InputReader inputReader = new InputReader();
        fullEventList = inputReader.getFullEventList();
        soloEventList = inputReader.getSoloEventList();
        sharedEventList = inputReader.getSharedEventList();
        virtualEventList = inputReader.getVirtualEventList();

        // Perform Calculations
        dailyAverageEvents = fullEventList.size() / 366.0;    // 2020 Leap Year
        weeklyAverageEvents = fullEventList.size() / 52.2857; // 52 Weeks + 2/7 Extra
        soloEventPercent = soloEventList.size() / (double) fullEventList.size();
        sharedEventPercent = sharedEventList.size() / (double) fullEventList.size();
        virtualEventPercent = virtualEventList.size() / (double) fullEventList.size();
        longestGap = getLongestGap();
        shortestGap = getShortestGap();
        peakDay = getPeakDay();
        peakWeek = getPeakWeek();

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
        int infoBaseHeight;

        // Title
        graphics.setColor(TEXT_COLOR_PRIMARY);
        graphics.setFont(new Font("Sanserif", Font.BOLD, TEXT_SIZE));
        graphics.drawString(
                "2020 Events",
                WINDOW_PADDING,
                currentHeight
        );
        currentHeight += graphics.getFontMetrics().getHeight();
        currentHeight += TEXT_LINE_SPACING;
        infoBaseHeight = currentHeight;

        // Events
        graphics.setColor(TEXT_COLOR_SECONDARY);
        graphics.setFont(new Font("Sanserif", Font.PLAIN, TEXT_SIZE));
        graphics.drawString(
                "Total: " + fullEventList.size()
                        + "  (Avg: " + format3(dailyAverageEvents)
                        + "/day, " + format3(weeklyAverageEvents) + "/week)",
                WINDOW_PADDING + TEXT_INDENT,
                currentHeight
        );
        currentHeight += graphics.getFontMetrics().getHeight();
        currentHeight += TEXT_LINE_SPACING;

        // Solo Events
        graphics.drawString(
                "Solo: " + soloEventList.size() + "  (" + formatP(soloEventPercent) + "%)",
                WINDOW_PADDING + TEXT_INDENT,
                currentHeight
        );
        currentHeight += graphics.getFontMetrics().getHeight();
        currentHeight += TEXT_LINE_SPACING;

        // Shared Events
        graphics.drawString(
                "Shared: " + sharedEventList.size() + "  (" + formatP(sharedEventPercent) + "%)",
                WINDOW_PADDING + TEXT_INDENT,
                currentHeight
        );
        currentHeight += graphics.getFontMetrics().getHeight();
        currentHeight += TEXT_LINE_SPACING;

        // Virtual Events
        graphics.drawString(
                "Virtual: " + virtualEventList.size() + "  (" + formatP(virtualEventPercent) + "%)",
                WINDOW_PADDING + TEXT_INDENT,
                currentHeight
        );

        // Longest Gap
        currentHeight = infoBaseHeight;
        graphics.setColor(TEXT_COLOR_SECONDARY);
        graphics.drawString(
                "Longest Gap: " + longestGap,
                WINDOW_PADDING + TEXT_COLUMN,
                currentHeight
        );
        currentHeight += graphics.getFontMetrics().getHeight();
        currentHeight += TEXT_LINE_SPACING;

        // Shortest Gap
        graphics.setColor(TEXT_COLOR_SECONDARY);
        graphics.drawString(
                "Shortest Gap: " + shortestGap,
                WINDOW_PADDING + TEXT_COLUMN,
                currentHeight
        );
        currentHeight += graphics.getFontMetrics().getHeight();
        currentHeight += TEXT_LINE_SPACING;

        // Peak Day
        graphics.setColor(TEXT_COLOR_SECONDARY);
        graphics.drawString(
                "Peak Day: " + peakDay,
                WINDOW_PADDING + TEXT_COLUMN,
                currentHeight
        );
        currentHeight += graphics.getFontMetrics().getHeight();
        currentHeight += TEXT_LINE_SPACING;

        // Peak Week
        graphics.setColor(TEXT_COLOR_SECONDARY);
        graphics.drawString(
                "Peak Week: " + peakWeek,
                WINDOW_PADDING + TEXT_COLUMN,
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


    /*--- Private Analysis Methods ---*/

    private String getLongestGap() {

        // Find Longest Gap
        long longestGap = 0;
        Date start = fullEventList.get(0);
        Date end = fullEventList.get(1);
        for (int x = 0; x < fullEventList.size() - 2; x++) {
            long testGap = fullEventList.get(x + 1).getTime() - fullEventList.get(x).getTime();
            if (testGap > longestGap) {
                longestGap = testGap;
                start = fullEventList.get(x);
                end = fullEventList.get(x + 1);
            }
        }

        // Format Output
        int days = truncateDecimals(longestGap / 1000 / 60 / 60 / 24.0);
        long daysInMillis = days * 24 * 60 * 60 * 1000;
        int hours = truncateDecimals((longestGap - daysInMillis) / 1000 / 60 / 60.0);
        int hoursInMillis = hours * 60 * 60 * 1000;
        int minutes = truncateDecimals((longestGap - daysInMillis - hoursInMillis) / 1000 / 60.0);


        return " " + days + "d, "
                + hours + "hr, and "
                + minutes + "min  ("
                + InputReader.EVENT_STRING_FORMAT.format(start).toLowerCase() + " - "
                + InputReader.EVENT_STRING_FORMAT.format(end).toLowerCase() + ").";
    }

    private String getShortestGap() {

        // Find Shortest Gap
        long shortestGap = fullEventList.get(1).getTime() - fullEventList.get(0).getTime();
        Date start = fullEventList.get(0);
        Date end = fullEventList.get(1);
        for (int x = 1; x < fullEventList.size() - 3; x++) {
            long testGap = fullEventList.get(x + 1).getTime() - fullEventList.get(x).getTime();
            if (testGap < shortestGap) {
                shortestGap = testGap;
                start = fullEventList.get(x);
                end = fullEventList.get(x + 1);
            }
        }

        // Format Output
        int days = truncateDecimals(shortestGap / 1000 / 60 / 60 / 24.0);
        long daysInMillis = days * 24 * 60 * 60 * 1000;
        int hours = truncateDecimals((shortestGap - daysInMillis) / 1000 / 60 / 60.0);
        int hoursInMillis = hours * 60 * 60 * 1000;
        int minutes = truncateDecimals((shortestGap - daysInMillis - hoursInMillis) / 1000 / 60.0);


        return " " + days + "d, "
                + hours + "hr, and "
                + minutes + "min  ("
                + InputReader.EVENT_STRING_FORMAT.format(start).toLowerCase() + " - "
                + InputReader.EVENT_STRING_FORMAT.format(end).toLowerCase() + ").";
    }

    private String getPeakDay() {

        // Find Peak Day
        int most = 0;
        Date peakDay = new Date();
        for (Date event : fullEventList) {
            List<Date> dayEvents = fullEventList.stream().filter(day ->
                    areDaysEqual(event, day)
            ).collect(Collectors.toList());
            if (dayEvents.size() > most) {
                most = dayEvents.size();
                peakDay = getZeroTimeDate(event);
            }
        }

        return " " + most + " on " + InputReader.EVENT_DAY_FORMAT.format(peakDay);
    }

    private String getPeakWeek() {

        // Find Peak Week
        int most = 0;
        Date peakWeekStart = new Date();
        for (int x = 0; x < fullEventList.size() - 8; x++) {
            Date startEvent = fullEventList.get(x);
            Date rangeEnd = getDateOneWeekLater(startEvent);

            List<Date> weekEvents = fullEventList.stream().filter(day ->
                    day.compareTo(startEvent) >= 0 && rangeEnd.compareTo(day) > 0
            ).collect(Collectors.toList());
            if (weekEvents.size() > most) {
                most = weekEvents.size();
                peakWeekStart = startEvent;
            }
        }

        return " " + most + " beginning (" + InputReader.EVENT_STRING_FORMAT.format(peakWeekStart).toLowerCase() + ")";
    }

    private boolean areDaysEqual(Date d1, Date d2) {
        Date newDate1 = getZeroTimeDate(d1);
        Date newDate2 = getZeroTimeDate(d2);

        return newDate1.compareTo(newDate2) == 0;
    }

    private Date getDateOneWeekLater(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 7);
        return calendar.getTime();
    }

    private Date getZeroTimeDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    private int truncateDecimals(double number) {
        String value = "" + number;
        String newValue = value.replaceFirst("\\..*$", "");
        return Integer.valueOf(newValue);
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
