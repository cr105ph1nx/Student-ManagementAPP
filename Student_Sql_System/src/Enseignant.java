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

public class Enseignant {

	private JFrame frmBaseDeDonnees;
	
	private final JPanel panel = new JPanel();
	private JTextField matricule_ens;
	private JTextField idfield;
	private JTextField surnamefield;
	private JTextField namefield;
	
	private JTable table;
	
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	DefaultTableModel model;
	/**
	 * Launch the application.
	 */
	public void updateTable(String matricule_ens, Boolean reset) {
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
					int size = model.getRowCount()-1;
					for(int i=size; i>=0; i--) {
						model.removeRow(i);
					}
				}
		
				while(rs.next()) {
					columnData[0] = rs.getString("matricule_ens");
					columnData[1] = rs.getString("nom_ens");
					columnData[2] = rs.getString("prenom_ens");
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
					Enseignant window = new Enseignant();
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
	public Enseignant() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBaseDeDonnees = new JFrame();
		frmBaseDeDonnees.setTitle("Teacher Data Base");
		frmBaseDeDonnees.getContentPane().setBackground(SystemColor.window);
		frmBaseDeDonnees.getContentPane().setLayout(null);
		panel.setBackground(SystemColor.window);
		panel.setBounds(0, 0, 652, 450);
		frmBaseDeDonnees.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton resetInsertBtn = new JButton("");
		resetInsertBtn.setIcon(new ImageIcon(Enseignant.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		resetInsertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Restore fields
				idfield.setText("");
				surnamefield.setText("");
				namefield.setText("");
				idfield.enable();
			}
		});
		resetInsertBtn.setBackground(Color.WHITE);
		resetInsertBtn.setBounds(24, 147, 28, 24);
		panel.add(resetInsertBtn);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(41, 250, 77, 21);
		panel.add(lblName);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(41, 216, 77, 21);
		panel.add(lblSurname);
		
		JLabel lblNewLabel_1 = new JLabel("Teacher ID");
		lblNewLabel_1.setBounds(41, 184, 77, 21);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("Teacher ID");
		lblNewLabel.setBounds(41, 45, 77, 21);
		panel.add(lblNewLabel);
		
		matricule_ens = new JTextField();
		matricule_ens.setBounds(107, 45, 125, 21);
		panel.add(matricule_ens);
		matricule_ens.setColumns(10);
		
		JButton searchBtn = new JButton("Search");
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTable(matricule_ens.getText(), true);
			}
		});
		searchBtn.setBounds(88, 87, 84, 29);
		panel.add(searchBtn);
		
		idfield = new JTextField();
		idfield.setColumns(10);
		idfield.setBounds(107, 184, 125, 21);
		panel.add(idfield);
		
		surnamefield = new JTextField();
		surnamefield.setColumns(10);
		surnamefield.setBounds(107, 216, 125, 21);
		panel.add(surnamefield);
		
		namefield = new JTextField();
		namefield.setColumns(10);
		namefield.setBounds(107, 250, 125, 21);
		panel.add(namefield);
		
		JButton updateBtn = new JButton("Update");
		updateBtn.setBounds(148, 340, 84, 29);
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
		logoutBtn.setBounds(41, 383, 84, 29);
		panel.add(logoutBtn);
		
		JButton deleteBtn = new JButton("Delete");
		deleteBtn.setBounds(148, 383, 84, 29);
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = table.getSelectedRow();
				if(i >= 0) {
					int action = JOptionPane.showConfirmDialog(null, "Do you really want to delete this record?", "Delete Record Confirmation", JOptionPane.YES_NO_OPTION);
					if(action == 0) {
						String matricule_ens = table.getModel().getValueAt(i, 0).toString();
						String sql = "DELETE FROM Enseignant WHERE matricule_ens = " + matricule_ens;
						
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
		scrollPane.setBounds(281, 45, 346, 367);
		panel.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				int i = table.getSelectedRow();
				idfield.setText((String) model.getValueAt(i, 0));
				surnamefield.setText((String) model.getValueAt(i, 1));
				namefield.setText((String) model.getValueAt(i, 2));

				idfield.disable();
			}
		});
		model = new DefaultTableModel();
		Object[] column = {"ID", "Surname", "Name"};
		final Object[] row = new Object[3];
		model.setColumnIdentifiers(column);
		table.setModel(model);
		table.getColumnModel().getColumn(1).setPreferredWidth(117);
		scrollPane.setViewportView(table);
		
		JButton insertBtn = new JButton("Insert");
		insertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(idfield.isEnabled()) {
					Object[] row = new Object[3];
					row[0] = idfield.getText();
					row[1] = surnamefield.getText();
					row[2] = namefield.getText();
					
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
							updateTable("*", true);
						}catch(Exception e1) {
							JOptionPane.showMessageDialog(null, "There seems to have been a problem...");
						}
					}
					// Restore fields
					idfield.setText("");
					surnamefield.setText("");
					namefield.setText("");
				}else {
					JOptionPane.showMessageDialog(null, "Please clear fields before inserting record.");
				}
			}
		});
		insertBtn.setBounds(41, 340, 84, 29);
		panel.add(insertBtn);
		
		JButton selectbtn = new JButton("");
		selectbtn.setBackground(Color.WHITE);
		selectbtn.setIcon(new ImageIcon(Etudiant.class.getResource("/com/sun/java/swing/plaf/windows/icons/DetailsView.gif")));
		selectbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateTable("*", true);
			}
		});
		selectbtn.setBounds(599, 11, 28, 24);
		panel.add(selectbtn);
		
		JButton resetBtn = new JButton("");
		resetBtn.setBackground(Color.WHITE);
		resetBtn.setIcon(new ImageIcon(Etudiant.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateTable("", true);
			}
		});
		resetBtn.setBounds(561, 11, 28, 24);
		panel.add(resetBtn);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setEnabled(false);
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setBounds(24, 147, 230, 154);
		panel.add(btnNewButton);
		
		frmBaseDeDonnees.setBounds(100, 100, 668, 489);
		frmBaseDeDonnees.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
