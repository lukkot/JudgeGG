package pl.gymkhana_gp.judge.model.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import pl.gymkhana_gp.judge.model.enums.PlayerClass;
import pl.gymkhana_gp.judge.model.enums.Sex;

@XmlRootElement
public class PlayerDto {
	public static int MEASUREMENT_ROUND_1 = 0;
	public static int MEASUREMENT_ROUND_2 = 1;
	public static int MEASUREMENT_ROUND_SIZE = 2;

	public static final PlayerDto NULL_PLAYER_DTO = getNullPlayerDto();

	private static PlayerDto getNullPlayerDto() {
		PlayerDto playerDto = new PlayerDto(-1);
		playerDto.setFirstName("");
		playerDto.setLastName("");
		playerDto.setNick("");
		
		return playerDto;
	}

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
		startNumber = new Integer((int) getId());

		gp8Measurements = new ArrayList<>(MEASUREMENT_ROUND_SIZE);
		gp8Measurements.add(MEASUREMENT_ROUND_1, null);
		gp8Measurements.add(MEASUREMENT_ROUND_2, null);

		classicMeasurements = new ArrayList<>(MEASUREMENT_ROUND_SIZE);
		classicMeasurements.add(MEASUREMENT_ROUND_1, null);
		classicMeasurements.add(MEASUREMENT_ROUND_2, null);
	}

	public void update(PlayerDto newPlayerDto) {
		setStartNumber(newPlayerDto.getStartNumber());
		setFirstName(newPlayerDto.getFirstName());
		setLastName(newPlayerDto.getLastName());
		setNick(newPlayerDto.getNick());
		setPlayerClass(newPlayerDto.getPlayerClass());
		setSex(newPlayerDto.getSex());
		setGp8Measurements(newPlayerDto.getGp8Measurements());
		setClassicMeasurements(newPlayerDto.getClassicMeasurements());
	}

	@Override
	public boolean equals(Object obj) {
		PlayerDto player = (PlayerDto) obj;

		return player != null && player.getId() == getId();
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
