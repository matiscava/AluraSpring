package med.voll.api.domain.consulta.validacionCancelamineto;


import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelaminetoConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;

import java.time.Duration;
import java.time.LocalDateTime;

public class ValidadorHorarioAnticipado implements ValidadorCancelaminetoDeConsulta {

    private ConsultaRepository repository;
    @Override
    public void validar(DatosCancelaminetoConsulta datos) {
        Consulta consulta = repository.getReferenceById(datos.id());
        LocalDateTime ahora = LocalDateTime.now();
        long diferenciaEnHoras = Duration.between(ahora, consulta.getData()).toHours();
        if(diferenciaEnHoras < 24) {
            throw new ValidacionDeIntegridad("Consulta solamente puede ser cancelada con una anticipación minima de 24hs");
        }
    }
}
