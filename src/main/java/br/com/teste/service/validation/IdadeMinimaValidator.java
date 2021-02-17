package br.com.teste.service.validation;

import java.util.Calendar;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdadeMinimaValidator implements ConstraintValidator<IdadeMinima, Date> {

	private Integer idade;

	@Override
	public void initialize(IdadeMinima constraintAnnotation) {
		this.idade = constraintAnnotation.idade();
	}

	@Override
	public boolean isValid(Date date, ConstraintValidatorContext context) {
		if(date == null) {
			return false;
		}
			
		try {
			Calendar dataAtual = Calendar.getInstance();
			Calendar dataCampo = Calendar.getInstance();
			dataCampo.setTime(date);
			dataCampo.add(Calendar.YEAR, this.idade);
			if (dataCampo.getTimeInMillis() <= dataAtual.getTimeInMillis()) {
				return true;
			}
		} catch (RuntimeException e) {
			return false;
		}
		return false;
	}

}