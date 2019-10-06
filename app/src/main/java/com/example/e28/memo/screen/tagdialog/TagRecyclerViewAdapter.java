package com.example.e28.memo.screen.tagdialog;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.e28.memo.R;
import com.example.e28.memo.model.Memo;
import com.example.e28.memo.model.Tag;

import java.text.SimpleDateFormat;
import java.util.Locale;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by User on 2019/07/16.
 */

/**
 * Created by User on 2019/07/16.
 */
public class TagRecyclerViewAdapter extends RecyclerView.Adapter<TagRecyclerViewAdapter.TaggedViewHolder> {
    public RealmResults<Memo> memoRealmResults;

    public class TaggedViewHolder extends RecyclerView.ViewHolder {
        public EditText memoEditText;

        public TaggedViewHolder(View itemView) {
            super(itemView);
            tagChk = itemView.findViewById(R.id.);
        }
    }

    public TagRecyclerViewAdapter(RealmResults<Memo> memoRealmResults) {
        this.memoRealmResults = memoRealmResults;
    }

    @Override
    public TaggedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tag, parent,false);
        TaggedViewHolder vh = new TaggedViewHolder(inflate);
        return vh;
    }

    @Override
    public void onBindViewHolder(TaggedViewHolder viewHolder, int position) {
        Memo memo = memoRealmResults.get(position);
        String tagStr = "";

        // ViewHolderにMemo.textの文章をセット
        viewHolder.memoEditText.setText(memo.getText());

        // ViewHolderにMemo.createdAtの時刻をセット
        // createdAtの内容がNullの場合は何もセットしない
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd_HH:mm", Locale.JAPAN);
        try {
            viewHolder.memoDate.setText(sdf.format(memo.getCreatedAt()));
        } catch (NullPointerException e) {}

        // タグがある場合は、ViewHolderにMemo.tagの文章をセット
        boolean isTagged = memo.getIsTagged();
        RealmList<Tag> tagRealmList = memo.getTagList();
        for (Tag tag : tagRealmList) {
            tagStr += ", " + tag.getName();
        }
        if(isTagged){
            viewHolder.memoTag.setText(tagStr);
        }
    }

    @Override
    public int getItemCount() {
        return memoRealmResults.size();
    }
}

/*
public class TaggedRecyclerViewAdapter extends RecyclerView.Adapter<TaggedViewHolder> {
    public List<TagItem> list;
    public TaggedRecyclerViewAdapter(List<TagItem> list) {
        this.list = list;
    }
    @Override
    public TaggedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tag, parent,false);
        TaggedViewHolder vh = new TaggedViewHolder(inflate);
        return vh;
    }
    @Override
    public void onBindViewHolder(TaggedViewHolder holder, int position) {
        holder.tagNameView.setText(list.get(position).getTagName());
        holder.tagSummaryView.setText(list.get(position).getTagSummary());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
*/