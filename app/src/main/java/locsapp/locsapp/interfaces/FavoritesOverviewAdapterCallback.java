package locsapp.locsapp.interfaces;

import locsapp.locsapp.models.Article;

/**
 * Created by Damien on 3/22/2016.
 */

public interface FavoritesOverviewAdapterCallback {
    void showArticle(String id);
    void deleteFavorite(String id);
}
