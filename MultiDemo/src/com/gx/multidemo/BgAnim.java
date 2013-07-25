package com.gx.multidemo;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class BgAnim {

	private View view;
	private TranslateAnimation anim;

	public Animation getAnim(final View v) {
		anim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.5f,
				Animation.RELATIVE_TO_PARENT, -0.3f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f);
		anim.setDuration(15000);
		anim.setFillAfter(true);
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(Animation.INFINITE);

		anim.setAnimationListener(new Animation.AnimationListener() {

			public void onAnimationStart(Animation animation) {
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				v.startAnimation(anim);
			}
		});

		return anim;
	}

}
