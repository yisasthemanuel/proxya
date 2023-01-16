package org.jlobato.imputaciones.sheets;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import lombok.extern.slf4j.Slf4j;

/** The Constant log. */

/** The Constant log. */
@Slf4j
public class GoogleAuthorizeUtil {
	
	/** The Constant API_KEY. */
	private static final String API_KEY = "AIzaSyBf3D2W-vFGTmIr8ujMdRaT6_2EfJ48LHM";
	
	/** The Constant SCOPES. */
	private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
	
	/** The Constant APPLICATION_NAME. */
	private static final String APPLICATION_NAME = "imputator";
	
	/** The Constant SPREADSHEET_ID. */
	private static final String SPREADSHEET_ID = "1AGotEBz5FH60zQUR8yPqGKb3URvQ80TgGAEwIXRR9Ok";
	
	/** The Constant RANGE. */
	private static final String RANGE = "Hoja 1";

	/** The Constant CREDENTIALS_FILE_PATH. */
	private static final String CREDENTIALS_FILE_PATH = "/google-sheets-imputator-service-account.json";

	/**
	 * Instantiates a new google authorize util.
	 */
	private GoogleAuthorizeUtil () {
	}
	
	/**
	 * Read sheet.
	 *
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void readSheet() throws GeneralSecurityException, IOException {
		ValueRange response = getSheetsServiceAccount().spreadsheets().values().get(SPREADSHEET_ID, RANGE).setKey(API_KEY).execute();
		log.info("Response: {}", response);

	}
	
	/**
	 * Gets the sheets service.
	 *
	 * @return the sheets service
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Sheets getSheetsApiKey() throws GeneralSecurityException, IOException {
		log.info("Sheet Service by ApiKey");
		Credential credentials = getCredentialsApiKey();
		return new Sheets.Builder(
				GoogleNetHttpTransport.newTrustedTransport(),
				JacksonFactory.getDefaultInstance(), credentials)
				.setApplicationName(APPLICATION_NAME)
				.build();
	}
	
	/**
	 * Gets the sheets service account.
	 *
	 * @return the sheets service account
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Sheets getSheetsServiceAccount() throws GeneralSecurityException, IOException {
		log.info("Sheet Service by Service Account");
		NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
		return new Sheets.Builder(
				httpTransport,
				jacksonFactory, getCredentialsServiceAccount(httpTransport, jacksonFactory))
				.setApplicationName(APPLICATION_NAME)
				.build();
	}
	
	/**
	 * Authorize.
	 *
	 * @return the credential
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public static Credential authorize() throws IOException, GeneralSecurityException {
		InputStream in = GoogleAuthorizeUtil.class.getResourceAsStream("/google-sheets-imputator-client-secret.json");
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new InputStreamReader(in));

		List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), clientSecrets, scopes).setDataStoreFactory(new MemoryDataStoreFactory())
				.setAccessType("offline").build();
		return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
	}

	/**
	 * Gets the credentials api key.
	 *
	 * @return the credentials api key
	 */
	public static Credential getCredentialsApiKey() {
	    return new GoogleCredential().createScoped(GoogleAuthorizeUtil.SCOPES);
	}
	
    /**
     * Gets the credentials service account.
     *
     * @param httpTransport the http transport
     * @param jsonFactory the json factory
     * @return the credentials service account
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static Credential getCredentialsServiceAccount(NetHttpTransport httpTransport, JsonFactory jsonFactory) throws IOException {
        InputStream in = GoogleAuthorizeUtil.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        return GoogleCredential.fromStream(in, httpTransport, jsonFactory).createScoped(SCOPES);
    }

}
