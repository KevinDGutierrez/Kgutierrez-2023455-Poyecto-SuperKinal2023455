/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kevingutierrez.controller;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.kevingutierrez.report.GenerarReporte;
import org.kevingutierrez.system.Main;

/**
 *
 * @author informatica
 */
public class MenuPrincipalController implements Initializable {
    
    private Main stage;
    
    @FXML
    Button btnCerrarSesion;
    @FXML
    MenuItem btnMenuClientes, btnMenuEmpleados, btnMenuCargos, btnMenuFacturas, btnMenuTicketSoporte,btnMenuDistribuidores,
             btnMenuCategoriaProductos, btnMenuProductos, btnMenuCompras, btnMenuPromociones,
             btnRClientes, btnRProductos;
   
    @FXML
    public void handleButtonAction(ActionEvent event){
        
        if(event.getSource() == btnMenuClientes){
            stage.menuClienteView();
        }else if (event.getSource() == btnMenuEmpleados){
            stage.menuEmpleadoView();
        }else if (event.getSource() == btnMenuCargos){
            stage.menuCargosView();
        }else if (event.getSource() == btnMenuDistribuidores){
            stage.menuDistribuidorView();
        }else if (event.getSource() == btnMenuTicketSoporte){
            stage.menuTicketSoporteView();
        }else if (event.getSource() == btnMenuCategoriaProductos){
            stage.menuCategoriaProductoView();
        }else if(event.getSource() == btnMenuProductos){
            stage.menuProductosView();
        }else if(event.getSource() == btnMenuPromociones){
            stage.menuPromocionesView();
        }else if(event.getSource() == btnMenuFacturas){
            stage.menuFacturasView();
        }else if(event.getSource() == btnMenuCompras){
            stage.menuComprasView();
        }else if(event.getSource() == btnRClientes){
            GenerarReporte.getInstance().generarClientes();
        }else if(event.getSource() == btnRProductos){
            GenerarReporte.getInstance().generarProductos();
        }else if(event.getSource() == btnCerrarSesion){
            stage.LoginView();
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
