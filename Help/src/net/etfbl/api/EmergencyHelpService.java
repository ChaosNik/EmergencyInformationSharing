package net.etfbl.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;

import net.etfbl.dao.EmergencyHelpDAO;
import net.etfbl.dto.EmergencyHelp;
import net.etfbl.model.User;

public class EmergencyHelpService {

	public EmergencyHelpService() {
	}

	public boolean deleteEmergencyHelpPost(int id) {
		return EmergencyHelpDAO.deletePost(id);
	}

	public String getRss() {
		SyndFeed feed = new SyndFeedImpl();
		feed.setFeedType("rss_2.0");
		feed.setTitle("test-title");
		feed.setDescription("test-description");
		feed.setLink("http://etf.unibl.org/");
		List<SyndEntry> entries = new ArrayList<>();
		List<EmergencyHelp> helps = EmergencyHelpDAO.getAll();
		for (int i = 0; i < helps.size(); i++) {
			EmergencyHelp eh = helps.get(i);
			SyndEntry item = new SyndEntryImpl();
			item.setTitle(eh.getDescription());
			item.setPublishedDate(eh.getTime());
			item.setAuthor("EmergencyAssistant");
			entries.add(item);
		}
		feed.setEntries(entries);
		try {
			return new SyndFeedOutput().outputString(feed);
		} catch (FeedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
