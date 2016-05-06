package net.exkazuu.mimicdance.pages.lesson.editor;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.models.program.Program;
import net.exkazuu.mimicdance.pages.editor.EditorFragment;
import net.exkazuu.mimicdance.pages.editor.PreviewFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Editor for Lesson
 */
public class LessonEditorFragment extends EditorFragment {
    private static final String TRANSITION_NAME_CHARACTER = "character";

    @Bind(R.id.root) View mRootView2;
    @Bind(R.id.toolbar) Toolbar mToolbar2;
    @Bind(R.id.tablayout) TabLayout mTabLayout2;
    @Bind(R.id.recycler) RecyclerView mRecyclerView2;
    @Bind(R.id.character) View characterView;

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
        mRootView = mRootView2;
        mToolbar = mToolbar2;
        mTabLayout = mTabLayout2;
        mRecyclerView = mRecyclerView2;

        mRecyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.HORIZONTAL, false));
        initTab();

        ViewCompat.setTransitionName(characterView, TRANSITION_NAME_CHARACTER);

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

    @OnClick(R.id.button_check_program)
    void checkProgramClicked() {
        // Get current program
        List<Program> programList = mAdapter.getAsList();

        FragmentManager manager = getFragmentManager();
        if (manager == null) { return; }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack("");

        String transitionName = ViewCompat.getTransitionName(characterView);
        PreviewFragment next = PreviewFragment.newInstance(programList, transitionName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransitionInflater inflater = TransitionInflater.from(getContext());
            setSharedElementReturnTransition(inflater.inflateTransition(R.transition.change_image_trans));
            setExitTransition(inflater.inflateTransition(android.R.transition.fade));

            next.setSharedElementEnterTransition(inflater.inflateTransition(R.transition.change_image_trans));
            next.setEnterTransition(inflater.inflateTransition(android.R.transition.fade));
        }

        transaction.replace(R.id.container, next);
        transaction.addSharedElement(characterView, transitionName);

        transaction.commit();
    }
}
