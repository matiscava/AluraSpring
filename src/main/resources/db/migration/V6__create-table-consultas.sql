CREATE TABLE IF NOT EXISTS consultas(
    id bigint not null auto_increment,
    medico_id bigint not null,
    paciente_id bigint not null,
    data datetime not null,

    primary key (id),

    CONSTRAINT  fk_consultas_medico_id foreign key (medico_id) references medicos(id),
    CONSTRAINT  fk_consultas_paciente_id foreign key (paciente_id) references pacientes(id)
);