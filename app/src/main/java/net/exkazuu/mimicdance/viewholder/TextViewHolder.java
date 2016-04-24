package net.exkazuu.mimicdance.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.exkazuu.mimicdance.R;

/**
 * ViewHolder for Text
 */
public class TextViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public static TextViewHolder create(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, View.OnClickListener listener) {
        View root = inflater.inflate(R.layout.item_text_list, parent, false);
        return new TextViewHolder(root, listener);
    }

    public TextViewHolder(View itemView, View.OnClickListener listener) {
        super(itemView);
        this.textView = (TextView) itemView;
        itemView.setOnClickListener(listener);
    }

    public void bind(String text, int position) {
        this.textView.setText(text);
        this.textView.setTag(position);
    }
}
