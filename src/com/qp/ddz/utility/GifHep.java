package com.qp.ddz.utility;
import com.qp.ddz.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.view.View;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class GifHep extends View {

	public final static int		MOVIE_TIME	= 2000;
	@SuppressWarnings("unused")
	private static final String	TAG			= "GifHep";

	private Movie				mMovie;
	private long				mMovieStart;

	public GifHep(Context context) {
		super(context);
		setBackgroundResource(R.drawable.bg_expression);
	}

	public void onDraw(Canvas canvas) {
		if (mMovieStart == 0)
			return;
		long now = System.currentTimeMillis();

		if (mMovie != null) {

			int dur = mMovie.duration();
			if (dur == 0) {
				dur = 1000;
			}
			int relTime = (int) ((now - mMovieStart) % dur);
			mMovie.setTime(relTime);
			mMovie.draw(canvas, 0, 0);
			if (now - mMovieStart > MOVIE_TIME)
				onStopGif();
			else
				invalidate();
		}
	}

	public void onStartGif(int resid) {
		mMovie = Movie.decodeStream(getResources().openRawResource(resid));

		this.layout(getLeft(), getTop(), getLeft() + mMovie.width(), getTop() + mMovie.height());

		mMovieStart = System.currentTimeMillis();
		
		invalidate();
	}

	public void onStopGif() {
		mMovie = null;
		mMovieStart = 0;
		postInvalidate();
	}
}
