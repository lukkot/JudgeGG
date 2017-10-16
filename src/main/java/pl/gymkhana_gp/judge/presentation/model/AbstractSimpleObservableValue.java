package pl.gymkhana_gp.judge.presentation.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public abstract class AbstractSimpleObservableValue<T> implements ObservableValue<T>  {
	private List<InvalidationListener> invalidationListeners = new ArrayList<>();
	private List<ChangeListener<? super T>> changeListeners = new ArrayList<>();
	
	private T oldValue;

	@Override
	public void addListener(InvalidationListener listener) {
		invalidationListeners.add(listener);
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		invalidationListeners.remove(listener);
	}

	@Override
	public void addListener(ChangeListener<? super T> listener) {
		changeListeners.add(listener);
	}

	@Override
	public void removeListener(ChangeListener<? super T> listener) {
		changeListeners.remove(listener);
	}
	
	protected void informInvalidationListeners() {
		for(InvalidationListener listener : invalidationListeners) {
			listener.invalidated(this);
		}
	}
	
	protected void informChangeListeners() {
		for(ChangeListener<? super T> listener : changeListeners) {
			listener.changed(this, oldValue, getValue());
		}
	}
}
