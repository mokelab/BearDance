package net.exkazuu.mimicdance.pages.editor;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.models.program.Command;
import net.exkazuu.mimicdance.models.program.Program;
import net.exkazuu.mimicdance.viewholder.TextViewHolder;

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

    public ProgramAdapter(Context context, List<Program> list, OnItemClickListener listener) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListener = listener;
        mProgramList = list;
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

    public Program[] getAsArray() {
        return mProgramList.toArray(new Program[mProgramList.size()]);
    }

    public List<Program> getAsList() {
        return mProgramList;
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

        holder.bind(program, position, mSelectedPosition, mSelectedIndex);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.text_line_number) TextView lineNumberText;
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

        public void bind(Program program, int position, int selectedPosition, int selectedIndex) {
            Context context = mIcon0.getContext();

            this.lineNumberText.setText(String.valueOf(position + 1));
            mIcon0.setImageResource(Command.getImage(program.getCommand(0)));
            if (selectedPosition == position && selectedIndex == 0) {
                mIcon0.setBackgroundColor(ContextCompat.getColor(context, R.color.program_selected));
            } else {
                mIcon0.setBackgroundColor(ContextCompat.getColor(context, R.color.transparency));
            }

            mIcon1.setImageResource(Command.getImage(program.getCommand(1)));
            if (selectedPosition == position && selectedIndex == 1) {
                mIcon1.setBackgroundColor(ContextCompat.getColor(context, R.color.program_selected));
            } else {
                mIcon1.setBackgroundColor(ContextCompat.getColor(context, R.color.transparency));
            }
        }
    }
}
