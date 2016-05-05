package net.exkazuu.mimicdance.pages.lesson.top;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.exkazuu.mimicdance.CharacterImageViewSet;
import net.exkazuu.mimicdance.CommandExecutor;
import net.exkazuu.mimicdance.Lessons;
import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.program.Block;
import net.exkazuu.mimicdance.program.CodeParser;
import net.exkazuu.mimicdance.program.UnrolledProgram;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Framgment for Lesson top page
 */
public class LessonTopFragment extends Fragment {
    private static final String ARGS_LESSON_NUMBER = "lessonNumber";

    private int lessonNumber;
    private UnrolledProgram coccoProgram;
    private UnrolledProgram altCoccoProgram;
    private CharacterImageViewSet coccoViewSet;
    private CharacterImageViewSet altCoccoViewSet;

    private CommandExecutor executor;

    public static LessonTopFragment newInstance(int lessonNumber) {
        LessonTopFragment fragment = new LessonTopFragment();

        Bundle args = new Bundle();
        args.putInt(ARGS_LESSON_NUMBER, lessonNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        this.lessonNumber = args.getInt(ARGS_LESSON_NUMBER);

        String coccoCode = Lessons.getCoccoCode(lessonNumber);
        Block coccoBlock = CodeParser.parse(coccoCode);
        coccoProgram = coccoBlock.unroll(true);
        altCoccoProgram = coccoBlock.unroll(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lesson_top, container, false);

        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        coccoViewSet = CharacterImageViewSet.createCoccoLeft(getActivity());
        altCoccoViewSet = CharacterImageViewSet.createCoccoRight(getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    // region UI event

    @OnClick(R.id.button_back)
    void backClicked() {
        FragmentManager manager = getFragmentManager();
        if (manager == null) { return; }
        manager.popBackStack();
    }

    @OnClick(R.id.button_move)
    void moveClicked() {
        if (this.executor != null) {
            this.executor.cancel(true);
        }
        this.executor = new CommandExecutor(this.coccoProgram, this.altCoccoProgram,
            this.coccoViewSet, this.altCoccoViewSet);
        this.executor.execute();
    }

    // endregion
}
