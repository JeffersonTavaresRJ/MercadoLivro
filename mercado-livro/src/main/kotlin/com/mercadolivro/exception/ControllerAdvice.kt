package com.mercadolivro.exception

import com.mercadolivro.controller.response.ErrorResponse
import com.mercadolivro.controller.response.FieldErrorResponse
import com.mercadolivro.enuns.Errors
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

/*classe que trata todas as exceções através da annotation @ControllerAdvice*/
@ControllerAdvice
class ControllerAdvice {
    @ExceptionHandler(NotFoundException::class)
    fun handleException(ex: NotFoundException, request: WebRequest):ResponseEntity<ErrorResponse>{
        var error = ErrorResponse(
            httpCode = HttpStatus.NOT_FOUND.value(),
            message = ex.message,
            internalCode = ex.errorCode,
            errors = null
        )
        return ResponseEntity(error, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException, request: WebRequest):ResponseEntity<ErrorResponse>{
        var error = ErrorResponse(
            httpCode = HttpStatus.BAD_REQUEST.value(),
            message = ex.message,
            internalCode = ex.errorCode,
            errors = null
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidExceptionException(ex: MethodArgumentNotValidException, request: WebRequest):ResponseEntity<ErrorResponse>{
        var error = ErrorResponse(
            httpCode = HttpStatus.UNPROCESSABLE_ENTITY.value(),
            message = Errors.ML0000.message,
            internalCode = Errors.ML0000.code,
            errors = ex.bindingResult.fieldErrors.map{ FieldErrorResponse(it.field, it.defaultMessage?:"Inválido") }
        )
        return ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);

    }


    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(ex: AccessDeniedException, request: WebRequest):ResponseEntity<ErrorResponse>{
        var error = ErrorResponse(
            httpCode = HttpStatus.FORBIDDEN.value(),
            message = Errors.ML0005.message,
            internalCode = Errors.ML0005.code,
            errors = null
        )
        return ResponseEntity(error, HttpStatus.FORBIDDEN);

    }
}