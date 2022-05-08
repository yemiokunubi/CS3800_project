
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.*;

public class FetchMail 
{
	public static void main(String[] args) 
	{
		String host = "pop.gmail.com";
		String mailStoreType = "pop3";
		String username = "billybronconetworking2022@gmail.com";
		String password = "cs380001";

		ArrayList<String> emails = checkMail(host, mailStoreType, username, password);
		 for (String i : emails) {
		      System.out.println(i);
		    }
	}

	public static ArrayList<String> checkMail(String host, String storeType, String user, String password) 
	{
		ArrayList<String> emails = new ArrayList<String>();
		try 
		{
			// Properties to set before creating Session object.
			Properties properties = new Properties();

			properties.put("mail.pop3.host", host);
			properties.put("mail.pop3.port", "995");
			properties.put("mail.pop3.starttls.enable", "true");
			properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
			Session emailSession = Session.getDefaultInstance(properties);

			// Create POP3 store object and connect with the pop server.
			Store store = emailSession.getStore("pop3s");

			store.connect(host, user, password);

			// Create folder object and open it.
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			// Retrieve messages from folder in an array and print them.
			Message[] messages = emailFolder.getMessages();
			System.out.println("Your Inbox \n\nNumber of Emails: " + messages.length);

			//Adds html tags to the string so that it can be sent to the GUI to display properly
			String result;
			for (int i = 0, n = messages.length; i < n; i++) 
			{
				Message message = messages[i];
				result = "<html>---------------------------------";
				result += "<br/>Email #" + (i + 1);
				result += "<br/>Subject: " + message.getSubject();
				result += "<br/>From: " + message.getFrom()[0];
				emails.add(result);
				result = "";
			}

			// Close the store object and folder.
			emailFolder.close(false);
			store.close();
			
			

		}

		catch (NoSuchProviderException e) 
		{
			e.printStackTrace();
		} 
		catch (MessagingException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return emails;
	}
}
