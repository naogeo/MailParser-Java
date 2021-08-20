import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Mail {

	ArrayList<String> searchList = new ArrayList<String>();
	HashMap<String, String> webLinks = new HashMap<String, String>();
	HashMap<String, Integer> webPage = new HashMap<String, Integer>();
	ArrayList<String> test = new ArrayList<String>();

	ArrayList<String> Pages = new ArrayList<String>();

	public void linkParser2021() {
		Document doc = null;
		Document doc_Links_Count = null;
		String link = "https://www.wlw.de/de/suche/page/1?q=metallverarbeitung";
		try {
			doc_Links_Count = Jsoup.connect(link).get();
			Elements tmp01 = doc_Links_Count
					.getElementsByClass("search-type-nav-items");
			Elements tmp02 = tmp01.select("a[href]");
			System.out.println(tmp02.text());
			String str = tmp02.text();
			String[] split = str.split(" ");
			int count = Integer.valueOf(split[0]);
			System.out.println(count);
			int webPageCount = count / 30;
			System.out.println(webPageCount);

			for (int i = 1; i <= webPageCount+1; i++) {
				if (i == 1) {
					try {
						doc = Jsoup.connect(link).get();
						Elements tmp03 = doc
								.getElementsByClass("company-title-link");
						Elements tmp04 = tmp03.select("a[href]");
						for (int j = 0; j < 30; j++) {
							// System.out.println(tmp01.toString());
							// System.out.println(tmp02.get(i).text());
							// System.out.println(tmp02.get(i).attr("abs:href").toString());
							mailParser2021(tmp04.get(j).attr("abs:href")
									.toString());
						}
					} catch (IOException e) {
					}
				} else if (i == webPageCount) {
					try {
						doc = Jsoup.connect(

								"https://www.wlw.de/de/suche/page/" + i
										+ "?q=metallverarbeitung").get();
						Elements tmp03 = doc
								.getElementsByClass("company-title-link");
						Elements tmp04 = tmp03.select("a[href]");
						for (int j = 0; j < ((count-webPageCount)%30); j++) {
							// System.out.println(tmp01.toString());
							// System.out.println(tmp02.get(i).text());
							// System.out.println(tmp02.get(i).attr("abs:href").toString());
							mailParser2021(tmp04.get(j).attr("abs:href")
									.toString());
						}
					} catch (IOException e) {
					}
				} else {
					try {
						doc = Jsoup.connect(

								"https://www.wlw.de/de/suche/page/" + i
										+ "?q=metallverarbeitung").get();
						Elements tmp03 = doc
								.getElementsByClass("company-title-link");
						Elements tmp04 = tmp03.select("a[href]");
						for (int j = 0; j < 30; j++) {
							// System.out.println(tmp01.toString());
							// System.out.println(tmp02.get(i).text());
							// System.out.println(tmp02.get(i).attr("abs:href").toString());
							mailParser2021(tmp04.get(j).attr("abs:href")
									.toString());
						}
					} catch (IOException e) {
					}

				}
			}
		} catch (IOException e) {
		}

	}

	public void mailParser2021(String link) {
		Document doc = null;
		try {
			doc = Jsoup.connect(link).get();
			Elements tmp01 = doc
					.getElementsByClass("location-and-contact__button nav-link");
			Elements tmp02 = tmp01.select("span");
			System.out.println(tmp02.text());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		Mail pars = new Mail();
		pars.linkParser2021();
	}

	public void linkParser() {
		Document doc = null;
		Elements links = null;
		Elements tmp5 = null;
		try {

			searchList.add("stalbau");
			// searchList.add("cnc");
			// searchList.add("metal");
			// searchList.add("maschienenbau");
			// searchList.add("edelstahl");
			// searchList.add("stahl");
			// searchList.add("");
			// searchList.add("stahl");
			// searchList.add("stahl");
			int summe = 0;
			int h = 0;
			for (int i = 0; i < searchList.size(); i++) {
				doc = Jsoup.connect(
						"https://www.wlw.de/de/kategorien?utf8=%E2%9C%93&entered_search=1&q="
								+ searchList.get(i)).get();
				tmp5 = doc.select("a[class=list-group-item]");

				for (Element element : tmp5) {
					if (Integer.valueOf(element.select("span[class=text-info]")
							.text().replaceAll("[^\\d.]", "")) > 10) {
						System.out.println(searchList.get(i) + h + " : "
								+ element.attr("href"));
						webLinks.put(searchList.get(i) + h,
								"https://www.wlw.de"
										+ element.attr("href").split("\\?")[0]);
						summe += Integer.valueOf(element
								.select("span[class=text-info]").text()
								.replaceAll("[^\\d.]", ""));
						h++;

						if (!test
								.contains(element.attr("href").split("\\?")[0])) {
							test.add(element.attr("href").split("\\?")[0]);
						}
					}

				}
			}

			System.out.println("SUMME : " + summe);

			System.out.println("Size Test: " + test.size());
			System.out.println("SIZE: " + webLinks.size());
			for (String key : webLinks.keySet()) {
				doc = Jsoup.connect(webLinks.get(key) + "?page=2").get();
				// System.out.println(webLinks.get(key)
				// +"?page=2");
				tmp5 = doc.select("ul[class=pagination]");

				Elements tmp6 = tmp5.select("li");

				tmp6.remove(tmp6.size() - 1);
				System.out.println("LAST: " + key + " : " + webLinks.get(key)
						+ " " + tmp6.last().text());

				webPage.put(key, Integer.valueOf(tmp6.last().text()));

				// System.out.println(key + " : " + webLinks.get(key) + " : " +
				// webPage.get(key) );
				// System.out.println("");

			}

			// webLinks.put("&q=Metallbau","https://www.wlw.de/de/firmen/metallbau?entered_search=1&page="
			// );
			// webLinks.put("&q=CNC-Drehteile",
			// "https://www.wlw.de/de/firmen/cnc-drehteile?page=");
			// webLinks.put("&q=cnc-dreharbeiten-im-lohn",
			// "https://www.wlw.de/de/firmen/cnc-dreharbeiten-im-lohn?page=");
			//
			//
			// webPage.put("&q=Metallbau", 2);
			// webPage.put("&q=CNC-Drehteile", 2);
			// webPage.put("&q=cnc-dreharbeiten-im-lohn", 2);

			for (String key : webLinks.keySet()) {
				System.out.println("Key: " + key);
				for (int k = 1; k < webPage.get(key); k++) {
					doc = null;

					// System.out.println("Link: " + webLinks.get(key)
					// +"?page=" + k );
					doc = Jsoup.connect(webLinks.get(key) + "?page=" + k).get();

					links = doc.select("a[class=btn btn-primary hidden-xs]");
					for (Element link : links) {
						String linkHref = link.attr("href");

						System.out.println("Linkhref: " + linkHref);
						mailParser("https://www.wlw.de/" + linkHref);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void mailParser(String link) {
		Document doc = null;
		try {

			doc = Jsoup.connect(link).get();
			Elements tmp1 = doc.select("ul[id=location-and-contact__email]");
			System.out.println(tmp1.get(1).toString());

			Elements tmp2 = tmp1.select("li");

			if (tmp2.get(1).text() != null) {
				System.out.println((new StringBuilder(tmp2.get(1).text())
						.reverse().toString()));
				System.out.println();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
