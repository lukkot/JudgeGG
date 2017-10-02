package pl.gymkhana_gp.judge.presentation.views.printihelpersawt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import pl.gymkhana_gp.judge.presentation.model.TournamentBoardModel;

import java.awt.print.Printable;
import java.awt.print.PrinterAbortException;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;

@Component
public class PrintingAwtController {

	private static final Logger LOG = LogManager.getLogger(PrintingAwtController.class);

	public void printWaitingList(TournamentBoardModel tournamentBoardModel) throws IOException {
		print(getWaitingListPrintable(tournamentBoardModel));
	}

	public void printScoreBoardList(TournamentBoardModel tournamentBoardModel) throws IOException {
		print(getScoreBoardListPrintable(tournamentBoardModel));
	}

	private void print(Printable printable) {
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		printerJob.setPrintable(printable);

		if(printerJob.printDialog()) {
			try {
				printerJob.print();
			} catch (PrinterAbortException e) {
				LOG.info("Printing aborted. Everything OK.");
			} catch (PrinterException e) {
				LOG.error("Error while printing.", e);
			}
		}
	}

	private AbstractListPrintable getWaitingListPrintable(TournamentBoardModel tournamentBoardModel) {
		return new WaitingListPrintable(tournamentBoardModel);
	}

	private AbstractListPrintable getScoreBoardListPrintable(TournamentBoardModel tournamentBoardModel) {
		return new ScoreBoardListPrintable(tournamentBoardModel);
	}
}
