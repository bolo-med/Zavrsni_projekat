import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

import javax.swing.*;


public class AutorUnos extends JFrame {
	private Connection veza;
	private JPanel panGlavni, panDugmad, panPutanja;
	private JTextField txtIme, txtSlika;
	private JTextArea txaBiografija;
	private JScrollPane jspBiografija;
	private JButton btnUnesi, btnOdustani, btnPutanja, btnObrisiPutanju;
	private String imePrezime, biografija, slikaPutanja;
	
	public AutorUnos(Connection veza, String imeAutora) {
		this.veza = veza;
		setTitle("Obrazac za unos autora u bazu");
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int)(d.getWidth()*0.3), (int)(d.getHeight()*0.4));
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		ActionListener osl = new Akcija();
		
		panGlavni = new JPanel();
		panDugmad = new JPanel();
		panDugmad.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
		panPutanja = new JPanel();
		
		txtIme = new JTextField(25);
		txtIme.setText(imeAutora); //Za slucaj kad se nov autor unosi prilikom unosa nove knjige.
		txaBiografija = new JTextArea(7, 25);
		txaBiografija.setLineWrap(true);
		txaBiografija.setWrapStyleWord(true);
		jspBiografija = new JScrollPane(txaBiografija);
		txtSlika = new JTextField(25);
		txtSlika.setEditable(false);
		
		btnUnesi = new JButton("Unesi");
		btnUnesi.addActionListener(osl);
		btnOdustani = new JButton("Odustani");
		btnOdustani.addActionListener(osl);
		panDugmad.add(btnUnesi);
		panDugmad.add(btnOdustani);
		btnPutanja = new JButton("Izaberi");
		btnPutanja.addActionListener(osl);
		btnObrisiPutanju = new JButton("Obrisi");
		btnObrisiPutanju.addActionListener(osl);
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		p1.add(btnPutanja);
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
		p2.add(btnObrisiPutanju);
		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 2));
		p3.add(p1);
		p3.add(p2);
		panPutanja.setLayout(new BorderLayout());
		panPutanja.add(txtSlika, BorderLayout.NORTH);
		panPutanja.add(p3, BorderLayout.CENTER);
		
		panGlavni.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.ipadx = 5;
		gbc.ipady = 5;
		gbc.weightx = 100;
		gbc.weighty = 100;
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panGlavni.add(new JLabel("Ime:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 0;
		panGlavni.add(txtIme, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 1;
		panGlavni.add(new JLabel("Biografija:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 1;
		panGlavni.add(jspBiografija, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 2;
		panGlavni.add(new JLabel("Slika:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 2;
		panGlavni.add(panPutanja, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 3;
		panGlavni.add(panDugmad, gbc);
		
		add(panGlavni);
		
		
	} //Kraj konstruktora.
	
	//Osluskivac.
	private class Akcija implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object izvor = e.getSource();
			if (izvor == btnUnesi) {
				sakupiVrijednostiIzObrasca();
			}
			else if(izvor == btnOdustani) {
				AutorUnos.this.dispose();
			}
			else if (izvor == btnPutanja) {
				JFileChooser jfc = new JFileChooser();
				if (jfc.showOpenDialog(AutorUnos.this) == JFileChooser.APPROVE_OPTION) {
					String apsolutnaPutanja = jfc.getSelectedFile().getAbsolutePath();
					if (apsolutnaPutanja.matches(".*src/slike.*")) {
						JOptionPane.showMessageDialog(
								null, "Ne mozete odabrati slike iz tog foldera!", 
														"Napomena", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						txtSlika.setText(apsolutnaPutanja);
					}
				}
			}
			else if (izvor == btnObrisiPutanju) {
				txtSlika.setText("");
			}
			
		}
		
	} //Kraj klase-osluskivaca.
	
	//Prikuplja vrijednosti koje su unesene u formu.
	private void sakupiVrijednostiIzObrasca() {
		
		//Ime i prezime autora.
		imePrezime = txtIme.getText().trim();
		if (imePrezime.equals("")) {
			JOptionPane.showMessageDialog(
					null, "Morate unijeti ime autora!", "Napomena", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		else if (imePrezime.length()>50) {
			JOptionPane.showMessageDialog(
					null, "Ime autora ne moze sadrzati vise og 50 karaktera!", "Napomena", 
															JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		else if (postojiLiUBazi(imePrezime)) {
			JOptionPane.showMessageDialog(
					null, "Autor je vec upisan u bazu podataka!", "Napomena", 
															JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		//Biografija autora.
		biografija = txaBiografija.getText().trim();
		if (biografija.length()>1000) {
			JOptionPane.showMessageDialog(
					null, "Biografija autora ne moze sadrzati vise od 1000 karaktera!", "Napomena", 
																	JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		else if (biografija.equals("")) {
			biografija = null;
		}
		
		//Putanja do slike.
		slikaPutanja = txtSlika.getText().trim();
		String slikaExt = "";
		if (!slikaPutanja.equals("") && slikaPutanja.length()>4)
			slikaExt = slikaPutanja.substring(slikaPutanja.length()-3);
		if (slikaPutanja.length() > 100) {
			JOptionPane.showMessageDialog(
					null, "Putanja do slike(" + slikaPutanja.length() + 
										") ne smije biti duza od 100 karaktera!", 
											"Napomena", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		else if (slikaPutanja.equals("") || slikaPutanja.length()<=4) {
			slikaPutanja = null;
		}
		else if (!slikaExt.equals("png") && 
					!slikaExt.equals("jpg") && !slikaExt.equals("gif")) {
			JOptionPane.showMessageDialog(
					null, "Format slike mora biti PNG, JPG ili GIF!", 
											"Napomena", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		unesiAutora();
		
	} //Kraj sakupiVrijednostiIzObrasca.
	
	//Provjerava postoji li ime autora u bazi.
	private boolean postojiLiUBazi(String ime) {
		int rezultat = -1;
		if (veza == null) return true;
		String sql;
		PreparedStatement ps;
		
		try {
			sql = "select count(*) from AUTOR where Ime_prezime = ?";
			ps = veza.prepareStatement(sql);
			ps.setString(1, ime);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				rezultat = rs.getInt("count(*)");
			}
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (rezultat > 0) {
			return true;
		}
		else {
			return false;
		}
		
	} //Kraj postojiLiUBazi.
	
	//Unosi zapis u tabelu AUTOR.
	private void unesiAutora() {
		String sql = "";
		PreparedStatement ps;
		try {
			if (veza == null) return;
			sql = "insert into AUTOR (Ime_prezime, Biografija, Slika) values (?, ?, ?)";
			ps = veza.prepareStatement(sql);
			ps.setString(1, imePrezime);
			ps.setString(2, biografija);
			ps.setString(3, slikaPutanja);
			if (slikaPutanja == null || slikaPutanja.equals("")) {
				ps.setString(3, "src/slike_a/nema_slike_a.png");
			}
			ps.executeUpdate();
			ps.close();
			
			int id = idPoslednjeDodatogAutora();
			
			if (slikaPutanja != null) {
				kopirajSliku(slikaPutanja, id);
			}
			
			JOptionPane.showMessageDialog(
					null, "Podaci o autoru su unijeti u bazu podataka!", 
								"Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		AutorUnos.this.dispose();
		
	} //Kraj unesiAutora.
	
	//Vraca vrijednost kolone Id za poslednje dodatoh autora.
	private int idPoslednjeDodatogAutora() {
		int id = -1;
		String sql = "select Id from AUTOR order by Id desc limit 1";
		Statement stmt;
		ResultSet rs;
		try {
			stmt = veza.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			id = Integer.parseInt(rs.getString("Id"));
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	} //Kraj idPoslednjeDodatogAutora.
	
	//Kopira sliku u predvidjeni folder 
	//i azurira vrijednost kolone Slika za datog autora.
	private void kopirajSliku(String putanja, int id) {
		FileInputStream fis = null; //null je bitno.
		FileOutputStream fos = null; //null je bitno.
		String ekstenzija = putanja.substring(putanja.length()-3);
		try {
			fis = new FileInputStream(putanja);
			fos = new FileOutputStream("src/slike_a/" + id + "." + ekstenzija);
			byte[] buffer = new byte[1024];
	        int length;
	        while ((length = fis.read(buffer)) > 0) {
	            fos.write(buffer, 0, length);
	        }
	        azurirajCelijuTabele(id, ekstenzija);
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
	} //Kraj kopirajSliku.
	
	//Mijenja vrijednost kolone Slika, za odgovarajuceg autora.
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
	
} //kraj glavne klase
