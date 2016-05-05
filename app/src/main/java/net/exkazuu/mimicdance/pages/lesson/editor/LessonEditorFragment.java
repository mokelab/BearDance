package net.exkazuu.mimicdance.pages.lesson.editor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.pages.editor.EditorFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Editor for Lesson
 */
public class LessonEditorFragment extends EditorFragment {
    public static LessonEditorFragment newInstance(Fragment target, int requestCode) {
        LessonEditorFragment fragment = new LessonEditorFragment();
        fragment.setTargetFragment(target, requestCode);

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lesson_editor, container, false);

        ButterKnife.bind(this, root);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.HORIZONTAL, false));
        initTab();

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_lesson_editor, menu);
    }

    @OnClick(R.id.button_back)
    void backClicked() {
        FragmentManager manager = getFragmentManager();
        if (manager == null) { return; }
        manager.popBackStack();
    }
}
