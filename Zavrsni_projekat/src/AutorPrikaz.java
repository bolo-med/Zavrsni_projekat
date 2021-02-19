import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.*;
import java.sql.*;

import javax.swing.*;


public class AutorPrikaz extends JFrame {
	private Connection veza;
	private String idAutora;
	private JLabel lblSlika;
	private JTextField txtIme;
	private JTextArea txaBiografija, txaDjela;
	private JPanel panGlavni;
	private String imeAutora, biografijaAutora, slikaAutora;
	private JButton btnZatvori;
	
	public AutorPrikaz(Connection c, String idAutora) {
		veza = c;
		this.idAutora = idAutora;
		
		setTitle("Biografija autora");
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int)(d.getWidth()*0.34), (int)(d.getHeight()*0.685));
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		ActionListener osl = new Osluskivac();
		
		txtIme = new JTextField(25);
		txtIme.setEditable(false);
		
		//Biografija.
		txaBiografija = new JTextArea(10, 25);
		txaBiografija.setLineWrap(true);
		txaBiografija.setWrapStyleWord(true);
		txaBiografija.setEditable(false);
		
		//Naslovi knjiga.
		txaDjela = new JTextArea(5, 25);
		txaDjela.setLineWrap(true);
		txaDjela.setWrapStyleWord(true);
		txaDjela.setEditable(false);
		txaDjela.setText(sveAutoroveKnjige(idAutora));
		
		//Fotografija pisca.
		lblSlika = new JLabel("");
		//lblSlika = new JLabel("", SwingConstants.CENTER);  //Vazi tek nakon zavrsetka konstruktora.
		//lblSlika.setPreferredSize(new Dimension(150, 150)); //Vazi tek nakon zavrsetka konstruktora.
		lblSlika.setSize(new Dimension(150, 150));
		
		btnZatvori = new JButton("Zatvori");
		btnZatvori.addActionListener(osl);
		JPanel panDugme = new JPanel();
		panDugme.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
		panDugme.add(btnZatvori);
		
		panGlavni = new JPanel();
		panGlavni.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.ipadx = 5;
		gbc.ipady = 5;
		gbc.weightx = 100;
		gbc.weighty = 100;
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panGlavni.add(new JLabel("Fotografija:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 0;
		panGlavni.add(lblSlika, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 1;
		panGlavni.add(new JLabel("Ime i prezime:"), gbc);
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
		panGlavni.add(new JScrollPane(txaBiografija), gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 3;
		panGlavni.add(new JLabel("Djela u biblioteci:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 3;
		panGlavni.add(new JScrollPane(txaDjela), gbc);
		
		gbc.gridwidth =  2;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 4;
		panGlavni.add(panDugme, gbc);
		
		add(panGlavni, BorderLayout.CENTER);
		
		prikupiPodatke(this.idAutora);
		
		prikaziPodatke();
		
		postaviSliku(slikaAutora);
		
	} //Kraj konstruktora.
	
	//Smijesta podatke iz reda u odgovarajuce promenljive.
	private void prikupiPodatke(String idAutora) {
		if (veza == null) return;
		String sql;
		try {
			sql = "select Ime_prezime, Biografija, Slika from AUTOR where Id = ?";
			PreparedStatement ps = veza.prepareStatement(sql);
			ps.setString(1, idAutora);
			ResultSet rs = ps.executeQuery();
			rs.next();
			imeAutora = rs.getString("Ime_prezime");
			biografijaAutora = rs.getString("Biografija");
			slikaAutora = rs.getString("Slika");
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} //Kraj prikupiPodatke.
	
	//
	private void prikaziPodatke() {
		txtIme.setText(imeAutora);
		txaBiografija.setText(biografijaAutora);
	}
	
	//Metod postavlja sliku na panel.
	private void postaviSliku(String putanjaDoSlike) {
		ImageIcon ii = napraviImageIcon(putanjaDoSlike);
		
		double sirinaSlike = ii.getIconWidth();
		double novaSirinaSlike = 10;
		double sirinaLabele = lblSlika.getWidth();
		
		double visinaSlike = ii.getIconHeight();
		double novaVisinaSlike = 10;
		double visinaLabele = lblSlika.getHeight();
		
		double odnosStranica = visinaSlike / sirinaSlike;
		
		if ((visinaSlike>=sirinaSlike) && (visinaSlike>=visinaLabele)) {
			novaVisinaSlike = visinaLabele;
			novaSirinaSlike = novaVisinaSlike / odnosStranica;
		}
		else if ((sirinaSlike>=visinaSlike) && (sirinaSlike>=sirinaLabele)) {
			novaSirinaSlike = sirinaLabele;
			novaVisinaSlike = novaSirinaSlike * odnosStranica;
		}
		else {
			novaVisinaSlike = visinaSlike;
			novaSirinaSlike = sirinaSlike;
		}
		
		/*
		System.out.println("SirinaSlike: " + sirinaSlike);
		System.out.println("VisinaSlike: " + visinaSlike);
		System.out.println("SirinaLabele: " + sirinaLabele);
		System.out.println("VisinaLabele: " + visinaLabele);
		System.out.println("novaSirinaSlike: " + novaSirinaSlike);
		System.out.println("novaVisinaSlike: " + novaVisinaSlike);
		*/
		lblSlika.setIcon(new ImageIcon(ii.getImage().getScaledInstance(
				(int)novaSirinaSlike, (int)novaVisinaSlike, Image.SCALE_SMOOTH)));
		
	} //Kraj metoda postaviSliku.
	
	//Vraca tip ImageIcon.
	private ImageIcon napraviImageIcon(String putanja) {
		if (putanja == null) putanja = "";
		//if (putanja.length() > 3) putanja = putanja.substring(3);
		//System.out.println(putanja);
		//java.net.URL slikaURL = Prozor.class.getResource(putanja);
		//Class cls = AutorPrikaz.this.getClass();
		//URL slikaURL = cls.getResource(putanja);
		URL slikaURL = null;
		try {
			slikaURL = new File(putanja).toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if (slikaURL != null) {
	           return new ImageIcon(slikaURL);
		} else {
			System.err.println("Nemoguce naci fajl: " + putanja);
			return null;
		}
	} //Kraj metode napraviImageIcon.
	
	private class Osluskivac implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object izvor = e.getSource();
			if (izvor == btnZatvori) {
				AutorPrikaz.this.dispose();
			}
		}
	} //Kraj klase-osluskivaca.
	
	//Vraca sve naslone knjiga, koje je napisao dati autor.
	private String sveAutoroveKnjige(String id) {
		String rezultat = "";
		if (veza == null) return rezultat;
		String sql;
		PreparedStatement ps;
		try {
			sql = "select Naslov from KNJIGA inner join AUTOR inner join NAPISAO "
					+ "on KNJIGA.Id = NAPISAO.Knjiga_Id "
					+ "and AUTOR.Id = NAPISAO.Autor_Id "
					+ "and AUTOR.Id = ?";
			ps = veza.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				rezultat += rs.getString("Naslov") + "\n";
			}
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rezultat;
	}
} //Kraj klase AutorPrikaz.
