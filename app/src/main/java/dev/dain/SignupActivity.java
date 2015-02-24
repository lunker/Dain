package dev.dain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class SignupActivity extends Activity {

	int register_age;
	EditText register_id;
	EditText register_pw;
	EditText register_name;
	int gender;
	
	RadioButton male;
	RadioButton female;
	
	CheckBox check;



    private Button btnSignup = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);


        btnSignup = (Button) findViewById(R.id.btn_signup_request);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast toast = Toast.makeText(SignupActivity.this, "회원가입완료", Toast.LENGTH_SHORT);
                toast.show();


                Intent mainIntent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(mainIntent);

            }
        });
	}
	
}
