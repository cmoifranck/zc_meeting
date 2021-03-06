package org.zeroclick.meeting.server.calendar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.clientnotification.ClientNotificationRegistry;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroclick.configuration.shared.api.ApiCreatedNotification;
import org.zeroclick.configuration.shared.api.ApiTablePageData;
import org.zeroclick.meeting.server.sql.SQLs;
import org.zeroclick.meeting.shared.calendar.ApiFormData;
import org.zeroclick.meeting.shared.calendar.CreateApiPermission;
import org.zeroclick.meeting.shared.calendar.DeleteApiPermission;
import org.zeroclick.meeting.shared.calendar.IApiService;
import org.zeroclick.meeting.shared.calendar.ReadApiPermission;
import org.zeroclick.meeting.shared.calendar.UpdateApiPermission;
import org.zeroclick.meeting.shared.event.IEventService;
import org.zeroclick.meeting.shared.event.ReadEventPermission;
import org.zeroclick.meeting.shared.security.AccessControlService;

public class ApiService implements IApiService {

	private static final Logger LOG = LoggerFactory.getLogger(ApiService.class);

	@Override
	public ApiTablePageData getApiTableData(final SearchFilter filter) {
		final ApiTablePageData pageData = new ApiTablePageData();

		String OwnerFilter = "";
		Long currentConnectedUserId = 0L;
		if (ACCESS.getLevel(new ReadEventPermission((Long) null)) != ReadEventPermission.LEVEL_ALL) {
			OwnerFilter = SQLs.OAUHTCREDENTIAL_PAGE_SELECT_FILTER_USER;
			final AccessControlService acs = BEANS.get(AccessControlService.class);
			currentConnectedUserId = acs.getZeroClickUserIdOfCurrentSubject();
		}

		final String sql = SQLs.OAUHTCREDENTIAL_PAGE_SELECT + OwnerFilter + SQLs.OAUHTCREDENTIAL_PAGE_DATA_SELECT_INTO;
		SQL.selectInto(sql, new NVPair("page", pageData), new NVPair("currentUser", currentConnectedUserId));

		return pageData;
	}

	@Override
	public ApiFormData prepareCreate(final ApiFormData formData) {
		if (!ACCESS.check(new CreateApiPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}
		LOG.warn("PrepareCreate for Api");
		return this.store(formData);
	}

	@Override
	public ApiFormData create(final ApiFormData formData) {
		if (!ACCESS.check(new CreateApiPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		Boolean isNew = Boolean.FALSE;

		if (!this.checkAlreadyExists(formData)) {
			LOG.info("Creating new API in DB for user : " + formData.getUserIdProperty().getValue() + " for provider : "
					+ formData.getProvider().getValue());
			isNew = Boolean.TRUE;
			// add a unique id if necessary
			if (null == formData.getApiCredentialId()) {
				formData.setApiCredentialId(SQL.getSequenceNextval("OAUHTCREDENTIAL_ID_SEQ"));
			}
			SQL.insert(SQLs.OAUHTCREDENTIAL_INSERT, formData);
		} else {
			final Long existingApiId = this.getApiId(formData.getUserId());
			LOG.warn("Trying to create a new API for user " + formData.getUserId()
					+ " and this user already has a configured API Key with ID : " + existingApiId);
			formData.setApiCredentialId(existingApiId);
		}
		final ApiFormData apiFormCreated = this.store(formData);

		if (isNew) {
			this.sendModifiedNotifications(apiFormCreated);
		}

		return apiFormCreated;

	}

	private void sendModifiedNotifications(final ApiFormData formData) {
		final AccessControlService acs = BEANS.get(AccessControlService.class);
		BEANS.get(ClientNotificationRegistry.class).putForUsers(acs.getUserNotificationIds(formData.getUserId()),
				new ApiCreatedNotification(formData));
	}

	private boolean checkAlreadyExists(final ApiFormData formData) {
		final Long existingApiId = this.getApiId(formData.getUserId());
		return existingApiId != null;
	}

	@Override
	public ApiFormData load(final ApiFormData formData) {
		final Long userId = formData.getUserIdProperty().getValue();
		Long oAuthId = formData.getApiCredentialIdProperty().getValue();

		LOG.debug("Loading credential by ID : " + oAuthId + ", and UserId : " + userId);

		if (null == oAuthId) {
			oAuthId = this.getApiId(userId);
		}
		if (null == oAuthId) {
			LOG.debug("No API ID found for userId : " + userId);
			return formData;
		}

		LOG.debug("Loading credential by ID : " + oAuthId);

		if (!ACCESS.check(new ReadApiPermission(oAuthId))) {
			final AccessControlService acs = BEANS.get(AccessControlService.class);
			LOG.error("User :" + acs.getUserIdOfCurrentSubject() + " (id : " + acs.getZeroClickUserIdOfCurrentSubject()
					+ " try to load Api Data with Id : " + oAuthId + " (user : " + userId + ") wich belong to User "
					+ userId + " But haven't 'ALL'/'RELATED' read permission");
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		SQL.selectInto(
				SQLs.OAUHTCREDENTIAL_SELECT + SQLs.OAUHTCREDENTIAL_FILTER_OAUTH_ID + SQLs.OAUHTCREDENTIAL_SELECT_INTO,
				formData);

		if (null == formData.getProviderData()) {
			// force load BLOB data
			final Object[][] apiProvierData = SQL.select(
					SQLs.OAUHTCREDENTIAL_SELECT_PROVIDER_DATA_ONLY + SQLs.OAUHTCREDENTIAL_FILTER_OAUTH_ID,
					new NVPair("apiCredentialId", oAuthId));
			if (apiProvierData.length == 1) {
				formData.setProviderData((byte[]) apiProvierData[0][0]);
			}
		}

		return formData;
	}

	@Override
	public ApiFormData store(final ApiFormData formData) {
		if (!ACCESS.check(new UpdateApiPermission(formData.getApiCredentialId()))) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		LOG.debug("Storing API in DB for user : " + formData.getUserIdProperty().getValue() + " for provider : "
				+ formData.getProvider().getValue());
		SQL.update(SQLs.OAUHTCREDENTIAL_UPDATE, formData);
		return formData;
	}

	@Override
	public void delete(final ApiFormData formData) {
		if (!ACCESS.check(new DeleteApiPermission(formData.getApiCredentialId()))) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		LOG.debug("Deleting API in DB for user : " + formData.getUserIdProperty().getValue() + " for provider : "
				+ formData.getProvider().getValue());

		SQL.delete(SQLs.OAUHTCREDENTIAL_DELETE, formData);
	}

	private Long getApiId(final Long userId) {
		// Warning no permission check to allow "attendee" to check "host"
		// calendar
		LOG.debug("Searching API Id for user : " + userId);

		final ApiFormData formData = new ApiFormData();
		formData.setUserId(userId);
		SQL.selectInto(SQLs.OAUHTCREDENTIAL_SELECT_API_ID + SQLs.OAUHTCREDENTIAL_FILTER_USER_ID
				+ SQLs.OAUHTCREDENTIAL_SELECT_INTO_API_ID, formData);
		return formData.getApiCredentialId();
	}

	@Override
	public Set<String> getAllUserId() {
		// FIXME Djer13 ByPassing userPermission security. Is it required ?
		// if (!ACCESS.check(new ReadApiPermission())) {
		// throw new VetoException(TEXTS.get("AuthorizationFailed"));
		// }

		final Set<String> result = new HashSet<>();
		LOG.debug("Loading all userId");

		final Object[][] queryResult = SQL.select(SQLs.OAUHTCREDENTIAL_SELECT_ALL_USER_IDS, new ApiFormData());

		for (int i = 0; i < queryResult.length; i++) {
			result.add((String) queryResult[i][0]);
		}
		return result;
	}

	@Override
	public Collection<ApiFormData> loadGoogleData() {
		// FIXME Djer13 ByPassing userPermission security. Is it required ?
		// if (!ACCESS.check(new ReadApiPermission())) {
		// throw new VetoException(TEXTS.get("AuthorizationFailed"));
		// }
		final List<ApiFormData> result = new ArrayList<>();
		LOG.debug("Loading all datas (only Binary Google Data)");

		SQL.selectInto(SQLs.OAUHTCREDENTIAL_SELECT_GOOGLE_DATA + SQLs.OAUHTCREDENTIAL_SELECT_INTO, result);
		return result;
	}

	private Long getOwner(final Long apiCredentialId) {
		final ApiFormData formData = new ApiFormData();
		formData.setApiCredentialId(apiCredentialId);
		SQL.selectInto(SQLs.OAUHTCREDENTIAL_SELECT_OWNER, formData);

		return formData.getUserId();
	}

	@Override
	public boolean isOwn(final Long apiCredentialId) {
		final AccessControlService acs = BEANS.get(AccessControlService.class);
		final Long currentUserId = acs.getZeroClickUserIdOfCurrentSubject();

		final Long apiCredentialOwner = this.getOwner(apiCredentialId);

		if (null == apiCredentialOwner) {
			LOG.error("ApiCrentialId " + apiCredentialId + " as NO owner (user_id)");
			return false;
		} else if (apiCredentialOwner.equals(currentUserId)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRelated(final Long apiCredentialId) {
		final Long apiCredentialOwner = this.getOwner(apiCredentialId);

		final IEventService eventService = BEANS.get(IEventService.class);

		final Map<Long, Integer> pendingUsers = eventService.getUsersWithPendingMeeting();

		return pendingUsers.containsKey(apiCredentialOwner) ? pendingUsers.get(apiCredentialOwner) > 0 : Boolean.FALSE;

	}

}
