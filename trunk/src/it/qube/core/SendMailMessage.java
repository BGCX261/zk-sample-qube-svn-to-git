package it.qube.core;


import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SendMailMessage {

	private static final Logger logger = LoggerFactory.getLogger(SendMailMessage.class);
	public final static String HTML = "0";
	public final static String TEXT = "1";

	private String bodyFormat;
	private MailConfig config;

	public SendMailMessage(String serverSMTP)  {
		config = new MailConfig();
		config.setServerSMTP(serverSMTP);
		this.bodyFormat = TEXT;
	}

	public SendMailMessage(String serverSMTP, int porta, String user,
			String password)  {
		this(serverSMTP);
		if (porta != 0)
			config.setPorta(porta);
		config.setUser(user);
		config.setPassword(password);
	}

	public SendMailMessage(MailConfig config) {
		super();
		this.config = config;
		this.bodyFormat = TEXT;
	}

	private void setRecipients(Message msg, List<String> adresses,
			RecipientType type) throws MessagingException {
		if (adresses != null) {
			InternetAddress[] emails = new InternetAddress[adresses.size()];
			for (int i = 0; i < adresses.size(); i++)
				emails[i] = new InternetAddress(adresses.get(i));
			msg.setRecipients(type, emails);
		}
	}

	private void setReply(Message msg, List<String> adresses) throws MessagingException {
		if (adresses != null) {
			InternetAddress[] emails = new InternetAddress[adresses.size()];
			for (int i = 0; i < adresses.size(); i++)
				emails[i] = new InternetAddress(adresses.get(i));
			msg.setReplyTo(emails);
		}
	}

	public void sendMail(String from, List<String> to, List<String> cc,
			List<String> bcc, List<String> reply, String subject,
			String text, List<String> attachment) {
		if ((to == null || to.size() == 0) && (cc == null || cc.size() == 0) && (bcc == null || bcc.size() == 0)) {
			return;
		}
		try {
			Properties props = new Properties();
			if ((config.getUser() != null) && (config.getPassword() != null))
				props.put("mail.smtp.auth", "true");
			else
				props.put("mail.smtp.host", config.getServerSMTP());
			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(false);
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			setRecipients(msg, to, Message.RecipientType.TO);
			setRecipients(msg, cc, Message.RecipientType.CC);
			setRecipients(msg, bcc, Message.RecipientType.BCC);
			setReply(msg, reply);
			msg.setSubject(subject);
			MimeBodyPart mbpText = new MimeBodyPart();
			if (TEXT.equals(bodyFormat)) {
				if (config.getCharset() == null || config.getCharset().isEmpty())
					// uso il set di caratteri standard
					mbpText.setText(text);
				else
					mbpText.setText(text, config.getCharset());
			} else if (HTML.equals(bodyFormat)) {
				// se è html non serve impostare il set di caratteri perchè è
				// già contenuto nell'html stesso
				mbpText.setContent(text, "text/html");
			}
			MimeMultipart mp = new MimeMultipart();
			mp.addBodyPart(mbpText);
			if (attachment != null) {
				for (int i = 0; i < attachment.size(); i++) {
					FileDataSource fds = new FileDataSource(attachment.get(i));
					MimeBodyPart mbpAttacch = new MimeBodyPart();
					mbpAttacch.setDataHandler(new DataHandler(fds));
					mbpAttacch.setFileName(fds.getName());
					mp.addBodyPart(mbpAttacch);
				}
			}
			msg.setContent(mp);
			Transport tr = session.getTransport("smtp");
			if ((config.getUser() != null) && (config.getPassword() != null))
				tr.connect(config.getServerSMTP(), config.getPorta(), config.getUser(), config.getPassword());
			else
				tr.connect();
			msg.saveChanges();
			tr.sendMessage(msg, msg.getAllRecipients());
			tr.close();
		} catch (MessagingException e) {
			logger.error("Error", e);
			throw new ApplicationError(e);
		} catch (Exception e) {
			logger.error("Error", e);
			throw new ApplicationError(e);
		}
	}

	public void setBodyFormat(String bodyFormat) {
		this.bodyFormat = bodyFormat;
	}
}