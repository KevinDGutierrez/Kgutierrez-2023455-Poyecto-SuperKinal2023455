/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kevingutierrez.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 *
 * @author informatica
 */
public class SuperKinalAlert {
    private static SuperKinalAlert instance;
    
    private SuperKinalAlert(){
    
    }
    
    public static SuperKinalAlert getInstance(){
        if(instance == null){
            instance = new SuperKinalAlert();
        }
        return instance;
    }
    
    public void mostrarAlertaInformacion(int code){
        if(code == 400){ // Codigo 400 sirve para confirmación de resgistro
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmación Registro");
            alert.setHeaderText("Confirmación de Regitro");
            alert.setContentText("¡Registro realizado con éxito!");
            alert.showAndWait();
        }else if(code == 500){ // Codigo 500 sirve para edición de registro
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Edición Registro");
            alert.setHeaderText("Edición de Regitro");
            alert.setContentText("¡Edición realizado con éxito!");
            alert.showAndWait();
        }else if(code == 700){ // Codigo 700 sirve para eliminación de registro
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Eliminación Registro");
            alert.setHeaderText("Eliminación de Regitro");
            alert.setContentText("¡Eliminación realizado con éxito!");
            alert.showAndWait();
        }else if(code == 600){ // Codigo 600 sirve para alerta de campos pendientes
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Pendientes");
            alert.setHeaderText("Campos Pendientes");
            alert.setContentText("¡Algunos campos necesarios aun estan vacios!");
            alert.showAndWait();
        }else if(code == 602) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Usuario incorrecto");
            alert.setHeaderText("Usuario incorrecto");
            alert.setContentText("Verifique el usuario");
            alert.showAndWait();
        }else if(code == 005) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Contraseña incorrecta");
            alert.setHeaderText("Contraseña incorrecta");
            alert.setContentText("Verifique la contraseña");
            alert.showAndWait();
        }    
    }
    
    public Optional<ButtonType> mostrarAlertaConfirmacion(int code){
        Optional<ButtonType> action = null;
        
        if(code == 404){ // Codigo 404 sire para confirmar la eliminacion de un registro
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminacion Registro");
            alert.setHeaderText("Eliminacion de Registro");
            alert.setContentText("¿Esta seguro de eliminar el registro?");
            action = alert.showAndWait();
        }else if(code == 505){ // Codigo 505 sirve para confirmar la edicion de registro
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edición Registro");
            alert.setHeaderText("Edición de Registro");
            alert.setContentText("¿Esta seguro de editar el registro?");
            action = alert.showAndWait();
        }
        
        return action;
    }
    
    public void alertaSaludo(String usuario) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bienvenido");
        alert.setHeaderText("Bienvenido " + usuario);
        alert.showAndWait();
            
    }
    
    public void mostrarAlertaAdvertencia(String mensaje) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
        
    }
}
