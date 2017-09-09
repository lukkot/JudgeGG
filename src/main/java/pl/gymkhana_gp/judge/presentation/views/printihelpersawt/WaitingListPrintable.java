package pl.gymkhana_gp.judge.presentation.views.printihelpersawt;

import pl.gymkhana_gp.judge.presentation.model.PlayerViewData;
import pl.gymkhana_gp.judge.presentation.model.TournamentBoardModel;

class WaitingListPrintable extends AbstractListPrintable {

	//KOLEJNOSC_STARTU NUMER IMIE NAZWISKO KSYWA
	//4 + 1 + 4 + 1 + 20 + 1 + 28 + 1 + 20

	private static final int POSITION = 4;
	private static final int NUMBER = 4;
	private static final int FIRST_NAME = 20;
	private static final int LAST_NAME = 28;
	private static final int NICK = 20;

	private static final int LINE_MAX_CHARACTERS = 80;

	WaitingListPrintable(TournamentBoardModel tournamentBoardModel) {
		super(tournamentBoardModel);
	}

	@Override
	String createHeader(TournamentBoardModel tournamentBoardModel) {
		return getFormattedLine("Lp.", "Nr", "Imię", "Nazwisko", "Ksywa");
	}

	@Override
	String createLine(int index, TournamentBoardModel tournamentBoardModel) {
		PlayerViewData playerViewData = tournamentBoardModel.getWaitingPlayersList().get(index);
		return getFormattedLine(Integer.toString(index+1),
				Integer.toString(playerViewData.getStartNumber().get()),
				playerViewData.getFirstName().get(),
				playerViewData.getLastName().get(),
				playerViewData.getNick().get());
	}

	private String getFormattedLine(String index, String startNumber, String firstName, String lastName, String nick) {
		return format(index, POSITION)
				+ SEPARATOR
				+ format(startNumber, NUMBER)
				+ SEPARATOR
				+ format(firstName, FIRST_NAME)
				+ SEPARATOR
				+ format(lastName, LAST_NAME)
				+ SEPARATOR
				+ format(nick, NICK);
	}

	@Override
	boolean isEndOfList(int pageIndex, int linesCount, TournamentBoardModel tournamentBoardModel) {
		return pageIndex * linesCount > tournamentBoardModel.getWaitingPlayersList().size();
	}

	@Override
	int getLineMaxCharacters() {
		return LINE_MAX_CHARACTERS;
	}
}
