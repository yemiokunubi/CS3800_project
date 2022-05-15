import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;

import java.awt.event.ActionEvent;
import java.io.File;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Image;

public class SendMailGUI extends JDialog 
{

	private final JPanel contentPanel = new JPanel();
	private JTextField toTextField;
	private JTextField subjectTextField;
	private JTextField attachmentTextField;
	private String files = "";
	private File[] selectedFiles;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		try 
		{
			SendMailGUI dialog = new SendMailGUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SendMailGUI() 
	{
		setBounds(100, 100, 651, 415);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		// To and From labels.
		JLabel toLabel = new JLabel("To:");
		toLabel.setFont(new Font("Monaco", Font.PLAIN, 9));
		
		JLabel fromLabel = new JLabel("Subject:");
		fromLabel.setFont(new Font("Monaco", Font.PLAIN, 9));
		
		// Text fields for To and From.
		toTextField = new JTextField();
		toTextField.setColumns(10);
		
		subjectTextField = new JTextField();
		subjectTextField.setColumns(10);
		
		JTextArea textArea = new JTextArea();
		
		// Attachment label.
		JLabel attachmentLabel = new JLabel("Attachment:");
		attachmentLabel.setFont(new Font("Monaco", Font.PLAIN, 9));
		
		attachmentTextField = new JTextField();
		attachmentTextField.setColumns(10);
		
		// Attachment Button - Attach files to email.
		JButton attachmentButton = new JButton("Attach File");
		Image attachmentImg = new ImageIcon(this.getClass().getResource("AttachmentIcon.png")).getImage();
	    	attachmentButton.setIcon(new ImageIcon(attachmentImg));
		attachmentButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setMultiSelectionEnabled(true);
			    int returnValue = fileChooser.showOpenDialog(null);
			    if (returnValue == JFileChooser.APPROVE_OPTION) 
			    {
				    selectedFiles = fileChooser.getSelectedFiles();
				    for (int i = 0; i < selectedFiles.length; i++)
				    {
				    	files += selectedFiles[i].getName();
				    	
				    }
				    attachmentTextField.setText(files);
			    }

			}
		});
		
		// Send Button - Send finished email.
		JButton sendButton = new JButton("Send");
		Image sendImg = new ImageIcon(this.getClass().getResource("SendEmailIcon.png")).getImage();
	    	sendButton.setIcon(new ImageIcon(sendImg));
		sendButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String to = toTextField.getText();
				String subject = subjectTextField.getText();
				String body = textArea.getText();
				
				MailClient.sendMail(to, subject, body, selectedFiles);
				JOptionPane.showMessageDialog(null, "Message sent");
			}
		});
		
		// Message Body label.
		JLabel messageLabel = new JLabel("Message:");
		messageLabel.setFont(new Font("Monaco", Font.PLAIN, 9));
		
	
		// Create content panel to contain components.
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(toLabel, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addComponent(fromLabel, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addComponent(messageLabel, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addComponent(attachmentLabel, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 436, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(sendButton))
								.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
									.addComponent(attachmentTextField, GroupLayout.PREFERRED_SIZE, 298, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
									.addComponent(attachmentButton)
									.addGap(35))
								.addComponent(toTextField, GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
								.addComponent(subjectTextField, GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE))
							.addGap(68))))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(toLabel)
						.addComponent(toTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(subjectTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(fromLabel))
					.addGap(33)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(attachmentButton)
								.addComponent(attachmentLabel)
								.addComponent(attachmentTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
							.addComponent(sendButton))
						.addComponent(messageLabel))
					.addContainerGap())
		);
		contentPanel.setLayout(gl_contentPanel);
	}
}
