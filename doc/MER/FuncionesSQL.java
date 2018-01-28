package pruebas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;

import javafx.beans.property.ObjectProperty;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * 
 * @author Carlos Marrero Ramos - xibhu
 *
 */
public class FuncionesSQL {

	/**
	 * Variable global para la conexión con la base de datos.
	 */
	private static Connection con;
	
	/**
	 * 
	 * @return Conexión con la base de datos para poder realizar las consultas.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private static Connection conectarServidor() throws ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		return con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbhearthstoneinfo?serverTimezone=UTC", "anonimo", "anonimo");

	}
	/**
	 * Cerrar la conexión con el servidor una vez realizada la consulta. No se deja abierta la conexión.
	 */
	private static void cerrarServidor() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		con = null;
	}
	
	/**
	 * Consulta si existe un usuario en la base de datos.
	 * 
	 * @param nombreUsuario Nombre del usuario.
	 * @return True, si existe. False, si no existe.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	//Empieza funciones de Usuario
	public static boolean consultaExisteUsuario(String nombreUsuario) throws ClassNotFoundException, SQLException {
		PreparedStatement ps = conectarServidor().prepareStatement("select * from T_USUARIOS where Nombre = ?");
		ps.setString(1, nombreUsuario);
		
		ResultSet rs = ps.executeQuery();
		boolean b = rs.last() ? true : false;
		
		cerrarServidor();
		
		return b;
	}
	
	/**
	 * 
	 * Para la creación de un usuario, inserta un nombre y una contraseña.
	 * 
	 * @param nombreUsuario
	 * @param password
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	public static boolean insertarUsuario(String nombreUsuario, String password) throws ClassNotFoundException, SQLException {
		
		PreparedStatement ps = conectarServidor().prepareStatement("insert into T_USUARIOS (Nombre, Password) values (?, ?);");
		ps.setString(1, nombreUsuario);
		ps.setString(2, password);
		
		boolean b = ps.execute();
		
		cerrarServidor();
		
		return b;
	}
	
	/**
	 * Borra un usuario de la base de datos.
	 * 
	 * @param nombreUsuario
	 * @param password
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	
	public static boolean borrarUsuario(String nombreUsuario, String password) throws SQLException, ClassNotFoundException {
		
		PreparedStatement ps = conectarServidor().prepareStatement("delete from T_USUARIOS where Nombre = ?;");
		ps.setString(1, nombreUsuario);
		
		boolean b = ps.execute();
		
		cerrarServidor();
		
		return b;
		
		
	}
	
	/**
	 * Consulta si la combinación de usuario y contraseña están en la base de datos.
	 * 
	 * @param nombreUsuario
	 * @param password
	 * @return True, si la combinación es correcta, False en caso contrario.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	
	public static boolean consultaInicioSesion(String nombreUsuario, String password) throws SQLException, ClassNotFoundException {
		
		PreparedStatement ps = conectarServidor().prepareStatement("select * from T_USUARIOS where Nombre = ? and Password = ?");
		ps.setString(1, nombreUsuario);
		ps.setString(2, password);
		
		ResultSet rs = ps.executeQuery();
		boolean b = rs.last() ? true : false;
		
		cerrarServidor();
		
		return b;
		
	}
	
	/**
	 * Devuelve todos los nombres de los usuarios.
	 * @return Lista con los nombres de usuarios.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	public static List<String> consultaUsuarios() throws ClassNotFoundException, SQLException{
		List<String> lista = new ArrayList<>();
		
		PreparedStatement ps = conectarServidor().prepareStatement("select Nombre from T_USUARIOS");
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			lista.add(rs.getString(1));
		}
		
		return lista;
		
	}
	//Termina funciones de usuario
	
	//Empieza funciones de Favorito
	
	/**
	 * Inserta el id de una carta, junto con el nombre de usuario, para así guardar los favoritos.
	 * @param nombreUsuario
	 * @param idCard
	 * @return 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static boolean insertarFavorito(String nombreUsuario, String idCard) throws SQLException, ClassNotFoundException {
		
		PreparedStatement ps = conectarServidor().prepareStatement("insert into T_FAVORITOS (NombreUsuario, IdCarta) values (?, ?);");
		ps.setString(1, nombreUsuario);
		ps.setString(2, idCard);
		
		boolean b = ps.execute();
		
		cerrarServidor();
		
		return b;
		
		
	}
	
	/**
	 * Consulta si un id está guardado con un nombre de usuario.
	 * 
	 * @param nombreUsuario
	 * @param idCard
	 * @return True si se cumple la combinación, False en caso contrario.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	public static boolean consultarExisteFavorito(String nombreUsuario, String idCard) throws ClassNotFoundException, SQLException {
		PreparedStatement ps = conectarServidor().prepareStatement("select * from T_USUARIOS where Nombre = ? and IdCard = ?");
		ps.setString(1, nombreUsuario);
		ps.setString(2, idCard);
		
		ResultSet rs = ps.executeQuery();
		boolean b = rs.last() ? true : false;
		
		cerrarServidor();
		
		return b;
	}
	
	/**
	 * Elimina un registro con la combinación de nombre de usuario e id de la carta.
	 * @param nombreUsuario
	 * @param idCard
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	public static boolean eliminarFavorito(String nombreUsuario, String idCard) throws ClassNotFoundException, SQLException {
		
		PreparedStatement ps = conectarServidor().prepareStatement("delete from T_FAVORITOS where Nombre = ? and IdCarta = ?;");
		ps.setString(1, nombreUsuario);
		ps.setString(2, idCard);
		
		boolean b = ps.execute();
		
		cerrarServidor();
		
		return b;
		
	}
	
	/**
	 * Devuelve todos los favoritos de un usuario concreto
	 * @param nombreUsuario
	 * @return Lista de String con los id de carta
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	public static List<String> consultaFavoritos(String nombreUsuario) throws ClassNotFoundException, SQLException{
		List<String> lista = new ArrayList<>();
		
		PreparedStatement ps = conectarServidor().prepareStatement("select IdCarta from T_FAVORITOS where NombreUsuario = ?");
		ps.setString(2, nombreUsuario);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			lista.add(rs.getString(1));
		}
		
		return lista;
		
	}
	
	//Termina funciones de Favorito
	
	//Empieza funciones de Mazo
	
	/**
	 * Inserta un nuevo mazo a un usuario.
	 * @param nombreUsuario
	 * @param nombreMazo
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static boolean insertarMazo(String nombreUsuario, String nombreMazo) throws ClassNotFoundException, SQLException {
		
		PreparedStatement ps = conectarServidor().prepareStatement("insert into T_Mazos (Nombre, NombreUsuario) values (?, ?);");
		ps.setString(1, nombreMazo);
		ps.setString(2, nombreUsuario);
		
		boolean b = ps.execute();
		
		cerrarServidor();
		
		return b;
		
	}
	
	/**
	 * Elimina el mazo de un usuario concreto
	 * @param nombreUsuario
	 * @param nombreMazo
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	public static boolean eliminarMazo(String nombreUsuario, String nombreMazo) throws ClassNotFoundException, SQLException {
		
		PreparedStatement ps = conectarServidor().prepareStatement("delete from T_MAZOS where Nombre = ? and NombreUsuario = ?;");
		ps.setString(1, nombreMazo);
		ps.setString(2, nombreUsuario);
		
		boolean b = ps.execute();
		
		cerrarServidor();
		
		return b;
		
	}
	//TODO PENDIENTE hacer con clase ArrayBidi o con Mazo
	public static List<String> consultaMazos1(String nombreUsuario) throws ClassNotFoundException, SQLException{
		List<String> lista = new ArrayList<>();
		
		PreparedStatement ps = conectarServidor().prepareStatement("select * from T_MAZOS where NombreUsuario = ?");
		ps.setString(1, nombreUsuario);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			lista.add(rs.getString(2));
		}
		
		return lista;
		
	}
	
	//Termina funciones de Mazo
	
	//Empieza funciones de MazoCartas
	/**
	 * Inserta una carta, en un mazo determinado,
	 * @param idMAzo
	 * @param idCarta
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static boolean insertarMazoCarta(int idMAzo, String idCarta) throws ClassNotFoundException, SQLException {
		
		PreparedStatement ps = conectarServidor().prepareStatement("insert into T_Mazo_Cartas (IdMazo, IdCarta) values (?, ?);");
		ps.setInt(1, idMAzo);
		ps.setString(2, idCarta);
		
		boolean b = ps.execute();
		
		cerrarServidor();
		
		return b;
		
	}
	
	/**
	 * Elimina una carta de un mazo determinado
	 * @param idMAzo
	 * @param idCarta
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	// TODO REVISAR PARA NO BORRAR CARTAS IGUALES
	public static boolean eliminarMazoCarta(int idMAzo, String idCarta) throws ClassNotFoundException, SQLException {
		PreparedStatement ps = conectarServidor().prepareStatement("delete from T_Mazo_Cartas where IdMazo = ? and IdCarta = ?;");
		ps.setInt(1, idMAzo);
		ps.setString(2, idCarta);
		
		boolean b = ps.execute();
		
		cerrarServidor();
		
		return b;
	}
	
	/**
	 * Devuelve todas las cartas de un mazo determinado
	 * @param IdMazo
	 * @return Lista de String con los id de las cartas
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	public static List<String> consultaMazoCarta(int IdMazo) throws ClassNotFoundException, SQLException{
		List<String> lista = new ArrayList<>();
		
		PreparedStatement ps = conectarServidor().prepareStatement("select IdCarta from T_MAZO_CARTAS where IdMazo = ?");
		ps.setInt(1, IdMazo);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			lista.add(rs.getString(1));
		}
		
		return lista;
		
	}
	
	//Termina funciones de CartaMazo

}
