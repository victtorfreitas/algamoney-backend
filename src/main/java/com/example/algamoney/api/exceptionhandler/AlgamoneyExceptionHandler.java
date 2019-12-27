package com.example.algamoney.api.exceptionhandler;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex,
                                                                  @NonNull HttpHeaders headers, @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        String mensagemDesenvolvedor = getMensagemDesenvolvedor(ex);
        Erro erro = new Erro(mensagemDesenvolvedor, "Mensagem Invalida!");
        List<Erro> erros = Collections.singletonList(erro);
        return handleExceptionInternal(ex, erros, headers, status, request);
    }

    private String getMensagemDesenvolvedor(HttpMessageNotReadableException ex) {
        return ex.getCause() != null ? ex.getCause().toString() : ex.toString();
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers, @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        List<Erro> erros = criarListaErros(ex.getBindingResult());
        return handleExceptionInternal(ex, erros, headers, status, request);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(RuntimeException ex, WebRequest request) {
        String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);
        List<Erro> erros = Collections.singletonList(new Erro(mensagemDesenvolvedor, "Recurso não encontrado!"));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> hanldeDataIntegrityViolationException(RuntimeException ex, WebRequest request) {
        String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);
        List<Erro> erros = Collections.singletonList(new Erro(mensagemDesenvolvedor, "Operação não permitida!"));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({PessoaInativaOuInexistenteException.class})
    public ResponseEntity<Object> handlePessoaInativaOuInexistenteException(PessoaInativaOuInexistenteException ex,
                                                                            WebRequest request) {
        String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);
        List<Erro> erros = Collections.singletonList(
                new Erro(mensagemDesenvolvedor, "Pessoa inexistente ou inativa!")
        );
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    private List<Erro> criarListaErros(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(fieldError -> new Erro(fieldError.toString(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
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
