 -- DROP DATABASE IF EXISTS SuperKinal;

CREATE DATABASE IF NOT EXISTS SuperKinal;

USE SuperKinal;

-- ********************************** TABLAS ********************************** --

CREATE TABLE Clientes(
	clienteId INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,
    apellido VARCHAR(30) NOT NULL,
    telefono VARCHAR(15),
    direccion VARCHAR(150) NOT NULL,
    nit VARCHAR(15),
    PRIMARY KEY PK_clienteId(clienteId)
);

CREATE TABLE Cargos(
	cargoId INT NOT NULL AUTO_INCREMENT,
    nombreCargo VARCHAR(30) NOT NULL,
    descripcionCargo VARCHAR(100) NOT NULL,
    PRIMARY KEY PK_cargoId(cargoId)
);

create table Empleados(
		empleadoId int not null auto_increment,
        nombreEmpleado varchar (30) not null,	
        apellidoEmpleado varchar (30) not null,
        sueldo decimal(10,2) not null,
		horaentrada Time,
        horaSalida time,
        cargoId int not null,
        encargadoId int,
        primary key empleadoId (empleadoId),
        constraint FK_encargadoId_Empleados foreign key (encargadoId)
			references Empleados(empleadoId),
		constraint FK_cargoId_Empleados foreign key (cargoId)
			references Cargos(cargoId)
);

CREATE TABLE Distribuidores(
	distribuidorId INT NOT NULL AUTO_INCREMENT,
    nombreDistribuidor VARCHAR(30) NOT NULL,
    direccionDistribuidor VARCHAR(200) NOT NULL,
    nitDistribuidor VARCHAR(15) NOT NULL,
    telefonoDistribuidor VARCHAR(15) NOT NULL,
    web VARCHAR(50),
    PRIMARY KEY PK_distribuidorId(distribuidorId)
);

CREATE TABLE CategoriaProductos(
	categoriaProductoId INT NOT NULL AUTO_INCREMENT,
    nombreCategoria VARCHAR(30) NOT NULL,
    descripcionCategoria VARCHAR(100) NOT NULL,
    PRIMARY KEY PK_categoriaProductoId(categoriaProductoId)
);

create table Compras(
	compraId int not null auto_increment,
    fechaCompra date not null,
    totalCompra decimal (10,2),
    primary key PK_compraId (compraId)

);

create table Facturas(
	facturaId int not null auto_increment,
    fecha date not null,
    hora time not null,
    clienteId int not null,
    empleadoId int not null,
    total decimal(10,2),
    primary key PK_facturaId (facturaId),
    constraint FK_clienteId_Facturas foreign key (clienteId)
			references Clientes(clienteId),
	constraint FK_empleadoId_Facturas foreign key (empleadoId)
			references Empleados(empleadoId)

);

CREATE TABLE TicketSoportes(
	ticketSoporteId INT NOT NULL AUTO_INCREMENT,
    descripcionTicket VARCHAR(250) NOT NULL,
    estatus VARCHAR(30) NOT NULL,
    clienteId INT NOT NULL,
    facturaId INT,
    PRIMARY KEY PK_ticketSoporteId(ticketSoporteId),
    CONSTRAINT FK_TicketSoportes_Clientes FOREIGN KEY (clienteId)
		REFERENCES Clientes(clienteId),
	CONSTRAINT FK_TicketSoportes_Facturas FOREIGN KEY (facturaId)
		REFERENCES Facturas(facturaId)
);

create table Productos(
	productoId int not null auto_increment,
	nombreProducto varchar(50) not null,
    descripcionProducto varchar(100),
    cantidadStock int not null,
    precioVentaUnitario decimal(10,2),
    precioVentaMayor decimal(10,2),
    precioCompra decimal(10,2),
    imagenProducto longblob,
    distribuidorId int not null,
    categoriaProductoId int not null,
    primary key PK_productoId(productoId),
    constraint FK_distribuidorId_Productos foreign key (distribuidorId)
			references Distribuidores(distribuidorId),
	constraint FK_categoriaProductoId_Productos foreign key (categoriaProductoId)
			references CategoriaProductos(categoriaProductoId)
);

create table detalleCompra(
    detalleCompraId int not null auto_increment,
    cantidadCompra int not null,
    productoId int not null,
    compraId int not null,
    primary key PK_detalleCompraId(detalleCompraId),
    constraint FK_productoId_detalleCompra foreign key (productoId)
            references Productos(productoId),
    constraint FK_compraId_detalleCompra  foreign key (compraId)
            references Compras(compraId)
);

create table detalleFactura(
    detalleFacturaId int not null auto_increment,
	productoId int not null,
    facturaId int not null,
    primary key PK_detalleFacturaId(detalleFacturaId),
	constraint FK_productoId_detalleFactura foreign key (productoId)
            references Productos(productoId),
	constraint FK_facturaId_detalleFactura  foreign key (facturaId)
            references Facturas(facturaId)
);

create table Promociones(
    promocionId int not null auto_increment,
    precioPromocion decimal(10,2) not null,
    descripcionPromocion varchar (100),
    fechaInicio Date not null,
    fechaFinalizacion Date not null,
    productoId int not null,
    primary key promocionId(promocionId),
    constraint FK_productoId_Promociones foreign key (productoId)
            references Productos(productoId)
);

CREATE TABLE NivelesAcceso (
    nivelAccesoId INT NOT NULL AUTO_INCREMENT,
    nivelAcceso VARCHAR(40) NOT NULL,
    PRIMARY KEY (nivelAccesoId)
);

CREATE TABLE Usuarios (
    usuarioId INT NOT NULL AUTO_INCREMENT,
    usuario VARCHAR(30) NOT NULL,
    contrasenia VARCHAR(100) NOT NULL,
    nivelAccesoId INT NOT NULL,
    empleadoId INT NOT NULL,
    PRIMARY KEY (usuarioId),
    CONSTRAINT FK_Usuarios_NivelesAcceso FOREIGN KEY (nivelAccesoId)
        REFERENCES NivelesAcceso(nivelAccesoId),
    CONSTRAINT FK_Usuarios_Empleados FOREIGN KEY (empleadoId)
        REFERENCES Empleados(empleadoId)
);


-- ********************************** DATOS ********************************** -3-
INSERT INTO Cargos(nombreCargo, descripcionCargo) VALUES
	('Acesor','Encargado de acesorar a los clientes');

INSERT INTO Clientes(nombre, apellido, telefono, direccion, nit) VALUES
	('Daniel', 'Rodriguez', '5555-4444', 'Ciudad', 'CF'),
	('Luis', 'Escobar', '9870-7474', 'Ciudad', 'CF'),
	('Andy', 'Pineda', '1244-5743', 'Pueblo', 'CF');
    
    
INSERT INTO NivelesAcceso(nivelAcceso)VALUES 
('Admin'),
('User');

set global time_zone = '-6:00';

SELECT * FROM DetalleFactura
JOIN Facturas ON DetalleFactura.facturaId = Facturas.facturaId
JOIN Clientes ON Facturas.clienteId = Clientes.clienteId
JOIN Productos ON DetalleFactura.productoId = Productos.productoId
WHERE Facturas.facturaId = 1