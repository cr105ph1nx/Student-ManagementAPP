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
import javax.swing.text.View;
import java.awt.Component;
import java.awt.CardLayout;
import java.awt.Color;

public class BDDAdmin {

	private JFrame frmBaseDeDonnees;
	
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	DefaultTableModel modelEtu;
	DefaultTableModel modelEns;
	DefaultTableModel modelSub;
	DefaultTableModel modelSS;

	
	private JTextField matricule_etu;
	
	private JTextField idfieldEtu;
	private JTextField surnamefieldEtu;
	private JTextField namefieldEtu;
	private JTextField birthdayfieldEtu;
	private JTextField adressefieldEtu;
	private JTable tableEtu;
	
	
	private JTextField matricule_ens;
	
	private JTextField idfieldEns;
	private JTextField surnamefieldEns;
	private JTextField namefieldEns;
	private JTable tableEns;
	
	private JTextField code;
	
	private JTextField codefield;
	private JTextField labelfield;
	private JTextField nbrfield;
	private JTextField idfieldSub;
	private JTable tableSub;
	
	private JTextField matriculeSS;
	
	private JTextField idfieldSS;
	private JTextField codefieldSS;
	private JTextField note_cc;
	private JTextField note_tp;
	private JTextField note_examen;
	private JTable tableSS;
	
	/**
	 * Launch the application.
	 */
	public void updateTableEtu(String matricule_etu, Boolean reset) {
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
					int size = modelEtu.getRowCount()-1;
					for(int i=size; i>=0; i--) {
						modelEtu.removeRow(i);
					}
				}
		
				while(rs.next()) {
					columnData[0] = rs.getString("matricule_etu");
					columnData[1] = rs.getString("nom_etu");
					columnData[2] = rs.getString("prenom_etu");
					columnData[3] = rs.getString("date_naissance");
					columnData[4] = rs.getString("adresse");
					modelEtu.addRow(columnData);
				}
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	public void updateTableEns(String matricule_ens, Boolean reset) {
		con = JDBCConnection.ConnectDb();
		if(con != null) {
			String sql;
			if(matricule_ens.equals("*")) {
				sql = "select * from Enseignant";
			}
				
			else
				sql = "select * from Enseignant where matricule_ens='" + matricule_ens + "'";
				
			try {
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[3];
				
				if(reset) {
					int size = modelEns.getRowCount()-1;
					for(int i=size; i>=0; i--) {
						modelEns.removeRow(i);
					}
				}
		
				while(rs.next()) {
					columnData[0] = rs.getString("matricule_ens");
					columnData[1] = rs.getString("nom_ens");
					columnData[2] = rs.getString("prenom_ens");
					modelEns.addRow(columnData);
				}
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	public void updateTableSub(String code, Boolean reset) {
		con = JDBCConnection.ConnectDb();
		if(con != null) {
			String sql;
			if(code.equals("*")) {
				sql = "select * from Unite";
			}
				
			else
				sql = "select * from Unite where code_Unite ='" + code + "'";
				
			try {
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[4];
				
				if(reset) {
					int size = modelSub.getRowCount()-1;
					for(int i=size; i>=0; i--) {
						modelSub.removeRow(i);
					}
				}
		
				while(rs.next()) {
					columnData[0] = rs.getString("code_Unite");
					columnData[1] = rs.getString("libelle");
					columnData[2] = rs.getString("nbr_heures");
					columnData[3] = rs.getString("matricule_ens");
					modelSub.addRow(columnData);
				}
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	public void updateTableSS(String matricule_etu, Boolean reset) {
		con = JDBCConnection.ConnectDb();
		if(con != null) {
			String sql;
			if(matricule_etu.equals("*")) {
				sql = "select * from EtudiantUnite";
			}
				
			else
				sql = "select * from EtudiantUnite where matricule_etu ='" + matricule_etu + "'";
				
			try {
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[5];
				
				if(reset) {
					int size = modelSS.getRowCount()-1;
					for(int i=size; i>=0; i--) {
						modelSS.removeRow(i);
					}
				}
		
				while(rs.next()) {
					columnData[0] = rs.getString("matricule_etu");
					columnData[1] = rs.getString("code_Unite");
					columnData[2] = rs.getString("note_CC");
					columnData[3] = rs.getString("note_TP");
					columnData[4] = rs.getString("note_examen");
					modelSS.addRow(columnData);
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
					BDDAdmin window = new BDDAdmin();
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
	public BDDAdmin() {
		initialize();
		con = JDBCConnection.ConnectDb();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBaseDeDonnees = new JFrame();
		frmBaseDeDonnees.setTitle("Data Base Admin");
		frmBaseDeDonnees.getContentPane().setBackground(Color.WHITE);
		frmBaseDeDonnees.getContentPane().setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 24, 664, 536);
		frmBaseDeDonnees.getContentPane().add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		/******************************************** DEFAULT PANEL *************************************************************/
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		layeredPane.add(panel, "name_192753529472800");
		panel.setLayout(null);
		
		JButton logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(278, 349, 89, 38);
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Return to login menu
				Login login = new Login();
				frmBaseDeDonnees.setVisible(false);
				login.main(null);
			}
		});
		panel.add(logoutBtn);
		
		JButton tp6Btn = new JButton("TP6 Queries");
		tp6Btn.setBounds(275, 134, 107, 38);
		tp6Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TP6Operations ins = new TP6Operations();
				ins.main(null);
			}
		});
		panel.add(tp6Btn);
		
		JButton tp8Btn = new JButton("TP8 Queries");
		tp8Btn.setBounds(272, 240, 107, 38);
		tp8Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TP8Operations ins = new TP8Operations();
				ins.main(null);
			}
		});
		panel.add(tp8Btn);
		/******************************************** DEFAULT PANEL *************************************************************/
		
		/******************************************** STUDENT PANEL *************************************************************/
		JPanel studentPanel = new JPanel();
		studentPanel.setBackground(Color.WHITE);
		layeredPane.add(studentPanel, "name_192775631037400");
		studentPanel.setLayout(null);
		
		JButton resetInsertBtnEtu;
		resetInsertBtnEtu = new JButton("");
		resetInsertBtnEtu.setIcon(new ImageIcon(Etudiant.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		resetInsertBtnEtu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Restore fields
				idfieldEtu.setText("");
				surnamefieldEtu.setText("");
				namefieldEtu.setText("");
				birthdayfieldEtu.setText("");
				adressefieldEtu.setText("");
				idfieldEtu.enable();
			}
		});
		resetInsertBtnEtu.setBackground(Color.WHITE);
		resetInsertBtnEtu.setBounds(22, 141, 28, 24);
		studentPanel.add(resetInsertBtnEtu);
		
		JLabel lblNewLabel = new JLabel("Student ID");
		lblNewLabel.setBounds(52, 45, 77, 21);
		studentPanel.add(lblNewLabel);
		
		matricule_etu = new JTextField();
		matricule_etu.setBounds(118, 45, 125, 21);
		studentPanel.add(matricule_etu);
		matricule_etu.setColumns(10);
		
		JButton searchBtnEtu;
		searchBtnEtu = new JButton("Search");
		searchBtnEtu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTableEtu(matricule_etu.getText(), true);
			}
		});
		searchBtnEtu.setBounds(99, 87, 84, 29);
		studentPanel.add(searchBtnEtu);
		
		JLabel lblNewLabel_1 = new JLabel("Student ID");
		lblNewLabel_1.setBounds(40, 169, 77, 21);
		studentPanel.add(lblNewLabel_1);
		
		idfieldEtu = new JTextField();
		idfieldEtu.setColumns(10);
		idfieldEtu.setBounds(106, 169, 125, 21);
		studentPanel.add(idfieldEtu);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(40, 201, 77, 21);
		studentPanel.add(lblSurname);
		
		surnamefieldEtu = new JTextField();
		surnamefieldEtu.setColumns(10);
		surnamefieldEtu.setBounds(106, 201, 125, 21);
		studentPanel.add(surnamefieldEtu);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(40, 235, 77, 21);
		studentPanel.add(lblName);
		
		namefieldEtu = new JTextField();
		namefieldEtu.setColumns(10);
		namefieldEtu.setBounds(106, 235, 125, 21);
		studentPanel.add(namefieldEtu);
		
		JLabel lblBirthday = new JLabel("Birthday");
		lblBirthday.setBounds(40, 267, 77, 21);
		studentPanel.add(lblBirthday);
		
		birthdayfieldEtu = new JTextField();
		birthdayfieldEtu.setColumns(10);
		birthdayfieldEtu.setBounds(106, 267, 125, 21);
		studentPanel.add(birthdayfieldEtu);
		
		JLabel lblAdresse = new JLabel("Adresse");
		lblAdresse.setBounds(40, 299, 77, 21);
		studentPanel.add(lblAdresse);
		
		adressefieldEtu = new JTextField();
		adressefieldEtu.setColumns(10);
		adressefieldEtu.setBounds(106, 299, 125, 21);
		studentPanel.add(adressefieldEtu);
		
		JButton updateBtnEtu = new JButton("Update");
		updateBtnEtu.setBounds(159, 367, 84, 29);
		updateBtnEtu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = tableEtu.getSelectedRow();
				if(i >= 0) {
					int action = JOptionPane.showConfirmDialog(null, "Do you really want to update this record?", "Update Record Confirmation", JOptionPane.YES_NO_OPTION);
					if(action == 0) {
						Object[] row = new Object[5];
						row[0] = idfieldEtu.getText();
						row[1] = surnamefieldEtu.getText();
						row[2] = namefieldEtu.getText();
						row[3] = birthdayfieldEtu.getText();
						row[4] = adressefieldEtu.getText();
						
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
							updateTableEtu("*", true);
							
						}catch(Exception e1) {
							JOptionPane.showMessageDialog(null, "There seems to have been a problem...");
						}
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "Please select a record to update.");
				}
			}
		});
		studentPanel.add(updateBtnEtu);
		
		JButton logoutBtnEtu = new JButton("Logout");
		logoutBtnEtu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Return to login menu
				Login login = new Login();
				frmBaseDeDonnees.setVisible(false);
				login.main(null);
			}
		});
		logoutBtnEtu.setBounds(52, 410, 84, 29);
		studentPanel.add(logoutBtnEtu);
		
		JButton deleteBtnEtu = new JButton("Delete");
		deleteBtnEtu.setBounds(159, 410, 84, 29);
		deleteBtnEtu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = tableEtu.getSelectedRow();
				if(i >= 0) {
					int action = JOptionPane.showConfirmDialog(null, "Do you really want to delete this record?", "Delete Record Confirmation", JOptionPane.YES_NO_OPTION);
					if(action == 0) {
						String matricule_etu = tableEtu.getModel().getValueAt(i, 0).toString();
						String sql = "DELETE FROM Etudiant WHERE matricule_etu = " + matricule_etu;
						
						try {
							pst = con.prepareStatement(sql);
							pst.execute();
				
							rs.close();
							pst.close();
							
							JOptionPane.showMessageDialog(null, "Deleted Succesfully!");
							updateTableEtu("*", true);
						}catch(Exception e1) {
							JOptionPane.showMessageDialog(null, "There seems to have been a problem...");
						}
					}

				}else {
					JOptionPane.showMessageDialog(null, "Please select a record to delete.");
				}
				
			}
		});
		studentPanel.add(deleteBtnEtu);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(296, 45, 346, 394);
		studentPanel.add(scrollPane);
		
		tableEtu = new JTable();
		tableEtu.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				int i = tableEtu.getSelectedRow();
				idfieldEtu.setText((String) modelEtu.getValueAt(i, 0));
				surnamefieldEtu.setText((String) modelEtu.getValueAt(i, 1));
				namefieldEtu.setText((String) modelEtu.getValueAt(i, 2));
				birthdayfieldEtu.setText((String) modelEtu.getValueAt(i, 3));
				adressefieldEtu.setText((String) modelEtu.getValueAt(i, 4));
				
				idfieldEtu.disable();
			}
		});
		modelEtu = new DefaultTableModel();
		Object[] column = {"ID", "Surname", "Name", "Birthday", "Adresse"};
		final Object[] row = new Object[5];
		modelEtu.setColumnIdentifiers(column);
		tableEtu.setModel(modelEtu);
		tableEtu.getColumnModel().getColumn(3).setPreferredWidth(117);
		scrollPane.setViewportView(tableEtu);
		
		JButton insertBtnEtu = new JButton("Insert");
		insertBtnEtu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(idfieldEtu.isEnabled()) {
					Object[] row = new Object[5];
					row[0] = idfieldEtu.getText();
					row[1] = surnamefieldEtu.getText();
					row[2] = namefieldEtu.getText();
					row[3] = birthdayfieldEtu.getText();
					row[4] = adressefieldEtu.getText();
					
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
							updateTableEtu("*", true);
						}catch(Exception e1) {
							JOptionPane.showMessageDialog(null, "There seems to have been a problem...");
						}
					}
					// Restore fields
					idfieldEtu.setText("");
					surnamefieldEtu.setText("");
					namefieldEtu.setText("");
					birthdayfieldEtu.setText("");
					adressefieldEtu.setText("");
				}else {
					JOptionPane.showMessageDialog(null, "Please clear fields before inserting record.");
				}
			}
		});
		insertBtnEtu.setBounds(52, 367, 84, 29);
		studentPanel.add(insertBtnEtu);
		
		JButton selectbtnEtu = new JButton("");
		selectbtnEtu.setBackground(Color.WHITE);
		selectbtnEtu.setIcon(new ImageIcon(Etudiant.class.getResource("/com/sun/java/swing/plaf/windows/icons/DetailsView.gif")));
		selectbtnEtu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateTableEtu("*", true);
			}
		});
		selectbtnEtu.setBounds(614, 11, 28, 24);
		studentPanel.add(selectbtnEtu);
		
		JButton resetBtnEtu = new JButton("");
		resetBtnEtu.setBackground(Color.WHITE);
		resetBtnEtu.setIcon(new ImageIcon(Etudiant.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		resetBtnEtu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateTableEtu("", true);
			}
		});
		resetBtnEtu.setBounds(576, 11, 28, 24);
		studentPanel.add(resetBtnEtu);
		
		JButton idinfoBtn = new JButton("");
		idinfoBtn.setBackground(Color.WHITE);
		idinfoBtn.setIcon(new ImageIcon(Etudiant.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		idinfoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "ID MUST be between 20190000 and 20199999.");
			}
		});
		idinfoBtn.setBounds(239, 169, 22, 21);
		studentPanel.add(idinfoBtn);
		
		JButton birthdayinfoBtn = new JButton("");
		birthdayinfoBtn.setBackground(Color.WHITE);
		birthdayinfoBtn.setIcon(new ImageIcon(Etudiant.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		birthdayinfoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Birthday MUST be in the format DD/MM/YYYY.");
			}
		});
		birthdayinfoBtn.setBounds(239, 266, 22, 21);
		studentPanel.add(birthdayinfoBtn);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.setBounds(22, 141, 253, 204);
		studentPanel.add(btnNewButton_1);
		
		JButton tp6BtnEtu = new JButton("TP6 Queries");
		tp6BtnEtu.setBounds(211, 471, 107, 38);
		tp6BtnEtu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TP6Operations ins = new TP6Operations();
				ins.main(null);
			}
		});
		studentPanel.add(tp6BtnEtu);
		
		JButton tp8BtnEtu = new JButton("TP8 Queries");
		tp8BtnEtu.setBounds(345, 471, 107, 38);
		tp8BtnEtu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TP8Operations ins = new TP8Operations();
				ins.main(null);
			}
		});
		studentPanel.add(tp8BtnEtu);
		/******************************************** STUDENT PANEL *************************************************************/
		
		/******************************************** TEACHER PANEL *************************************************************/
		JPanel teacherPanel = new JPanel();
		teacherPanel.setBackground(Color.WHITE);
		layeredPane.add(teacherPanel, "name_192778598924800");
		teacherPanel.setLayout(null);
		
		JButton resetInsertBtnEns;
		resetInsertBtnEns = new JButton("");
		resetInsertBtnEns.setIcon(new ImageIcon(Enseignant.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		resetInsertBtnEns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Restore fields
				idfieldEns.setText("");
				surnamefieldEns.setText("");
				namefieldEns.setText("");
				idfieldEns.enable();
			}
		});
		resetInsertBtnEns.setBackground(Color.WHITE);
		resetInsertBtnEns.setBounds(24, 147, 28, 24);
		teacherPanel.add(resetInsertBtnEns);
		
		JLabel lblName_1 = new JLabel("Name");
		lblName_1.setBounds(41, 250, 77, 21);
		teacherPanel.add(lblName_1);
		
		JLabel lblSurname_1 = new JLabel("Surname");
		lblSurname_1.setBounds(41, 216, 77, 21);
		teacherPanel.add(lblSurname_1);
		
		JLabel lblNewLabel_2 = new JLabel("Teacher ID");
		lblNewLabel_2.setBounds(41, 184, 77, 21);
		teacherPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabe2 = new JLabel("Teacher ID");
		lblNewLabe2.setBounds(41, 45, 77, 21);
		teacherPanel.add(lblNewLabe2);
		
		matricule_ens = new JTextField();
		matricule_ens.setBounds(107, 45, 125, 21);
		teacherPanel.add(matricule_ens);
		matricule_ens.setColumns(10);
		
		JButton searchBtnEns;
		searchBtnEns = new JButton("Search");
		searchBtnEns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTableEns(matricule_ens.getText(), true);
			}
		});
		searchBtnEns.setBounds(88, 87, 84, 29);
		teacherPanel.add(searchBtnEns);
		
		idfieldEns = new JTextField();
		idfieldEns.setColumns(10);
		idfieldEns.setBounds(107, 184, 125, 21);
		teacherPanel.add(idfieldEns);
		
		surnamefieldEns = new JTextField();
		surnamefieldEns.setColumns(10);
		surnamefieldEns.setBounds(107, 216, 125, 21);
		teacherPanel.add(surnamefieldEns);
		
		namefieldEns = new JTextField();
		namefieldEns.setColumns(10);
		namefieldEns.setBounds(107, 250, 125, 21);
		teacherPanel.add(namefieldEns);
		
		JButton updateBtnEns = new JButton("Update");
		updateBtnEns.setBounds(148, 340, 84, 29);
		updateBtnEns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = tableEns.getSelectedRow();
				if(i >= 0) {
					int action = JOptionPane.showConfirmDialog(null, "Do you really want to update this record?", "Update Record Confirmation", JOptionPane.YES_NO_OPTION);
					if(action == 0) {
						Object[] row = new Object[5];
						row[0] = idfieldEns.getText();
						row[1] = surnamefieldEns.getText();
						row[2] = namefieldEns.getText();
						
						String sql = 
								"UPDATE Enseignant\n"
								+ "SET nom_ens = '" + row[1] + "', prenom_ens = '" + row[2] + "'\n"
								+ "WHERE matricule_ens = "+ row[0];
						
						try {
							pst = con.prepareStatement(sql);
							pst.execute();
							rs.close();
							pst.close();
							
							JOptionPane.showMessageDialog(null, "Updated Succesfully!");
							updateTableEns("*", true);
							
						}catch(Exception e1) {
							JOptionPane.showMessageDialog(null, "There seems to have been a problem...");
						}
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "Please select a record to update.");
				}
			}
		});
		teacherPanel.add(updateBtnEns);
		
		JButton logoutBtnEns;
		logoutBtnEns = new JButton("Logout");
		logoutBtnEns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Return to login menu
				Login login = new Login();
				frmBaseDeDonnees.setVisible(false);
				login.main(null);
			}
		});
		logoutBtnEns.setBounds(41, 383, 84, 29);
		teacherPanel.add(logoutBtnEns);
		
		JButton deleteBtnEns = new JButton("Delete");
		deleteBtnEns.setBounds(148, 383, 84, 29);
		deleteBtnEns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = tableEns.getSelectedRow();
				if(i >= 0) {
					int action = JOptionPane.showConfirmDialog(null, "Do you really want to delete this record?", "Delete Record Confirmation", JOptionPane.YES_NO_OPTION);
					if(action == 0) {
						String matricule_ens = tableEns.getModel().getValueAt(i, 0).toString();
						String sql = "DELETE FROM Enseignant WHERE matricule_ens = " + matricule_ens;
						
						try {
							pst = con.prepareStatement(sql);
							pst.execute();
				
							rs.close();
							pst.close();
							
							JOptionPane.showMessageDialog(null, "Deleted Succesfully!");
							updateTableEns("*", true);
						}catch(Exception e1) {
							JOptionPane.showMessageDialog(null, "There seems to have been a problem...");
						}
					}

				}else {
					JOptionPane.showMessageDialog(null, "Please select a record to delete.");
				}
				
			}
		});
		teacherPanel.add(deleteBtnEns);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(281, 45, 346, 367);
		teacherPanel.add(scrollPane_1);
		
		tableEns = new JTable();
		tableEns.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				int i = tableEns.getSelectedRow();
				idfieldEns.setText((String) modelEns.getValueAt(i, 0));
				surnamefieldEns.setText((String) modelEns.getValueAt(i, 1));
				namefieldEns.setText((String) modelEns.getValueAt(i, 2));

				idfieldEns.disable();
			}
		});
		modelEns = new DefaultTableModel();
		Object[] colEns = {"ID", "Surname", "Name"};
		final Object[] rowEns = new Object[3];
		modelEns.setColumnIdentifiers(colEns);
		tableEns.setModel(modelEns);
		tableEns.getColumnModel().getColumn(1).setPreferredWidth(117);
		scrollPane_1.setViewportView(tableEns);
		
		JButton insertBtnEns = new JButton("Insert");
		insertBtnEns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(idfieldEns.isEnabled()) {
					Object[] row = new Object[3];
					row[0] = idfieldEns.getText();
					row[1] = surnamefieldEns.getText();
					row[2] = namefieldEns.getText();
					
					if(row[0].equals("") || row[1].equals("") || row[2].equals("") )
						JOptionPane.showMessageDialog(null, "Looks like some fields are still empty.");
					else {
						try {
							String sql = "INSERT INTO Enseignant (matricule_ens, nom_ens, prenom_ens) VALUES (?, ?, ?)";
						    pst = con.prepareStatement(sql);
						    pst.setString(1, (String) row[0]);
						    pst.setString(2, (String) row[1]);
						    pst.setString(3, (String) row[2]);

						    pst.executeUpdate();
							JOptionPane.showMessageDialog(null, "Inserted Succesfully!");
							updateTableEns("*", true);
						}catch(Exception e1) {
							JOptionPane.showMessageDialog(null, "There seems to have been a problem...");
						}
					}
					// Restore fields
					idfieldEns.setText("");
					surnamefieldEns.setText("");
					namefieldEns.setText("");
				}else {
					JOptionPane.showMessageDialog(null, "Please clear fields before inserting record.");
				}
			}
		});
		insertBtnEns.setBounds(41, 340, 84, 29);
		teacherPanel.add(insertBtnEns);
		
		JButton selectbtnEns = new JButton("");
		selectbtnEns.setBackground(Color.WHITE);
		selectbtnEns.setIcon(new ImageIcon(Etudiant.class.getResource("/com/sun/java/swing/plaf/windows/icons/DetailsView.gif")));
		selectbtnEns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateTableEns("*", true);
			}
		});
		selectbtnEns.setBounds(599, 11, 28, 24);
		teacherPanel.add(selectbtnEns);
		
		JButton resetBtnEns = new JButton("");
		resetBtnEns.setBackground(Color.WHITE);
		resetBtnEns.setIcon(new ImageIcon(Etudiant.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		resetBtnEns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateTableEns("", true);
			}
		});
		resetBtnEns.setBounds(561, 11, 28, 24);
		teacherPanel.add(resetBtnEns);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setEnabled(false);
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setBounds(24, 147, 230, 154);
		teacherPanel.add(btnNewButton);
		
		JButton tp6BtnEns = new JButton("TP6 Queries");
		tp6BtnEns.setBounds(211, 461, 107, 38);
		tp6BtnEns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TP6Operations ins = new TP6Operations();
				ins.main(null);
			}
		});
		teacherPanel.add(tp6BtnEns);
		
		JButton tp8BtnEns = new JButton("TP8 Queries");
		tp8BtnEns.setBounds(345, 461, 107, 38);
		tp8BtnEns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TP8Operations ins = new TP8Operations();
				ins.main(null);
			}
		});
		teacherPanel.add(tp8BtnEns);
		/******************************************** TEACHER PANEL *************************************************************/
		
		/******************************************** SUBJECT PANEL *************************************************************/
		JPanel subjectPanel = new JPanel();
		subjectPanel.setBackground(Color.WHITE);
		layeredPane.add(subjectPanel, "name_192778598924800");
		subjectPanel.setLayout(null);
		
		JButton resetInsertBtnSub;
		resetInsertBtnSub = new JButton("");
		resetInsertBtnSub.setIcon(new ImageIcon(Etudiant.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		resetInsertBtnSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Restore fields
				codefield.setText("");
				labelfield.setText("");
				nbrfield.setText("");
				idfieldSub.setText("");

				codefield.enable();
			}
		});
		resetInsertBtnSub.setBackground(Color.WHITE);
		resetInsertBtnSub.setBounds(24, 147, 28, 24);
		subjectPanel.add(resetInsertBtnSub);
		
		JLabel lblName_2_1 = new JLabel("Teacher ID");
		lblName_2_1.setBounds(41, 284, 77, 21);
		subjectPanel.add(lblName_2_1);
		
	    idfieldSub = new JTextField();
		idfieldSub.setColumns(10);
		idfieldSub.setBounds(107, 284, 125, 21);
		subjectPanel.add(idfieldSub);
		
			
		JLabel lblName_2 = new JLabel("Hours");
		lblName_2.setBounds(41, 250, 77, 21);
		subjectPanel.add(lblName_2);
		
		JLabel lblSurname_2 = new JLabel("Label");
		lblSurname_2.setBounds(41, 216, 77, 21);
		subjectPanel.add(lblSurname_2);
		
		JLabel lblNewLabel_3 = new JLabel("Code");
		lblNewLabel_3.setBounds(41, 184, 77, 21);
		subjectPanel.add(lblNewLabel_3);
		
		JLabel lblNewLabe3 = new JLabel("Code");
		lblNewLabe3.setBounds(41, 45, 77, 21);
		subjectPanel.add(lblNewLabe3);
		
		code = new JTextField();
		code.setBounds(107, 45, 125, 21);
		subjectPanel.add(code);
		code.setColumns(10);
		
		JButton searchBtnSub = new JButton("Search");
		searchBtnSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTableSub(code.getText(), true);
			}
		});
		searchBtnSub.setBounds(88, 87, 84, 29);
		subjectPanel.add(searchBtnSub);
		
		codefield = new JTextField();
		codefield.setColumns(10);
		codefield.setBounds(107, 184, 125, 21);
		subjectPanel.add(codefield);
		
		labelfield = new JTextField();
		labelfield.setColumns(10);
		labelfield.setBounds(107, 216, 125, 21);
		subjectPanel.add(labelfield);
		
		nbrfield = new JTextField();
		nbrfield.setColumns(10);
		nbrfield.setBounds(107, 250, 125, 21);
		subjectPanel.add(nbrfield);
		
		JButton updateBtnSub = new JButton("Update");
		updateBtnSub.setBounds(148, 352, 84, 29);
		updateBtnSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = tableSub.getSelectedRow();
				if(i >= 0) {
					int action = JOptionPane.showConfirmDialog(null, "Do you really want to update this record?", "Update Record Confirmation", JOptionPane.YES_NO_OPTION);
					if(action == 0) {
						Object[] row = new Object[4];
						row[0] = codefield.getText();
						row[1] = labelfield.getText();
						row[2] = nbrfield.getText();
						row[3] = idfieldSub.getText();
						
						String sql = 
								"UPDATE Unite\n"
								+ "SET libelle = '" + row[1] + "', nbr_heures = '" + row[2] + "', matricule_ens = '" + row[3] + "'\n"
								+ "WHERE code_Unite = '"+ row[0] + "'";
						
						try {
							pst = con.prepareStatement(sql);
							pst.execute();
							rs.close();
							pst.close();
							
							JOptionPane.showMessageDialog(null, "Updated Succesfully!");
							updateTableSub("*", true);
							
						}catch(Exception e1) {
							JOptionPane.showMessageDialog(null, "There seems to have been a problem...");
						}
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "Please select a record to update.");
				}
			}
		});
		subjectPanel.add(updateBtnSub);
		
		JButton logoutBtnSub;
		logoutBtnSub = new JButton("Logout");
		logoutBtnSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Return to login menu
				Login login = new Login();
				frmBaseDeDonnees.setVisible(false);
				login.main(null);
			}
		});
		logoutBtnSub.setBounds(41, 395, 84, 29);
		subjectPanel.add(logoutBtnSub);
		
		JButton deleteBtnSub = new JButton("Delete");
		deleteBtnSub.setBounds(148, 395, 84, 29);
		deleteBtnSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = tableSub.getSelectedRow();
				if(i >= 0) {
					int action = JOptionPane.showConfirmDialog(null, "Do you really want to delete this record?", "Delete Record Confirmation", JOptionPane.YES_NO_OPTION);
					if(action == 0) {
						String code = tableSub.getModel().getValueAt(i, 0).toString();
						String sql = "DELETE FROM Unite WHERE code_Unite = '" + code + "'";

						try {
							pst = con.prepareStatement(sql);
							pst.execute();
				
							rs.close();
							pst.close();
							
							JOptionPane.showMessageDialog(null, "Deleted Succesfully!");
							updateTableSub("*", true);
						}catch(Exception e1) {
							JOptionPane.showMessageDialog(null, "There seems to have been a problem...");
						}
					}

				}else {
					JOptionPane.showMessageDialog(null, "Please select a record to delete.");
				}
				
			}
		});
		subjectPanel.add(deleteBtnSub);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(281, 45, 346, 367);
		subjectPanel.add(scrollPane_2);
		
		tableSub = new JTable();
		tableSub.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				int i = tableSub.getSelectedRow();
				codefield.setText((String) modelSub.getValueAt(i, 0));
				labelfield.setText((String) modelSub.getValueAt(i, 1));
				nbrfield.setText((String) modelSub.getValueAt(i, 2));
				idfieldSub.setText((String) modelSub.getValueAt(i, 3));

				codefield.disable();
			}
		});
		
		modelSub = new DefaultTableModel();
		Object[] colSub = {"Code", "Label", "Hours", "Teacher ID"};
		final Object[] rowSub = new Object[4];
		modelSub.setColumnIdentifiers(colSub);
		tableSub.setModel(modelSub);
		tableSub.getColumnModel().getColumn(1).setPreferredWidth(117);
		scrollPane_2.setViewportView(tableSub);
		
		JButton insertBtnSub = new JButton("Insert");
		insertBtnSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(codefield.isEnabled()) {
					Object[] row = new Object[4];
					row[0] = codefield.getText();
					row[1] = labelfield.getText();
					row[2] = nbrfield.getText();
					row[3] = idfieldSub.getText();
					
					if(row[0].equals("") || row[1].equals("") || row[2].equals("") || row[3].equals("") )
						JOptionPane.showMessageDialog(null, "Looks like some fields are still empty.");
					else {
						try {
							String sql = "INSERT INTO Unite (code_Unite, libelle, nbr_heures, matricule_ens) VALUES (?, ?, ?, ?)";
						    pst = con.prepareStatement(sql);
						    pst.setString(1, (String) row[0]);
						    pst.setString(2, (String) row[1]);
						    pst.setString(3, (String) row[2]);
						    pst.setString(4, (String) row[3]);

						    pst.executeUpdate();
							JOptionPane.showMessageDialog(null, "Inserted Succesfully!");
							updateTableSub("*", true);
						}catch(Exception e1) {
							JOptionPane.showMessageDialog(null, "There seems to have been a problem...");
						}
					}
					// Restore fields
					codefield.setText("");
					labelfield.setText("");
					nbrfield.setText("");
					idfieldSub.setText("");
				}else {
					JOptionPane.showMessageDialog(null, "Please clear fields before inserting record.");
				}
			}
		});
		insertBtnSub.setBounds(41, 352, 84, 29);
		subjectPanel.add(insertBtnSub);
		
		JButton selectBtnSub = new JButton("");
		selectBtnSub.setBackground(Color.WHITE);
		selectBtnSub.setIcon(new ImageIcon(Etudiant.class.getResource("/com/sun/java/swing/plaf/windows/icons/DetailsView.gif")));
		selectBtnSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateTableSub("*", true);
			}
		});
		selectBtnSub.setBounds(599, 11, 28, 24);
		subjectPanel.add(selectBtnSub);
		
		JButton resetBtnSub = new JButton("");
		resetBtnSub.setBackground(Color.WHITE);
		resetBtnSub.setIcon(new ImageIcon(Etudiant.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		resetBtnSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateTableSub("", true);
			}
		});
		resetBtnSub.setBounds(561, 11, 28, 24);
		subjectPanel.add(resetBtnSub);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setEnabled(false);
		btnNewButton_2.setBackground(Color.WHITE);
		btnNewButton_2.setBounds(24, 147, 230, 181);
		subjectPanel.add(btnNewButton_2);
		
		JButton tp6BtnSub = new JButton("TP6 Queries");
		tp6BtnSub.setBounds(211, 461, 107, 38);
		tp6BtnSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TP6Operations ins = new TP6Operations();
				ins.main(null);
			}
		});
		subjectPanel.add(tp6BtnSub);
		
		JButton tp8BtnSub = new JButton("TP8 Queries");
		tp8BtnSub.setBounds(345, 461, 107, 38);
		tp8BtnSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TP8Operations ins = new TP8Operations();
				ins.main(null);
			}
		});
		subjectPanel.add(tp8BtnSub);
		/******************************************** SUBJECT PANEL *************************************************************/
		
		/******************************************** STUDENT/SUBJECT PANEL *************************************************************/
		JPanel SSPanel = new JPanel();
		SSPanel.setBackground(Color.WHITE);
		layeredPane.add(SSPanel, "name_192778598924800");
		SSPanel.setLayout(null);
		
		JButton resetInsertBtnSS;
		resetInsertBtnSS = new JButton("");
		resetInsertBtnSS.setIcon(new ImageIcon(Etudiant.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		resetInsertBtnSS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Restore fields
				idfieldSS.setText("");
				codefieldSS.setText("");
				note_cc.setText("");
				note_tp.setText("");
				note_examen.setText("");

				idfieldSS.enable();
				codefieldSS.enable();
			}
		});
		
		JLabel lblName_2_2_1 = new JLabel("Exam");
		lblName_2_2_1.setBounds(41, 311, 77, 21);
		SSPanel.add(lblName_2_2_1);
		resetInsertBtnSS.setBackground(Color.WHITE);
		resetInsertBtnSS.setBounds(24, 141, 28, 24);
		SSPanel.add(resetInsertBtnSS);
		
		JLabel lblName_2_2 = new JLabel("TP");
		lblName_2_2.setBounds(41, 278, 77, 21);
		SSPanel.add(lblName_2_2);
		
		JLabel lblName_3 = new JLabel("CC");
		lblName_3.setBounds(41, 244, 77, 21);
		SSPanel.add(lblName_3);
		
		JLabel lblSurname_3 = new JLabel("Code");
		lblSurname_3.setBounds(41, 210, 77, 21);
		SSPanel.add(lblSurname_3);
		
		JLabel lblNewLabel_4 = new JLabel("Student ID");
		lblNewLabel_4.setBounds(41, 178, 77, 21);
		SSPanel.add(lblNewLabel_4);
		
		JLabel lblNewLabe4 = new JLabel("Student ID");
		lblNewLabe4.setBounds(41, 45, 77, 21);
		SSPanel.add(lblNewLabe4);
		
		matriculeSS = new JTextField();
		matriculeSS.setBounds(107, 45, 125, 21);
		matriculeSS.setColumns(10);
		SSPanel.add(matriculeSS);
		
		JButton searchBtnSS = new JButton("Search");
		searchBtnSS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTableSS(matriculeSS.getText(), true);
			}
		});
		searchBtnSS.setBounds(88, 87, 84, 29);
		SSPanel.add(searchBtnSS);
		
		idfieldSS = new JTextField();
		idfieldSS.setColumns(10);
		idfieldSS.setBounds(107, 178, 125, 21);
		SSPanel.add(idfieldSS);
		
		codefieldSS = new JTextField();
		codefieldSS.setColumns(10);
		codefieldSS.setBounds(107, 210, 125, 21);
		SSPanel.add(codefieldSS);
		
		note_cc = new JTextField();
		note_cc.setColumns(10);
		note_cc.setBounds(107, 244, 125, 21);
		SSPanel.add(note_cc);
		
		note_tp = new JTextField();
		note_tp.setColumns(10);
		note_tp.setBounds(107, 278, 125, 21);
		SSPanel.add(note_tp);
		
		note_examen = new JTextField();
		note_examen.setColumns(10);
		note_examen.setBounds(107, 312, 125, 21);
		SSPanel.add(note_examen);
		
		JButton updateBtnSS = new JButton("Update");
		updateBtnSS.setBounds(148, 373, 84, 29);
		updateBtnSS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = tableSS.getSelectedRow();
				if(i >= 0) {
					int action = JOptionPane.showConfirmDialog(null, "Do you really want to update this record?", "Update Record Confirmation", JOptionPane.YES_NO_OPTION);
					if(action == 0) {
						Object[] row = new Object[5];
						row[0] = idfieldSS.getText();
						row[1] = codefieldSS.getText();
						row[2] = note_cc.getText();
						row[3] = note_tp.getText();
						row[4] = note_examen.getText();
						
						String sql = 
								"UPDATE EtudiantUnite\n"
								+ "SET code_Unite = '" + row[1] + "', note_cc = '" + row[2] + "', note_tp = '" + row[3] + "', note_examen = '" + row[4] + "'\n"
								+ "WHERE matricule_etu = '"+ row[0] + "'";
						
						try {
							pst = con.prepareStatement(sql);
							pst.execute();
							rs.close();
							pst.close();
							
							JOptionPane.showMessageDialog(null, "Updated Succesfully!");
							updateTableSS("*", true);
							
						}catch(Exception e1) {
							JOptionPane.showMessageDialog(null, "There seems to have been a problem...");
						}
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "Please select a record to update.");
				}
			}
		});
		SSPanel.add(updateBtnSS);
		
		JButton logoutBtnSS;
		logoutBtnSS = new JButton("Logout");
		logoutBtnSS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Return to login menu
				Login login = new Login();
				frmBaseDeDonnees.setVisible(false);
				login.main(null);
			}
		});
		logoutBtnSS.setBounds(41, 416, 84, 29);
		SSPanel.add(logoutBtnSS);
		
		JButton deleteBtnSS = new JButton("Delete");
		deleteBtnSS.setBounds(148, 416, 84, 29);
		deleteBtnSS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = tableSS.getSelectedRow();
				if(i >= 0) {
					int action = JOptionPane.showConfirmDialog(null, "Do you really want to delete this record?", "Delete Record Confirmation", JOptionPane.YES_NO_OPTION);
					if(action == 0) {
						String matricule_etu = tableSS.getModel().getValueAt(i, 0).toString();
						String sql = "DELETE FROM EtudiantUnite WHERE matricule_etu = '" + matricule_etu + "'";
						
						try {
							pst = con.prepareStatement(sql);
							pst.execute();
				
							rs.close();
							pst.close();
							
							JOptionPane.showMessageDialog(null, "Deleted Succesfully!");
							updateTableSS("*", true);
						}catch(Exception e1) {
							JOptionPane.showMessageDialog(null, "There seems to have been a problem...");
						}
					}

				}else {
					JOptionPane.showMessageDialog(null, "Please select a record to delete.");
				}
				
			}
		});
		SSPanel.add(deleteBtnSS);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(281, 45, 346, 367);
		SSPanel.add(scrollPane_3);
		
		tableSS = new JTable();
		tableSS.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				int i = tableSS.getSelectedRow();
				
				idfieldSS.setText((String) modelSS.getValueAt(i, 0));
				codefieldSS.setText((String) modelSS.getValueAt(i, 1));
				note_cc.setText((String) modelSS.getValueAt(i, 2));
				note_tp.setText((String) modelSS.getValueAt(i, 3));
				note_examen.setText((String) modelSS.getValueAt(i, 4));

				idfieldSS.disable();
				codefieldSS.disable();
			}
		});
		
		modelSS = new DefaultTableModel();
		Object[] colSS = {"Student ID", "Code", "CC", "TP", "Exam"};
		final Object[] rowSS = new Object[5];
		modelSS.setColumnIdentifiers(colSS);
		tableSS.setModel(modelSS);
		tableSS.getColumnModel().getColumn(1).setPreferredWidth(117);
		scrollPane_3.setViewportView(tableSS);
		
		JButton insertBtnSS = new JButton("Insert");
		insertBtnSS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(idfieldSS.isEnabled()) {
					Object[] row = new Object[5];
					row[0] = idfieldSS.getText();
					row[1] = codefieldSS.getText();
					row[2] = note_cc.getText();
					row[3] = note_tp.getText();
					row[4] = note_examen.getText();
					
					if(row[0].equals("") || row[1].equals("") || row[2].equals("") || row[3].equals("") || row[4].equals(""))
						JOptionPane.showMessageDialog(null, "Looks like some fields are still empty.");
					else {
						try {
							String sql = "INSERT INTO EtudiantUnite (matricule_etu, code_Unite, note_cc, note_tp, note_examen) VALUES (?, ?, ?, ?, ?)";
						    pst = con.prepareStatement(sql);
						    pst.setString(1, (String) row[0]);
						    pst.setString(2, (String) row[1]);
						    pst.setString(3, (String) row[2]);
						    pst.setString(4, (String) row[3]);
						    pst.setString(5, (String) row[4]);

						    pst.executeUpdate();
							JOptionPane.showMessageDialog(null, "Inserted Succesfully!");
							updateTableSS("*", true);
						}catch(Exception e1) {
							JOptionPane.showMessageDialog(null, "There seems to have been a problem...");
						}
					}
					// Restore fields
					idfieldSS.setText("");
					codefieldSS.setText("");
					note_cc.setText("");
					note_tp.setText("");
					note_examen.setText("");
				}else {
					JOptionPane.showMessageDialog(null, "Please clear fields before inserting record.");
				}
			}
		});
		insertBtnSS.setBounds(41, 373, 84, 29);
		SSPanel.add(insertBtnSS);
		
		JButton selectBtnSS = new JButton("");
		selectBtnSS.setBackground(Color.WHITE);
		selectBtnSS.setIcon(new ImageIcon(Etudiant.class.getResource("/com/sun/java/swing/plaf/windows/icons/DetailsView.gif")));
		selectBtnSS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateTableSS("*", true);
			}
		});
		selectBtnSS.setBounds(599, 11, 28, 24);
		SSPanel.add(selectBtnSS);
		
		JButton resetBtnSS = new JButton("");
		resetBtnSS.setBackground(Color.WHITE);
		resetBtnSS.setIcon(new ImageIcon(Etudiant.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		resetBtnSS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateTableSS("", true);
			}
		});
		resetBtnSS.setBounds(561, 11, 28, 24);
		SSPanel.add(resetBtnSS);
		
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.setEnabled(false);
		btnNewButton_3.setBackground(Color.WHITE);
		btnNewButton_3.setBounds(24, 141, 230, 208);
		SSPanel.add(btnNewButton_3);
		
		JButton tp6BtnSS = new JButton("TP6 Queries");
		tp6BtnSS.setBounds(211, 469, 107, 38);
		tp6BtnSS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TP6Operations ins = new TP6Operations();
				ins.main(null);
			}
		});
		SSPanel.add(tp6BtnSS);
		
		JButton tp8BtnSS = new JButton("TP8 Queries");
		tp8BtnSS.setBounds(345, 469, 107, 38);
		tp8BtnSS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TP8Operations ins = new TP8Operations();
				ins.main(null);
			}
		});
		SSPanel.add(tp8BtnSS);
		/******************************************** SUBJECT PANEL *************************************************************/
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Select Data Base", "Student Data Base", "Teacher Data Base", "Subject Data Base", "Student/Subject Data Base"}));
		comboBox.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		        String db = (String) comboBox.getItemAt(comboBox.getSelectedIndex());
		        frmBaseDeDonnees.setBounds(100, 100, 446, 425);

		        if(db.equals("Student Data Base")) {
		        	frmBaseDeDonnees.setBounds(100, 100, 680, 599);	
		        	layeredPane.removeAll();
		    		layeredPane.add(studentPanel);
		    		layeredPane.repaint();
		    		layeredPane.revalidate();
		    		
		        }else if(db.equals("Teacher Data Base")) {
		        	frmBaseDeDonnees.setBounds(100, 100, 680, 599);	
		        	layeredPane.removeAll();
		    		layeredPane.add(teacherPanel);
		    		layeredPane.repaint();
		    		layeredPane.revalidate();    		
		        }else if(db.equals("Subject Data Base")) {
		        	frmBaseDeDonnees.setBounds(100, 100, 680, 599);	
		        	layeredPane.removeAll();
		    		layeredPane.add(subjectPanel);
		    		layeredPane.repaint();
		    		layeredPane.revalidate();    
		        }else if(db.equals("Student/Subject Data Base")) {
		        	frmBaseDeDonnees.setBounds(100, 100, 680, 599);	
		        	layeredPane.removeAll();
		    		layeredPane.add(SSPanel);
		    		layeredPane.repaint();
		    		layeredPane.revalidate();    
		        }else {
		        	frmBaseDeDonnees.setBounds(100, 100, 680, 599);	
		        	layeredPane.removeAll();
		    		layeredPane.add(panel);
		    		layeredPane.repaint();
		    		layeredPane.revalidate();    
		        }
		    }
		});
		comboBox.setBounds(0, 0, 664, 25);
		frmBaseDeDonnees.getContentPane().add(comboBox);
		
		frmBaseDeDonnees.setBounds(100, 100, 680, 599);	
		frmBaseDeDonnees.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
