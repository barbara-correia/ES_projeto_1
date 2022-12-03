package net.sourceforge.ganttproject.task;

import java.util.Iterator;


public interface FavoritesManager {

    /**
     * Resets the favorites manager data structure and erases any favorites that was in that data structure.
     */
    void reset();

    /**
     * Adds a task to the favorites' manager, if that task isn´t already a favorite task.
     * @param task new favorite task
     */
    void addFavorite(Task task);

    /**
     * Removes a favorite task, if that task is a favorite task.
     * @param task favorite task to be removed from the favorite list (data structure managed by this class).
     */
    void removeFavorite(Task task);

    /**
     * Checks if a task is already favorite.
     * @param task task to verify if it's already favorite.
     * @return true if the task is already a favorite task, false otherwise.
     */

    boolean isFavorite(Task task);

    /**
     * Iterates the favorite tasks.
     * @return iterator of the values of the favorite tasks collection.
     */
    Iterator<Task> favoritesIterator();

    /**
     * Iterates the key collection of the favorite tasks.
     * @return iterator of the key values of the favorite tasks collection.
     */
    Iterator<Task> favoritesIDIterator();
}
