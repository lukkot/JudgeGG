package pl.gymkhana_gp.judge.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.xml.transform.StringResult;

import pl.gymkhana_gp.judge.ConfigurationBean;
import pl.gymkhana_gp.judge.model.dao.CsvScoreBoardExporter;
import pl.gymkhana_gp.judge.model.dao.HtmlScoreBoardExporter;
import pl.gymkhana_gp.judge.model.dao.PlayerCsvFileDaoImpl;
import pl.gymkhana_gp.judge.model.dao.TournamentDaoImpl;
import pl.gymkhana_gp.judge.model.dto.GymkhanaTournamentDto;
import pl.gymkhana_gp.judge.model.dto.PlayerDto;
import pl.gymkhana_gp.judge.model.enums.TournamentType;
import pl.gymkhana_gp.judge.services.ClockService;
import pl.gymkhana_gp.judge.services.IClockObserver;

@Component
public class TournamentsControllerBean {

	private static final Logger LOG = LogManager.getLogger(TournamentsControllerBean.class);

	@Autowired
	private TournamentDaoImpl tournamentDaoImpl;

	@Autowired
	private ConfigurationBean configurationBean;

	@Autowired
	private Jaxb2Marshaller jaxb2Marshaller;
	
	@Autowired
	private PlayerCsvFileDaoImpl playerCsvFileDaoImpl;
	
	@Autowired
	private ClockService externalClockDaoImpl;

	@Autowired
	private HtmlScoreBoardExporter htmlScoreBoardExporter;

	@Autowired
	private CsvScoreBoardExporter csvScoreBoardExporter;

	public void saveData() {
		StringResult result = new StringResult();
		jaxb2Marshaller.marshal(tournamentDaoImpl.getGymkhanaTournamentData(), result);

		if(writeToFile(result.toString(), configurationBean.getXmlBackupFilePath())) {
			writeToFile(result.toString(), configurationBean.getXmlFilePath());
		}
	}
	
	private boolean writeToFile(String xmlBody, String filePath) {
		Path path = Paths.get(filePath);
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			writer.write(xmlBody);
		} catch (IOException e) {
			LOG.error("Problem with saving data to file.", e);
			
			return false;
		}
		
		return true;
	}

	public void loadData() {
		GymkhanaTournamentDto gymkhanaTournamentDto = (GymkhanaTournamentDto) jaxb2Marshaller
				.unmarshal(new StreamSource(new File(configurationBean.getXmlFilePath())));
		
		tournamentDaoImpl.setGymkhanaTournamentData(gymkhanaTournamentDto);
	}
	
	public void importPlayerDtos(String fileName) throws IOException {
		List<PlayerDto> playerDtos = playerCsvFileDaoImpl.read(fileName);
		
		for(PlayerDto playerDto : playerDtos) {
			tournamentDaoImpl.addPlayerData(playerDto);
		}

		saveData();
	}
	
	public void addClockListener(IClockObserver observer) {
		externalClockDaoImpl.addClockListener(observer);
	}
	
	public void removeClockListener(IClockObserver observer) {
		externalClockDaoImpl.removeClockListener(observer);
	}

	public List<PlayerDto> getAllPlayersData() {
		return tournamentDaoImpl.getAllPlayersData(null);
	}

	public List<PlayerDto> getAllPlayers(TournamentType tournamentType) {
		return tournamentDaoImpl.getAllPlayersData(tournamentType);
	}

	public List<PlayerDto> getPlayersWaiting(TournamentType tournamentType, int roundNumber) {
		return tournamentDaoImpl.getPlayersWaiting(tournamentType, roundNumber);
	}

	public PlayerDto getPlayerCurrent(TournamentType tournamentType) {
		return tournamentDaoImpl.getPlayerCurrent(tournamentType);
	}

	public void setPlayerCurrent(TournamentType tournamentType, PlayerDto player) {
		tournamentDaoImpl.setPlayerCurrent(tournamentType, player);
	}
	
	public void addPlayerData(PlayerDto playerData) {
		tournamentDaoImpl.addPlayerData(playerData);

		saveData();
	}

	public void removePlayerData(final long id) {
		tournamentDaoImpl.removePlayerData(id);

		saveData();
	}

	public void updatePlayerData(final PlayerDto newPlayerDto) {
		updatePlayerData(newPlayerDto, TournamentType.NO_TOURNAMENT);
	}

	public void updatePlayerData(final PlayerDto newPlayerDto, final TournamentType tournamentType) {
		tournamentDaoImpl.updatePlayerData(newPlayerDto, tournamentType);

		saveData();
	}

	public String generateScoreBoardHtml(TournamentType tournamentType) {
		return htmlScoreBoardExporter.createScoreBoardHtmlTable(getAllPlayers(tournamentType), tournamentType);
	}

	public String generateScoreBoardCsv(TournamentType tournamentType) {
		return csvScoreBoardExporter.createScoreBoardCsvBody(getAllPlayers(tournamentType), tournamentType);
	}
}
