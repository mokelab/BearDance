package net.exkazuu.mimicdance.pages.lesson.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.exkazuu.mimicdance.Lessons;
import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.activities.CoccoActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fragment for lesson list
 */
public class LessonListFragment extends Fragment {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.recycler) RecyclerView recyclerView;

    public static LessonListFragment newInstance() {
        LessonListFragment fragment = new LessonListFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lesson_list, container, false);

        ButterKnife.bind(this, root);

        toolbar.setTitle(R.string.title_lesson_list);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(this.backListener);

        Context context = inflater.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        LessonListAdapter adapter = new LessonListAdapter(context, this.clickListener);
        int lessonCount = Lessons.getLessonCount();
        for (int i = 1; i <= lessonCount; ++i) {
            adapter.addItem("レッスン" + i);
        }
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager manager = getFragmentManager();
            if (manager == null) { return; }
            manager.popBackStack();
        }
    };

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            Intent intent = new Intent(getActivity(), CoccoActivity.class);
            intent.putExtra("lessonNumber", position + 1);
            intent.putExtra("piyoCode", "");
            startActivity(intent);
        }
    };
}
