package gwt.material.design.addins.client.calendar;

import com.google.gwt.core.client.GWT;

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

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.addins.client.MaterialAddins;
import gwt.material.design.addins.client.dnd.events.DragEndEvent;
import gwt.material.design.addins.client.dnd.events.DragStartEvent;
import gwt.material.design.addins.client.fileuploader.base.HasFileUpload;
import gwt.material.design.addins.client.fileuploader.base.UploadFile;
import gwt.material.design.addins.client.fileuploader.base.UploadResponse;
import gwt.material.design.addins.client.fileuploader.constants.FileMethod;
import gwt.material.design.addins.client.fileuploader.events.*;
import gwt.material.design.client.MaterialDesignBase;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.Display;
import gwt.material.design.client.ui.MaterialToast;

import java.util.Date;

//@formatter:off

/**
 * Custom calendar widget with the help of http://fullcalendar.io/ and
 * https://github.com/jackyliang/Material-Design-For-Full-Calendar
 *
 * @author masterdany88
 */
// @formatter:on
public class MaterialCalendar extends MaterialWidget implements HasCalendarEventsHandlers<CalendarEvent> {

    static {
        if (MaterialAddins.isDebug()) {
            MaterialDesignBase.injectDebugJs(MaterialCalendarDebugClientBundle.INSTANCE.momentJs());
            MaterialDesignBase.injectDebugJs(MaterialCalendarDebugClientBundle.INSTANCE.fullcalendarJsDebug());
            MaterialDesignBase.injectCss(MaterialCalendarDebugClientBundle.INSTANCE.fullcalendarCssDebug());
            MaterialDesignBase.injectCss(MaterialCalendarDebugClientBundle.INSTANCE.materialFullCalendarCss());
        } else {
            MaterialDesignBase.injectJs(MaterialCalendarClientBundle.INSTANCE.momentJs());
            MaterialDesignBase.injectJs(MaterialCalendarClientBundle.INSTANCE.fullcalendarJs());
            MaterialDesignBase.injectCss(MaterialCalendarClientBundle.INSTANCE.fullcalendarCss());
            MaterialDesignBase.injectCss(MaterialCalendarClientBundle.INSTANCE.materialFullCalendarCss());
        }
    }

    private boolean initialize = false;
    interface Driver extends SimpleBeanEditorDriver<CalendarEvent, CalendarEventEditor> {}

    private Driver driver = GWT.create(Driver.class);

    public MaterialCalendar() {
        super(Document.get().createDivElement(), "fullCalendar");
        setId("fullCalendar");
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        if (!isInitialize()) {
            initCalendar();
            setInitialize(true);
        }
    }

    @Override
    public void add(Widget child) {
        super.add(child);
    }

    public void initCalendar() {
        initCalendar(getElement());
    }

    private native void initCalendar(Element e) /*-{
        var that = this;
        $wnd.jQuery(document).ready(function() {
            $wnd.jQuery('#fullCalendar').fullCalendar({
                editable : true,
                handleWindowResize : true,
                weekends : true,
                header : {
                    left : 'prev,today,next',
                    center : 'title',
                    right : 'month,agendaWeek,agendaDay'
                },
                defaultView : 'month',
                minTime : '07:30:00', // Start time for the calendar
                maxTime : '22:00:00', // End time for the calendar
                columnFormat : {
                    week : 'ddd' // Only show day of the week names
                },
                displayEventTime : true, // Display event time
                dayClick: function(date, jsEvent, view) {
                    that.@gwt.material.design.addins.client.calendar.MaterialCalendar::unoccupiedTimeClickedHandler(*)(date.format());
                },
                eventClick: function(calEvent, jsEvent, view) {
                    that.@gwt.material.design.addins.client.calendar.MaterialCalendar::occupiedTimeClieckedHandler(*)(calEvent.title);
                },
                events : [ {
                    title : 'event1',
                    start : '2016-08-12',
                    color : '#C2185B'

                }, {
                    title : 'event2',
                    start : '2016-08-08T10:30:00',
                    end : '2016-08-09T14:15:00',
                    color : '#7B1FA2'
                }, {
                    title : 'event3',
                    start : '2016-08-11T12:30:00',
                    end : '2016-08-11T14:15:00'
                } ]
            });
        });
    }-*/;

    @Override
    public void unoccupiedTimeClickedHandler(String date) {
        openEventWindow();
    }

    private void openEventWindow() {
        edit(new CalendarEvent("New Event"));
//        CalendarEventEditor e = new CalendarEventEditor();
//        e.openWindow();
    }

    @Override
    public void occupiedTimeClieckedHandler(String title) {
        CalendarEvent event = new CalendarEvent(title);
        openEventWindow(event);
    }

    private void openEventWindow(CalendarEvent event) {
        edit(event);
    }

    void edit(CalendarEvent e) {
        CalendarEventEditor editor = new CalendarEventEditor();
        driver.initialize(editor);
        driver.edit(e);
        editor.openWindow();
    }

    void save() {
        CalendarEvent edited = driver.flush();
        if (driver.hasErrors()) {

            MaterialToast.fireToast("Errors");
        }
        MaterialToast.fireToast("OK. No Errors");
    }

    public boolean isInitialize() {
        return initialize;
    }

    public void setInitialize(boolean initialize) {
        this.initialize = initialize;
    }
}