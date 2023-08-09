package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PacienteSinConsulta implements ValidadorDeConsultas{

    private final ConsultaRepository
            consultaRepository;

    public PacienteSinConsulta(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public void validar(DatosAgendarConsulta datos){
        LocalDateTime primerHorario = datos.fecha().withHour(7);
        LocalDateTime ultimoHorario = datos.fecha().withHour(18);

        var pacienteConConsulta = consultaRepository.existsByPacienteIdAndDataBetween(datos.idPaciente(),primerHorario,ultimoHorario);
        if(pacienteConConsulta){
            throw new ValidacionDeIntegridad("No se puede agendar dos consultas por día");
        }
    }
}
