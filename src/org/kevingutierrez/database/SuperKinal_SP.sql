USE SuperKinal;

-- ********************************** CRUDS ********************************** --
 
-- ********************************** CLIENTES ********************************** --

DELIMITER $$

CREATE PROCEDURE sp_AgregarClientes(IN nom VARCHAR(30), IN ape VARCHAR(30), IN tel VARCHAR(80), IN dir VARCHAR(50), IN nit VARCHAR(15))
    BEGIN
        INSERT INTO Clientes (nombre, apellido, telefono, direccion, nit)
            VALUES (nom, ape, tel, dir, nit);
     END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_ListarClientes()
    BEGIN
        SELECT
			Clientes.clienteId,
            Clientes.nombre,
            Clientes.apellido,
            Clientes.telefono,
            Clientes.direccion,
            Clientes.nit
                FROM Clientes;
    END$$

DELIMITER ;
 
DELIMITER $$
 
CREATE PROCEDURE sp_BuscarClientes(IN cliId INT)
	BEGIN
		SELECT
            Clientes.clienteId,
            Clientes.nombre,
            Clientes.apellido,
            Clientes.telefono,
            Clientes.direccion,
            Clientes.nit
				FROM Clientes
					WHERE clienteId = cliId;

	END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_EliminarClientes(IN cliId INT)

	BEGIN
		DELETE FROM Clientes
			WHERE clienteId = cliId;

	END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_EditarClientes(IN cliId INT, IN nom VARCHAR(30), IN ape VARCHAR(30), IN tel VARCHAR(80), IN dir VARCHAR(50), IN nit VARCHAR(15))
	BEGIN
		UPDATE Clientes
			SET
				nombre = nom,
				apellido = ape,
				telefono = tel,
				direccion = dir,
				nit = nit
					WHERE clienteId = cliId;
	END$$

DELIMITER ;

-- ********************************** CARGOS ********************************** --

DELIMITER $$

CREATE PROCEDURE sp_AgregarCargos(IN nomCar VARCHAR(30), IN desCar VARCHAR(300))
    BEGIN
        INSERT INTO Cargos (nombreCargo, descripcionCargo)
            VALUES (nomCar, desCar);
     END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_ListarCargos()
    BEGIN
        SELECT
			Cargos.cargoId,
            Cargos.nombreCargo,
            Cargos.descripcionCargo
                FROM Cargos;
    END$$

DELIMITER ;
 
DELIMITER $$
 
CREATE PROCEDURE sp_BuscarCargos(IN carId INT)
	BEGIN
		SELECT
            Cargos.cargoId,
            Cargos.nombreCargo,
            Cargos.descripcionCargo
				FROM Cargos
					WHERE cargoId = carId;

	END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_EliminarCargos(IN carId INT)
	BEGIN
		DELETE FROM Cargos
			WHERE cargoId = carId;
	END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_EditarCargos(IN carId INT, IN nomCar VARCHAR(30), IN desCar VARCHAR(100))
	BEGIN
		UPDATE Cargos
			SET
				nombreCargo = nomCar,
				descripcionCargo = desCar
					WHERE cargoId = carId;
	END$$

DELIMITER ;

-- ********************************** EMPLEADOS ********************************** --

DELIMITER $$
CREATE PROCEDURE sp_AgregarEmpleado(nomE VARCHAR(30), apeE VARCHAR(30), sueldoE DECIMAL(10,2), horaEntradaE TIME, horaSalidaE TIME, cargoIdE INT)
BEGIN
    INSERT INTO Empleados(nombreEmpleado, apellidoEmpleado, sueldo, horaentrada, horaSalida, cargoId)
    VALUES (nomE, apeE, sueldoE, horaEntradaE, horaSalidaE, cargoIdE );
END$$
DELIMITER ;




DELIMITER $$
CREATE PROCEDURE sp_ListarEmpleados()
BEGIN
    SELECT EP.empleadoId, EP.nombreEmpleado, EP.apellidoEmpleado, EP.sueldo, EP.horaentrada, EP.horaSalida,
        CONCAT("Id: ", Ca.cargoId, " | ", Ca.nombreCargo) AS cargo, 
        CONCAT(EE.nombreEmpleado, ' ', EE.apellidoEmpleado) AS nombreEncargado
    FROM Empleados EP
    JOIN Cargos Ca ON EP.cargoId = Ca.cargoId
    LEFT JOIN Empleados EE ON EP.encargadoId = EE.empleadoId;
END$$
DELIMITER ;
call sp_ListarEmpleados();





DELIMITER $$
CREATE PROCEDURE sp_EliminarEmpleado(IN empId INT)
BEGIN
    DELETE FROM Empleados 
    WHERE empleadoId = empId;
END$$
DELIMITER ;




DELIMITER $$
CREATE PROCEDURE sp_BuscarEmpleado(IN empId INT)
BEGIN
    SELECT EP.empleadoId, EP.nombreEmpleado, EP.apellidoEmpleado, EP.sueldo, EP.horaentrada, EP.horaSalida,
        CONCAT("Id: ", Ca.cargoId, " | ", Ca.nombreCargo) AS cargo, 
        CONCAT(EE.nombreEmpleado, ' ', EE.apellidoEmpleado) AS nombreEncargado
    FROM Empleados EP
    JOIN Cargos Ca ON EP.cargoId = Ca.cargoId
    LEFT JOIN Empleados EE ON EP.encargadoId = EE.empleadoId
	WHERE EP.empleadoId = empId;
END$$
DELIMITER ;




DELIMITER $$
CREATE PROCEDURE sp_EditarEmpleado(IN empId INT, IN nomE VARCHAR(30), IN apeE VARCHAR(30), IN sueldoE DECIMAL(10,2), IN horaEntradaE TIME, IN horaSalidaE TIME, IN cargoIdE INT)
BEGIN
    UPDATE Empleados
    SET nombreEmpleado = nomE, apellidoEmpleado = apeE, sueldo = sueldoE, horaentrada = horaEntradaE, horaSalida = horaSalidaE, cargoId = cargoIdE 
    WHERE empleadoId = empId;
END$$
DELIMITER ;



-- ENCARGADO 
Delimiter $$
create procedure sp_AsignarEncargado(In empId Int, In encarId int)
begin

	Update Empleados  
		Set 
			Empleados.encargadoId = encarId
			Where empleadoId = empId;
end$$
Delimiter ;

-- ********************************** FACTURAS ********************************** --

DELIMITER $$
CREATE PROCEDURE sp_AgregarFactura(cliId INT, empId INT)
BEGIN
    INSERT INTO Facturas(fecha, hora, clienteId, empleadoId)
    VALUES (curdate(), curtime(), cliId, empId);
END$$
DELIMITER ;



DELIMITER $$
CREATE PROCEDURE sp_ListarFacturas()
BEGIN
    SELECT F.facturaId, F.fecha, F.hora, 
		   CONCAT("Id: ", C.clienteId, " | ", C.nombre, " ", C.apellido) AS cliente,
		   CONCAT("Id: ", E.empleadoId, " | ", E.nombreEmpleado, " ", E.apellidoEmpleado) AS empleado,
		   F.total
	FROM Facturas F
	JOIN Clientes C ON F.clienteId = C.clienteId
	JOIN Empleados E ON F.empleadoId = E.empleadoId;

END$$
DELIMITER ;




DELIMITER $$
CREATE PROCEDURE sp_EliminarFactura(IN factId INT)
BEGIN
    DELETE FROM Facturas 
    WHERE facturaId = factId;
END$$
DELIMITER ;




DELIMITER $$
CREATE PROCEDURE sp_BuscarFactura(IN factId INT)
BEGIN
    SELECT F.facturaId, F.fecha, F.hora, 
		   CONCAT("Id: ", C.clienteId, " | ", C.nombre, " ", C.apellido) AS cliente,
		   CONCAT("Id: ", E.empleadoId, " | ", E.nombreEmpleado, " ", E.apellidoEmpleado) AS empleado,
		   F.total
		FROM Facturas F
	JOIN Clientes C ON F.clienteId = C.clienteId
	JOIN Empleados E ON F.empleadoId = E.empleadoId
	WHERE facturaId = factId;
END$$
DELIMITER ;




DELIMITER $$
CREATE PROCEDURE sp_EditarFactura(IN factId INT, IN cliId INT, IN empId INT)
BEGIN
    UPDATE Facturas
    SET fecha = curdate(), hora = curtime(), clienteId = cliId, empleadoId = empId
    WHERE facturaId = factId;
END$$
DELIMITER ;



-- ********************************** TICKET SOPORTES ********************************** --

DELIMITER $$

CREATE PROCEDURE sp_AgregarTicketSoportes(IN desTic VARCHAR(250), IN cliId INT, IN facId INT)
    BEGIN
        INSERT INTO TicketSoportes (descripcionTicket, estatus, clienteId, facturaId)
            VALUES (desTic, 'Recien creado', cliId, facId);
     END$$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE sp_ListarTicketSoportes()
    BEGIN
        SELECT TS.ticketSoporteId, TS.descripcionTicket, TS.estatus,
        CONCAT("Datos del Cliente : ", "ID : ", C.clienteId, " ", C.nombre, " ", C.apellido, " Nit : ", C.nit) AS Cliente, TS.facturaId FROM TicketSoportes TS
        JOIN Clientes C on TS.clienteId = C.clienteId
        ;
    END$$

DELIMITER ;
 
DELIMITER $$
 
CREATE PROCEDURE sp_BuscarTicketSoportes(IN ticSopId INT)
	BEGIN
		SELECT
            TicketSoportes.ticketSoporteId,
            TicketSoportes.descripcionTicket,
            TicketSoportes.estatus,
            TicketSoportes.clienteId,
            TicketSoportes.facturaId
				FROM TicketSoportes
					WHERE ticketSoporteId = ticSopId;

	END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_EliminarTicketSoportes(IN TicSopId INT)

	BEGIN
		DELETE FROM TicketSoportes
			WHERE ticketSoporteId = ticSopId;

	END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_EditarTicketSoportes(IN ticSopId INT, IN desTic VARCHAR(250), IN est VARCHAR(30), IN cliId INT, IN facId INT)
	BEGIN
		UPDATE TicketSoportes
			SET
				descripcionTicket = desTic,
				estatus = est,
				clienteId = cliId,
				facturaId = facId
				WHERE ticketSoporteId = ticSopId;
	END$$

DELIMITER ;

-- ********************************** DISTRIBUIDORES ********************************** --

DELIMITER $$

CREATE PROCEDURE sp_AgregarDistribuidores(IN nomDis VARCHAR(30), IN dirDis VARCHAR(200), IN ntDis VARCHAR(15), IN telDis VARCHAR(15), IN wb VARCHAR(50))
    BEGIN
        INSERT INTO Distribuidores (nombreDistribuidor, direccionDistribuidor, nitDistribuidor, telefonoDistribuidor, web)
            VALUES (nomDis, dirDis, ntDis, telDis, wb);
     END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_ListarDistribuidores()
    BEGIN
        SELECT
			Distribuidores.distribuidorId,
            Distribuidores.nombreDistribuidor,
            Distribuidores.direccionDistribuidor,
            Distribuidores.nitDistribuidor,
            Distribuidores.telefonoDistribuidor,
            Distribuidores.web
                FROM Distribuidores;
    END$$

DELIMITER ;
 
DELIMITER $$
 
CREATE PROCEDURE sp_BuscarDistribuidores(IN disId INT)
	BEGIN
		SELECT
            Distribuidores.distribuidorId,
            Distribuidores.nombreDistribuidor,
            Distribuidores.direccionDistribuidor,
            Distribuidores.nitDistribuidor,
            Distribuidores.telefonoDistribuidor,
            Distribuidores.web
				FROM Distribuidores
					WHERE distribuidorId = disId;

	END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_EliminarDistribuidores(IN disId INT)

	BEGIN
		DELETE FROM Distribuidores
			WHERE distribuidorId = disId;

	END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_EditarDistribuidores(IN disId INT, IN nomDis VARCHAR(30), IN dirDis VARCHAR(200), IN ntDis VARCHAR(15), IN telDis VARCHAR(15), IN wb VARCHAR(50))
	BEGIN
		UPDATE Distribuidores
			SET
				nombreDistribuidor = nomDis,
				direccionDistribuidor = dirDis,
				nitDistribuidor = ntDis,
				telefonoDistribuidor = telDis,
				web = wb
					WHERE distribuidorId = disId;
	END$$

DELIMITER ;

-- ********************************** CATEGORIA PRODUCTOS ********************************** --

DELIMITER $$

CREATE PROCEDURE sp_AgregarCategoriaProductos(IN nomCat VARCHAR(30), IN desCat VARCHAR(100))
    BEGIN
        INSERT INTO CategoriaProductos (nombreCategoria, descripcionCategoria)
            VALUES (nomCat, desCat);
     END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_ListarCategoriaProductos()
    BEGIN
        SELECT
			CategoriaProductos.categoriaProductoId,
            CategoriaProductos.nombreCategoria,
            CategoriaProductos.descripcionCategoria
                FROM CategoriaProductos;
    END$$

DELIMITER ;


 
DELIMITER $$
 
CREATE PROCEDURE sp_BuscarCategoriaProductos(IN catProId INT)
	BEGIN
		SELECT
            CategoriaProductos.categoriaProductoId,
            CategoriaProductos.nombreCategoria,
            CategoriaProductos.descripcionCategoria
				FROM CategoriaProductos
					WHERE categoriaProductoId = catProId;

	END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_EliminarCategoriaProductos(IN catProId INT)

	BEGIN
		DELETE FROM CategoriaProductos
			WHERE categoriaProductoId = catProId;

	END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_EditarCategoriaProductos(IN catProId INT, IN nomCat VARCHAR(30), IN desCat VARCHAR(100))
	BEGIN
		UPDATE CategoriaProductos
			SET
				nombreCategoria = nomCat,
				descripcionCategoria = desCat
					WHERE categoriaProductoId = catProId;
	END$$

DELIMITER ;

-- ********************************** PRODUCTOS ********************************** --

DELIMITER $$
CREATE PROCEDURE sp_AgregarProductos(nomP VARCHAR(50), descP VARCHAR(100), cantStock INT, precioVenta DECIMAL(10,2), precioVentaM DECIMAL(10,2), precioComp DECIMAL(10,2), imgP longblob, distId INT, cateId INT)
BEGIN
	INSERT INTO Productos(nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, imagenProducto, distribuidorId, categoriaProductoId)
	VALUES (nomP, descP, cantStock, precioVenta, precioVentaM, precioComp, imgP, distId, cateId);
END$$
DELIMITER ;



DELIMITER $$
CREATE PROCEDURE sp_ListarProductos()
BEGIN
	SELECT Productos.productoId, Productos.nombreProducto, Productos.descripcionProducto, Productos.cantidadStock, Productos.precioVentaUnitario, Productos.precioVentaMayor,  Productos.precioCompra,Productos.imagenProducto, 
       CONCAT("Distribuidor: ", D.nombreDistribuidor) AS distribuidor,
       CONCAT("Categoría: ", CP.nombreCategoria) AS categoria
	FROM Productos
	LEFT JOIN Distribuidores D ON Productos.distribuidorId = D.distribuidorId
	LEFT JOIN CategoriaProductos CP ON Productos.categoriaProductoId = CP.categoriaProductoId;

END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE sp_EliminarProducto(IN prodId INT)
BEGIN
	DELETE FROM Productos 
		WHERE productoId = prodId;
END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE sp_BuscarProducto(IN prodId INT)
BEGIN
	SELECT Productos.productoId, Productos.nombreProducto, Productos.descripcionProducto, Productos.cantidadStock, Productos.precioVentaUnitario, Productos.precioVentaMayor,  Productos.precioCompra,Productos.imagenProducto, 
       CONCAT("Distribuidor: ", D.nombreDistribuidor) AS distribuidor,
       CONCAT("Categoría: ", CP.nombreCategoria) AS categoria
		FROM Productos
		LEFT JOIN Distribuidores D ON Productos.distribuidorId = D.distribuidorId
		LEFT JOIN CategoriaProductos CP ON Productos.categoriaProductoId = CP.categoriaProductoId
		WHERE productoId = prodId;
END$$
DELIMITER ;



DELIMITER $$
CREATE PROCEDURE sp_EditarProducto(IN prodId INT, IN nomP VARCHAR(50), IN descP VARCHAR(100), IN cantStock INT, IN precioVenta DECIMAL(10,2), IN precioVentaM DECIMAL(10,2), IN precioComp DECIMAL(10,2), IN imgP longblob, IN distId INT, IN catId INT)
BEGIN
	UPDATE Productos 
	SET nombreProducto = nomP, descripcionProducto = descP, cantidadStock = cantStock, precioVentaUnitario = precioVenta, precioVentaMayor = precioVentaM, precioCompra = precioComp, imagenProducto = imgP, distribuidorId = distId, categoriaproductosId = catId 
    WHERE productoId = prodId;
END$$
DELIMITER ;




-- ********************************** PROMOCIONES ********************************** --

DELIMITER $$

CREATE PROCEDURE sp_AgregarPromociones(IN prePro DECIMAL(10,2), IN desPro VARCHAR(100), IN fecIni DATE, IN FecFin DATE, IN proId INT)
    BEGIN
        INSERT INTO Promociones (precioPromocion, descripcionPromocion, fechaInicio, fechaFinalizacion, productoId)
            VALUES (prePro, desPro, fecIni, FecFin, proId);
     END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_ListarPromociones()
BEGIN
    SELECT 
		PR.promocionId, 
		PR.precioPromocion, 
		PR.descripcionPromocion, 
		PR.fechaInicio, 
		PR.fechaFinalizacion, 
		CONCAT("Id: ", P.productoId," | ", P.nombreProducto) AS Producto
		
	FROM 
		Promociones PR
	JOIN 
		Productos P ON PR.productoId = P.productoId;

END$$

DELIMITER ;
 
DELIMITER $$
 
CREATE PROCEDURE sp_BuscarPromocion(IN promId INT)
BEGIN
    SELECT 
		PR.promocionId, 
		PR.precioPromocion, 
		PR.descripcionPromocion, 
		PR.fechaInicio, 
		PR.fechaFinalizacion, 
		CONCAT("Id: ", P.productoId," | ", P.nombreProducto) AS Producto
	FROM 
		Promociones PR
	JOIN 
		Productos P ON PR.productoId = P.productoId
	WHERE promocionId = promId;
END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_EliminarPromociones(IN promoId INT)

	BEGIN
		DELETE FROM Promociones
			WHERE promocionId = promoId;

	END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_EditarPromociones(IN promoId INT, IN prePro DECIMAL(10,2), IN desPro VARCHAR(100), IN fecIni DATE, IN fecFin DATE, IN proId INT)
	BEGIN
		UPDATE Promociones
			SET
				precioPromocion = prePro,
				descripcionPromocion = desPro,
				fechaInicio = fecIni,
				fechaFinalizacion = fecFin,
				productoId = proId
					WHERE promocionId = promoId;
	END$$

DELIMITER ;

-- ********************************** DETALLE FACTURAS ********************************** --

DELIMITER $$
CREATE PROCEDURE sp_AgregarDetalleFactura(prodIdFact INT, factIdFact INT)
BEGIN
    INSERT INTO detalleFactura(productoId, facturaId)
    VALUES (prodIdFact, factIdFact);
END$$
DELIMITER ;



DELIMITER $$
CREATE PROCEDURE sp_ListarDetalleFactura()
BEGIN
    SELECT detalleFacturaId, 
        productoId, 
        facturaId
    FROM detalleFactura;
END$$
DELIMITER ;




DELIMITER $$
CREATE PROCEDURE sp_EliminarDetalleFactura(IN detFactId INT)
BEGIN
    DELETE FROM detalleFactura 
    WHERE detalleFacturaId = detFactId;
END$$
DELIMITER ;




DELIMITER $$
CREATE PROCEDURE sp_BuscarDetalleFactura(IN detFactId INT)
BEGIN
    SELECT detalleFacturaId, 
        productoId, 
        facturaId
    FROM detalleFactura 
    WHERE detalleFacturaId = detFactId;
END$$
DELIMITER ;




DELIMITER $$
CREATE PROCEDURE sp_EditarDetalleFactura(IN detFactId INT, IN prodIdFact INT, IN factIdFact INT)
BEGIN
    UPDATE detalleFactura
    SET productoId = prodIdFact, facturaId = factIdFact
    WHERE detalleFacturaId = detFactId;
END$$
DELIMITER ;


-- ********************************** COMPRAS ********************************** --

DELIMITER $$
CREATE PROCEDURE sp_AgregarCompra()
BEGIN
    INSERT INTO Compras(fechaCompra)
    VALUES (curdate());
END$$
DELIMITER ;



DELIMITER $$
CREATE PROCEDURE sp_ListarCompras()
BEGIN
    SELECT compraId, 
		fechaCompra, 
		totalCompra
	FROM Compras;
END$$
DELIMITER ;
CALL sp_ListarCompras();



DELIMITER $$
CREATE PROCEDURE sp_EliminarCompra(IN compId INT)
BEGIN
    DELETE FROM Compras 
    WHERE compraId = compId;
END$$
DELIMITER ;




DELIMITER $$
CREATE PROCEDURE sp_BuscarCompra(IN compId INT)
BEGIN
    SELECT compraId, 
		fechaCompra, 
		totalCompra
	FROM Compras 
	WHERE compraId = compId;
END$$
DELIMITER ;




DELIMITER $$
CREATE PROCEDURE sp_EditarCompra(IN compId INT, IN fechaComp DATE)
BEGIN
    UPDATE Compras
    SET fechaCompra = fechaComp
    WHERE compraId = compId;
END$$
DELIMITER ;



-- ********************************** DETALLE COMPRAS ********************************** --

DELIMITER $$
CREATE PROCEDURE sp_AgregarDetalleCompra(cantComp INT, prodIdComp INT, compId INT)
BEGIN
    INSERT INTO detalleCompra(cantidadCompra, productoId, compraId)
    VALUES (cantComp, prodIdComp, compId);
END$$
DELIMITER ;




DELIMITER $$
CREATE PROCEDURE sp_ListarDetalleCompra()
BEGIN
    SELECT detalleCompraId, 
		cantidadCompra, 
		productoId, 
		compraId
	FROM detalleCompra;
END$$
DELIMITER ;




DELIMITER $$
CREATE PROCEDURE sp_EliminarDetalleCompra(IN detCompId INT)
BEGIN
    DELETE FROM detalleCompra 
    WHERE detalleCompraId = detCompId;
END$$
DELIMITER ;




DELIMITER $$
CREATE PROCEDURE sp_BuscarDetalleCompra(IN detCompId INT)
BEGIN
    SELECT detalleCompraId, 
		cantidadCompra, 
		productoId, 
		compraId
	FROM detalleCompra 
	WHERE detalleCompraId = detCompId;
END$$
DELIMITER ;




DELIMITER $$
CREATE PROCEDURE sp_EditarDetalleCompra(IN detCompId INT, IN cantComp INT, IN prodIdComp INT, IN compId INT)
BEGIN
    UPDATE detalleCompra
    SET cantidadCompra = cantComp, productoId = prodIdComp, compraId = compId
    WHERE detalleCompraId = detCompId;
END$$
DELIMITER ;

-- ******* LOGIN ******* --

DELIMITER $$

CREATE PROCEDURE sp_agregarUsuario(
    IN us VARCHAR(30), IN con VARCHAR(100), IN nivAccId INT, IN emp INT)
BEGIN
    INSERT INTO Usuarios(usuario, contrasenia, nivelAccesoId, empleadoId) 
    VALUES (us, con, nivAccId, emp);
END $$

DELIMITER ;

Delimiter $$
create procedure sp_buscarUsuario(us varchar(30))
begin
	select * from Usuarios
		where usuario = us;
end $$
delimiter ;


Delimiter $$
create procedure sp_listarNivelAcceso()
begin
	select * from nivelesAcceso;
end $$
delimiter ;
select * from Usuarios;



set global time_zone = '-6:00';