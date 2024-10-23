package com.bbs.cloud.admin.common.feigh.fallback;

import com.bbs.cloud.admin.common.feigh.client.TestFeighClient;
import org.springframework.stereotype.Component;

@Component
public class TestFeighClientFallback implements TestFeighClient {

    @Override
    public String testFeigh() {
        return null;
    }
}
