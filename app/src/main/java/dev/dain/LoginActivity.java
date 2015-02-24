package dev.dain;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.FacebookDialog.Callback;
import com.facebook.widget.FacebookDialog.PendingCall;
import com.facebook.widget.LoginButton;

public class LoginActivity extends Activity {

	private static final String PERMISSION = "publish_actions";
	private UiLifecycleHelper uiHelper;
	private LoginButton facebook_login;
	private GraphUser user;
	private PendingAction pendingAction = PendingAction.NONE;
	EditText user_id;
	EditText user_pw;
	Button email_login;
	Button sign_up;

//fhfftt5454
    //asfasfassgf
    //asfasfafafsfd

    // ㅅㅡㄹㅜㅔㄱㅣㄴㅁㄹㄴㅁㅇㄹㄴㅇㅁㄹㄴㅇ
	private enum PendingAction {
		NONE, POST_STATUS_UPDATE;
	}

	private Session.StatusCallback callback = new Session.StatusCallback() {

		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			// TODO Auto-generated method stub
			 onSesseionStateChange(session, state, exception);
		}
	};

	private FacebookDialog.Callback dialogCallback = new Callback() {

		@Override
		public void onError(PendingCall pendingCall, Exception error,
				Bundle data) {
			// TODO Auto-generated method stub
			Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
		}

		@Override
		public void onComplete(PendingCall pendingCall, Bundle data) {
			// TODO Auto-generated method stub
			Log.d("HelloFacebook", "Success!");
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 uiHelper = new UiLifecycleHelper(this, callback);
		 uiHelper.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		user_id = (EditText) findViewById(R.id.user_id);
		user_pw = (EditText) findViewById(R.id.user_pw);
		email_login = (Button) findViewById(R.id.email_login);
		sign_up = (Button) findViewById(R.id.sign_up);

		user_id.setOnClickListener(listener);
		user_pw.setOnClickListener(listener);
		email_login.setOnClickListener(listener);
		sign_up.setOnClickListener(listener);

		facebook_login = (LoginButton) findViewById(R.id.authButton);

		facebook_login
				.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {

					@Override
					public void onUserInfoFetched(GraphUser user) {
						// TODO Auto-generated method stub
						LoginActivity.this.user = user;
						updateUI();
					}
				});

	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.sign_up) {
				Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
				startActivity(intent);
			}

		}
	};

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		Session session = Session.getActiveSession();
		Session.saveSession(session, outState);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
	}

	private void onSesseionStateChange(Session session, SessionState state,
			Exception exception) {
		updateUI();
	}

	private boolean hasPublishPermission() {
		Session session = Session.getActiveSession();
		return session != null
				&& session.getPermissions().contains("publish_actions");
	}

	private void performPublish(PendingAction action, boolean allowNoSession) {
		Session session = Session.getActiveSession();
		if (session != null) {
			pendingAction = action;
			if (hasPublishPermission()) {
				// We can do the action right away.
				// handlePendingAction();
				return;
			} else if (session.isOpened()) {
				// We need to get new permissions, then complete the action when
				// we get called back.
				session.requestNewPublishPermissions(new Session.NewPermissionsRequest(
						this, PERMISSION));
				return;
			}
		}

		if (allowNoSession) {
			pendingAction = action;
			// handlePendingAction();
		}
	}

	private void updateUI() {
		Session session = Session.getActiveSession();
		
		  if(session.isOpened()) { Intent intent = new
		  Intent(LoginActivity.this, MainActivity.class); startActivity(intent); }
		 
	}

}
