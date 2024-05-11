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
import org.kevingutierrez.dto.CargoDTO;
import org.kevingutierrez.model.Cargo;
import org.kevingutierrez.system.Main;
/**
 *
 * @author informatica
 */
public class FormCargoController implements Initializable {
    
    private Main stage;
    private int op;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;

    @FXML
    TextField tfCargoId, tfNombreCargo, tfDescripcionCargo;
    
    @FXML
    Button btnGuardar, btnCancelar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(op == 1){
                agregarCargo();
                stage.menuCargoView();
            }else if(op == 2){
                editarCargo();
                stage.menuCargoView();
                CargoDTO.getCargoDTO().setCargo(null);
            }
        }else if(event.getSource() == btnCancelar){
            stage.menuCargoView();
            CargoDTO.getCargoDTO().setCargo(null);
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        if(CargoDTO.getCargoDTO().getCargo() != null){
            cargarDatos(CargoDTO.getCargoDTO().getCargo());
            
        }
    }
    
    public void cargarDatos(Cargo cargo){
        tfCargoId.setText(Integer.toString(cargo.getCargoId()));
        tfNombreCargo.setText(cargo.getNombreCargo());
        tfDescripcionCargo.setText(cargo.getDescripcionCargo());
        
    }
    
    public void agregarCargo(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_AgregarCargos(?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreCargo.getText());
            statement.setString(2, tfDescripcionCargo.getText());
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
        op = 1;
    }
    
    public void editarCargo(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_EditarCargos(?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCargoId.getText()));
            statement.setString(2, tfNombreCargo.getText());
            statement.setString(3, tfDescripcionCargo.getText());
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
        op = 2;
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
