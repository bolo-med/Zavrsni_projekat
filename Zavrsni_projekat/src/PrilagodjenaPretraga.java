import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

public class PrilagodjenaPretraga extends JFrame {
	private Connection veza;
	private Prozor p;
	private JComboBox<String> jcbAutor, jcbOblast, jcbGodina, jcbIzdavac;
	//private JLabel lblAutor, lblOblast, lblGodina, lblIzdavac;
	private JPanel panGlavni, panDugmad;
	private JButton btnPretrazi, btnOdustani;
	private String autor, idOblasti, godina, idIzdavaca;
	private ArrayList<String> listaAutora, listaOblasti, listaGodina, listaIzdavaca;
	
	//Konstruktor.
	public PrilagodjenaPretraga(Connection c, Prozor p) {
		veza = c;
		this.p = p;
		setTitle("Prilagodjena pretraga");
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int)(d.getWidth()*0.35), (int)(d.getHeight()*0.5));
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		//Osluskivac.
		ActionListener osl = new Osluskivac();
		
		listaAutora = new ArrayList<String>();
		listaOblasti = new ArrayList<String>();
		listaGodina = new ArrayList<String>();
		listaIzdavaca = new ArrayList<String>();
		
		//Autor
		jcbAutor = new JComboBox<String>();
		jcbAutor.setEditable(true);
		jcbAutor.setMaximumRowCount(10);
		jcbAutor.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		unesiSveAutore();
		jcbAutor.addActionListener(osl);
		
		//Oblast
		jcbOblast = new JComboBox<String>();
		jcbOblast.setEditable(true);
		jcbOblast.setMaximumRowCount(10);
		jcbOblast.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		unesiSveOblasti();
		
		//Godina
		jcbGodina = new JComboBox<String>();
		jcbGodina.setEditable(true);
		jcbGodina.setMaximumRowCount(10);
		jcbGodina.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		unesiSveGodine();
		
		//Izdavac
		jcbIzdavac = new JComboBox<String>();
		jcbIzdavac.setEditable(true);
		jcbIzdavac.setMaximumRowCount(10);
		jcbIzdavac.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		unesiSveIzdavace();
		
		//Dugmad
		btnPretrazi = new JButton("Pretrazi");
		btnPretrazi.addActionListener(osl);
		btnOdustani = new JButton("Odustani");
		btnOdustani.addActionListener(osl);
		
		//Panel sa dugmadima.
		panDugmad = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
		panDugmad.add(btnPretrazi);
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
		panGlavni.add(new JLabel("Izaberi autora:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 0;
		panGlavni.add(jcbAutor, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 1;
		panGlavni.add(new JLabel("Izaberi oblast:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 1;
		panGlavni.add(jcbOblast, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 2;
		panGlavni.add(new JLabel("Izaberi godinu:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 2;
		panGlavni.add(jcbGodina, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 3;
		panGlavni.add(new JLabel("Izaberi izdavaca:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 3;
		panGlavni.add(jcbIzdavac, gbc);
		
		gbc.gridwidth =  2;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 4;
		panGlavni.add(panDugmad, gbc);
		
		add(panGlavni, BorderLayout.CENTER);
		
	} //Kraj konstruktora.
	
	//Unosi sve autore u jcombobox.
	private void unesiSveAutore() {
		if (veza == null) return;
		String ime;
		String sql = "";
		Statement stmt;
		
		jcbAutor.addItem("Svi autori");
		jcbAutor.addItem("Bez autora");
		listaAutora.add("Svi autori");
		listaAutora.add("Bez autora");
		
		try {
			stmt = veza.createStatement();
			sql = "select Ime_prezime from AUTOR order by Ime_prezime";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				ime = rs.getString("Ime_prezime");
				jcbAutor.addItem(ime);
				listaAutora.add(ime);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	} // Kraj metode unesiSveAutore.
	
	//Unosi sve oblasti u jcombobox.
	private void unesiSveOblasti() {
		if (veza == null) return;
		String obl;
		String sql = "";
		Statement stmt;
		
		jcbOblast.addItem("Sve oblasti");
		listaOblasti.add("Sve oblasti");
		
		try {
			stmt = veza.createStatement();
			sql = "select Naziv from OBLAST order by Naziv";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				obl = rs.getString("Naziv");
				jcbOblast.addItem(obl);
				listaOblasti.add(obl);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	} // Kraj metode unesiSveOblasti.
	
	//Unosi sve godine u jcombobox.
	private void unesiSveGodine() {
		if (veza == null) return;
		String god;
		String sql = "";
		Statement stmt;
		
		jcbGodina.addItem("Sve godine");
		listaGodina.add("Sve godine");
		
		try {
			stmt = veza.createStatement();
			sql = "select Godina from KNJIGA where Godina is not null group by Godina";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				god = rs.getString("Godina");
				if (god.equals("5000")) {
					jcbGodina.addItem("Nepoznata godina");
					listaGodina.add("Nepoznata godina");
				}
				else {
					jcbGodina.addItem(god);
					listaGodina.add(god);
				}
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	} // Kraj metode unesiSveGodine.
	
	//Unosi sve izdavace u jcombobox.
	private void unesiSveIzdavace() {
		if (veza == null) return;
		String izd;
		String sql = "";
		Statement stmt;
		
		jcbIzdavac.addItem("Svi izdavaci");
		listaIzdavaca.add("Svi izdavaci");
		
		try {
			stmt = veza.createStatement();
			sql = "select Naziv from IZDAVAC order by Naziv";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				izd = rs.getString("Naziv");
				jcbIzdavac.addItem(izd);
				listaIzdavaca.add(izd);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} //Kraj metode unesiSveIzdavace.
	
	//Klasa osluskivaca.
	private class Osluskivac implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object izvor = e.getSource();
			if (izvor == btnOdustani) {
				PrilagodjenaPretraga.this.dispose();
			}
			else if (izvor == btnPretrazi) {
				autor = (String)jcbAutor.getEditor().getItem();
				if (!jeLiValidanUnos(autor, listaAutora)) {
					JOptionPane.showMessageDialog(
							null, "Morate izabrati autora sa liste!", 
										"Napomena", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				String oblast = (String)jcbOblast.getEditor().getItem();
				if (!jeLiValidanUnos(oblast, listaOblasti)) {
					JOptionPane.showMessageDialog(
							null, "Morate izabrati oblast sa liste!", 
										"Napomena", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				//System.out.println("oblast: " + oblast);
				if (oblast.equals("Sve oblasti")) {
					idOblasti = oblast;
				}
				else {
					idOblasti = vratiIdOblasti(oblast);
				}
				
				godina = (String)jcbGodina.getEditor().getItem();
				if (!jeLiValidanUnos(godina, listaGodina)) {
					JOptionPane.showMessageDialog(
							null, "Morate izabrati godinu sa liste!", 
										"Napomena", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (godina.equals("Nepoznata godina")) godina = "5000";
				
				String izdavac = (String)jcbIzdavac.getEditor().getItem();
				if (!jeLiValidanUnos(izdavac, listaIzdavaca)) {
					JOptionPane.showMessageDialog(
							null, "Morate izabrati izdavaca sa liste!", 
										"Napomena", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				//System.out.println("izdavac: " + izdavac);
				if (izdavac.equals("Svi izdavaci")) {
					idIzdavaca = izdavac;
				}
				else {
					idIzdavaca = vratiIdIzdavaca(izdavac);
				}
				
				p.prikaziPrilagodjeno(autor, idOblasti, godina, idIzdavaca);
				p.osvjeziTabelu();
				
				//System.out.println("autor: " + autor);
				//System.out.println("idOblasti: " + idOblasti);
				//System.out.println("godina: " + godina);
				//System.out.println("idIzdavaca: " + idIzdavaca);
				
				//prikaziPrilagodjeno(autor, idOblasti, godina, idIzdavaca);
			}
		}
	} //Kraj klase osluskivaca.
	
	//Vraca ID oblasti, na osnovu naziva.
	private String vratiIdOblasti(String naziv) {
		String id = "";
		if (veza == null) return id;
		
		try {
			String sql = "select Id from OBLAST where Naziv = ?";
			PreparedStatement ps = veza.prepareStatement(sql);
			ps.setString(1, naziv);
			ResultSet rs = ps.executeQuery();
			rs.next();
			id = rs.getString("Id");
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
		
	} //kraj metode vratiIdOblasti.
	
	//Vraca ID izdavaca, na osnovu naziva.
	private String vratiIdIzdavaca(String naziv) {
		//System.out.println("naziv: " + naziv);
		String id = "";
		if (veza == null) return id;
		
		try {
			String sql = "select Id from IZDAVAC where Naziv = ?";
			PreparedStatement ps = veza.prepareStatement(sql);
			ps.setString(1, naziv);
			ResultSet rs = ps.executeQuery();
			rs.next();
			id = rs.getString("Id");
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
		
	} //Kraj metode vratiIdzdavaca.
	
	//Metoda provjerava je li korisnik odabrao stavku sa padajuce liste.
	private boolean jeLiValidanUnos(String s, ArrayList<String> arrLst) {
		for (String e : arrLst) {
			if (s.equals(e)) {
				return true;
			}
		}
		return false;
	} //Kraj jeLiValidanUnos.
	
} //Kraj klase PrilagodjenaPretraga.
