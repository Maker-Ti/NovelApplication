package com.example.maker.novelapplication.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.maker.novelapplication.ActionController;
import com.example.maker.novelapplication.R;
import com.example.maker.novelapplication.activity.ReadingActivity;

import java.util.ArrayList;
import java.util.List;

public class ReadingText extends View {

    private String data = "";
    private int dataIndex = 0;
    public int pageIndex = 0;
    public int chinaWidth,englishWidth;
    public int windowWidth,windowHeight;
    private Paint mainPaint;
    public int lineSize;
    private boolean init = false;
    private float touchX,touchY;
    private Canvas canvas;
    private Context context;

    public ReadingText(Context context) {
        super(context);
        Log.e("makerLog","context");
    }

    public ReadingText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }


    public ReadingText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

   /* @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touchX = event.getX();
            touchY = event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_UP){

            float x = event.getX();
            float y = event.getY();
            float num = 0;

                num = touchX - x;

            if(num<50&&num>-50){
                if(touchX>windowWidth/2){
                    invalidate();
                    drawPageByIndex(++pageIndex);
                }else {
                  //  drawPageByIndex(pageIndex--);
                }
            }else if(num>50){
               // drawPageByIndex(pageIndex++);
            }else {
             //   drawPageByIndex(pageIndex--);
            }
        }
        return true;
    }*/

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.canvas = canvas;
        Log.e("makerLog","drawing");
        if(ActionController.singlePageData == null){
            Log.e("makerLog","callback");
            mainPaint = new Paint();
            mainPaint.setTextSize(50);
            mainPaint.setColor(Color.BLACK);
            chinaWidth = (int) mainPaint.measureText("测");
            englishWidth = (int) mainPaint.measureText("c");
            windowWidth = getMeasuredWidth();
            windowHeight = getMeasuredHeight();
            lineSize = (int) (windowHeight/(chinaWidth*1.5));
            ActionController.readingActivity.ReadingTextCallBack(chinaWidth,englishWidth,windowWidth,windowHeight,lineSize);
        }else {
            this.canvas = canvas;
            Log.e("makerLog","drawing");
            drawPageByIndex();
        }
    }
    public void drawPageByIndex(){
                int index = 0;
                for(int i=0;i<ActionController.singlePageData.size();i++){
                    Log.e("makerLog","datadraw:"+ActionController.singlePageData.get(i));
                    canvas.drawText(ActionController.singlePageData.get(i),0, (float) ((index+1)*((chinaWidth*1.5))),mainPaint);
                    index++;
                }
    }

    @Override
    public void invalidate() {
        super.invalidate();
    }
}
