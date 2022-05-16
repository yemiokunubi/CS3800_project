
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MailClient
{
	public static String username;
	public static String password;
	public static void sendMail(String recepient, String subject, String body, String[] filePath) 
	{

		String to = recepient;

		
		String emailServer = LoginGUI.emailChoice;	//email server that the user chooses
		
		String portNumber = null;
		String from = null;
		String smtpHost = null;
//		LoginGUI.imapName, "imaps", LoginGUI.emailName, LoginGUI.password, LoginGUI.portNum		

		if(emailServer.equals("gmail")) {
			username = LoginGUI.emailName;
			from = username;
			password = LoginGUI.password;
			portNumber= "995";
			smtpHost = "smtp.gmail.com";
			
		}
		
		if(emailServer.equals("yahoo")) {
			username = LoginGUI.emailName;
			from = username;
			password = LoginGUI.password;
			portNumber = "993";
			smtpHost = "smtp.mail.yahoo.com";
			
			
		}

		if(emailServer.equals("outlook")) {
			username = LoginGUI.emailName;
			from = username;
			password = LoginGUI.password;
			portNumber = "993";
			smtpHost = "smtp.outlook.com";
			
		}
		
		// Set host.
		String host = "localhost";

		// Properties to set before creating Session object.
		Properties properties = System.getProperties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", smtpHost);
		properties.put("mail.smtp.port", "587");		
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
			for (int i = 0; i < filePath.length; i++) {

				if (filePath[i] == null) {
					break;
				}

				if (filePath[i].contains("\\") || filePath[i].contains("/")) { 

					MimeBodyPart attachPart = new MimeBodyPart();

					try 
					{
						attachPart.attachFile(filePath[i]);
					} 
					catch (IOException ex) 
					{
						ex.printStackTrace();
					}

					multipart.addBodyPart(attachPart);
				}
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
