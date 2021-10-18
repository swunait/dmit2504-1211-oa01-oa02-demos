package ca.nait.dmit.dmit2504.chattergen2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatterListViewAdapter extends BaseAdapter {

    private List<Chatter> chatterList;

    public ChatterListViewAdapter() {
        chatterList = new ArrayList<>();
    }

    public void addItem(Chatter newChatter) {
        chatterList.add(newChatter);
        notifyDataSetChanged();
    }

    public ChatterListViewAdapter(List<Chatter> chatterList) {
        this.chatterList = chatterList;
    }

    @Override
    public int getCount() {
        return chatterList.size();
    }

    @Override
    public Chatter getItem(int i) {
        return chatterList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View listItemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.chatter_listview_item, viewGroup, false);

        TextView loginNameTextView = listItemView.findViewById(R.id.chatter_listview_item_loginname);
        TextView messageTextView = listItemView.findViewById(R.id.chatter_listview_item_message);
        TextView dateTextView = listItemView.findViewById(R.id.chatter_listview_item_date);

        Chatter currentChatter = getItem(i);

        loginNameTextView.setText(currentChatter.getLoginName());
        messageTextView.setText(currentChatter.getMessage());
        dateTextView.setText(currentChatter.getDate());

        return listItemView;
    }
}
