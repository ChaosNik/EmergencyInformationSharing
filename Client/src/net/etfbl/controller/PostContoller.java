package net.etfbl.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import net.etfbl.beans.UserBean;
import net.etfbl.dao.ImageDAO;
import net.etfbl.dao.PostCategoryDAO;
import net.etfbl.dao.PostDAO;
import net.etfbl.dao.PostHasPostCategory;
import net.etfbl.dao.UserDAO;
import net.etfbl.dto.Post;
import net.etfbl.dto.User;

@WebServlet("/Post")
public class PostContoller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private Properties properties = System.getProperties();

	private Session session = Session.getDefaultInstance(properties);

	public PostContoller() {
		super();
		properties.setProperty("mail.smtp.host", "localhost");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("userBean");
		if ((userBean != null) && (userBean.isLoggedIn())) {
			String jsonText = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			JSONObject jsonObject = new JSONObject(jsonText);
			createDanger(jsonObject, request, userBean);
		}
	}

	private void createDanger(JSONObject jsonObject, HttpServletRequest request, UserBean userBean) {
		String description = null, location = null, video = null, link = null;
		List<String> images = null;
		List<Integer> categoriesId = null;

		if (!jsonObject.isNull("description")) {
			description = jsonObject.getString("description");
		}
		if (!jsonObject.isNull("location")) {
			location = jsonObject.getString("location");
		}
		if (!jsonObject.isNull("checkboxesChecked")) {
			JSONArray jsonCategoriesId = jsonObject.getJSONArray("checkboxesChecked");
			categoriesId = new ArrayList<Integer>();
			for (int i = 0; i < jsonCategoriesId.length(); i++)
				categoriesId.add(jsonCategoriesId.getInt(i)); // lista ID-eva kategorija kojoj objava pripada
		}
		if (!jsonObject.isNull("images")) {
			JSONArray jsonImages = jsonObject.getJSONArray("images");
			images = new ArrayList<>();
			for (int i = 0; i < jsonImages.length(); i++)
				images.add(jsonImages.getString(i));
		}
		if (!jsonObject.isNull("video")) {
			video = jsonObject.getString("video");
		}
		if (!jsonObject.isNull("link")) {
			link = jsonObject.getString("link").toString();
		}

		Integer isEmergency = jsonObject.getBoolean("isEmergency") ? 1 : 0;
		User postCreator = userBean.getUser();

		java.util.Date postTime = new java.util.Date();
		Post post = new Post(description, postCreator, postTime, location, video, isEmergency, link);
		post.setImages(images);
		post.setUserId(userBean.getUser().getId());
		Integer postId = PostDAO.insert(post);
		ImageDAO.insert(images, postId);
		PostHasPostCategory.insert(categoriesId, postId);
		if (isEmergency == 1) {
			sendMailToUsers(post, userBean.getUser().getMail(), userBean.getUser().getPassword());
		}
	}

	private void sendMailToUsers(Post post, String senderMail, String senderPassword) {
		senderMail = "nikola.karpic95@gmail.com";
		senderPassword = "dinhrkwkvdevxubs";
		List<String> usersMails = UserDAO.getUsersMailsForEmergencyMail(senderMail);
		for (String recieverMail : usersMails) {
		    sendMail(senderMail, senderPassword, recieverMail, post);
		}
		System.out.println("Sent messages successfully....");
	}

	private void sendMail(String senderMail, String senderPassword, String recieverMail, Post post) {
		String host = "smtp.gmail.com";

		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(senderMail, senderPassword);

			}

		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(senderMail));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recieverMail));
			message.setSubject("HITNO");

			String msg = getMessageContent(post);
			message.setContent(msg, "text/html");
			Transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	public String getMessageContent(Post post) {
		String imagesHtml[] = { "" };
		String locationHtml = "";
		String descriptionHtml = "";
		Integer i = 0;
		String location;
		String description;
		String link;

		List<String> images = post.getImages();
		images.forEach(image -> {
			if (images.indexOf(image) == 0 && images.indexOf(image) == 4)
				imagesHtml[0] += "<img src=" + image + " alt='Smiley face' height='100' width='100'>";
			else
				imagesHtml[0] += "<img src=" + image + " alt='Smiley face' height='100' width='100'>";
		});
		if (imagesHtml[0] != "") {
			imagesHtml[0] += "<br><br><br>";
		}
		String linkHtml = "";

		if ((location = post.getLocation()) != null && location != "") {
			locationHtml += "<p>Lokacija: " + location + "</p>";
		}

		if ((description = post.getText()) != null) {
			descriptionHtml += "<p>Opis: " + description + "</p><br>";
		}

		if ((link = post.getLink()) != null) {
			linkHtml += "<a href=" + link + ">Pro&#269;itajte vi&#353;e</a>";
		}

		return imagesHtml[0] + locationHtml + descriptionHtml + linkHtml;
	}
}