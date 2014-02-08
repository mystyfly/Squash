package ch.squash.simulation.main;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;

public class SquashView extends GLSurfaceView implements SurfaceHolder.Callback {
	private final static String TAG = SquashView.class.getSimpleName();

	private final GestureDetector mGestureDetector;
	private static boolean mLongPress;
	
	@Override
	public void onPause() {
		super.onPause();
		MovementEngine.pause();
	}

	@Override
	public void onResume() {
		super.onResume();
//		MovementEngine.resume();
	}

	public SquashView(final Context context) {
		super(context);

		setEGLContextClientVersion(2);

		setRenderer(SquashRenderer.getInstance());

		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

		mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
		    public void onLongPress(MotionEvent e) {
		    	MovementEngine.pause();
		    	MovementEngine.resetMovables();
		    	
		    	Toast.makeText(SquashActivity.getInstance(), "Movables reset", Toast.LENGTH_SHORT).show();
		    	
		        mLongPress = true;
		    }
		});
		
		Log.i(TAG, "SquashView created");
	}
	
	public boolean onTouchEvent(final MotionEvent event) {
		mGestureDetector.onTouchEvent(event);
		
		if (mLongPress){
			// only reset longpress flag if it was completed (if the action is UP)
			mLongPress = event.getAction() != MotionEvent.ACTION_UP;
			return true;
		}
		
		if (event.getAction() == MotionEvent.ACTION_UP){
	    	Toast.makeText(SquashActivity.getInstance(),
	    			"MovementEngine " + (MovementEngine.isRunning() ? "stopped" : "started"), Toast.LENGTH_SHORT).show();
			MovementEngine.toggleRunning();
		}
		
		return true;
	}
}