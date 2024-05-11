/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kevingutierrez.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.kevingutierrez.controller.FormClienteController;
import org.kevingutierrez.controller.MenuClientesController;
import org.kevingutierrez.controller.MenuPrincipalController;
import org.kevingutierrez.controller.MenuEmpleadosController;
import org.kevingutierrez.controller.MenuDistribuidoresController;
import org.kevingutierrez.controller.MenuCargosController;
import org.kevingutierrez.controller.FormEmpleadoController;
import org.kevingutierrez.controller.FormDistribuidorController;
import org.kevingutierrez.controller.FormCargoController;
import org.kevingutierrez.controller.MenuTicketSoporteController;

/**
 *
 * @author informatica
 */
public class Main extends Application {
    
    private Stage stage;
    private Scene scene;
    private final String URLVIEW = "/org/kevingutierrez/view/";
    
    @Override
    public void start(Stage stage) throws Exception {
        
        this.stage = stage;
        stage.setTitle("Super Kinal App");
        menuPrincipalView();
        stage.show();
    }
    
    public Initializable switchScene(String fxmlName, int width, int height) throws Exception{
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();
        
        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));
        
        scene = new Scene((AnchorPane) loader.load(file), width, height);
        stage.setScene(scene);
        stage.sizeToScene();
             
        resultado = (Initializable)loader.getController();
        
        return resultado;
    }
    
    public void menuPrincipalView(){
        try{
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController)switchScene("MenuPrincipalView.fxml", 950, 675);
            menuPrincipalView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuClienteView(){
         try{
            MenuClientesController menuClienteView = (MenuClientesController)switchScene("MenuClientesView.fxml", 1200, 750);
            menuClienteView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formClienteView(int op){
         try{
            FormClienteController formClienteView = (FormClienteController)switchScene("FormClienteView.fxml", 500, 750);
            formClienteView.setOp(op);
            formClienteView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
     public void menuEmpleadoView(){
         try{
            MenuEmpleadosController menuEmpleadosView = (MenuEmpleadosController)switchScene("MenuEmpleadosView.fxml", 1200, 750);
            menuEmpleadosView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void formEmpleadosView(int op){
         try{
            FormEmpleadoController formEmpleadosView = (FormEmpleadoController)switchScene("FormEmpleadosView.fxml", 500, 750);
            formEmpleadosView.setOp(op);
            formEmpleadosView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void menuCargoView(){
         try{
            MenuCargosController menuCargosView = (MenuCargosController)switchScene("MenuCargosView.fxml", 1200, 750);
            menuCargosView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void formCargosView(int op){
         try{
            FormCargoController formCargosView = (FormCargoController)switchScene("FormCargosView.fxml", 500, 750);
            formCargosView.setOp(op);
            formCargosView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuDistribuidorView(){
         try{
            MenuDistribuidoresController menuDistribuidoresView = (MenuDistribuidoresController)switchScene("MenuDistribuidoresView.fxml", 1200, 750);
            menuDistribuidoresView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void formDistribuidoresView(int op){
         try{
            FormDistribuidorController formDistribuidoresView = (FormDistribuidorController)switchScene("FormDistribuidoresView.fxml", 500, 750);
            formDistribuidoresView.setOp(op);
            formDistribuidoresView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuTicketSoporteView(){
         try{
            MenuTicketSoporteController menuTicketSoportesView = (MenuTicketSoporteController)switchScene("MenuTicketSoporteView.fxml", 1200, 750);
            menuTicketSoportesView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
   
    public void formTicketSoporteView(int op){
         try{
            FormDistribuidorController formDistribuidoresView = (FormDistribuidorController)switchScene("FormDistribuidoresView.fxml", 500, 750);
            formDistribuidoresView.setOp(op);
            formDistribuidoresView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
