
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
	public static void main(String[] args) 
	{
		// Ask for recepient's email address.
		Scanner scnr = new Scanner(System.in);
		System.out.println("What is the recepient's email?");
		String to = scnr.nextLine();

		// Declare sender's email address and information.
		String from = "billybronconetworking2022@gmail.com";

		final String username = "billybronconetworking2022@gmail.com";
		final String password = "cs380001";

		// Set host.
		String host = "localhost";

		// Properties to set before creating Session object.
		Properties properties = System.getProperties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

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
			System.out.println("Type subject text: ");
			message.setSubject(scnr.nextLine());

			// Create the message body object.
			BodyPart messageBodyPart = new MimeBodyPart();

			// Ask user to compose message body.
			System.out.println("Type body text (type 'done' to finish): ");
			String bodyText;
			String resultBodyText = "";

			// Declare value for infinite while loop
			int inf = 0;
			
			// Scan all input until 'done' is read.
			while (inf != 1) 
			{
				bodyText = scnr.nextLine();
				if (bodyText.contains("done")) {
					break;
				}
				resultBodyText += bodyText + "\n";
			}

			messageBodyPart.setText(resultBodyText + "\n");

			// Create a multipart message.
			Multipart multipart = new MimeMultipart();

			// Set text message part.
			multipart.addBodyPart(messageBodyPart);

			// Include attachment.
			messageBodyPart = new MimeBodyPart();
			
			// Ask user for file.
			System.out.println("Attach a file (give file path, or type 'none' for no attachment): ");
			// EX - /Users/kyle/eclipse-workspace/MailClient/bin/filename (on Apple, will be different on Windows)
			String fileData = scnr.nextLine();
			if (!(fileData.contains("none"))) 
			{
				DataSource source = new FileDataSource(fileData);
			
				// Ask user for attachment name.
				System.out.println("Set name for your attachment: ");
				String fileName = scnr.nextLine();
				
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(fileName);
				multipart.addBodyPart(messageBodyPart);
			}

			// Send the complete message parts.
			message.setContent(multipart);

			// Send message.
			Transport.send(message);

			System.out.println("Sent message successfully....");
		}

		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
	}
}
