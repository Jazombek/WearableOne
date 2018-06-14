package pl.edu.pwr.wearableone;

import android.content.Context;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.app.ActionBar.LayoutParams;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends WearableActivity {


    //private final GestureDetector gestureDetector;
    private ImageSwitcher imageSwitcher;
    private FrameLayout frame;
    private int[] images;
    private int currPos = 0;
    //private GestureListener mListener;

    public class OnSwipeTouchListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener(Context ctx) {
            gestureDetector = new GestureDetector(ctx, new GestureListener());
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                            result = true;
                        }
                    } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            // onSwipeBottom();
                        } else {
                            // onSwipeTop();
                        }
                        result = true;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        imageSwitcher = (ImageSwitcher)findViewById(R.id.imageSwitcher);
        frame = findViewById(R.id.frameLayout);
        //imageSwitcher.setImageResource(R.drawable.dn);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
                                     public View makeView() {
                                         ImageView myView = new ImageView(getApplicationContext());
                                         myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                         myView.setLayoutParams(new
                                                 ImageSwitcher.LayoutParams(LayoutParams.WRAP_CONTENT,
                                                 LayoutParams.WRAP_CONTENT));
                                         return myView;
                                     }
                                 });
        Animation in = AnimationUtils.loadAnimation(this,android.R.anim.fade_in);
        imageSwitcher.setInAnimation(in);
        Animation out = AnimationUtils.loadAnimation(this,android.R.anim.fade_out);
        imageSwitcher.setOutAnimation(out);
        makeList();
        imageSwitcher.setImageResource(images[0]);
        setGestureListener();
        //imageSwitcher.setOutAnimation(out);
        // Enables Always-on
        setAmbientEnabled();
    }



    private void setGestureListener(){
        frame.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this));
    }

    private void onSwipeRight(){
        currPos--;
        if(currPos<0)
            currPos=images.length;

        imageSwitcher.setImageResource(images[currPos]);
    }
    private void onSwipeLeft(){
        currPos++;
        currPos%=images.length;
        imageSwitcher.setImageResource(images[currPos]);
    }

    private void makeList() {

        images=new int[7];
        images[0] = R.drawable.a;
        images[1] = R.drawable.b;
        images[2] = R.drawable.c;
        images[3] = R.drawable.d;
        images[4] = R.drawable.e;
        images[5] = R.drawable.f;
        images[6] = R.drawable.g;
    }


}
