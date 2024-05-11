/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kevingutierrez.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.kevingutierrez.dao.Conexion;
import org.kevingutierrez.dto.EmpleadoDTO;
import org.kevingutierrez.model.Empleado;
import org.kevingutierrez.system.Main;
/**
 *
 * @author informatica
 */
public class MenuEmpleadosController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;

    @FXML
    TableView tblEmpleados;
    
    @FXML
    TableColumn colEmpleadoId, colNombreEmpleado, colApellidoEmpleado, colSueldo, colHoraEntrada, colHoraSalida, colCargoId, colEncargadoId;
    
    @FXML
    Button btnBack, btnAgregar, btnEditar, btnEliminar, btnBuscar;
    
    @FXML
    TextField tfEmpleadoId;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnBack){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            stage.formEmpleadosView(1);
        }else if(event.getSource() == btnEditar){
            EmpleadoDTO.getEmpleadoDTO().setEmpleado((Empleado)tblEmpleados.getSelectionModel().getSelectedItem());
            stage.formEmpleadosView(2);
        }else if(event.getSource() == btnEliminar){
            eliminarEmpleado(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getEmpleadoId());
            cargarLista();
        }else if(event.getSource() == btnBuscar){
            tblEmpleados.getItems().clear();
            if(tfEmpleadoId.getText().equals("")){
                cargarLista();
            }else{
            op = 3;
            cargarLista();
            }
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarLista();  
    }
    
    public void cargarLista(){
        if(op == 3){
            tblEmpleados.getItems().add(buscarEmpleado());
            op = 0;
   
        }else{
            tblEmpleados.setItems(listarEmpleado());
        }
        
        colEmpleadoId.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("empleadoId"));
        colNombreEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombreEmpleado"));
        colApellidoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidoEmpleado"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleado, String>("sueldo"));
        colHoraEntrada.setCellValueFactory(new PropertyValueFactory<Empleado, String>("horaEntrada"));
        colHoraSalida.setCellValueFactory(new PropertyValueFactory<Empleado, String>("horaSalida"));
        colCargoId.setCellValueFactory(new PropertyValueFactory<Empleado, String>("cargoId"));
        colEncargadoId.setCellValueFactory(new PropertyValueFactory<Empleado, String>("encargadoId"));
    }
    
    public ObservableList<Empleado> listarEmpleado(){
        ArrayList<Empleado> empleados = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarEmpleados()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int empleadoId = resultSet.getInt("empleadoId");
                String nombreEmpleado = resultSet.getString("nombreEmpleado");
                String apellidoEmpleado = resultSet.getString("apellidoEmpleado");
                Double sueldo = resultSet.getDouble("sueldo");
                Time horaEntrada = resultSet.getTime("horaEntrada");
                Time horaSalida = resultSet.getTime("horaSalida");
                int cargoId = resultSet.getInt("cargoId");
                int encargadoId = resultSet.getInt("encargadoId");
                
                    empleados.add(new Empleado(empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId, encargadoId));
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
        return FXCollections.observableList(empleados);
    }
    
    public void eliminarEmpleado(int empId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_EliminarEmpleados(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, empId);
            statement.execute();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }else if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public Empleado buscarEmpleado(){
        Empleado empleado = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_BuscarEmpleados(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(tfEmpleadoId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                 int empleadoId = resultSet.getInt("empleadoId");
                String nombreEmpleado = resultSet.getString("nombreEmpleado");
                String apellidoEmpleado = resultSet.getString("apellidoEmpleado");
                Double sueldo = resultSet.getDouble("sueldo");
                Time horaEntrada = resultSet.getTime("horaEntrada");
                Time horaSalida = resultSet.getTime("horaSalida");
                int cargoId = resultSet.getInt("cargoId");
                int encargadoId = resultSet.getInt("encargadoId");
                
                empleado = new Empleado(empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId, encargadoId);
               
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
        
        
        return empleado;
    }
       
    public void setStage(Main stage) {
        this.stage = stage;
    }

    public Main getStage() {
        return stage;
    }
}
