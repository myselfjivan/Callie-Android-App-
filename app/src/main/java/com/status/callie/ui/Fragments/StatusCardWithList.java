package com.status.callie.ui.Fragments;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.status.callie.R;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.prototypes.CardWithList;

/**
 * Created by jivan.ghadage on 10/19/2016.
 */
public class StatusCardWithList extends CardWithList {
    public StatusCardWithList(Context context) {
        super(context);
    }

    @Override
    protected CardHeader initCardHeader() {
        //Add Header
        CardHeader header = new CardHeader(getContext());

        //Add a popup menu. This method set OverFlow button to visible
        header.setPopupMenu(R.menu.popup_item, new CardHeader.OnClickCardHeaderPopupMenuListener() {
            @Override
            public void onMenuItemClick(BaseCard card, MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_add:
                        WeatherObject w1 = new WeatherObject(MyCard.this);
                        w1.city = "Madrid";
                        w1.temperature = 24;
                        w1.weatherIcon = R.drawable.ic_action_sun;
                        w1.setObjectId(w1.city);
                        mLinearListAdapter.add(w1);
                        break;
                    case R.id.action_remove:
                        mLinearListAdapter.remove(mLinearListAdapter.getItem(0));
                        break;
                }
            }
        });
        header.setTitle("Weather"); //should use R.string.
        return header;
    }

    @Override
    protected void initCard() {
        setSwipeable(true);
        setOnSwipeListener(new OnSwipeListener() {
            @Override
            public void onSwipe(Card card) {
                Toast.makeText(getContext(), "Swipe on " + card.getCardHeader().getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected List<ListObject> initChildren() {
        List<ListObject> mObjects = new ArrayList<ListObject>();

        WeatherObject w1 = new WeatherObject(this);
        w1.city = "London";
        w1.temperature = 16;
        w1.weatherIcon = R.drawable.ic_action_cloud;
        w1.setObjectId(w1.city);
        mObjects.add(w1);

        return mObjects;
    }

    @Override
    public View setupChildView(int childPosition, ListObject object, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getChildLayoutId() {
        return 0;
    }
}
