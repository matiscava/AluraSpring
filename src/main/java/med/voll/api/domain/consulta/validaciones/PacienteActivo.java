package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.stereotype.Component;


@Component
public class PacienteActivo implements ValidadorDeConsultas{
    private final PacienteRepository pacienteRepository;

    public PacienteActivo(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public void validar(DatosAgendarConsulta datos) {
        if (datos.idPaciente() == null) {
            return;
        }
        Boolean pacienteActivo = pacienteRepository.findActivoById(datos.idPaciente());
        if(!pacienteActivo){
            throw new ValidacionDeIntegridad("No se puede permitir agendar citas con pacientes inactivos en el sistema");
        }
    }
}
