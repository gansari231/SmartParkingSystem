package com.parking.exception;

import com.parking.exception.ParkingException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import jakarta.persistence.OptimisticLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ObjectOptimisticLockingFailureException.class, OptimisticLockException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleOptimisticLock() {

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Spot was just taken, try again");
        response.put("status", 409);

        return response;
    }

    @ExceptionHandler(TransactionSystemException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleTransactionException() {

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Spot was just taken, try again");
        response.put("status", 409);

        return response;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleDBError() {

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Database constraint error");
        response.put("status", 400);

        return response;
    }

    @ExceptionHandler(ParkingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleParkingException(ParkingException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("status", 400);

        return response;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleGenericException(Exception ex) {

        ex.printStackTrace(); // 🔥 IMPORTANT for debugging

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Something went wrong");
        response.put("status", 500);

        return response;
    }


}