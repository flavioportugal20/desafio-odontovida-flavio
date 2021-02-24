package br.com.teste.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;

import br.com.teste.model.Funcionario;
import br.com.teste.util.jpa.Transactional;
import br.com.teste.util.jsf.FacesUtil;

public class FuncionarioRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Funcionario findById(Long id) {
		try {
			return manager.find(Funcionario.class, id);
		} catch (NoResultException e) {
			return null;
		}
	}

	public Funcionario findByCpf(String cpf) {
		try {
			return manager.createQuery("SELECT f FROM Funcionario f WHERE upper(f.cpf) = :cpf", Funcionario.class)
					.setParameter("cpf", cpf.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Funcionario> findAll() {
		try {
			return manager.createQuery("SELECT f FROM Funcionario f ORDER BY f.id DESC", Funcionario.class)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Transactional
	public Funcionario createUpdate(Funcionario funcionario) {
		try {
			return manager.merge(funcionario);
		} catch (PersistenceException e) {
			String tipo = (funcionario.getId() != null) ? "editar" : "criar";
			FacesUtil.addErrorMessage("Falha ao tentar " + tipo + " funcionário!");
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public void delete(Funcionario funcionario) {
		try {
			funcionario = findById(funcionario.getId());
			manager.remove(funcionario);
			manager.flush();
		} catch (PersistenceException e) {
			FacesUtil.addErrorMessage("Falha ao tentar deletar funcionário!");
			e.printStackTrace();
		}
	}

	public List<Funcionario> filtrados(String filtro) {
		try {
			Session session = manager.unwrap(Session.class);
			/* se a pesquisa for vazia retorna todos os dados */
			if (StringUtils.isBlank(filtro)) {
				return findAll();
			}
			return session.createQuery("Select f From Funcionario f where upper(nome) like :nome ORDER BY f.id DESC",
					Funcionario.class).setParameter("nome", filtro.toUpperCase() + "%").getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
