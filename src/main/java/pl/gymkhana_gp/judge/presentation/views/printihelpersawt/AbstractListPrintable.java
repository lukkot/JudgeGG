package pl.gymkhana_gp.judge.presentation.views.printihelpersawt;

import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;
import pl.gymkhana_gp.judge.presentation.model.PlayerViewData;
import pl.gymkhana_gp.judge.presentation.model.TournamentBoardModel;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

@Component
public abstract class AbstractListPrintable implements Printable {

	static final String SEPARATOR = " ";

	private static final int MARGIN = 50;
	private static final String FONT_NAME = "Courier New";

	private TournamentBoardModel tournamentBoardModel;

	AbstractListPrintable(TournamentBoardModel tournamentBoardModel) {
		this.tournamentBoardModel = tournamentBoardModel;
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		ObservableList<PlayerViewData> waitingPlayersList = tournamentBoardModel.getWaitingPlayersList();
		Graphics2D g2d = (Graphics2D)graphics;

		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font(FONT_NAME, Font.PLAIN, getBiggestFontSize(pageFormat, g2d, FONT_NAME)));

		int fontAscent = g2d.getFontMetrics().getMaxAscent();
		int stringHeight = g2d.getFontMetrics().getHeight();
		int linesCount = getLinesCountForCurrentFont(pageFormat, g2d);
		int indexStart = pageIndex * linesCount;
		int indexEnd = waitingPlayersList.size();

		// Exit condition
		if (isEndOfList(pageIndex, linesCount, tournamentBoardModel)) {
			return NO_SUCH_PAGE;
		}

		// Printing...
		g2d.drawString(createHeader(tournamentBoardModel), MARGIN, MARGIN + fontAscent);
		for(int indexGlobal=indexStart, indexLocal=1; indexGlobal<indexEnd && indexLocal<linesCount; indexGlobal++, indexLocal++) {
			g2d.drawString(
					createLine(indexGlobal, tournamentBoardModel),
					MARGIN,
					MARGIN + fontAscent + indexLocal*stringHeight
			);
		}

		return PAGE_EXISTS;
	}

	abstract String createHeader(TournamentBoardModel tournamentBoardModel);

	abstract String createLine(int index, TournamentBoardModel tournamentBoardModel);

	abstract boolean isEndOfList(int pageIndex, int linesCount, TournamentBoardModel tournamentBoardModel);

	abstract int getLineMaxCharacters();

	private int getBiggestFontSize(PageFormat pageFormat, Graphics2D g2d, String fontName) {
		StringBuilder testLineBuilder = new StringBuilder();
		for(int i=0; i<getLineMaxCharacters(); i++) {
			testLineBuilder.append("0");
		}

		String testLine = testLineBuilder.toString();

		int i=2;
		for(; i<100; i++) {
			if(g2d.getFontMetrics(new Font(fontName, Font.PLAIN, i)).stringWidth(testLine) > getPagePrintableAreaWidth(pageFormat)) {
				return i-1;
			}
		}

		return i;
	}

	private int getPagePrintableAreaWidth(PageFormat pageFormat) {
		return (int) pageFormat.getImageableWidth() - 2*MARGIN;
	}

	private int getPagePrintableAreaHeight(PageFormat pageFormat) {
		return (int) pageFormat.getImageableHeight() - 2*MARGIN;
	}

	private int getLinesCountForCurrentFont(PageFormat pageFormat, Graphics2D g2d) {
		return getPagePrintableAreaHeight(pageFormat) / g2d.getFontMetrics().getHeight();
	}

	String format(int integer, int length) {
		return String.format("%" + length + "d", integer).substring(0, length);
	}

	String format(String string, int length) {
		return String.format("%" + length + "s", string).substring(0, length);
	}
}
