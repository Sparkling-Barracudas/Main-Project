package email;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * A customer email class that sends an email to a recepient and a the message contains a the product ID
 * 
 *
 */
public class CustomerEmail {
	public static void sendMail(String recepient, String productID) throws Exception {
		System.out.println("Preparing to send email");
		Properties properties = new Properties();
		
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		//variables to contain google account email address and password
		String myAccountEmail = "spectacularbarracudas3@gmail.com";
		String password = "grogu@7337";
		
		Session session = Session.getInstance(properties, new Authenticator(){
			// Overiding getPasswordAuthentication method to pass in an email and password instead of a URL
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccountEmail, password);
			}
		});
		Message message = prepareMessage(session, myAccountEmail, recepient, productID);
		
		Transport.send(message);
		System.out.println("Message sent successfully");
	}
	//message method that passes in the session the email address the reciepient and the product ID
	private static Message prepareMessage(Session session, String myAccountEmail, String recepient, String productID) throws MessagingException {
		try {
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(myAccountEmail));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
		
		//setting the text for the email message
		message.setSubject("Your Order Has Been Placed");
		message.setText("Thank You, valued customer for your purchase of product " + productID + ".");
		return message;
		}catch(AddressException ex) {
			Logger.getLogger(CustomerEmail.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
}
