package net.cattaka.android.adaptertoolbox.example.utils;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import net.cattaka.android.adaptertoolbox.example.R;

import java.util.List;

/**
 * Created by cattaka on 17/01/02.
 */

public class FlashColorItemAnimator extends DefaultItemAnimator {
    public enum FlashColor {
        RED(Color.RED),
        BLUE(Color.BLUE),
        WHITE(Color.WHITE),
        //
        ;
        public final int intValue;

        FlashColor(int intValue) {
            this.intValue = intValue;
        }
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
        {   // resetAnimation
            oldHolder.itemView.clearAnimation();
            endAnimation(oldHolder);
        }
        if (newHolder != null) {
            // carry over translation values
            newHolder.itemView.clearAnimation();
            endAnimation(newHolder);

            FlashColor flashColor = (FlashColor) newHolder.itemView.getTag(R.id.flash_color_on_change);
            if (flashColor == null) {
                flashColor = FlashColor.WHITE;
            }
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(
                    ObjectAnimator.ofObject(newHolder.itemView, "backgroundColor", new ArgbEvaluator(), flashColor.intValue, Color.WHITE),
                    ObjectAnimator.ofFloat(newHolder.itemView, View.ALPHA, 0, 1)
            );
            animatorSet.start();
        }
        return true;
    }

    @NonNull
    @Override
    public ItemHolderInfo recordPreLayoutInformation(@NonNull RecyclerView.State state, @NonNull RecyclerView.ViewHolder viewHolder, int changeFlags, @NonNull List<Object> payloads) {
        Object flashColorOnChange = (payloads.size() > 0) ? payloads.get(0) : null;
        if (flashColorOnChange instanceof FlashColor) {
            viewHolder.itemView.setTag(R.id.flash_color_on_change, flashColorOnChange);
        } else {
            viewHolder.itemView.setTag(R.id.flash_color_on_change, null);
        }
        return super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads);
    }

    @Override
    public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder) {
        return true;
    }
}
