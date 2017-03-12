package pl.modrakowski.mvpjug.login.ui.simple;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import pl.modrakowski.mvpjug.R;
import pl.modrakowski.mvpjug.common.BaseActivity;
import pl.modrakowski.mvpjug.common.NetworkChecker;
import pl.modrakowski.mvpjug.common.NormalUserActivity;
import pl.modrakowski.mvpjug.common.PremiumUserActivity;
import pl.modrakowski.mvpjug.login.LoginPresenter;
import pl.modrakowski.mvpjug.login.LoginView;
import pl.modrakowski.mvpjug.login.data.LocalUserRepository;
import pl.modrakowski.mvpjug.login.data.RemoteUserRepository;

public class SimpleLoginActivity extends BaseActivity implements LoginView {

	private LoginPresenter loginPresenter;

	private EditText userNameInputView, userPasswordInputView;
	private View focusThief, progressView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		setupViews();

		loginPresenter = new LoginPresenter(
				new LocalUserRepository(),
				new RemoteUserRepository(),
				new NetworkChecker(getApplicationContext()));
	}

	private void setupViews() {
		focusThief = findViewById(R.id.focus_thief);
		progressView = findViewById(R.id.login_progress);
		userNameInputView = (EditText) findViewById(R.id.login_username_input);
		userPasswordInputView = (EditText) findViewById(R.id.login_password_input);

		findViewById(R.id.login_action_button).setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				onLoginButtonClicked();
			}
		});
	}

	@Override protected void onResume() {
		super.onResume();
		loginPresenter.attach(this);
	}

	@Override protected void onPause() {
		loginPresenter.detach();
		super.onPause();
	}

	@Override public void continueAsNormalUser() {
		startActivity(new Intent(this, NormalUserActivity.class));
		finish();
	}

	@Override public void continueAsPremiumUser() {
		startActivity(new Intent(this, PremiumUserActivity.class));
		finish();
	}

	@Override public void clearUserNameError() {
		runOnUiThread(new Runnable() {
			@Override public void run() {
				userNameInputView.setError(null);
			}
		});
	}

	@Override public void showUserNameError(final String wrongNameMessage) {
		runOnUiThread(new Runnable() {
			@Override public void run() {
				userNameInputView.setError(wrongNameMessage);
			}
		});
	}

	@Override public void clearUserPasswordError() {
		runOnUiThread(new Runnable() {
			@Override public void run() {
				userPasswordInputView.setError(null);
			}
		});
	}

	@Override public void showUserPasswordError(final String wrongPasswordMessage) {
		runOnUiThread(new Runnable() {
			@Override public void run() {
				userPasswordInputView.setError(wrongPasswordMessage);
			}
		});
	}

	@Override public void showProgress() {
		runOnUiThread(new Runnable() {
			@Override public void run() {
				progressView.setVisibility(View.VISIBLE);
			}
		});
	}

	@Override public void hideProgress() {
		runOnUiThread(new Runnable() {
			@Override public void run() {
				progressView.setVisibility(View.INVISIBLE);
			}
		});
	}

	private void onLoginButtonClicked() {
		hideKeyboard(userNameInputView);

		final String userName = userNameInputView.getText().toString();
		final String userPassword = userPasswordInputView.getText().toString();

		loginPresenter.onLoginButtonClicked(userName, userPassword);
	}

	private void hideKeyboard(EditText inputView) {
		focusThief.requestFocus();
		inputView.clearFocus();

		final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(inputView.getWindowToken(), 0);
	}
}
