package pl.gymkhana_gp.judge.presentation.views.printihelpersawt;

import pl.gymkhana_gp.judge.presentation.model.PlayerViewData;
import pl.gymkhana_gp.judge.presentation.model.TournamentBoardModel;

class ScoreBoardListPrintable extends AbstractListPrintable {

	//KOLEJNOSC_STARTU NUMER IMIE NAZWISKO KSYWA (SEPARATOR_CZASU CZAS KARA LACZNIE){3}
	//4 + 1 + 4 + 1 + 20 + 1 + 28 + 1 + 20 + (1 + 1 + 1 + 9 + 1 + 2 + 1 + 9)*3 = 155

	private static final int POSITION = 4;
	private static final int NUMBER = 4;
	private static final int FIRST_NAME = 20;
	private static final int LAST_NAME = 28;
	private static final int NICK = 20;
	private static final int TIME = 9;
	private static final int PENALTY = 2;

	private static final int LINE_MAX_CHARACTERS = 155;

	private static final String TIME_SEPARATOR = "|";

	ScoreBoardListPrintable(TournamentBoardModel tournamentBoardModel) {
		super(tournamentBoardModel);
	}

	@Override
	String createHeader(TournamentBoardModel tournamentBoardModel) {
		return getFormattedLine(
				"Lp.", "Nr", "Imię", "Nazwisko", "Ksywa",
				"Czas 1", "+", "Razem",
				"Czas 2", "+", "Razem",
				"Lepszy", "+", "Razem"
		);
	}

	@Override
	String createLine(int index, TournamentBoardModel tournamentBoardModel) {
		PlayerViewData playerViewData = tournamentBoardModel.getDonePlayersList().get(index);
		return getFormattedLine(Integer.toString(index+1),
				Integer.toString(playerViewData.getStartNumber().get()),
				playerViewData.getFirstName().get(),
				playerViewData.getLastName().get(),
				playerViewData.getNick().get(),
				playerViewData.getTime1().get(),
				Integer.toString(playerViewData.getPenalty1().get()),
				playerViewData.getFullMeasurement1().get(),
				playerViewData.getTime2().get(),
				Integer.toString(playerViewData.getPenalty2().get()),
				playerViewData.getFullMeasurement2().get(),
				playerViewData.getTimeBest().get(),
				Integer.toString(playerViewData.getPenaltyBest().get()),
				playerViewData.getFullMeasurementBest().get());
	}

	private String getFormattedLine(
			String index, String startNumber, String firstName, String lastName, String nick,
			String time1, String penalty1, String fullMeasurement1,
			String time2, String penalty2, String fullMeasurement2,
			String timeBest, String penaltyBest, String fullMeasurementBest
	) {
		return format(index, POSITION)
				+ SEPARATOR
				+ format(startNumber, NUMBER)
				+ SEPARATOR
				+ format(firstName, FIRST_NAME)
				+ SEPARATOR
				+ format(lastName, LAST_NAME)
				+ SEPARATOR
				+ format(nick, NICK)
				+ SEPARATOR + TIME_SEPARATOR + SEPARATOR
				// Time 1
				+ SEPARATOR
				+ format(time1, TIME)
				+ SEPARATOR
				+ format(penalty1, PENALTY)
				+ SEPARATOR
				+ format(fullMeasurement1, TIME)
				+ SEPARATOR + TIME_SEPARATOR + SEPARATOR
				// Time 2
				+ SEPARATOR
				+ format(time2, TIME)
				+ SEPARATOR
				+ format(penalty2, PENALTY)
				+ SEPARATOR
				+ format(fullMeasurement2, TIME)
				+ SEPARATOR + TIME_SEPARATOR + SEPARATOR
				// Time Best
				+ SEPARATOR
				+ format(timeBest, TIME)
				+ SEPARATOR
				+ format(penaltyBest, PENALTY)
				+ SEPARATOR
				+ format(fullMeasurementBest, TIME);
	}

	@Override
	boolean isEndOfList(int pageIndex, int linesCount, TournamentBoardModel tournamentBoardModel) {
		return pageIndex * linesCount > tournamentBoardModel.getDonePlayersList().size();
	}

	@Override
	int getLineMaxCharacters() {
		return LINE_MAX_CHARACTERS;
	}
}
