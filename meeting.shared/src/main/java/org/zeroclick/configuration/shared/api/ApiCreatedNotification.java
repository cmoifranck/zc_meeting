/**
 * Copyright 2017 Djer13

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
limitations under the License.
 */
package org.zeroclick.configuration.shared.api;

import org.zeroclick.meeting.shared.calendar.ApiFormData;

/**
 * @author djer
 *
 */
public class ApiCreatedNotification extends AbstractApiNotification {

	private static final long serialVersionUID = 1291791661924835564L;

	public ApiCreatedNotification(final ApiFormData apiForm) {
		super(apiForm);
	}

}
