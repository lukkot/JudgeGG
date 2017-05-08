package pl.gymkhana_gp.judge.utils.validation;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;

import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;

@Component
@Scope("prototype")
public class TableViewValidationHelperBean<E> {

	@Autowired
	Validator validator;

	TableView<?> tableView;

	IValidationErrorsListener validationErrorsListener;
	
	IOnDataChangedListener<E> onDataChangedListener;
	
	public <T> void validateInput(CellEditEvent<E, T> event, String propertyName) {
		T oldValue = event.getOldValue();
		
		BeanWrapper dataBean = new BeanWrapperImpl(event.getRowValue());
		dataBean.setPropertyValue(propertyName, event.getNewValue());

		BeanPropertyBindingResult errors = new BeanPropertyBindingResult(event.getRowValue(), propertyName);
		validator.validate(event.getRowValue(), errors);

		if (errors.getErrorCount() > 0) {
			dataBean.setPropertyValue(propertyName, oldValue);
			System.out.println("errors.getErrorCount(): " + errors.getErrorCount());
			System.out.println("errors: " + errors.getAllErrors().get(0).getDefaultMessage());

			tableView.refresh();
		}

		if (validationErrorsListener != null) {
			validationErrorsListener.onErrorMessageChanged(errors);
		}
		
		if(onDataChangedListener != null) {
			onDataChangedListener.onDataChanged(event.getRowValue());
		}
	}

	public TableView<?> getTableView() {
		return tableView;
	}

	public void setTableView(TableView<?> tableView) {
		this.tableView = tableView;
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
}
