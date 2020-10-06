package net.etfbl.rss;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import net.etfbl.dto.Post;
import net.etfbl.dto.User;

public class RSSReader {

	public static List<Post> parseRSSData() throws ParseException {
		RSSFeedParser parser = new RSSFeedParser(
				"https://europa.eu/newsroom/calendar.xml_en?field_nr_events_by_topic_tid=151");
		Feed feed = parser.readFeed();
		List<Post> rssPosts = new ArrayList<>();
		for (FeedMessage message : feed.getMessages()) {
			Post p = new Post();
			p.setText(message.getDescription());
			p.setLink(message.getLink());
			User u = new User();
			u.setUsername("EuropeanUnion");
			u.setphoto(
					"https://image.shutterstock.com/image-vector/european-union-flag-official-colors-260nw-405765847.jpg");
			u.setFirstName("");
			u.setLastName("");
			p.setCreator(u);
			DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
			Date date = df.parse(message.getPubDate());
			p.setCreationTime(date);
			p.setIsFeed(true);
			rssPosts.add(p);
		}
		return rssPosts;
	}
}
