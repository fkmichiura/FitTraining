package com.fkmichiura.project.fittraining.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fkmichiura.project.fittraining.ViewHolders.CategoryViewHolder;
import com.fkmichiura.project.fittraining.ViewHolders.ExerciseViewHolder;
import com.fkmichiura.project.fittraining.Models.Category;
import com.fkmichiura.project.fittraining.Models.Exercise;
import com.fkmichiura.project.fittraining.R;
import com.thoughtbot.expandablecheckrecyclerview.CheckableChildRecyclerViewAdapter;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class CategoryAdapter extends CheckableChildRecyclerViewAdapter<CategoryViewHolder, ExerciseViewHolder> {

    public CategoryAdapter(List<Category> groups) {
        super(groups);
    }

    @Override
    public CategoryViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public ExerciseViewHolder onCreateCheckChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_row, parent,false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindGroupViewHolder(CategoryViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setCategoryTitle(group.getTitle());
    }

    @Override
    public void onBindCheckChildViewHolder(ExerciseViewHolder holder, int flatPosition, CheckedExpandableGroup group, int childIndex) {
        final Exercise exercise = (Exercise)group.getItems().get(childIndex);
        holder.setExercise(exercise.getIdImage(), exercise.getExerciseName());
    }
}