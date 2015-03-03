package http.functions;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class UserFunctions {
	private final JSONParser jsonParser;
	public static String URL = "http://nearfieldltd.com/mobins/index.php";
	public static String create_customer_url = "http://nearfieldltd.com/mobileinsurance/create_customer.php";
	public static String verify_policy_url = "http://nearfieldltd.com/mobileinsurance/verify_policy.php";
	public static String make_claim_url = "http://nearfieldltd.com/mobileinsurance/make_claim.php";
	private static String quote_tag = "quote";
	private static String verify_tag = "verify";

	public UserFunctions() {
		jsonParser = new JSONParser();
	}

	public JSONObject getQuote(String Surname, String First, String Last,
			String Mobile, String Total, String Email, String KRA,
			String National_ID, String Model, String Year, String Price,
			String Number_plate, String Image) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", quote_tag));
		params.add(new BasicNameValuePair("cust_surname", Surname));
		params.add(new BasicNameValuePair("cust_fname", First));
		params.add(new BasicNameValuePair("cust_lname", Last));
		params.add(new BasicNameValuePair("cust_phone", Mobile));
		params.add(new BasicNameValuePair("insurance_price", Total));
		params.add(new BasicNameValuePair("logbook", Image));
		params.add(new BasicNameValuePair("cust_email", Email));
		params.add(new BasicNameValuePair("cust_pin", KRA));
		params.add(new BasicNameValuePair("cust_id", KRA));
		params.add(new BasicNameValuePair("Model", Model));
		params.add(new BasicNameValuePair("Year", Year));
		params.add(new BasicNameValuePair("Number_plate", Number_plate));
		params.add(new BasicNameValuePair("price", Price));
		JSONObject json = jsonParser
				.getJSONFromUrl(create_customer_url, params);
		return json;
	}

	public JSONObject verifyPolicy(String PolicyNumber, String NumberPlate) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", verify_tag));
		params.add(new BasicNameValuePair("policy_id", PolicyNumber));
		params.add(new BasicNameValuePair("number_plate", NumberPlate));
		JSONObject json = jsonParser.getJSONFromUrl(verify_policy_url, params);
		return json;
	}

	public JSONObject makeClaim(String ab, String ins, String notice,
			String cust_id, String policy_id) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", verify_tag));
		params.add(new BasicNameValuePair("police_abstract", ab));
		params.add(new BasicNameValuePair("inspection_report", ins));
		params.add(new BasicNameValuePair("notice_prosecution", notice));
		params.add(new BasicNameValuePair("cust_id", cust_id));
		params.add(new BasicNameValuePair("policy_id", policy_id));
		JSONObject json = jsonParser.getJSONFromUrl(make_claim_url, params);
		return json;
	}
}