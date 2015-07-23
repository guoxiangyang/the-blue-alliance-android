package com.thebluealliance.androidclient.fragments.district;

import android.os.Bundle;

import com.thebluealliance.androidclient.fragments.ListviewFragment;
import com.thebluealliance.androidclient.helpers.DistrictHelper;
import com.thebluealliance.androidclient.models.DistrictTeam;
import com.thebluealliance.androidclient.subscribers.DistrictRankingsSubscriber;

import java.util.List;

import rx.Observable;

public class DistrictRankingsFragment
  extends ListviewFragment<List<DistrictTeam>, DistrictRankingsSubscriber> {

    public static final String KEY = "districtKey";
    public static final String DATAFEED_TAG_FORMAT = "district_rankings_%1$d_%2$s";

    private String mDatafeedTag;
    private String mShort;
    private int mYear;

    public static DistrictRankingsFragment newInstance(String key) {
        DistrictRankingsFragment f = new DistrictRankingsFragment();
        Bundle args = new Bundle();
        args.putString(KEY, key);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() == null || !getArguments().containsKey(KEY)) {
            throw new IllegalArgumentException("DistrictRankingsFragment must be constructed with district key");
        }
        String key = getArguments().getString(KEY);
        if (!DistrictHelper.validateDistrictKey(key)) {
            throw new IllegalArgumentException("Invalid district key + " + key);
        }
        mShort = key.substring(4);
        mYear = Integer.parseInt(key.substring(0, 4));
        mDatafeedTag = String.format(DATAFEED_TAG_FORMAT, mYear, mShort);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void inject() {
        mComponent.inject(this);
    }

    @Override
    protected Observable<List<DistrictTeam>> getObservable() {
        return mDatafeed.fetchDistrictRankings(mShort, mYear);
    }

    @Override
    protected String getDatafeedTag() {
        return mDatafeedTag;
    }
}
