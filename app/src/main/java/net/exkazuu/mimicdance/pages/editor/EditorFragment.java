package net.exkazuu.mimicdance.pages.editor;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.models.program.Command;
import net.exkazuu.mimicdance.models.program.Program;
import net.exkazuu.mimicdance.models.program.ProgramDAO;
import net.exkazuu.mimicdance.models.program.ProgramDAOImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.fkmsoft.android.framework.util.FragmentUtils;

/**
 * プログラムを入力するためのFragment
 */
public class EditorFragment extends Fragment {
    private static final int STATE_SELECT_PROGRAM = 0;
    private static final int STATE_SELECT_COMMAND = 1;
    // 中断用
    private static final String STATE_PROGRAM_LIST = "programList";
    private static final String STATE_TAB_INDEX = "tabIndex";
    private static final String STATE_PAGE_STATE = "pageState";
    private static final String STATE_SELECTED_POSITION = "selectedPosition";
    private static final String STATE_SELECTED_INDEX = "selectedIndex";

    @Bind(R.id.root) View mRootView;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.tablayout) TabLayout mTabLayout;
    @Bind(R.id.recycler) public RecyclerView mRecyclerView;

    private ProgramAdapter mAdapter;
    private ProgramDAO mProgramDAO;
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

    public static EditorFragment newInstance() {
        EditorFragment fragment = new EditorFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mProgramDAO = new ProgramDAOImpl(getContext());
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
        // Toolbarの設定。タイトル文字列は消す
        activity.setSupportActionBar(mToolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) { actionBar.setTitle(""); }

        // 保存されているプログラムを読み込み
        List<Program> programList;
        int tabIndex;
        if (savedInstanceState == null) {
            programList = mProgramDAO.load();
            tabIndex = 0;
            mState = STATE_SELECT_PROGRAM;
            mSelectedPosition = -1;
            mSelectedIndex = -1;
        } else {
            Parcelable[] list = savedInstanceState.getParcelableArray(STATE_PROGRAM_LIST);
            tabIndex = savedInstanceState.getInt(STATE_TAB_INDEX);
            mState = savedInstanceState.getInt(STATE_PAGE_STATE);
            mSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mSelectedIndex = savedInstanceState.getInt(STATE_SELECTED_INDEX);
            if (list == null) {
                programList = mProgramDAO.load();
            } else {
                programList = new ArrayList<>(list.length);
                for (Parcelable p : list) {
                    programList.add((Program)p);
                }
            }
        }

        mAdapter = new ProgramAdapter(activity, programList, mItemClickListener);
        mAdapter.setSelected(mSelectedPosition, mSelectedIndex);
        mRecyclerView.setAdapter(mAdapter);
        mTouchHelper.attachToRecyclerView(mRecyclerView);
        TabLayout.Tab tab = mTabLayout.getTabAt(tabIndex);
        if (tab != null) { tab.select(); }

        if (savedInstanceState == null) {
            FragmentUtils.toNextFragment(getChildFragmentManager(), R.id.layout_toolbox,
                ToolboxFragment.newInstance(Command.GROUP_ACTION), false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArray(STATE_PROGRAM_LIST, mAdapter.getAsArray());
        outState.putInt(STATE_TAB_INDEX, mTabLayout.getSelectedTabPosition());
        outState.putInt(STATE_PAGE_STATE, mState);
        outState.putInt(STATE_SELECTED_POSITION, mSelectedPosition);
        outState.putInt(STATE_SELECTED_INDEX, mSelectedIndex);
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
        case R.id.action_save: // 保存
            mProgramDAO.save(mAdapter.getAsList());
            Snackbar.make(mRootView, R.string.save_done, Snackbar.LENGTH_LONG).show();
            return true;
        case R.id.action_reset: // やりなおし
            mState = STATE_SELECT_PROGRAM;
            mAdapter.clearProgram();
            mAdapter.setSelected(-1, -1);
            mAdapter.notifyDataSetChanged();
            return true;
        case R.id.action_quit:
            getFragmentManager().beginTransaction().remove(this).commit();
            getActivity().finish();
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
            Snackbar.make(mRootView, R.string.select_program_first, Snackbar.LENGTH_SHORT).show();
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

    protected void initTab() {
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
