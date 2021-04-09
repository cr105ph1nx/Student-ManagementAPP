import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class TP8Operations {

	private JFrame frmTpQueries;
	private JPanel panel0;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	private JLayeredPane layeredPane;
	
	private JTable table3;
	private JTable table4;
	private JTable table5;
	private JTable table7;
	private JTable table8;
	private JTable table9;
	private JTable table10;
	
	DefaultTableModel model3;
	DefaultTableModel model4;
	DefaultTableModel model5;
	DefaultTableModel model7;
	DefaultTableModel model8;
	DefaultTableModel model9;
	DefaultTableModel model10;
	
	
	Connection con = null;
	PreparedStatement pst = null;
	Statement st = null;
	ResultSet rs = null;
	private JTextField totalField;
	private JTextField maxField;
	private JTextField minField;
	private JTextField averageField;
	
	private static DecimalFormat df = new DecimalFormat("0.00");
	/**
	 * Launch the application.
	 */
	
	public void updateLabel1(){
		con = JDBCConnection.ConnectDb();
		if(con != null) {
			String sql = "SELECT COUNT(*) AS TOTAL FROM Etudiant";	
			try {
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();
				
				if(rs.next()) {
					String total = rs.getString("TOTAL");
					totalField.setText(total);
				}

			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	public void updateLabel2(){
		con = JDBCConnection.ConnectDb();
		if(con != null) {
			
			try {
				String sql = "SELECT MAX(note_examen) AS Max FROM EtudiantUnite";
				
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();
				
				if(rs.next()) {
					String tmp = rs.getString("Max");
					maxField.setText(tmp);
				}
				
				
			    sql = "SELECT MIN(note_examen) AS Min FROM EtudiantUnite";
				
			    pst = con.prepareStatement(sql);
				rs = pst.executeQuery();
				
				if(rs.next()) {
					String tmp = rs.getString("Min");
					minField.setText(tmp);
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
					"SELECT U.libelle as Subject, EU.code_Unite AS Code, COUNT(*) AS Total\r\n" + 
					"FROM EtudiantUnite EU, Unite U\r\n" + 
					"WHERE U.code_Unite = EU.code_Unite\r\n" + 
					"GROUP BY U.libelle, EU.code_Unite";
				
			try {
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[2];
				
				
				int size = model3.getRowCount()-1;
				for(int i=size; i>=0; i--) {
					model3.removeRow(i);
				}
				
				
				while(rs.next()) {
					columnData[0] = rs.getString("Subject");
					columnData[1] = rs.getString("Total");
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
					"SELECT M.matricule_etu AS ID, SUM(M.MoyEtuMat)/(SELECT COUNT(*)\r\n" + 
					"FROM EtudiantUnite EU\r\n" + 
					"WHERE M.matricule_etu = EU.matricule_etu) AS Average\r\n" + 
					"FROM MoyEtuMat M, NbrUnite N\r\n" + 
					"WHERE M.matricule_etu = N.matricule_etu\r\n" + 
					"GROUP BY M.matricule_etu";
				
			try {
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[2];
				
				int size = model4.getRowCount()-1;
				for(int i=size; i>=0; i--) {
					model4.removeRow(i);
				}
				
				while(rs.next()) {
					columnData[0] = rs.getString("ID");
					Float tmp = rs.getFloat("Average");
					columnData[1] = df.format(tmp);
					model4.addRow(columnData);
				}
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	public void updateTable5(){
		con = JDBCConnection.ConnectDb();
		if(con != null) {
			String sql = 
					"SELECT libelle, AVG(MoyEtuMat) AS Average\r\n" + 
					"FROM MOYETUMAT\r\n" + 
					"GROUP BY libelle";
				
			try {
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[2];
				
				int size = model5.getRowCount()-1;
				for(int i=size; i>=0; i--) {
					model5.removeRow(i);
				}
				
				while(rs.next()) {
					columnData[0] = rs.getString("libelle");
					Float tmp = rs.getFloat("Average");
					columnData[1] = df.format(tmp);
					model5.addRow(columnData);
				}
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	public void updateLabel6(){
		con = JDBCConnection.ConnectDb();
		if(con != null) {
			String sql = "SELECT AVG(MgEtu) AS AVERAGE FROM MGETU";	
			try {
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();
				
				if(rs.next()) {
					Float tmp = rs.getFloat("AVERAGE");
					averageField.setText(df.format(tmp));
				}

			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	public void updateTable7(){
		con = JDBCConnection.ConnectDb();
		if(con != null) {
			String sql = 
					"SELECT U.libelle as Subject, MAX(M.MoyEtuMat) As Max\r\n" + 
					"FROM Unite U, MOYETUMAT M, EtudiantUnite EU\r\n" + 
					"WHERE U.code_Unite = EU.code_Unite\r\n" + 
					"AND EU.matricule_etu = M.matricule_etu\r\n" + 
					"AND U.libelle = M.libelle\r\n" + 
					"GROUP BY U.libelle";
				
			try {
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[2];
				
				int size = model7.getRowCount()-1;
				for(int i=size; i>=0; i--) {
					model7.removeRow(i);
				}
				
				while(rs.next()) {
					columnData[0] = rs.getString("Subject");
					Float tmp = rs.getFloat("Max");
					columnData[1] = df.format(tmp);

					model7.addRow(columnData);
				}
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	public void updateTable8(){
		con = JDBCConnection.ConnectDb();
		if(con != null) {
			String sql = 
					"SELECT U.libelle as Subject, MIN(M.MoyEtuMat) As Min\r\n" + 
					"FROM Unite U, MOYETUMAT M, EtudiantUnite EU\r\n" + 
					"WHERE U.code_Unite = EU.code_Unite\r\n" + 
					"AND EU.matricule_etu = M.matricule_etu\r\n" + 
					"AND U.libelle = M.libelle\r\n" + 
					"GROUP BY U.libelle";
				
			try {
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[2];
				
				int size = model8.getRowCount()-1;
				for(int i=size; i>=0; i--) {
					model8.removeRow(i);
				}
				
				while(rs.next()) {
					columnData[0] = rs.getString("Subject");
					Float tmp = rs.getFloat("Min");
					columnData[1] = df.format(tmp);

					model8.addRow(columnData);
				}
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	public void updateTable9(){
		con = JDBCConnection.ConnectDb();
		if(con != null) {
			String sql = 
					"SELECT M.matricule_etu AS ID, E.nom_etu as Surname, E.prenom_etu as Name\r\n" + 
					"FROM MgEtu M, Etudiant E\r\n" + 
					"WHERE M.matricule_etu = E.matricule_etu\r\n" + 
					"AND M.MgEtu >= (SELECT AVG(MgEtu) FROM MGETU)\r\n" + 
					"GROUP BY M.matricule_etu, M.MgEtu, E.nom_etu, E.prenom_etu";
				
			try {
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[3];
				
				int size = model9.getRowCount()-1;
				for(int i=size; i>=0; i--) {
					model9.removeRow(i);
				}
				
				while(rs.next()) {
					columnData[0] = rs.getString("ID");
					columnData[1] = rs.getString("Surname");
					columnData[2] = rs.getString("Name");
					model9.addRow(columnData);
				}
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	public void updateTable10(){
		con = JDBCConnection.ConnectDb();
		if(con != null) {
			String sql = 
					"SELECT E.nom_etu as Surname, E.prenom_etu as Name\r\n" + 
					"FROM Etudiant E, EtudiantUnite EU\r\n" + 
					"WHERE E.matricule_etu  = EU.matricule_etu\r\n" + 
					"GROUP BY E.nom_etu, E.prenom_etu\r\n" + 
					"HAVING COUNT(*) = (SELECT COUNT(*) FROM Unite)";
				
			try {
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[2];
				
				int size = model10.getRowCount()-1;
				for(int i=size; i>=0; i--) {
					model10.removeRow(i);
				}
				
				while(rs.next()) {
					columnData[0] = rs.getString("Surname");
					columnData[1] = rs.getString("Name");

					model10.addRow(columnData);
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
					TP8Operations window = new TP8Operations();
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
	public TP8Operations() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTpQueries = new JFrame();
		frmTpQueries.setTitle("TP8 Queries ");
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
		returnBtn1.setBounds(177, 195, 79, 39);
		returnBtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmTpQueries.setVisible(false);
			}
		});
		panel1.add(returnBtn1);
		
		JLabel lblNewLabel = new JLabel("The total number of students is:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(84, 49, 266, 39);
		panel1.add(lblNewLabel);
		
		totalField = new JTextField();
		totalField.setHorizontalAlignment(SwingConstants.CENTER);
		totalField.setText("");
		totalField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		totalField.setBounds(160, 99, 113, 28);
		panel1.add(totalField);
		totalField.setColumns(10);
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
		
		JLabel maxLbl = new JLabel("HIGHEST: ");
		maxLbl.setHorizontalAlignment(SwingConstants.CENTER);
		maxLbl.setForeground(SystemColor.textHighlight);
		maxLbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		maxLbl.setBounds(20, 81, 218, 39);
		panel2.add(maxLbl);
		
		JLabel lblNewLabel_1 = new JLabel("The highest and lowest exam scores are:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(10, 31, 414, 39);
		panel2.add(lblNewLabel_1);
		
		JLabel minLbl = new JLabel("LOWEST: ");
		minLbl.setHorizontalAlignment(SwingConstants.CENTER);
		minLbl.setForeground(SystemColor.textHighlight);
		minLbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		minLbl.setBounds(20, 123, 218, 39);
		panel2.add(minLbl);
		
		maxField = new JTextField();
		maxField.setHorizontalAlignment(SwingConstants.CENTER);
		maxField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		maxField.setBounds(202, 89, 123, 30);
		panel2.add(maxField);
		maxField.setColumns(10);
		
		minField = new JTextField();
		minField.setHorizontalAlignment(SwingConstants.CENTER);
		minField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		minField.setColumns(10);
		minField.setBounds(202, 131, 123, 30);
		panel2.add(minField);
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
		Object[] col3 = {"Subject", "Total Students"};
		final Object[] row3 = new Object[2];
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
		Object[] col4 = {"Student ID", "Average Score"};
		final Object[] row4 = new Object[2];
		model4.setColumnIdentifiers(col4);
		table4.setModel(model4);
		table4.setBounds(10, 23, 414, 152);
		scrollPane4.setViewportView(table4);
		/************************************************* FOURTH QUERY *************************************************************/
		
		/************************************************* FIFTH QUERY *************************************************************/
		JPanel panel5 = new JPanel();
		panel5.setLayout(null);
		panel5.setBackground(Color.WHITE);
		layeredPane.add(panel5, "name_220077383787800");
		
		JButton returnBtn5 = new JButton("Return");
		returnBtn5.setBounds(177, 195, 79, 39);
		returnBtn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmTpQueries.setVisible(false);
			}
		});
		panel5.add(returnBtn5);
		
		JScrollPane scrollPane5 = new JScrollPane();
		scrollPane5.setBounds(10, 23, 414, 152);
		panel5.add(scrollPane5);
		
		table5 = new JTable();
		model5 = new DefaultTableModel();
		Object[] col5 = {"Subject", "Average Score"};
		final Object[] row5 = new Object[2];
		model5.setColumnIdentifiers(col5);
		table5.setModel(model5);
		table5.setBounds(10, 23, 414, 152);
		scrollPane5.setViewportView(table5);
		/************************************************* FIFTH QUERY *************************************************************/
		
		/************************************************* SIXTH QUERY *************************************************************/
		JPanel panel6 = new JPanel();
		panel6.setLayout(null);
		panel6.setBackground(Color.WHITE);
		layeredPane.add(panel6, "name_220085835735800");
		
		JButton returnBtn6 = new JButton("Return");
		returnBtn6.setBounds(177, 195, 79, 39);
		returnBtn6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmTpQueries.setVisible(false);
			}
		});
		panel6.add(returnBtn6);
		
		JLabel lblNewLabel_2 = new JLabel("The average score of the section is:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(10, 53, 414, 39);
		panel6.add(lblNewLabel_2);
		
		averageField = new JTextField();
		averageField.setText("");
		averageField.setHorizontalAlignment(SwingConstants.CENTER);
		averageField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		averageField.setColumns(10);
		averageField.setBounds(160, 103, 113, 28);
		panel6.add(averageField);
		/************************************************* SIXTH QUERY *************************************************************/
		
		/************************************************* SEVENTH QUERY *************************************************************/
		JPanel panel7 = new JPanel();
		panel7.setLayout(null);
		panel7.setBackground(Color.WHITE);
		layeredPane.add(panel7, "name_220087958001100");
		
		JButton returnBtn7 = new JButton("Return");
		returnBtn7.setBounds(177, 195, 79, 39);
		returnBtn7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmTpQueries.setVisible(false);
			}
		});
		panel7.add(returnBtn7);
		
		JScrollPane scrollPane7 = new JScrollPane();
		scrollPane7.setBounds(10, 23, 414, 152);
		panel7.add(scrollPane7);
		
		table7 = new JTable();
		model7 = new DefaultTableModel();
		Object[] col7 = {"Subject", "Highest Score"};
		final Object[] row7 = new Object[2];
		model7.setColumnIdentifiers(col7);
		table7.setModel(model7);
		table7.setBounds(10, 23, 414, 152);
		scrollPane7.setViewportView(table7);
		/************************************************* SEVENTH QUERY *************************************************************/
		
		/************************************************* EIGTH QUERY *************************************************************/
		JPanel panel8 = new JPanel();
		panel8.setLayout(null);
		panel8.setBackground(Color.WHITE);
		layeredPane.add(panel8, "name_220869621081500");
		
		JButton returnBtn8 = new JButton("Return");
		returnBtn8.setBounds(177, 195, 79, 39);
		returnBtn8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmTpQueries.setVisible(false);
			}
		});
		panel8.add(returnBtn8);
		
		JScrollPane scrollPane8 = new JScrollPane();
		scrollPane8.setBounds(10, 23, 414, 152);
		panel8.add(scrollPane8);
		
		table8 = new JTable();
		model8 = new DefaultTableModel();
		Object[] col8 = {"Subject", "Lowest Score"};
		final Object[] row8 = new Object[2];
		model8.setColumnIdentifiers(col8);
		table8.setModel(model8);
		table8.setBounds(10, 23, 414, 152);
		scrollPane8.setViewportView(table8);
		/************************************************* EIGHT QUERY *************************************************************/
		
		/************************************************* NINTH QUERY *************************************************************/
		JPanel panel9 = new JPanel();
		panel9.setLayout(null);
		panel9.setBackground(Color.WHITE);
		layeredPane.add(panel9, "name_220873526108900");
		
		JButton returnBtn9 = new JButton("Return");
		returnBtn9.setBounds(177, 195, 79, 39);
		returnBtn9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmTpQueries.setVisible(false);
			}
		});
		panel9.add(returnBtn9);
		
		JScrollPane scrollPane9 = new JScrollPane();
		scrollPane9.setBounds(10, 23, 414, 152);
		panel9.add(scrollPane9);
		
		table9 = new JTable();
		model9 = new DefaultTableModel();
		Object[] col9 = {"Student ID", "Surname", "Name"};
		final Object[] row9 = new Object[3];
		model9.setColumnIdentifiers(col9);
		table9.setModel(model9);
		table9.setBounds(10, 23, 414, 152);
		scrollPane9.setViewportView(table9);
		/************************************************* NINTH QUERY *************************************************************/
		
		/************************************************* TENTH QUERY *************************************************************/
		JPanel panel10 = new JPanel();
		panel10.setLayout(null);
		panel10.setBackground(Color.WHITE);
		layeredPane.add(panel10, "name_220877198261100");
		
		JButton returnBtn10 = new JButton("Return");
		returnBtn10.setBounds(177, 195, 79, 39);
		returnBtn10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmTpQueries.setVisible(false);
			}
		});
		panel10.add(returnBtn10);
		
		JScrollPane scrollPane10 = new JScrollPane();
		scrollPane10.setBounds(10, 23, 414, 152);
		panel10.add(scrollPane10);
		
		table10 = new JTable();
		model10 = new DefaultTableModel();
		Object[] col10 = {"Surname", "Name"};
		final Object[] row10 = new Object[2];
		model10.setColumnIdentifiers(col10);
		table10.setModel(model10);
		table10.setBounds(10, 23, 414, 152);
		scrollPane10.setViewportView(table10);
		/************************************************* TENTH QUERY *************************************************************/
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Select Operation", 
				"Total number of students", 
				"Max and Min exam score", 
				"Total number of students following each subject", 
				"Average score of all students",
				"Average score of section in each subject",
				"Average score of section",
				"Highest score of section in each subject",
				"Lowest score of section in each subject",
				"Students who have an average score greater or equal than the section's average",
				"Students who have an exam mark in each subject",
		}));
		
		comboBox.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		        String db = (String) comboBox.getItemAt(comboBox.getSelectedIndex());
		        frmTpQueries.setBounds(100, 100, 446, 425);

		        if(db.equals("Total number of students")) {
		        	frmTpQueries.setBounds(100, 100, 450, 323);
		        	layeredPane.removeAll();
		    		layeredPane.add(panel1);
		    		layeredPane.repaint();
		    		layeredPane.revalidate();
		    		updateLabel1();
		    		
		        }else if(db.equals("Max and Min exam score")) {
		        	frmTpQueries.setBounds(100, 100, 450, 323);
		        	layeredPane.removeAll();
		    		layeredPane.add(panel2);
		    		layeredPane.repaint();
		    		layeredPane.revalidate();
		    		updateLabel2();
		    		
		        }else if(db.equals("Total number of students following each subject")) {
		        	frmTpQueries.setBounds(100, 100, 450, 323);
		        	layeredPane.removeAll();
		    		layeredPane.add(panel3);
		    		layeredPane.repaint();
		    		layeredPane.revalidate();
		    		updateTable3();
		    		
		        }else if(db.equals("Average score of all students")) {
		        	frmTpQueries.setBounds(100, 100, 450, 323);
		        	layeredPane.removeAll();
		    		layeredPane.add(panel4);
		    		layeredPane.repaint();
		    		layeredPane.revalidate();
		    		updateTable4();
		    		
		        }else if(db.equals("Average score of section in each subject")) {
		        	frmTpQueries.setBounds(100, 100, 450, 323);
		        	layeredPane.removeAll();
		    		layeredPane.add(panel5);
		    		layeredPane.repaint();
		    		layeredPane.revalidate();
		    		updateTable5();
		    		
		        }else if(db.equals("Average score of section")) {
		        	frmTpQueries.setBounds(100, 100, 450, 323);
		        	layeredPane.removeAll();
		    		layeredPane.add(panel6);
		    		layeredPane.repaint();
		    		layeredPane.revalidate();
		    		updateLabel6();
		    		
		        }else if(db.equals("Highest score of section in each subject")) {
		        	frmTpQueries.setBounds(100, 100, 450, 323);
		        	layeredPane.removeAll();
		    		layeredPane.add(panel7);
		    		layeredPane.repaint();
		    		layeredPane.revalidate();
		    		updateTable7();
		    		
		        }else if(db.equals("Lowest score of section in each subject")) {
		        	frmTpQueries.setBounds(100, 100, 450, 323);
		        	layeredPane.removeAll();
		    		layeredPane.add(panel8);
		    		layeredPane.repaint();
		    		layeredPane.revalidate();
		    		updateTable8();
		    		
		        }else if(db.equals("Students who have an average score greater or equal than the section's average")) {
		        	frmTpQueries.setBounds(100, 100, 450, 323);
		        	layeredPane.removeAll();
		    		layeredPane.add(panel9);
		    		layeredPane.repaint();
		    		layeredPane.revalidate();
		    		updateTable9();
		    		
		        }else if(db.equals("Students who have an exam mark in each subject")) {
		        	frmTpQueries.setBounds(100, 100, 450, 323);
		        	layeredPane.removeAll();
		    		layeredPane.add(panel10);
		    		layeredPane.repaint();
		    		layeredPane.revalidate();
		    		updateTable10();
		    		
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