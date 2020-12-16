package com.example.qrscanner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class BorderView extends View {
    Paint paint;
    public BorderView(Context context) {
        super(context);
        paint = new Paint();
    }
    @Override
    protected void onDraw(Canvas canvas){
        int rectWidth = 15;
        int rectHeght = 5;
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        paint.setARGB(255,255,0,0);
        canvas.drawRect(0,0,rectWidth,rectHeght,paint);
        canvas.drawRect(width-rectWidth,0,width,rectHeght,paint);
        canvas.drawRect(0,height-rectHeght,rectWidth,height,paint);
        canvas.drawRect(0,0,15,5,paint);


    }
}
