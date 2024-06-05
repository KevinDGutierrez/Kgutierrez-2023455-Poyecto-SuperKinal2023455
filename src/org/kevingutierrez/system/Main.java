/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kevingutierrez.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.kevingutierrez.controller.FormAsignarEController;
import org.kevingutierrez.controller.FormClienteController;
import org.kevingutierrez.controller.MenuClientesController;
import org.kevingutierrez.controller.MenuPrincipalController;
import org.kevingutierrez.controller.MenuEmpleadosController;
import org.kevingutierrez.controller.MenuDistribuidoresController;
import org.kevingutierrez.controller.MenuCargosController;
import org.kevingutierrez.controller.FormEmpleadosController;
import org.kevingutierrez.controller.FormDistribuidorController;
import org.kevingutierrez.controller.FormCargoController;
import org.kevingutierrez.controller.FormCategoriaProductoController;
import org.kevingutierrez.controller.FormComprasController;
import org.kevingutierrez.controller.FormDetalleCompraController;
import org.kevingutierrez.controller.FormDetalleFacturaController;
import org.kevingutierrez.controller.FormFacturasController;
import org.kevingutierrez.controller.FormProductosController;
import org.kevingutierrez.controller.FormPromocionesController;
import org.kevingutierrez.controller.LoginController;
import org.kevingutierrez.controller.MenuCategoriaProductosController;
import org.kevingutierrez.controller.MenuTicketSoporteController;
import org.kevingutierrez.controller.MenuCategoriaProductosController;
import org.kevingutierrez.controller.MenuComprasController;
import org.kevingutierrez.controller.MenuFacturasController;
import org.kevingutierrez.controller.MenuProductosController;
import org.kevingutierrez.controller.MenuPromocionesController;
import org.kevingutierrez.controller.RegistrarUsuarioController;
import org.kevingutierrez.model.Producto;
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
        LoginView();
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
            FormEmpleadosController formEmpleadosView = (FormEmpleadosController)switchScene("FormEmpleadosView.fxml", 500, 750);
            formEmpleadosView.setOp(op);
            formEmpleadosView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formAsignarEView(){
        try{
            FormAsignarEController formAsignarE = (FormAsignarEController)switchScene("FormAsignarEView.fxml", 500, 750);
            formAsignarE.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuCargosView(){
         try{
            MenuCargosController menuCargoView = (MenuCargosController)switchScene("MenuCargosView.fxml", 1200, 750);
            menuCargoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
   public void formCargoView(int op){
         try{
            FormCargoController formCargoView = (FormCargoController)switchScene("FormCargosView.fxml", 500, 750);
            formCargoView.setOp(op);
            formCargoView.setStage(this);
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
    
    public void menuCategoriaProductoView(){
         try{
            MenuCategoriaProductosController menuCategoriaProductosView = (MenuCategoriaProductosController)switchScene("MenuCategoriaProductoView.fxml", 1200, 750);
            menuCategoriaProductosView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    
    public void formCategoriaProductoView(int op){
         try{
            FormCategoriaProductoController formCategoriaProductoView = (FormCategoriaProductoController)switchScene("FormCategoriaProductoView.fxml", 500, 750);
            formCategoriaProductoView.setOp(op);
            formCategoriaProductoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuProductosView(){
        try{
            MenuProductosController menuProductos = (MenuProductosController)switchScene("MenuProductosView.fxml", 1200, 750);
            menuProductos.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    
    public void formProductosView(int op){
        try{
            FormProductosController formProductos = (FormProductosController)switchScene("FormProductosView.fxml", 1200, 750);
            formProductos.setOp(op);
            formProductos.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuPromocionesView(){
        try{
            MenuPromocionesController menuPromociones = (MenuPromocionesController)switchScene("MenuPromocionesView.fxml", 1200, 750);
            menuPromociones.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formPromocionesView(int op){
        try{
            FormPromocionesController formPromociones = (FormPromocionesController)switchScene("FormPromocionesView.fxml", 500, 750);
            formPromociones.setOp(op);
            formPromociones.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuFacturasView(){
        try{
            MenuFacturasController menuFacturas = (MenuFacturasController)switchScene("MenuFacturasView.fxml", 1200, 750);
            menuFacturas.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formFacturasView(int op){
        try{
            FormFacturasController formFacturas = (FormFacturasController)switchScene("FormFacturasView.fxml", 500, 750);
            formFacturas.setOp(op);
            formFacturas.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formDetalleFacturaView(int op){
        try{
            FormDetalleFacturaController formDetalleFactura = (FormDetalleFacturaController)switchScene("FormDetalleFacturaView.fxml", 500, 750);
            formDetalleFactura.setOp(op);
            formDetalleFactura.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuComprasView(){
        try{
            MenuComprasController menuCompras = (MenuComprasController)switchScene("MenuComprasView.fxml", 1200, 750);
            menuCompras.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formComprasView(int op){
        try{
            FormComprasController formCompras = (FormComprasController)switchScene("FormComprasView.fxml", 500, 750);
            formCompras.setOp(op);
            formCompras.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formDetalleCompraView(int op){
        try{
            FormDetalleCompraController formDetalleFactura = (FormDetalleCompraController)switchScene("FormDetalleCompraView.fxml", 500, 750);
            formDetalleFactura.setOp(op);
            formDetalleFactura.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void RegistrarUsuarioView(){
        try{
            RegistrarUsuarioController registrarUsuarioView = (RegistrarUsuarioController) switchScene ("RegistrarUsuarioView.fxml", 500, 750);
            registrarUsuarioView.setStage(this);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void LoginView(){
        try{
            LoginController loginView = (LoginController) switchScene("LoginView.fxml", 500, 750);
            loginView.setStage(this);
        }catch(Exception e) {
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
