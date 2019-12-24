package com.zls.bishe.service;

import com.zls.bishe.utils.PagedResult;

public interface ISysInfoService {

    PagedResult queryReviewInfo(String userId, int pageNo, int pageSize);

    PagedResult querySysInfo(int pageNo, int pageSize);

}
