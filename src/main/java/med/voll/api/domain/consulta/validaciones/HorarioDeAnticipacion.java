package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioDeAnticipacion implements ValidadorDeConsultas{
    public void validar(DatosAgendarConsulta datos) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime horaDeConsulta = datos.fecha();
        boolean diferenciaDe30Minutos = Duration.between(ahora,horaDeConsulta).toMinutes() < 30;

        if(diferenciaDe30Minutos){
            throw new ValidacionDeIntegridad("Las consultas deben tener al menos 30 minutos de anticipación");
        }
    }
}
