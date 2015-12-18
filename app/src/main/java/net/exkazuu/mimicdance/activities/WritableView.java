package net.exkazuu.mimicdance.activities;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.exkazuu.mimicdance.R;

/**
 * Created by t-yokoi on 2015/11/27.
 */
public class WritableView extends ImageView {
    private TextView resultText;

//    private final Bitmap writable = Bitmap.createScaledBitmap(
//        BitmapFactory.decodeResource(getContext().getResources()
//            , R.drawable.icon_writable), 200, 200, true);

    private final Bitmap writable = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.icon_writable);

    private Bitmap overWideBitmap = writable;
    private int x;
    private int y;
    private String command;

//    private boolean isDragging;
//    private boolean isHovering;
//    private boolean isDropped;

    private int droppenId;
    private Bitmap bitmap;

    public WritableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                Log.v("onTouch", "action=" + action);
                if (action == MotionEvent.ACTION_MOVE) {
                    ClipData data = ClipData.newPlainText("actionID", WritableView.this.x + "," + WritableView.this.y + "," + WritableView.this.command);
                    v.startDrag(data, new DragShadowBuilder(v), null, 0);
                    Log.v("onTouch", "called");
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
                        Log.v("onDrag", "called");
                        Toast.makeText(getContext(), "Dropしてません", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
        bitmap = Bitmap.createScaledBitmap(overWideBitmap, 200, 200, true);
    }

    public void setResultTextView(TextView resultText) {
        this.resultText = resultText;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (isDragging) {
//            if (isHovering) {
//                Log.i("GridView", "isDragging & isHovering");
//                bitmap = writable;
//            } else {
//                Log.i("GridView", "isDragging");
//                bitmap = writable;
//            }
//        } else if (isDropped) {
//            Log.i("GridView", "isDropped");
//            //画像差し替え？
//            bitmap = overWideBitmap;
//        }

        float x = (getWidth() - bitmap.getWidth()) / 2;
        float y = (getHeight() - bitmap.getHeight()) / 2;
        canvas.drawBitmap(bitmap, x, y, null);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        setMeasuredDimension(writable.getWidth(), writable.getHeight());
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
//                isDragging = true;
//                isHovering = false;
//                isDropped = false;
//                invalidate();
                break;

            case DragEvent.ACTION_DRAG_ENDED:
//                isDragging = false;
//                isHovering = false;
//                isDropped = false;
//                invalidate();
                break;

            case DragEvent.ACTION_DRAG_LOCATION:
                break;

            case DragEvent.ACTION_DROP:
//                isDragging = false;
//                isHovering = false;

                ClipData data = event.getClipData();
                if (data.getItemCount() != 1) {
                    return true;
                }

                ClipData.Item item = data.getItemAt(0);
                String sid = (item.coerceToText(getContext())).toString();
                String[] values = sid.split(",", -1);
                int x = Integer.parseInt(values[0]);
                int y = Integer.parseInt(values[1]);
                String command = values[2];

                if (x == -1 || y == -1) {
                    this.command = command;
                    //画像設定
                    this.setImageResource(getImageCommand(command));
                } else {
                    int id = this.getResources().getIdentifier("canwrite" + y + "_" + x, "id", getContext().getPackageName());
                    Log.v("tag", "x=" + x + ",y=" + y + ",id=" + id);
                    WritableView fromView = (WritableView) ((ViewGroup) getParent()).findViewById(id);
                    fromView.command = this.command;
                    fromView.setImageResource(getImageCommand(fromView.command));
                    this.command = command;
                    this.setImageResource(getImageCommand(this.command));
                    //移動元とここの画像設定

                }
                break;

            case DragEvent.ACTION_DRAG_ENTERED:
//                isHovering = true;
//                invalidate();
                break;

            case DragEvent.ACTION_DRAG_EXITED:
//                isHovering = false;
//                invalidate();
                break;
        }

        return true;
    }

    private int getImageCommand(String command) {
        if ("right_hand_up".equals(command)) {
            return R.drawable.icon_right_hand_up;
        } else if ("right_hand_down".equals(command)) {
            return R.drawable.icon_right_hand_down;
        } else {
            return R.drawable.icon_none;
        }
        //コマンド名から画像IDを取得する(列挙する)
    }

    public void setLocate(int i, int j) {
        this.x = i;
        this.y = j;
    }

    public void setCommand(String s) {
        this.command = s;
    }


}
