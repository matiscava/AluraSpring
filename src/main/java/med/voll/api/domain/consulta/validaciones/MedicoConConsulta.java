package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.stereotype.Component;


@Component
public class MedicoConConsulta implements ValidadorDeConsultas{
    private final ConsultaRepository consultaRepository;

    public MedicoConConsulta(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public void validar(DatosAgendarConsulta datos){
        if(datos.idMedico() == null) {
            return;
        }

        Boolean medicoConConsulta = consultaRepository.existsByMedicoIdAndData(datos.idMedico(), datos.fecha());

        if(medicoConConsulta){
            throw new ValidacionDeIntegridad("Este Medico ya tiene una consulta ese día");
        }
    }
}
