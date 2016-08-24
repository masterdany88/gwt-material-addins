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
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

import gwt.material.design.addins.client.MaterialAddins;
import gwt.material.design.addins.client.calendar.ItemSaveEvent.ItemSaveEventHandler;
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

import java.text.ParseException;
import java.util.Date;

//@formatter:off

/**
 * Custom calendar widget with the help of http://fullcalendar.io/ and
 * https://github.com/jackyliang/Material-Design-For-Full-Calendar
 *
 * @author masterdany88
 */
// @formatter:on
public class MaterialCalendar extends MaterialWidget implements HasCalendarEventsHandlers<CalendarItem>, ItemSaveEventHandler {

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

    interface Driver extends SimpleBeanEditorDriver<CalendarItem, CalendarItemEditor> {
    }

    private Driver driver = GWT.create(Driver.class);
    private CalendarItemEditor editor;

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
        editor = new CalendarItemEditor();
        editor.addItemSaveEventHandler(this);
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
                    that.@gwt.material.design.addins.client.calendar.MaterialCalendar::newItemClickedHandler(*)(date.format());
                },
                eventClick: function(calEvent, jsEvent, view) {
                    that.@gwt.material.design.addins.client.calendar.MaterialCalendar::editTtemClickedHandler(*)(calEvent.id, calEvent.title, calEvent.allDay, calEvent.start, calEvent.end);
                    $('#fullCalendar').fullCalendar('updateEvent', calEvent);
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
    public void newItemClickedHandler(String stringDate) {
        Date date = DateTimeFormat.getFormat("yyyy-MM-dd").parse(stringDate);
        CalendarItem item = new CalendarItem(date);
        edit(item);
    }

    @Override
    public void editTtemClickedHandler(int id, String title, boolean allDay, Date start, Date end) {
        CalendarItem item = new CalendarItem(Long.valueOf(id), title, allDay, start, end);
        edit(item);
    }

    private void edit(CalendarItem e) {
        GWT.log(e.toString());
//        editor.startDate.setDate(e.getStartDate());
//        editor.endDate.setDate(e.getEndDate());
        driver.initialize(editor);
        driver.edit(e);
        editor.openWindow();
    }

    @Override
    public void onItemSave(ItemSaveEvent event) {
        MaterialToast.fireToast("ontest");
        CalendarItem edited = driver.flush();
        if (driver.hasErrors()) {
            MaterialToast.fireToast("Errors");
        }
        MaterialToast.fireToast("OK. No Errors");
        GWT.log(edited.toString());
    }

    private boolean isInitialize() {
        return initialize;
    }

    private void setInitialize(boolean initialize) {
        this.initialize = initialize;
    }

}