/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kevingutierrez.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.kevingutierrez.dao.Conexion;
import org.kevingutierrez.model.Cliente;
import org.kevingutierrez.model.Factura;
import org.kevingutierrez.model.TicketSoporte;
import org.kevingutierrez.system.Main;
import org.kevingutierrez.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author kevin
 */
public class MenuTicketSoporteController implements Initializable {
    Main stage;
    private Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    
    @FXML
    TextField tfTicketSoporteId;
    
    @FXML
    ComboBox cmbEstatus, cmbCliente, cmbFactura;
    
    @FXML
    TextArea taDescripcionTicket;
    
    @FXML
    TableView tblTicketSoportes;
    
    @FXML
    TableColumn colTicketSoporteId, colDescripcionTicket, colEstatus, colCliente, colFacturaId;
    
    @FXML
    Button btnRegresar, btnGuardar, btnVaciarForm;
      
    @FXML
    public void handleButtonAction(ActionEvent event) {
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if (!taDescripcionTicket.getText().equals("") && cmbCliente.getValue() != null) {
                if (tfTicketSoporteId.getText().equals("")) {
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(400);
                    agregarTicket();
                    cargarDatos();
                } else {
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505).get() == ButtonType.OK){
                        SuperKinalAlert.getInstance().mostrarAlertaInformacion(500);
                        editarTicket();
                        cargarDatos();
                    }else{
                        stage.menuTicketSoporteView();
                    }
                }
            } else {
                SuperKinalAlert.getInstance().mostrarAlertaInformacion(33);
            }

        }else if(event.getSource() == btnVaciarForm){
            vaciarForm();
            tblTicketSoportes.getItems().clear();
            cargarDatos();
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarCmbEstatus();
        cmbCliente.setItems(listarCliente());
        cmbFactura.setItems(listarFacturas());
        cargarDatos();
    }
    
    public  ObservableList<TicketSoporte> listarTickets(){
        
        ArrayList<TicketSoporte> tickets = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " Call sp_ListarTicketSoportes()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int ticketSoporteId = resultSet.getInt("ticketSoporteId");
                String descripcionTicket = resultSet.getString("descripcionTicket");
                String estatus = resultSet.getString("estatus");
                String Cliente = resultSet.getString("Cliente");
                int facturaId = resultSet.getInt("facturaId");
                
                tickets.add(new TicketSoporte(ticketSoporteId, descripcionTicket, estatus, Cliente, facturaId));
                
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
        
        return FXCollections.observableList(tickets);
        
    }
    
    public ObservableList<Cliente> listarCliente(){
        ArrayList<Cliente> clientes = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarClientes()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int clienteId = resultSet.getInt("clienteId");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");
                String nit = resultSet.getString("nit");
                
                clientes.add(new Cliente(clienteId, nombre, apellido, telefono, direccion, nit));
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
        return FXCollections.observableList(clientes);
    }
    
    public void cargarDatos(){
        tblTicketSoportes.setItems(listarTickets());
        colTicketSoporteId.setCellValueFactory(new PropertyValueFactory<TicketSoporte, Integer>("ticketSoporteId"));
        colDescripcionTicket.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("descripcionTicket"));
        colEstatus.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("estatus"));
        colCliente.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("Cliente"));
        colFacturaId.setCellValueFactory(new PropertyValueFactory<TicketSoporte, Integer>("facturaId"));
    }
    
    public void cargarCmbEstatus(){
        cmbEstatus.getItems().add("En proceso");
        cmbEstatus.getItems().add("Finalizado");
    }
    
    
    public void vaciarForm(){
        tfTicketSoporteId.clear();
        taDescripcionTicket.clear();
        cmbEstatus.getSelectionModel().clearSelection();
        cmbCliente.getSelectionModel().clearSelection();
        cmbFactura.getSelectionModel().clearSelection();
        
    }
    
    public void agregarTicket(){
        try{
           conexion = Conexion.getInstance().obtenerConexion();
           String sql = " call sp_AgregarTicketSoportes(?, ?, ?)";
           statement = conexion.prepareStatement(sql);
           statement.setString(1,taDescripcionTicket.getText());
           statement.setInt(2, ((Cliente)cmbCliente.getSelectionModel().getSelectedItem()).getClienteId());
           statement.setInt(3, ((Factura)cmbFactura.getSelectionModel().getSelectedItem()).getFacturaId());
           statement.execute();
                   
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
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
    }
    
    @FXML
    public void cargarForm(){
        TicketSoporte ts = (TicketSoporte)tblTicketSoportes.getSelectionModel().getSelectedItem();
        if(ts != null){
            tfTicketSoporteId.setText(Integer.toString(ts.getTicketSoporteId()));
            taDescripcionTicket.setText(ts.getDescripcionTicket());
            cmbEstatus.getSelectionModel().select(0);
            cmbCliente.getSelectionModel().select(obtenerIndexCliente());
            cmbFactura.getSelectionModel().select(0);
        }
    }
    
    public int obtenerIndexCliente(){
        int index = 0;
        String clienteTbl = ((TicketSoporte)tblTicketSoportes.getSelectionModel().getSelectedItem()).getCliente();

        for(int i = 0; i <= cmbCliente.getItems().size(); i++){
           String clienteCmb = cmbCliente.getItems().get(i).toString();
           
           if(clienteTbl.equals(clienteCmb)){
               index = i;
               break;
           }
        }
        
        return index;
    }
    
    public void editarTicket(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " call sp_EditarTicketSoportes(?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(tfTicketSoporteId.getText()));
            statement.setString(2, taDescripcionTicket.getText());
            statement.setString(3, (cmbEstatus.getSelectionModel().getSelectedItem().toString()));
            statement.setInt(4, ((Cliente)cmbCliente.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(5, ((Factura)cmbFactura.getSelectionModel().getSelectedItem()).getFacturaId());
            statement.execute();
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
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
    }
    
    public ObservableList<Factura> listarFacturas(){
        ArrayList<Factura> facturas = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " CALL sp_ListarFacturas()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int facturaId = resultSet.getInt("facturaId");
                Date fecha = resultSet.getDate("fecha");
                Time hora = resultSet.getTime("hora");
                String cliente = resultSet.getString("cliente");
                String empleado = resultSet.getString("empleado");
                Double total = resultSet.getDouble("total");
            
                facturas.add(new Factura(facturaId, fecha, hora, cliente, empleado, total));
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
        
        
        return FXCollections.observableList(facturas);
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
}
