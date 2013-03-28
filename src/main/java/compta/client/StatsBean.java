package compta.client;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import lombok.Getter;
import lombok.Setter;

import com.google.common.collect.Lists;
import compta.persistence.common.STATS;

@ManagedBean
@SessionScoped
public class StatsBean {

	@Getter
	@Setter
	private Date	date1;

	@Getter
	@Setter
	private Date	date2;

	@Getter
	@Setter
	private STATS	stat;

	public void launchStats() {
		if (date1 == null || date2 == null || stat == null) {
			return;
		}
	}

	public List<STATS> getStats() {
		return Lists.newArrayList(STATS.values());
	}
}
