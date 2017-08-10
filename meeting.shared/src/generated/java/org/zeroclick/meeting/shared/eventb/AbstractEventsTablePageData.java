package org.zeroclick.meeting.shared.eventb;

import java.util.Date;

import javax.annotation.Generated;

import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications
 * recommended.
 */
@Generated(value = "org.zeroclick.meeting.client.event.AbstractEventsTablePage", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public abstract class AbstractEventsTablePageData extends AbstractTablePageData {

	private static final long serialVersionUID = 1L;

	@Override
	public AbstractEventsTableRowData addRow() {
		return (AbstractEventsTableRowData) super.addRow();
	}

	@Override
	public AbstractEventsTableRowData addRow(int rowState) {
		return (AbstractEventsTableRowData) super.addRow(rowState);
	}

	@Override
	public abstract AbstractEventsTableRowData createRow();

	@Override
	public Class<? extends AbstractTableRowData> getRowType() {
		return AbstractEventsTableRowData.class;
	}

	@Override
	public AbstractEventsTableRowData[] getRows() {
		return (AbstractEventsTableRowData[]) super.getRows();
	}

	@Override
	public AbstractEventsTableRowData rowAt(int index) {
		return (AbstractEventsTableRowData) super.rowAt(index);
	}

	public void setRows(AbstractEventsTableRowData[] rows) {
		super.setRows(rows);
	}

	public abstract static class AbstractEventsTableRowData extends AbstractTableRowData {

		private static final long serialVersionUID = 1L;
		public static final String eventId = "eventId";
		public static final String organizer = "organizer";
		public static final String organizerEmail = "organizerEmail";
		public static final String guestId = "guestId";
		public static final String email = "email";
		public static final String subject = "subject";
		public static final String slot = "slot";
		public static final String duration = "duration";
		public static final String state = "state";
		public static final String startDate = "startDate";
		public static final String endDate = "endDate";
		public static final String externalIdOrganizer = "externalIdOrganizer";
		public static final String externalIdRecipient = "externalIdRecipient";
		public static final String reason = "reason";
		private Long m_eventId;
		private Long m_organizer;
		private String m_organizerEmail;
		private Long m_guestId;
		private String m_email;
		private String m_subject;
		private Integer m_slot;
		private Integer m_duration;
		private String m_state;
		private Date m_startDate;
		private Date m_endDate;
		private String m_externalIdOrganizer;
		private String m_externalIdRecipient;
		private String m_reason;

		public Long getEventId() {
			return m_eventId;
		}

		public void setEventId(Long newEventId) {
			m_eventId = newEventId;
		}

		public Long getOrganizer() {
			return m_organizer;
		}

		public void setOrganizer(Long newOrganizer) {
			m_organizer = newOrganizer;
		}

		public String getOrganizerEmail() {
			return m_organizerEmail;
		}

		public void setOrganizerEmail(String newOrganizerEmail) {
			m_organizerEmail = newOrganizerEmail;
		}

		public Long getGuestId() {
			return m_guestId;
		}

		public void setGuestId(Long newGuestId) {
			m_guestId = newGuestId;
		}

		public String getEmail() {
			return m_email;
		}

		public void setEmail(String newEmail) {
			m_email = newEmail;
		}

		public String getSubject() {
			return m_subject;
		}

		public void setSubject(String newSubject) {
			m_subject = newSubject;
		}

		public Integer getSlot() {
			return m_slot;
		}

		public void setSlot(Integer newSlot) {
			m_slot = newSlot;
		}

		public Integer getDuration() {
			return m_duration;
		}

		public void setDuration(Integer newDuration) {
			m_duration = newDuration;
		}

		public String getState() {
			return m_state;
		}

		public void setState(String newState) {
			m_state = newState;
		}

		public Date getStartDate() {
			return m_startDate;
		}

		public void setStartDate(Date newStartDate) {
			m_startDate = newStartDate;
		}

		public Date getEndDate() {
			return m_endDate;
		}

		public void setEndDate(Date newEndDate) {
			m_endDate = newEndDate;
		}

		public String getExternalIdOrganizer() {
			return m_externalIdOrganizer;
		}

		public void setExternalIdOrganizer(String newExternalIdOrganizer) {
			m_externalIdOrganizer = newExternalIdOrganizer;
		}

		public String getExternalIdRecipient() {
			return m_externalIdRecipient;
		}

		public void setExternalIdRecipient(String newExternalIdRecipient) {
			m_externalIdRecipient = newExternalIdRecipient;
		}

		public String getReason() {
			return m_reason;
		}

		public void setReason(String newReason) {
			m_reason = newReason;
		}
	}
}
