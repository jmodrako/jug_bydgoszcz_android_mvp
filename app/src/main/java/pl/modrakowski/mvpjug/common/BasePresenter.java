package pl.modrakowski.mvpjug.common;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

public abstract class BasePresenter<ViewImplementation> {

	private ViewImplementation view;

	@NonNull protected ViewImplementation view() {
		return view;
	}

	@CallSuper public void attach(@NonNull ViewImplementation view) {
		this.view = view;
	}

	@CallSuper public void detach() {
		view = provideEmptyView();
	}

	@NonNull protected abstract ViewImplementation provideEmptyView();
}