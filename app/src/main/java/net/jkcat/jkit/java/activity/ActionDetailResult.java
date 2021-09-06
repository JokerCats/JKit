package net.jkcat.jkit.java.activity;

import net.jkcat.network.BaseResult;

/**
 * @author JokerCats on 2020.04.13
 */
public class ActionDetailResult extends BaseResult {

    public DetailActionEntity activity;

    public static class DetailActionEntity {

        public String coverImg;
        public String isLike;
        public String mobile;
        public String startTime;
        public String endTime;
        public String id;
        public String position;
        public String state;
        public String title;
        public String info;
        public String likes;
        public String number;
        public String isCheck;
        public String type; // 0 免费 1 付费
        public String entryFee;
        public String video;
        public String videoImg;

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }
    }
}
