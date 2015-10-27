package com.radomar.customprogressbar;

import android.content.Context;
import android.util.Property;
import android.view.View;

/**
 * Created by Radomar on 23.10.2015.
 */
public class Rectangle {

    public int sideSize;
    public int x;
    public int y;
    public int opacity = 255;
//
//    public int RectangleCenterX;
//    public int RectangleCenterY;

    public void init(int x, int y, int sideSize) {
        this.x = x;
        this.y = y;
        this.sideSize = sideSize;
    }

//    private void initCenterCoordinates() {
//        RectangleCenterX = x + sideSize / 2;
//        RectangleCenterY = y + sideSize / 2;
//    }

    public static final Property<Rectangle, Integer> X = new Property<Rectangle, Integer>(Integer.TYPE,"setX") {

        @Override
        public void set(Rectangle object, Integer value) {
            object.x = value;
        }

        @Override
        public Integer get(Rectangle object) {
            return null;
        }
    };

    public static final Property<Rectangle, Integer> Y = new Property<Rectangle, Integer>(Integer.TYPE,"setY") {

        @Override
        public void set(Rectangle object, Integer value) {
            object.y = value;
        }

        @Override
        public Integer get(Rectangle object) {
            return null;
        }
    };


    public static final Property<Rectangle, Integer> ALPHA = new Property<Rectangle, Integer>(Integer.TYPE,"alpha") {

        @Override
        public void set(Rectangle object, Integer value) {
            object.opacity = value;
        }

        @Override
        public Integer get(Rectangle object) {
            return null;
        }
    };
}
