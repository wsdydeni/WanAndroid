package com.wsdydeni.library_view;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class RecyclerViewUtil {
    private boolean enable = true;
    private final RecyclerView mRecyclerView;
    private RecyclerViewLoadMoreListener mRecyclerViewLoadMoreListener;

    public RecyclerViewUtil(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    public void setRecyclerViewLoadMoreListener(RecyclerViewLoadMoreListener listener) {
        this.mRecyclerViewLoadMoreListener = listener;
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && isBottom(mRecyclerView)) {
                    if (enable && mRecyclerViewLoadMoreListener != null) {
                        mRecyclerViewLoadMoreListener.onLoadMore();
                    }
                }
            }
        });
    }

    public void setLoadMoreEnable(boolean enable) {
        this.enable = enable;
    }

    private boolean isBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;

        return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange();
    }

    public interface RecyclerViewLoadMoreListener {
        void onLoadMore();
    }
}
