package br.com.teste.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.teste.model.Funcionario;
import br.com.teste.service.FuncionarioService;
import br.com.teste.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroFuncionarioBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private FuncionarioService cadastroFuncionarioService;
	private Funcionario funcionario;
	
	public CadastroFuncionarioBean() {
		limpar();
	}

	@PostConstruct
	public void inicializar() {		
		if (FacesUtil.isGetback()) {		
			String idParam = FacesUtil.getParameter("id");			
			if (idParam != null) {
				try {
					Long id = Long.parseLong(idParam);
					this.funcionario = cadastroFuncionarioService.findById(id);	
				} catch (NumberFormatException e) {
					FacesUtil.addErrorMessage("Falha ao tentar buscar funcionário");
				}
			}
		}
	}

	private void limpar() {
		this.funcionario = new Funcionario();
	}

	public void createUpdate() {
		cadastroFuncionarioService.createUpdate(this.funcionario);
		if(isEditando()) {
			FacesUtil.addInfoMessage("Funcionario EDITADO com sucesso!");
		}else {
			limpar();
			FacesUtil.addInfoMessage("Funcionario SALVO com sucesso!");
		}
	}
	
	public Funcionario getFuncionario() {
		return this.funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public boolean isEditando() {
		return this.funcionario.getId() != null;
	}

	public void clearMsg() {
		FacesUtil.execute("$('frm:messages').hide()");
	}

}