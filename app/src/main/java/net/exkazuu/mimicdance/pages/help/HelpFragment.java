package net.exkazuu.mimicdance.pages.help;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import net.exkazuu.mimicdance.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * あそびかた画面
 */
public class HelpFragment extends Fragment {
    private final int[] HELP_IMAGE_RESOURCES = {R.drawable.tutorial1, R.drawable.tutorial2,
        R.drawable.tutorial3, R.drawable.tutorial4, R.drawable.tutorial5,
        R.drawable.helptext1, R.drawable.helptext2};

    @Bind(R.id.page) EditText pageText;
    @Bind(R.id.helpImage) ImageView image;
    private int pageNumber;

    public static HelpFragment newInstance() {
        HelpFragment fragment = new HelpFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.help, container, false);

        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        update();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    // region UI event

    @OnClick(R.id.button1)
    void prevClicked() {
        if (pageNumber == 0) {
            pageNumber = HELP_IMAGE_RESOURCES.length - 1;
        } else {
            --pageNumber;
        }
        update();
    }

    @OnClick(R.id.button2)
    void nextClicked() {
        pageNumber = (pageNumber + 1) % HELP_IMAGE_RESOURCES.length;
        update();
    }

    @OnClick(R.id.button3)
    void closeClicked() {
        FragmentManager manager = getFragmentManager();
        if (manager == null) { return; }
        manager.popBackStack();
    }

    // endregion

    private void update() {
        pageText.setText(getString(R.string.help_page, (pageNumber + 1), HELP_IMAGE_RESOURCES.length));
        image.setImageResource(HELP_IMAGE_RESOURCES[pageNumber]);
    }
}
