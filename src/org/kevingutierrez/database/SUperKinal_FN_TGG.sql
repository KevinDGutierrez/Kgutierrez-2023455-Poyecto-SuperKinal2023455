use SuperKinal;

Delimiter $$
create procedure sp_asignarTotalFactura(in factId int, in totalFact decimal (10,2))
Begin
	Update facturas
		set total = totalFact
			where facturaId =factId; 
End $$
Delimiter ;

Delimiter $$
create procedure sp_modificarStock(in detaFactId int, in stockActual int)
begin
	Update productos
		set cantidadStock = stockActual
			where productoId = detaFactId;
end $$
Delimiter ;

Delimiter $$
create procedure sp_asignarTotalCompra(in compId int, in totalC decimal (10,2))
Begin
	Update compras
		set totalCompra = totalC
			where compraId =compId; 
End $$
Delimiter ;

Delimiter $$
create procedure sp_modificarStockCompra(in detaFactId int, in stockActual int)
begin
	Update productos
		set cantidadStock = stockActual
			where productoId = detaFactId;
end $$
Delimiter ;

DELIMITER $$
CREATE FUNCTION fn_CalcularPromociones(prodId INT) RETURNS DECIMAL(10,2) DETERMINISTIC
BEGIN
    DECLARE resultado INT DEFAULT 0;
    DECLARE i INT DEFAULT 1;
    DECLARE fechaFin DATE;

    SET resultado = 0; 
    
    resultadoLoop: LOOP
        SELECT fechaFinalizacion INTO fechaFin FROM Promociones
        WHERE promocionId = i AND productoId = prodId;

        IF fechaFin IS NOT NULL THEN
            IF fechaFin > DATE(NOW()) THEN
                SET resultado = 1; 
            END IF;
        END IF;

        SET i = i + 1; 

        IF i > (SELECT COUNT(*) FROM Promociones WHERE productoId = prodId) THEN
            LEAVE resultadoLoop; 
        END IF;
    END LOOP resultadoLoop;

    RETURN resultado;
END$$
DELIMITER ;





Delimiter $$
create function fn_CalcularTotalFactura(factId int) returns decimal(10,2) deterministic
begin
    declare total decimal(10,2) default 0.0;
    declare i int default 1;
    declare precio decimal(10,2);
    declare curCantidadCompra, curProductoId int;

    totalLoop: loop
        if fn_CalcularPromociones(factId) = 0 then
            if factId = (select facturaId from detalleFactura DF where detalleFacturaId = i) then
                set precio = (select P.precioVentaUnitario from Productos P where productoId = (select productoId from detalleFactura where detalleFacturaId = i));
                set total = total + precio + (precio*0.12);
            end if;
        else 
            if factId = (select facturaId from detalleFactura DF where detalleFacturaId = i) then
                set precio = (select PR.precioPromocion from Prmociones PR where productoId = (select productoId from detalleFactura where detalleFacturaId = i));
                set total = total + precio + (precio*0.12);
            end if;
        end if;

        if i = (select count(*) from detalleFactura) then
            leave totalLoop;
        end if;

        set i = i + 1;
    end loop totalLoop;

    call sp_asignarTotalFactura(factId,total);

    return total;
end $$
Delimiter ;


Delimiter $$
create function fn_PrecioTotalCompras(compId int) returns decimal (10,2) deterministic
begin
	declare totalC decimal (10,2) default 0.0;
    declare i int default 1;
    declare precio decimal (10,2);
    declare cantidadComprada int default 0;
    declare curCantidadCompra, curProductoId, curCompraId int;
    
    declare cursorDetalleCompra cursor for
		select DC.cantidadCompra, DC.productoId, DC.compraId from DetalleCompra DC
	;
    
    open cursorDetalleCompra;
    
    totalLoop : loop
    fetch cursorDetalleCompra into curCantidadCompra, curProductoId, curCompraId;
    
    if compId = curCompraId then
		set precio = (select P.precioCompra from Productos P where P.productoId = curProductoId);
		set cantidadComprada = curCantidadCompra;
		set totalC = totalC + (precio * cantidadComprada + (cantidadComprada*precio*0.12));
    end if;
    
    if i = (select count(*) from detalleCompra) then
		leave totalLoop;
    end if;
    
    set i = i +1;
    end loop totalLoop;
    
    call sp_asignarTotalCompra(compId,totalC);
    
    return totalC;
end $$
Delimiter ;


Delimiter $$
create function fn_IncrementarStockProducto(productId int) returns int deterministic
begin
    declare stockActual int default 0;
    declare cantidadComprada int default 0;
    declare cantidad int default 0;
	
    select cantidadStock into cantidad from productos where productoId = productId LIMIT 1;
    select cantidadCompra into cantidadComprada from detalleCompra where productoId = productId LIMIT 1;
    
    set stockActual = stockActual + cantidadComprada + cantidad;
    
    call sp_modificarStockCompra(productId, stockActual);
    
    return stockActual;
end $$
Delimiter ;

Delimiter $$
create function fn_eliminarStockProducto(productId int) returns int deterministic
begin
    declare stockActual int default 0;
    declare cantidadComprada int default 0;

    select cantidadStock into cantidadComprada from Productos where productoId = productId;
    
    set stockActual = cantidadComprada - 1;
    
    call sp_modificarStock(productId, stockActual);
    
    return stockActual;
end $$
Delimiter ;


Delimiter $$
create trigger tg_totalCompras
after insert on DetalleCompra
for each row
Begin
    declare totalC decimal(10,2);
    declare stockActual int;
    
    set totalC= fn_PrecioTotalCompras(new.compraId);
    set stockActual = fn_IncrementarStockProducto(new.productoId); 
End$$
Delimiter ;

Delimiter $$
create trigger tg_totalFacturas
after insert on DetalleFactura
for each row
Begin
    declare totalFact decimal(10,2);
    declare stockActual int;
    
    set totalFact = fn_CalcularTotalFactura(new.facturaId);
    set stockActual = fn_eliminarStockProducto(new.productoId); 
End$$
Delimiter ;

set global time_zone = '-6:00';