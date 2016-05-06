package net.exkazuu.mimicdance.pages.editor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.activities.notification.NotificationFragment;
import net.exkazuu.mimicdance.models.program.Command;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ツールボックスを表示するためのFragment
 */
public class ToolboxFragment extends Fragment {
    private static final String ARGS_TYPE = "type";

    @Bind(R.id.recycler) RecyclerView mRecyclerView;

    private int mGroup;

    private ToolboxAdapter mAdapter;

    public static ToolboxFragment newInstance(int type) {
        ToolboxFragment fragment = new ToolboxFragment();

        Bundle args = new Bundle();
        args.putInt(ARGS_TYPE, type);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mGroup = args.getInt(ARGS_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_toolbox, container, false);

        ButterKnife.bind(this, root);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(10, StaggeredGridLayoutManager.VERTICAL));

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        mAdapter = new ToolboxAdapter(activity, mClickListener);
        mAdapter.setCommands(Command.getByGroup(mGroup));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    // region UI event

    private ToolboxAdapter.OnCommandClickListener mClickListener = new ToolboxAdapter.OnCommandClickListener() {
        @Override
        public void onCommandClick(String command) {
            // 親にコマンドが選択されたことを伝える
            EditorFragment parent = (EditorFragment) getParentFragment();
            parent.onCommandClicked(command);
        }
    };

    // endregion
}
