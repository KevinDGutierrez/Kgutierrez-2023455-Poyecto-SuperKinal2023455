/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kevingutierrez.report;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.kevingutierrez.dao.Conexion;
import win.zqxu.jrviewer.JRViewerFX;

/**
 *
 * @author informatica
 */
public class GenerarReporte {
    private static GenerarReporte instance;
    
    private static Connection conexion = null;
    
    private GenerarReporte(){
    
    }
    
    public static GenerarReporte getInstance(){
        if(instance == null){
            instance = new GenerarReporte();
        }
        return instance;
    }
    
    public void generarFactura(int facId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("factId", facId);
            
            Stage reportStage = new Stage();
            
            JasperPrint reporte = JasperFillManager.fillReport(GenerarReporte.class.getResourceAsStream("/org/kevingutierrez/report/Factura.jasper"), parametros, conexion);
            
            JRViewerFX reportView = new JRViewerFX(reporte);
            
            Pane root = new Pane();
            root.getChildren().add(reportView);
            
            reportView.setPrefSize(1000, 800);
            
            Scene scene = new Scene(root);
            reportStage.setScene(scene);
            reportStage.setTitle("Factura");
            reportStage.show();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void generarClientes(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            
            Map<String, Object> parametros = new HashMap<>();
            
            Stage reportStage = new Stage();
            
            JasperPrint reporte = JasperFillManager.fillReport(GenerarReporte.class.getResourceAsStream("/org/kevingutierrez/report/Clientes.jasper"), parametros, conexion);
            
            JRViewerFX reportView = new JRViewerFX(reporte);
            
            Pane root = new Pane();
            root.getChildren().add(reportView);
            
            reportView.setPrefSize(1000, 800);
            
            Scene scene = new Scene(root);
            reportStage.setScene(scene);
            reportStage.setTitle("Clientes");
            reportStage.show();
                    
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void generarProductos(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            
            Map<String, Object> parametros = new HashMap<>();
            
            Stage reportStage = new Stage();
            
            JasperPrint reporte = JasperFillManager.fillReport(GenerarReporte.class.getResourceAsStream("/org/kevingutierrez/report/Productos.jasper"), parametros, conexion);
            
            JRViewerFX reportView = new JRViewerFX(reporte);
            
            Pane root = new Pane();
            root.getChildren().add(reportView);
            
            reportView.setPrefSize(1000, 800);
            
            Scene scene = new Scene(root);
            reportStage.setScene(scene);
            reportStage.setTitle("Productos");
            reportStage.show();
                    
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
