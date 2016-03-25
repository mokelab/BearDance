package net.exkazuu.mimicdance.models.program;

import android.util.SparseArray;

import net.exkazuu.mimicdance.R;

import java.util.HashMap;
import java.util.Map;

/**
 * コマンド文字列の定数
 */
public class Command {
    public static final String RIGHT_HAND_UP = "right_hand_up";
    public static final String RIGHT_HAND_DOWN = "right_hand_down";
    public static final String LEFT_HAND_UP = "left_hand_up";
    public static final String LEFT_HAND_DOWN = "left_hand_down";

    public static final String IF = "if";
    public static final String ELSE = "else";
    public static final String END_IF = "endif";

    public static final String LOOP = "loop";
    public static final String END_LOOP = "end_loop";


    public static final String GMAIL = "gmail";
    public static final String TWITTER = "twitter";
    public static final String FACEBOOK = "facebook";
    public static final String CALENDER = "calender";

    public static final String NUMBER_0 = "0";
    public static final String NUMBER_1 = "1";
    public static final String NUMBER_2 = "2";
    public static final String NUMBER_3 = "3";
    public static final String NUMBER_4 = "4";
    public static final String NUMBER_5 = "5";
    public static final String NUMBER_6 = "6";
    public static final String NUMBER_7 = "7";
    public static final String NUMBER_8 = "8";
    public static final String NUMBER_9 = "9";

    private static final SparseArray<String[]> sCommandGroupMap;

    public static final int GROUP_ACTION = 0;
    public static final int GROUP_CONDITION = 1;
    public static final int GROUP_EVENT = 2;
    public static final int GROUP_NUMBER = 3;

    private static final Map<String, Integer> sCommandImageMap;


    static {
        // コマンドのグループを作っておく
        sCommandGroupMap = new SparseArray<>(4);
        sCommandGroupMap.put(GROUP_ACTION, new String[]{
            RIGHT_HAND_UP, RIGHT_HAND_DOWN, LEFT_HAND_UP, LEFT_HAND_DOWN
        });
        sCommandGroupMap.put(GROUP_CONDITION, new String[]{
            IF, ELSE, END_IF
        });
        sCommandGroupMap.put(GROUP_EVENT, new String[]{
            GMAIL, FACEBOOK, TWITTER, CALENDER
        });
        sCommandGroupMap.put(GROUP_NUMBER, new String[]{
            LOOP, END_LOOP, 
            NUMBER_1, NUMBER_2, NUMBER_3, NUMBER_4, NUMBER_5,
            NUMBER_6, NUMBER_7, NUMBER_8, NUMBER_9
        });

        // コマンドとアイコンの対応表
        sCommandImageMap = new HashMap<>();
        sCommandImageMap.put(RIGHT_HAND_UP, R.drawable.icon_right_hand_up);
        sCommandImageMap.put(RIGHT_HAND_DOWN, R.drawable.icon_right_hand_down);
        sCommandImageMap.put(LEFT_HAND_UP, R.drawable.icon_left_hand_up);
        sCommandImageMap.put(LEFT_HAND_DOWN, R.drawable.icon_left_hand_down);
        sCommandImageMap.put(NUMBER_0, R.drawable.icon_num0);
        sCommandImageMap.put(NUMBER_1, R.drawable.icon_num1);
        sCommandImageMap.put(NUMBER_2, R.drawable.icon_num2);
        sCommandImageMap.put(NUMBER_3, R.drawable.icon_num3);
        sCommandImageMap.put(NUMBER_4, R.drawable.icon_num4);
        sCommandImageMap.put(NUMBER_5, R.drawable.icon_num5);
        sCommandImageMap.put(NUMBER_6, R.drawable.icon_num6);
        sCommandImageMap.put(NUMBER_7, R.drawable.icon_num7);
        sCommandImageMap.put(NUMBER_8, R.drawable.icon_num8);
        sCommandImageMap.put(NUMBER_9, R.drawable.icon_num9);
        sCommandImageMap.put(IF, R.drawable.icon_if);
        sCommandImageMap.put(ELSE, R.drawable.icon_else);
        sCommandImageMap.put(END_IF, R.drawable.icon_end_if);
        sCommandImageMap.put(LOOP, R.drawable.icon_loop);
        sCommandImageMap.put(END_LOOP, R.drawable.icon_end_loop);
        sCommandImageMap.put(GMAIL,R.drawable.icon_gmail);
        sCommandImageMap.put(TWITTER,R.drawable.icon_gmail);
        sCommandImageMap.put(GMAIL,R.drawable.icon_gmail);
        sCommandImageMap.put(TWITTER,R.drawable.icon_twitter);
        sCommandImageMap.put(FACEBOOK,R.drawable.icon_fb);
        sCommandImageMap.put(CALENDER,R.drawable.icon_calender);
    }

    public static String[] getByGroup(int group) {
        return sCommandGroupMap.get(group);
    }

    public static int getImage(String command) {
        Integer value = sCommandImageMap.get(command);
        return value == null ? R.drawable.icon_writable : value;
    }
}
