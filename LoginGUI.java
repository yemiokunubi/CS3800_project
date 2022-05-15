import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import com.sun.mail.handlers.image_jpeg;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JCheckBoxMenuItem;
import javax.security.sasl.AuthenticationException;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.awt.Color;

public class LoginGUI extends JFrame {

	public static String emailChoice = "yahoo";
	public static String imapName;
	public static String emailName;
	public static String portNum;
	public static String password;
	private JPanel contentPane;
	private JTextField email_input;
	private JPasswordField passwordField;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		
	
//		final String emailChoice;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 651, 415);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JRadioButton yahoo_button = new JRadioButton("Yahoo");
		yahoo_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emailChoice = "yahoo";
				imapName = "imap.mail.yahoo.com";
				// emailName = "billybronco567@yahoo.com";
				// password = "fehrieoydktslbqu";
				portNum = "993";
			}
		});
		buttonGroup.add(yahoo_button);
		yahoo_button.setBounds(287, 118, 85, 56);
		contentPane.add(yahoo_button);
		
		JRadioButton gmail_button = new JRadioButton("Gmail");
		gmail_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emailChoice = "gmail";
				imapName = "imap.gmail.com";
				// emailName = "billybronconetworking2022@gmail.com";
				// password = "cs380001";
				portNum = "995";
			}
		});
		buttonGroup.add(gmail_button);
		gmail_button.setBounds(481, 118, 68, 56);
		contentPane.add(gmail_button);
		
		JRadioButton outlook_button = new JRadioButton("Outlook");
		outlook_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emailChoice = "outlook";
				imapName = "outlook.office365.com";
				// emailName = "billybronconetworking2022@gmail.com";
				// password = "cs380001";
				portNum = "993";
			}
		});
		buttonGroup.add(outlook_button);
		outlook_button.setBounds(106, 118, 85, 56);
		contentPane.add(outlook_button);
		
		email_input = new JTextField();
		email_input.setBounds(157, 217, 349, 26);
		contentPane.add(email_input);
		email_input.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Email");
		lblNewLabel.setBounds(161, 200, 61, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(161, 255, 61, 16);
		contentPane.add(lblNewLabel_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(157, 273, 349, 26);
		contentPane.add(passwordField);
		
		JLabel incorrectLabel = new JLabel();
		incorrectLabel.setForeground(Color.RED);
		incorrectLabel.setBounds(224, 300, 223, 16);
		contentPane.add(incorrectLabel);
		incorrectLabel.setVisible(false);
		
		
		JButton LoginButton = new JButton("Login");
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
		        {
					emailName = email_input.getText();
					password = new String(passwordField.getPassword());
					ArrayList<String> emails = FetchMail.checkMail(imapName, "imaps", emailName, password, portNum);
					if (EmailValidator.checkMail(emailName) && emails.size() > 0) {
						EmailGUI newEmailWindow = new EmailGUI();		//change ticket window name
						newEmailWindow.setVisible(true);
						setVisible(false);
						emails = null;
					} else {
						if (EmailValidator.checkMail(emailName)) {
							System.out.println("Incorrect password");
							incorrectLabel.setText("Incorrect password!");
							incorrectLabel.setVisible(true);
							
						
						}
							
						else {
							System.out.println("This " + emailChoice + " account does not exist");
							incorrectLabel.setVisible(true);
							incorrectLabel.setText("This " + emailChoice + " account does not exist");
						}
							
						
					}
		        }
		        catch (Exception ex)
		        {
		            ex.printStackTrace();
		        }
			
			}
		});
		LoginButton.setBounds(268, 321, 117, 29);
		contentPane.add(LoginButton);
		
		JLabel outlookLogoLabel = new JLabel("");
		Image img1 = new ImageIcon(this.getClass().getResource("outlook.png")).getImage();
		outlookLogoLabel.setIcon(new ImageIcon(img1));
		outlookLogoLabel.setBounds(124, 33, 85, 99);
		contentPane.add(outlookLogoLabel);
		
		JLabel yahooLogoLabel = new JLabel("");
		Image img2 = new ImageIcon(this.getClass().getResource("yahoo.png")).getImage();
		yahooLogoLabel.setIcon(new ImageIcon(img2));
		yahooLogoLabel.setBounds(298, 33, 94, 99);
		contentPane.add(yahooLogoLabel);
		
		JLabel gmailLogoLabel = new JLabel("");
		Image img3 = new ImageIcon(this.getClass().getResource("gmail.png")).getImage();
		gmailLogoLabel.setIcon(new ImageIcon(img3));
		gmailLogoLabel.setBounds(484, 33, 117, 99);
		contentPane.add(gmailLogoLabel);
		
		
		
	}
}
