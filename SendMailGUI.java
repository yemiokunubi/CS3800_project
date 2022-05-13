import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
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

public class SendMailGUI extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField toTextField;
	private JTextField subjectTextField;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SendMailGUI dialog = new SendMailGUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SendMailGUI() {
		setBounds(100, 100, 651, 415);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("To:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		JLabel lblNewLabel_1 = new JLabel("Subject:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		toTextField = new JTextField();
		toTextField.setColumns(10);
		
		
		subjectTextField = new JTextField();
		subjectTextField.setColumns(10);
		
		
		JTextArea textArea = new JTextArea();
		
		
		JLabel lblNewLabel_1_1 = new JLabel("Attachment:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		
		JButton btnNewButton_1 = new JButton("Attach File");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
			    int returnValue = fileChooser.showOpenDialog(null);
			    if (returnValue == JFileChooser.APPROVE_OPTION) 
			    {
				    File selectedFile = fileChooser.getSelectedFile();
				    textField_2.setText(selectedFile.getAbsolutePath());
			    }

			}
		});
		

		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String to = toTextField.getText();
				String subject = subjectTextField.getText();
				String body = textArea.getText();
				String file = textField_2.getText();
				MailClient.sendMail(to, subject, body, file);
				JOptionPane.showMessageDialog(null, "Message sent");
			}
		});
		
		JLabel lblNewLabel_2 = new JLabel("Message:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
	
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 436, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnNewButton))
								.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
									.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 298, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
									.addComponent(btnNewButton_1)
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
						.addComponent(lblNewLabel)
						.addComponent(toTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(subjectTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1))
					.addGap(33)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnNewButton_1)
								.addComponent(lblNewLabel_1_1)
								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
							.addComponent(btnNewButton))
						.addComponent(lblNewLabel_2))
					.addContainerGap())
		);
		contentPanel.setLayout(gl_contentPanel);
	}
}
