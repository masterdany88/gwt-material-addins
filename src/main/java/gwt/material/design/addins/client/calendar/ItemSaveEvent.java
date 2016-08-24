package gwt.material.design.addins.client.calendar;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;


public class ItemSaveEvent extends GwtEvent<ItemSaveEvent.ItemSaveEventHandler> {


    public interface ItemSaveEventHandler extends EventHandler {
        void onItemSave(ItemSaveEvent event);
    }
    
    public interface ItemSaveEventHandlers extends HasHandlers {
        HandlerRegistration addItemSaveEventHandler(ItemSaveEventHandler handler);
    }
    
    public static Type<ItemSaveEventHandler> TYPE = new Type<ItemSaveEventHandler>();

    public ItemSaveEvent() {}

    @Override
    public Type<ItemSaveEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ItemSaveEventHandler handler) {
        handler.onItemSave(this);
    }


}