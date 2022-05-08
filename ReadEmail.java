
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

import javax.mail.*;

public class ReadEmail 
{
	public static void main(String[] args) 
	{

		String host = "pop.gmail.com";
		String mailStoreType = "pop3";
		String username = "billybronconetworking2022@gmail.com";
		String password = "cs380001";

		// Call method fetch
		fetch(host, mailStoreType, username, password);

	}

	public static void fetch(String pop3Host, String storeType, String user, String password) 
	{
		try 
		{
			// Properties to set before creating Session object.
			Properties properties = new Properties();
			properties.put("mail.store.protocol", "pop3");
			properties.put("mail.pop3.host", pop3Host);
			properties.put("mail.pop3.port", "995");
			properties.put("mail.pop3.starttls.enable", "true");
			properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
			Session emailSession = Session.getDefaultInstance(properties);

			// Create POP3 store object and connect with the pop server.
			Store store = emailSession.getStore("pop3s");

			store.connect(pop3Host, user, password);

			// Create folder object and open it.
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			// Retrieve messages from folder in an array and print them.
			Message[] messages = emailFolder.getMessages();
			System.out.println("Your Inbox \n\nNumber of Emails: " + messages.length);

			for (int i = 0; i < messages.length; i++) 
			{
				Message message = messages[i];
				System.out.println("---------------------------------");
				writePart(message);
				String line = reader.readLine();
				if ("YES".equals(line)) 
				{
					message.writeTo(System.out);
				} 
				else if ("QUIT".equals(line)) 
				{
					break;
				}
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
	}

	public static void writePart(Part p) throws Exception 
	{
		if (p instanceof Message)
		{
			// Call methos writeEnvelope
			writeEnvelope((Message) p);
		}

		System.out.println("----------------------------");

		// If content is plain text.
		if (p.isMimeType("text/plain")) 
		{
			//System.out.println("This is plain text");
			System.out.println((String) p.getContent());
		}
		// If content has an attachment.
		else if (p.isMimeType("multipart/*")) 
		{
			//System.out.println("This is a Multipart");
			Multipart mp = (Multipart) p.getContent();
			int count = mp.getCount();
			for (int i = 0; i < count; i++)
				writePart(mp.getBodyPart(i));
		}
		// If content is a nested message.
		else if (p.isMimeType("message/rfc822")) 
		{
			//System.out.println("This is a Nested Message");
			writePart((Part) p.getContent());
		}
		// If content is an inline image.
		else if (p.isMimeType("image/jpeg")) 
		{
			//System.out.println("--------> image/jpeg");
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
		// Keep remarked; this block causes the HTML messy output. Will delete later.
		/*else 
		{
			Object o = p.getContent();
			if (o instanceof String) 
			{
				System.out.println("This is a string");
				System.out.println("---------------------------");
				System.out.println((String) o);
			} 
			else if (o instanceof InputStream) 
			{
				System.out.println("This is just an input stream");
				System.out.println("---------------------------");
				InputStream is = (InputStream) o;
				is = (InputStream) o;
				int c;
				while ((c = is.read()) != -1)
					System.out.write(c);
			} 
			else 
			{
				System.out.println("This is an unknown type");
				System.out.println("---------------------------");
				System.out.println(o.toString());
			}
		}*/
		//System.exit(0);
	}

	// Print sender, receiver, and subject of the message.
	public static void writeEnvelope(Message m) throws Exception 
	{
		Address[] a;

		// Sender
		if ((a = m.getFrom()) != null) 
		{
			for (int j = 0; j < a.length; j++)
			{
				System.out.println("FROM: " + a[j].toString());
			}
		}

		// Receiver
		if ((a = m.getRecipients(Message.RecipientType.TO)) != null) 
		{
			for(int j = 0; j < a.length; j++)
			{
				System.out.println("TO: " + a[j].toString());
			}	
		}

		// Subject
		if (m.getSubject() != null)
		{
			System.out.println("SUBJECT: " + m.getSubject());
		}
	}
}
