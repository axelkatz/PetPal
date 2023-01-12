package fr.eql.akatz.demo.petpal.business.impl;

import fr.eql.akatz.demo.petpal.business.GlossaryBusiness;
import fr.eql.akatz.demo.petpal.dao.GlossaryDao;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.pattern.RegexReplacement;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

@Remote(GlossaryBusiness.class)
@Stateless
public class GlossaryBusinessImpl implements GlossaryBusiness {

	private static final Logger logger = LogManager.getLogger();

	private static final int CONNECT_TIMEOUT = 15000;
	private static final int READ_TIMEOUT = 60000;

	@EJB
	private GlossaryDao glossaryDao;

	@Override
	public List<String> findGlossary() {
		return glossaryDao.findAll();
	}

	@Override
	public String fetchExtract(String expression){
		String extract = "";
		try {
			URL url = new URL("https://fr.wikipedia.org/w/api.php?action=query&titles="
					+ expression.replace(" ", "+")
					+ "&prop=extracts&explaintext&exintro=&utf8&format=json");
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setConnectTimeout(CONNECT_TIMEOUT);
			httpConn.setReadTimeout(READ_TIMEOUT);
			httpConn.setRequestMethod("GET");

			int status = httpConn.getResponseCode();
			if (status == HttpURLConnection.HTTP_OK) {
				ByteArrayOutputStream result = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int length;
				while ((length = httpConn.getInputStream().read(buffer)) != -1) {
					result.write(buffer, 0, length);
				}
				String response = result.toString("UTF-8");
				httpConn.disconnect();

				JSONObject jsonBody = new JSONObject(response);

				JSONObject pages = jsonBody
						.getJSONObject("query")
						.getJSONObject("pages");

				Iterator<String> pageIdKeys = pages.keys();
				String pageIdKey = "";
				while (pageIdKeys.hasNext()) {
					pageIdKey = pageIdKeys.next();
				}
				extract = pages
						.getJSONObject(pageIdKey)
						.getString("extract")
						.replace("==", "\n");
			} else {
				throw new IOException("Status non-OK retourné par le serveur : " + status);
			}
		} catch (IOException e) {
			logger.error("Une erreur s'est produite lors de la récupération de l'extrait Wikipedia.", e);
		} catch (JSONException e) {
			logger.warn("Pas d'extrait disponible pour cette expression.");
			extract = "Pas de définition pour cette expression.";
		}
		return extract;
	}
}
