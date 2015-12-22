package net.exkazuu.mimicdance.activities.notification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.models.program.Command;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.fkmsoft.android.framework.util.FragmentUtils;

/**
 * 通知用のプログラムを入力するためのFragment
 */
public class NotificationFragment extends Fragment {
    private static final int STATE_SELECT_PROGRAM = 0;
    private static final int STATE_SELECT_COMMAND = 1;

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.tablayout) TabLayout mTabLayout;
    @Bind(R.id.recycler) RecyclerView mRecyclerView;

    private ProgramAdapter mAdapter;
    /**
     * 選択の状態
     */
    private int mState;
    /**
     * 選択されたプログラムの位置
     */
    private int mSelectedPosition;
    /**
     * 選択されたプログラムの0個目/1個目
     */
    private int mSelectedIndex;

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mState = STATE_SELECT_PROGRAM;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, root);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.HORIZONTAL, false));
        initTab();

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) { actionBar.setTitle(""); }

        mAdapter = new ProgramAdapter(activity, mItemClickListener);
        mRecyclerView.setAdapter(mAdapter);
        mTouchHelper.attachToRecyclerView(mRecyclerView);
        if (savedInstanceState == null) {
            FragmentUtils.toNextFragment(getChildFragmentManager(), R.id.layout_toolbox,
                ToolboxFragment.newInstance(Command.GROUP_ACTION), false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_trash:
            // ゴミ箱は、選択した枠を空にする処理
            onCommandClicked("");
            return true;
        case R.id.action_reset: // やりなおし
            mState = STATE_SELECT_PROGRAM;
            mAdapter.clearProgram();
            mAdapter.setSelected(-1, -1);
            mAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * {@link ToolboxFragment} でコマンドが選択された時に呼ばれます
     * @param command 選択されたコマンド
     */
    public void onCommandClicked(String command) {
        if (mState == STATE_SELECT_PROGRAM) {
            // 先にプログラムの枠を選んでもらうので、何もしない
        } else if (mState == STATE_SELECT_COMMAND) {
            mAdapter.setCommand(mSelectedPosition, mSelectedIndex, command);
            mAdapter.setSelected(-1, -1);
            mAdapter.notifyDataSetChanged();
            mState = STATE_SELECT_PROGRAM;
        }
    }

    /**
     * プログラムの枠が選択されたときに呼ばれます
     */
    private ProgramAdapter.OnItemClickListener mItemClickListener = new ProgramAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position, int index) {
            if (mState == STATE_SELECT_PROGRAM) {
                mSelectedPosition = position;
                mSelectedIndex = index;
                mAdapter.setSelected(position, index);
                mAdapter.notifyDataSetChanged();
                mState = STATE_SELECT_COMMAND;
            } else if (mState == STATE_SELECT_COMMAND) {
                mSelectedPosition = position;
                mSelectedIndex = index;
                mAdapter.setSelected(position, index);
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    // region Tab

    private void initTab() {
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.tab_action)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.tab_condition)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.tab_event)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.tab_repeat)));

        mTabLayout.setOnTabSelectedListener(mTabSelectedListener);
    }

    private TabLayout.OnTabSelectedListener mTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int type;
            switch (tab.getPosition()) {
            case 0: // アクション
                type = Command.GROUP_ACTION;
                break;
            case 1: // 場合分け
                type = Command.GROUP_CONDITION;
                break;
            case 2: // イベント
                type = Command.GROUP_EVENT;
                break;
            case 3: // 繰り返し
                type = Command.GROUP_NUMBER;
                break;
            default:
                return;
            }
            FragmentManager manager = getChildFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            transaction.replace(R.id.layout_toolbox, ToolboxFragment.newInstance(type));

            transaction.commit();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    // endregion

    // region RecyclerView

    private final ItemTouchHelper mTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG, ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            mAdapter.swapProgram(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            mAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }
    });
}
