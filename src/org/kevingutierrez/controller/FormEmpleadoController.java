/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kevingutierrez.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.kevingutierrez.dao.Conexion;
import org.kevingutierrez.dto.EmpleadoDTO;
import org.kevingutierrez.model.Empleado;
import org.kevingutierrez.system.Main;
/**
 *
 * @author informatica
 */
public class FormEmpleadoController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;

    @FXML
    TextField tfEmpleadoId , tfNombreEmpleado, tfApellidoEmpleado, tfSueldo, tfHoraEntrada, tfHoraSalida, tfCargoId, tfEncargadoId;
    
    @FXML
    Button btnGuardar, btnCancelar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(op == 1){
                agregarEmpleado();
                stage.menuEmpleadoView();
            }else if(op == 2){
                editarCliente();
                stage.menuEmpleadoView();
                EmpleadoDTO.getEmpleadoDTO().setEmpleado(null);
            }
        }else if(event.getSource() == btnCancelar){
            stage.menuEmpleadoView();
            EmpleadoDTO.getEmpleadoDTO().setEmpleado(null);
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        if(EmpleadoDTO.getEmpleadoDTO().getEmpleado() != null){
            cargarDatos(EmpleadoDTO.getEmpleadoDTO().getEmpleado());
            
        }
    }
    
    public void cargarDatos(Empleado empleado){
        tfEmpleadoId.setText(Integer.toString(empleado.getEmpleadoId()));
        tfNombreEmpleado.setText(empleado.getNombreEmpleado());
        tfApellidoEmpleado.setText(empleado.getApellidoEmpleado());
        
    }
    
    public void agregarEmpleado(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_AgregarClientes(?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreEmpleado.getText());
            statement.setString(2, tfApellidoEmpleado.getText());
            statement.setString(3, tfSueldo.getText());
            statement.setString(4, tfHoraEntrada.getText());
            statement.setString(5, tfHoraSalida.getText());
            statement.setString(6, tfCargoId.getText());
            statement.setString(7, tfEncargadoId.getText());
            statement.execute();
        }catch(SQLException e){
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
    
    public void editarCliente(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "sp_EditarEmpleados(?, ?, ?, ?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfEmpleadoId.getText()));
            statement.setString(2, tfNombreEmpleado.getText());
            statement.setString(3, tfApellidoEmpleado.getText());
            statement.setString(4, tfSueldo.getText());
            statement.setString(5, tfHoraEntrada.getText());
            statement.setString(6, tfHoraSalida.getText());
            statement.setString(7, tfCargoId.getText());
            statement.setString(8, tfEncargadoId.getText());
            statement.execute();
        }catch(SQLException e){
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
