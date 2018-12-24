package com.khn.mydbapp.api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.khn.mydbapp.R;
import com.khn.mydbapp.models.UserListResponse;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

        private Context context;
        private List<UserListResponse> userListResponseData;

public UsersAdapter(Context context, List<UserListResponse> userListResponseData) {
        this.userListResponseData = userListResponseData;
        this.context = context;
        }

@Override
@NonNull
public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.users_list_items, null);
        UsersViewHolder usersViewHolder = new UsersViewHolder(view);
        return usersViewHolder;
        }

@Override
public void onBindViewHolder(UsersViewHolder holder, final int position) {

        holder.username.setText("Username: " + userListResponseData.get(position).getUsername());
        holder.fullname.setText("Fullname: " + userListResponseData.get(position).getFullname());
        holder.email.setText("Email: " + userListResponseData.get(position).getEmail());
        holder.createdat.setText("Created At: " + userListResponseData.get(position).getCreatedAt());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
             public void onClick(View view) {
             Toast.makeText(context, userListResponseData.get(position).getUsername(), Toast.LENGTH_SHORT).show();      }
          });
        }
//-----------------------------------
@Override
public int getItemCount() {
        return userListResponseData.size(); // size of the list items
        }

class UsersViewHolder extends RecyclerView.ViewHolder {
    // init the item view's
    TextView username, fullname, email, createdat;

    UsersViewHolder(View itemView) {
        super(itemView);
        // get the reference of item view's
        username =  itemView.findViewById(R.id.textViewUserName);
        fullname =  itemView.findViewById(R.id.textViewFullName);
        email =  itemView.findViewById(R.id.textViewEmail);
        createdat = itemView.findViewById(R.id.textViewCreatedAt);
    }
}
}
