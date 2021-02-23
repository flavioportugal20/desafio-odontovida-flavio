/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.teste.service;



import com.github.adminfaces.template.exception.BusinessException;

import br.com.teste.infra.model.Filter;
import br.com.teste.infra.model.SortOrder;
import br.com.teste.model.Funcionario;


import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.github.adminfaces.template.util.Assert.has;

/**
 * @author rmpestano
 *         Funcionario Business logic
 */

public class FuncionarioService2 implements Serializable {

    @Inject
    List<Funcionario> allFuncionarios;

    public List<Funcionario> listByNome(String nome) {
        return allFuncionarios.stream()
                .filter(c -> c.getNome().equalsIgnoreCase(nome))
                .collect(Collectors.toList());

    }

    public List<Funcionario> paginate(Filter<Funcionario> filter) {
        List<Funcionario> pagedFuncionarios = new ArrayList<>();
        if(has(filter.getSortOrder()) && !SortOrder.UNSORTED.equals(filter.getSortOrder())) {
                pagedFuncionarios = allFuncionarios.stream().
                    sorted((f1, f2) -> {
                        if (filter.getSortOrder().isAscending()) {
                            return f1.getId().compareTo(f2.getId());
                        } else {
                            return f2.getId().compareTo(f1.getId());
                        }
                    })
                    .collect(Collectors.toList());
            }

        int page = filter.getFirst() + filter.getPageSize();
        if (filter.getParams().isEmpty()) {
            pagedFuncionarios = pagedFuncionarios.subList(filter.getFirst(), page > allFuncionarios.size() ? allFuncionarios.size() : page);
            return pagedFuncionarios;
        }

        List<Predicate<Funcionario>> predicates = configFilter(filter);

        List<Funcionario> pagedList = allFuncionarios.stream().filter(predicates
                .stream().reduce(Predicate::or).orElse(t -> true))
                .collect(Collectors.toList());

        if (page < pagedList.size()) {
            pagedList = pagedList.subList(filter.getFirst(), page);
        }

        if (has(filter.getSortField())) {
            pagedList = pagedList.stream().
                    sorted((f1, f2) -> {
                        boolean asc = SortOrder.ASCENDING.equals(filter.getSortOrder());
                        if (asc) {
                            return f1.getId().compareTo(f2.getId());
                        } else {
                            return f2.getId().compareTo(f1.getId());
                        }
                    })
                    .collect(Collectors.toList());
        }
        return pagedList;
    }

    private List<Predicate<Funcionario>> configFilter(Filter<Funcionario> filter) {
        List<Predicate<Funcionario>> predicates = new ArrayList<>();
        if (filter.hasParam("id")) {
            Predicate<Funcionario> idPredicate = (Funcionario c) -> c.getId().equals(filter.getParam("id"));
            predicates.add(idPredicate);
        }

        if (filter.hasParam("minSalario") && filter.hasParam("maxSalario")) {
            Predicate<Funcionario> minMaxPricePredicate = (Funcionario c) -> c.getSalario()
                    >= Double.valueOf((String) filter.getParam("minSalario")) && c.getSalario()
                    <= Double.valueOf((String) filter.getParam("maxSalario"));
            predicates.add(minMaxPricePredicate);
        } else if (filter.hasParam("minSalario")) {
            Predicate<Funcionario> minSalarioPredicate = (Funcionario c) -> c.getSalario()
                    >= Double.valueOf((String) filter.getParam("minSalario"));
            predicates.add(minSalarioPredicate);
        } else if (filter.hasParam("maxSalario")) {
            Predicate<Funcionario> maxSalarioPredicate = (Funcionario c) -> c.getSalario()
                    <= Double.valueOf((String) filter.getParam("maxSalario"));
            predicates.add(maxSalarioPredicate);
        }

        if (has(filter.getEntity())) {
            Funcionario filterEntity = filter.getEntity();
            if (has(filterEntity.getNome())) {
                Predicate<Funcionario> modelPredicate = (Funcionario c) -> c.getNome().toLowerCase().contains(filterEntity.getNome().toLowerCase());
                predicates.add(modelPredicate);
            }

            if (has(filterEntity.getSalario())) {
                Predicate<Funcionario> pricePredicate = (Funcionario c) -> c.getSalario().equals(filterEntity.getSalario());
                predicates.add(pricePredicate);
            }

            if (has(filterEntity.getNome())) {
                Predicate<Funcionario> namePredicate = (Funcionario c) -> c.getNome().toLowerCase().contains(filterEntity.getNome().toLowerCase());
                predicates.add(namePredicate);
            }
        }
        return predicates;
    }

    public List<String> getNomes(String query) {
        return allFuncionarios.stream().filter(c -> c.getNome()
                .toLowerCase().contains(query.toLowerCase()))
                .map(Funcionario::getNome)
                .collect(Collectors.toList());
    }

    public void insert(Funcionario funcionario) {
        validate(funcionario);
        funcionario.setId(allFuncionarios.stream()
                .mapToLong(c -> c.getId())
                .max()
                .getAsLong()+1);
        allFuncionarios.add(funcionario);
    }

    public void validate(Funcionario funcionario) {
        BusinessException be = new BusinessException();
        
       if (allFuncionarios.stream().filter(c -> c.getNome().equalsIgnoreCase(funcionario.getNome())
                && c.getId() != c.getId()).count() > 0) {
            be.addException(new BusinessException("Funcionario name must be unique"));
        }
        if(has(be.getExceptionList())) {
            throw be;
        }
    }


    public void remove(Funcionario funcionario) {
        allFuncionarios.remove(funcionario);
    }

    public long count(Filter<Funcionario> filter) {
        return allFuncionarios.stream()
                .filter(configFilter(filter).stream()
                        .reduce(Predicate::or).orElse(t -> true))
                .count();
    }

    public Funcionario findById(Integer id) {
        return allFuncionarios.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Funcionario not found with id " + id));
    }

    public void update(Funcionario funcionario) {
        validate(funcionario);
        allFuncionarios.remove(allFuncionarios.indexOf(funcionario));
        allFuncionarios.add(funcionario);
    }
}
