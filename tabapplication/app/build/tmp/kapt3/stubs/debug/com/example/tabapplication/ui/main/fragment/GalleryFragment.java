package com.example.tabapplication.ui.main.fragment;

import java.lang.System;

@kotlin.Suppress(names = {"DEPRECATION"})
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u0012H\u0002J\"\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00052\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0017J\u0010\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0016J&\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0016J\b\u0010#\u001a\u00020\u0012H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082D\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\tX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/example/tabapplication/ui/main/fragment/GalleryFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/example/tabapplication/ui/main/adapter/GalleryImageClickListener;", "()V", "SPAN_COUNT", "", "TAG", "", "galleryAdapter", "Lcom/example/tabapplication/ui/main/adapter/GalleryImageAdapter;", "getGalleryAdapter", "()Lcom/example/tabapplication/ui/main/adapter/GalleryImageAdapter;", "setGalleryAdapter", "(Lcom/example/tabapplication/ui/main/adapter/GalleryImageAdapter;)V", "imageList", "Ljava/util/ArrayList;", "Lcom/thesimplycoder/imagegallery/adapter/Image;", "goToAlbum", "", "loadImages", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onClick", "position", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "tedPermission", "app_debug"})
public final class GalleryFragment extends androidx.fragment.app.Fragment implements com.example.tabapplication.ui.main.adapter.GalleryImageClickListener {
    private final java.lang.String TAG = "Jungbin";
    private int SPAN_COUNT;
    private final java.util.ArrayList<com.thesimplycoder.imagegallery.adapter.Image> imageList = null;
    @org.jetbrains.annotations.NotNull()
    public com.example.tabapplication.ui.main.adapter.GalleryImageAdapter galleryAdapter;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.tabapplication.ui.main.adapter.GalleryImageAdapter getGalleryAdapter() {
        return null;
    }
    
    public final void setGalleryAdapter(@org.jetbrains.annotations.NotNull()
    com.example.tabapplication.ui.main.adapter.GalleryImageAdapter p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onClick(int position) {
    }
    
    private final void loadImages() {
    }
    
    private final void tedPermission() {
    }
    
    private final void goToAlbum() {
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.JELLY_BEAN)
    @java.lang.Override()
    public void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    public GalleryFragment() {
        super();
    }
}