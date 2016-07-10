package com.geeky.adarsh.moviescollection.AnimationUtils;

import android.support.v7.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


public class AnimUtil {

    public static void animate(RecyclerView.ViewHolder holder, boolean goesDown) {
        YoYo.with(Techniques.SlideInUp)
                .duration(500)
                .playOn(holder.itemView);
//        AnimatorSet animatorSet = new AnimatorSet();
//        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(holder.itemView, "scaleX" ,0.5F, 0.8F, 1.0F);
//        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(holder.itemView, "scaleY", 0.5F, 0.8F, 1.0F);
//        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", goesDown?100:-100, 0);
//        animatorTranslateY.setDuration(500);
//        animatorTranslateY.start();
    }
}
