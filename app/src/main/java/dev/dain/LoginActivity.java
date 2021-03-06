package dev.dain;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.FacebookDialog.Callback;
import com.facebook.widget.FacebookDialog.PendingCall;
import com.facebook.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends Activity {

    private static final String PERMISSION = "publish_actions";
    private static final String FRIEND_PERMISSION = "user_friends";
    private UiLifecycleHelper uiHelper;
    private LoginButton facebook_login;
    private GraphUser user;
    private PendingAction pendingAction = PendingAction.NONE;
    EditText user_id;
    EditText user_pw;
    Button email_login;
    Button sign_up;
    String[] Facebook_friends_names;
    String[] Facebook_friends_id;

    public static Activity AActivity;

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

    private BackPressCloseHandler backPressCloseHandler;

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
        facebook_login.setReadPermissions(Arrays.asList("user_friends"));
        facebook_login
                .setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {

                    @Override
                    public void onUserInfoFetched(GraphUser user) {
                        // TODO Auto-generated method stub
                        LoginActivity.this.user = user;
                        updateUI();
                    }
                });


        backPressCloseHandler = new BackPressCloseHandler(this);
        AActivity = LoginActivity.this;
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
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
                && session.getPermissions().contains("publish_actions") && session.getPermissions().contains("user_friends");
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
                session.requestNewPublishPermissions(new Session.NewPermissionsRequest(this, FRIEND_PERMISSION));
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

        if (session.isOpened()) {

            //페이스북 친구리스트 api
            Request r = new Request(session, "/me/friends", null, HttpMethod.GET, new Request.Callback() {
                @Override
                public void onCompleted(Response response) {


                    GraphObject graphObject = response.getGraphObject();
                    if (graphObject != null) {
                        JSONObject jsonObject = graphObject.getInnerJSONObject();
                        try {
                            JSONArray array = jsonObject.getJSONArray("data");

                            Facebook_friends_id = new String[array.length()];
                            Facebook_friends_names = new String[array.length()];

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = (JSONObject) array.get(i);
                                Facebook_friends_id[i] = String.valueOf(object.get("id"));
                                Facebook_friends_names[i] = String.valueOf(object.get("name"));
                            }
                        } catch (JSONException e) {

                        }
                    }
                    SharedPreferencesActivity pref = new SharedPreferencesActivity(LoginActivity.this);
                    pref.savePreferences("facebookId", user.getId());
                    pref.savePreferences("facebookName", user.getName());
                    pref.savePreferences("friends_names", Facebook_friends_names);
                    pref.savePreferences("friends_id", Facebook_friends_id);

                }
            });
            r.executeAsync();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }


    }

}


