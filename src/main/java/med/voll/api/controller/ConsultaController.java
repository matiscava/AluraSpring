package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosCancelaminetoConsulta;
import med.voll.api.domain.consulta.DatosDetallesConsulta;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {
    private AgendaConsultaService servicio;

    public ConsultaController(AgendaConsultaService servicio) {
        this.servicio = servicio;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetallesConsulta> agendar(@RequestBody @Valid DatosAgendarConsulta datos){
        DatosDetallesConsulta response = servicio.agendar(datos);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<String> cancelar(@RequestBody @Valid DatosCancelaminetoConsulta datos){
        servicio.cancelar(datos);
        return ResponseEntity.noContent().build();
    }
}
