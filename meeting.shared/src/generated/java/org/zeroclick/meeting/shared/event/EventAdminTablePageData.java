package org.zeroclick.meeting.shared.event;

import javax.annotation.Generated;

import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.zeroclick.meeting.shared.eventb.AbstractEventsTablePageData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications
 * recommended.
 */
@Generated(value = "org.zeroclick.meeting.client.event.EventAdminTablePage", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class EventAdminTablePageData extends AbstractEventsTablePageData {

	private static final long serialVersionUID = 1L;

	@Override
	public EventAdminTableRowData addRow() {
		return (EventAdminTableRowData) super.addRow();
	}

	@Override
	public EventAdminTableRowData addRow(int rowState) {
		return (EventAdminTableRowData) super.addRow(rowState);
	}

	@Override
	public EventAdminTableRowData createRow() {
		return new EventAdminTableRowData();
	}

	@Override
	public Class<? extends AbstractTableRowData> getRowType() {
		return EventAdminTableRowData.class;
	}

	@Override
	public EventAdminTableRowData[] getRows() {
		return (EventAdminTableRowData[]) super.getRows();
	}

	@Override
	public EventAdminTableRowData rowAt(int index) {
		return (EventAdminTableRowData) super.rowAt(index);
	}

	public void setRows(EventAdminTableRowData[] rows) {
		super.setRows(rows);
	}

	public static class EventAdminTableRowData extends AbstractEventsTableRowData {

		private static final long serialVersionUID = 1L;
	}
}
