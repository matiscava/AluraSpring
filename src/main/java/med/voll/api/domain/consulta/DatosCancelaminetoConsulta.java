package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosCancelaminetoConsulta(@NotNull Long id, @NotNull @NotBlank MotivoCancelacion motivo) {
}
