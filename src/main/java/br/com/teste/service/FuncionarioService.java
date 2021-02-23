package br.com.teste.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.teste.model.Funcionario;
import br.com.teste.repository.FuncionarioRepository;
import br.com.teste.util.Mascara;
import br.com.teste.util.Utils;
import br.com.teste.util.jsf.FacesUtil;

public class FuncionarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FuncionarioRepository funcionarioRepository;

	public Funcionario findById(Long id) {
		return funcionarioRepository.findById(id);
	}

	public List<Funcionario> findAll() {
		return funcionarioRepository.findAll();
	}

	public List<Funcionario> filtrados(String filtro) {
		return funcionarioRepository.filtrados(filtro);
	}

	public Funcionario createUpdate(Funcionario funcionario) {
		/* garante a mascara do CPF */
		funcionario.setCpf(Mascara.maskCPF(funcionario.getCpf()));
		Funcionario funcionarioExistente = funcionarioRepository.findByCpf(funcionario.getCpf());
		if (funcionarioExistente != null && !funcionarioExistente.equals(funcionario)) {
			//FacesUtil.addErrorMessage("Já existe um funcionário com o CPF informado!");
			Utils.addErrorMessage("Já existe um funcionário com o CPF informado!");
			return null;
		}
		return funcionarioRepository.createUpdate(funcionario);

	}

	public void delete(Funcionario funcionario) {
		funcionarioRepository.delete(funcionario);
	}

}
