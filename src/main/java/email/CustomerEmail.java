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

public class CustomerEmail {
	public static void sendMail(String recepient, String productID) throws Exception {
		System.out.println("Preparing to send email");
		Properties properties = new Properties();
		
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		String myAccountEmail = "spectacularbarracudas3@gmail.com";
		String password = "grogu@7337";
		
		Session session = Session.getInstance(properties, new Authenticator(){
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccountEmail, password);
			}
		});
		
		Message message = prepareMessage(session, myAccountEmail, recepient, productID);
		
		Transport.send(message);
		System.out.println("Message sent successfully");
	}
	private static Message prepareMessage(Session session, String myAccountEmail, String recepient, String productID) throws MessagingException {
		try {
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(myAccountEmail));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
		message.setSubject("Your Order Has Been Placed");
		message.setText("Thank You, valued customer for your purchase of product " + productID);
		return message;
		}catch(AddressException ex) {
			Logger.getLogger(CustomerEmail.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
}
