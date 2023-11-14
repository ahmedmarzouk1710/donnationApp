package com.ahmed.petapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.ahmed.petapp.Module.Post;

import java.util.List;

@Dao
public interface PostDAO {
    @Insert
    void insertOne(Post user);
    @Delete
    void delete(Post user);
    @Query("SELECT * FROM post")
    List<Post> getAll();
    @Query("UPDATE post SET likes = likes + 1 WHERE pid = :postId")
    void incrementLikes(int postId);
    @Query("UPDATE post SET likes = CASE WHEN likes > 0 THEN likes - 1 ELSE 0 END WHERE pid = :postId")
    void decrementLikes(int postId);
    @Query("SELECT * FROM post WHERE pid = :postId")
    Post getPostById(int postId);
}
