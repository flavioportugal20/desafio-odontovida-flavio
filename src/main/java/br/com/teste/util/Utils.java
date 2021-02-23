package br.com.teste.util;


import org.omnifaces.util.Messages;

import br.com.teste.model.Funcionario;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by rmpestano on 07/02/17.
 */
@ApplicationScoped
public class Utils implements Serializable {

    private List<Funcionario> funcionarios;


    @PostConstruct
    public void init() {
        funcionarios = new ArrayList<>();
        IntStream.rangeClosed(1, 50)
                .forEach(i -> funcionarios.add(create(i)));
    }

    private static Funcionario create(int i) {
        return new Funcionario();
    }

    public static void addDetailMessage(String message) {
        addDetailMessage(message, null);
    }
    

    public static void addErrorMessage(String message) {
        addDetailMessage(message, FacesMessage.SEVERITY_ERROR);
    }


    public static void addDetailMessage(String message, FacesMessage.Severity severity) {

        FacesMessage facesMessage = Messages.create("").detail(message).get();
        if (severity != null && severity != FacesMessage.SEVERITY_INFO) {
            facesMessage.setSeverity(severity);
        }
        Messages.add(null, facesMessage);
    }

    @Produces
    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

}
