package pl.modrakowski.mvpjug.login

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_login.*
import pl.modrakowski.mvpjug.R
import pl.modrakowski.mvpjug.common.BaseActivity
import pl.modrakowski.mvpjug.common.NormalUserActivity
import pl.modrakowski.mvpjug.common.PremiumUserActivity
import pl.modrakowski.mvpjug.common.di.ActivityComponent
import pl.modrakowski.mvpjug.common.di.NetworkModule
import pl.modrakowski.mvpjug.extension.*
import pl.modrakowski.mvpjug.login.ui.charged.di.ChargedLoginComponent
import pl.modrakowski.mvpjug.login.ui.charged.di.DaggerChargedLoginComponent
import javax.inject.Inject

class KotlinLoginActivity : BaseActivity(), LoginView {

    @Inject lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerChargedLoginComponent.builder()
                .chargedModule(ChargedLoginComponent.ChargedModule())
                .activityModule(ActivityComponent.ActivityModule(this))
                .networkModule(NetworkModule())
                .build().inject(this)

        setContentView(R.layout.activity_login)

        login_action_button.click {
            hideKeyboard(login_username_input)

            loginPresenter.onLoginButtonClicked(
                    login_username_input.text.toString(),
                    login_password_input.text.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        loginPresenter.attach(this)
    }

    override fun onPause() {
        loginPresenter.detach()
        super.onPause()
    }

    override fun continueAsNormalUser() = onUi { launchActivity<NormalUserActivity>() }

    override fun continueAsPremiumUser() = onUi { launchActivity<PremiumUserActivity>() }

    override fun clearUserNameError() = onUi { login_username_input.clearError() }

    override fun showUserNameError(wrongNameMessage: String?) = onUi { login_username_input.error = wrongNameMessage }

    override fun clearUserPasswordError() = onUi { login_password_input.clearError() }

    override fun showUserPasswordError(wrongPasswordMessage: String?) = onUi { login_password_input.error = wrongPasswordMessage }

    override fun showProgress() = onUi { login_progress.visible(true) }

    override fun hideProgress() = onUi { login_progress.visible(false) }

    private fun hideKeyboard(inputView: EditText) {
        focus_thief.requestFocus()
        inputView.clearFocus()

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(inputView.windowToken, 0)
    }
}
