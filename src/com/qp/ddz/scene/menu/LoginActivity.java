package com.qp.ddz.scene.menu;

import java.security.NoSuchAlgorithmException;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.qp.ddz.GameActivity;
import com.qp.lib.cmd.CMD;
import com.qp.lib.main.AppMain;
import com.qp.lib.utility.NetEncoding;
import com.qp.ddz.R;
import com.qp.ddz.define.GDF;
import com.smw.cmd.plazz.MSG_Login;
import com.smw.net.DynamicUI;
 
/**
*
* 购买完整源码联系 q344717871
* 
*/

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
 
	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";
	public static final String TAG = "LoginActivity";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	static LoginActivity instance;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);
		instance=this;
		
		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
		
		 
	}
	@Override
	protected void onResume() {

		super.onResume();
		//get
				SharedPreferences sp= getSharedPreferences("shared_file", 0);
 
				String user=  sp.getString("username", "");
				String  pwd=  sp.getString("pwd", "");
				mEmailView.setText(user);
				mPasswordView.setText(pwd);
				
				
				
		DynamicUI.LoadBG(TAG, this, R.id.login_LinearLayout  );
				
	}
	
	
	@Override
	public void onDestroy() {
		instance = null;
		super.onDestroy();
		
		DynamicUI.onDestroy(this,R.id.login_LinearLayout);
	}
	
	
 

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		 		//set
		 SharedPreferences    sp= getSharedPreferences("shared_file", 0);
		 
		 sp.edit().putString("username", mEmail).commit();
		  sp.edit().putString("pwd", mPassword ).commit();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

 
			
			try {
				// Simulate network access.
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				return false;
			}

			
	 // 		GameActivity.onShowDialog("登陆", "请稍后...");
//			//直接进入大厅界面
//			GameActivity.getInstance().onShowScene(GDF.SCENE_SERVER);
	//
	 		GameActivity.getGameActivityInstance().m_loginclient.close();
			boolean conn=GameActivity.getGameActivityInstance().m_loginclient.start(GDF.UrlLogin, GDF.PortLogin);
			if(conn==false) {
				//Toast.makeText(instance, "连接服务器失败!请检查网络!", Toast.LENGTH_SHORT).show();
				return true;
			}
			String szMachineID_MD5 = "";
			if (AppMain.getOption().getMachineID()   != null && !AppMain.getOption().getMachineID() .equals("")) {
				try {
					szMachineID_MD5 = NetEncoding.changeToMD5(AppMain.getOption().getMachineID() );
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} 
			}
			MSG_Login o=new MSG_Login();
			o.cmd=CMD.C2S_LOGIN;
			o.VERSION=(int)GDF.GAME_VERSION;
			o.name= mEmail;
			o.pwd= mPassword ;
			o.appgameType=GDF.GAME_TYPE;
			o.cbDeviceType=AppMain.getOption().getDeviceType();
			o.szMachineID= szMachineID_MD5;
			GameActivity.getGameActivityInstance().m_loginclient.send(o);
			
			//记下 登陆房间用
			GameActivity.m_user=o.name;
			GameActivity.m_pwd=o.pwd;
			

			// TODO: register the new account here.
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				//finish();
			} else {
				mPasswordView.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
