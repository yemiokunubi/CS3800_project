
/** 
 * Project by Jordan Bui, Christopher Hoang, Yemi Okunubi, Gia Quach, Kyle Tam
 *
 * Class: CS 3800.01
 *
 * Name of Program: MailClient
 *
 */

import java.util.Properties; 
import java.util.Scanner;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

public class MailClient
{
	public static String username;
	public static String password;
	public static void sendMail(String recepient, String subject, String body, String filePath) 
	{

		String to = recepient;
		// Declare sender's email address and information.
//		String from = "billybronconetworking2022@gmail.com";
//		String from = "billybronco567@yahoo.com";
		//String from = "billybronconetworking2022@outlook.com";
		
//		final String username = "billybronconetworking2022@gmail.com";
//		final String username = "billybronco567@yahoo.com";
//		final String password = "cs380001"; // Outlook uses same password
//		final String password = "fehrieoydktslbqu"; // Yahoo Mail password

		
		String emailServer = LoginGUI.emailChoice;	//email server that the user chooses
		
//		public static String host;
//		String mailStoreType = null;
//		String username = null;
//		String password = null;
		String portNumber = null;
		String from = null;
		String smtpHost = null;
		
		if(emailServer.equals("gmail")) {
//			mailStoreType = "imap";
			username = "billybronconetworking2022@gmail.com";
			from = "billybronconetworking2022@gmail.com";
			password = "cs380001";
			portNumber= "995";
			smtpHost = "smtp.gmail.com";
			
		}
		
		if(emailServer.equals("yahoo")) {
//			mailStoreType = "imap";
			username = "billybronco567@yahoo.com";
			from = username;
			password = "fehrieoydktslbqu";
			portNumber = "993";
			smtpHost = "smtp.mail.yahoo.com";
			
			
		}
		
		// Set host.
		String host = "localhost";

		// Properties to set before creating Session object.
		Properties properties = System.getProperties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
//		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.host", smtpHost);
		//properties.put("mail.smtp.host", "smtp.outlook.com");
		properties.put("mail.smtp.port", "587");		//this might need to change for outlook
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
		properties.put("mail.smtp.ssl.checkserveridentity", "true");

		// Get the Session object.
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() 
		{
			protected PasswordAuthentication getPasswordAuthentication() 
			{
				return new PasswordAuthentication(username, password);
			}
		});

		try 
		{
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject: header field.
			
			message.setSubject(subject);

			// Create the message body object.
			BodyPart messageBodyPart = new MimeBodyPart();


			messageBodyPart.setText(body + "\n");

			// Create a multipart message.
			Multipart multipart = new MimeMultipart();

			// Set text message part.
			multipart.addBodyPart(messageBodyPart);

			// Include attachment.
			messageBodyPart = new MimeBodyPart();

			// Ask user for file.
			// EX - /Users/kyle/eclipse-workspace/MailClient/bin/filename (on Apple, will be different on Windows)
			if (filePath.contains("\\") || filePath.contains("/")) { 

				DataSource source = new FileDataSource(filePath);
						
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(filePath);
				multipart.addBodyPart(messageBodyPart);
			}

			// Send the complete message parts.
			message.setContent(multipart);

			// Send message.
			Transport.send(message);

		}

		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
	}
}
