package com.anagaf.uselessrestclient.view;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anagaf.uselessrestclient.R;
import com.anagaf.uselessrestclient.model.User;

import java.util.List;

final class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private final List<User> users;

    UsersAdapter(final List<User> users) {
        this.users = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_user, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.nameTextView.setText(users.get(position).getName());
        holder.emailTextView.setText(users.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        final int count = users.size();
        return count;
    }

    /* ========== Inner Classes ========== */

    static final class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView emailTextView;

        ViewHolder(final CardView cardView) {
            super(cardView);
            nameTextView = (TextView) cardView.findViewById(R.id.user_name);
            emailTextView = (TextView) cardView.findViewById(R.id.user_email);
        }
    }
}
