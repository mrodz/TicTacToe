package tictactoe;

import external.LogMessage;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Standard Colors Library to be used throughout this project.
 * Use the {@link #query(String)} method in order to get the requested color. Entry keys
 * take the format of:
 * <pre>
 *     &lt;COMPONENT NAME&gt;_&lt;APPLICATION&gt;
 * </pre>
 * Where <tt>COMPONENT NAME</tt> is the name of the element that this color is being used in,
 * and <tt>APPLICATION</tt> refers to the exact component this is being applied to; the background,
 * the font, the border, etc.
 *
 * <p>
 *     New {@link Color Colors} should be defined here, instead of within the scope of their
 *     respective elements in order to provide consistency when creating styles.
 * </p>
 * <p>
 *     List of active keys:
 *     <ul>
 *         <li>SIDEBAR_BACKGROUND</li>
 *         <li>MAIN_BORDER</li>
 *         <li>STATUS_BAR_BACKGROUND</li>
 *         <li>BUBBLE_BORDER</li>
 *         <li>BUBBLE_BACKGROUND</li>
 *         <li>SIDEBAR_ITEM_DIVIDER_BORDER</li>
 *         <li>PLAYZONE_BUTTON_BACKGROUND</li>
 *         <li>PLAYZONE_BUTTON_BORDER</li>
 *         <li>PLAYZONE_BUTTON_BACKGROUND_HOVER</li>
 *         <li>PLAYZONE_BUTTON_BACKGROUND_CLICKED</li>
 *         <li>PLAYZONE_BUTTON_BACKGROUND_MIXED</li>
 *         <li>PLAYZONE_BACKGROUND</li>
 *         <li>PLAYZONE_BUTTON_BACKGROUND_LOCKED</li>
 *         <li>PLAYZONE_BUTTON_BORDER_HOVER</li>
 *         <li>WON_TILE</li>
 *         <li>WON_TILE_HOVER</li>
 *         <li>CONTROL_PANEL_BORDER</li>
 *         <li>CONTROL_PANEL_BACKGROUND</li>
 *         <li>START_BUTTON_FONT</li>
 *         <li>START_BUTTON_CLICKED</li>
 *         <li>TRANSPARENT</li>
 *     </ul>
 * </p>
 */
@SuppressWarnings("unused")
public final class Colors {
    private static final Map<String, Color> MAPPED = new HashMap<>();

    private static final Color[] COLORS = {
            new Color(0xD76164),
            new Color(0x722837),
            new Color(0xC9AB98),
            new Color(0xae826b),
            new Color(0xFFFFFDF3, true),
            new Color(0xAD5870),
            new Color(0x77FFFFFF, true),
            new Color(0x3B3B3636, true),
            new Color(0xEBFFFFFF, true),
            new Color(0xC1B3F3DE, true),
            new Color(0x97E3FCF1, true),
            new Color(0xF5D6D8FF, true),
            new Color(0x3EFF6240, true),
            new Color(0x953B3636, true),
            new Color(0xBF409335, true),
            new Color(0xDF83C6AB, true),
            /*new Color(0x8CCB72)*/ new Color(0xFFF064),
            /*new Color(0x749C49)*/ new Color(0xE3C34D),
            new Color(0x6E726C),
            new Color(0x8FA0C7B1, true),
            new Color(0xFFFFFF, true)
    };

    private static final String[] KEYS = {
            "SIDEBAR_BACKGROUND",
            "MAIN_BORDER",
            "STATUS_BAR_BACKGROUND",
            "BUBBLE_BORDER",
            "BUBBLE_BACKGROUND",
            "SIDEBAR_ITEM_DIVIDER_BORDER",
            "PLAYZONE_BUTTON_BACKGROUND",
            "PLAYZONE_BUTTON_BORDER",
            "PLAYZONE_BUTTON_BACKGROUND_HOVER",
            "PLAYZONE_BUTTON_BACKGROUND_CLICKED",
            "PLAYZONE_BUTTON_BACKGROUND_MIXED",
            "PLAYZONE_BACKGROUND",
            "PLAYZONE_BUTTON_BACKGROUND_LOCKED",
            "PLAYZONE_BUTTON_BORDER_HOVER",
            "WON_TILE",
            "WON_TILE_HOVER",
            "CONTROL_PANEL_BORDER",
            "CONTROL_PANEL_BACKGROUND",
            "START_BUTTON_FONT",
            "START_BUTTON_CLICKED",
            "TRANSPARENT",
    };

    static {
        for (int i = 0; i < KEYS.length; i++) {
            MAPPED.put(KEYS[i], COLORS[i]);
        }
    }

    public static Color query(String key) throws NullPointerException {
        Color c = null;
        if (key != null) c = MAPPED.get(key);

        if (key == null || c == null) {
            new LogMessage("Could not find color from key: " + key, LogMessage.WARN);
            return Color.GREEN;
        }
        else return c;
    }

    public static Color[] getColors() {
        return Arrays.copyOf(COLORS, COLORS.length);
    }

    public static String[] getKeys() {
        return Arrays.copyOf(KEYS, KEYS.length);
    }
}
