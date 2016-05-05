package net.exkazuu.mimicdance.pages.lesson.top;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.models.computer.Computer;
import net.exkazuu.mimicdance.models.computer.ComputerImpl;
import net.exkazuu.mimicdance.models.lesson.LessonDAO;
import net.exkazuu.mimicdance.models.lesson.LessonDAOImpl;
import net.exkazuu.mimicdance.models.program.Program;
import net.exkazuu.mimicdance.models.robot.CoccoRobot;
import net.exkazuu.mimicdance.models.robot.PiyoRobot;
import net.exkazuu.mimicdance.models.robot.Robot;
import net.exkazuu.mimicdance.pages.editor.EditorFragment;
import net.exkazuu.mimicdance.pages.lesson.editor.LessonEditorFragment;
import net.exkazuu.mimicdance.pages.lesson.preview.ComputeRunnable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.fkmsoft.android.framework.util.FragmentUtils;

/**
 * Framgment for Lesson top page
 */
public class LessonTopFragment extends Fragment {
    private static final String ARGS_LESSON_NUMBER = "lessonNumber";

    private static final int REQUEST_WRITE = 1000;

    private int lessonNumber;

    @Bind(R.id.character_left) View leftCharacter;
    @Bind(R.id.character_right) View rightCharacter;

    private LessonDAO lessonDAO;

    private Computer leftComputer;
    private Robot leftRobot;
    private Computer rightComputer;
    private Robot rightRobot;

    private Handler handler;

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

        this.handler = new Handler();
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

        this.lessonDAO = new LessonDAOImpl(getActivity());
        this.leftRobot = new PiyoRobot(this.leftCharacter);
        this.rightRobot = new CoccoRobot(this.rightCharacter);
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

    @OnClick(R.id.button_write)
    void writeClicked() {
        FragmentUtils.toNextFragment(getFragmentManager(), R.id.container,
            LessonEditorFragment.newInstance(this, REQUEST_WRITE), true);
    }

    @OnClick(R.id.button_move)
    void moveClicked() {
        if (this.leftComputer != null) {
            this.leftComputer.stop();
            this.leftRobot.reset();
        }
        if (this.rightComputer != null) {
            this.rightComputer.stop();
            this.rightRobot.reset();
        }

        this.leftComputer  = new ComputerImpl(this.lessonDAO.getLessonProgram(this.lessonNumber, LessonDAO.POSITION_LEFT));
        this.rightComputer = new ComputerImpl(this.lessonDAO.getLessonProgram(this.lessonNumber, LessonDAO.POSITION_RIGHT));

        handler.post(new ComputeRunnable(this.leftComputer, this.leftRobot, handler));
        handler.post(new ComputeRunnable(this.rightComputer, this.rightRobot, handler));
/*
        if (this.executor != null) {
            this.executor.cancel(true);
        }
        this.executor = new CommandExecutor(this.coccoProgram, this.altCoccoProgram,
            this.coccoViewSet, this.altCoccoViewSet);
        this.executor.execute();
*/
    }

    // endregion
}
