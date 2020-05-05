package com.baatu.servicedemo.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.baatu.servicedemo.R;
import com.baatu.servicedemo.data.model.User;
import com.baatu.servicedemo.databinding.RowUserDetailBinding;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserDetailHolder> {

    private List<User> mUserList;
    private Context mContext;

    public UserListAdapter(List<User> userList, Context context) {
        mContext = context;
        mUserList = userList;
    }

    @NonNull
    @Override
    public UserDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RowUserDetailBinding rowUserListBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.row_user_detail, parent, false);
        return new UserDetailHolder(rowUserListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserDetailHolder holder, int position) {
        User user = mUserList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public void refreshData(List<User> userList) {
        mUserList = userList;
        notifyDataSetChanged();
    }

    public class UserDetailHolder extends RecyclerView.ViewHolder {

        private final RowUserDetailBinding mRowUserDetailBinding;

        public UserDetailHolder(@NonNull RowUserDetailBinding rowUserDetailBinding) {
            super(rowUserDetailBinding.getRoot());
            mRowUserDetailBinding = rowUserDetailBinding;
        }

        public void bind(User user) {
            mRowUserDetailBinding.setUser(user);
        }

    }


}
