package gwt.material.design.addins.client.calendar;

import java.util.Date;

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
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;

import gwt.material.design.addins.client.calendar.ItemSaveEvent.ItemSaveEventHandler;
import gwt.material.design.addins.client.fileuploader.base.UploadFile;
import gwt.material.design.addins.client.fileuploader.base.UploadResponse;
import gwt.material.design.addins.client.fileuploader.events.CompleteEvent;
import gwt.material.design.addins.client.timepicker.MaterialTimePicker;
import gwt.material.design.addins.client.window.MaterialWindow;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class CalendarItemEditor extends Composite implements Editor<CalendarItem>, HasHandlers {

    private static CalendarItemEditorUiBinder uiBinder = GWT.create(CalendarItemEditorUiBinder.class);
    interface CalendarItemEditorUiBinder extends UiBinder<Widget, CalendarItemEditor> {}

    @UiField
    MaterialWindow window;
    @UiField
    MaterialTextArea title;
    @UiField
    MaterialTextArea description;
    @UiField
    MaterialCheckBox allDay;
    @UiField
    MaterialDatePicker startDate, endDate;
    @UiField
    MaterialTimePicker startTime, endTime;
    @UiField
    MaterialButton cancelButton, saveButton;

    private HandlerManager handlerManager;

    public CalendarItemEditor() {
        initWidget(uiBinder.createAndBindUi(this));
        handlerManager = new HandlerManager(this);
        setUpHandlers();
    }

    private void setUpHandlers() {
        cancelButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                window.closeWindow();
            }
        });
        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                fireEvent(new ItemSaveEvent());
            }
        });
    }

    public void openWindow() {
        window.openWindow();
    }
    public void closeWindow() {
        window.closeWindow();
    }
    @Override
    public void fireEvent(GwtEvent<?> event) {
        handlerManager.fireEvent(event);
    }

    public HandlerRegistration addItemSaveEventHandler(ItemSaveEventHandler handler) {
        return handlerManager.addHandler(ItemSaveEvent.TYPE, handler);
    }
}
