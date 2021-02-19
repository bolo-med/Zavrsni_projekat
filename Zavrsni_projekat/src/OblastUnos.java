import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class OblastUnos extends JFrame {
	private Connection veza;
	private JPanel panGlavni, panDugmad;
	private JTextField txtNaziv;
	private JButton btnUnesi, btnOdustani;
	private String nazivOblasti;
	
	public OblastUnos(Connection c, String nazivOblasti) {
		veza = c;
		this.nazivOblasti = nazivOblasti;
		postaviKomponenteRaspored();
	}
	
	//Postavlja komponente grafickog interfejsa.
	private void postaviKomponenteRaspored() {
		setTitle("Obrazac za unos oblasti u bazu");
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int)(d.getWidth()*0.3), (int)(d.getHeight()*0.2));
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		ActionListener osl = new Osluskivac();
		
		txtNaziv = new JTextField(25);
		txtNaziv.setText(nazivOblasti);
		
		btnUnesi = new JButton("Unesi");
		btnUnesi.addActionListener(osl);
		btnOdustani = new JButton("Odustani");
		btnOdustani.addActionListener(osl);
		
		panDugmad = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
		panDugmad.add(btnUnesi);
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
		panGlavni.add(new JLabel("Naziv:"), gbc);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.gridy = 0;
		panGlavni.add(txtNaziv, gbc);
		
		gbc.gridwidth =  2;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 1;
		panGlavni.add(panDugmad, gbc);
		
		add(panGlavni, BorderLayout.CENTER);
		
	} //Kraj postaviKomponenteRaspored.
	
	//Klasa osluskivaca.
	private class Osluskivac implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object izvor = e.getSource();
			
			if (izvor == btnOdustani) {
				OblastUnos.this.dispose();
			}
			else if (izvor == btnUnesi) {
				String naziv = txtNaziv.getText().trim();
				if (jeLiIspravanUnos(naziv)) {
					unesiNazivUBazu(naziv);
				}
			}
		}
		
	} //Kraj klase osluskivaca.
	
	//Provjerava je li unos ispravan
	private boolean jeLiIspravanUnos(String naziv) {
		if (naziv.equals("")) {
			JOptionPane.showMessageDialog(
					null, "Morate unijeti naziv oblasti!", "Napomena", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		else if (naziv.length()>20) {
			JOptionPane.showMessageDialog(
					null, "Naziv oblasti ne moze sadrzati vise og 20 karaktera!", "Napomena", 
							JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		else if (postojiLiUBazi(naziv)) {
			JOptionPane.showMessageDialog(
					null, "Naziv oblasti vec postoji u bazi podataka!", "Napomena", 
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
			sql = "select count(*) from OBLAST where Naziv = ?";
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
	
	//Unosi zapis u tabelu OBLAST.
	private void unesiNazivUBazu(String naziv) {
		if (veza == null) return;
		String sql;
		PreparedStatement ps;
		
		try {
			sql = "insert into OBLAST (Naziv) values (?)";
			ps = veza.prepareStatement(sql);
			ps.setString(1, naziv);
			ps.executeUpdate();
			ps.close();
			
			JOptionPane.showMessageDialog(
					null, "Nova oblast je dodata u bazu podataka!", "Napomena", 
							JOptionPane.INFORMATION_MESSAGE);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		OblastUnos.this.dispose();
		
	} //Kraj metode unesiNazivUBazu.
	
} //Kraj klase OblasUnos.
