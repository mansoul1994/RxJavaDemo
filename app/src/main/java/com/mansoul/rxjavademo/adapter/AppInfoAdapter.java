package com.mansoul.rxjavademo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mansoul.rxjavademo.R;
import com.mansoul.rxjavademo.model.AppInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mansoul on 16/5/31.
 */
public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.ViewHolder> {

    private Context mContext;
    private List<AppInfo> appInfoList;

    public AppInfoAdapter(Context mContext, List<AppInfo> appInfoList) {
        this.mContext = mContext;
        this.appInfoList = appInfoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_app, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AppInfo appInfo = appInfoList.get(position);
        holder.appImg.setImageDrawable(appInfo.getIcon());
        holder.tvName.setText(appInfo.getName());
        holder.tvTime.setText(appInfo.getInstallTime());

    }

    @Override
    public int getItemCount() {
        return appInfoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_app)
        ImageView appImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
