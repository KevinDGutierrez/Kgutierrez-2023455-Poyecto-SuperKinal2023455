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
import org.kevingutierrez.dto.DistribuidorDTO;
import org.kevingutierrez.model.Distribuidor;
import org.kevingutierrez.system.Main;
/**
 *
 * @author kevin
 */
public class FormDistribuidorController implements Initializable {
    
    private Main stage;
    private int op;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;

    @FXML
    TextField tfDistribuidorId , tfNombreDistribuidor, tfDireccionDistribuidor , tfNitDistribuidor , tfTelefonoDistribuidor , tfWeb;
    
    @FXML
    Button btnGuardar, btnCancelar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(op == 1){
                agregarDistribuidor();
                stage.menuDistribuidorView();
            }else if(op == 2){
                editarDistribuidor();
                stage.menuDistribuidorView();
                DistribuidorDTO.getDistribuidorDTO().setDistribuidor(null);
            }
        }else if(event.getSource() == btnCancelar){
            stage.menuDistribuidorView();
            DistribuidorDTO.getDistribuidorDTO().setDistribuidor(null);
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(DistribuidorDTO.getDistribuidorDTO().getDistribuidor() != null){
            cargarDatos(DistribuidorDTO.getDistribuidorDTO().getDistribuidor());
            
        }
    }
    
    public void cargarDatos(Distribuidor distribuidor){
        tfDistribuidorId.setText(Integer.toString(distribuidor.getDistribuidorId()));
        tfNombreDistribuidor.setText(distribuidor.getNombreDistribuidor());
        tfDireccionDistribuidor.setText(distribuidor.getDireccionDistribuidor());
        tfNitDistribuidor.setText(distribuidor.getNitDistribuidor());
        tfTelefonoDistribuidor.setText(distribuidor.getTelefonoDistribuidor());
        tfWeb.setText(distribuidor.getWeb());
    }
    
    public void agregarDistribuidor(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_AgregarDistribuidores(?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreDistribuidor.getText());
            statement.setString(2, tfDireccionDistribuidor.getText());
            statement.setString(3, tfNitDistribuidor.getText());
            statement.setString(4, tfTelefonoDistribuidor.getText());
            statement.setString(5, tfWeb.getText());
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
    
    public void editarDistribuidor(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EditarDistribuidores(?, ?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfDistribuidorId.getText()));
            statement.setString(2, tfNombreDistribuidor.getText());
            statement.setString(3, tfDireccionDistribuidor.getText());
            statement.setString(4, tfNitDistribuidor.getText());
            statement.setString(5, tfTelefonoDistribuidor.getText());
            statement.setString(6, tfWeb.getText());
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
