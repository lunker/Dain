package dev.dain;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

public class SignupActivity extends Activity {

	int register_age;
	EditText register_id;
	EditText register_pw;
	EditText register_name;
	int gender;
	
	RadioButton male;
	RadioButton female;
	
	CheckBox check;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
	}
	
}
