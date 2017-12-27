package com.zdk.paopao.gson;

import java.util.List;

/**
 * Created by Administrator on 2017/12/26.
 */

public class Image {
    public List<ListData> data;

    public class ListData{
        public String txt;
        public String nickname;
        public String avatar;
    }
}
