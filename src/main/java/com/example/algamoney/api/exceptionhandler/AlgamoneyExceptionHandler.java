package com.example.algamoney.api.exceptionhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        List<Erro> erros = Arrays.asList(new Erro(ex.getCause().toString(), "Mensagem Invalida!"));
        return handleExceptionInternal(ex, erros, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Erro> erros = criarListaErros(ex.getBindingResult());
        return handleExceptionInternal(ex, erros, headers, status, request);
    }

    private List<Erro> criarListaErros(BindingResult bindingResult) {
        List<Erro> erros = bindingResult.getFieldErrors().stream()
                .map(fieldError -> new Erro(fieldError.toString(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return erros;
    }

    public static class Erro {
        public String mensagemDesenvolvedor;
        public String mensagemUsuario;

        public Erro(String mensagemDesenvolvedor, String mensagemUsuario) {
            this.mensagemDesenvolvedor = mensagemDesenvolvedor;
            this.mensagemUsuario = mensagemUsuario;
        }
    }
}
