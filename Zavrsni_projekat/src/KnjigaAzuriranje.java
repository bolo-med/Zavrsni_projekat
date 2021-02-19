import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

import javax.swing.*;

import java.util.*;

public class KnjigaAzuriranje extends JFrame {
	private Connection veza;
	private String[] vrijednostiKolona;
	private Prozor p;
	private String kolId, kolNaslov, kolOblastId, kolGodina, kolOpis, kolBrStrana, kolIzdavacId, kolSlika;
	private String oblastNaziv, izdavacNaziv;
	private JLabel lblNaslov, lblAutor, lblOblast, lblGodina, lblOpis;
	private JTextField txtNaslov, txtGodina, txtBrStrana, txtNaslovnica;
	private JTextArea txaOpis;
	private JButton btnDodaj, btnUkloni, btnAzuriraj, btnOdustani, btnPutanja, btnObrisiPutanju, btnUkloniKnjigu;
	private JPanel glavniPanel, autoriPanel, dgmPanel, dgmPanel2, panIzdavac, panOblast, panSlika;
	private JList listaAutor;
	private DefaultListModel dlmAutor;
	private JComboBox<String> jcbAutori;
	private JComboBox<String> jcbOblast;
	private JComboBox<String> jcbIzdavac;
	private JScrollPane listaKlizac, opisKlizac;
	private ActionListener osl;
	private KeyListener osl2;
	private LinkedList<Integer> listaAutoraId, listaAutoraIdPocetna;
	
	//Konstruktor.
	public KnjigaAzuriranje(Connection c, String[] n, Prozor p) {
		this.veza = c;
		this.vrijednostiKolona = n;
		this.p = p;
		
		dodijeliVrijednostiPoljima(vrijednostiKolona);
		
		setTitle("Obrazac za azuriranje zapisa u bazi podataka");
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int)(d.getWidth()*0.3), (int)(d.getHeight()*0.65));
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		osl = new Akcija();
		osl2 = new Taster();
		
		listaAutoraId = new LinkedList<Integer>();
		listaAutoraIdPocetna = new LinkedList<Integer>();
		
		lblNaslov = new JLabel("Naslov:");
		lblAutor = new JLabel("Autori:");
		lblOblast = new JLabel("Oblast:");
		lblGodina = new JLabel("Godina:"); //Voditi racuna o duzini teksta.
		lblOpis = new JLabel("Opis:");
		
		//Naslov
		txtNaslov = new JTextField(25);
		txtNaslov.setText(kolNaslov);
		//Oblast.
		oblastNaziv = nadjiVrijednost(kolOblastId, "OBLAST");
		//Godina.
		txtGodina = new JTextField(25);
		if (kolGodina.equals("5000")) kolGodina = "nepoznata";
		txtGodina.setText(kolGodina);
		//Opis.
		txaOpis = new JTextArea(5, 25);
		txaOpis.setLineWrap(true);
		txaOpis.setWrapStyleWord(true);
		txaOpis.setText(kolOpis);
		opisKlizac = new JScrollPane(txaOpis);
		//Broj strana.
		txtBrStrana = new JTextField(25);
		if (kolBrStrana.equals("0")) kolBrStrana = "";
		txtBrStrana.setText(kolBrStrana);
		//Izdavac.
		izdavacNaziv = nadjiVrijednost(kolIzdavacId, "IZDAVAC");
		//Slika.
		txtNaslovnica = new JTextField(25);
		txtNaslovnica.setText(kolSlika);
		txtNaslovnica.setEditable(false);
		//Autori.
		dlmAutor = new DefaultListModel();
		listaAutor = new JList(dlmAutor);
		listaAutor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaAutor.setLayoutOrientation(JList.VERTICAL);
		listaKlizac = new JScrollPane(listaAutor);
		listaKlizac.setPreferredSize(new Dimension(278, 50));
		popuniListuAutora(kolId);
		
		btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(osl);
		btnUkloni = new JButton("Ukloni");
		btnUkloni.addActionListener(osl);
		
		//Izbor putanje do slike.
		btnPutanja = new JButton("Izaberi");
		btnPutanja.addActionListener(osl);
		btnObrisiPutanju = new JButton("Obrisi");
		btnObrisiPutanju.addActionListener(osl);
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 5));
		p1.add(txtNaslovnica);
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		p2.add(btnPutanja);
		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		p3.add(btnObrisiPutanju);
		panSlika = new JPanel();
		panSlika.setLayout(new BorderLayout());
		panSlika.add(p1, BorderLayout.NORTH);
		panSlika.add(p2, BorderLayout.WEST);
		panSlika.add(p3, BorderLayout.CENTER);
		
		//Dugmad - Azuriraj, Ukloni, Odustani.
		btnAzuriraj = new JButton("Azuriraj");
		btnAzuriraj.addActionListener(osl);
		btnUkloniKnjigu = new JButton("Ukloni");
		btnUkloniKnjigu.addActionListener(osl);
		btnOdustani = new JButton("Odustani");
		btnOdustani.addActionListener(osl);
		
		dgmPanel2 = new JPanel();
		dgmPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
		dgmPanel2.add(btnAzuriraj);
		dgmPanel2.add(btnOdustani);
		dgmPanel2.add(btnUkloniKnjigu);
		
		jcbAutori = new JComboBox<String>();
		jcbAutori.setEditable(true);
		jcbAutori.setMaximumRowCount(10);
		jcbAutori.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		jcbAutori.getEditor().getEditorComponent().addKeyListener(osl2);
		unesiSvePadajucaLista("AUTOR");
		
		jcbIzdavac = new JComboBox<String>();
		jcbIzdavac.setEditable(true);
		jcbIzdavac.setMaximumRowCount(10);
		jcbIzdavac.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		jcbIzdavac.getEditor().getEditorComponent().addKeyListener(osl2);
		unesiSvePadajucaLista("IZDAVAC");
		
		panIzdavac = new JPanel();
		panIzdavac.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panIzdavac.add(jcbIzdavac);
		
		jcbOblast = new JComboBox<String>();
		jcbOblast.setEditable(true);
		jcbOblast.setMaximumRowCount(10);
		jcbOblast.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		jcbOblast.getEditor().getEditorComponent().addKeyListener(osl2);
		unesiSvePadajucaLista("OBLAST");
		
		panOblast = new JPanel();
		panOblast.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panOblast.add(jcbOblast);
		
		autoriPanel = new JPanel();
		autoriPanel.setLayout(new BorderLayout(5, 5));
		autoriPanel.add(jcbAutori, BorderLayout.NORTH);
		autoriPanel.add(listaKlizac, BorderLayout.CENTER);
		dgmPanel = new JPanel();
		dgmPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		dgmPanel.add(btnDodaj);
		dgmPanel.add(btnUkloni);
		autoriPanel.add(dgmPanel, BorderLayout.SOUTH);
		
		glavniPanel = new JPanel();
		glavniPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.ipadx = 5;
		gbc.ipady = 5;
		gbc.weightx = 100;
		gbc.weighty = 100;
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		glavniPanel.add(lblNaslov, gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 0;
		glavniPanel.add(txtNaslov, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 1;
		glavniPanel.add(lblAutor, gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 1;
		glavniPanel.add(autoriPanel, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 2;
		glavniPanel.add(lblOblast, gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 2;
		glavniPanel.add(panOblast, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 3;
		glavniPanel.add(lblGodina, gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 3;
		glavniPanel.add(txtGodina, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 4;
		glavniPanel.add(lblOpis, gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 4;
		glavniPanel.add(opisKlizac, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 5;
		glavniPanel.add(new JLabel("Broj strana:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 5;
		glavniPanel.add(txtBrStrana, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 6;
		glavniPanel.add(new JLabel("Slika:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 6;
		glavniPanel.add(panSlika, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 7;
		glavniPanel.add(new JLabel("Izdavac:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 7;
		glavniPanel.add(panIzdavac, gbc);
		
		gbc.gridwidth =  2;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 8;
		glavniPanel.add(dgmPanel2, gbc);
		
		add(glavniPanel, BorderLayout.CENTER);
		
	} //Kraj konstruktora.
	
	//
	private void dodijeliVrijednostiPoljima(String[] niz) {
		kolId = niz[0];
		kolNaslov = niz[1];
		kolOblastId = niz[2];
		kolGodina = niz[3];
		kolOpis = niz[4];
		kolBrStrana = niz[5];
		kolIzdavacId = niz[6];
		kolSlika = niz[7];
	}
	
	private String nadjiVrijednost(String id, String imeTabele) {
		String naziv = "";
		String sql = "";
		PreparedStatement ps;
		try {
			if ((veza==null) || id.equals("0")) return naziv;
			if (imeTabele.equals("OBLAST")) sql = "select Naziv from OBLAST where Id=?";
			if (imeTabele.equals("IZDAVAC")) sql = "select Naziv from IZDAVAC where Id=?";
			ps = veza.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			naziv = rs.getString("Naziv");
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return naziv;
	} //Kraj metode nadjiVrijednost.
	
	//Pronalazi imena autora knjige i popunjava jlistu.
	private void popuniListuAutora(String id) {
		if (imaLiKnjigaAutora(id)>0) {
			if (veza == null) return;
			String sql;
			try {
				sql = "select Ime_prezime from AUTOR inner join NAPISAO inner join KNJIGA"
						+ " on AUTOR.Id = NAPISAO.Autor_Id"
						+ " and KNJIGA.Id = NAPISAO.Knjiga_Id"
						+ " where KNJIGA.Id=?";
				PreparedStatement ps = veza.prepareStatement(sql);
				ps.setString(1, id);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					String imeAutora = rs.getString("Ime_prezime");
					dlmAutor.addElement(imeAutora);
					listaAutoraId.add((Integer)vratiIdAutora(imeAutora));
				}
				rs.close();
				ps.close();
				
				kopirajListaAutoraId(listaAutoraId);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	} //Kraj popuniListuAutora.
	
	//Provjerava da li knjiga ima autora.
	private int imaLiKnjigaAutora(String id) {
		int rezultat = -1;
		if (veza == null) return rezultat;
		String sql;
		try {
			sql = "select count(*) from NAPISAO where Knjiga_Id=?";
			PreparedStatement ps = veza.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			rezultat = rs.getInt("count(*)");
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rezultat;
	} //Kraj imaLiKnjigaAutora.
	
	private void kopirajListaAutoraId(LinkedList<Integer> l) {
		for (Integer e : listaAutoraId) {
			listaAutoraIdPocetna.add(e);
		}
		//System.out.println(listaAutoraIdPocetna);
	}
	
	private void unesiSvePadajucaLista(String t) {
		String sql = "";
		try {
			if (veza == null) return;
			Statement stmt = veza.createStatement();
			
			if (t.equals("AUTOR")) {
				sql = "select Ime_prezime from AUTOR order by Ime_prezime";
				ResultSet rs = stmt.executeQuery(sql);
				while(rs.next()) {
					jcbAutori.addItem(rs.getString("Ime_prezime"));
				}
				jcbAutori.setSelectedItem(""); //?????????????????????????????????????????????????????????????????
			}
			else if (t.equals("IZDAVAC")) {
				int ind = 0;
				sql = "select Naziv from IZDAVAC order by Naziv";
				ResultSet rs = stmt.executeQuery(sql);
				while(rs.next()) {
					String s = rs.getString("Naziv");
					jcbIzdavac.addItem(s);
					if (s.equals(izdavacNaziv)) {
						jcbIzdavac.setSelectedItem(s);
						ind = 1;
					}
				}
				if (ind == 0) jcbIzdavac.setSelectedItem("");
				ind = 0; //Ne mora.
			}
			else if (t.equals("OBLAST")) {
				int ind = 0;
				sql = "select Naziv from OBLAST order by Naziv";
				ResultSet rs = stmt.executeQuery(sql);
				while(rs.next()) {
					String s = rs.getString("Naziv");
					jcbOblast.addItem(s);
					if (s.equals(oblastNaziv)) {
						jcbOblast.setSelectedItem(s);
						ind = 1;
					}
				}
				if (ind == 0) jcbOblast.setSelectedItem("");
				ind = 0; //Ne mora.
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} //Kraj metode unesiSvePadajucaLista.
	
	private void unesiAutorePadajucaLista() {
		String s = (String)jcbAutori.getEditor().getItem();
		if (s.equals("")) {
			jcbAutori.hidePopup();
			jcbAutori.removeAllItems();
			unesiSvePadajucaLista("AUTOR");
			jcbAutori.showPopup();
			jcbAutori.getEditor().setItem("");
			return;
		}
		try {
			if (veza == null) return;
			String sql = "select Ime_prezime from AUTOR where Ime_prezime like ?";
			PreparedStatement ps = veza.prepareStatement(sql);
			String s2 = s;
			ps.setString(1, s2+"%");
			ResultSet rs = ps.executeQuery();
			
			jcbAutori.hidePopup();
			jcbAutori.removeAllItems();
			jcbAutori.addItem(s);
			
			while(rs.next()) {
				jcbAutori.addItem(rs.getString("Ime_prezime"));
			}
			
			jcbAutori.showPopup();
			
			rs.close();
			ps.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	} //Kraj metoda unesiAutorePadajucaLista.
	
	//Metod prikazuje padajucu listu izdavaca, na osnovu unosa.
	private void unesiIzdavacePadajucaLista() {
		String s = (String)jcbIzdavac.getEditor().getItem();
		if (s.equals("")) {
			jcbIzdavac.hidePopup();
			jcbIzdavac.removeAllItems();
			unesiSvePadajucaLista("IZDAVAC");
			jcbIzdavac.showPopup();
			jcbIzdavac.getEditor().setItem("");
			return;
		}
		try {
			if (veza == null) return;
			String sql = "select Naziv from IZDAVAC where Naziv like ?";
			PreparedStatement ps = veza.prepareStatement(sql);
			String s2 = s;
			ps.setString(1, s2+"%");
			ResultSet rs = ps.executeQuery();
			
			jcbIzdavac.hidePopup();
			jcbIzdavac.removeAllItems();
			jcbIzdavac.addItem(s);
			
			while(rs.next()) {
				jcbIzdavac.addItem(rs.getString("Naziv"));
			}
			
			jcbIzdavac.showPopup();
			
			rs.close();
			ps.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	} //Kraj metoda unesiIzdavacePadajucaLista.
	
	//Metod prikazuje padajucu listu oblasti, na osnovu unosa.
	private void unesiOblastiPadajucaLista() {
		String s = (String)jcbOblast.getEditor().getItem();
		if (s.equals("")) {
			jcbOblast.hidePopup();
			jcbOblast.removeAllItems();
			unesiSvePadajucaLista("OBLAST");
			jcbOblast.showPopup();
			jcbOblast.getEditor().setItem("");
			return;
		}
		try {
			if (veza == null) return;
			String sql = "select Naziv from OBLAST where Naziv like ?";
			PreparedStatement ps = veza.prepareStatement(sql);
			String s2 = s;
			ps.setString(1, s2+"%");
			ResultSet rs = ps.executeQuery();
			
			jcbOblast.hidePopup();
			jcbOblast.removeAllItems();
			jcbOblast.addItem(s);
			
			while(rs.next()) {
				jcbOblast.addItem(rs.getString("Naziv"));
			}
			
			jcbOblast.showPopup();
			
			rs.close();
			ps.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	} //Kraj metoda unesiOblastiPadajucaLista.
	
	//Metod vraca indeks, na osnovu polja tabele.
	private int dajIndeks(String s, String t) {
		int id = -1;
		try {
			if (veza == null)
				return id;
			
			String sql = "";
			String sql2 = "";
			if (t.equals("OBLAST")) {
				sql = "select Id from OBLAST where Naziv like ?";
				sql2 = "select count(*) from OBLAST where Naziv like ?";
			}
			else if (t.equals("IZDAVAC")) {
				sql = "select Id from IZDAVAC where Naziv like ?";
				sql2 = "select count(*) from IZDAVAC where Naziv like ?";
			}
			
			PreparedStatement ps2 = veza.prepareStatement(sql2);
			ps2.setString(1, s);
			ResultSet rs2 = ps2.executeQuery();
			rs2.next();
			if (rs2.getString("count(*)").equals("0")) {
				rs2.close();
				ps2.close();
				return id;
			}
			else {
				rs2.close();
				ps2.close();
			}
			
			PreparedStatement ps = veza.prepareStatement(sql);
			s = s.trim();
			ps.setString(1, s);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				s = rs.getString("Id");
			}
			
			id = Integer.parseInt(s);
			rs.close();
			ps.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	} //Kraj metoda dajIndeks.
	
	//Metod smijesta autore iz jliste u niz.
	private String[] smjestiUNiz(JList jl) {
		String[] niz = new String[jl.getModel().getSize()];
		for (int i=0; i<jl.getModel().getSize(); i++) {
			niz[i] = (String)jl.getModel().getElementAt(i);
			//System.out.println(niz[i]);
		}
		return niz;
	} //Kraj metoda smjestiUNiz.
	
	//Metod obavlja azuriranje reda u tabeli KNJIGA.
	private void knjigaAzuriraj(String naslov, int oblId, int god, String opis, 
											int brStr, int izdavId, String slika) {
		try {
			if (veza == null) return;
			
			String sql = "update KNJIGA set Naslov=?, Oblast_Id=?, Godina=?, Opis=?, Br_strana=?, "
					+ "Izdavac_Id=?, Slika=? where id=?";
			PreparedStatement ps = veza.prepareStatement(sql);
			
			//Kolona - Naslov.
			ps.setString(1, naslov);
			//Kolona - Oblast_Id.
			if (oblId == 0) {
				ps.setNull(2, Types.INTEGER);
			}
			else {
				ps.setInt(2, oblId);
			}
			//Kolona - Godina
			//if (god == 0) {
				//ps.setNull(3, Types.INTEGER);
			//}
			//else {
				ps.setInt(3, god);
			//}
			//Kolona - Opis.
			ps.setString(4, opis);
			//Kolona - Br_strana.
			if (brStr == 0) {
				ps.setNull(5, Types.INTEGER);
			}
			else {
				ps.setInt(5, brStr);
			}
			//Kolona - Izdavac_Id.
			if (izdavId == 0) {
				ps.setNull(6, Types.INTEGER);
			}
			else {
				ps.setInt(6, izdavId);
			}
			//Kolona - Slika.
			ps.setString(7, slika);
			if (slika == null) ps.setString(7, "src/slike/nema_slike.png");
			//Kolona id.
			ps.setInt(8, Integer.parseInt(kolId));
			
			ps.executeUpdate();
			ps.close();
			
			//Ako je izabrana putanja, koja je razlicita od prvobitne.
			if (slika != null && !slika.equals(kolSlika)) {
				kopirajSliku(slika, Integer.parseInt(kolId));
			}
			
			//Ako je uklonjena putanja, a prethodno nije bila putanja do podrazumijevane slike.
			if (slika == null && !kolSlika.equals("src/slike/nema_slike.png")) {
					ukloniSliku(kolSlika);
			}
			
			//JLista autora.
			if (jeLiKaoPocetna(listaAutoraId) == false) {
				ukloniAutoreKnjizi(kolId);
				dodijeliAutoreKnjizi(kolId);
			}
			
			JOptionPane.showMessageDialog(
					null, "Knjiga je azurirana u bazi podataka!", 
								"Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	} //Kraj metoda unesiUKnjiga.
	
	//Metod kopira sliku sa navedene lokacije u odgovarajuci folder.
	//I, promijeni vrijednost kolone Slika.
	private void kopirajSliku(String putanja, int id) {
		FileInputStream fis = null; //null je bitno.
		FileOutputStream fos = null; //null je bitno.
		String formatSlike = putanja.substring(putanja.length()-3);
		try {
			fis = new FileInputStream(putanja);
			fos = new FileOutputStream("src/slike/" + id + "." + formatSlike);
			byte[] buffer = new byte[1024];
	        int length;
	        while ((length = fis.read(buffer)) > 0) {
	            fos.write(buffer, 0, length);
	        }
	        azurirajCelijuTabele(id, formatSlike);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	} //Kraj metoda kopirajSliku.
	
	//Mijenja vrijednost polja kolone Slika.
	private void azurirajCelijuTabele(int id, String ext) {
		String sql = "update KNJIGA set Slika = ? where Id = ?";
        try {
			PreparedStatement ps = veza.prepareStatement(sql);
			ps.setString(1, "src/slike/" + id + "." + ext);
			ps.setInt(2, id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} //Kraj metoda azurirajCelijuTabele.
	
	//Id knjige koja ima najvecu vrijednost kolone Id 
	//(to je poslednje dodata knjiga).
	private int idPoslednjeDodateKnjige() {
		int id = -1;
		String sql = "select Id from KNJIGA order by Id desc limit 1";
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
	} //Kraj metoda idPoslednjeDodateKnjige.
	
	//Uklanja sve autore koji su bili dodijeljeni knjizi.
	private void ukloniAutoreKnjizi(String idKnjige) {
		if (veza == null) return;
		String sql;
		try {
			sql = "delete from NAPISAO where Knjiga_Id=?";
			PreparedStatement ps = veza.prepareStatement(sql);
			ps.setString(1, idKnjige);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} //Kraj ukloniAutoreKnjizi.
	
	//Dodjeljuje autore knjizi
	private void dodijeliAutoreKnjizi(String idKnjige) {
		if (veza == null) return;
		String sql;
		try {
			sql = "insert into NAPISAO values(?, ?)";
			PreparedStatement ps = veza.prepareStatement(sql);
			for (Integer e : listaAutoraId) {
				ps.setInt(1, (int)e);
				ps.setInt(2, Integer.parseInt(idKnjige));
				ps.executeUpdate();
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} //Kraj dodijeliAutoreKnjizi.
	
	private class Akcija implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object izvor = e.getSource();
			
			if (izvor == btnDodaj) {
				String imeAutora = (String)jcbAutori.getSelectedItem();
				imeAutora = imeAutora.trim();
				if (imeAutora.equals("")) {
					JOptionPane.showConfirmDialog(null, 
							"Izaberite autora!", 
							"Obavjestenje", JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				else if (imaLiAutoraLista(imeAutora)) {
					JOptionPane.showConfirmDialog(null, 
							imeAutora + " je vec u listi.", 
							"Obavjestenje", JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				int ind = imaLiAutoraBaza(imeAutora);
				// Ako je autor u bazi.
				if (ind >= 1) {
					dlmAutor.addElement(imeAutora);
					int id = vratiIdAutora(imeAutora);
					listaAutoraId.add((Integer)id);
				}
				// Ako autor nije u bazi.
				else if (ind == 0) {
					int izbor = JOptionPane.showConfirmDialog(null, 
						imeAutora + " se ne nalazi u bazi podataka.\nZelite li da ga dodate?", 
						"Obavjestenje", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					// 0 - OK
					if (izbor == 0) {
						//System.out.println("DA");
						new AutorUnos(veza, imeAutora).setVisible(true);
					}
					// 2 - CANCEL
					else if (izbor == 2) {
						//System.out.println("NE");
					}
				}
				//System.out.println(listaAutoraId);
				//System.out.println(jeLiKaoPocetna(listaAutoraId));
			}
			else if (izvor == btnUkloni) {
				if (dlmAutor.getSize()>0 && listaAutor.getSelectedValue()!=null) {
					String imeAutora = (String)listaAutor.getSelectedValue();
					dlmAutor.removeElement(imeAutora);
					int id = vratiIdAutora(imeAutora);
					listaAutoraId.remove((Integer)id);
					//System.out.println(listaAutoraId);
					//System.out.println(jeLiKaoPocetna(listaAutoraId));
				}
			}
			else if (izvor == btnAzuriraj) {
				//Naslov.
				String naslov = txtNaslov.getText().trim();
				if (naslov.equals("")) {
					JOptionPane.showMessageDialog(
							null, "Morate unijeti naslov!", "Napomena", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				else if (naslov.length() > 100) {
					JOptionPane.showMessageDialog(
							null, "Naslov(" + naslov.length() + ") ne smije biti duzi od 100 karaktera!", 
															"Napomena", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				//Opis.
				String opis = txaOpis.getText().trim();
				if (opis.length() > 2000) {
					JOptionPane.showMessageDialog(
							null, "Opis(" + opis.length() + ") ne smije biti duzi od 2000 karaktera!", 
															"Napomena", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				else if (opis.equals("")) {
					opis = null;
				}
				
				//Slika (putanja).
				String slika = txtNaslovnica.getText().trim();
				String slikaExt = "";
				if (!slika.equals("") && slika.length()>4) slikaExt = slika.substring(slika.length()-3);
				if (slika.length() > 100) {
					JOptionPane.showMessageDialog(
							null, "Putanja do slike(" + slika.length() + 
												") ne smije biti duza od 100 karaktera!", 
													"Napomena", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				else if (slika.equals("") || slika.length()<=3) {
					slika = null;
				}
				else if (!slikaExt.equals("png") && 
							!slikaExt.equals("jpg") && !slikaExt.equals("gif")) {
					JOptionPane.showMessageDialog(
							null, "Format slike mora biti PNG, JPG ili GIF!", 
													"Napomena", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				//Oblast
				String oblNaziv = (String)jcbOblast.getSelectedItem();
				if (oblNaziv.equals("")) oblNaziv = "Neodredjena";
				int oblastId = dajIndeks(oblNaziv, "OBLAST");
				if (oblastId == -1 && !oblNaziv.equals("")) {
					int odgovor = JOptionPane.showConfirmDialog(
							null, "Oblast ne postoji u bazi!\n Zelite li da je unesete?", 
										"Napomena", JOptionPane.YES_NO_OPTION);
					if (odgovor == JOptionPane.YES_OPTION) {
						new OblastUnos(veza, oblNaziv).setVisible(true);
						return;
					}
					else {
						return;
					}
				}
				else if (oblNaziv.equals("")) {
					oblastId = 0;
				}
				
				//Godina.
				String godinaString = txtGodina.getText().trim();
				if (!godinaString.matches("[1-2]\\d{3}") && !godinaString.equals("") && 
																	!godinaString.equals("nepoznata")) {
					JOptionPane.showMessageDialog(
							null, "Godina mora biti 4-cifren broj!", 
										"Napomena", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				else if (godinaString.equals("") || godinaString.equals("nepoznata")) {
					//godinaString = "0";
					godinaString = "5000";
				}
				int godina = Integer.parseInt(godinaString);
				
				//Broj strana.
				String brStranaString = txtBrStrana.getText().trim();
				if (!brStranaString.matches("\\d{2,4}") && !brStranaString.equals("")) {
					JOptionPane.showMessageDialog(
							null, "Broj strana mora sadrzati najmanje 2, a najvise 4 karaktera!" + 
										"\nSvi karakteri moraju biti cifre!", 
										"Napomena", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				else if (brStranaString.equals("")) {
					brStranaString = "0";
				}
				int brStrana = Integer.parseInt(brStranaString);
				
				//Izdavac.
				String izdNaziv = (String)jcbIzdavac.getSelectedItem();
				if (izdNaziv.equals("")) izdNaziv = "Nepoznat";
				int izdavacId = dajIndeks(izdNaziv, "IZDAVAC");
				if (izdavacId == -1 && !izdNaziv.equals("")) {
					int odgovor = JOptionPane.showConfirmDialog(
							null, "Izdavac ne postoji u bazi!\n Zelite li da ga unesete?", 
										"Napomena", JOptionPane.YES_NO_OPTION);
					if (odgovor == JOptionPane.YES_OPTION) {
						new IzdavacUnos(veza, izdNaziv).setVisible(true);
						return;
					}
					else {
						return;
					}
				}
				else if (izdNaziv.equals("")) {
					izdavacId = 0;
				}
				
				knjigaAzuriraj(naslov, oblastId, godina, opis, brStrana, izdavacId, slika);
				
				p.prikaziSveKnjige2();
				p.osvjeziTabeluUnos(kolId);
				
				KnjigaAzuriranje.this.dispose();
				
			}//Kraj izvor - btnAzuriraj.
			
			else if (izvor == btnPutanja) {
				JFileChooser jfc = new JFileChooser();
				if (jfc.showOpenDialog(KnjigaAzuriranje.this) == JFileChooser.APPROVE_OPTION) {
					String apsolutnaPutanja = jfc.getSelectedFile().getAbsolutePath();
					if (apsolutnaPutanja.matches(".*src/slike.*")) {
						JOptionPane.showMessageDialog(
								null, "Ne mozete odabrati slike iz tog foldera!", 
														"Napomena", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					else {
						txtNaslovnica.setText(apsolutnaPutanja);
					}
					//txtNaslovnica.setText(jfc.getSelectedFile().getAbsolutePath());
				}
			}
			
			else if (izvor == btnObrisiPutanju) {
				txtNaslovnica.setText("");
			}
			else if (izvor == btnOdustani) {
				KnjigaAzuriranje.this.dispose();
			}
			else if (izvor == btnUkloniKnjigu) {
				int odgovor = JOptionPane.showConfirmDialog(null, "Da li zelite da uklonite knjigu iz baze podataka?", 
						"Napomena", JOptionPane.YES_NO_OPTION);
				
				if (odgovor == JOptionPane.YES_OPTION) {
					String slikaPutanja = nadjiPutanjuDoSlike(kolId);
					ukloniKnjiguIzBaze(kolId);
					
					if (!slikaPutanja.equals("") &&
							!(slikaPutanja.substring(slikaPutanja.length()-6).equals("ke.png"))) {
						ukloniSliku(slikaPutanja);
					}
					
					JOptionPane.showMessageDialog(
							null, "Knjiga je izbrisana iz baze podataka!", 
										"Napomena", JOptionPane.INFORMATION_MESSAGE);
					
					p.prikaziSveKnjige2();
					p.osvjeziTabelu();
					
					KnjigaAzuriranje.this.dispose();
					
				}
				else {
					return; //Ne mora.
				}
			}
			
		} //Kraj actionperformed.
		
	} //Kraj klase Akcija.
	
	//Da li se ime autora nalazi u JListi.
	private boolean imaLiAutoraLista(String ime) {
		boolean rezultat = false;
		for (int i=0; i<dlmAutor.getSize(); i++) {
			if (ime.equals(dlmAutor.getElementAt(i))) {
				rezultat = true;
			}
		}
		return rezultat;
	} //Kraj imaLiAutoraLista.
	
	//Da li se ime autora nalazi u bazi podataka.
	private int imaLiAutoraBaza(String ime) {
		int ind = 0;
		String sql;
		try {
			sql = "select count(*) from AUTOR where Ime_prezime=?";
			PreparedStatement ps = veza.prepareStatement(sql);
			ps.setString(1, ime);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int rezultat = Integer.parseInt(rs.getString("count(*)"));
			if (rezultat >=1 ) ind = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ind;
	} //Kraj imaLiAutoraBaza.
	
	//Vraca ID odgovarajuceg autora.
	private int vratiIdAutora(String ime) {
		int id = -1;
		if (veza == null) return id;
		String sql;
		try {
			sql = "select Id from AUTOR where Ime_prezime=?";
			PreparedStatement ps = veza.prepareStatement(sql);
			ps.setString(1, ime);
			ResultSet rs = ps.executeQuery();
			rs.next();
			id = rs.getInt("Id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	} //Kraj vratiIdAutora.
	
	//Provjerava jesu li listaAutoraId i listaAutoraIdPocetna - jednake.
	private boolean jeLiKaoPocetna(LinkedList<Integer> lista) {
		if (listaAutoraIdPocetna.size() != listaAutoraId.size()) return false;
		for (int i=0; i<listaAutoraIdPocetna.size(); i++) {
			if (listaAutoraIdPocetna.get(i) != listaAutoraId.get(i)) return false;
		}
		return true;
	}
	
	//Vraca putanju do slike.
	private String nadjiPutanjuDoSlike(String id) {
		String putanja = "";
		if (veza == null) return putanja;
		String sql;
		
		try {
			sql = "select Slika from KNJIGA where Id = ?";
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
	
	//Uklanja zapise iz tabela KNJIGA i NAPISAO
	private void ukloniKnjiguIzBaze(String idKnjige) {
		if (veza == null) return;
		String sql1;
		String sql2;
		PreparedStatement ps1;
		PreparedStatement ps2;
		
		try {
			sql2 = "delete from NAPISAO where Knjiga_Id = ?";
			ps2 = veza.prepareStatement(sql2);
			ps2.setString(1, idKnjige);
			ps2.executeUpdate();
			ps2.close();
			
			sql1 = "delete from KNJIGA where Id = ?";
			ps1 = veza.prepareStatement(sql1);
			ps1.setString(1, idKnjige);
			ps1.executeUpdate();
			ps1.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	} //Kraj ukloniKnjiguIzBaze.
	
	private class Taster extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() != e.VK_ENTER && 
				e.getKeyCode() != e.VK_UP && 
				e.getKeyCode() != e.VK_DOWN) {
				//System.out.println(jcbAutori.getEditor().getItem());
				Object izvor = e.getSource();
				if (izvor == jcbAutori.getEditor().getEditorComponent())
				{
					unesiAutorePadajucaLista();
				}
				else if (izvor == jcbIzdavac.getEditor().getEditorComponent()) {
					unesiIzdavacePadajucaLista();
				}
				else if (izvor == jcbOblast.getEditor().getEditorComponent()) {
					unesiOblastiPadajucaLista();
				}
			}
		}
	} //Kraj klase Taster.
	
	//Brise fajl sa hard-diska.
	private void ukloniSliku(String putanja) {
		File slikaFajl = new File(putanja);
		if (slikaFajl.delete()) {
			System.out.println("Slika knjige je obrisana.");
		}
		else {
			System.out.println("Slika knjige NIJE je obrisana.");
		}
	} //Kraj ukloniSliku.
	
}
