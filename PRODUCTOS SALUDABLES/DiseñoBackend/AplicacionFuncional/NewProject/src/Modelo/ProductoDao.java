/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**

 *
 * @author Valeria
 */
public class ProductoDao {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean RegistrarProductos(Producto p) {
        
        String sql = "INSERT INTO public.\"Producto\" (id,nombre,cantidad,precio) VALUES (?,?,?,?)";
        try {
            con = cn.iniciarconexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, p.getId());
            ps.setString(2, p.getNombre());
            ps.setInt(3, p.getCantidad());
            ps.setInt(4,p.getPrecio());
            ps.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;

        }
    }
    
//     public void ConsultarProveedor(JComboBox proveedor) {
//        String sql = "SELECT nombre FROM public.\"Proveedor\" ";
//        try {
//            con = cn.iniciarconexion();
//            ps = con.prepareStatement(sql);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                proveedor.addItem(rs.getString("nombre"));
//            }
//        } catch (SQLException e) {
//            System.out.println(e.toString());
//        }
//    }
    
    public List listarProductos()
    {
        List <Producto> ListaPro= new ArrayList();
        String sql="SELECT * FROM public.\"Producto\" ";
        String aux="";
        try
        {
            con= cn.iniciarconexion();
            ps=con.prepareStatement(sql);
            rs= ps.executeQuery();
            while (rs.next()) 
            {               
               Producto pro = new Producto();
               pro.setId(rs.getInt("id"));
               pro.setNombre(rs.getString("nombre"));
               pro.setCantidad(rs.getInt("cantidad"));
               pro.setPrecio(rs.getInt("precio"));
               ListaPro.add(pro);
               //aux= rs.getString("nombre");
           }  
            //PRUEBA PARA VERIFICAR LA CONEXION CON LA BASE DE DATOS
            //JOptionPane.showMessageDialog(null,aux);
        }
        
        catch(SQLException e)
        {
            System.out.println(e.toString());
            
        }
        return ListaPro;
    }
    
    
    public boolean eliminarProducto(int id)
    {
        String sql= "DELETE FROM public.\"Producto\" WHERE id=?";
        try{
            ps=con.prepareStatement(sql);
            ps.setInt(1,id);
            ps.execute();
            return true;
        }
        catch(SQLException e){
            System.out.println(e.toString());
            return true;
        }
        finally{
            try
            {
              con.close();  
            }
            catch(SQLException e){
                System.out.println(e.toString());
            }
        }
        
    }
        
        public boolean ModificarProducto(Producto p){
        String sql="UPDATE public.\"Producto\" SET nombre=?,precio=?,cantidad=?, WHERE id=?";
        try{ 
            con= cn.iniciarconexion();
            ps=con.prepareStatement(sql);
            ps.setString(1,p.getNombre());
            ps.setInt(2, p.getPrecio());
            ps.setInt(3,p.getCantidad());
            ps.setInt(4, p.getId());
            ps.execute();
            return true;
            
            
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
            return false;        
        }finally{
            try{
                con.close();               
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "Código :" + ex.getErrorCode()
                        + "\n " + ex.getMessage());
            }            
        }
        
    }
    
    public Producto BuscarProducto(int id) {
        Producto producto = new Producto();
        String sql = "SELECT * FROM public.\"Producto\" WHERE id = ?";
        try {
            con = cn.iniciarconexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {

                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getInt("precio"));
                producto.setCantidad(rs.getInt("cantidad"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return producto;
    }
        
        
    public boolean AumentarInventarioProducto(int id,int cantidadProducida){
        String sql="UPDATE public.\"Producto\" SET cantidad=? WHERE id=?";
        try{
            int cantidad=cantidadProducida+inventarioProducto(id);
            //JOptionPane.showMessageDialog(null,  cantidad);
            con= cn.iniciarconexion();
            ps=con.prepareStatement(sql); 
            ps.setInt(1,cantidad);
            ps.setInt(2,id);
            ps.execute();
            return true;
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally{
            try{
                con.close();
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "Código :" + ex.getErrorCode()
                        + "\n " + ex.getMessage());
            } 
        } 
    }
        
        
        

    public int inventarioProducto(int idProducto) {
        int producto=0;
        String sql = "SELECT * FROM public.\"Producto\" WHERE id = ?";
        try {
            con = cn.iniciarconexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idProducto);
            rs = ps.executeQuery();
            if (rs.next()) {
                producto=(rs.getInt("cantidad"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return producto;
    }
    
    
    public void BuscarInsumoParaProducirProducto(int id ,int cantidad) {
        //Producto producto = new Producto();
        String sql = "SELECT * FROM public.\"InsumosParaproducir1Producto\" WHERE id_producto = ?";
        
        try {
            int harina=0;
            int carne=0;
            int pollo=0;
            int huevos=0;
            int sal=0;
            int levadura=0;
            int aceite=0;
            int papa=0;
            int queso=0;
            boolean invSuficient=true;
            con = cn.iniciarconexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
           

            if (rs.next()) {
            
                harina= rs.getInt("harina")*cantidad;
                carne=(rs.getInt("carne"))*cantidad;
                pollo=(rs.getInt("pollo"))*cantidad;
                huevos=(rs.getInt("huevos"))*cantidad;
                sal=(rs.getInt("sal"))*cantidad;
                levadura=(rs.getInt("levadura"))*cantidad;
                aceite=(rs.getInt("aceite"))*cantidad;
                papa=(rs.getInt("papa"))*cantidad;
                queso=(rs.getInt("queso"))*cantidad;
                
                
                for (int i=1;i<10;i++) {
                  if(invSuficient)
                  {
                      invSuficient=inventarioSuficiente(i,cantidad);
                  }
                  else{
                      break;
                  }
                    
                }
                
                if(invSuficient)
                {
                    ModificarInsumo(1,harina,"harina");
                    ModificarInsumo(2,carne,"carne");
                    ModificarInsumo(3,pollo,"pollo");
                    ModificarInsumo(4,huevos,"huevos");
                    ModificarInsumo(5,sal,"sal");
                    ModificarInsumo(6,levadura,"levadura");
                    ModificarInsumo(7,aceite,"aceite");
                    ModificarInsumo(8,papa,"papa");
                    ModificarInsumo(9,queso,"queso");
                    AumentarInventarioProducto(id,cantidad);
                 }
                else{
                    JOptionPane.showMessageDialog(null, "insumos insuficientes");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    
    
    public boolean inventarioSuficiente(int codigo,int cantidad){
            int existencias= buscarInsumo(codigo)-cantidad;
            return existencias>0;
    }
    
    
    
     public boolean ModificarInsumo(int codigo,int cantidad, String nombre){
        String sql = "UPDATE public.\"InventarioInsumos\" SET cantidad_insumo=? "
                + "WHERE codigo=?";
        //int inventarioPoducto;
       
        try{
            int existencias= buscarInsumo(codigo)-cantidad;
            con= cn.iniciarconexion();
            ps=con.prepareStatement(sql);
            ps.setInt(1,existencias);
            ps.setInt(2,codigo);
            ps.execute();
            //JOptionPane.showMessageDialog(null, "Insumo modificado");
            return true;
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally{
            try{
                con.close();   
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "Código :" + ex.getErrorCode()
                        + "\n " + ex.getMessage());
                return false;
            }
        }
        
        
    }
    
    public int buscarInsumo(int codigo){     
        String sql="SELECT cantidad_insumo FROM  public.\"InventarioInsumos\" WHERE codigo= ? ";
        try{         
            int existencias=0;
            con= cn.iniciarconexion();
            ps=con.prepareStatement(sql);
            ps.setInt(1, codigo);
            rs= ps.executeQuery();
            while (rs.next()){
                existencias= rs.getInt("cantidad_insumo");  
            }       
            return existencias;
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
            return 0;
        
        }finally{
            try{
                con.close();
                
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "Código :" + ex.getErrorCode()
                        + "\n " + ex.getMessage());
            }
            
        }
        
    }
    
    
    
}

