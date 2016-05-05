package net.exkazuu.mimicdance.models.robot;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.models.program.Command;
import net.exkazuu.mimicdance.models.program.Program;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Implementation
 */
public class CoccoRobot implements Robot {
    @Bind(R.id.image_left_hand) ImageView leftHandImage;
    @Bind(R.id.image_right_hand) ImageView rightHandImage;

    private int leftHand;
    private int rightHand;

    public CoccoRobot(View root) {
        this.leftHand = 0;
        this.rightHand = 0;

        ButterKnife.bind(this, root);
    }

    @Override
    public void execCommand(Program program) {
        String command = program.getCommand(0);
        switch (command) {
        case Command.LEFT_HAND_UP: {
            ++this.leftHand;
            updateLeftHand();
        } break;
        case Command.LEFT_HAND_DOWN: {
            --this.leftHand;
            updateLeftHand();
        } break;
        case Command.RIGHT_HAND_UP: {
            ++this.rightHand;
            updateRightHand();
        } break;
        case Command.RIGHT_HAND_DOWN: {
            --this.rightHand;
            updateRightHand();
        } break;
        }

        Log.v("Robot", "left=" + this.leftHand + " right=" + this.rightHand);
    }

    @Override
    public void reset() {
        this.leftHand = 0;
        this.rightHand = 0;
        updateLeftHand();
        updateRightHand();
    }

    private void updateLeftHand() {
        switch (this.leftHand) {
        case 0:
            this.leftHandImage.setImageResource(R.drawable.alt_cocco_left_hand_up1);
            break;
        case 1:
            this.leftHandImage.setImageResource(R.drawable.alt_cocco_left_hand_up2);
            break;
        case 2:
            this.leftHandImage.setImageResource(R.drawable.alt_cocco_left_hand_up3);
            break;
        }
    }

    private void updateRightHand() {
        switch (this.rightHand) {
        case 0:
            this.rightHandImage.setImageResource(R.drawable.alt_cocco_right_hand_up1);
            break;
        case 1:
            this.rightHandImage.setImageResource(R.drawable.alt_cocco_right_hand_up2);
            break;
        case 2:
            this.rightHandImage.setImageResource(R.drawable.alt_cocco_right_hand_up3);
            break;
        }
    }
}
