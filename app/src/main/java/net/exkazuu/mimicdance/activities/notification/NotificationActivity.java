package net.exkazuu.mimicdance.activities.notification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import net.exkazuu.mimicdance.R;

import jp.fkmsoft.android.framework.util.FragmentUtils;

/**
 * 通知のプログラムを入力するためのActivity
 */
public class NotificationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        if (savedInstanceState == null) {
            FragmentUtils.toNextFragment(getSupportFragmentManager(), R.id.container,
                NotificationFragment.newInstance(), false);
        }
    }
}
