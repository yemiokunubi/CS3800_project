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
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;
import java.awt.Panel;
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;


public class LoginGUI extends JFrame 
{

	public static String emailChoice = "yahoo";
	public static String imapName;
	public static String emailName;
	public static String portNum;
	public static String password;
	private JPanel contentPane;
	private JTextField email_input;
	private JPasswordField passwordField;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	// emailName = "billybronco14@yahoo.com";
	// password = "fehrieoydktslbqu";
	
	//	emailName = "billybronconetworking2022@gmail.com";
	// password = "cs380002";
	
	// emailName = "billybronconetworking2022@outlook.com";
	// password = "cs380001";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() 
	{
		// final String emailChoice;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 651, 415);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("FormattedTextField.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Create Yahoo Email Choice button.
		JRadioButton yahoo_button = new JRadioButton("Yahoo");
		yahoo_button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				emailChoice = "yahoo";
				imapName = "imap.mail.yahoo.com";
				// emailName = "billybronco567@yahoo.com";
				// password = "fehrieoydktslbqu";
				portNum = "993";
			}
		});
		buttonGroup.add(yahoo_button);
		yahoo_button.setBounds(64, 204, 85, 56);
		contentPane.add(yahoo_button);
		
		// Create Gmail Email Choice button.
		JRadioButton gmail_button = new JRadioButton("Gmail");
		gmail_button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				emailChoice = "gmail";
				imapName = "imap.gmail.com";
				// emailName = "billybronconetworking2022@gmail.com";
				// password = "cs380001";
				portNum = "995";
			}
		});
		buttonGroup.add(gmail_button);
		gmail_button.setBounds(64, 136, 68, 56);
		contentPane.add(gmail_button);
		
		// Create Outlook Email Choice button.
		JRadioButton outlook_button = new JRadioButton("Outlook");
		outlook_button.setFont(new Font("Geneva", Font.PLAIN, 13));
		outlook_button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				emailChoice = "outlook";
				imapName = "outlook.office365.com";
				// password = "cs380001";
				portNum = "993";
			}
		});
		buttonGroup.add(outlook_button);
		outlook_button.setBounds(64, 282, 85, 56);
		contentPane.add(outlook_button);
		
		// Create text field for email address input.
		email_input = new JTextField();
		email_input.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Email", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		email_input.setBounds(308, 126, 337, 43);
		contentPane.add(email_input);
		email_input.setColumns(10);
		
		// Create text field for password input.
		passwordField = new JPasswordField();
		passwordField.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Password", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		passwordField.setBounds(308, 199, 337, 43);
		contentPane.add(passwordField);
		
		// Create label for email error.
		JLabel incorrectLabel = new JLabel();
		incorrectLabel.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		incorrectLabel.setForeground(new Color(178, 34, 34));
		incorrectLabel.setBounds(325, 322, 307, 16);
		contentPane.add(incorrectLabel);
		
		// Create login button.
		JButton LoginButton = new JButton("LOGIN");
		LoginButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		LoginButton.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		LoginButton.setOpaque(true);
		LoginButton.setForeground(new Color(255, 255, 255));
		LoginButton.setBackground(new Color(147, 112, 219));
		LoginButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
		        {
					emailName = email_input.getText();
					password = new String(passwordField.getPassword());
					ArrayList<String> emails = FetchMail.checkMail(imapName, "imaps", emailName, password, portNum);
					if (EmailValidator.checkMail(emailName) && emails.size() > 0) 
					{
						EmailGUI newEmailWindow = new EmailGUI();		//change ticket window name
						newEmailWindow.setVisible(true);
						setVisible(false);
						emails = null;
					} 
					else 
					{
						if (EmailValidator.checkMail(emailName)) 
						{
							System.out.println("Incorrect password");
							incorrectLabel.setText("Incorrect password!");
							incorrectLabel.setVisible(true);
						}
							
						else 
						{
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
		LoginButton.setBounds(315, 272, 317, 44);
		contentPane.add(LoginButton);
		
		
		// Add labels and images for email choices.
		JLabel outlookLogoLabel = new JLabel("");
		Image img1 = new ImageIcon(this.getClass().getResource("OutlookIcon.png")).getImage();
		outlookLogoLabel.setIcon(new ImageIcon(img1));
		outlookLogoLabel.setBounds(171, 272, 68, 84);
		contentPane.add(outlookLogoLabel);
		
		JLabel yahooLogoLabel = new JLabel("");
		yahooLogoLabel.setBorder(null);
		Image img2 = new ImageIcon(this.getClass().getResource("YahooIcon.png")).getImage();
		yahooLogoLabel.setIcon(new ImageIcon(img2));
		yahooLogoLabel.setBounds(169, 191, 82, 84);
		contentPane.add(yahooLogoLabel);
		
		JLabel gmailLogoLabel = new JLabel("");
		Image img3 = new ImageIcon(this.getClass().getResource("GmailIcon.png")).getImage();
		gmailLogoLabel.setIcon(new ImageIcon(img3));
		gmailLogoLabel.setBounds(169, 119, 85, 78);
		contentPane.add(gmailLogoLabel);
		Image bg = new ImageIcon(this.getClass().getResource("rectCol.png")).getImage();
		
		JLabel welcomeLabel = new JLabel("Welcome");
		welcomeLabel.setFont(new Font("Monaco", Font.BOLD, 40));
		welcomeLabel.setForeground(UIManager.getColor("FormattedTextField.background"));
		welcomeLabel.setBounds(76, 41, 168, 47);
		contentPane.add(welcomeLabel);
		
		JLabel selectPromptLabel = new JLabel("Select server:");
		selectPromptLabel.setFont(new Font("Monaco", Font.BOLD, 13));
		selectPromptLabel.setBounds(99, 89, 114, 16);
		contentPane.add(selectPromptLabel);
		
		JLabel shape = new JLabel("");
		shape.setIcon(new ImageIcon(bg));
		shape.setBounds(12, 17, 291, 347);
		contentPane.add(shape);
		
		JLabel lblSignIn = new JLabel("Sign In");
		lblSignIn.setForeground(new Color(147, 112, 219));
		lblSignIn.setFont(new Font("Monaco", Font.BOLD, 30));
		lblSignIn.setBounds(411, 67, 168, 47);
		contentPane.add(lblSignIn);
		
	}
}
