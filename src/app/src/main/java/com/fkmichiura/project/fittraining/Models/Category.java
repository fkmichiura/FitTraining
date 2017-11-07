package com.fkmichiura.project.fittraining.Models;

import com.thoughtbot.expandablecheckrecyclerview.models.MultiCheckExpandableGroup;

import java.util.List;

public class Category extends MultiCheckExpandableGroup{

    public Category(String title, List<Exercise> items) {
        super(title, items);
    }
}
