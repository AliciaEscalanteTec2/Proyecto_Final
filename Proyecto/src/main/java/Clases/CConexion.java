/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import com.toedter.calendar.JDateChooser;
import java.io.File;
import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Usuario
 */
public class CConexion {
    //UNA CLASE ES UNA PLANTILLA PARA CREAR OBJETOS. SIRVEN PARA REPRESENTAR OBJETVOS EN MENSAJES Y TIENEN
    //DEFINIDOS UNA SERIE DE MÉTODOS Y VARIABLES
    
    //UN OBJETO ES DONDE SE PUEDEN ALMACENAR DATOS Y DIFERENTES TAREAS 

    Connection conectar = null;
    //EL NULL SE UTILIZA CUANDO SE DESCONOCE EL VALOR DE LA VARIABLE
    //EN LAS FOTOS SE USA PARA CENTRAR LAS FOTO
    
    String usuario = "root";
    String contrasenia = "root";
    String driver="com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/bdzoo";
    String bd = "bdzoo";
    String ip = "localhost";
    String puerto = "3306";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+bd;
    
    //Método de conección
    public Connection estableceConexion(){
        try {
           //Class.forName(driver);
           Class.forName("com.mysql.cj.jdbc.Driver");//RUTA DE LA CONECCIÓN DE LAS DEPENDENCAS INSTALADAS 
           conectar = DriverManager.getConnection(cadena,usuario,contrasenia);
           
        }catch(Exception e){
            
        }
        return conectar;
    }
    
    
    
}

