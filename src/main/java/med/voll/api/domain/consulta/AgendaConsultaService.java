package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.validacionCancelamineto.ValidadorCancelaminetoDeConsulta;
import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AgendaConsultaService {
    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    final
    List<ValidadorDeConsultas> validadores;
    final
    List<ValidadorCancelaminetoDeConsulta> validadoresCancelacion;

    public AgendaConsultaService(ConsultaRepository consultaRepository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository, List<ValidadorDeConsultas> validadores, List<ValidadorCancelaminetoDeConsulta> validadoresCancelacion) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.validadores = validadores;
        this.validadoresCancelacion = validadoresCancelacion;
    }


    public DatosDetallesConsulta agendar(DatosAgendarConsulta datos) {
        if(!pacienteRepository.findById(datos.idPaciente()).isPresent()) {
            throw new ValidacionDeIntegridad("Este id para el paciente no fue encontrado");
        }
        if(datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico()) ){
            throw new ValidacionDeIntegridad("Este id para el médico no fue encontrado");
        }
        // Validaciones
        validadores.forEach(v->v.validar(datos));

        Medico medico = seleccionMedico(datos);
        if(medico==null){
            throw new ValidacionDeIntegridad("No hay Médicos de esa especialidad disponibles para este horario.");
        }
        Paciente paciente = pacienteRepository.findById(datos.idPaciente()).get();
        Consulta consulta = new Consulta(medico,paciente, datos.fecha());
        consultaRepository.save(consulta);

        return new DatosDetallesConsulta(consulta);
    }

    private Medico seleccionMedico(DatosAgendarConsulta datos) {
        if( datos.idMedico() != null) {
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if( datos.especialidad() == null ) {
            throw new ValidacionDeIntegridad("Debe seleccionarse una especialidad para el médico");
        }

        List<Medico> medicosLibres =medicoRepository.selecionarMedicoConEspecialidadEnFecha(datos.especialidad(),datos.fecha());
        return seleccionarMedicoAleatorio(medicosLibres);
    }


    private Medico seleccionarMedicoAleatorio(List<Medico> medicos) {
        if (medicos.isEmpty()) {
            return null;
        }
        int indiceAleatorio = new Random().nextInt(medicos.size());
        return medicos.get(indiceAleatorio);
    }

    public void cancelar(DatosCancelaminetoConsulta datos) {
        if(!consultaRepository.existsById(datos.id())){
            throw new ValidacionDeIntegridad("Id de la consulta informada no existe!");
        }
        validadoresCancelacion.forEach(v -> v.validar(datos));

        Consulta consulta = consultaRepository.getReferenceById(datos.id());
        consulta.cancelar(datos.motivo());

    }
}
