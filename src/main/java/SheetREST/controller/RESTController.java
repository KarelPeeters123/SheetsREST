package SheetREST.controller;

import SheetREST.model.Cell;
import SheetREST.model.SheetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
public class RESTController {
	@Autowired
	private SheetsService service;
	@GetMapping("/sample")
	@ResponseStatus(HttpStatus.OK)
	public void writeSample() {
		try {
			service.writeSamples();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostMapping("/cell")
	@ResponseStatus(HttpStatus.OK)
	public void writeCell(@Valid @RequestBody Cell cell) {
		try {
			service.writeCell(cell);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@PostMapping("/cells")
	@ResponseStatus(HttpStatus.OK)
	public void writeCells(@Valid @RequestBody List<Cell> cells) {
		try {
			service.writeCells(cells);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
