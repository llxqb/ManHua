package com.shushan.manhua.ireader.model.local;


import com.shushan.manhua.ireader.model.bean.AuthorBean;
import com.shushan.manhua.ireader.model.bean.BookCommentBean;
import com.shushan.manhua.ireader.model.bean.BookHelpfulBean;
import com.shushan.manhua.ireader.model.bean.BookHelpsBean;
import com.shushan.manhua.ireader.model.bean.BookReviewBean;
import com.shushan.manhua.ireader.model.bean.ReviewBookBean;

import java.util.List;

/**
 * Created by newbiechen on 17-4-28.
 */

public interface DeleteDbHelper {
    void deleteBookComments(List<BookCommentBean> beans);
    void deleteBookReviews(List<BookReviewBean> beans);
    void deleteBookHelps(List<BookHelpsBean> beans);
    void deleteAuthors(List<AuthorBean> beans);
    void deleteBooks(List<ReviewBookBean> beans);
    void deleteBookHelpful(List<BookHelpfulBean> beans);
    void deleteAll();
}
