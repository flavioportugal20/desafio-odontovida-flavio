package br.com.teste.util;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

import br.com.teste.service.exception.NegocioException;

public class Mascara {

	public static String maskCPF(String cpf) {
		cpf = cpf.replaceAll("[^\\d ]", "");
		MaskFormatter mask;
		try {;
			mask = new MaskFormatter("###.###.###-##");
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(cpf);
		} catch (ParseException e) {
			throw new NegocioException("O CPF informado é inválido");
		}
	}

}
