package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {
    @Autowired
    MedicoRepository medicoRepository;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Debería retornar nulo cuando el médico se encuetre en consulta con otro paciente en ese horario")
    void selecionarMedicoConEspecialidadEnFechaEscenario1() {
        //given
        LocalDateTime proximoLunes10H = LocalDate.now()
                .with( TemporalAdjusters.next(DayOfWeek.MONDAY) )
                .atTime(10,0);

        var medico= registrarMedico("Pedro","p@mail.com","123456",Especialidad.CARDIOLOGIA);
        var paciente=registrarPaciente("Antonio","a@mail.com","456789");
        registrarConsulta(medico,paciente,proximoLunes10H);
        //when
        List<Medico> medicosLibre = medicoRepository.selecionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA, proximoLunes10H);
        Medico medicoLibre = seleccionarMedicoAleatorio(medicosLibre);
        //then
        assertTrue(medicoLibre == null);
    }

    @Test
    @DisplayName("Debería retornar 1 medico cuando realice la consulta en la base de datos para ese horarios")
    void selecionarMedicoConEspecialidadEnFechaEscenario2() {
        //given
        LocalDateTime proximoLunes10H = LocalDate.now()
                .with( TemporalAdjusters.next(DayOfWeek.MONDAY) )
                .atTime(10,0);

        var medico= registrarMedico("Pedro","p@mail.com","123456",Especialidad.CARDIOLOGIA);

        //when
        List<Medico> medicosLibre = medicoRepository.selecionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA, proximoLunes10H);
        Medico medicoLibre = seleccionarMedicoAleatorio(medicosLibre);
        //then
        assertTrue(medicoLibre == medico);
    }
    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha){
        em.persist(new Consulta(null, medico,paciente,fecha,null));
    }

    private Medico registrarMedico(String nombre, String mail, String documento, Especialidad especialidad){
        Medico medico = new Medico(datosMedico(nombre,mail,documento,especialidad));
        em.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String mail, String documento){
        Paciente paciente = new Paciente(datosPaciente(nombre, mail,documento));
        em.persist(paciente);
        return paciente;
    }
    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
        return new DatosRegistroMedico(
                nombre,
                email,
                "61999999999",
                documento,
                especialidad,
                datosDireccion()
        );
    }

    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
        return new DatosRegistroPaciente(
                nombre,
                email,
                "61999999999",
                documento,
                datosDireccion()
        );
    }

    private DatosDireccion datosDireccion() {
        return new DatosDireccion(
                " loca",
                "azul",
                "acapulpo",
                "321",
                "12"
        );
    }

    private Medico seleccionarMedicoAleatorio(List<Medico> medicos) {
        if (medicos.isEmpty()) {
            return null;
        }
        int indiceAleatorio = new Random().nextInt(medicos.size());
        return medicos.get(indiceAleatorio);
    }

}