package com.mapper.yelp;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Map.Entry;

import org.json.JSONException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class YelpQueryManager {

	private double MapCenterLatitude = 44.975667;
	private double MapCenterLongitude = -93.270793;
	
	final String LOG_TAG = "JonLee";
	
	final String REGION_TAG = "region";
	final String CENTER_TAG = "center";
	final String LATITUDE_TAG = "latitude";
	final String LONGITUDE_TAG = "longitude";
	final String TOTAL_TAG = "total";
	final String DISPLAY_PHONE_TAG = "display_phone";
	final String LOCATION_TAG = "location";
	final String BUSINESS_TAG = "businesses";
	final String ADDRESS_TAG = "address";
	final String COORDINATE_TAG = "coordinate";
	final String CITY_TAG = "city";
	final String POSTAL_CODE_TAG = "postal_code";
	final String STATE_TAG = "state_code";
	final String RATING_IMG_TAG = "rating_img_url";
	final String REVIEW_TAG = "review_count";
	final String URL_TAG = "url";
	final String NAME_TAG = "name";
	final String IMAGE_URL_TAG = "image_url";
	
	private YelpResultsResponse currentResult;

	public YelpQueryManager() throws IOException, UnsupportedEncodingException,
			JSONException {
		String consumerKey = "7vt3-VYr6iTT2h8UP1I_TQ";
		String consumerSecret = "0NE2zJcCekassEx8vneMqPHCGrE";
		String token = "8FbokVwPFOOn5UmnvZXe1ZQgPLJAxYAg";
		String tokenSecret = "6t3zlSabL5VkVIN9CNEDkbD_vNc";

		YelpService yelp = new YelpService(consumerKey, consumerSecret, token,
				tokenSecret);
		String response = yelp.search("burritos", MapCenterLatitude,
				MapCenterLongitude);

		YelpResultsResponse resp = new YelpResultsResponse();

		parseResults(resp, response);

		setCurrentResult(resp);
	}

	private void parseResults(YelpResultsResponse resp, String reader)
			throws IOException, JSONException {

		if (reader.isEmpty()) {
			System.out.println("ERROR - YELP Returned invalid results");
			return;
		}

		JsonElement jse = null;
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(reader.getBytes()), "UTF-8"));
			jse = new JsonParser().parse(in);
			in.close();

			for (final Entry<String, JsonElement> entry : jse.getAsJsonObject()
					.entrySet()) {

				final String key = entry.getKey();
				final JsonElement value = entry.getValue();

				if (key.contains(REGION_TAG)) {

					for (final Entry<String, JsonElement> centry : value
							.getAsJsonObject().entrySet()) {
						final String k = centry.getKey();
						final JsonElement v = centry.getValue();

						if (k.contains(CENTER_TAG)) {

							for (final Entry<String, JsonElement> loc : v
									.getAsJsonObject().entrySet()) {

								if (loc.getKey().contains(LATITUDE_TAG))
									resp.setLatitude(loc.getValue()
											.getAsDouble());
								else if (loc.getKey().contains(LONGITUDE_TAG))
									resp.setLongitude(loc.getValue()
											.getAsDouble());
							}

						}

					}
				} else if (key.contains(TOTAL_TAG)) {
					resp.setTotalResults(value.getAsDouble());
				}
			}

			JsonArray jsa = jse.getAsJsonObject().getAsJsonArray(BUSINESS_TAG);
			for (int i = 0; i < jsa.size(); i++) {

				final JsonElement element = jsa.get(i);

				YelpBusiness business = new YelpBusiness();

				for (final Entry<String, JsonElement> entry : element
						.getAsJsonObject().entrySet()) {

					final String key = entry.getKey();
					final JsonElement v = entry.getValue();

					if (key.contains(DISPLAY_PHONE_TAG)) {
						business.setPhoneNumber(v.getAsString());
						
					} else if (key.contains(LOCATION_TAG)) {
						// Location
						for (final Entry<String, JsonElement> centry : v
								.getAsJsonObject().entrySet()) {
							final String k1 = centry.getKey();
							final JsonElement v1 = centry.getValue();

							if (k1.contains(ADDRESS_TAG)) {
								String address = "";
								for (final JsonElement e2 : v1.getAsJsonArray()) {
									address += e2.getAsString() + " ";
								}

								business.setAddress(address);

							} else if (k1.contains(COORDINATE_TAG)) {
								for (final Entry<String, JsonElement> loc : v1
										.getAsJsonObject().entrySet()) {
									if (loc.getKey().contains(LATITUDE_TAG))
										business.setLatitude(loc.getValue()
												.getAsDouble());
									else if (loc.getKey().contains(LONGITUDE_TAG))
										business.setLongitude(loc.getValue()
												.getAsDouble());
								}
							} else if (k1.contains(CITY_TAG)) {
								business.setCity(v1.getAsString());
							} else if (k1.contains(POSTAL_CODE_TAG)) {
								business.setPostalCode(v1.getAsInt());
							} else if (k1.contains(STATE_TAG)) {
								business.setState(v1.getAsString());
							}
						}
					} else if (key.contains(RATING_IMG_TAG)) {
						business.setRatingUrl(v.getAsString());
					} else if (key.contains(REVIEW_TAG)) {
						business.setReviewCount(v.getAsInt());
					} else if (key.contains(URL_TAG)) {
						business.setUrl(v.getAsString());
					} else if (key.contains(NAME_TAG)) {
						business.setName(v.getAsString());
					} else if (key.contains(IMAGE_URL_TAG)) {
						business.setImageUrl(v.getAsString());
					}
				}

				System.out.println(business.toString());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public YelpResultsResponse getCurrentResult() {
		return currentResult;
	}

	public void setCurrentResult(YelpResultsResponse currentResult) {
		this.currentResult = currentResult;
	}
}
