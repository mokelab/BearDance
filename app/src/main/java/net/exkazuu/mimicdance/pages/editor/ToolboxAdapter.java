package net.exkazuu.mimicdance.pages.editor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.models.program.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ツールボックス用のAdapter
 */
public class ToolboxAdapter extends RecyclerView.Adapter<ToolboxAdapter.ViewHolder> {

    /**
     * コマンドがタップされたのを通知するためのinterface
     */
    public interface OnCommandClickListener {
        /**
         * コマンドがタップされたときに呼ばれます
         * @param command タップされたコマンド
         */
        void onCommandClick(String command);
    }

    private final LayoutInflater mInflater;
    private final OnCommandClickListener mListener;
    private final List<String> mCommandList = new ArrayList<>();

    public ToolboxAdapter(Context context, OnCommandClickListener listener) {
        mInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_toolbox, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String command = mCommandList.get(position);
        holder.mCommand = command;
        holder.mIcon.setImageResource(Command.getImage(command));
    }

    @Override
    public int getItemCount() {
        return mCommandList.size();
    }

    public void setCommands(String[] commands) {
        mCommandList.clear();
        mCommandList.addAll(Arrays.asList(commands));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.icon) ImageView mIcon;
        private final OnCommandClickListener mListener;
        private String mCommand;

        public ViewHolder(View itemView, OnCommandClickListener listener) {
            super(itemView);
            mListener = listener;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.icon)
        void iconClicked() {
            if (mListener == null) { return; }
            mListener.onCommandClick(mCommand);
        }
    }
}
