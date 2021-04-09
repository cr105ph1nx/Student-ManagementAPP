import java.sql.*;
import javax.swing.*;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import java.awt.Color;

public class Etudiant {

	private JFrame frmBaseDeDonnees;
	
	private final JPanel panel = new JPanel();
	
	private JTextField matricule_etu;
	private JTextField idfield;
	private JTextField surnamefield;
	private JTextField namefield;
	private JTextField birthdayfield;
	private JTextField adressefield;
	private JTable table;
	
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	DefaultTableModel model;
	/**
	 * Launch the application.
	 */

	public void updateTable(String matricule_etu, Boolean reset) {
		con = JDBCConnection.ConnectDb();
		if(con != null) {
			String sql;
			if(matricule_etu.equals("*")) {
				sql = "select * from Etudiant";
			}
				
			else
				sql = "select * from Etudiant where matricule_etu='" + matricule_etu + "'";
				
			try {
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[5];
				
				if(reset) {
					int size = model.getRowCount()-1;
					for(int i=size; i>=0; i--) {
						model.removeRow(i);
					}
				}
		
				while(rs.next()) {
					columnData[0] = rs.getString("matricule_etu");
					columnData[1] = rs.getString("nom_etu");
					columnData[2] = rs.getString("prenom_etu");
					columnData[3] = rs.getString("date_naissance");
					columnData[4] = rs.getString("adresse");
					model.addRow(columnData);
				}
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Etudiant window = new Etudiant();
					window.frmBaseDeDonnees.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Etudiant() {
		initialize();
		con = JDBCConnection.ConnectDb();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBaseDeDonnees = new JFrame();
		frmBaseDeDonnees.setTitle("Student Data Base");
		frmBaseDeDonnees.getContentPane().setBackground(SystemColor.window);
		frmBaseDeDonnees.getContentPane().setLayout(null);
		panel.setBackground(SystemColor.window);
		panel.setBounds(0, 0, 664, 470);
		frmBaseDeDonnees.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton resetInsertBtn = new JButton("");
		resetInsertBtn.setIcon(new ImageIcon(Etudiant.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		resetInsertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Restore fields
				idfield.setText("");
				surnamefield.setText("");
				namefield.setText("");
				birthdayfield.setText("");
				adressefield.setText("");
				idfield.enable();
			}
		});
		resetInsertBtn.setBackground(Color.WHITE);
		resetInsertBtn.setBounds(22, 141, 28, 24);
		panel.add(resetInsertBtn);
		
		JLabel lblNewLabel = new JLabel("Student ID");
		lblNewLabel.setBounds(52, 45, 77, 21);
		panel.add(lblNewLabel);
		
		matricule_etu = new JTextField();
		matricule_etu.setBounds(118, 45, 125, 21);
		panel.add(matricule_etu);
		matricule_etu.setColumns(10);
		
		JButton searchBtn = new JButton("Search");
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTable(matricule_etu.getText(), true);
			}
		});
		searchBtn.setBounds(99, 87, 84, 29);
		panel.add(searchBtn);
		
		JLabel lblNewLabel_1 = new JLabel("Student ID");
		lblNewLabel_1.setBounds(40, 169, 77, 21);
		panel.add(lblNewLabel_1);
		
		idfield = new JTextField();
		idfield.setColumns(10);
		idfield.setBounds(106, 169, 125, 21);
		panel.add(idfield);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(40, 201, 77, 21);
		panel.add(lblSurname);
		
		surnamefield = new JTextField();
		surnamefield.setColumns(10);
		surnamefield.setBounds(106, 201, 125, 21);
		panel.add(surnamefield);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(40, 235, 77, 21);
		panel.add(lblName);
		
		namefield = new JTextField();
		namefield.setColumns(10);
		namefield.setBounds(106, 235, 125, 21);
		panel.add(namefield);
		
		JLabel lblBirthday = new JLabel("Birthday");
		lblBirthday.setBounds(40, 267, 77, 21);
		panel.add(lblBirthday);
		
		birthdayfield = new JTextField();
		birthdayfield.setColumns(10);
		birthdayfield.setBounds(106, 267, 125, 21);
		panel.add(birthdayfield);
		
		JLabel lblAdresse = new JLabel("Adresse");
		lblAdresse.setBounds(40, 299, 77, 21);
		panel.add(lblAdresse);
		
		adressefield = new JTextField();
		adressefield.setColumns(10);
		adressefield.setBounds(106, 299, 125, 21);
		panel.add(adressefield);
		
		JButton updateBtn = new JButton("Update");
		updateBtn.setBounds(159, 367, 84, 29);
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = table.getSelectedRow();
				if(i >= 0) {
					int action = JOptionPane.showConfirmDialog(null, "Do you really want to update this record?", "Update Record Confirmation", JOptionPane.YES_NO_OPTION);
					if(action == 0) {
						Object[] row = new Object[5];
						row[0] = idfield.getText();
						row[1] = surnamefield.getText();
						row[2] = namefield.getText();
						row[3] = birthdayfield.getText();
						row[4] = adressefield.getText();
						
						String sql = 
								"UPDATE Etudiant\n"
								+ "SET nom_etu = '" + row[1] + "', prenom_etu = '" + row[2] + "', date_naissance = '" + row[3] + "', adresse = '" + row[4] + "'\n"
								+ "WHERE matricule_etu = "+ row[0];
						
						try {
							pst = con.prepareStatement(sql);
							pst.execute();
							rs.close();
							pst.close();
							
							JOptionPane.showMessageDialog(null, "Updated Succesfully!");
							updateTable("*", true);
							
						}catch(Exception e1) {
							JOptionPane.showMessageDialog(null, "There seems to have been a problem...");
						}
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "Please select a record to update.");
				}
			}
		});
		panel.add(updateBtn);
		
		JButton logoutBtn = new JButton("Logout");
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Return to login menu
				Login login = new Login();
				frmBaseDeDonnees.setVisible(false);
				login.main(null);
			}
		});
		logoutBtn.setBounds(52, 410, 84, 29);
		panel.add(logoutBtn);
		
		JButton deleteBtn = new JButton("Delete");
		deleteBtn.setBounds(159, 410, 84, 29);
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = table.getSelectedRow();
				if(i >= 0) {
					int action = JOptionPane.showConfirmDialog(null, "Do you really want to delete this record?", "Delete Record Confirmation", JOptionPane.YES_NO_OPTION);
					if(action == 0) {
						String matricule_etu = table.getModel().getValueAt(i, 0).toString();
						String sql = "DELETE FROM Etudiant WHERE matricule_etu = " + matricule_etu;
						
						try {
							pst = con.prepareStatement(sql);
							pst.execute();
				
							rs.close();
							pst.close();
							
							JOptionPane.showMessageDialog(null, "Deleted Succesfully!");
							updateTable("*", true);
						}catch(Exception e1) {
							JOptionPane.showMessageDialog(null, "There seems to have been a problem...");
						}
					}

				}else {
					JOptionPane.showMessageDialog(null, "Please select a record to delete.");
				}
				
			}
		});
		panel.add(deleteBtn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(296, 45, 346, 394);
		panel.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				int i = table.getSelectedRow();
				idfield.setText((String) model.getValueAt(i, 0));
				surnamefield.setText((String) model.getValueAt(i, 1));
				namefield.setText((String) model.getValueAt(i, 2));
				birthdayfield.setText((String) model.getValueAt(i, 3));
				adressefield.setText((String) model.getValueAt(i, 4));
				
				idfield.disable();
			}
		});
		model = new DefaultTableModel();
		Object[] column = {"ID", "Surname", "Name", "Birthday", "Adresse"};
		final Object[] row = new Object[5];
		model.setColumnIdentifiers(column);
		table.setModel(model);
		table.getColumnModel().getColumn(3).setPreferredWidth(117);
		scrollPane.setViewportView(table);
		
		JButton insertBtn = new JButton("Insert");
		insertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(idfield.isEnabled()) {
					Object[] row = new Object[5];
					row[0] = idfield.getText();
					row[1] = surnamefield.getText();
					row[2] = namefield.getText();
					row[3] = birthdayfield.getText();
					row[4] = adressefield.getText();
					
					if(row[0].equals("") || row[1].equals("") || row[2].equals("") || row[3].equals("")  || row[4].equals("") )
						JOptionPane.showMessageDialog(null, "Looks like some fields are still empty.");
					else {
						try {
							String sql = "INSERT INTO Etudiant (matricule_etu, nom_etu, prenom_etu, date_naissance, adresse) VALUES (?, ?, ?, ?, ?)";
						    pst = con.prepareStatement(sql);
						    pst.setString(1, (String) row[0]);
						    pst.setString(2, (String) row[1]);
						    pst.setString(3, (String) row[2]);
						    pst.setString(4, (String) row[3]);
						    pst.setString(5, (String) row[4]);

						    pst.executeUpdate();
							JOptionPane.showMessageDialog(null, "Inserted Succesfully!");
							updateTable("*", true);
						}catch(Exception e1) {
							JOptionPane.showMessageDialog(null, "There seems to have been a problem...");
						}
					}
					// Restore fields
					idfield.setText("");
					surnamefield.setText("");
					namefield.setText("");
					birthdayfield.setText("");
					adressefield.setText("");
				}else {
					JOptionPane.showMessageDialog(null, "Please clear fields before inserting record.");
				}
			}
		});
		insertBtn.setBounds(52, 367, 84, 29);
		panel.add(insertBtn);
		
		JButton selectbtn = new JButton("");
		selectbtn.setBackground(Color.WHITE);
		selectbtn.setIcon(new ImageIcon(Etudiant.class.getResource("/com/sun/java/swing/plaf/windows/icons/DetailsView.gif")));
		selectbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateTable("*", true);
			}
		});
		selectbtn.setBounds(614, 11, 28, 24);
		panel.add(selectbtn);
		
		JButton resetBtn = new JButton("");
		resetBtn.setBackground(Color.WHITE);
		resetBtn.setIcon(new ImageIcon(Etudiant.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateTable("", true);
			}
		});
		resetBtn.setBounds(576, 11, 28, 24);
		panel.add(resetBtn);
		
		JButton idinfoBtn = new JButton("");
		idinfoBtn.setBackground(Color.WHITE);
		idinfoBtn.setIcon(new ImageIcon(Etudiant.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		idinfoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "ID MUST be between 20190000 and 20199999.");
			}
		});
		idinfoBtn.setBounds(239, 169, 22, 21);
		panel.add(idinfoBtn);
		
		JButton birthdayinfoBtn = new JButton("");
		birthdayinfoBtn.setBackground(Color.WHITE);
		birthdayinfoBtn.setIcon(new ImageIcon(Etudiant.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		birthdayinfoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Birthday MUST be in the format DD/MM/YYYY.");
			}
		});
		birthdayinfoBtn.setBounds(239, 266, 22, 21);
		panel.add(birthdayinfoBtn);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setEnabled(false);
		btnNewButton.setBounds(22, 141, 253, 204);
		panel.add(btnNewButton);
		
		frmBaseDeDonnees.setBounds(100, 100, 680, 509);
		frmBaseDeDonnees.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
