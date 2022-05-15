
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.*;


public class ReadEmail 
{
	public static void main(String[] args) 
	{

		
		String emailServer = LoginGUI.emailChoice;	//email server that the user chooses
		
		String host = null;
		String mailStoreType = null;
		String username = null;
		String password = null;
		String portNumber = null;
		
		
		if(emailServer.equals("gmail")) {
			host = "imap.gmail.com";
			mailStoreType = "imap";
			username = LoginGUI.emailName;
			password = LoginGUI.password;
			portNumber= "995";
			
		}
		
		if(emailServer.equals("yahoo")) {
			host = "imap.mail.yahoo.com";
			mailStoreType = "imap";
			username = LoginGUI.emailName;
			password = LoginGUI.password;
			portNumber = "993";
			
		}

		if(emailServer.equals("outlook")) {
			host = "outlook.office365.com";
			mailStoreType = "imap";
			username = LoginGUI.emailName;
			password = LoginGUI.password;
			portNumber = "993";
			
		}

	}

	public static ArrayList<String> fetch(String imapHost, String storeType, String user, String password, String portNumber) 
	{
		ArrayList<String> emailMsgs = new ArrayList<String>();
		try 
		{
			// Properties to set before creating Session object.
			Properties properties = new Properties();
			properties.put("mail.store.protocol", "imaps");
			properties.put("mail.imap.host", imapHost);
			properties.put("mail.imap.port", portNumber);
			properties.put("mail.imap.starttls.enable", "true");
			properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
			Session emailSession = Session.getDefaultInstance(properties);

			// Create POP3 store object and connect with the pop server.
			Store store = emailSession.getStore("imaps");

			store.connect(imapHost, user, password);

			// Create folder object and open it.
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			// BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			// Retrieve messages from folder in an array and print them.
			Message[] messages = emailFolder.getMessages();
			// System.out.println("Your Inbox \n\nNumber of Emails: " + messages.length);

			for (int i = 0; i < messages.length; i++) 
			{
				Message message = messages[i];
				StringBuilder emailBody = new StringBuilder();
				writePart(message, emailBody);
				emailMsgs.add(emailBody.toString());
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
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return emailMsgs;
	}

	public static void writePart(Part p, StringBuilder str) throws Exception 
	{
		if (p instanceof Message)
		{
			// Call method writeEnvelope.
			writeEnvelope((Message) p, str);
		}

		// If content is plain text.
		if (p.isMimeType("text/plain")) 
		{
			str.append((String) p.getContent());
			str.append(System.getProperty("line.separator"));
		}
		// If content has an attachment.
		else if (p.isMimeType("multipart/*")) 
		{
			Multipart mp = (Multipart) p.getContent();
			int count = mp.getCount();
			for (int i = 0; i < count; i++)
				writePart(mp.getBodyPart(i), str);
		}
		// If content is a nested message.
		else if (p.isMimeType("message/rfc822")) 
		{
			writePart((Part) p.getContent(), str);
		}
		// If content is an inline image.
		else if (p.isMimeType("image/jpeg")) 
		{
			Object o = p.getContent();

			InputStream x = (InputStream) o;
			// Construct the required byte array
			int i;
			byte[] bArray = new byte[x.available()];
			System.out.println("x.length = " + x.available());
			while ((i = (int) ((InputStream) x).available()) > 0) 
			{
				int result = (int) (((InputStream) x).read(bArray));
				if (result == -1) 
				{
					i = 0;
					break;
				}
			}
			FileOutputStream f2 = new FileOutputStream("/tmp/image.jpg");
			f2.write(bArray);
		} 
		else if (p.getContentType().contains("image/")) 
		{
			System.out.println("content type" + p.getContentType());
			File f = new File("image" + new Date().getTime() + ".jpg");
			DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
			com.sun.mail.util.BASE64DecoderStream test = (com.sun.mail.util.BASE64DecoderStream) p.getContent();
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = test.read(buffer)) != -1) 
			{
				output.write(buffer, 0, bytesRead);
			}
		}
	}

	// Print sender, receiver, and subject of the message.
	public static void writeEnvelope(Message m, StringBuilder str) throws Exception 
	{
		Address[] a;

		// Sender
		if ((a = m.getFrom()) != null) 
		{
			for (int j = 0; j < a.length; j++)
			{
				str.append("FROM: " + a[j].toString());
				str.append(System.getProperty("line.separator"));
			}
		}

		// Receiver
		if ((a = m.getRecipients(Message.RecipientType.TO)) != null) 
		{
			for(int j = 0; j < a.length; j++)
			{
				str.append("TO: " + a[j].toString());
				str.append(System.getProperty("line.separator"));
			}	
		}

		// Subject
		if (m.getSubject() != null)
		{
			str.append("SUBJECT: " + m.getSubject());
			str.append(System.getProperty("line.separator"));
		}
	}
}
