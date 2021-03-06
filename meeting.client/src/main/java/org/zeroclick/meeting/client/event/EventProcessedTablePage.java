package org.zeroclick.meeting.client.event;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.userfilter.TextColumnUserFilterState;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.zeroclick.meeting.client.event.EventProcessedTablePage.Table;
import org.zeroclick.meeting.shared.event.IEventService;
import org.zeroclick.meeting.shared.eventb.EventsTablePageData;

@Data(EventsTablePageData.class)
public class EventProcessedTablePage extends AbstractEventsTablePage<Table> {

	// private static final Logger LOG =
	// LoggerFactory.getLogger(EventProcessedTablePage.class);

	@Override
	protected void execLoadData(final SearchFilter filter) {
		this.importPageData(BEANS.get(IEventService.class).getEventProcessedTableData(filter));
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("zc.meeting.eventsProccessed");
	}

	public class Table extends AbstractEventsTablePage<Table>.Table {

		@Override
		protected void initConfig() {
			super.initConfig();
			this.addDefaultFilters();
		}

		protected void addDefaultFilters() {
			if (null == this.getUserFilterManager()) {
				this.setUserFilterManager(this.createUserFilterManager());
			}
			if (this.getUserFilterManager().getFilter(this.getStateColumn().getColumnId()) == null) {
				final TextColumnUserFilterState askedFilter = new TextColumnUserFilterState(this.getStateColumn());
				final Set<Object> selectedValues = new HashSet<>();
				selectedValues.add(TEXTS.get("zc.meeting.state.Accepted"));
				selectedValues.add(TEXTS.get("zc.meeting.state.refused"));
				askedFilter.setSelectedValues(selectedValues);
				// askedFilter.setFreeText(TEXTS.get("EventsProccessed"));
				this.getUserFilterManager().addFilter(askedFilter);
			}
		}

		@Override
		protected String getConfiguredTitle() {
			return TEXTS.get("zc.meeting.eventsProccessed");
		}

	}
}
