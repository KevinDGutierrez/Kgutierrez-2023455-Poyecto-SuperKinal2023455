/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kevingutierrez.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.kevingutierrez.dao.Conexion;
import org.kevingutierrez.dto.PromocionDTO;
import org.kevingutierrez.model.Promocion;
import org.kevingutierrez.system.Main;
import org.kevingutierrez.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author kevin
 */
public class MenuPromocionesController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    @FXML
    TableView tblPromociones;
    
    @FXML
    TableColumn colPromocionId,colPrecio,colDescripcion,colFechaI,colFechaF,colProductoId;
    
    @FXML
    Button btnRegresar,btnAgregar,btnEditar,btnEliminar,btnBuscar;
    
    @FXML
    TextField tfPromocionId;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
    
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            stage.formPromocionesView(1);
        }else if(event.getSource() == btnEditar){
            PromocionDTO.getPromocionDTO().setPromocion((Promocion)tblPromociones.getSelectionModel().getSelectedItem());
            stage.formPromocionesView(2);
        }else if(event.getSource() == btnEliminar){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(404).get() == ButtonType.OK){
                eliminarPromocion(((Promocion)tblPromociones.getSelectionModel().getSelectedItem()).getPromocionId());
                cargarDatos();
            }
        }else if (event.getSource() == btnBuscar){
            tblPromociones.getItems().clear();
            if(tfPromocionId.getText().equals("")){
                cargarDatos();
            
            }else{
                op = 3;
                cargarDatos();
            }
        }
    }
    
    public void cargarDatos(){
        if(op == 3){
            tblPromociones.getItems().add(buscarPromocion());
            op = 0;
        }else{
            tblPromociones.setItems(listarPromociones()); 
        }
            colPromocionId.setCellValueFactory(new PropertyValueFactory<Promocion, Integer>("promocionId"));
            colPrecio.setCellValueFactory(new PropertyValueFactory<Promocion, Double>("precioProm"));
            colDescripcion.setCellValueFactory(new PropertyValueFactory<Promocion, String>("descripcionProm"));
            colFechaI.setCellValueFactory(new PropertyValueFactory<Promocion, Date>("fechaInicio"));
            colFechaF.setCellValueFactory(new PropertyValueFactory<Promocion, Date>("fechaFin"));
            colProductoId.setCellValueFactory(new PropertyValueFactory<Promocion, String>("producto"));;
        
    }
    
    public ObservableList<Promocion> listarPromociones(){
        ArrayList<Promocion> promociones = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " CALL sp_ListarPromociones()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int promocionId = resultSet.getInt("promocionId");
                Double precioProm = resultSet.getDouble("precioPromocion");
                String descripcionProm = resultSet.getString("descripcionPromocion");
                Date fechaI = resultSet.getDate("fechaInicio");
                Date fechaF = resultSet.getDate("fechaFinalizacion");
                String productoId = resultSet.getString("Producto");
            
                promociones.add(new Promocion(promocionId,precioProm,descripcionProm,fechaI,fechaF,productoId));
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
        
        
        return FXCollections.observableList(promociones);
    }
    
    public void eliminarPromocion(int promId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_EliminarPromocion(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,promId);
            statement.execute();
            
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
    }
    
    public Promocion buscarPromocion(){
        Promocion promocion = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_BuscarPromocion(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(tfPromocionId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int promocionId = resultSet.getInt("promocionId");
                Double precioProm = resultSet.getDouble("precioPromocion");
                String descripcionProm = resultSet.getString("descripcionPromocion");
                Date fechaI = resultSet.getDate("fechaInicio");
                Date fechaF = resultSet.getDate("fechaFinalizacion");
                String productoId = resultSet.getString("Producto");
            
                promocion = new Promocion(promocionId, precioProm, descripcionProm, fechaI, fechaF, productoId);

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
        return promocion;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    } 
    
}




