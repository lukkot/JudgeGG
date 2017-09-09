package pl.gymkhana_gp.judge.presentation.views.printihelpersawt;

import org.springframework.stereotype.Component;
import pl.gymkhana_gp.judge.presentation.model.TournamentBoardModel;

import java.awt.print.Printable;
import java.awt.print.PrinterAbortException;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;

@Component
public class PrintingAwtController {

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
				// OK
			} catch (PrinterException e) {
				e.printStackTrace();
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
