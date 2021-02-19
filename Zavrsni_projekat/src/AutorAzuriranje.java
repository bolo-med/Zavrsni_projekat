import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

import javax.swing.*;


public class AutorAzuriranje extends JFrame {
	private Connection veza;
	private String idAutora, imeAutora, pocetnoImeAutora, biografijaAutora, 
					pocetnaPutanjaDoSlike, putanjaDoSlikeAutora;
	private JComboBox<String> jcbAutori;
	private JTextArea txaBiografija;
	private JTextField txtIme, txtSlikaPutanja;
	private JButton btnIzaberiPutanju, btnObrisiPutanju, btnAzuriraj, btnUkloni, btnOdustani;
	
	int i = 0; //Ne sluzi nicemu.
	
	public AutorAzuriranje(Connection c) {
		veza = c;
		ActionListener osl = new Osluskivac();
		
		setTitle("Obrazac za azuriranje zapisa u bazi podataka");
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int)(d.getWidth()*0.33), (int)(d.getHeight()*0.6));
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		//Autori - kombo-boks.
		jcbAutori = new JComboBox<String>();
		jcbAutori.setEditable(true); //Ne reaguje na dogadjaje ako je false!!!
		jcbAutori.setMaximumRowCount(10);
		jcbAutori.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		jcbAutori.addActionListener(osl);
		
		//Ime autora.
		txtIme = new JTextField(25);
		
		//Biografija autora.
		txaBiografija = new JTextArea(10, 25);
		txaBiografija.setLineWrap(true);
		txaBiografija.setWrapStyleWord(true);
		JScrollPane jspBiografija = new JScrollPane(txaBiografija);
		
		//Putanja do fotografije.
		txtSlikaPutanja = new JTextField(25);
		txtSlikaPutanja.setEditable(false);
		
		//Dugmad - Izaberi, Obrisi.
		btnIzaberiPutanju = new JButton("Izaberi");
		btnIzaberiPutanju.addActionListener(osl);
		btnObrisiPutanju = new JButton("Obrisi");
		btnObrisiPutanju.addActionListener(osl);
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		p1.add(btnIzaberiPutanju);
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
		p2.add(btnObrisiPutanju);
		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 2));
		p3.add(p1);
		p3.add(p2);
		JPanel panPutanja = new JPanel();
		panPutanja.setLayout(new BorderLayout());
		panPutanja.add(txtSlikaPutanja, BorderLayout.NORTH);
		panPutanja.add(p3, BorderLayout.CENTER);
		
		//Dugmad - Azuriraj, Ukloni, Odustani.
		btnAzuriraj = new JButton("Azuriraj");
		btnAzuriraj.addActionListener(osl);
		btnUkloni = new JButton("Ukloni");
		btnUkloni.addActionListener(osl);
		btnOdustani = new JButton("Odustani");
		btnOdustani.addActionListener(osl);
		JPanel panDugmad2 = new JPanel();
		panDugmad2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
		panDugmad2.add(btnAzuriraj);
		panDugmad2.add(btnUkloni);
		panDugmad2.add(btnOdustani);
		
		//Raspored grafickih komponenata.
		JPanel panGlavni = new JPanel();
		panGlavni.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.ipadx = 5;
		gbc.ipady = 5;
		gbc.weightx = 100;
		gbc.weighty = 100;
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panGlavni.add(new JLabel("Odaberi autora:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 0;
		panGlavni.add(jcbAutori, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 1;
		panGlavni.add(new JLabel("Ime autora:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 1;
		panGlavni.add(txtIme, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 2;
		panGlavni.add(new JLabel("Biografija:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 2;
		panGlavni.add(jspBiografija, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 3;
		panGlavni.add(new JLabel("Fotografija:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 3;
		panGlavni.add(panPutanja, gbc);
		
		gbc.gridwidth =  2;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 4;
		panGlavni.add(panDugmad2, gbc);
		
		add(panGlavni, BorderLayout.CENTER);
		
		unesiSveAutorePadajucaLista();
		
	} //Kraj konstruktora.
	
	//U JComboBox unosi imena svih autora.
	private void unesiSveAutorePadajucaLista() {
		if (veza == null) return;
		String sql = "";
		
		try {
			Statement stmt = veza.createStatement();
			sql = "select Ime_prezime from AUTOR order by Ime_prezime";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				jcbAutori.addItem(rs.getString("Ime_prezime"));
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} //Kraj metode unesiSveAutorePadajucaLista.
	
	//Osluskivac.
	private class Osluskivac implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object izvor = e.getSource();
			
			if (izvor == jcbAutori) {
				prikaziVrijednostiKolona((String)jcbAutori.getEditor().getItem());
			}
			if (izvor == btnAzuriraj) {
				//Ime autora.
				imeAutora = txtIme.getText().trim();
				if (imeAutora.equals("")) {
					JOptionPane.showMessageDialog(
							null, "Morate unijeti ime autora!", "Napomena", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				else if (imeAutora.length()>50) {
					JOptionPane.showMessageDialog(
							null, "Ime autora ne moze sadrzati vise og 50 karaktera!", "Napomena", 
																	JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				else if (postojiLiIme(imeAutora) && !imeAutora.equals(pocetnoImeAutora)) {
					JOptionPane.showMessageDialog(
							null, "Ime autora mora biti jedinstveno!", "Napomena", 
																	JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				//Biografija autora.
				biografijaAutora = txaBiografija.getText().trim();
				if (biografijaAutora.length()>1000) {
					JOptionPane.showMessageDialog(
							null, "Biografija autora ne moze sadrzati vise od 1000 karaktera!", "Napomena", 
																			JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				else if (biografijaAutora.equals("")) {
					biografijaAutora = null;
				}
				
				//Putanja do autorove slike.
				putanjaDoSlikeAutora = txtSlikaPutanja.getText().trim();
				String slikaExt = "";
				if (!putanjaDoSlikeAutora.equals("") && putanjaDoSlikeAutora.length()>4)
					slikaExt = putanjaDoSlikeAutora.substring(putanjaDoSlikeAutora.length()-3);
				if (putanjaDoSlikeAutora.length() > 100) {
					JOptionPane.showMessageDialog(
							null, "Putanja do slike(" + putanjaDoSlikeAutora.length() + 
												") ne smije biti duza od 100 karaktera!", 
													"Napomena", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				else if (putanjaDoSlikeAutora.equals("") || putanjaDoSlikeAutora.length()<=4) {
					//putanjaDoSlikeAutora = null; //Problem sa poredjenjem!!!
					putanjaDoSlikeAutora = "";
				}
				else if (!slikaExt.equals("png") && 
							!slikaExt.equals("jpg") && !slikaExt.equals("gif")) {
					JOptionPane.showMessageDialog(
							null, "Format slike mora biti PNG, JPG ili GIF!", 
													"Napomena", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				azurirajAutora();
			}
			else if (izvor == btnIzaberiPutanju) {
				JFileChooser jfc = new JFileChooser();
				if (jfc.showOpenDialog(AutorAzuriranje.this) == JFileChooser.APPROVE_OPTION) {
					String apsolutnaPutanja = jfc.getSelectedFile().getAbsolutePath();
					if (apsolutnaPutanja.matches(".*src/slike.*")) {
						JOptionPane.showMessageDialog(
								null, "Ne mozete odabrati slike iz tog foldera!", 
														"Napomena", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						txtSlikaPutanja.setText(apsolutnaPutanja);
					}
				}
			}
			else if (izvor == btnObrisiPutanju) {
				txtSlikaPutanja.setText("");
			}
			else if (izvor == btnUkloni) {
				if (jeLiNapisaoNekuKnjigu(idAutora)) {
					JOptionPane.showMessageDialog(
							null, "Ne mozete obrisati autora!\nPrvo obrisite sve knjige koje je napisao!", 
													"Napomena", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				else {
					int odgovor = JOptionPane.showConfirmDialog(
							null, "Da li zelite da uklonite autora iz baze podataka?", 
							"Napomena", JOptionPane.YES_NO_OPTION);
					if (odgovor == JOptionPane.YES_OPTION) {
						String slikaPutanja = nadjiPutanjuDoSlike(idAutora);
						ukloniAutora(idAutora);
						if (!slikaPutanja.equals("") &&
								//!(slikaPutanja.substring(slikaPutanja.length()-6).equals("_a.png"))) {
								!(slikaPutanja.equals("src/slike_a/nema_slike_a.png"))) {
							ukloniSliku(slikaPutanja);
							//System.out.println(slikaPutanja);
							//System.out.println(slikaPutanja.substring(slikaPutanja.length()-6));
						}
						
						JOptionPane.showMessageDialog(
								null, "Autor je uklonjen iz baze podataka!", 
														"Napomena", JOptionPane.INFORMATION_MESSAGE);
						
						AutorAzuriranje.this.dispose();
					}
				}
			}
			else if (izvor == btnOdustani) {
				AutorAzuriranje.this.dispose();
			}
			
		}
	} //Kraj klase-osluskivaca.
	
	//
	private void prikaziVrijednostiKolona(String ime) {
		if (veza == null) return;
		String sql;
		PreparedStatement ps;
		
		try {
			sql = "select * from AUTOR where Ime_prezime = ?";
			ps = veza.prepareStatement(sql);
			ps.setString(1, ime);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				idAutora = rs.getString("Id");
				pocetnoImeAutora = rs.getString("Ime_prezime");
				txtIme.setText(pocetnoImeAutora);
				txaBiografija.setText(rs.getString("Biografija"));
				pocetnaPutanjaDoSlike = rs.getString("Slika");
				txtSlikaPutanja.setText(pocetnaPutanjaDoSlike);
				if (!btnAzuriraj.isEnabled()) omoguciDugmad();
			}
			else {
				onemoguciDugmad();
			}
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	} //Kraj prikaziVrijednostiKolona.
	
	//Onemogucava dugmad, u slucaju nepravilnog izbora autora.
	private void onemoguciDugmad() {
		btnAzuriraj.setEnabled(false);
		btnUkloni.setEnabled(false);
		btnIzaberiPutanju.setEnabled(false);
	}
	//Omogucava dugmad, u slucaju ispravnog izbora autora.
	private void omoguciDugmad() {
		btnAzuriraj.setEnabled(true);
		btnUkloni.setEnabled(true);
		btnIzaberiPutanju.setEnabled(true);
	}
	
	//provjerava postoji li autor sa istim imenom.
	private boolean postojiLiIme(String autor) {
		int rezultat = 1;
		if (veza == null) return true;
		String sql;
		PreparedStatement ps;
		
		try {
			sql = "select count(*) from AUTOR where Ime_prezime = ?";
			ps = veza.prepareStatement(sql);
			ps.setString(1, autor);
			ResultSet rs = ps.executeQuery();
			rs.next();
			rezultat = rs.getInt("count(*)");
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (rezultat == 0) {
			return false;
		}
		else {
			return true;
		}
		
	} //Kraj postojiLiIme.
	
	//
	private void azurirajAutora() {
		if (veza == null) return;
		String sql;
		PreparedStatement ps;
		try {
			sql = "update AUTOR set Ime_prezime = ?, Biografija = ?, Slika = ? where id = ?";
			ps = veza.prepareStatement(sql);
			
			ps.setString(1, imeAutora);
			ps.setString(2, biografijaAutora);
			ps.setString(3, putanjaDoSlikeAutora);
			if (putanjaDoSlikeAutora == null || putanjaDoSlikeAutora.equals("")) {
				ps.setString(3, "src/slike_a/nema_slike_a.png");
			}
			ps.setString(4, idAutora);
			ps.executeUpdate();
			ps.close();
			
			//System.out.println("pocetna: \t" + pocetnaPutanjaDoSlike);
			//System.out.println("nova: \t" + putanjaDoSlikeAutora);
			
			if (!putanjaDoSlikeAutora.equals(pocetnaPutanjaDoSlike)) {
				if (!putanjaDoSlikeAutora.equals("")) {
					kopirajSliku(putanjaDoSlikeAutora, Integer.parseInt(idAutora));
				}
				else if (putanjaDoSlikeAutora.equals("") && 
						!pocetnaPutanjaDoSlike.equals("src/slike_a/nema_slike_a.png")) {
					ukloniSliku(pocetnaPutanjaDoSlike);
				}
			}
			
			JOptionPane.showMessageDialog(
					null, "Autor je azuriran u bazi podataka!", 
								"Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		AutorAzuriranje.this.dispose();
		
	} //Kraj azurirajAutora.
	
	//
	private void kopirajSliku(String putanja, int id) {
		FileInputStream fis = null; //null je bitno.
		FileOutputStream fos = null; //null je bitno.
		String formatSlike = putanja.substring(putanja.length()-3);
		try {
			fis = new FileInputStream(putanja);
			fos = new FileOutputStream("src/slike_a/" + id + "." + formatSlike);
			byte[] buffer = new byte[1024];
	        int length;
	        while ((length = fis.read(buffer)) > 0) {
	            fos.write(buffer, 0, length);
	        }
	        //azurirajCelijuTabele(id, formatSlike);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		azurirajCelijuTabele(id, formatSlike);
		
	} //Kraj kopirajSliku.
	
	//Mijenja vrijednost polja kolone Slika.
	private void azurirajCelijuTabele(int id, String ext) {
		String sql = "update AUTOR set Slika = ? where Id = ?";
        try {
			PreparedStatement ps = veza.prepareStatement(sql);
			ps.setString(1, "src/slike_a/" + id + "." + ext);
			ps.setInt(2, id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} //Kraj metoda azurirajCelijuTabele.
	
	//Provjerava je li dati autor, pisac neke od knjiga u bazi.
	private boolean jeLiNapisaoNekuKnjigu(String id) {
		if (veza == null) return true;
		int i = 0;
		String sql;
		try {
			sql = "select count(*) from NAPISAO where Autor_Id = ?";
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
		
	} //Kraj jeLiNapisaoNekuKnjigu.
	
	//Vraca putanju do slike.
	private String nadjiPutanjuDoSlike(String id) {
		String putanja = "";
		if (veza == null) return putanja;
		String sql;
		
		try {
			sql = "select Slika from AUTOR where Id = ?";
			PreparedStatement ps = veza.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				putanja = rs.getString("Slika");
			}
			
			if (putanja == null) {
				putanja = "";
			}
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return putanja;
	} //Kraj nadjiPutanjuDoSlike.
	
	//Brise autora iz baze podataka.
	private void ukloniAutora(String id) {
		if (veza == null) return;
		String sql;
		
		try {
			sql = "delete from AUTOR where Id = ?";
			PreparedStatement ps = veza.prepareStatement(sql);
			ps.setString(1, id);
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} //Kraj ukloniAutora.
	
	//Brise fajl sa hard-diska.
	private void ukloniSliku(String putanja) {
		File slikaFajl = new File(putanja);
		if (slikaFajl.delete()) {
			System.out.println("Slika autora je obrisana.");
		}
		else {
			System.out.println("Slika autora NIJE je obrisana.");
		}
	} //Kraj ukloniSliku.
	
} //Kraj klase AutorAzuriranje.
