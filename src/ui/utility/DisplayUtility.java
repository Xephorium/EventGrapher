package ui.utility;

import ui.InterfaceConstants;

import java.awt.*;

/* Event Grapher
 * Christopher Cruzen
 * 01.01.2021
 *
 * DisplayUtility contains common screen dimension retrieval methods.
 */

public class DisplayUtility {

    public static int getScreenWidth() {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    public static int getScreenHeight() {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    public static int getScreenCenterX() {
        return (getScreenWidth() / 2);
    }

    public static int getScreenCenterY() {
        return (getScreenHeight() / 2);
    }

    public static int getWindowCenterX() {
        return InterfaceConstants.WINDOW_WIDTH / 2;
    }

    public static int getWindowCenterY() {
        return InterfaceConstants.WINDOW_HEIGHT / 2;
    }

    public static int getWindowStartX() {
        return (getScreenWidth() / 2) - (InterfaceConstants.WINDOW_WIDTH / 2);
    }

    public static int getWindowStartY() {
        return (getScreenHeight() / 2) - (InterfaceConstants.WINDOW_HEIGHT / 2);
    }
}
