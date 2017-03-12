package pl.modrakowski.mvpjug.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pl.modrakowski.mvpjug.R;

public class NormalUserActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_normal_user);
	}
}
