package br.com.teste.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.br.CPF;


import br.com.teste.service.validation.IdadeMinima;
import br.com.teste.service.validation.SomenteAlfabeto;

@Entity
@Table(name = "funcionario")
public class Funcionario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idFuncionario")
	private Long id;
	
	@Column(length=100)
	@NotNull(message="Informe o nome do funcionário!")
	@SomenteAlfabeto(message ="O nome do funcionário deve conter apenas letras!")
	private String nome;
	
	@NotNull(message="Informe a data de nascimento!")
	@IdadeMinima(idade=18, message="O funcionário não pode ter menos 18 anos!")
	private Date dataNascimento; 
	
	@Column(length=14, unique=true)
	@NotNull(message="Informe o CPF!")
	@CPF(message="O CPF informado é inválido!")
	private String cpf;
	
	@Column(precision=10, scale=2)
	@NotNull(message="Informe o salário!")
	@Range(min=0, message="O salário não pode ser menor que zero!")
	@Digits(integer=10, fraction=2, message="O salário não pode ser maior que 9.999.999.999,99!")
	private Double salario;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funcionario other = (Funcionario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
