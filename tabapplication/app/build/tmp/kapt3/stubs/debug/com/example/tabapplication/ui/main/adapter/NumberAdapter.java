package com.example.tabapplication.ui.main.adapter;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0018B\'\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0002\u0010\tJ\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u000fH\u0016J\u0018\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000fH\u0016J\u000e\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u000fR\u001d\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0019"}, d2 = {"Lcom/example/tabapplication/ui/main/adapter/NumberAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/example/tabapplication/ui/main/adapter/NumberAdapter$UserViewHolder;", "users", "", "Lcom/example/tabapplication/ui/main/fragment/NumberFragment$Contact;", "onClick", "Lkotlin/Function1;", "", "(Ljava/util/List;Lkotlin/jvm/functions/Function1;)V", "getOnClick", "()Lkotlin/jvm/functions/Function1;", "getUsers", "()Ljava/util/List;", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "removeAt", "UserViewHolder", "app_debug"})
public final class NumberAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.example.tabapplication.ui.main.adapter.NumberAdapter.UserViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.example.tabapplication.ui.main.fragment.NumberFragment.Contact> users = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.example.tabapplication.ui.main.fragment.NumberFragment.Contact, kotlin.Unit> onClick = null;
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.example.tabapplication.ui.main.adapter.NumberAdapter.UserViewHolder holder, int position) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.example.tabapplication.ui.main.adapter.NumberAdapter.UserViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    public final void removeAt(int position) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.tabapplication.ui.main.fragment.NumberFragment.Contact> getUsers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlin.jvm.functions.Function1<com.example.tabapplication.ui.main.fragment.NumberFragment.Contact, kotlin.Unit> getOnClick() {
        return null;
    }
    
    public NumberAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.tabapplication.ui.main.fragment.NumberFragment.Contact> users, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.example.tabapplication.ui.main.fragment.NumberFragment.Contact, kotlin.Unit> onClick) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0019\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0019\u0010\f\u001a\n \t*\u0004\u0018\u00010\b0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000b\u00a8\u0006\u000e"}, d2 = {"Lcom/example/tabapplication/ui/main/adapter/NumberAdapter$UserViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "root", "Landroid/view/View;", "(Landroid/view/View;)V", "getRoot", "()Landroid/view/View;", "userNameText", "Landroid/widget/TextView;", "kotlin.jvm.PlatformType", "getUserNameText", "()Landroid/widget/TextView;", "userPhoneText", "getUserPhoneText", "app_debug"})
    public static final class UserViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final android.widget.TextView userNameText = null;
        private final android.widget.TextView userPhoneText = null;
        @org.jetbrains.annotations.NotNull()
        private final android.view.View root = null;
        
        public final android.widget.TextView getUserNameText() {
            return null;
        }
        
        public final android.widget.TextView getUserPhoneText() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.view.View getRoot() {
            return null;
        }
        
        public UserViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View root) {
            super(null);
        }
    }
}