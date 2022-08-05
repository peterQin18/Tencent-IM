package com.tencent.qcloud.tim.demo.main;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMConversationListener;
import com.tencent.imsdk.v2.V2TIMFriendApplication;
import com.tencent.imsdk.v2.V2TIMFriendApplicationResult;
import com.tencent.imsdk.v2.V2TIMFriendshipListener;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.tencent.qcloud.tim.demo.DemoApplication;
import com.tencent.qcloud.tim.demo.R;
import com.tencent.qcloud.tim.demo.SplashActivity;
import com.tencent.qcloud.tim.demo.bean.UserInfo;
import com.tencent.qcloud.tim.demo.login.KImManager;
import com.tencent.qcloud.tim.demo.push.OfflineMessageBean;
import com.tencent.qcloud.tim.demo.push.OfflineMessageDispatcher;
import com.tencent.qcloud.tim.demo.utils.DemoLog;
import com.tencent.qcloud.tim.demo.utils.TUIUtils;
import com.tencent.qcloud.tuicore.component.TitleBarLayout;
import com.tencent.qcloud.tuicore.component.activities.BaseLightActivity;
import com.tencent.qcloud.tuicore.component.interfaces.ITitleBarLayout;
import com.tencent.qcloud.tuicore.util.ErrorMessageConverter;
import com.tencent.qcloud.tuicore.util.ToastUtil;
//import com.tencent.qcloud.tuikit.tuicontact.bean.ChatInfo;
//import com.tencent.qcloud.tuikit.tuicontact.bean.GroupInfo;
//import com.tencent.qcloud.tuikit.tuicontact.ui.pages.TUIContactFragment;
//import com.tencent.qcloud.tuikit.tuicontact.util.ContactUtils;
//import com.tencent.qcloud.tuikit.tuiconversation.ui.page.TUIConversationFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseLightActivity implements KImManager.LoginInterface, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    //    private TextView mMsgUnread;
    private TextView mNewFriendUnread;


    private ViewPager2 mainViewPager;
    private List<Fragment> fragments;
    private KImManager imManager;
    private TitleBarLayout titleBarLayout;

    private static WeakReference<MainActivity> instance;

    private TextView login, logout, createGroup, sendCustomMsg, sendNormalMsg,
            inviteMember, changeOrderStatus, finishChat;

    private String mGroupId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        DemoLog.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        instance = new WeakReference<>(this);
        imManager = new KImManager(this, this);
        handleData();
    }

    private void handleData() {
        UserInfo userInfo = UserInfo.getInstance();
        if (userInfo != null && userInfo.isAutoLogin()) {
            DemoApplication.instance().init(0);
            imManager.login(true);
        } else {
            imManager.login(false);
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        DemoLog.i(TAG, "onNewIntent");
        setIntent(intent);
    }

    private void initView() {
        setContentView(R.layout.main_activity);
        titleBarLayout = findViewById(R.id.main_title_bar);
        titleBarLayout.setTitle(UserInfo.getInstance().getName(), ITitleBarLayout.Position.MIDDLE);
//        mMsgUnread = findViewById(R.id.msg_total_unread);
        mNewFriendUnread = findViewById(R.id.new_friend_total_unread);

        fragments = new ArrayList<>();
//        fragments.add(new TUIConversationFragment());
//        fragments.add(new TUIContactFragment());
//        fragments.add(new ProfileFragment());

        mainViewPager = findViewById(R.id.view_pager);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(this);
        fragmentAdapter.setFragmentList(fragments);
        // 关闭左右滑动切换页面
        mainViewPager.setUserInputEnabled(false);
        // 设置缓存数量为4 避免销毁重建
        mainViewPager.setOffscreenPageLimit(4);
        mainViewPager.setAdapter(fragmentAdapter);
        mainViewPager.setCurrentItem(0, false);

        // 初始化
        login = findViewById(R.id.login);
        logout = findViewById(R.id.logout);
        createGroup = findViewById(R.id.create_group);
        sendCustomMsg = findViewById(R.id.send_custom_msg);
        sendNormalMsg = findViewById(R.id.send_normal_msg);
        inviteMember = findViewById(R.id.invite_group_member);
        changeOrderStatus = findViewById(R.id.change_order_status);
        finishChat = findViewById(R.id.finish_group_chat);

        login.setOnClickListener(this);
        logout.setOnClickListener(this);
        createGroup.setOnClickListener(this);
        sendCustomMsg.setOnClickListener(this);
        sendNormalMsg.setOnClickListener(this);
        inviteMember.setOnClickListener(this);
        changeOrderStatus.setOnClickListener(this);
        finishChat.setOnClickListener(this);
    }

//    private void prepareToClearAllUnreadMessage() {
//        mMsgUnread.setOnTouchListener(new View.OnTouchListener() {
//            private float downX;
//            private float downY;
//            private boolean isTriggered = false;
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        downX = mMsgUnread.getX();
//                        downY = mMsgUnread.getY();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        if (isTriggered) {
//                            return true;
//                        }
//                        float viewX = view.getX();
//                        float viewY = view.getY();
//                        float eventX = event.getX();
//                        float eventY = event.getY();
//                        float translationX = eventX + viewX - downX;
//                        float translationY = eventY + viewY - downY;
//                        view.setTranslationX(translationX);
//                        view.setTranslationY(translationY);
//                        // 移动的 x 和 y 轴坐标超过一定像素则触发一键清空所有会话未读
//                        if (Math.abs(translationX) > 200|| Math.abs(translationY) > 200) {
//                            isTriggered = true;
//                            mMsgUnread.setVisibility(View.GONE);
//                            triggerClearAllUnreadMessage();
//                        }
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        view.setTranslationX(0);
//                        view.setTranslationY(0);
//                        isTriggered = false;
//                        break;
//                    case MotionEvent.ACTION_CANCEL:
//                        isTriggered = false;
//                        break;
//                }
//
//                return true;
//            }
//        });
//    }
//
//    private void triggerClearAllUnreadMessage() {
//        V2TIMManager.getMessageManager().markAllMessageAsRead(new V2TIMCallback() {
//            @Override
//            public void onSuccess() {
//                Log.i(TAG, "markAllMessageAsRead success");
//                ToastUtil.toastShortMessage(MainActivity.this.getString(R.string.mark_all_message_as_read_succ));
//            }
//
//            @Override
//            public void onError(int code, String desc) {
//                Log.i(TAG, "markAllMessageAsRead error:" + code + ", desc:" + ErrorMessageConverter.convertIMError(code, desc));
//                ToastUtil.toastShortMessage(MainActivity.this.getString(R.string.mark_all_message_as_read_err_format, code, ErrorMessageConverter.convertIMError(code, desc)));
//                mMsgUnread.setVisibility(View.VISIBLE);
//            }
//        });
//    }
//
//    private final V2TIMConversationListener unreadListener = new V2TIMConversationListener() {
//        @Override
//        public void onTotalUnreadMessageCountChanged(long totalUnreadCount) {
//            if (totalUnreadCount > 0) {
//                mMsgUnread.setVisibility(View.VISIBLE);
//            } else {
//                mMsgUnread.setVisibility(View.GONE);
//            }
//            String unreadStr = "" + totalUnreadCount;
//            if (totalUnreadCount > 100) {
//                unreadStr = "99+";
//            }
//            mMsgUnread.setText(unreadStr);
//            // 华为离线推送角标
//            OfflineMessageDispatcher.updateBadge(MainActivity.this, (int) totalUnreadCount);
//        }
//    };

//    private final V2TIMFriendshipListener friendshipListener = new V2TIMFriendshipListener() {
//        @Override
//        public void onFriendApplicationListAdded(List<V2TIMFriendApplication> applicationList) {
//            refreshFriendApplicationUnreadCount();
//        }
//
//        @Override
//        public void onFriendApplicationListDeleted(List<String> userIDList) {
//            refreshFriendApplicationUnreadCount();
//        }
//
//        @Override
//        public void onFriendApplicationListRead() {
//            refreshFriendApplicationUnreadCount();
//        }
//    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onStart() {
        DemoLog.i(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        DemoLog.i(TAG, "onResume");
        super.onResume();
//        registerUnreadListener();
        handleOfflinePush();
    }

//    private void registerUnreadListener() {
//        V2TIMManager.getConversationManager().addConversationListener(unreadListener);
//        V2TIMManager.getConversationManager().getTotalUnreadMessageCount(new V2TIMValueCallback<Long>() {
//            @Override
//            public void onSuccess(Long aLong) {
//                runOnUiThread(() -> unreadListener.onTotalUnreadMessageCountChanged(aLong));
//            }
//
//            @Override
//            public void onError(int code, String desc) {
//
//            }
//        });
//
//        V2TIMManager.getFriendshipManager().addFriendListener(friendshipListener);
//        refreshFriendApplicationUnreadCount();
//    }
//
//    private void refreshFriendApplicationUnreadCount() {
//        V2TIMManager.getFriendshipManager().getFriendApplicationList(new V2TIMValueCallback<V2TIMFriendApplicationResult>() {
//            @Override
//            public void onSuccess(V2TIMFriendApplicationResult v2TIMFriendApplicationResult) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        int unreadCount = v2TIMFriendApplicationResult.getUnreadCount();
//                        if (unreadCount > 0) {
//                            mNewFriendUnread.setVisibility(View.VISIBLE);
//                        } else {
//                            mNewFriendUnread.setVisibility(View.GONE);
//                        }
//                        String unreadStr = "" + unreadCount;
//                        if (unreadCount > 100) {
//                            unreadStr = "99+";
//                        }
//                        mNewFriendUnread.setText(unreadStr);
//                    }
//                });
//            }
//
//            @Override
//            public void onError(int code, String desc) {
//
//            }
//        });
//    }

    private void handleOfflinePush() {
        if (V2TIMManager.getInstance().getLoginStatus() == V2TIMManager.V2TIM_STATUS_LOGOUT) {
            Intent intent = new Intent(MainActivity.this, SplashActivity.class);
            if (getIntent() != null) {
                intent.setData(getIntent().getData());
                intent.putExtras(getIntent());
            }
            startActivity(intent);
            finish();
            return;
        }

        final OfflineMessageBean bean = OfflineMessageDispatcher.parseOfflineMessage(getIntent());
        if (bean != null) {
            setIntent(null);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.cancelAll();
            }

            if (bean.action == OfflineMessageBean.REDIRECT_ACTION_CHAT) {
                if (TextUtils.isEmpty(bean.sender)) {
                    return;
                }
                TUIUtils.startChat(bean.sender, bean.nickname, bean.chatType);
            }
        }
    }

    @Override
    protected void onPause() {
        DemoLog.i(TAG, "onPause");
        super.onPause();
//        V2TIMManager.getConversationManager().removeConversationListener(unreadListener);
//        V2TIMManager.getFriendshipManager().removeFriendListener(friendshipListener);
    }

    @Override
    protected void onStop() {
        DemoLog.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        DemoLog.i(TAG, "onDestroy");
        super.onDestroy();
    }

    public static void finishMainActivity() {
        if (instance != null && instance.get() != null) {
            instance.get().finish();
        }
    }

    @Override
    public void loginSuccess() {
        initView();
    }

    @Override
    public void createGroupSuccess(String groupId) {
//        titleBarLayout.setTitle(groupInfo.getGroupName(), ITitleBarLayout.Position.MIDDLE);
//        mGroupId = groupId;
//        ContactUtils.startChatActivity(groupId, ChatInfo.TYPE_GROUP, groupInfo.getGroupName(), groupInfo.getGroupType());
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.login) {

            return;
        }
        if (id == R.id.logout) {
            imManager.logout();
            return;
        }
        if (id == R.id.create_group) {
            imManager.createGroup();
            return;
        }

        if (id == R.id.send_custom_msg) {

            return;
        }
        if (id == R.id.send_normal_msg) {

            return;
        }
        if (id == R.id.invite_group_member) {
            if (TextUtils.isEmpty(mGroupId)) {
                return;
            }
            imManager.inviteMember(mGroupId);
            return;
        }
        if (id == R.id.change_order_status) {

            return;
        }
        if (id == R.id.finish_group_chat) {

        }
    }
}
