import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JTextArea;

public class EmailGUI extends JFrame 
{

	private JPanel contentPane;
	// private ArrayList<String> emails = FetchMail.checkMail("imap.gmail.com", "imaps", "billybronconetworking2022@gmail.com", "cs380001");
	// private ArrayList<String> emailBody = ReadEmail.fetch("imap.gmail.com", "imaps", "billybronconetworking2022@gmail.com", "cs380001");
	// private ArrayList<String> emails = FetchMail.checkMail("imap.mail.yahoo.com", "imaps", "billybronco567@yahoo.com", "fehrieoydktslbqu", "993");
	
	private ArrayList<String> emails = FetchMail.checkMail(LoginGUI.imapName, "imaps", LoginGUI.emailName, LoginGUI.password, LoginGUI.portNum);
	private ArrayList<String> emailBody = ReadEmail.fetch(LoginGUI.imapName, "imaps", LoginGUI.emailName, LoginGUI.password, LoginGUI.portNum);


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
					EmailGUI frame = new EmailGUI();
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
	public EmailGUI() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 675, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		Color backgroundColor = new Color(201,223,236);
		contentPane.setBackground(backgroundColor);
		setContentPane(contentPane);
		
		
		// Adds inbox title.
		JLabel inboxLabel = new JLabel("Inbox");
		inboxLabel.setFont(new Font("Monaco", Font.BOLD, 40));
		inboxLabel.setForeground(new Color(147, 112, 219));
		inboxLabel.setBounds(8, 25, 168, 47);
		contentPane.add(inboxLabel);
		
		// Adds Current Message title.
		JLabel currMessageLabel = new JLabel("Message");
		currMessageLabel.setFont(new Font("Monaco", Font.BOLD, 20));
		currMessageLabel.setForeground(new Color(147, 112, 219));
		currMessageLabel.setBounds(280, 40, 168, 47);
		contentPane.add(currMessageLabel);
		
		// Adds double-click label
		JLabel dClickLabel = new JLabel("Double Click to view contents");
		dClickLabel.setFont(new Font("Monaco", Font.BOLD, 9));
		dClickLabel.setForeground(new Color(54, 69, 79));
		dClickLabel.setBounds(10, 47, 168, 47);
		contentPane.add(dClickLabel);
		
		// Adds Compose button.
		JButton btnNewButton = new JButton("+ Compose");
		btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		btnNewButton.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnNewButton.setOpaque(true);
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(147, 112, 219));
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				SendMailGUI sendMail = new SendMailGUI();
				sendMail.setVisible(rootPaneCheckingEnabled);
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
							.addContainerGap())))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(14)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(scrollPane_1)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		JTextArea textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		
		DefaultListModel listModel = new DefaultListModel();
		for(int i = 0; i < emails.size(); i++)
		{
			listModel.addElement(emails.get(i));
		}
		
		JList list = new JList(listModel);
		list.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				 JList list = (JList)e.getSource();
			     if (e.getClickCount() == 2) 
			     {
			         // Double-click detected
			         int index = list.locationToIndex(e.getPoint());
			         textArea.setText(emailBody.get(index));
			     }
			}
		});
		list.setCellRenderer(new MyListCellRenderer());
		scrollPane.setViewportView(list);
		
		contentPane.setLayout(gl_contentPane);
	}
	
	// Helper class for the JList. Normally, the JList will show items on a single line; this class helps break up each item
	// while making the entire thing still selectable in the JList
	private class MyListCellRenderer extends DefaultListCellRenderer 
	{
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) 
        {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
           
            String labelText = emails.get(index);
            setText(labelText);

            return this;
        }
    }
}
