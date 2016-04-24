package net.exkazuu.mimicdance.pages.lesson.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.viewholder.TextViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for lesson list
 */
public class LessonListAdapter extends RecyclerView.Adapter<TextViewHolder> {

    private final LayoutInflater inflater;
    private final View.OnClickListener listener;
    private final List<String> list = new ArrayList<>();

    public LessonListAdapter(@NonNull Context context, View.OnClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    public void addItem(String item) {
        list.add(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TextViewHolder(this.inflater.inflate(R.layout.item_lesson_list, parent, false), this.listener);
    }

    @Override
    public void onBindViewHolder(TextViewHolder holder, int position) {
        holder.bind(list.get(position), position);
    }
}
