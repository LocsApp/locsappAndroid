package locsapp.locsapp.interfaces;

import locsapp.locsapp.models.Article;
import locsapp.locsapp.models.BillingAddress;
import locsapp.locsapp.models.LivingAddress;

public interface ArticleOverviewAdapterCallback {
    void showArticle(Article article);
    void addFavorite(String id);
}
