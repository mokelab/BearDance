package net.exkazuu.mimicdance.pages.editor;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.models.computer.Computer;
import net.exkazuu.mimicdance.models.computer.ComputerImpl;
import net.exkazuu.mimicdance.models.program.Program;
import net.exkazuu.mimicdance.models.robot.PiyoRobot;
import net.exkazuu.mimicdance.models.robot.Robot;
import net.exkazuu.mimicdance.pages.lesson.preview.ComputeRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fragment for Preview
 */
public class PreviewFragment extends Fragment {
    private static final String ARGS_PROGRAM = "program";
    private static final String ARGS_TRANSITION_NAME = "transitionName";

    private List<Program> programList;
    private String transitionName;

    @Bind(R.id.character) View characterView;

    private Handler handler;
    private Computer computer;
    private Robot robot;

    public static PreviewFragment newInstance(List<Program> programList, String transitionName) {
        PreviewFragment fragment = new PreviewFragment();

        Bundle args = new Bundle();
        args.putParcelableArray(ARGS_PROGRAM, programList.toArray(new Program[programList.size()]));
        args.putString(ARGS_TRANSITION_NAME, transitionName);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        Parcelable[] array = args.getParcelableArray(ARGS_PROGRAM);
        if (array != null) {
            this.programList = new ArrayList<>(array.length);
            for (Parcelable p : array) {
                this.programList.add((Program)p);
            }
        } else {
            this.programList = Collections.emptyList();
        }
        this.transitionName = args.getString(ARGS_TRANSITION_NAME);

        this.handler = new Handler();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_editor_preview, container, false);

        ButterKnife.bind(this, root);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bundle args = getArguments();
            this.characterView.setTransitionName(transitionName);
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.computer  = new ComputerImpl(this.programList);
        this.robot = new PiyoRobot(characterView);

        handler.post(new ComputeRunnable(this.computer, this.robot, handler));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }
}
