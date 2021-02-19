import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class IzdavacAzuriranje extends JFrame {
	private Connection veza;
	private JPanel panGlavni, panDugmad;
	private JTextField txtNaziv;
	private JButton btnIzmijeni, btnOdustani, btnUkloni;
	private JComboBox<String> jcbSviIzdavaci;
	private String idIzdavaca, nazivIzdavaca;
	
	public IzdavacAzuriranje(Connection c) {
		veza = c;
		postaviKomponenteRaspored();
		//System.out.println(nazivIzdavaca);
	}
	
	//Postavlja komponente grafickog interfejsa.
	private void postaviKomponenteRaspored() {
		setTitle("Obrazac za azuriranje izdavaca u bazi");
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int)(d.getWidth()*0.33), (int)(d.getHeight()*0.2));
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		ActionListener osl = new Osluskivac();
		
		jcbSviIzdavaci = new JComboBox<String>();
		jcbSviIzdavaci.setEditable(true); //Ne reaguje na dogadjaje ako je false!!!
		jcbSviIzdavaci.setMaximumRowCount(10);
		jcbSviIzdavaci.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		jcbSviIzdavaci.addActionListener(osl);
		
		txtNaziv = new JTextField(25);
		
		btnIzmijeni = new JButton("Izmijeni");
		btnIzmijeni.addActionListener(osl);
		btnUkloni = new JButton("Ukloni");
		btnUkloni.addActionListener(osl);
		btnOdustani = new JButton("Odustani");
		btnOdustani.addActionListener(osl);
		
		unesiSveIzdavacePadajucaLista();
		
		panDugmad = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
		panDugmad.add(btnIzmijeni);
		panDugmad.add(btnUkloni);
		panDugmad.add(btnOdustani);
		
		panGlavni = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.ipadx = 5;
		gbc.ipady = 5;
		gbc.weightx = 100;
		gbc.weighty = 100;
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panGlavni.add(new JLabel("Izaberi izdavaca:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 0;
		panGlavni.add(jcbSviIzdavaci, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 1;
		panGlavni.add(new JLabel("Naziv:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 1;
		panGlavni.add(txtNaziv, gbc);
		
		gbc.gridwidth =  2;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 2;
		panGlavni.add(panDugmad, gbc);
		
		add(panGlavni, BorderLayout.CENTER);
		
	} //Kraj postaviKomponenteRaspored.
	
	//U JComboBox unosi nazive svih izdavaca.
	private void unesiSveIzdavacePadajucaLista() {
		if (veza == null) return;
		String sql = "";
		Statement stmt;
		
		jcbSviIzdavaci.removeAllItems();
		
		try {
			stmt = veza.createStatement();
			sql = "select Naziv from IZDAVAC order by Naziv";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				jcbSviIzdavaci.addItem(rs.getString("Naziv"));
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} //Kraj metode unesiSveIzdavacePadajucaLista.
	
	//Klasa osluskivaca.
	private class Osluskivac implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object izvor = e.getSource();
			
			if (izvor == btnOdustani) {
				IzdavacAzuriranje.this.dispose();
			}
			else if (izvor == btnIzmijeni) {
				String naziv = txtNaziv.getText().trim();
				if (jeLiIspravanUnos(naziv)) {
					izmijeniNazivUBazi(naziv, idIzdavaca);
					unesiSveIzdavacePadajucaLista();
				}
			}
			else if (izvor == btnUkloni) {
				if (nazivIzdavaca.equals("Nepoznat")) {
					JOptionPane.showMessageDialog(
							null, "Ne mozete ukloniti ovog izdavaca!", 
													"Napomena", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				else if (jeLiPridruzenNekojKnjizi(idIzdavaca)) {
					JOptionPane.showMessageDialog(
							null, "Ne mozete ukloniti izdavaca!\nPrvo obrisite sve knjige koje je izdao!", 
													"Napomena", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				else {
					int odgovor = JOptionPane.showConfirmDialog(null, 
							"Da li zelite da uklonite izdavaca \"" + 
							nazivIzdavaca + "\", iz baze podataka?", 
							"Obavjestenje", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (odgovor == JOptionPane.OK_OPTION) {
						ukloniIzdavaca(idIzdavaca);
						unesiSveIzdavacePadajucaLista();
					}
				}
			}
			else if (izvor == jcbSviIzdavaci) {
				nazivIzdavaca = (String)jcbSviIzdavaci.getEditor().getItem();
				idIzdavaca = nadjiIdIzdavaca(nazivIzdavaca);
				if (idIzdavaca.equals("")) {
					return;
				}
				else {
					txtNaziv.setText(nazivIzdavaca);
				}
			}
			
		}
	} //kraj klase osluskivaca.
	
	//Provjerava je li unos ispravan
	private boolean jeLiIspravanUnos(String naziv) {
		if (naziv.equals("")) {
			JOptionPane.showMessageDialog(
					null, "Morate unijeti naziv izdavaca!", "Napomena", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		else if (naziv.length()>20) {
			JOptionPane.showMessageDialog(
					null, "Naziv izdavaca ne moze sadrzati vise og 20 karaktera!", "Napomena", 
							JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (postojiLiUBazi(naziv)) {
			JOptionPane.showMessageDialog(
					null, "Naziv izdavaca vec postoji u bazi podataka!", "Napomena", 
							JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		
		return true;
	} //Kraj metode jeLiIspravanUnos.
	
	//Provjerava postoji li isti naziv u bazi.
	private boolean postojiLiUBazi(String naziv) {
		boolean rezultat = true;
		if (veza == null) return rezultat;
		String sql;
		PreparedStatement ps;
		
		try {
			sql = "select count(*) from IZDAVAC where Naziv = ?";
			ps = veza.prepareStatement(sql);
			ps.setString(1, naziv);
			ResultSet rs = ps.executeQuery();
			rs.next();
			if (rs.getInt("count(*)") == 0) rezultat = false;
			else if (rs.getInt("count(*)") == 1) rezultat = true;
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rezultat;
		
	} //Kraj metode postojiLiUBazi.
	
	//Azurira zapis u tabeli IZDAVAC.
	private void izmijeniNazivUBazi(String naziv, String id) {
		if (veza == null) return;
		String sql;
		PreparedStatement ps;
		
		try {
			sql = "update IZDAVAC set Naziv = ? where Id = ?";
			ps = veza.prepareStatement(sql);
			ps.setString(1, naziv);
			ps.setString(2, id);
			ps.executeUpdate();
			ps.close();
			
			JOptionPane.showMessageDialog(
					null, "Naziv izdavaca je azuriran u bazi podataka!", "Napomena", 
							JOptionPane.INFORMATION_MESSAGE);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	} //Kraj metode izmijeniNazivUBazi.
	
	//Je li izdavac pridruzen knjizi.
	private boolean jeLiPridruzenNekojKnjizi(String id) {
		if (veza == null) return true;
		int i = 0;
		String sql;
		try {
			sql = "select count(*) from KNJIGA where Izdavac_Id = ?";
			PreparedStatement ps = veza.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			i = rs.getInt("count(*)");
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (i <= 0) {
			return false;
		}
		else {
			return true;
		}
	} //Kraj metode jeLiPridruzenNekojKnjizi.
	
	//Uklanja izdavaca iz baze.
	private void ukloniIzdavaca(String id) {
		if (veza == null) return;
		String sql;
		
		try {
			sql = "delete from IZDAVAC where Id = ?";
			PreparedStatement ps = veza.prepareStatement(sql);
			ps.setString(1, id);
			ps.executeUpdate();
			ps.close();
			
			JOptionPane.showMessageDialog(
					null, "Izdavac je uklonjen iz baze podataka!", 
											"Napomena", JOptionPane.INFORMATION_MESSAGE);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} //Kraj ukloniIzdavaca.
	
	//Nalazi ID, na osnovu naziva.
	private String nadjiIdIzdavaca(String naziv) {
		String id = "";
		if (veza == null) return id;
		String sql = "";
		PreparedStatement ps;
		
		try {
			sql = "select Id from IZDAVAC where Naziv = ?";
			ps = veza.prepareStatement(sql);
			ps.setString(1, naziv);
			ResultSet rs = ps.executeQuery();
			if (rs.next() && !nazivIzdavaca.equals("Nepoznat")) {
				id = rs.getString("Id");
				if (!btnIzmijeni.isEnabled() || !btnUkloni.isEnabled()) {
					btnIzmijeni.setEnabled(true);
					btnUkloni.setEnabled(true);
				}
			}
			else {
				btnIzmijeni.setEnabled(false);
				btnUkloni.setEnabled(false);
			}
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
		
	} //Kraj metode nadjiIdIzdavaca.
	
} //Kraj klase IzdavacAzuriranje.
