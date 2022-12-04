package net.sourceforge.ganttproject.task;

import java.util.*;


public class FavoritesManagerImpl implements FavoritesManager{

    private Map<Integer, Task> favorites;

    public FavoritesManagerImpl(){
        this.reset();
    }

    public void reset(){

        this.favorites = new HashMap<Integer, Task>();
    }

    public void addFavorite(Task task){
        if(!favorites.containsKey(task.getTaskID()))
            favorites.put(task.getTaskID(), task);
    }

    @Override
    public Task getFavoriteTask(Integer taskID) {
        Task t = favorites.get(taskID);
        return t;
    }

    public void removeFavorite(Task task){
        if(favorites.containsKey(task.getTaskID()))
            favorites.remove(task.getTaskID());
    }
    public boolean isFavorite(Task task){
        return favorites.containsKey(task.getTaskID());
    }

    @Override
    public int getNFavorites() {
        return favorites.size();
    }

    public Iterator<Task> favoritesIterator(){
        return favorites.values().iterator();
    }

    @Override
    public Iterator<Task> favoritesIDIterator() {
        return favorites.values().iterator();
    }
}
