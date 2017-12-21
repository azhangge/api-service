package com.huajie.educomponent.suggestion.bo;

import lombok.Data;
import java.util.List;

/**
 * Created by zgz on 2017/9/6.
 */
@Data
public class SuggestionBo {

    private String suggestion;
    private Integer type;
    private List<String> picIds;
    private List<String> picPath;
}
