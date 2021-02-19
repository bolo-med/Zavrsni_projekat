import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class Prozor extends JFrame {
	
	private Connection veza;
	private JMenuBar trakaSaMenijima;
	private JMenu meniPretrazi, meniUnesi, meniAzuriraj, meniObrisi;
	private JMenuItem stavkaSveKnjige, stavkaUnesiKnjigu, stavkaUnesiAutora, stavkaAzurirajKnjigu, 
						stavkaAzurirajAutora, stavkaUnesiOblast, stavkaUnesiIzdavaca, 
						stavkaAzurirajOblast, stavkaAzurirajIzdavaca, stavkaPrilagodjenaPretraga;
	private JTable tabela;
	private DefaultTableModel dtm;
	private ListSelectionModel lsm;
	private Vector<String> imenaKolona;
	private Vector<Object> podaci;
	private Vector<String> red;
	private JScrollPane jsp;
	private ActionListener osl;
	private WindowListener oslProzor;
	private JPanel tabelaPanel, prikazPanel, autoriPanel, dugmePanel;
	private JLabel lblSlika;
	private int kolonaId, kolonaOblastId, kolonaGodina, kolonaBrStrana, kolonaIzdavacId;
	private String kolonaNaslov, kolonaOpis, kolonaSlika;
	private JTextField txtNaslov, txtOblast, txtGodina, txtBrStrana, txtIzdavac;
	private JTextArea txaOpis;
	private String[] vrijednostoKolona;
	
	private JList listaAutora;
	private DefaultListModel<String> dlmListaAutora;
	private JScrollPane listaAutoraKlizac;
	
	private JButton btnAutori;
	
	public Prozor() {
		setTitle("Kucna biblioteka");
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int)(d.getWidth()*0.9), (int)(d.getHeight()*0.7));
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(1, 2));
		
		veza = KonekcijaBaza.otvoriVezu();
		
		btnAutori = new JButton("Opsirnije");
		
		lblSlika = new JLabel("", SwingConstants.CENTER);
		lblSlika.setPreferredSize(new Dimension(210, 210));
		
		JPanel slikaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		slikaPanel.add(lblSlika);
		
		txtNaslov = new JTextField(25);
		txtNaslov.setEditable(false);
		txtOblast = new JTextField(25);
		txtOblast.setEditable(false);
		txtGodina = new JTextField(25);
		txtGodina.setEditable(false);
		txtBrStrana = new JTextField(25);
		txtBrStrana.setEditable(false);
		txtIzdavac = new JTextField(25);
		txtIzdavac.setEditable(false);
		
		//Opis.
		txaOpis = new JTextArea(10, 25);
		txaOpis.setLineWrap(true);
		txaOpis.setWrapStyleWord(true);
		txaOpis.setEditable(false);
		//Lista sa autorima - JList.
		dlmListaAutora = new DefaultListModel<String>();
		listaAutora = new JList(dlmListaAutora);
		listaAutora.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaAutora.setLayoutOrientation(JList.VERTICAL);
		listaAutoraKlizac = new JScrollPane(listaAutora);
		Dimension d2 = Toolkit.getDefaultToolkit().getScreenSize();
		d2.setSize((int)d2.getWidth()*0.205, (int)d2.getHeight()*0.07);
		listaAutoraKlizac.setPreferredSize(d2);
		
		autoriPanel = new JPanel();
		dugmePanel = new JPanel();
		dugmePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 3));
		dugmePanel.add(btnAutori);
		autoriPanel.setLayout(new BorderLayout());
		autoriPanel.add(listaAutoraKlizac, BorderLayout.NORTH);
		autoriPanel.add(dugmePanel, BorderLayout.CENTER);
		
		JPanel detaljiPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.ipadx = 5;
		gbc.ipady = 5;
		gbc.weightx = 100;
		gbc.weighty = 100;
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		detaljiPanel.add(new JLabel("Naslov:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 0;
		detaljiPanel.add(txtNaslov, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 2;
		detaljiPanel.add(new JLabel("Autor(i):"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 2;
		detaljiPanel.add(autoriPanel, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 3;
		detaljiPanel.add(new JLabel("Oblast:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 3;
		detaljiPanel.add(txtOblast, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 4;
		detaljiPanel.add(new JLabel("Godina:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 4;
		detaljiPanel.add(txtGodina, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 5;
		detaljiPanel.add(new JLabel("Opis:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 5;
		detaljiPanel.add(new JScrollPane(txaOpis), gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 6;
		detaljiPanel.add(new JLabel("Br. str.:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 6;
		detaljiPanel.add(txtBrStrana, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 7;
		detaljiPanel.add(new JLabel("Izdavac:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 7;
		detaljiPanel.add(txtIzdavac, gbc);
		
		prikazPanel = new JPanel(new BorderLayout());
		prikazPanel.add(slikaPanel, BorderLayout.WEST);
		prikazPanel.add(detaljiPanel, BorderLayout.CENTER);
		
		osl = new KlikNaStavku();
		oslProzor = new OsluskivacProzor();
		
		btnAutori.addActionListener(osl);
		
		//Traka sa menijima.
		trakaSaMenijima = new JMenuBar();
		setJMenuBar(trakaSaMenijima);
		
		//Meni - Pretrazi.
		meniPretrazi = new JMenu("Pretraga");
		//Sve knjige
		stavkaSveKnjige = new JMenuItem("Sve knjige");
		stavkaSveKnjige.addActionListener(osl);
		//Odabrane knjige
		stavkaPrilagodjenaPretraga = new JMenuItem("Prilagodi pretragu");
		stavkaPrilagodjenaPretraga.addActionListener(osl);
		meniPretrazi.add(stavkaSveKnjige);
		meniPretrazi.add(stavkaPrilagodjenaPretraga);
		trakaSaMenijima.add(meniPretrazi);
		
		//Meni - Unesi.
		meniUnesi = new JMenu("Unos");
		//knjiga
		stavkaUnesiKnjigu = new JMenuItem("Unesi knjigu");
		stavkaUnesiKnjigu.addActionListener(osl);
		meniUnesi.add(stavkaUnesiKnjigu);
		//autor
		stavkaUnesiAutora = new JMenuItem("Unesi autora");
		stavkaUnesiAutora.addActionListener(osl);
		meniUnesi.add(stavkaUnesiAutora);
		//oblast
		stavkaUnesiOblast = new JMenuItem("Unesi oblast");
		stavkaUnesiOblast.addActionListener(osl);
		meniUnesi.add(stavkaUnesiOblast);
		//izdavac
		stavkaUnesiIzdavaca = new JMenuItem("Unesi izdavaca");
		stavkaUnesiIzdavaca.addActionListener(osl);
		meniUnesi.add(stavkaUnesiIzdavaca);
		trakaSaMenijima.add(meniUnesi);
		
		//Meni - Azuriraj.
		meniAzuriraj = new JMenu("Azuriranje");
		//knjiga
		stavkaAzurirajKnjigu = new JMenuItem("Azuriraj knjigu");
		stavkaAzurirajKnjigu.addActionListener(osl);
		//autor
		stavkaAzurirajAutora = new JMenuItem("Azuriraj autora");
		stavkaAzurirajAutora.addActionListener(osl);
		//oblast
		stavkaAzurirajOblast = new JMenuItem("Azuriraj oblast");
		stavkaAzurirajOblast.addActionListener(osl);
		//izdavac
		stavkaAzurirajIzdavaca = new JMenuItem("Azuriraj izdavaca");
		stavkaAzurirajIzdavaca.addActionListener(osl);
		//dodavanje stavki i menija
		meniAzuriraj.add(stavkaAzurirajKnjigu);
		meniAzuriraj.add(stavkaAzurirajAutora);
		meniAzuriraj.add(stavkaAzurirajOblast);
		meniAzuriraj.add(stavkaAzurirajIzdavaca);
		trakaSaMenijima.add(meniAzuriraj);
		
		prikaziSveKnjige();
		
		tabelaPanel = new JPanel(new BorderLayout());
		tabelaPanel.add(jsp, BorderLayout.CENTER);
		tabelaPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		tabelaPanel.setBackground(Color.GRAY);
		
		add(tabelaPanel);
		add(prikazPanel);
		
		this.addWindowListener(oslProzor);
		
	} //Kraj konstruktora.
	
	//Metod prikazuje sve knjige iz tabele.
	private void prikaziSveKnjige() {
		imenaKolona = new Vector<String>();
		imenaKolona.add("ID#");
		imenaKolona.add("Naslov");
		imenaKolona.add("Oblast");
		imenaKolona.add("Godina");
		podaci = new Vector<Object>();
		
		try {
			if (veza == null) return;
			Statement stmt = veza.createStatement();
			String sql = "select Id, Naslov, Oblast_Id, Godina from KNJIGA order by Naslov";
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				red = new Vector<String>();
				red.add(rs.getString("Id"));
				red.add(rs.getString("Naslov"));
				int oblastId = rs.getInt("Oblast_Id");
				red.add(nadjiNazivOblasti(oblastId));
				String god = rs.getString("Godina");
				if (god == null || god.equals("5000")) {
					red.add("Nije unijeta.");
				}
				else {
					red.add(god + ".");
				}
				//System.out.print(rs.getInt("Id") + "\t");
				//System.out.println(rs.getString("Naslov"));
				podaci.add(red);
			}
			rs.close();
			stmt.close();
			
			tabela = new JTable(podaci, imenaKolona);
			
			tabela.setAutoCreateRowSorter(true);
			
			dtm = new DefaultTableModel(podaci, imenaKolona) {
				public boolean isCellEditable(int row, int column) {
					return false; //Sve celije tabele su onemogucene.
					//return column == 0; //Samo prva kolona se moze mijenjati.
				}
			};
			//dtm = new DefaultTableModel(podaci, imenaKolona);
			tabela.setModel(dtm);
			//dtm.fireTableDataChanged(); // osvjezi tabelu
			
			podesiDimenzijeTabele(tabela);
			podesiTabeluZaDogadjaje();
			
			jsp = new JScrollPane(tabela); //Mora unutra, da bi se vidjela imena kolona.
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	} //Kraj prikaziSveKnjige().
	
	//Podesava sirinu kolona tabele
	private void podesiDimenzijeTabele(JTable t) {
		t.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		t.getColumnModel().getColumn(0).setPreferredWidth((int)(d.getWidth()*0.03));
		t.getColumnModel().getColumn(1).setPreferredWidth((int)(d.getWidth()*0.25));
		t.getColumnModel().getColumn(2).setPreferredWidth((int)(d.getWidth()*0.084));
		t.getColumnModel().getColumn(3).setPreferredWidth((int)(d.getWidth()*0.084));
		t.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		
		//t.getSelectionModel().setSelectionInterval(0, 0);
	} //Kraj podesiDimenzijeTabele.
	
	//Podesava osobine tabele.
	//Postavlja osluskivac.
	private void podesiTabeluZaDogadjaje() {
		lsm = tabela.getSelectionModel(); //ListSelectionModel
		lsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		lsm.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				//Metod se poziva i prilikom pritiska na taster misa i, 
				//prilikom otpustanja tastera misa.
				//Pomocu getValueIsAdjusting(), se provjerava je li doslo 
				//do promjene vrijednosti. Ako jeste, izvrsavaju se linije u if-u.
				if (!e.getValueIsAdjusting()) {
					int[] odabraniRed = tabela.getSelectedRows();
					//System.out.println("Duzina niza: " + odabraniRed.length);
					//if (odabraniRed.length == 1) {
					if (tabela.getRowCount() >= 1 && odabraniRed.length == 1) {
						String poljeId = (String)tabela.getValueAt(odabraniRed[0], 0);
						//System.out.println(poljeId);
						preuzmiVrijednostiKolona(Integer.parseInt(poljeId));
					}
					
				}
			}
		});
	} //Kraj metoda podesiTabeluZaDogadjaje.
	
	//Metod smijesta vrijednosti kolona odabranog reda u odgovarajuce promenljive.
	private void preuzmiVrijednostiKolona(int id) {
		if (veza == null) return;
		String sql = "select * from KNJIGA where id = ?";
		try {
			PreparedStatement ps = veza.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			//Id
			kolonaId = rs.getInt("Id");
			
			//Naslov
			kolonaNaslov = rs.getString("Naslov");
			txtNaslov.setText(kolonaNaslov);
			
			//Oblast_Id
			String kolonaOblastIdString = rs.getString("Oblast_Id");
			if (kolonaOblastIdString == null) {
				kolonaOblastId = 0;
				txtOblast.setText("Nije unijeta.");
			}
			else {
				kolonaOblastId = Integer.parseInt(kolonaOblastIdString);
				txtOblast.setText(nadjiNazivOblasti(kolonaOblastId));
			}
			
			//Godina
			String kolonaGodinaString = rs.getString("Godina");
			if (kolonaGodinaString == null || kolonaGodinaString.equals("5000")) {
				kolonaGodina = 5000;
				kolonaGodinaString = "Nije unijeta";
			}
			else {
				kolonaGodina = Integer.parseInt(kolonaGodinaString);
			}
			txtGodina.setText(kolonaGodinaString + ".");
			
			//Opis
			kolonaOpis = rs.getString("Opis");
			txaOpis.setText(kolonaOpis);
			
			//Br_strana
			String kolonaBrStranaString = rs.getString("Br_strana");
			if (kolonaBrStranaString == null) kolonaBrStrana = 0;
			else kolonaBrStrana = Integer.parseInt(kolonaBrStranaString);
			txtBrStrana.setText(kolonaBrStranaString);
			
			//Izdavac_Id
			String kolonaIzdavacIdString = rs.getString("Izdavac_Id");
			if (kolonaIzdavacIdString == null) {
				kolonaIzdavacId = 0;
				txtIzdavac.setText("Nije unijet.");
			}
			else {
				kolonaIzdavacId = Integer.parseInt(kolonaIzdavacIdString);
				txtIzdavac.setText(nadjiNazivIzdavaca(kolonaIzdavacId));
			}
			
			//Slika
			kolonaSlika = rs.getString("Slika");
			postaviSliku(kolonaSlika);
			
			//Lista autora.
			popuniListuAutora(kolonaId);
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//Metoda postavlja sliku na panel.
	private void postaviSliku(String putanja) {
		ImageIcon ii = napraviImageIcon(putanja);
		
		double sirinaSlike = ii.getIconWidth();
		double novaSirinaSlike = 1;
		double sirinaLabele = lblSlika.getWidth();
		
		double visinaSlike = ii.getIconHeight();
		double novaVisinaSlike = 1;
		double visinaLabele = lblSlika.getHeight();
		
		double odnosStranica = visinaSlike / sirinaSlike;
		
		if ((visinaSlike>=sirinaSlike) && (visinaSlike>visinaLabele)) {
			novaVisinaSlike = visinaLabele;
			novaSirinaSlike = novaVisinaSlike / odnosStranica;
		}
		else if ((sirinaSlike>=visinaSlike) && (sirinaSlike>sirinaLabele)) {
			novaSirinaSlike = sirinaLabele;
			novaVisinaSlike = novaSirinaSlike * odnosStranica;
		}
		else {
			novaVisinaSlike = visinaSlike;
			novaSirinaSlike = sirinaSlike;
		}
		lblSlika.setIcon(new ImageIcon(ii.getImage().getScaledInstance(
				(int)novaSirinaSlike, (int)novaVisinaSlike, Image.SCALE_SMOOTH)));
		//lblSlika.setIcon(new ImageIcon(ii.getImage().getScaledInstance(
		//							lblSlika.getWidth(), lblSlika.getHeight(), Image.SCALE_SMOOTH)));
	} //Kraj metode postaviSliku.
	
	//Vraca tip ImageIcon.
	private ImageIcon napraviImageIcon(String putanja) {
		if (putanja == null) putanja = "";
		//if (putanja.length() > 3) putanja = putanja.substring(3); //Ukloni src iz putanje.
		//System.out.println(putanja);
		//java.net.URL slikaURL = Prozor.class.getResource(putanja);
		//Class cls = Prozor.this.getClass();
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
		
	} //Kraj metoda napraviImageIcon.
	
	//
	private void popuniListuAutora(int idKnjige) {
		if (veza == null) return;
		String sql;
		PreparedStatement ps;
		dlmListaAutora.removeAllElements();
		try {
			sql = "select Ime_prezime from AUTOR inner join NAPISAO "
					+ "on AUTOR.Id = NAPISAO.Autor_Id where NAPISAO.Knjiga_Id = ?";
			ps = veza.prepareStatement(sql);
			ps.setInt(1, idKnjige);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dlmListaAutora.addElement(rs.getString("Ime_prezime"));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Vraca naziv oblasti, iz tabele OBLAST, na osnovu Id-a.
	private String nadjiNazivOblasti(int idOblasti) {
		String naziv = "Nije pronadjen!!!";
		if (veza == null) return naziv;
		String sql;
		try {
			sql = "select Naziv from OBLAST where id = ?";
			PreparedStatement ps = veza.prepareStatement(sql);
			ps.setInt(1, idOblasti);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				naziv = rs.getString("Naziv");
			}
			else {
				naziv = "Nije unijeta.";
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return naziv;
	} //Kraj nadjiNazivOblasti.
	
	//Vraca naziv izdavaca, iz tabele IZDAVAC, na osnovu Id-a.
	private String nadjiNazivIzdavaca(int idIzdavaca) {
		String naziv = "Nije pronadjen.";
		if (veza == null) return naziv;
		String sql;
		try {
			sql = "select Naziv from IZDAVAC where id = ?";
			PreparedStatement ps = veza.prepareStatement(sql);
			ps.setInt(1, idIzdavaca);
			ResultSet rs = ps.executeQuery();
			rs.next();
			naziv = rs.getString("Naziv");
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return naziv;
	} //Kraj nadjiNazivIzdavaca.
	
	//Metod Main().
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Prozor p = new Prozor();
				p.setVisible(true);
			}
		});
	} //Kraj Main().
	
	private class KlikNaStavku implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object izvor = e.getSource();
			
			if (izvor == stavkaUnesiKnjigu) {
				new KnjigaUnos(veza, Prozor.this).setVisible(true);
			}
			else if (izvor == stavkaUnesiAutora) {
				new AutorUnos(veza, "").setVisible(true);
			}
			else if (izvor == stavkaAzurirajKnjigu) {
				vrijednostoKolona = new String[8];
				vrijednostoKolona[0] = Integer.toString(kolonaId);
				vrijednostoKolona[1] = kolonaNaslov;
				vrijednostoKolona[2] = Integer.toString(kolonaOblastId);
				vrijednostoKolona[3] = Integer.toString(kolonaGodina);
				vrijednostoKolona[4] = kolonaOpis;
				vrijednostoKolona[5] = Integer.toString(kolonaBrStrana);
				vrijednostoKolona[6] = Integer.toString(kolonaIzdavacId);
				vrijednostoKolona[7] = kolonaSlika;
				
				new KnjigaAzuriranje(veza, vrijednostoKolona, Prozor.this).setVisible(true);
			}
			else if (izvor == btnAutori) {
				if (dlmListaAutora.size()>0 && listaAutora.getSelectedValue()!= null) {
					String idAutora = nadjiIdAutora((String)listaAutora.getSelectedValue());
					new AutorPrikaz(veza, idAutora).setVisible(true);
				}
			}
			else if (izvor == stavkaAzurirajAutora) {
				new AutorAzuriranje(veza).setVisible(true);
			}
			else if (izvor == stavkaUnesiOblast) {
				new OblastUnos(veza, "").setVisible(true);
			}
			else if (izvor == stavkaUnesiIzdavaca) {
				new IzdavacUnos(veza, "").setVisible(true);
			}
			else if (izvor == stavkaAzurirajOblast) {
				new OblastAzuriranje(veza).setVisible(true);
			}
			else if (izvor == stavkaAzurirajIzdavaca) {
				new IzdavacAzuriranje(veza).setVisible(true);
			}
			else if (izvor == stavkaSveKnjige) {
				prikaziSveKnjige2();
				osvjeziTabelu();
			}
			else if (izvor == stavkaPrilagodjenaPretraga) {
				new PrilagodjenaPretraga(veza, Prozor.this).setVisible(true);
			}
		}
	} //Kraj KlikNaStavku - ActionListener.
	
	//Vraca sve redove tabele KNJIGA.
	void prikaziSveKnjige2() {
		if (veza == null) return;
		podaci.removeAllElements();
		try {
			String sql = "select Id, Naslov, Oblast_Id, Godina from KNJIGA order by Naslov";
			Statement stmt = veza.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				red = new Vector<String>();
				red.add(rs.getString("Id"));
				red.add(rs.getString("Naslov"));
				int oblastId = rs.getInt("Oblast_Id");
				red.add(nadjiNazivOblasti(oblastId));
				String god = rs.getString("Godina");
				if (god == null || god.equals("5000")) {
					red.add("Nije unijeta.");
				}
				else {
					red.add(god + ".");
				}
				
				podaci.add(red);
			}
			rs.close();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	} //Kraj metode prikaziSveKnjige2.
	
	//Metoda osvjezava podatke u tabeli, 
	//obiljezava prvi red tabele,
	//uzima vrijednost ID-a knjige,
	//poziva metodu preuzmiVrijednostiKolona.
	void osvjeziTabelu() {
		dtm.fireTableDataChanged();
		tabela.getSelectionModel().setSelectionInterval(0, 0);
		//Prethodne dvije metode, generisu dogadjaj, pa su sledece linije visak.
		int[] odabraniRed = tabela.getSelectedRows();
		//System.out.println(tabela.getRowCount());
		if (tabela.getRowCount() > 0) {
			String poljeId = (String)tabela.getValueAt(odabraniRed[0], 0);
			preuzmiVrijednostiKolona(Integer.parseInt(poljeId));
		}
	} //Kraj metode osvjeziTabelu.
	
	//Metoda osvjezava podatke u tabeli, 
	//pronalazi red u kojem se nalazi unijeta knjiga, 
	//obiljezava red, 
	//uzima vrijednost ID-a knjige, 
	//poziva metodu preuzmiVrijednostiKolona.
	void osvjeziTabeluUnos(String idKnjige) {
		dtm.fireTableDataChanged();
		tabela.setSelectionModel(lsm); //lsm je podesen na single selection.
		int brRedova = tabela.getRowCount();
		
		String poljeId = "-1";
		ArrayList<String> lista = new ArrayList<String>();
		for (int i = 0; i < brRedova; i++) {
			tabela.getSelectionModel().setSelectionInterval(0, i); //Prvi clan se zanemaruje, jer je single selection.
			int[] odabraniRed = tabela.getSelectedRows();
			poljeId = (String)tabela.getValueAt(odabraniRed[0], 0);
			if (poljeId.equals(idKnjige)) {
				break;
			}
			lista.add(poljeId);
		}
		preuzmiVrijednostiKolona(Integer.parseInt(poljeId));
		System.out.println(lista);
	} //Kraj metode osvjeziTabeluUnos.
	
	
	//Mijenja sadrzaj jtable, podacima dobijenim iz prilagodjene pretrage.
	void prikaziPrilagodjeno(String autor, String idOblasti, String godina, String idIzdavaca) {
		if (veza == null) return;
		podaci.removeAllElements();
		PreparedStatement ps;
		ResultSet rs;
		
		try {
			//Knjiga ima pisca.
			String sql = "select KNJIGA.Id, KNJIGA.Naslov, KNJIGA.Oblast_Id, KNJIGA.Godina "
							+ "from KNJIGA inner join NAPISAO inner join AUTOR "
							+ "where KNJIGA.Id = NAPISAO.Knjiga_Id "
							+ "and AUTOR.Id = NAPISAO.Autor_Id "
							+ "and AUTOR.Ime_prezime like ? "
							+ "and KNJIGA.Oblast_Id like ? "
							+ "and KNJIGA.Godina like ? "
							+ "and KNJIGA.Izdavac_Id like ? "
							+ "group by Naslov "
							+ "order by Naslov";
			
			//Knjiga nema pisca
			String sql2 = "select KNJIGA.Id, KNJIGA.Naslov, KNJIGA.Oblast_Id, KNJIGA.Godina "
							+ "from KNJIGA "
							+ "where (select count(*) "
									  + "from NAPISAO "
									  + "where KNJIGA.Id = NAPISAO.Knjiga_Id) = 0 "
							+ "and KNJIGA.Oblast_Id like ? "
							+ "and KNJIGA.Godina like ? "
							+ "and KNJIGA.Izdavac_Id like ? "
							+ "order by Naslov ";
			
			if (!autor.equals("Bez autora")) {
				
				ps = veza.prepareStatement(sql);
				
				//Autor.
				if (autor.equals("Svi autori")) {
					ps.setString(1, "%");
				}
				else {
					ps.setString(1, autor);
				}
				//Oblast.
				if (idOblasti.equals("Sve oblasti")) {
					ps.setString(2, "%");
				}
				else {
					ps.setString(2, idOblasti);
				}
				//Godina
				if (godina.equals("Sve godine")) {
					ps.setString(3, "%");
				}
				else {
					ps.setString(3, godina);
				}
				//Izdavac.
				if (idIzdavaca.equalsIgnoreCase("Svi izdavaci")) {
					ps.setString(4, "%");
				}
				else {
					ps.setString(4, idIzdavaca);
				}
				
			}
			else {
				ps = veza.prepareStatement(sql2);
				
				//Oblast.
				if (idOblasti.equals("Sve oblasti")) {
					ps.setString(1, "%");
				}
				else {
					ps.setString(1, idOblasti);
				}
				//Godina
				if (godina.equals("Sve godine")) {
					ps.setString(2, "%");
				}
				else {
					ps.setString(2, godina);
				}
				//Izdavac.
				if (idIzdavaca.equalsIgnoreCase("Svi izdavaci")) {
					ps.setString(3, "%");
				}
				else {
					ps.setString(3, idIzdavaca);
				}
			}
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				red = new Vector<String>();
				red.add(rs.getString("Id"));
				red.add(rs.getString("Naslov"));
				int oblastId = rs.getInt("Oblast_Id");
				red.add(nadjiNazivOblasti(oblastId));
				String g = rs.getString("Godina");
				if (g.equals("5000")) {
					red.add("Nije unesena.");
				}
				else {
					red.add(g + ".");
				}
				//System.out.print(rs.getInt("Id") + "\t");
				//System.out.println(rs.getString("Naslov"));
				podaci.add(red);
			}
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (podaci.isEmpty()) {
			//System.out.println("nema redova");
			txtNaslov.setText("");
			dlmListaAutora.clear();
			txtOblast.setText("");
			txtGodina.setText("");
			txaOpis.setText("");
			txtIzdavac.setText("");
			postaviSliku("src/slike/nema_slike.png");
		}
		
		//dtm.fireTableDataChanged();
		
	} //Kraj metode prikaziPrilagodjeno.
	
	//Vraca ID autora, iz tabele AUTOR, na osnovu imena.
	private String nadjiIdAutora(String imeAutora) {
		String id = null;
		if (veza == null) return id;
		String sql;
		try {
			sql = "select Id from AUTOR where Ime_prezime = ?";
			PreparedStatement ps = veza.prepareStatement(sql);
			ps.setString(1, imeAutora);
			ResultSet rs = ps.executeQuery();
			rs.next();
			id = rs.getString("Id");
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	} //Kraj nadjiIdAutora.
	
	//Klasa osluskuvaca prozora
	private class OsluskivacProzor extends WindowAdapter {
		
		@Override
		public void windowClosing(WindowEvent e) {
			KonekcijaBaza.zatvoriVezu(veza);
		}
		
		//Da se prikazu podaci o knjizi po otvaranju aplikacije.
		@Override
		public void windowOpened(WindowEvent e) {
			tabela.getSelectionModel().setSelectionInterval(0, 0); //Problem ako nema knjiga. Potrebno dodati if.
			int[] odabraniRed = tabela.getSelectedRows();
			String poljeId = (String)tabela.getValueAt(odabraniRed[0], 0);
			//System.out.println(poljeId);
			preuzmiVrijednostiKolona(Integer.parseInt(poljeId));
		}
		
	} //Kraj klase osluskivaca prozora
	
} //Kraj klase Prozor.
