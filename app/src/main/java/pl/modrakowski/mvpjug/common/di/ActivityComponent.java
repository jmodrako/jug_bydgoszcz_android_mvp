package pl.modrakowski.mvpjug.common.di;

import android.content.Context;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import pl.modrakowski.mvpjug.common.BaseActivity;

@Component(modules = ActivityComponent.ActivityModule.class)
public interface ActivityComponent {

	Context exposeContext();

	@Module class ActivityModule {
		private final BaseActivity activity;

		public ActivityModule(BaseActivity activity) {
			this.activity = activity;
		}

		@Provides BaseActivity activity() {
			return this.activity;
		}

		@Provides Context providesContext() {
			return activity.getApplicationContext();
		}
	}
}

