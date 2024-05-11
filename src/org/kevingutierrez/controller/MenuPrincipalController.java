/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kevingutierrez.controller;

import java.awt.Button;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.kevingutierrez.system.Main;

/**
 *
 * @author informatica
 */
public class MenuPrincipalController implements Initializable {
    private Main stage;
    
    @FXML
    MenuItem btnMenuClientes, btnMenuEmpleados, btnMenuCargos, btnMenuFacturas, btnMenuTicketSoporte,btnMenuDistribuidores,
             btnMenuCategoriaProductos, btnMenuProductos, btnMenuDetalleFacturas, btnMenuCompras, btnMenuDetalleCompras, btnMenuPromociones;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnMenuClientes){
            stage.menuClienteView();
        }else if (event.getSource() == btnMenuEmpleados){
            stage.menuEmpleadoView();
        }else if (event.getSource() == btnMenuCargos){
            stage.menuCargoView();
        }else if (event.getSource() == btnMenuDistribuidores){
            stage.menuDistribuidorView();
        }else if (event.getSource() == btnMenuTicketSoporte){
            stage.menuTicketSoporteView();
        }
    }
    
    public void setStage(Main stage) {
        this.stage = stage;
    }

    public Main getStage() {
        return stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
