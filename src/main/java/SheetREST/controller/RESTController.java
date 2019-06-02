package SheetREST.controller;

import SheetREST.model.SheetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
public class RESTController {
	@Autowired
	private SheetsService service;
	@GetMapping("/sample")
	@ResponseStatus(HttpStatus.OK)
	public void writeSample() {
		try {
			service.writeSamples();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}
}
