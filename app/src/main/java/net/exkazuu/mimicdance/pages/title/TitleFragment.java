package net.exkazuu.mimicdance.pages.title;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.activities.HelpActivity;
import net.exkazuu.mimicdance.activities.LessonListActivity;
import net.exkazuu.mimicdance.activities.TitleActivity;
import net.exkazuu.mimicdance.activities.notification.NotificationActivity;
import net.exkazuu.mimicdance.models.lesson.LessonDAO;
import net.exkazuu.mimicdance.pages.help.HelpFragment;
import net.exkazuu.mimicdance.pages.lesson.list.LessonListFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.fkmsoft.android.framework.util.FragmentUtils;

/**
 * タイトル画面
 */
public class TitleFragment extends Fragment {

    private LessonDAO lessonDAO;

    public static TitleFragment newInstance() {
        TitleFragment fragment = new TitleFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.title, container, false);

        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TitleActivity activity = (TitleActivity) getActivity();
        lessonDAO = activity.getLessonDAO();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    // regions UI event

    @OnClick(R.id.help_button)
    void helpClicked() {
        lessonDAO.upload();
        FragmentManager manager = getFragmentManager();
        if (manager == null) { return; }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack("");
        transaction.replace(R.id.container, HelpFragment.newInstance());
        transaction.commit();

        /*
        Intent intent = new Intent(getActivity(), HelpActivity.class);
        startActivity(intent);
        */
    }


    @OnClick(R.id.notification_button)
    void notificationClicked() {
        //lessonDAO.upload();
        Intent intent = new Intent(getActivity(), NotificationActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.start_button)
    void startClicked() {
        //lessonDAO.upload();
        FragmentUtils.toNextFragment(getFragmentManager(), R.id.container,
            LessonListFragment.newInstance(), true);
    }

    // endregion
}
