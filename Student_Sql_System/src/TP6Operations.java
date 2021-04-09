import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.CardLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.Color;

public class TP6Operations {
	private JFrame frmTpQueries;
	private JPanel panel0;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	private JLayeredPane layeredPane;
	private JTable table2;
	private JTable table3;
	private JTable table4;
	
	DefaultTableModel model1;
	DefaultTableModel model2;
	DefaultTableModel model3;
	DefaultTableModel model4;
	
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	private JTable table1;
	
	private static DecimalFormat df = new DecimalFormat("0.00");
	/**
	 * Launch the application.
	 */
	
	public void updateTable1(){
		con = JDBCConnection.ConnectDb();
		if(con != null) {
			String sql = 
					"SELECT nom_etu, prenom_etu FROM Etudiant\r\n" + 
					"WHERE matricule_etu IN\r\n" + 
					"(SELECT matricule_etu FROM EtudiantUnite\r\n" + 
					"WHERE note_examen = 20)";
				
			try {
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[2];
					
				while(rs.next()) {
					columnData[0] = rs.getString("nom_etu");
					columnData[1] = rs.getString("prenom_etu");
					model1.addRow(columnData);
				}
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	
	public void updateTable2(){
		con = JDBCConnection.ConnectDb();
		if(con != null) {
			String sql = 
					"SELECT nom_etu, prenom_etu FROM Etudiant \r\n" + 
					"WHERE matricule_etu NOT IN\r\n" + 
					"(SELECT matricule_etu FROM EtudiantUnite\r\n" + 
					"WHERE code_Unite IN\r\n" + 
					"(SELECT code_Unite FROM Unite WHERE libelle = 'POO'))";
				
			try {
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[2];
					
				while(rs.next()) {
					columnData[0] = rs.getString("nom_etu");
					columnData[1] = rs.getString("prenom_etu");
					model2.addRow(columnData);
				}
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	
	public void updateTable3(){
		con = JDBCConnection.ConnectDb();
		if(con != null) {
			String sql = 
					"SELECT libelle FROM Unite \r\n" + 
					"WHERE code_Unite NOT IN\r\n" + 
					"(SELECT code_Unite FROM EtudiantUnite)";
				
			try {
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[1];
				
				while(rs.next()) {
					columnData[0] = rs.getString("libelle");
					model3.addRow(columnData);
				}
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	
	public void updateTable4(){
		con = JDBCConnection.ConnectDb();
		if(con != null) {
			String sql = 
					"SELECT E.nom_etu as Surname, E.prenom_etu as Name, U.libelle as Subject,\r\n" + 
					"AVG(EU.note_cc + EU.note_tp + EU.note_examen)/3.0 AS Result \r\n" + 
					"FROM EtudiantUnite EU, Unite U, Etudiant E\r\n" + 
					"WHERE EU.code_Unite = U.code_Unite \r\n" + 
					"AND EU.matricule_etu = E.matricule_etu\r\n" + 
					"GROUP BY E.nom_etu, E.prenom_etu, U.libelle";
				
			try {
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[4];
				
				while(rs.next()) {
					columnData[0] = rs.getString("Surname");
					columnData[1] = rs.getString("Name");
					columnData[2] = rs.getString("Subject");
					Float tmp = rs.getFloat("Result");
					columnData[3] = df.format(tmp);
					model4.addRow(columnData);
				}
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TP6Operations window = new TP6Operations();
					window.frmTpQueries.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public TP6Operations() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTpQueries = new JFrame();
		frmTpQueries.setTitle("TP6 Queries ");
		frmTpQueries.setBounds(100, 100, 450, 323);
		frmTpQueries.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTpQueries.getContentPane().setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 28, 434, 256);
		frmTpQueries.getContentPane().add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		/************************************************* DEFAULT PANEL *************************************************************/
		JPanel panel0 = new JPanel();
		panel0.setBackground(Color.WHITE);
		layeredPane.add(panel0, "name_114923743546399");
		panel0.setLayout(null);
		
		JButton returnBtn = new JButton("Return");
		returnBtn.setBounds(177, 101, 79, 39);
		returnBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmTpQueries.setVisible(false);
			}
		});
		panel0.add(returnBtn);
		/************************************************* DEFAULT PANEL *************************************************************/
		
		/************************************************* FIRST QUERY *************************************************************/
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.WHITE);
		layeredPane.add(panel1, "name_114923743546400");
		panel1.setLayout(null);
		
		JButton returnBtn1 = new JButton("Return");
		returnBtn1.setBounds(177, 196, 79, 39);
		returnBtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmTpQueries.setVisible(false);
			}
		});
		panel1.add(returnBtn1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 23, 414, 152);
		panel1.add(scrollPane);

		table1 = new JTable();
		model1 = new DefaultTableModel();
		Object[] col1 = {"Surname", "Name"};
		final Object[] row1 = new Object[2];
		model1.setColumnIdentifiers(col1);
		table1.setModel(model1);
		table1.setBounds(10, 23, 414, 152);
		
		scrollPane.setViewportView(table1);
		/************************************************* FIRST QUERY *************************************************************/
		
		/************************************************* SECOND QUERY *************************************************************/
		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.WHITE);
		layeredPane.add(panel2, "name_114963378528600");
		panel2.setLayout(null);
		
		JButton returnBtn2 = new JButton("Return");
		returnBtn2.setBounds(177, 195, 79, 39);
		returnBtn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmTpQueries.setVisible(false);
			}
		});
		panel2.add(returnBtn2);
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(10, 23, 414, 152);
		panel2.add(scrollPane2);
		
		table2 = new JTable();
		model2 = new DefaultTableModel();
		Object[] col2 = {"Surname", "Name"};
		final Object[] row2 = new Object[2];
		model2.setColumnIdentifiers(col2);
		table2.setModel(model2);
		table2.setBounds(10, 23, 414, 152);
		
		scrollPane2.setViewportView(table2);
		/************************************************* SECOND QUERY *************************************************************/
		
		/************************************************* THIRD QUERY *************************************************************/
		JPanel panel3 = new JPanel();
		panel3.setBackground(Color.WHITE);
		layeredPane.add(panel3, "name_114966101967600");
		panel3.setLayout(null);
		

		JButton returnBtn3 = new JButton("Return");
		returnBtn3.setBounds(177, 195, 79, 39);
		returnBtn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmTpQueries.setVisible(false);
			}
		});
		panel3.add(returnBtn3);
		
		JScrollPane scrollPane3 = new JScrollPane();
		scrollPane3.setBounds(10, 23, 414, 152);
		panel3.add(scrollPane3);
		
		table3 = new JTable();
		model3 = new DefaultTableModel();
		Object[] col3 = {"Subject"};
		final Object[] row3 = new Object[1];
		model3.setColumnIdentifiers(col3);
		table3.setModel(model3);
		table3.setBounds(10, 23, 414, 152);
		scrollPane3.setViewportView(table3);
		/************************************************* THIRD QUERY *************************************************************/
		
		/************************************************* FOURTH QUERY *************************************************************/
		JPanel panel4 = new JPanel();
		panel4.setBackground(Color.WHITE);
		layeredPane.add(panel4, "name_114970973437100");
		panel4.setLayout(null);
		
		JButton returnBtn4 = new JButton("Return");
		returnBtn4.setBounds(177, 195, 79, 39);
		returnBtn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmTpQueries.setVisible(false);
			}
		});
		panel4.add(returnBtn4);
		
		JScrollPane scrollPane4 = new JScrollPane();
		scrollPane4.setBounds(10, 23, 414, 152);
		panel4.add(scrollPane4);
		
		table4 = new JTable();
		model4 = new DefaultTableModel();
		Object[] col4 = {"Surname", "Name", "Subject", "Result"};
		final Object[] row4 = new Object[4];
		model4.setColumnIdentifiers(col4);
		table4.setModel(model4);
		table4.setBounds(10, 23, 414, 152);
		scrollPane4.setViewportView(table4);
		/************************************************* FOURTH QUERY *************************************************************/
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Select Operation", "Students who got marks equal to 20", 
					"Students not following the subject 'POO'", "Subjects which no student follows", "Transcript of student results"}));
		comboBox.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		        String db = (String) comboBox.getItemAt(comboBox.getSelectedIndex());
		        frmTpQueries.setBounds(100, 100, 446, 425);

		        if(db.equals("Students who got marks equal to 20")) {
		        	frmTpQueries.setBounds(100, 100, 450, 323);
		        	layeredPane.removeAll();
		    		layeredPane.add(panel1);
		    		layeredPane.repaint();
		    		layeredPane.revalidate();
		    		updateTable1();
		    		
		        }else if(db.equals("Students not following the subject 'POO'")) {
		        	frmTpQueries.setBounds(100, 100, 450, 323);
		        	layeredPane.removeAll();
		    		layeredPane.add(panel2);
		    		layeredPane.repaint();
		    		layeredPane.revalidate();
		    		updateTable2();
		    		
		        }else if(db.equals("Subjects which no student follows")) {
		        	frmTpQueries.setBounds(100, 100, 450, 323);
		        	layeredPane.removeAll();
		    		layeredPane.add(panel3);
		    		layeredPane.repaint();
		    		layeredPane.revalidate();
		    		updateTable3();
		    		
		        }else if(db.equals("Transcript of student results")) {
		        	frmTpQueries.setBounds(100, 100, 450, 323);
		        	layeredPane.removeAll();
		    		layeredPane.add(panel4);
		    		layeredPane.repaint();
		    		layeredPane.revalidate();
		    		updateTable4();
		    		
		        }else {
		        	frmTpQueries.setBounds(100, 100, 450, 323);
		        	layeredPane.removeAll();
		    		layeredPane.add(panel0);
		    		layeredPane.repaint();
		    		layeredPane.revalidate();
		        }
		    }
		});
		comboBox.setBounds(0, 0, 434, 26);
		frmTpQueries.getContentPane().add(comboBox);
	}
}
