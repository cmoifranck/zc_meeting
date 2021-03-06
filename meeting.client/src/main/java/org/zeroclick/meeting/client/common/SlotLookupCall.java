package org.zeroclick.meeting.client.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

public class SlotLookupCall extends LocalLookupCall<Integer> {

	private static final long serialVersionUID = 1L;

	@Override
	protected List<LookupRow<Integer>> execCreateLookupRows() {
		final List<LookupRow<Integer>> rows = new ArrayList<>();

		rows.add(new LookupRow<>(1, TEXTS.get("zc.meeting.slot.duringDay")));
		rows.add(new LookupRow<>(2, TEXTS.get("zc.meeting.slot.duringLunch")));
		rows.add(new LookupRow<>(3, TEXTS.get("zc.meeting.slot.duringNight")));
		rows.add(new LookupRow<>(4, TEXTS.get("zc.meeting.slot.duringWeekend")));

		return rows;
	}

	public ILookupRow<Integer> getDataById(final Integer searchedId) {
		final List<? extends ILookupRow<Integer>> datas = this.getDataByAll();

		final Iterator<? extends ILookupRow<Integer>> it = datas.iterator();
		while (it.hasNext()) {
			final ILookupRow<Integer> data = it.next();
			if (data.getKey().equals(searchedId)) {
				return data; // early break
			}
		}
		return null;
	}

	public String getText(final Integer searchedId) {
		String label = "";
		final ILookupRow<Integer> data = this.getDataById(searchedId);
		if (null != data) {
			label = data.getText();
		}
		return label;
	}
}
