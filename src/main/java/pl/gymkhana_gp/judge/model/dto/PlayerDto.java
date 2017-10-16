package pl.gymkhana_gp.judge.model.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import pl.gymkhana_gp.judge.model.enums.PlayerClass;
import pl.gymkhana_gp.judge.model.enums.Sex;
import pl.gymkhana_gp.judge.model.enums.TournamentType;

@XmlRootElement
public class PlayerDto {
	private static final int MEASUREMENT_ROUND_1 = 0;
	private static final int MEASUREMENT_ROUND_2 = 1;
	private static final int MEASUREMENT_ROUND_SIZE = 2;

	private static long idSequence = 0;

	private final long id;

	private Integer startNumber;
	private String firstName = "Imię";
	private String lastName = "Nazwisko";
	private String nick = "Ksywa";
	private PlayerClass playerClass = PlayerClass.AMATEUR;
	private Sex sex = Sex.MALE;

	private List<FullMeasurementDto> gp8Measurements;
	private List<FullMeasurementDto> classicMeasurements;

	public PlayerDto() {
		this(++idSequence);
	}

	public PlayerDto(long id) {
		this.id = id;
		startNumber = (int) getId();

		gp8Measurements = new ArrayList<>(MEASUREMENT_ROUND_SIZE);
		gp8Measurements.add(MEASUREMENT_ROUND_1, new FullMeasurementDto());
		gp8Measurements.add(MEASUREMENT_ROUND_2, new FullMeasurementDto());

		classicMeasurements = new ArrayList<>(MEASUREMENT_ROUND_SIZE);
		classicMeasurements.add(MEASUREMENT_ROUND_1, new FullMeasurementDto());
		classicMeasurements.add(MEASUREMENT_ROUND_2, new FullMeasurementDto());
	}

	public void update(final PlayerDto newPlayerDto, final TournamentType tournamentType) {
		setStartNumber(newPlayerDto.getStartNumber());
		setFirstName(newPlayerDto.getFirstName());
		setLastName(newPlayerDto.getLastName());
		setNick(newPlayerDto.getNick());
		setPlayerClass(newPlayerDto.getPlayerClass());
		setSex(newPlayerDto.getSex());

		switch (tournamentType) {
			case GP8:
				setGp8Measurements(newPlayerDto.getGp8Measurements());
				break;
			case CLASSIC_PRO:
			case CLASSIC_AMATEUR:
				setClassicMeasurements(newPlayerDto.getClassicMeasurements());
				break;
			default:
				break;
		}
	}

	public List<FullMeasurementDto> getMeasurementByTournamentType(TournamentType tournamentType) {
		switch (tournamentType) {
			case GP8:
				return getGp8Measurements();
			case CLASSIC_PRO:
			case CLASSIC_AMATEUR:
				return getClassicMeasurements();
			default:
				throw new IllegalStateException("Wrong tournament type: " + tournamentType);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;

		PlayerDto player = (PlayerDto) obj;

		return player.getId() == getId();
	}

	public long getId() {
		return id;
	}

	@XmlElement
	public Integer getStartNumber() {
		return startNumber;
	}

	public void setStartNumber(Integer startNumber) {
		this.startNumber = startNumber;
	}

	@XmlElement
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@XmlElement
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@XmlElement
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	@XmlElement
	public PlayerClass getPlayerClass() {
		return playerClass;
	}

	public void setPlayerClass(PlayerClass playerClass) {
		this.playerClass = playerClass;
	}

	@XmlElement
	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	@XmlElementWrapper
	@XmlElement(name = "measurement", nillable=true)
	public List<FullMeasurementDto> getGp8Measurements() {
		return gp8Measurements;
	}

	public void setGp8Measurements(List<FullMeasurementDto> gp8Measurements) {
		if ((gp8Measurements != null) && (!gp8Measurements.isEmpty())) {
			this.gp8Measurements = gp8Measurements;
		}
	}

	@XmlElementWrapper
	@XmlElement(name = "measurement", nillable=true)
	public List<FullMeasurementDto> getClassicMeasurements() {
		return classicMeasurements;
	}

	public void setClassicMeasurements(List<FullMeasurementDto> classicMeasurements) {
		this.classicMeasurements = classicMeasurements;
	}
}
