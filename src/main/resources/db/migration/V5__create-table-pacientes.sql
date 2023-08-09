CREATE TABLE pacientes(
    id BIGINT NOT NULL AUTO_INCREMENT ,
    nombre varchar(100) NOT NULL ,
    email varchar(100) NOT NULL unique ,
    documento varchar(14) NOT NULL unique ,
    calle varchar(100) not null ,
    distrito varchar(100) not null ,
    complemento varchar(100),
    numero varchar(20),
    ciudad varchar(100) not null ,
    telefono varchar(20) not null ,
    activo tinyint not null ,
    primary key (id)
);