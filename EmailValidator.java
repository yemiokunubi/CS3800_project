import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

//Check if the email exist
public class EmailValidator {
    public static boolean checkMail (String email) {
        boolean isValid = false;

        try {
            InternetAddress internetAddress = new InternetAddress (email);
            internetAddress.validate();
            isValid = true;
        } catch (AddressException e) {
            return isValid;
        }

        return isValid;
    }
}
