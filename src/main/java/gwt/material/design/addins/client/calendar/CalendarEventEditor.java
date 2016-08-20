package gwt.material.design.addins.client.calendar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.timepicker.MaterialTimePicker;
import gwt.material.design.addins.client.window.MaterialWindow;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;

public class CalendarEventEditor extends Composite implements Editor<CalendarEvent> {

    private static CalendarEventEditorUiBinder uiBinder = GWT.create(CalendarEventEditorUiBinder.class);
    interface CalendarEventEditorUiBinder extends UiBinder<Widget, CalendarEventEditor> {}

    @UiField
    MaterialWindow window;
    @UiField
    MaterialTextBox title;
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

    
    public CalendarEventEditor() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void openWindow() {
        window.openWindow();
    }

}
