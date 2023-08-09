package med.voll.api.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratadorDeErrores {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> tratarError404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DatosErrorValidacion>> tratarError400(MethodArgumentNotValidException exception){
        var errores = exception.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();

        return ResponseEntity.badRequest().body(errores);
    }
    @ExceptionHandler(ValidacionDeIntegridad.class)
    public ResponseEntity<String> tratarErrorValidacionesDeNegocio(ValidacionDeIntegridad exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    private record DatosErrorValidacion(String campo, String Error){
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(),error.getDefaultMessage());
        };
    }
}
