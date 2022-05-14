import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.Arrays;

import javax.mail.*;
import javax.mail.internet.*;

public class MultipleAttachmentsEmailClient
{
	
	public static void main(String[] args) 
	{
		// Ask for recepient's email address.
		Scanner scnr = new Scanner(System.in);
		System.out.println("What is the recepient's email?");
		String to = scnr.nextLine();
		
		String from = "billybronconetworking2022@gmail.com";

		final String username = "billybronconetworking2022@gmail.com";
		final String password = "cs380001";

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
			
			// Create an attachment array by taking file paths as strings.
			System.out.println("Attach a file (give file path, or type 'none' for no attachment): ");
			String fileData = scnr.nextLine();
			String[] attachmentList = new String[5];
			Arrays.fill(attachmentList, null);
			int attachmentCount = 0;
			
			// Allows up to five attachments to be included with email.
			while(!(fileData.contains("none")))
			{
				attachmentList[attachmentCount] = fileData;
				attachmentCount++;
				
				if(attachmentCount == 5)
				{
					break;
				}
				System.out.println("Attach another file (give file path, or type 'none' for no attachment): ");
				fileData = scnr.nextLine();
			}

			// Create Multipart and add attachments.
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			if (attachmentList[0] != null) 
			{
				int nameCounter = 1;
				attachmentCount = 0;
				while (attachmentCount < 5 && attachmentList[attachmentCount] != null) 
				{
					MimeBodyPart attachPart = new MimeBodyPart();
					
					String filePath = attachmentList[attachmentCount];
					
					try 
					{
						attachPart.attachFile(filePath);
					} 
					catch (IOException ex) 
					{
						ex.printStackTrace();
					}

					System.out.println("Set name for your #" + nameCounter + " attachment: ");
					String fileName = scnr.nextLine();
					
					attachPart.setFileName(fileName);
					nameCounter++;
					attachmentCount++;
					multipart.addBodyPart(attachPart);
				}
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
