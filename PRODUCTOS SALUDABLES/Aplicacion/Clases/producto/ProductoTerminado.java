package Aplicacion.Clases.producto;

public class ProductoTerminado extends Producto{

  /**
   * @param codigo
   * @param existencia
   * @param precioVenta
   * @param precioCompra
   */
  public ProductoTerminado(int codigo, int existencia, int precioVenta, int precioCompra,int cantidad) {
    super(codigo, existencia, precioVenta, precioCompra,cantidad);
  }

  /**
   * 
   */
  public ProductoTerminado() {
  
  }
  
}
