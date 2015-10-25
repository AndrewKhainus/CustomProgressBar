package com.radomar.customprogressbar;

import android.content.Context;
import android.view.View;

/**
 * Created by Radomar on 23.10.2015.
 */
public class Rectangle extends View {

    public int sideSize;
    public int x;
    public int y;
    private int mOpacity = 255;

    public Rectangle(Context context) {
        super(context);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getOpacity() {
        return mOpacity;
    }

    public void setOpacity(int mOpacity) {
        this.mOpacity = mOpacity;
    }
}
