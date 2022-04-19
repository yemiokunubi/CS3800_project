 /** 
 * Project by Jordan Bui, Christopher Hoang, Yemi Okunubi, Gia Quach, Kyle Tam
 *
 * Class: CS 3800.01
 *
 * Name of Program: MailClient
 *
 */
import java.util.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailClient
{
	public static void main (String[] args)
	{
		//Initial ask for recepient email, along with senders email and login info
		Scanner scnr = new Scanner(System.in);
		System.out.println("What is the recepient's email?");
		String dst = scnr.nextLine();

		String from = "billybronconetworking2022@gmail.com";
		final String username = "billybronconetworking2022@gmail.com";
		final String password = "cs380001";

		String host = "localhost";

		//Set up before creating a Session object
		Properties properties = System.getProperties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.socketFactory.class",
							"javax.net.ssl.SSLSocketFactory");

		//Get Session object
		Session session = Session.getInstance(properties,new javax.mail.Authenticator() 
		{
            protected PasswordAuthentication getPasswordAuthentication() 
			{
               return new PasswordAuthentication(username, password);
	  		}
        });

		try 
		{
			// Create message (MimeMessage is an abstract class of Message)
			Message message = new MimeMessage(session);
		 
			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));
		 
			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(dst));
		 
			// Set subject header
			message.setSubject("Testing Subject");
		 
			// Set the message text/body of the message
			message.setText("Sample text");
	 
			// Send message command
			Transport.send(message);
	 
			System.out.println("Message sent successfully");
	 
		   } 
		   catch (MessagingException e) 
		   {
			  throw new RuntimeException(e);
		   }

	}
}