package com.chat.firebasechat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import com.chat.firebasechat.Constantvalue.ChatOnItemClickListener;
import com.chat.firebasechat.Constantvalue.UserDetails;
import com.chat.firebasechat.Constantvalue.UserDetailsData;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {


    ArrayList<UserDetailsData> userDetailsDatas;
    ChatOnItemClickListener chatOnItemClickListener;

    public UserListAdapter(ArrayList<UserDetailsData> userDetailsDatas, ChatOnItemClickListener chatOnItemClickListener) {
        super();
        this.userDetailsDatas = userDetailsDatas;
        this.chatOnItemClickListener = chatOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userlistitem, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.userName.setText(userDetailsDatas.get(position).getUserName());
        holder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserDetails.Second_User = userDetailsDatas.get(position).getUserName();
               /* v.getContext().startActivity(new Intent(v.getContext(), ChatWith_.class));*/
                chatOnItemClickListener.OnItemClickListener(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return userDetailsDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private android.widget.TextView userName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.userName = (TextView) itemView.findViewById(R.id.userName);
        }
    }


}
