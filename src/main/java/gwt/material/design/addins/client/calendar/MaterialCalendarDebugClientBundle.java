package gwt.material.design.addins.client.calendar;

/*
 * #%L
 * GwtMaterial
 * %%
 * Copyright (C) 2015 - 2016 GwtMaterialDesign
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.resources.client.ClientBundle.Source;

/**
 * @author masterdany88
 */
interface MaterialCalendarDebugClientBundle extends ClientBundle {

	MaterialCalendarDebugClientBundle INSTANCE = GWT.create(MaterialCalendarDebugClientBundle.class);
	
	@Source("resources/js/moment.min.js")
	TextResource momentJs();

    @Source("resources/js/fullcalendar.js")
    TextResource fullcalendarJsDebug();
    
    @Source("resources/css/fullcalendar.css")
    TextResource fullcalendarCssDebug();
    
    @Source("resources/css/materialFullCalendar.css")
    TextResource materialFullCalendarCss();
}
