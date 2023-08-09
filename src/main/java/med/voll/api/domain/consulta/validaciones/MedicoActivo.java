package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.stereotype.Component;

@Component
public class MedicoActivo implements ValidadorDeConsultas{
    private final MedicoRepository medicoRepository;

    public MedicoActivo(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public void validar(DatosAgendarConsulta datos){
        if (datos.idMedico() == null) {
            return;
        }
        Boolean medicoActivo = medicoRepository.findActivoById(datos.idMedico());
        if(!medicoActivo){
            throw new ValidacionDeIntegridad("No se puede permitir agendar citas con medicos inactivos en el sistema");
        }
    }
}
