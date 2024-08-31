package com.obss_final_project.e_commerce.mapper;

import com.obss_final_project.e_commerce.dto.response.blacklist.BlackListResponse;
import com.obss_final_project.e_commerce.model.BlackList;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BlackListMapper {

    public BlackListResponse mapToBlackListResponse(BlackList blackList) {
        BlackListResponse blackListResponse = new BlackListResponse();
        blackListResponse.setId(blackList.getId());
        blackListResponse.setSellerId(blackList.getSeller().getId());
        blackListResponse.setUserId(blackList.getUser().getId());
//        blackListResponse.setReason(blackList.getReason());

        return blackListResponse;
    }

}
