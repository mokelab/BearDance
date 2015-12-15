package net.exkazuu.mimicdance.activities;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.exkazuu.mimicdance.R;

public class ProgramIconView extends ImageView {
    private TextView statusView;
//    private TextView resultText;

    private Bitmap action;
    private String command;

    public ProgramIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.action = BitmapFactory.decodeResource(getContext().getResources()
                , R.drawable.icon_none);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_MOVE) {
                    ClipData data = ClipData.newPlainText("actionID", "-1,-1," + ProgramIconView.this.command);
                    v.startDrag(data, new DragShadowBuilder(v), null, 0);
                    return true;
                } else if (action == MotionEvent.ACTION_DOWN) {
                    return true;
                }
                return false;
            }
        });

        setOnDragListener(new OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                if (event.getAction() == DragEvent.ACTION_DRAG_ENDED) {
                    if (!event.getResult()) {
                        Toast.makeText(getContext(), "Dropしてません", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
    }

    /*
    @Override
    protected void onDraw(Canvas canvas) {
        float x = (getWidth() - action.getWidth()) / 2;
        float y = (getHeight() - action.getHeight()) / 2;

        canvas.drawBitmap(action, x, y, null);
    }
    */

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        setMeasuredDimension(action.getWidth(), action.getHeight());
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
//                statusView.setText("ACTION_DRAG_STARTED");
//                resultText.setText(null);
                break;

            case DragEvent.ACTION_DRAG_ENDED:
//                statusView.setText("ACTION_DRAG_ENDED");
                break;

            case DragEvent.ACTION_DRAG_LOCATION:
//                statusView.setText("ACTION_DRAG_LOCATION");
                break;

            case DragEvent.ACTION_DROP:
//                statusView.setText("ACTION_DROP");
//                resultText.setText("Dropped on Honeycomb");
                break;

            case DragEvent.ACTION_DRAG_ENTERED:
//                statusView.setText("ACTION_DRAG_ENTERED");
                break;

            case DragEvent.ACTION_DRAG_EXITED:
//                statusView.setText("ACTION_DRAG_EXITED");
                break;
        }

        return true;
    }

    public void setCommand(String s) {
        this.command = s;
    }


}
