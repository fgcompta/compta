package compta.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtil {

	private static final BigDecimal	HUNDRED	= BigDecimal.valueOf(100);

	public static BigDecimal addVat(final BigDecimal ht, final BigDecimal rate) {
		if (ht == null) {
			return null;
		}
		return rate == null ? ht : ht.add(multiply(ht, rate.divide(HUNDRED, 3)));
	}

	public static BigDecimal retainVat(final BigDecimal ttc, final BigDecimal rate) {
		if (ttc == null) {
			return null;
		}
		return rate == null ? ttc : divide(ttc, BigDecimal.ONE.add(divide(rate, HUNDRED, 3)));
	}

	public static BigDecimal divide(final BigDecimal numerator, final BigDecimal divisor) {
		return divide(numerator, divisor, 2);
	}

	public static BigDecimal divide(final BigDecimal numerator, final BigDecimal divisor, final int scale) {
		return numerator.divide(divisor, scale, RoundingMode.HALF_EVEN);
	}

	public static BigDecimal multiply(final BigDecimal one, final BigDecimal two) {
		return one.multiply(two).setScale(2, RoundingMode.HALF_EVEN);
	}

	public static BigDecimal round(final BigDecimal n) {
		return n == null ? null : n.setScale(2, RoundingMode.HALF_EVEN);
	}

}
