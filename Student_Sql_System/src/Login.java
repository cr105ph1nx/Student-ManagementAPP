import javax.swing.JFrame;
import java.awt.EventQueue;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.SystemColor;
import javax.swing.JPasswordField;

public class Login {

	private JFrame frmTpN;
	private JTextField username;
	private JPasswordField password;
	
	// Credentials
	final String etu_user = "Etudiant";
	final String etu_pass = "TPEtudiant";
	final String ens_user = "Enseignant";
	final String ens_pass = "TPEnseignant";
	final String admin_user = "BDDAdmin";
	final String admin_pass = "TPAdmin";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frmTpN.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTpN = new JFrame();
		frmTpN.setForeground(SystemColor.activeCaption);
		frmTpN.setTitle("TP N\u00B0 11 : Final Project - Lilia MEHAMLI");
		frmTpN.getContentPane().setBackground(SystemColor.window);
		
		JLabel lblNewLabel_2 = new JLabel("Username");
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		
		username = new JTextField();
		username.setColumns(10);
		
		JButton loginBtn = new JButton("Login");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String user = username.getText();
				@SuppressWarnings("deprecation")
				String pass = password.getText();
				
				if(user.equals(etu_user) && pass.equals(etu_pass)) {
					//Display new window
					Etudiant log = new Etudiant();
					frmTpN.setVisible(false);
					log.main();
					
				}else if(user.equals(ens_user) && pass.equals(ens_pass)) {
					//Display new window
					Enseignant log = new Enseignant();
					frmTpN.setVisible(false);
					log.main();
					
				}else if(user.equals(admin_user) && pass.equals(admin_pass)) {
					//Display new window
					BDDAdmin log = new BDDAdmin();
					frmTpN.setVisible(false);
					log.main();
					
				}else {
					//Display error
					JOptionPane.showMessageDialog(null, "Login Failed. Try Again...");
				}
			}
		});
		
		password = new JPasswordField();
		GroupLayout groupLayout = new GroupLayout(frmTpN.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(128)
							.addComponent(loginBtn, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(71)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(password, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
								.addComponent(username, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(username, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(password, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
					.addComponent(loginBtn, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addGap(33))
		);
		frmTpN.getContentPane().setLayout(groupLayout);
		frmTpN.setBounds(100, 100, 361, 213);
		frmTpN.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
