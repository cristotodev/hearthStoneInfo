package hsi.jasper;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hsi.sql.FuncionesSQL;
import hsi.sql.Mazo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class FuncionesJasper {

	private static String usuario;
	private List<Mazo> mazos;
	

	public void EjecutarJasper() throws ClassNotFoundException, SQLException, JRException, IOException {
		
		mazos = RecogerMazos();
		
		JasperReport jr = JasperCompileManager.compileReport(FuncionesJasper.class.getResourceAsStream("/hsi/jasper/HearthStoneReport.jrxml"));
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("TITULO", "HearthStoneInfo");
		
		JasperPrint jp = JasperFillManager.fillReport(jr, parametros, 
				new JRBeanCollectionDataSource(mazos));
		
		JasperExportManager.exportReportToPdfFile(jp, "DetallesHSI.pdf");
		Desktop.getDesktop().open(new File("DetallesHSI.pdf"));
	}
	
	private List<Mazo> RecogerMazos() throws ClassNotFoundException, SQLException {
		return FuncionesSQL.consultaMazos(usuario);
	}
	
	public static void setUsuario(String usuario) {
		FuncionesJasper.usuario = usuario;
	}
	
}
