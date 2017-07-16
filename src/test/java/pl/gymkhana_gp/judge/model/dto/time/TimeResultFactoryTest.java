package pl.gymkhana_gp.judge.model.dto.time;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by filus on 08.07.17.
 */
public class TimeResultFactoryTest {

	@Test
	public void shouldReturnDoNotStart() {
		//given
		final String dns = "DNS";

		//when
		final TimeResult<?> result = TimeResultFactory.obtainTimeResult(dns);

		//then
		assertTrue(result instanceof DoNotStart);

	}

	@Test
	public void shouldReturnNotYetStartForBlankString() {
		//given
		final String nys = "  ";

		//when
		final TimeResult<?> result = TimeResultFactory.obtainTimeResult(nys);

		//then
		assertTrue(result instanceof NotYetStarted);

	}

	@Test
	public void shouldReturnDoNotFinished() {
		//given
		final String dnf = "DNF";

		//when
		final TimeResult<?> result = TimeResultFactory.obtainTimeResult(dnf);

		//then
		assertTrue(result instanceof DoNotFinish);

	}

	@Test
	public void shouldReturnDisqualified() {
		//given
		final String dsq = "DSQ";

		//when
		final TimeResult<?> result = TimeResultFactory.obtainTimeResult(dsq);

		//then
		assertTrue(result instanceof Disqualified);

	}

	@Test
	public void shouldReturnRegisteredForTimeString() {
		//given
		final String time = "01:23,05";
		final String expected = "01:23,050";

		//when
		final TimeResult<?> result = TimeResultFactory.obtainTimeResult(time);

		//then
		assertTrue(result instanceof Registered);
		assertEquals(expected, result.getTime());
	}
}