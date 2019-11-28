package com.shushan.manhua.mvp.ui.fragment.selection;


import com.shushan.manhua.entity.request.SupportRequest;
import com.shushan.manhua.entity.request.SelectionRequest;
import com.shushan.manhua.entity.response.SelectionResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class SelectionFragmentControl {
    public interface SelectionView extends LoadDataView {

        void getSelectionInfoSuccess(SelectionResponse selectionResponse);

        void getSuggestSuccess();
    }

    public interface SelectionFragmentPresenter extends Presenter<SelectionView> {
        /**
         * 请求漫画选集信息
         */
        void onRequestSelectionInfo(SelectionRequest selectionRequest);

        /**
         * 评论点赞
         */
        void onCommentSuggestRequest(SupportRequest commentSuggestRequest);

    }

}
