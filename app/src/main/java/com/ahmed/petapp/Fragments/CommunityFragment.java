package com.ahmed.petapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ahmed.petapp.Activity.addPost;
import com.example.petapp.R;
import com.ahmed.petapp.Database.AppDatabase;
import com.ahmed.petapp.Module.Post;

import java.util.Collections;
import java.util.List;

public class CommunityFragment extends Fragment {

    private AppDatabase appDatabase;

    public CommunityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_community, container, false);

        // Initialize the database
        appDatabase = AppDatabase.getAppDatabase(getContext());

        // Load posts from the database and add them to the UI
        loadAndDisplayPosts(rootView);

        return rootView;
    }

    private void loadAndDisplayPosts(View rootView) {
        LinearLayout postContainer = rootView.findViewById(R.id.post_container);

        // Clear the existing posts before loading new ones
        postContainer.removeAllViews();

        // Retrieve posts from the database
        List<Post> posts = appDatabase.postDao().getAll();

        // Reverse the list to display the latest post first
        Collections.reverse(posts);

        // Loop through posts and add them to the UI
        for (Post post : posts) {
            addPostItem(postContainer, post);
        }
    }

    private void addPostItem(LinearLayout postContainer, Post post) {
        View postItemView = LayoutInflater.from(getContext()).inflate(R.layout.post_item, postContainer, false);

        // Find views in the post item layout
        TextView likesTextView = postItemView.findViewById(R.id.likes);
        TextView usernameTextView = postItemView.findViewById(R.id.username);
        TextView descriptionTextView = postItemView.findViewById(R.id.description);
        ImageView likeButton = postItemView.findViewById(R.id.like);
        ImageView saveButton = postItemView.findViewById(R.id.save);

        String likes = Integer.toString(post.getLikes());

        // Set values for the post item
        likesTextView.setText(likes);
        usernameTextView.setText(post.getUserName());
        descriptionTextView.setText(post.getDescription());

        // Set like button src based on the likes count
        if (post.getLikes() > 0) {
            likeButton.setImageResource(R.drawable.baseline_favorite_clicked);
        } else {
            likeButton.setImageResource(R.drawable.baseline_favorite_border_24);
        }

        // Set click listeners for like and save buttons
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLikeButton(likeButton, post.getPid());
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSaveButton(saveButton, post.getPid());
            }
        });

        // Add the post item to the container
        postContainer.addView(postItemView);
    }

    private void toggleLikeButton(ImageView likeButton, int postId) {
        Post post = appDatabase.postDao().getPostById(postId);

        boolean isLiked = likeButton.getTag() != null && (boolean) likeButton.getTag();
        isLiked = !isLiked;
        likeButton.setTag(isLiked);

        if (isLiked) {
            appDatabase.postDao().decrementLikes(postId);
            post.decrementLikes(); // Decrement when liked
            likeButton.setImageResource(R.drawable.baseline_favorite_border_24);
        } else {
            appDatabase.postDao().incrementLikes(postId);
            post.incrementLikes();// Increment when unliked
            likeButton.setImageResource(R.drawable.baseline_favorite_clicked);
        }

        TextView likesTextView = likeButton.getRootView().findViewById(R.id.likes);
        likesTextView.setText(String.valueOf(post.getLikes()));

    }

    private void toggleSaveButton(ImageView saveButton, int postId) {
        // Implement the logic to toggle save button as explained in the previous response
        // ...

        // Example logic:
        boolean isSaved = saveButton.getTag() != null && (boolean) saveButton.getTag();
        isSaved = !isSaved;
        saveButton.setTag(isSaved);
        if (isSaved) {
            saveButton.setImageResource(R.drawable.baseline_bookmark_clicked);
        } else {
            saveButton.setImageResource(R.drawable.baseline_bookmark_border_24);
        }

    }

    private void navigateToAddPostActivity() {
        Intent intent = new Intent(getActivity(), addPost.class);
        startActivity(intent);
        getActivity().finish();
    }
    public void refreshPosts() {
        loadAndDisplayPosts(getView());
    }
}
