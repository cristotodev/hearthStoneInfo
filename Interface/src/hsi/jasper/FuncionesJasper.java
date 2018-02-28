package hsi.jasper;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hsi.sql.FuncionesSQL;
import hsi.sql.Mazo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class FuncionesJasper {

	private static String usuario;
	private List<String> favoritos;
	private List<Mazo> mazos;
	

	public void EjecutarJasper() throws ClassNotFoundException, SQLException, JRException, IOException {
		
		favoritos = RecogerFavoritos();
		mazos = RecogerMazos();
		
		
		InputStream is = FuncionesJasper.class.getResourceAsStream("/hsi/jasper/HearthStoneReport.jasper");
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("TITULO", "HearthStoneInfo");
		
		JasperPrint jp = JasperFillManager.fillReport(is, parametros, 
				new JRBeanCollectionDataSource(mazos));
		
		JasperExportManager.exportReportToPdfFile(jp, "DetallesHSI.pdf");
		Desktop.getDesktop().open(new File("DetallesHSI.pdf"));
		
		
	}
	
	
	private List<String> RecogerFavoritos() throws ClassNotFoundException, SQLException {
		return FuncionesSQL.consultaFavoritos(usuario);
	}
	
	private List<Mazo> RecogerMazos() throws ClassNotFoundException, SQLException {
		return FuncionesSQL.consultaMazos(usuario);
	}
	
	public static void setUsuario(String usuario) {
		FuncionesJasper.usuario = usuario;
	}
	
}
