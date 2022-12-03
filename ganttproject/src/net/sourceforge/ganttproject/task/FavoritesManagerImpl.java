package net.sourceforge.ganttproject.task;

import java.util.*;


public class FavoritesManagerImpl implements FavoritesManager{

    private Map<String, Task> favorites;

    public FavoritesManagerImpl(){
        this.reset();
    }

    public void reset(){

        this.favorites = new HashMap<String, Task>();
    }

    public void addFavorite(Task task){
        if(!favorites.containsKey(task.getName()))
            favorites.put(task.getName(), task);
    }

    public void removeFavorite(Task task){
        if(favorites.containsKey(task.getName()))
            favorites.remove(task.getName());
    }
    public boolean isFavorite(Task task){
        return favorites.containsKey(task.getName());
    }

    public Iterator<Task> favoritesIterator(){
        return favorites.values().iterator();
    }

    @Override
    public Iterator<Task> favoritesIDIterator() {
        return favorites.values().iterator();
    }
}
