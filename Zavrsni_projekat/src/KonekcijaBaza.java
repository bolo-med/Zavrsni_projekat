import java.sql.*;

public class KonekcijaBaza {
	
	//Metod otvara vezu ka bazi podataka.
		public static Connection otvoriVezu() {
			Connection veza = null;
			String url = "jdbc:mysql://localhost/Kucna_biblioteka?useSSL=false";
			String korisnik = "root";
			String lozinka = "root";
			
			try {
				Class.forName("com.mysql.jdbc.Driver" ).newInstance();
				veza = DriverManager.getConnection(url, korisnik, lozinka);
				System.out.println("Veza sa bazom je uspostavljena!");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			return veza;
		} //Kraj otvoriVezu().
		
		//Metod zatvara vezu sa bazom podataka.
		public static void zatvoriVezu(Connection c) {
			try {
				c.close();
				System.out.println("Veza sa bazom je zatvorena!");
			}
			catch (SQLException e) {
				//e.printStackTrace();
				System.out.println("Greska pri zatvaranj veze sa bazom: " + e.getMessage());
			}
		} // Kraj zatvoriVezu().
		
		
} //Kraj klase.
