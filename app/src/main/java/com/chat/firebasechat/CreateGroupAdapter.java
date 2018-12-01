package com.chat.firebasechat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

import com.chat.firebasechat.Constantvalue.CheckedUser;
import com.chat.firebasechat.Constantvalue.UserDetailsData;


class CreateGroupAdapter extends RecyclerView.Adapter<CreateGroupAdapter.CreateGroup> {

    ArrayList<UserDetailsData> userDetailsDatas;
    ArrayList<UserDetailsData> selectedUser = new ArrayList<>();
    CheckedUser checkedUser;
    Context context;

    public CreateGroupAdapter(ArrayList<UserDetailsData> userDetailsDatas, Context context, CheckedUser checkedUser) {
        super();
        this.userDetailsDatas = userDetailsDatas;
        this.context = context;
        this.checkedUser = checkedUser;
    }

    @Override
    public CreateGroup onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.groupistitem, parent, false);

        CreateGroup createGroup = new CreateGroup(view);
        return createGroup;
    }

    @Override
    public void onBindViewHolder(CreateGroup holder, final int position) {

        holder.userName.setText(userDetailsDatas.get(position).getUserName());

        holder.userName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedUser.add(userDetailsDatas.get(position));
                    checkedUser.totalUser(selectedUser);
                } else {
                    selectedUser.remove(userDetailsDatas.get(position));
                    checkedUser.totalUser(selectedUser);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userDetailsDatas.size();
    }

    public class CreateGroup extends RecyclerView.ViewHolder {
        private android.widget.CheckBox userName;

        public CreateGroup(View itemView) {
            super(itemView);
            this.userName = (CheckBox) itemView.findViewById(R.id.userName);
        }
    }


}
