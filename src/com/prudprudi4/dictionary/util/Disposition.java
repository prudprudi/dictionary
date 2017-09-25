package com.prudprudi4.dictionary.util;

import java.awt.*;

public class Disposition {

    public static class ScreenConstants {
        public final static int SCREEN_CENTER_X;
        public final static int SCREEN_CENTER_Y;

        static {
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            SCREEN_CENTER_X = d.width / 2;
            SCREEN_CENTER_Y = d.height / 2;
        }
    }

    public static Point getWindowCenterPos(int width, int height) {
        return new Point (
                ScreenConstants.SCREEN_CENTER_X - width / 2,
                ScreenConstants.SCREEN_CENTER_Y - height / 2
        );
    }

}
