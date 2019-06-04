package SheetREST.model;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.*;

@Service
public class SheetsService {
	private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
	private static final String spreadsheetId = "187aXbNM3E5LEnSx1ENrI4ecjEuWUK4LjKRa1JdMlomU";
	private static final String sheetName = "Expenses";
	private Map<Integer, String> columns = new HashMap<>();

	/**
	 * Global instance of the scopes required by this quickstart.
	 * If modifying these scopes, delete your previously saved tokens/ folder.
	 */
	private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
	private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

	/**
	 * Creates an authorized Credential object.
	 * @param HTTP_TRANSPORT The network HTTP Transport.
	 * @return An authorized Credential object.
	 * @throws IOException If the credentials.json file cannot be found.
	 */
	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		// Load client secrets.
		InputStream in = SheetsService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
				.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
				.setAccessType("offline")
				.build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}

	public void setColumns() {
		columns.put(1, "A");
		columns.put(2, "B");
		columns.put(3, "C");
		columns.put(4, "D");
		columns.put(5, "E");
		columns.put(6, "F");
		columns.put(7, "G");
		columns.put(8, "H");
		columns.put(9, "I");
		columns.put(10, "J");
		columns.put(11, "K");
		columns.put(12, "L");
		columns.put(13, "M");
		columns.put(14, "N");
		columns.put(15, "O");
		columns.put(16, "P");
		columns.put(17, "Q");
		columns.put(18, "R");
		columns.put(19, "S");
		columns.put(20, "T");
		columns.put(21, "U");
		columns.put(22, "V");
		columns.put(23, "W");
		columns.put(24, "X");
		columns.put(25, "Y");
		columns.put(26, "Z");
	}

	public Sheets setUp() throws IOException, GeneralSecurityException {
		setColumns();
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Sheets sheets = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
				.setApplicationName(APPLICATION_NAME)
				.build();
		return sheets;
	}

	public int findColumnInteger(String date) throws IOException, GeneralSecurityException {
		int column = -1;
		Sheets sheets = setUp();
		ValueRange result = sheets.spreadsheets().values().get(spreadsheetId, "Expenses!B1:IL").execute();
		for (int i = 0; i < result.getValues().get(0).size(); i++) {
			if (result.getValues().get(0).get(i).equals(date))
				column = i + 2;
		}
		return column;
	}
	public String findColumnString(String date) throws IOException, GeneralSecurityException {
		int index = findColumnInteger(date);
		String columnString = "";
		while (index > 26) {
			String character = columns.get(index % 26);
			index = index/26;
			columnString = character + columnString;
		}
		columnString = columns.get(index) + columnString;
		return columnString;
	}
	public Cell getCell(Cell cell, String date) throws IOException, GeneralSecurityException {
		cell.setColumn(findColumnString(date));
		return cell;
	}
	public void writeCell(Cell cell) throws IOException, GeneralSecurityException {
		Sheets sheets = setUp();
		List<List<Object>> values = Arrays.asList(Arrays.asList(cell.getValue()));
		ValueRange body = new ValueRange().setValues(values);
		UpdateValuesResponse result = sheets
				.spreadsheets().values()
				.update(spreadsheetId, sheetName + "!" + cell.getColumn() + cell.getRow() + ":" + cell.getColumn(), body)
				.setValueInputOption("RAW")
				.execute();
	}
	public void writeCells(List<Cell> cells) throws IOException, GeneralSecurityException {
		for (Cell cell : cells) {
			writeCell(cell);
		}
	}
	public void writeSamples() throws IOException, GeneralSecurityException {
		Sheets sheets = setUp();
		ValueRange body = new ValueRange().setValues(sampleValues());
		AppendValuesResponse result = sheets
				.spreadsheets().values()
				.append(spreadsheetId, "Expenses!U2:Y", body)
				.setValueInputOption("RAW")
				.execute();
	}
	public static List<List<Object>> sampleValues() {
		List<List<Object>> values = Arrays.asList(
				Arrays.asList(
						new Integer(1),
						new Integer(2),
						new Integer(3),
						new Integer(4),
						new Integer(5)
				),
				Arrays.asList(
						new Integer(2),
						new Integer(4),
						new Integer(6),
						new Integer(8),
						new Integer(10)
				),
				Arrays.asList(
						new Integer(3),
						new Integer(6),
						new Integer(9),
						new Integer(12),
						new Integer(15)
				),
				Arrays.asList(
						new Integer(4),
						new Integer(8),
						new Integer(12),
						new Integer(16),
						new Integer(20)
				),
				Arrays.asList(
						new Integer(5),
						new Integer(10),
						new Integer(15),
						new Integer(20),
						new Integer(25)
				)
		);
		return values;
	}

	public static void readValues(ValueRange response) {
		List<List<Object>> values = response.getValues();
		if (values == null || values.isEmpty()) {
			System.out.println("No data found.");
		} else {
			System.out.println("Name, Major");
			List<Object> row = values.get(51);
			for (int i = 0; i < row.size(); i++) {
				System.out.println(values.get(51).get(i).toString());
			}
		}
	}
}
