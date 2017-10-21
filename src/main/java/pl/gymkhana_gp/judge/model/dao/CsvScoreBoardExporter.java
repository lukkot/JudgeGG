package pl.gymkhana_gp.judge.model.dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.gymkhana_gp.judge.model.dto.PlayerDto;
import pl.gymkhana_gp.judge.model.enums.TournamentType;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@Component
public class CsvScoreBoardExporter {

	private static final Logger LOG = LogManager.getLogger(CsvScoreBoardExporter.class);

	private static final String NEW_LINE_SEPARATOR = "\n";

	@Autowired
	private ScoreBoardExporterHelper scoreBoardExporterHelper;

	public String createScoreBoardCsvBody(List<PlayerDto> players, TournamentType tournamentType) {
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR)
				.withQuoteMode(QuoteMode.ALL);

		StringWriter writer;
		CSVPrinter csvFilePrinter = null;
		String result = null;

		try {
			writer = new StringWriter();
			csvFilePrinter = new CSVPrinter(writer, csvFileFormat);

			List<String[]> data = scoreBoardExporterHelper.exportScoreBoard(players, tournamentType);
			for(String[] row : data) {
				csvFilePrinter.printRecord((Object[])row);
			}

			writer.flush();
			result = writer.toString();

		} catch (IOException e) {
			LOG.error(e);

		} finally {
			if(csvFilePrinter != null) {
				try {
					csvFilePrinter.close();
				} catch (IOException e) {
					LOG.error(e);
				}
			}
		}

		return result;
	}
}
