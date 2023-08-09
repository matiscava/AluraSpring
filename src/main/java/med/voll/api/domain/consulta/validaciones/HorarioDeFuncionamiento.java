package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class HorarioDeFuncionamiento implements ValidadorDeConsultas{
    public void validar(DatosAgendarConsulta datos){
        boolean domingo = DayOfWeek.SUNDAY.equals(datos.fecha().getDayOfWeek());
        boolean antesDeApertura = datos.fecha().getHour() < 7;
        boolean despuesDeCierre = datos.fecha().getHour() > 19;

        if (domingo || antesDeApertura || despuesDeCierre){
            throw new ValidacionDeIntegridad("El Horario de atención de la clínica es de Lunes a Sábado");
        }
    }
}
