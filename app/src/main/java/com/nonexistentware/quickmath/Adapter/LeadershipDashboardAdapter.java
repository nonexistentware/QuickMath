package com.nonexistentware.quickmath.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nonexistentware.quickmath.Model.PlayerModel;
import com.nonexistentware.quickmath.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class LeadershipDashboardAdapter extends RecyclerView.Adapter<LeadershipDashboardAdapter.LeadershipViewHolder> {

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;

    List<PlayerModel> playerModelList;
    Context context;

    private RecyclerView listView;
    private LinearLayoutManager mLayoutManager;

    public LeadershipDashboardAdapter(List<PlayerModel> playerModelList, Context context) {
        this.playerModelList = playerModelList;
        this.context = context;
    }
    @NonNull
    @Override
    public LeadershipDashboardAdapter.LeadershipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.top_players_item, parent, false);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        return new LeadershipViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LeadershipDashboardAdapter.LeadershipViewHolder holder, int position) {
        holder.playerName.setText(playerModelList.get(position).getPlayerName());
        holder.playerGameMode.setText(playerModelList.get(position).getGameMode());
        holder.playerLevel.setText(Long.toString(playerModelList.get(position).getPlayerLevel()));
        holder.playerScore.setText(Long.toString(playerModelList.get(position).getPlayerScore()));
        holder.playersGameDifLevel.setText(playerModelList.get(position).getDifficultLevel());
        holder.playerBestTime.setText(playerModelList.get(position).getRemainCounterTime());
        Picasso.get()
                .load(playerModelList.get(position).getPlayerImg())
                .into(holder.playersImg);

    }

    @Override
    public int getItemCount() {
        return playerModelList.size();
    }

    public class LeadershipViewHolder extends RecyclerView.ViewHolder {

        ImageView playersImg;
        TextView playerName, playerLevel, playerScore, playersGameDifLevel, playerBestTime, playerGameMode;

        public LeadershipViewHolder(@NonNull View itemView) {
            super(itemView);

            playersImg = itemView.findViewById(R.id.top_player_item_img);
            playerName = itemView.findViewById(R.id.top_player_item_pname);
            playerLevel = itemView.findViewById(R.id.top_player_item_level_counter);
            playerScore = itemView.findViewById(R.id.top_player_item_score_counter);
            playersGameDifLevel = itemView.findViewById(R.id.top_player_item_last_game_dif_level);
            playerBestTime = itemView.findViewById(R.id.top_player_item_last_best_time);
            playerGameMode = itemView.findViewById(R.id.top_player_item_game_mode);

//            if (playerModelList.equals("playerFlag")) {
//
//            }

        }
    }
}

