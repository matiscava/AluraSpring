package med.voll.api.controller;

import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.consulta.AgendaConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetallesConsulta;
import med.voll.api.domain.medico.Especialidad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
@SuppressWarnings("all")
class ConsultaControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DatosAgendarConsulta> agendarConsultaJacksonTester;

    @Autowired
    private JacksonTester<DatosDetallesConsulta> datosDetallesConsultaJacksonTester;

    @Autowired
    private AgendaConsultaService agendaConsultaService;

    @Test
    @DisplayName("Debería retornar estado http 400 cuando los datos ingresados sean inválidos")
    @WithMockUser
    void agendarEscenario1() throws Exception {
        //given //when
        var response = mvc.perform(post("/consultas")).andReturn().getResponse();
        //then
        assertTrue(response.getStatus() == HttpStatus.BAD_REQUEST.value());
    }
//    @Test
//    @DisplayName("deberia retornar estado http 200 cuando los datos ingresados son validos")
//    @WithMockUser
//    void agendarEscenario2() throws Exception {
//        //given
//        var fecha = LocalDateTime.now().plusHours(1);
//        var especialidad = Especialidad.CARDIOLOGIA;
//        var datos = new DatosDetallesConsulta(null,2l,5l,fecha);
//
//        // when
//        when(agendaConsultaService.agendar(any())).thenReturn(datos);
//
//        var response = mvc.perform(post("/consultas")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(agendarConsultaJacksonTester.write(new DatosAgendarConsulta(null, 2l,5l,fecha,especialidad)).getJson())
//        ).andReturn().getResponse();
//
//        //then
//        assertTrue(response.getStatus() == HttpStatus.OK.value());
//
//        var jsonEsperado = datosDetallesConsultaJacksonTester.write(datos).getJson();
//
//        assertTrue(response.getContentAsString()== jsonEsperado);
//
//    }
}