package com.tencent.qcloud.tim.demo.login;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMCreateGroupMemberInfo;
import com.tencent.imsdk.v2.V2TIMGroupInfo;
import com.tencent.imsdk.v2.V2TIMGroupMemberOperationResult;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.tencent.qcloud.tim.demo.DemoApplication;
import com.tencent.qcloud.tim.demo.bean.UserInfo;
import com.tencent.qcloud.tim.demo.signature.GenerateTestUserSig;
import com.tencent.qcloud.tim.demo.utils.DemoLog;
import com.tencent.qcloud.tim.demo.utils.TUIUtils;
import com.tencent.qcloud.tuicore.TUIConstants;
import com.tencent.qcloud.tuicore.TUICore;
import com.tencent.qcloud.tuicore.TUILogin;
import com.tencent.qcloud.tuicore.interfaces.TUICallback;
import com.tencent.qcloud.tuicore.util.ToastUtil;
//import com.tencent.qcloud.tuikit.tuicontact.bean.GroupInfo;
//import com.tencent.qcloud.tuikit.tuicontact.bean.GroupMemberInfo;
//import com.tencent.qcloud.tuikit.tuicontact.util.ContactUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: peter.qin@kikitrade.com
 * @date: 2022/8/2 at 11:15 上午
 * @Description:
 */
public class KImManager {

    private static final String TAG = "KImManager";
    private Context mContext;
    LoginInterface mLoginInterface;
    private String inviteMember = "kiki88";
    private String groupMember = "Test1";

    public KImManager(Context context, LoginInterface loginInterface) {
        this.mContext = context;
        this.mLoginInterface = loginInterface;
    }

    public void login(boolean isExist) {
        String name = "kiki" + TUIUtils.getRandom(100);
        DemoApplication.instance().init(0);
        if (!isExist) {
            UserInfo.getInstance().setUserId(name);
            // 获取userSig函数
            String userSig = GenerateTestUserSig.genTestUserSig(name);
            UserInfo.getInstance().setUserSig(userSig);
            UserInfo.getInstance().setName(name);
        }

        TUILogin.login(DemoApplication.instance(), DemoApplication.instance().getSdkAppId(), UserInfo.getInstance().getName(), UserInfo.getInstance().getUserSig(), new TUICallback() {
            @Override
            public void onError(final int code, final String desc) {
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        ToastUtil.toastLongMessage(mContext.getString(R.string.failed_login_tip) + ", errCode = " + code + ", errInfo = " + desc);
//                    }
//                });
                DemoLog.i(TAG, "imLogin errorCode = " + code + ", errorInfo = " + desc);
            }

            @Override
            public void onSuccess() {
                UserInfo.getInstance().setAutoLogin(true);
                UserInfo.getInstance().setDebugLogin(true);
                mLoginInterface.loginSuccess();
            }
        });
    }

    public void logout() {
        UserInfo.getInstance().cleanUserInfo();
        ToastUtil.toastLongMessage("退出登录成功");
    }

    public void createGroup() {
        // 构造数据
//        ArrayList<GroupMemberInfo> mMembers = new ArrayList<>();
//        GroupMemberInfo memberInfo = new GroupMemberInfo();
//        memberInfo.setAccount(groupMember);
//        mMembers.add(memberInfo);
//
//        final GroupInfo groupInfo = new GroupInfo();
//        String groupName = ContactUtils.getLoginUser();
//        for (int i = 1; i < mMembers.size(); i++) {
//            groupName = groupName + "、" + mMembers.get(i).getAccount();
//        }
//        if (groupName.length() > 20) {
//            groupName = groupName.substring(0, 17) + "...";
//        }
//        groupInfo.setChatName(groupName);
//        groupInfo.setGroupName(groupName);
//        groupInfo.setMemberDetails(mMembers);
//        groupInfo.setGroupType("Private");
//        groupInfo.setJoinType(-1);
//
//        V2TIMGroupInfo v2TIMGroupInfo = new V2TIMGroupInfo();
//        v2TIMGroupInfo.setGroupType(groupInfo.getGroupType());
//        v2TIMGroupInfo.setGroupName(groupInfo.getGroupName());
//        v2TIMGroupInfo.setGroupAddOpt(groupInfo.getJoinType());
//
//        List<V2TIMCreateGroupMemberInfo> v2TIMCreateGroupMemberInfoList = new ArrayList<>();
//        for (int i = 0; i < groupInfo.getMemberDetails().size(); i++) {
//            GroupMemberInfo groupMemberInfo = groupInfo.getMemberDetails().get(i);
//            V2TIMCreateGroupMemberInfo v2TIMCreateGroupMemberInfo = new V2TIMCreateGroupMemberInfo();
//            v2TIMCreateGroupMemberInfo.setUserID(groupMemberInfo.getAccount());
//            v2TIMCreateGroupMemberInfoList.add(v2TIMCreateGroupMemberInfo);
//        }
//
//        V2TIMManager.getGroupManager().createGroup(v2TIMGroupInfo, v2TIMCreateGroupMemberInfoList, new V2TIMValueCallback<String>() {
//            @Override
//            public void onSuccess(String s) {
//                mLoginInterface.createGroupSuccess(s, groupInfo);
//            }
//
//            @Override
//            public void onError(int code, String desc) {
//                Log.e(TAG, "创建失败" + desc);
//            }
//        });

    }

    public void sendCustomMsg() {

    }

    public void sendNormalMsg() {

    }

    public void inviteMember(String groupId) {
        List<String> userList = new ArrayList<>();
        userList.add(inviteMember);
        V2TIMManager.getGroupManager().inviteUserToGroup(groupId, userList, new V2TIMValueCallback<List<V2TIMGroupMemberOperationResult>>() {
            @Override
            public void onSuccess(List<V2TIMGroupMemberOperationResult> v2TIMGroupMemberOperationResults) {
                ToastUtil.toastLongMessage("邀请入群成功");
            }

            @Override
            public void onError(int i, String s) {
                ToastUtil.toastLongMessage("邀请入群失败" + s);
            }
        });
    }

    public void changeStatus() {

    }

    public void finishChat() {

    }

    public interface LoginInterface {
        void loginSuccess();

        void createGroupSuccess(String groupId);
    }


}
