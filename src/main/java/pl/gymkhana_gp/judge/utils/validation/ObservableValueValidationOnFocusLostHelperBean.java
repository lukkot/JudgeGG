package pl.gymkhana_gp.judge.utils.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

@Component
@Scope("prototype")
public class ObservableValueValidationOnFocusLostHelperBean<E> implements ChangeListener<Boolean> {

	@Autowired
	private Validator validator;
	
	private E parentObject;
	
	private TextField validatedObject;
	
	private IValidationErrorsListener validationErrorsListener;
	
	private IOnDataChangedListener<E> onDataChangedListener;

	@Override
	public void changed(ObservableValue<? extends Boolean> observableFocus, Boolean oldFocusValue, Boolean newFocusValue) {
		if(parentObject == null) {
			return;
		}
		
		if(oldFocusValue && !newFocusValue) {
			BeanPropertyBindingResult errors = new BeanPropertyBindingResult(parentObject, validatedObject.getId());
			validator.validate(parentObject, errors);
	
			if (errors.getErrorCount() > 0) {
				validatedObject.requestFocus();
				
				System.out.println("errors.getErrorCount(): " + errors.getErrorCount());
				System.out.println("errors: " + errors.getAllErrors().get(0).getDefaultMessage());
			}
	
			if (validationErrorsListener != null) {
				validationErrorsListener.onErrorMessageChanged(errors);
			}
			
			if(onDataChangedListener != null) {
				onDataChangedListener.onDataChanged(parentObject);
			}
		}
	}

	public E getParentObject() {
		return parentObject;
	}

	public void setParentObject(E parentObject) {
		this.parentObject = parentObject;
	}

	public IValidationErrorsListener getValidationErrorsListener() {
		return validationErrorsListener;
	}

	public void setValidationErrorsListener(IValidationErrorsListener validationErrorsListener) {
		this.validationErrorsListener = validationErrorsListener;
	}

	public IOnDataChangedListener<E> getOnDataChangedListener() {
		return onDataChangedListener;
	}

	public void setOnDataChangedListener(IOnDataChangedListener<E> onDataChangedListener) {
		this.onDataChangedListener = onDataChangedListener;
	}

	public TextField getValidatedObject() {
		return validatedObject;
	}

	public void setValidatedObject(TextField validatedObject) {
		this.validatedObject = validatedObject;
	}

}
