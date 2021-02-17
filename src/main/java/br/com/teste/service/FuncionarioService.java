package br.com.teste.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.teste.model.Funcionario;
import br.com.teste.repository.FuncionarioRepository;
import br.com.teste.service.exception.NegocioException;
import br.com.teste.util.Mascara;

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
			throw new NegocioException("Já existe um funcionário com o CPF informado!");
		}
		return funcionarioRepository.createUpdate(funcionario);
	}

	public void delete(Funcionario funcionario) {
		funcionarioRepository.delete(funcionario);
	}
	
}
