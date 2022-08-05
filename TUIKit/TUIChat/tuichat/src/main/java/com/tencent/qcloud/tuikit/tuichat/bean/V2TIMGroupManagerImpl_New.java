package com.tencent.qcloud.tuikit.tuichat.bean;

import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMCreateGroupMemberInfo;
import com.tencent.imsdk.v2.V2TIMGroupApplication;
import com.tencent.imsdk.v2.V2TIMGroupApplicationResult;
import com.tencent.imsdk.v2.V2TIMGroupInfo;
import com.tencent.imsdk.v2.V2TIMGroupInfoResult;
import com.tencent.imsdk.v2.V2TIMGroupManager;
import com.tencent.imsdk.v2.V2TIMGroupMemberFullInfo;
import com.tencent.imsdk.v2.V2TIMGroupMemberInfoResult;
import com.tencent.imsdk.v2.V2TIMGroupMemberOperationResult;
import com.tencent.imsdk.v2.V2TIMGroupMemberSearchParam;
import com.tencent.imsdk.v2.V2TIMGroupSearchParam;
import com.tencent.imsdk.v2.V2TIMTopicInfo;
import com.tencent.imsdk.v2.V2TIMTopicInfoResult;
import com.tencent.imsdk.v2.V2TIMTopicOperationResult;
import com.tencent.imsdk.v2.V2TIMValueCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: peter.qin@kikitrade.com
 * @date: 2022/8/1 at 5:15 下午
 * @Description:
 */
public class V2TIMGroupManagerImpl_New  extends V2TIMGroupManager {
    @Override
    public void createGroup(V2TIMGroupInfo v2TIMGroupInfo, List<V2TIMCreateGroupMemberInfo> list, V2TIMValueCallback<String> v2TIMValueCallback) {

    }

    @Override
    public void getJoinedGroupList(V2TIMValueCallback<List<V2TIMGroupInfo>> v2TIMValueCallback) {

    }

    @Override
    public void getGroupsInfo(List<String> list, V2TIMValueCallback<List<V2TIMGroupInfoResult>> v2TIMValueCallback) {

    }

    @Override
    public void searchGroups(V2TIMGroupSearchParam v2TIMGroupSearchParam, V2TIMValueCallback<List<V2TIMGroupInfo>> v2TIMValueCallback) {

    }

    @Override
    public void setGroupInfo(V2TIMGroupInfo v2TIMGroupInfo, V2TIMCallback v2TIMCallback) {

    }

    @Override
    public void initGroupAttributes(String s, HashMap<String, String> hashMap, V2TIMCallback v2TIMCallback) {

    }

    @Override
    public void setGroupAttributes(String s, HashMap<String, String> hashMap, V2TIMCallback v2TIMCallback) {

    }

    @Override
    public void deleteGroupAttributes(String s, List<String> list, V2TIMCallback v2TIMCallback) {

    }

    @Override
    public void getGroupAttributes(String s, List<String> list, V2TIMValueCallback<Map<String, String>> v2TIMValueCallback) {

    }

    @Override
    public void getGroupOnlineMemberCount(String s, V2TIMValueCallback<Integer> v2TIMValueCallback) {

    }

    @Override
    public void getGroupMemberList(String s, int i, long l, V2TIMValueCallback<V2TIMGroupMemberInfoResult> v2TIMValueCallback) {

    }

    @Override
    public void getGroupMembersInfo(String s, List<String> list, V2TIMValueCallback<List<V2TIMGroupMemberFullInfo>> v2TIMValueCallback) {

    }

    @Override
    public void searchGroupMembers(V2TIMGroupMemberSearchParam v2TIMGroupMemberSearchParam, V2TIMValueCallback<HashMap<String, List<V2TIMGroupMemberFullInfo>>> v2TIMValueCallback) {

    }

    @Override
    public void setGroupMemberInfo(String s, V2TIMGroupMemberFullInfo v2TIMGroupMemberFullInfo, V2TIMCallback v2TIMCallback) {

    }

    @Override
    public void muteGroupMember(String s, String s1, int i, V2TIMCallback v2TIMCallback) {

    }

    @Override
    public void inviteUserToGroup(String s, List<String> list, V2TIMValueCallback<List<V2TIMGroupMemberOperationResult>> v2TIMValueCallback) {

    }

    @Override
    public void kickGroupMember(String s, List<String> list, String s1, V2TIMValueCallback<List<V2TIMGroupMemberOperationResult>> v2TIMValueCallback) {

    }

    @Override
    public void setGroupMemberRole(String s, String s1, int i, V2TIMCallback v2TIMCallback) {

    }

    @Override
    public void transferGroupOwner(String s, String s1, V2TIMCallback v2TIMCallback) {

    }

    @Override
    public void getGroupApplicationList(V2TIMValueCallback<V2TIMGroupApplicationResult> v2TIMValueCallback) {

    }

    @Override
    public void acceptGroupApplication(V2TIMGroupApplication v2TIMGroupApplication, String s, V2TIMCallback v2TIMCallback) {

    }

    @Override
    public void refuseGroupApplication(V2TIMGroupApplication v2TIMGroupApplication, String s, V2TIMCallback v2TIMCallback) {

    }

    @Override
    public void setGroupApplicationRead(V2TIMCallback v2TIMCallback) {

    }

    @Override
    public void getJoinedCommunityList(V2TIMValueCallback<List<V2TIMGroupInfo>> v2TIMValueCallback) {

    }

    @Override
    public void createTopicInCommunity(String s, V2TIMTopicInfo v2TIMTopicInfo, V2TIMValueCallback<String> v2TIMValueCallback) {

    }

    @Override
    public void deleteTopicFromCommunity(String s, List<String> list, V2TIMValueCallback<List<V2TIMTopicOperationResult>> v2TIMValueCallback) {

    }

    @Override
    public void setTopicInfo(V2TIMTopicInfo v2TIMTopicInfo, V2TIMCallback v2TIMCallback) {

    }

    @Override
    public void getTopicInfoList(String s, List<String> list, V2TIMValueCallback<List<V2TIMTopicInfoResult>> v2TIMValueCallback) {
    }
}
