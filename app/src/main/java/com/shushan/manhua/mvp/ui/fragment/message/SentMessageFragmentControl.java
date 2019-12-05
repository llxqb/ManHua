package com.shushan.manhua.mvp.ui.fragment.message;


import com.shushan.manhua.entity.request.DeleteMessageRequest;
import com.shushan.manhua.entity.request.MessageRequest;
import com.shushan.manhua.entity.response.SentMessageResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class SentMessageFragmentControl {
    public interface SentMessageView extends LoadDataView {
        void getMessageInfoSuccess(SentMessageResponse messageResponse);

        void getDeleteMessageSuccess();
    }

    public interface SentMessageFragmentPresenter extends Presenter<SentMessageView> {

        /**
         * 请求消息列表
         */
        void onRequestMessageInfo(MessageRequest messageRequest);

        /**
         * 删除评论/回复
         */
        void onDeleteMessageRequest(DeleteMessageRequest deleteMessageRequest);
    }

}
