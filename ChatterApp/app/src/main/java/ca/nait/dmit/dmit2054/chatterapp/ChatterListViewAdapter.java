package ca.nait.dmit.dmit2054.chatterapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ChatterListViewAdapter extends BaseAdapter {

    private List<ChatterMessage> mMessageList;

    public ChatterListViewAdapter(List<ChatterMessage> messageList) {
        this.mMessageList = messageList;
    }

    @Override
    public int getCount() {
        return mMessageList.size();
    }

    @Override
    public ChatterMessage getItem(int i) {
        return mMessageList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View listItemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.listview_chattermessage_item, viewGroup, false);

        TextView loginNameTextView = listItemView.findViewById(R.id.chattermessage_listitem_loginname);
        TextView messageTextView = listItemView.findViewById(R.id.chattermessage_listitem_message);
        TextView dateTextView =listItemView.findViewById(R.id.chattermessage_listitem_date);

        ChatterMessage currentChatterMessage = getItem(i);

        loginNameTextView.setText(currentChatterMessage.loginName);
        messageTextView.setText(currentChatterMessage.message);
        dateTextView.setText(currentChatterMessage.date);

        return listItemView;
    }
}
