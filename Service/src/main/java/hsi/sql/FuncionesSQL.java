package hsi.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 
 * @author Carlos Marrero Ramos - xibhu
 *
 */
public class FuncionesSQL {

	/**
	 * Variable global para guardar la conexión con la base de datos.
	 */
	private static Connection con;

	/**
	 * 
	 * Hace la conexión con la base de datos predefinida local "localhost" por el
	 * puerto de serie 3306, con el usuario "anonimo" de la base de datos con los
	 * permisos justos.
	 * 
	 * @return Conexión con la base de datos para poder realizar las consultas.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private static Connection conectarServidor() throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.cj.jdbc.Driver");
		return con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbhearthstoneinfo?serverTimezone=UTC",
				"anonimo", "anonimo");

	}

	/**
	 * Cerrar la conexión con el servidor una vez realizada la consulta. No se deja
	 * abierta la conexión.
	 * 
	 * @throws SQLException
	 */
	private static void cerrarServidor() throws SQLException {

		con.close();
		con = null;
	}

	/**
	 * Encriptación para la contraseña a la hora de insertar en la base de datos.
	 * 
	 * @param nombreUsuario
	 *            Nombre del usuario que se quiere insertar.
	 * @param password
	 *            Contraseña del usuario que se quiere insertar.
	 * @return String de longitud 40.
	 */

	private static String encriptarSha1(String nombreUsuario, String password) {
		return DigestUtils.sha1Hex(nombreUsuario + ":" + password);
	}

	/**
	 * Consulta si existe un usuario en la base de datos. Utilizado para saber de
	 * forma previa, si se puede crear un usuario nuevo con ese nombre.
	 * 
	 * @param nombreUsuario
	 *            Nombre del usuario que se quiere consultar.
	 * @return True, si existe. False, si no existe.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	// Empieza funciones de Usuario
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
	 * Para la creación de un usuario, inserta un nombre y una contraseña, dentro de
	 * la función ya se encripta en sha1.
	 * 
	 * @param nombreUsuario
	 *            El nombre de usuario que se quiere insertar, no influye mayúsculas
	 *            y minúsculas ya que en MySql no se hace esa distinción.
	 * @param password
	 *            La constraseña, que dentro de esta misma función es encriptada en
	 *            sha1 para su inserción en la base de datos.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public static void insertarUsuario(String nombreUsuario, String password)
			throws ClassNotFoundException, SQLException {

		PreparedStatement ps = conectarServidor()
				.prepareStatement("insert into T_USUARIOS (Nombre, Password) values (?, ?);");
		ps.setString(1, nombreUsuario);
		ps.setString(2, encriptarSha1(nombreUsuario, password));

		ps.execute();

		cerrarServidor();

	}

	/**
	 * Borra un usuario de la base de datos utilizando su usuario y contraseña.
	 * 
	 * @param nombreUsuario
	 *            Nombre del usuario que se desea borrar.
	 * @param password
	 *            Contraseña del usuario que se desea borrar, dentro de la función
	 *            se converte en sha1.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	public static void borrarUsuario(String nombreUsuario, String password)
			throws SQLException, ClassNotFoundException {

		PreparedStatement ps = conectarServidor()
				.prepareStatement("delete from T_USUARIOS where Nombre = ? and Password = ?;");
		ps.setString(1, nombreUsuario);
		ps.setString(2, encriptarSha1(nombreUsuario, password));

		ps.execute();

		cerrarServidor();

	}

	/**
	 * Consulta si la combinación de usuario y contraseña están en la base de datos.
	 * 
	 * @param nombreUsuario
	 *            Nombre del usuario que se quiere autenticar.
	 * @param password
	 *            Contraseña del usuario que se quiere autenticar, dentro de la
	 *            función se convierte en sha1;
	 * @return True, si la combinación es correcta, False en caso contrario.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	public static boolean consultaInicioSesion(String nombreUsuario, String password)
			throws SQLException, ClassNotFoundException {

		PreparedStatement ps = conectarServidor()
				.prepareStatement("select * from T_USUARIOS where Nombre = ? and Password = ?");
		ps.setString(1, nombreUsuario);
		ps.setString(2, encriptarSha1(nombreUsuario, password));

		ResultSet rs = ps.executeQuery();
		boolean b = rs.last() ? true : false;

		cerrarServidor();

		return b;

	}

	/**
	 * Devuelve todos los nombres de los usuarios. No se utiliza en la interfaz en
	 * principio.
	 * 
	 * @return Lista con los nombres de usuarios.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public static List<String> consultaUsuarios() throws ClassNotFoundException, SQLException {
		List<String> lista = new ArrayList<>();

		PreparedStatement ps = conectarServidor().prepareStatement("select Nombre from T_USUARIOS");
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			lista.add(rs.getString(1));
		}

		return lista;

	}
	
	/**
	 * Consulta el número total de usuarios que hay registrados en la base de datos.
	 * @return int con la cantidad de usuarios registrados.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static int consultaNumeroUsuarios() throws ClassNotFoundException, SQLException {

		PreparedStatement ps = conectarServidor()
				.prepareStatement("select count(*) from T_USUARIOS");

		ResultSet rs = ps.executeQuery();

		rs.next();
		int num = rs.getInt(1);

		cerrarServidor();

		return num;

	}

	
	
	// Termina funciones de usuario

	// Empieza funciones de Favorito

	/**
	 * Inserta el id de una carta, junto con el nombre de usuario, para así hacer un
	 * guardado de los favoritos en la base de datos.
	 * 
	 * @param nombreUsuario
	 *            Nombre del usuario en el que se quiere hacer el guardado de la
	 *            carta.
	 * @param idCard
	 *            Carta que se quiere guardar.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void insertarFavorito(String nombreUsuario, String idCard)
			throws SQLException, ClassNotFoundException {

		PreparedStatement ps = conectarServidor()
				.prepareStatement("insert into T_FAVORITOS (NombreUsuario, IdCarta) values (?, ?);");
		ps.setString(1, nombreUsuario);
		ps.setString(2, idCard);

		ps.execute();

		cerrarServidor();

	}

	/**
	 * Consulta si un id está guardado con un nombre de usuario concreto.
	 * 
	 * @param nombreUsuario
	 *            Nombre del usuario del que se quiere saber si tiene una carta
	 *            favorita concreta.
	 * @param idCard
	 *            Carta que se quiere saber si es favorita.
	 * @return True si se cumple la combinación, False en caso contrario.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public static boolean consultarExisteFavorito(String nombreUsuario, String idCard)
			throws ClassNotFoundException, SQLException {
		PreparedStatement ps = conectarServidor()
				.prepareStatement("select * from T_USUARIOS where Nombre = ? and IdCard = ?");
		ps.setString(1, nombreUsuario);
		ps.setString(2, idCard);

		ResultSet rs = ps.executeQuery();
		boolean b = rs.last() ? true : false;

		cerrarServidor();

		return b;
	}

	/**
	 * Elimina un registro con la combinación de nombre de usuario e id de la carta
	 * de la tabla t_favoritos.
	 * 
	 * @param nombreUsuario
	 *            Nombre del usuario del que se quiere borrar un favorito.
	 * @param idCard
	 *            Carta que se quiere eliminar de ese usuario.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public static void eliminarFavorito(String nombreUsuario, String idCard)
			throws ClassNotFoundException, SQLException {

		PreparedStatement ps = conectarServidor()
				.prepareStatement("delete from T_FAVORITOS where Nombre = ? and IdCarta = ?;");
		ps.setString(1, nombreUsuario);
		ps.setString(2, idCard);

		ps.execute();

		cerrarServidor();

	}

	/**
	 * 
	 * Elimina todos los favoritos de un usuario concreto de una sola vez.
	 * 
	 * @param nombreUsuario
	 *            Nombre del usuario del que se quiere eliminar los favoritos.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public static void eliminarTodosFavoritos(String nombreUsuario) throws ClassNotFoundException, SQLException {
		PreparedStatement ps = conectarServidor().prepareStatement("delete from T_FAVORITOS where Nombre = ?;");
		ps.setString(1, nombreUsuario);

		ps.execute();

		cerrarServidor();
	}

	/**
	 * Devuelve todos los favoritos de un usuario concreto
	 * 
	 * @param nombreUsuario
	 * @return Lista de String con los id de carta
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public static List<String> consultaFavoritos(String nombreUsuario) throws ClassNotFoundException, SQLException {
		List<String> lista = new ArrayList<>();

		PreparedStatement ps = conectarServidor()
				.prepareStatement("select IdCarta from T_FAVORITOS where NombreUsuario = ?");
		ps.setString(2, nombreUsuario);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			lista.add(rs.getString(1));
		}

		cerrarServidor();

		return lista;

	}

	/**
	 * Consulta el número total de cartas favoritas de un usuario concreto.
	 * @param nombreUsuario Nombre del usuario del que se quiere sacar el número de favoritos.
	 * @return int con la cantidad de cartas.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static int consultaNumeroFavoritos(String nombreUsuario) throws ClassNotFoundException, SQLException {

		PreparedStatement ps = conectarServidor()
				.prepareStatement("select count(*) from T_FAVORITOS where NombreUsuario = ?");
		ps.setString(1, nombreUsuario);

		ResultSet rs = ps.executeQuery();

		rs.next();
		int num = rs.getInt(1);

		cerrarServidor();

		return num;

	}

	// Termina funciones de Favorito

	// Empieza funciones de Mazo

	/**
	 * Inserta un nuevo mazo a un usuario concreto.
	 * 
	 * @param nombreUsuario
	 *            Nombre del usuario en el que se quiere crear un nuevo mazo.
	 * @param nombreMazo
	 *            Nombre del nuevo mazo.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void insertarMazo(String nombreUsuario, String nombreMazo)
			throws ClassNotFoundException, SQLException {

		PreparedStatement ps = conectarServidor()
				.prepareStatement("insert into T_Mazos (Nombre, NombreUsuario) values (?, ?);");
		ps.setString(1, nombreMazo);
		ps.setString(2, nombreUsuario);

		ps.execute();

		cerrarServidor();

	}

	/**
	 * Elimina el mazo de un usuario concreto.
	 * 
	 * @param nombreUsuario
	 *            Nombre del usuario del que se quiere borrar un mazo.
	 * @param nombreMazo
	 *            Mazo que se quiere borrar del usuario.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public static void eliminarMazo(String nombreUsuario, String nombreMazo)
			throws ClassNotFoundException, SQLException {

		PreparedStatement ps = conectarServidor()
				.prepareStatement("delete from T_MAZOS where Nombre = ? and NombreUsuario = ?;");
		ps.setString(1, nombreMazo);
		ps.setString(2, nombreUsuario);

		ps.execute();

		cerrarServidor();

	}

	/**
	 * Elimina todos los mazos de un usuario concreto.
	 * 
	 * @param nombreUsuario
	 *            Usuario del que se quiere borrar todos los mazos.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void eliminarTodosMazos(String nombreUsuario) throws ClassNotFoundException, SQLException {

		PreparedStatement ps = conectarServidor().prepareStatement("delete from T_MAZOS whereNombreUsuario = ?;");
		ps.setString(1, nombreUsuario);

		ps.execute();

		cerrarServidor();

	}

	/**
	 * 
	 * Consulta los mazos de un usuario, y los devuelve como una list de mazo.
	 * 
	 * @param nombreUsuario Nombre de usuario del que se quiere sacar todos los mazos.
	 * @return List de Mazo
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static List<Mazo> consultaMazos(String nombreUsuario) throws ClassNotFoundException, SQLException {
		List<Mazo> lista = new ArrayList<>();

		PreparedStatement ps = conectarServidor().prepareStatement("select * from T_MAZOS where NombreUsuario = ?");
		ps.setString(1, nombreUsuario);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {

			lista.add(new Mazo(rs.getInt(1), rs.getString(2)));
		}
		cerrarServidor();
		return lista;

	}
	
	/**
	 * Consulta el número total de mazos de un usuario concreto.
	 * @param nombreUsuario Nombre del usuario del que se quiere saber la cantidad de mazos que tiene.
	 * @return int con la cantidad de mazos que tiene.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static int consultaNumeroMazos(String nombreUsuario) throws ClassNotFoundException, SQLException {

		PreparedStatement ps = conectarServidor()
				.prepareStatement("select count(*) from T_MAZOS where NombreUsuario = ?");
		ps.setString(1, nombreUsuario);

		ResultSet rs = ps.executeQuery();

		rs.next();
		int num = rs.getInt(1);

		cerrarServidor();

		return num;

	}

	// Termina funciones de Mazo

	// Empieza funciones de MazoCartas
	/**
	 * Inserta una carta, en un mazo determinado. Como los mazos tienen  un id autonumérico asignado a un usuario, no hay que utilizar al usuario en este caso.
	 * 
	 * @param idMAzo Id del mazo en el que se quiere insertar la carta. Ya está relacionado con el usuario.
	 * @param idCarta Id de la carta que se quiere meter en el mazo.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void insertarMazoCarta(int idMAzo, String idCarta) throws ClassNotFoundException, SQLException {

		PreparedStatement ps = conectarServidor()
				.prepareStatement("insert into T_Mazo_Cartas (IdMazo, IdCarta) values (?, ?);");
		ps.setInt(1, idMAzo);
		ps.setString(2, idCarta);

		ps.execute();

		cerrarServidor();


	}

	/**
	 * Elimina una carta de un mazo determinado.
	 * 
	 * @param idMAzo Id del mazo del que se quiere eliminar una carta.
	 * @param idCarta Id de la carta que se quiere eliminar.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public static void eliminarMazoCarta(int idMAzo, String idCarta) throws ClassNotFoundException, SQLException {
		PreparedStatement ps = conectarServidor()
				.prepareStatement("delete top(1) from T_Mazo_Cartas where IdMazo = ? and IdCarta = ?;");
		ps.setInt(1, idMAzo);
		ps.setString(2, idCarta);

		ps.execute();

		cerrarServidor();

	}
	
	/**
	 * Elimina todas las cartas de un mazo concreto.
	 * 
	 * @param idMAzo Id del mazo del que se van a borrar las cartas.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void eliminarTodosMazoCarta(int idMAzo) throws ClassNotFoundException, SQLException {
		PreparedStatement ps = conectarServidor()
				.prepareStatement("delete from T_Mazo_Cartas where IdMazo = ?;");
		ps.setInt(1, idMAzo);

		ps.execute();

		cerrarServidor();

	}

	/**
	 * Devuelve todas las cartas de un mazo determinado
	 * 
	 * @param IdMazo Id del mazo del que se quieren las cartas.
	 * @return Lista de String con los id de las cartas
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public static List<String> consultaMazoCarta(int IdMazo) throws ClassNotFoundException, SQLException {
		List<String> lista = new ArrayList<>();

		PreparedStatement ps = conectarServidor()
				.prepareStatement("select IdCarta from T_MAZO_CARTAS where IdMazo = ?");
		ps.setInt(1, IdMazo);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			lista.add(rs.getString(1));
		}

		return lista;

	}
	
	/**
	 * Consulta el número total de cartas de un mazo concreto.
	 * @param IdMazo Id del mazo del que se quiere saber el número total de cartas.
	 * @return int con la cantidad de cartas.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static int consultaNumeroMazoCarta(int IdMazo) throws ClassNotFoundException, SQLException {

		PreparedStatement ps = conectarServidor()
				.prepareStatement("select count(*) from T_MAZO_CARTAS where IdMazo = ?");
		ps.setInt(1, IdMazo);

		ResultSet rs = ps.executeQuery();

		rs.next();
		int num = rs.getInt(1);

		cerrarServidor();

		return num;

	}

	// Termina funciones de CartaMazo

}
