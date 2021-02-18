package br.com.teste.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.teste.model.Funcionario;
import br.com.teste.service.FuncionarioService;
import br.com.teste.service.exception.NegocioException;
import br.com.teste.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaFuncionariosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private FuncionarioService cadastroFuncionarioService;
	private Funcionario funcionario;
	
	private List<Funcionario> funcionarioFiltrados;	
	private String filtro;
	
	public PesquisaFuncionariosBean() {
		filtro = null;
	}
	
	@PostConstruct
	public void pesquisar() {
		funcionarioFiltrados = cadastroFuncionarioService.filtrados(filtro);
	}
	
	public void delete() {
		try {
			cadastroFuncionarioService.delete(this.funcionario);
			funcionarioFiltrados.remove(this.funcionario);

			FacesUtil.addInfoMessage("Funcionário " + this.funcionario.getNome() + " excluído com sucesso!");		
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public List<Funcionario> getFuncionariosFiltrados() {
		return funcionarioFiltrados;
	}
	
	public Funcionario getFuncionario() {
		return this.funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public String getFiltro() {
		return filtro;
	}
	
	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}
	
	public Integer getTotalFuncionarios() {
		return funcionarioFiltrados.size();
	}


}
