package net.jkcat.jkit.java.list;

import net.jkcat.core.item.BaseCustomViewModel;
import net.jkcat.network.BaseResult;

import java.util.List;

public class ActionListResult extends BaseResult {

    public ActionEntity page;

    public static class ActionEntity {
        public int currPage;
        public int totalPage;
        public int pageSize;
        public List<ActionBean> list;
        public int totalCount;

        public static class ActionBean extends BaseCustomViewModel {
            public String coverImg;
            public String startTime;
            public String id;
            public String position;
            public String state;
            public String title;
            public String isCheck;
            public String isDisplay;
            // 0 免费 1 收费
            public int type;
            public double entryFee;
            public int isJoin;
        }
    }
}
