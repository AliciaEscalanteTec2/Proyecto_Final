/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import com.toedter.calendar.JDateChooser;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class CAnimales {

    int idFamilia;
    int idNamenaza;
    int idGenero;
    
    //UN CONSTRUCTOR SE ENCARGAR DE INICIALIZAR UN OBJETO, SE TRATA DE CREARUN NUEVO OBJETO
    public void establecerIdFamilia(int idFamilia) {
        this.idFamilia = idFamilia;
    }
    
    public void mostrarFamiliaCombo(JComboBox comboFamilia){
        Clases.CConexion objetoConexion = new Clases.CConexion();
        
        String sql = "SELECT * FROM familia";
        Statement st;
        //EL STATEMENT ES EL QUE ACCEDE A LOS DATOS DE LA BASE DE DATOS, PERO SE UTILIZA CUANDO
        //LOS DATOS SON FIJOS (YA DEFINIDOS)
        
        try {
            st = objetoConexion.estableceConexion().createStatement();
            
            ResultSet rs = st.executeQuery(sql); //DA LA POSIBILIDAD DE ACCEDER A SQL QUE ES LA TABLA DE FAMILIA
            //RESULTSET ES UN OBJETO QUE PERTENECE AL PAQUETE DE MYSQL 
            comboFamilia.removeAllItems();
           
            //Corra todos los registros de la tabla
            while(rs.next()){ //EL rs son los datos que tenemos de la tabla y el NEXT devuelve true o false.
                //EL NEXT VA LEYENDO TODOS LOS REGISTROS DE LA TABLA Y CUANDO YA NO HAYA NINGUNA LINEA (NINGUN REGISTRO) SE PARA EL WHILE
                String nombreFamilia = rs.getString("familia"); //EL GETSTRING ES PARA TRANFORMAR DE TIPO TXT A STRING
                this.establecerIdFamilia(rs.getInt("id"));
                
                comboFamilia.addItem(nombreFamilia); //LOS DATOS DEL COMBO FAMILIA LOS ALMACENA Y PRESENTA LOS DATOS EN DONDE DICE NOMBRE 
                comboFamilia.putClientProperty(nombreFamilia, idFamilia);//ASOCIAR EL NOMBRE FAMILIA CON EL ID
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar la familia"+e.toString());
        }
        
    }
    
    
    public void establecerIdNamenaza(int idNamenaza) {
        this.idNamenaza = idNamenaza;
    }
    
    public void mostrarNamenazaCombo(JComboBox comboNamenaza){
        Clases.CConexion objetoConexion = new Clases.CConexion();
        
        String sql = "SELECT * FROM namenaza";
        Statement st;
        
        try {
            st = objetoConexion.estableceConexion().createStatement();
            
            ResultSet rs = st.executeQuery(sql);
            comboNamenaza.removeAllItems();
           
            //Corra todos los registros de la tabla
            while(rs.next()){
                String nombreNamenaza = rs.getString("namenaza");
                this.establecerIdNamenaza(rs.getInt("id"));
                
                comboNamenaza.addItem(nombreNamenaza);
                comboNamenaza.putClientProperty(nombreNamenaza, idNamenaza);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar el Nivel de amenaza"+e.toString());
        }
        
    }
    
    
    public void establecerIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }
    public void mostrarGeneroCombo(JComboBox comboGenero){
        Clases.CConexion objetoConexion = new Clases.CConexion();
        
        String sql = "SELECT * FROM genero";
        Statement st;
        
        try {
            st = objetoConexion.estableceConexion().createStatement();
            
            ResultSet rs = st.executeQuery(sql);
            comboGenero.removeAllItems();
           
            //Corra todos los registros de la tabla
            while(rs.next()){
                String nombreGenero = rs.getString("genero");
                this.establecerIdGenero(rs.getInt("id"));
                
                comboGenero.addItem(nombreGenero);
                comboGenero.putClientProperty(nombreGenero, idGenero);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar el genero"+e.toString());
        }

    }
    
    public void agregarAnimal(JTextField especie, JTextField nombre, 
            JTextField edad, JTextField peso, JTextField altura, 
            JComboBox combofamilia, JComboBox combonamenaza, JComboBox combogenero,
            JTextField habitatant, JDateChooser fingreso, File foto){
        
        CConexion objetoConexion = new CConexion();
        
        String consulta = "INSERT INTO animales (especie, nombre, edad, peso, altura, fkfamilia, fknamenaza, fkgenero, habitatant, fingreso, foto ) VALUES (?, ?, ?, ?,?,?,?,?,?,?,?)";
        
        try {
            FileInputStream fis = new FileInputStream(foto);
            /*
            SE USA PARA LEER LOS DATOS DE UN ARCHIVO, ES UNA CLASE DEL PAQUETE DE JAVA DONDE SE VAN A LEER
            LOS ARCHIVOS DE FOTO
            */
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            int idFamilia = (int) combofamilia.getClientProperty(combofamilia.getSelectedItem());
            int idNamenaza = (int) combonamenaza.getClientProperty(combonamenaza.getSelectedItem());
            int idGenero = (int) combogenero.getClientProperty(combogenero.getSelectedItem());
            Date fechaSeleccionada = fingreso.getDate();
            java.sql.Date fechaSQL = new java.sql.Date(fechaSeleccionada.getTime());
            
            cs.setString(1, especie.getText());
            cs.setString(2, nombre.getText());
            cs.setInt(3, Integer.parseInt(edad.getText()));
            cs.setInt(4, Integer.parseInt(peso.getText()));
            cs.setInt(5, Integer.parseInt(altura.getText()));
            cs.setInt(6, idFamilia);
            cs.setInt(7, idNamenaza);
            cs.setInt(8, idGenero);
            cs.setString(9,habitatant.getText());
            cs.setDate(10, fechaSQL);
            cs.setBinaryStream(11, fis, (int) foto.length()); //Foto
            
            cs.execute(); //Termina
            
            JOptionPane.showMessageDialog(null, "Se guardo el animal correctamente ");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se guardo el animal correctamente"+e.toString());
        }
        
    }
    
    public void mostrarAnimales (JTable tablaTotalAnimales){
        Clases.CConexion objetoConexion = new Clases.CConexion();
        
        DefaultTableModel modelo = new DefaultTableModel();
        //MODELO DE LA TABLA POR DEFECTO. QUE LA TABLA ESTE DESDE CERO
        String sql = "";
        
        modelo.addColumn("id");
        modelo.addColumn("Especie");
        modelo.addColumn("Nombre");
        modelo.addColumn("Edad");
        modelo.addColumn("Peso");
        modelo.addColumn("Altura");
        modelo.addColumn("Familia");
        modelo.addColumn("N. Amenaza");
        modelo.addColumn("Genero");
        modelo.addColumn("Habitat anterior");
        modelo.addColumn("F. Ingreso");
        modelo.addColumn("Foto");
        
        tablaTotalAnimales.setModel(modelo);
        //SELECT multitabla
        sql = "select animales.id, animales.especie, animales.nombre, animales.edad, animales.peso, animales.altura, familia.familia, namenaza.namenaza, genero.genero, animales.habitatant, animales.fingreso, animales.foto \n" +
"from animales INNER JOIN familia ON animales.fkfamilia = familia.id INNER JOIN namenaza ON animales.fknamenaza = namenaza.id \n" +
"INNER JOIN genero ON animales.fkgenero = genero.id;";
        
        try {
            Statement st = objetoConexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                String id = rs.getString("id");
                String especie = rs.getString("especie");
                String nombre = rs.getString("nombre");
                String edad = rs.getString("edad");
                String peso = rs.getString("peso");
                String altura = rs.getString("altura");
                String familia = rs.getString("familia");
                String namenaza = rs.getString("namenaza");
                String genero = rs.getString("genero");
                String habitatant = rs.getString("habitatant");
                
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                java.sql.Date fechaSQL = rs.getDate("fingreso");
                String nuevaFecha = sdf.format(fechaSQL);

                byte [] imageBytes = rs.getBytes("foto"); //MATRIZ QUE ES UNA COLEECION ORDENADA DE DATOS PARA UNA MEJOR
                //MANIPULACIÓN DE LA INFORMACIÓN
                Image foto = null; //PARA QUE SE CENTRE 
                
                if (imageBytes !=null) {
                    //SOLO MUESTRA LA DESCRIPCION DE LA IMAGEN 
                    try {
                        ImageIcon imageIcon = new ImageIcon(imageBytes);
                        foto = imageIcon.getImage();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error en: " +e.toString());
                    }
                    
                    modelo.addRow(new Object[]{id, especie, nombre,edad, peso, altura, familia, namenaza, genero, habitatant, nuevaFecha, foto});
                    //Añade una fila al final del modelo
                }
                
               tablaTotalAnimales.setModel(modelo);
 
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar animales, error en: "+ e.toString());
        }
 
    }
    
    public void seleccionar (JTable totalAnimales, JTextField id, JTextField especie, JTextField nombre, 
            JTextField edad, JTextField peso, JTextField altura, JComboBox familia, JComboBox namenaza, 
            JComboBox genero, JTextField habitatant, JDateChooser fingreso, JLabel foto){
        
        int fila = totalAnimales.getSelectedRow();
        
        if (fila >= 0) {
            id.setText(totalAnimales.getValueAt(fila, 0).toString());
            especie.setText(totalAnimales.getValueAt(fila, 1).toString());
            nombre.setText(totalAnimales.getValueAt(fila, 2).toString());
            edad.setText(totalAnimales.getValueAt(fila, 3).toString());
            peso.setText(totalAnimales.getValueAt(fila, 4).toString());
            altura.setText(totalAnimales.getValueAt(fila, 5).toString());
            familia.setSelectedItem(totalAnimales.getValueAt(fila, 6).toString());
            namenaza.setSelectedItem(totalAnimales.getValueAt(fila, 7).toString());
            genero.setSelectedItem(totalAnimales.getValueAt(fila, 8).toString());
            habitatant.setText(totalAnimales.getValueAt(fila, 9).toString());
            String fechaString = totalAnimales.getValueAt(fila, 10).toString();
            Image imagen = (Image) totalAnimales.getValueAt(fila, 11);
            ImageIcon originalIcon = new ImageIcon(imagen);
            int lblanchura = foto.getWidth();
            int lblaltura = foto.getHeight();
            
            Image ImagenEscalada = originalIcon.getImage().getScaledInstance(lblanchura, lblaltura, Image.SCALE_SMOOTH);
            
            foto.setIcon(new ImageIcon(ImagenEscalada));
            
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date fechaDate = sdf.parse(fechaString);
                
                fingreso.setDate(fechaDate);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al seleccionar: "+e.toString());
            }
            
        }
    }
    
    public void modificarAnimales(JTextField id, JTextField especie, JTextField nombre, 
            JTextField edad, JTextField peso, JTextField altura, 
            JComboBox combofamilia, JComboBox combonamenaza, JComboBox combogenero,
            JTextField habitatant, JDateChooser fingreso, File foto){
        
        CConexion objetoConexion = new CConexion();
        
        String consulta = "UPDATE animales SET animales.especie = ?, animales.nombre = ? , animales.edad = ?, animales.peso = ?, animales.altura = ?, animales.fkfamilia = ?, animales.fknamenaza = ?, animales.fkgenero = ?, animales.habitatant = ?, animales.fingreso = ?, animales.foto=? \n" +
"WHERE animales.id = ?";
        
        try {
            FileInputStream fis = new FileInputStream(foto);
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            
            int idFamilia = (int) combofamilia.getClientProperty(combofamilia.getSelectedItem());
            int idNamenaza = (int) combonamenaza.getClientProperty(combonamenaza.getSelectedItem());
            int idGenero = (int) combogenero.getClientProperty(combogenero.getSelectedItem());
            Date fechaSeleccionada = fingreso.getDate();
            java.sql.Date fechaSQL = new java.sql.Date(fechaSeleccionada.getTime());
            
            cs.setString(1, especie.getText());
            cs.setString(2, nombre.getText());
            cs.setInt(3, Integer.parseInt(edad.getText()));
            cs.setInt(4, Integer.parseInt(peso.getText()));
            cs.setInt(5, Integer.parseInt(altura.getText()));
            cs.setInt(6, idFamilia);
            cs.setInt(7, idNamenaza);
            cs.setInt(8, idGenero);
            cs.setString(9,habitatant.getText());
            cs.setDate(10, fechaSQL);
            cs.setBinaryStream(11, fis, (int) foto.length()); //Foto
            cs.setInt(12, Integer.parseInt(id.getText()));
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null,"Se modifico correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No se modifico correctamente, error: "+e.toString());
        }

    }
    
    public void eliminarAnimal(JTextField id){
        CConexion objetoConexion = new CConexion();
        
        String consulta = "DELETE FROM animales WHERE animales.id=?; ";
        
        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, Integer.parseInt(id.getText()));
            
            cs.execute();
            JOptionPane.showMessageDialog(null,"Se eliminó correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No se eliminó correctamente, error: "+e.toString());
        }

    }
    
    public void limpiarCampos(JTextField id, JTextField especie, JTextField nombre, 
            JTextField edad, JTextField peso, JTextField altura, 
            JTextField habitatant, JDateChooser fingreso, JTextField rutaimagen, JLabel imagenContenido){
        
        Calendar calendario = Calendar.getInstance();

        id.setText("");
        especie.setText(""); 
        nombre.setText(""); 
        edad.setText(""); 
        peso.setText(""); 
        altura.setText(""); 
        habitatant.setText(""); 
        fingreso.setDate(calendario.getTime());
        rutaimagen.setText("");
        imagenContenido.setIcon(null);
 
    }
    
}
