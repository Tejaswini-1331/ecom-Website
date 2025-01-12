package example.ecommerce.website.exceptions;

import example.ecommerce.website.payload.APIResponse;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice //handles rest excepyions
public class MyGlobalExceptionHandler {



    @ExceptionHandler(MethodArgumentNotValidException.class) //define methods to handle expctions
    public ResponseEntity<Map<String,String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e){
    Map<String,String> response = new HashMap<>();
    e.getBindingResult().getAllErrors().forEach(error->{
        String fieldName = ((FieldError)error).getField();
        String message = error.getDefaultMessage();
        response.put("field name "+fieldName,"message "+message);
    });
   return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
}




@ExceptionHandler(ResponseNotFoundException.class)
public ResponseEntity<String> responseNotFound(ResponseNotFoundException e){
    String message = e.getMessage();
    return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
}


    @ExceptionHandler(ConfigDataResourceNotFoundException.class)
    public ResponseEntity<APIResponse> myResourceNotFoundException(ResourceNotFoundException e){
        String message = e.getMessage();
        APIResponse apiResponse = new APIResponse(message,false);
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }

@ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> myAPIException(APIException e){
        String message = e.getMessage();
    APIResponse apiResponse = new APIResponse(message,false);
    return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }
}
