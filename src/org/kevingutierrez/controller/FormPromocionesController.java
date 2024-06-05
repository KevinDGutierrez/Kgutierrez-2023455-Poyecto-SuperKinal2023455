/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kevingutierrez.controller;

import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.kevingutierrez.dao.Conexion;
import org.kevingutierrez.dto.PromocionDTO;
import org.kevingutierrez.model.Producto;
import org.kevingutierrez.model.Promocion;
import org.kevingutierrez.system.Main;
import org.kevingutierrez.utils.SuperKinalAlert;


public class FormPromocionesController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
   @FXML
    Button btnRegresarFMA,btnGuardar;
   
   @FXML
   TextField tfPromocionId,tfPrecio,tfDescripcion,tfFechaI,tfFechaF;
   
   @FXML
   ComboBox cmbProductos;
   
   @FXML
    private void handleButtonAction(ActionEvent event) {
    
        if(event.getSource() == btnRegresarFMA){
            PromocionDTO.getPromocionDTO().setPromocion(null);
            stage.menuPromocionesView();
        }else if(event.getSource() == btnGuardar){
            if(op == 1){
                if(!tfPrecio.getText().equals("") && !tfFechaI.getText().equals("") && !tfFechaF.getText().equals("")){
                    agregarPromocion();
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(400);
                    stage.menuPromocionesView();
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(33);
                    if(tfPrecio.getText().equals("")){
                        tfPrecio.requestFocus();
                    }else if(tfFechaI.getText().equals("")){
                        tfFechaI.requestFocus();
                    }else if(tfFechaF.getText().equals("")){
                        tfFechaF.requestFocus();
                    }
                }
                
               
            }else if(op == 2){
                if(!tfPrecio.getText().equals("") && !tfFechaI.getText().equals("") && !tfFechaF.getText().equals("")){
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505).get() == ButtonType.OK){
                    editarPromocion();
                        PromocionDTO.getPromocionDTO().setPromocion(null);
                        SuperKinalAlert.getInstance().mostrarAlertaInformacion(500);
                        stage.menuPromocionesView();
                    }else{
                        stage.menuPromocionesView();
                    }
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(33);
                    if(tfPrecio.getText().equals("")){
                        tfPrecio.requestFocus();
                    }else if(tfFechaI.getText().equals("")){
                        tfFechaI.requestFocus();
                    }else if(tfFechaF.getText().equals("")){
                        tfFechaF.requestFocus();
                    }
                }
                
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(PromocionDTO.getPromocionDTO().getPromocion() != null){
            cargarDatos(PromocionDTO.getPromocionDTO().getPromocion());
        }
        cmbProductos.setItems(listarProductos());
        
    }
    
    public void cargarDatos(Promocion Promocion){
        tfPromocionId.setText(Integer.toString(Promocion.getPromocionId()));
        tfPrecio.setText(Double.toString(Promocion.getPrecioProm()));
        tfDescripcion.setText(Promocion.getDescripcionProm());
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
        tfFechaI.setText(formatoFecha.format(Promocion.getFechaInicio()));
        tfFechaF.setText(formatoFecha.format(Promocion.getFechaFin()));
    }
    
    public void agregarPromocion(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_AgregarPromociones(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfPrecio.getText());
            statement.setString(2, tfDescripcion.getText());
            statement.setString(3, tfFechaI.getText());
            statement.setString(4, tfFechaF.getText());
            statement.setInt(5,((Producto)cmbProductos.getSelectionModel().getSelectedItem()).getProductoId());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void editarPromocion(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_EditarPromocion(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfPromocionId.getText()));
            statement.setString(2, tfPrecio.getText());
            statement.setString(3, tfDescripcion.getText());
            statement.setString(4, tfFechaI.getText());
            statement.setString(5, tfFechaF.getText());
            statement.setInt(6,((Producto)cmbProductos.getSelectionModel().getSelectedItem()).getProductoId());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public ObservableList<Producto> listarProductos(){
        ArrayList<Producto> productos = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " CALL sp_ListarProductos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int productoId = resultSet.getInt("productoId");
                String nombreProducto = resultSet.getString("nombreProducto");
                String descripcionProducto = resultSet.getString("descripcionProducto");
                int cantidadStock = resultSet.getInt("cantidadStock");
                double precioVentaUnitario = resultSet.getDouble("precioVentaUnitario");
                double precioVentaMayor = resultSet.getDouble("precioVentaMayor");
                double precioCompra = resultSet.getDouble("precioCompra");
                Blob imagenProducto = resultSet.getBlob("imagenProducto");
                String distribuidor = resultSet.getString("distribuidor");
                String categoria = resultSet.getString("categoria");
            
                productos.add(new Producto(productoId, nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor,precioCompra,imagenProducto,distribuidor,categoria));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                
                if(statement != null){
                    statement.close();
                }
                
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        
        
        return FXCollections.observableList(productos);
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    public void setOp(int op) {
        this.op = op;
    }
    
}
