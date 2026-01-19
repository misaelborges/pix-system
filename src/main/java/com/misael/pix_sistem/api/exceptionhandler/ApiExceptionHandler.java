package com.misael.pix_sistem.api.exceptionhandler;

import com.misael.pix_sistem.domain.exceptions.*;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String MSG_ERRO_GENERICA =
            "Ocorreu um erro interno inesperado no sistema. " +
                    "Tente novamente mais tarde ou entre em contato com o suporte.";

    @ExceptionHandler({
            AccountNotFoundException.class,
            PixKeyNotFoundException.class,
            TransactionNotFoundException.class
    })
    public ResponseEntity<Object> handleRecursoNaoEncontrado(RuntimeException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;

        ApiError apiError = createApiErrorBuilder(status, problemType, ex.getMessage())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
    }

    @ExceptionHandler({
            EmailAlreadyExistsException.class,
            PixKeyAlreadyExistsException.class,
            InsufficientBalanceException.class
    })
    public ResponseEntity<Object> handleConflitoNegocio(RuntimeException ex, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.ERRO_NEGOCIO;

        ApiError apiError = createApiErrorBuilder(status, problemType, ex.getMessage())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleNegocio(BusinessException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.ERRO_NEGOCIO;

        ApiError apiError = createApiErrorBuilder(status, problemType, ex.getMessage())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        String detail = "Um ou mais campos estão inválidos. Corrija e tente novamente.";

        List<ApiError.Field> fields = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> ApiError.Field.builder()
                        .name(fieldError.getField())
                        .userMessage(fieldError.getDefaultMessage())
                        .build())
                .toList();

        ApiError apiError = createApiErrorBuilder(HttpStatus.BAD_REQUEST, problemType, detail)
                .userMessage(detail)
                .fields(fields)
                .build();

        return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException mismatch) {

            String detail = String.format(
                    "O parâmetro '%s' recebeu o valor '%s', que é inválido. O tipo esperado é '%s'.",
                    mismatch.getName(),
                    mismatch.getValue(),
                    mismatch.getRequiredType() != null
                            ? mismatch.getRequiredType().getSimpleName()
                            : "desconhecido"
            );

            ApiError apiError = createApiErrorBuilder(
                    HttpStatus.BAD_REQUEST,
                    ProblemType.PARAMETRO_INVALIDO,
                    detail
            ).userMessage("Um ou mais parâmetros estão inválidos. Corrija e tente novamente.")
                    .build();

            return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.MENSGEM_INCOMPREENSIVEL;

        ApiError apiError = createApiErrorBuilder(
                httpStatus,
                problemType,
                "O corpo da requisição está inválido. Verifique o formato do JSON."
        ).userMessage("O corpo da requisição está inválido. Corrija e tente novamente.")
                .build();

        return handleExceptionInternal(ex, apiError, headers, httpStatus, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;

        ApiError apiError = createApiErrorBuilder(status, problemType, MSG_ERRO_GENERICA)
                .userMessage(MSG_ERRO_GENERICA)
                .build();

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
    }

    private ApiError.ApiErrorBuilder createApiErrorBuilder(
            HttpStatus status,
            ProblemType problemType,
            String detail) {

        return ApiError.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }
}