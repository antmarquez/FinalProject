import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class BerryMain {

	public static void main(String[] args) throws IOException {

		String rootURL = "https://pokeapi.co/api/v2/berry/";

		JSONObject root = connectingAPI(rootURL);
		int countedBerries = (int) root.get("count");
		List<Berry> berryBunch = collectingBerries(rootURL, countedBerries);
		Optional<Berry> minTimeMaxGrowthBerry = pickBerryWithMaxGrowthRate(berryBunch);
		System.out.println("The berry that grows the most for shortiest time possible is " + minTimeMaxGrowthBerry.get());
	}

	
	public static Optional<Berry> pickBerryWithMaxGrowthRate(List<Berry> berryBunch) {
		
		List<Berry> sortedByTime = berryBunch.stream().sorted((b1, b2) -> b1.berryGrowthTime - b2.berryGrowthTime)
				.collect(Collectors.toList());
		int minTime = sortedByTime.get(0).berryGrowthTime;

		List<Berry> minTimeBerry = sortedByTime.stream().filter(t -> t.getBerryGrowthTime() == minTime)
				.collect(Collectors.toList());

		Optional<Berry> minTimeMaxGrowthBerry = minTimeBerry.stream().max(Comparator.comparing(Berry::getBerrySize));
		return minTimeMaxGrowthBerry;
	}

	
	public static List<Berry> collectingBerries(String rootURL, int countedBerries)
			throws MalformedURLException, IOException {
		List<Berry> berryBunch = new ArrayList<Berry>();

		int i = 1;
		while (i <= countedBerries) {
			String URL = rootURL + i + "/";
			JSONObject loopRoot = connectingAPI(URL);
			String berryName = (String) loopRoot.get("name");
			int berrySize = (int) loopRoot.get("size");
			int berryGrowthTime = (int) loopRoot.get("growth_time");
			berryBunch.add(new Berry(berryName, berrySize, berryGrowthTime));
			i++;
		}
		return berryBunch;
	}

	public static JSONObject connectingAPI(String rootURL) throws MalformedURLException, IOException {
		URL request = new URL(rootURL);
		InputStream openStream = request.openStream();
		String response = IOUtils.toString(openStream);
		JSONObject root = new JSONObject(response);
		return root;
	}

}