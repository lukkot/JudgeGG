package pl.gymkhana_gp.judge.utils.validation;

import org.springframework.validation.Errors;

public interface IValidationErrorsListener {
	void onErrorMessageChanged(Errors errors);
}
