package pl.gymkhana_gp.judge.presentation.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.valuehandling.UnwrapValidatedValue;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.gymkhana_gp.judge.comparators.FullMeasurementDtoComparator;
import pl.gymkhana_gp.judge.model.dto.FullMeasurementDto;
import pl.gymkhana_gp.judge.model.dto.TimeDto;
import pl.gymkhana_gp.judge.model.enums.PlayerClass;
import pl.gymkhana_gp.judge.model.enums.Sex;

public class PlayerViewData implements ChangeListener<Object> {
	
	public static final PlayerViewData NULL_PLAYER_VIEW_DATA = getNullPlayerViewData();
	
	private static PlayerViewData getNullPlayerViewData() {
		return new PlayerViewData(-1);
	}

	public static ObservableList<String> getSexValues() {
		return FXCollections.observableArrayList(Sex.MALE.toString(), Sex.FEMALE.toString());
	}

	public static ObservableList<String> getPlayerClassValues() {
		return FXCollections.observableArrayList(PlayerClass.AMATEUR.toString(), PlayerClass.PRO.toString());
	}
	
	private List<FullMeasurementDto> measurements;

	private final long playerDataId;
	private final IntegerProperty startNumber;
	@UnwrapValidatedValue
	@NotEmpty
	private final StringProperty firstName;
	@UnwrapValidatedValue
	@NotEmpty
	private final StringProperty lastName;
	@UnwrapValidatedValue
	private final StringProperty nick;
	@UnwrapValidatedValue
	@NotEmpty
	private final StringProperty playerClass;
	@UnwrapValidatedValue
	@NotEmpty
	private final StringProperty sex;
	@UnwrapValidatedValue
	@Pattern(regexp = "(^[0-9]{2,}:[0-9]{2}.[0-9]{2,3}$)|(^DNF$)|(^DNS$)|(^DSQ$)|(^$)", message = "Wymagany format: \"099:99.990\", \"DNS\", \"DNF\", \"DSQ\" lub puste")
	private final StringProperty time1;
	private final IntegerProperty penalty1;
	private final StringProperty fullMeasurement1;
	@UnwrapValidatedValue
	@Pattern(regexp = "(^[0-9]{2,}:[0-9]{2}.[0-9]{2,3}$)|(^DNF$)|(^DNS$)|(^DSQ$)|(^$)", message = "Wymagany format: \"099:99.990\", \"DNS\", \"DNF\", \"DSQ\" lub puste")
	private final StringProperty time2;
	private final IntegerProperty penalty2;
	private final StringProperty fullMeasurement2;
	private StringProperty timeBest;
	private IntegerProperty penaltyBest;
	private StringProperty fullMeasurementBest;
	private final List<StringProperty> times;
	private final List<IntegerProperty> penalties;
	private final List<StringProperty> fullMeasurements;

	public PlayerViewData(int playerDtoId) {
		// Przepisanie pól statycznych
		playerDataId = playerDtoId;

		measurements = new ArrayList<>();
		measurements.add(null);
		measurements.add(null);
		
		// Przepisanie pól prostych
		startNumber = new SimpleIntegerProperty();
		firstName = new SimpleStringProperty("");
		lastName = new SimpleStringProperty("");
		nick = new SimpleStringProperty("");
		playerClass = new SimpleStringProperty("");
		sex = new SimpleStringProperty("");
		
		time1 = new SimpleStringProperty("");
		penalty1 = new SimpleIntegerProperty();
		fullMeasurement1 = new SimpleStringProperty("");
		
		time2 = new SimpleStringProperty("");
		penalty2 = new SimpleIntegerProperty();
		fullMeasurement2 = new SimpleStringProperty("");
		
		timeBest = new SimpleStringProperty("");
		penaltyBest = new SimpleIntegerProperty();
		fullMeasurementBest = new SimpleStringProperty("");
		
		// Spięcie czasów w listę
		times = new ArrayList<>();
		times.add(time1);
		times.add(time2);

		penalties = new ArrayList<>();
		penalties.add(penalty1);
		penalties.add(penalty2);

		fullMeasurements = new ArrayList<>();
		fullMeasurements.add(fullMeasurement1);
		fullMeasurements.add(fullMeasurement2);
	}

	private void recalculateTimes() {
		calculateFullMeasurement(0);
		calculateFullMeasurement(1);
		calculateBest();
	}

	private void calculateBest() {
		List<FullMeasurementDto> sortedMeasurements = new ArrayList<>(measurements);
		sortedMeasurements.sort(new FullMeasurementDtoComparator());
		FullMeasurementDto bestFullMeasurement = sortedMeasurements.get(0);

		if (bestFullMeasurement == null) {
			timeBest.set("");
			penaltyBest.set(0);
			fullMeasurementBest.set("");
		} else {
			timeBest.set(bestFullMeasurement.getTime().getTimeFormatted());
			penaltyBest.set((int) bestFullMeasurement.getPenalty());
			fullMeasurementBest.set(bestFullMeasurement.getTimeWithPenalty().getTimeFormatted());
		}
	}

	public StringProperty getTime(int roundNumber) {
		return times.get(roundNumber);
	}

	public void setTime(int roundNumber, String timeValue) {
		times.get(roundNumber).set(timeValue);
		measurements.set(roundNumber,
				FullMeasurementDto.getInstanceOrNull(timeValue, penalties.get(roundNumber).get()));
		recalculateTimes();
	}

	public IntegerProperty getPenalty(int roundNumber) {
		return penalties.get(roundNumber);
	}

	public void setPenalty(int roundNumber, Integer penaltyValue) {
		penalties.get(roundNumber).set(penaltyValue);
		measurements.set(roundNumber, FullMeasurementDto.getInstanceOrNull(times.get(roundNumber).get(), penaltyValue));
		recalculateTimes();
	}

	public long getPlayerDataId() {
		return playerDataId;
	}

	public IntegerProperty getStartNumber() {
		return startNumber;
	}

	public void setStartNumber(Integer startNumber) {
		this.startNumber.set(startNumber);
	}

	public StringProperty getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public StringProperty getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public StringProperty getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick.set(nick);
	}

	public StringProperty getPlayerClass() {
		return playerClass;
	}

	public void setPlayerClass(String playerClass) {
		this.playerClass.set(playerClass);
	}

	public StringProperty getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex.set(sex);
	}

	public StringProperty getTime1() {
		return time1;
	}

	public void setTime1(String timeValue) {
		setTime(0, timeValue);
	}

	public IntegerProperty getPenalty1() {
		return penalty1;
	}

	public void setPenalty1(Integer penaltyValue) {
		setPenalty(0, penaltyValue);
	}

	public StringProperty getFullMeasurement1() {
		return fullMeasurement1;
	}

	public StringProperty getTime2() {
		return time2;
	}

	public void setTime2(String timeValue) {
		setTime(1, timeValue);
	}

	public IntegerProperty getPenalty2() {
		return penalty2;
	}

	public void setPenalty2(Integer penaltyValue) {
		setPenalty(1, penaltyValue);
	}

	public StringProperty getFullMeasurement2() {
		return fullMeasurement2;
	}

	public StringProperty getTimeBest() {
		return timeBest;
	}

	public IntegerProperty getPenaltyBest() {
		return penaltyBest;
	}

	public StringProperty getFullMeasurementBest() {
		return fullMeasurementBest;
	}

	@Override
	public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
		if (observable == time1 || observable == penalty1) {
			calculateFullMeasurement(0);
			updateMeasurementsData(0);
		} else if (observable == time2 || observable == penalty2) {
			calculateFullMeasurement(1);
			updateMeasurementsData(1);
		}

		calculateBest();
	}

	private void calculateFullMeasurement(int roundNumber) {
		fullMeasurements.get(roundNumber).set(getFullMeasurement(times.get(roundNumber), penalties.get(roundNumber)));
	}

	private void updateMeasurementsData(int roundNumber) {
		if (measurements.get(roundNumber) == null) {
			return;
		}

		measurements.get(roundNumber).getTime().setTime(times.get(roundNumber).get());
		measurements.get(roundNumber).setPenalty(penalties.get(roundNumber).get());
	}

	private String getFullMeasurement(StringProperty time, IntegerProperty penalty) {
		return new FullMeasurementDto(new TimeDto(time.get()), penalty.get()).getTimeWithPenalty().getTimeFormatted();
	}
}
