package net.exkazuu.mimicdance.activities.notification;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.models.program.Command;
import net.exkazuu.mimicdance.models.program.Program;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * プログラム部分(下)用のAdapter
 */
public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position, int index);
    }

    private final Context mContext;
    private final LayoutInflater mInflater;
    private final OnItemClickListener mListener;

    private List<Program> mProgramList;
    private int mSelectedPosition;
    private int mSelectedIndex;

    public ProgramAdapter(Context context, OnItemClickListener listener) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListener = listener;
        mProgramList = new ArrayList<>(12);
        for (int i = 0 ; i < 12 ; ++i) {
            mProgramList.add(new Program());
        }
        // 最初は未選択状態
        mSelectedPosition = -1;
        mSelectedIndex = -1;
    }

    public void swapProgram(int from, int to) {
        Collections.swap(mProgramList, from, to);
    }

    public void setCommand(int position, int index, String command) {
        Program program = mProgramList.get(position);
        program.setCommands(index, command);
    }

    public void setSelected(int position, int index) {
        mSelectedPosition = position;
        mSelectedIndex = index;
    }

    /**
     * 全プログラムを空にします
     */
    public void clearProgram() {
        for (Program p : mProgramList) {
            p.setCommands(0, "");
            p.setCommands(1, "");
        }
    }

    @Override
    public int getItemCount() {
        return mProgramList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_command, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Program program = mProgramList.get(position);
        holder.mIcon0.setImageResource(Command.getImage(program.getCommand(0)));
        if (mSelectedPosition == position && mSelectedIndex == 0) {
            holder.mIcon0.setBackgroundColor(ContextCompat.getColor(mContext, R.color.program_selected));
        } else {
            holder.mIcon0.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparency));
        }

        holder.mIcon1.setImageResource(Command.getImage(program.getCommand(1)));
        if (mSelectedPosition == position && mSelectedIndex == 1) {
            holder.mIcon1.setBackgroundColor(ContextCompat.getColor(mContext, R.color.program_selected));
        } else {
            holder.mIcon1.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparency));
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.icon_0) ImageView mIcon0;
        @Bind(R.id.icon_1) ImageView mIcon1;

        private final OnItemClickListener mListener;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mListener = listener;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.icon_0)
        void icon0Clicked() {
            if (mListener == null) { return; }
            mListener.onItemClick(getAdapterPosition(), 0);
        }

        @OnClick(R.id.icon_1)
        void icon1Clicked() {
            if (mListener == null) { return; }
            mListener.onItemClick(getAdapterPosition(), 1);
        }
    }
}
