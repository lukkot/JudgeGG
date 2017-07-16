package pl.gymkhana_gp.judge.others;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.Ignore;
import org.junit.Test;

public class CommonsCsvTest {

	@Test
	@Ignore
	public void test() throws IOException {
		Reader in = new FileReader("src\\test\\resources\\g16t_registrations_list_test_data.csv");
		Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
		for (CSVRecord record : records) {
		    System.out.println(record.get(5) + " " + record.get(6) + " " + record.get(7));
		}
		
	}

}
