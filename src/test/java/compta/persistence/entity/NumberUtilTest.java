package compta.persistence.entity;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import compta.common.NumberUtil;

public class NumberUtilTest {

	@Test
	public void htToTtc() {
		final BigDecimal ht = BigDecimal.valueOf(20.00);
		final BigDecimal ttc = BigDecimal.valueOf(23.92);
		final BigDecimal rate = BigDecimal.valueOf(19.6);
		final BigDecimal addVat = NumberUtil.addVat(ht, rate);
		Assert.assertEquals(ttc, addVat);
	}

	@Test
	public void ttcToht() {
		final BigDecimal ht = BigDecimal.valueOf(20.00);
		final BigDecimal ttc = BigDecimal.valueOf(23.92);
		final BigDecimal rate = BigDecimal.valueOf(19.6);
		final BigDecimal addVat = NumberUtil.retainVat(ttc, rate);
		Assert.assertTrue(ht.compareTo(addVat) == 0);
	}
}
