package pl.modrakowski.mvpjug.login.ui.charged;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import javax.inject.Inject;

import pl.modrakowski.mvpjug.R;
import pl.modrakowski.mvpjug.common.BaseActivity;
import pl.modrakowski.mvpjug.common.NormalUserActivity;
import pl.modrakowski.mvpjug.common.PremiumUserActivity;
import pl.modrakowski.mvpjug.common.di.ActivityComponent;
import pl.modrakowski.mvpjug.common.di.NetworkModule;
import pl.modrakowski.mvpjug.login.LoginPresenter;
import pl.modrakowski.mvpjug.login.LoginView;
import pl.modrakowski.mvpjug.login.ui.charged.di.ChargedLoginComponent;
import pl.modrakowski.mvpjug.login.ui.charged.di.DaggerChargedLoginComponent;

@EActivity(R.layout.activity_login)
public class ChargedLoginActivity extends BaseActivity implements LoginView {

	@Inject protected LoginPresenter loginPresenter;

	@ViewById(R.id.login_username_input) protected EditText userNameInputView;
	@ViewById(R.id.login_password_input) protected EditText userPasswordInputView;

	@ViewById(R.id.focus_thief) protected View focusThief;
	@ViewById(R.id.login_progress) protected View progressView;

	@AfterInject protected void afterInject() {
		DaggerChargedLoginComponent.builder()
				.chargedModule(new ChargedLoginComponent.ChargedModule())
				.activityModule(new ActivityComponent.ActivityModule(this))
				.networkModule(new NetworkModule())
				.build().inject(this);
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

	@UiThread(propagation = UiThread.Propagation.REUSE)
	@Override public void clearUserNameError() {
		userNameInputView.setError(null);
	}

	@UiThread(propagation = UiThread.Propagation.REUSE)
	@Override public void showUserNameError(final String wrongNameMessage) {
		userNameInputView.setError(wrongNameMessage);
	}

	@UiThread(propagation = UiThread.Propagation.REUSE)
	@Override public void clearUserPasswordError() {
		userPasswordInputView.setError(null);
	}

	@UiThread(propagation = UiThread.Propagation.REUSE)
	@Override public void showUserPasswordError(final String wrongPasswordMessage) {
		userPasswordInputView.setError(wrongPasswordMessage);
	}

	@UiThread(propagation = UiThread.Propagation.REUSE)
	@Override public void showProgress() {
		progressView.setVisibility(View.VISIBLE);
	}

	@UiThread(propagation = UiThread.Propagation.REUSE)
	@Override public void hideProgress() {
		progressView.setVisibility(View.INVISIBLE);
	}

	@Click(R.id.login_action_button) protected void onLoginButtonClicked() {
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

