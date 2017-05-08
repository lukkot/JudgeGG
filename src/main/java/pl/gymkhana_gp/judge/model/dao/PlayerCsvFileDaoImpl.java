package pl.gymkhana_gp.judge.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import pl.gymkhana_gp.judge.model.dto.PlayerDto;
import pl.gymkhana_gp.judge.model.enums.PlayerClass;
import pl.gymkhana_gp.judge.model.enums.Sex;

@Component
public class PlayerCsvFileDaoImpl {
	public List<PlayerDto> read(String fileName) throws IOException {
		List<PlayerDto> playerDtos = new ArrayList<>();

		//new FileReader(fileName)
		Reader in = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
		Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
		for (CSVRecord record : records) {
			if (record.size() < 21) {
				continue;
			}

			try {
				PlayerDto playerDto = new PlayerDto();
				try {
					playerDto.setStartNumber(Integer.valueOf(record.get(21)));
				} catch (NumberFormatException ex) {
					playerDto.setStartNumber(0);
				}
				playerDto.setFirstName(record.get(5));
				playerDto.setLastName(record.get(6));
				playerDto.setNick(record.get(19));
				playerDto.setPlayerClass(convertCsvStringToPlayerClass(record.get(20)));
				playerDto.setSex(Sex.MALE);

				playerDtos.add(playerDto);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
		}

		return playerDtos;
	}

	private PlayerClass convertCsvStringToPlayerClass(String csvString) {
		switch (csvString) {
		case "amator":
			return PlayerClass.AMATEUR;
		case "pro":
			return PlayerClass.PRO;
		default:
			throw new IllegalArgumentException("Nieznana klasa zawodnika: (" + csvString + ")");
		}
	}
}
