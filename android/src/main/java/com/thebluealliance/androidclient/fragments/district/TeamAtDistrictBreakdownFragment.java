package com.thebluealliance.androidclient.fragments.district;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.thebluealliance.androidclient.R;
import com.thebluealliance.androidclient.binders.ExpandableListBinder;
import com.thebluealliance.androidclient.fragments.DatafeedFragment;
import com.thebluealliance.androidclient.helpers.DistrictHelper;
import com.thebluealliance.androidclient.listitems.ListGroup;
import com.thebluealliance.androidclient.models.DistrictTeam;
import com.thebluealliance.androidclient.subscribers.TeamAtDistrictBreakdownSubscriber;
import com.thebluealliance.androidclient.views.ExpandableListView;

import java.util.List;

import rx.Observable;

public class TeamAtDistrictBreakdownFragment
  extends DatafeedFragment<DistrictTeam, List<ListGroup>,
  TeamAtDistrictBreakdownSubscriber, ExpandableListBinder> {

    public static final String DISTRICT = "districtKey";
    public static final String TEAM = "teamKey";
    public static final String DATAFEED_TAG_FORMAT = "team_at_district_breakdown_%1$s_%2$d_%3$s";

    private String mDatafeedTag;
    private String mTeamKey;
    private String mDistrictShort;
    private int mYear;

    public static TeamAtDistrictBreakdownFragment newInstance(String teamKey, String districtKey) {
        TeamAtDistrictBreakdownFragment f = new TeamAtDistrictBreakdownFragment();
        Bundle args = new Bundle();
        args.putString(TEAM, teamKey);
        args.putString(DISTRICT, districtKey);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mTeamKey = getArguments().getString(TEAM);
            String districtKey = getArguments().getString(DISTRICT);
            if (!DistrictHelper.validateDistrictKey(districtKey)) {
                throw new IllegalArgumentException("Invalid District Key " + districtKey);
            }
            mDistrictShort = districtKey.substring(4);
            mYear = Integer.parseInt(districtKey.substring(0, 4));
            mDatafeedTag = String.format(DATAFEED_TAG_FORMAT, mTeamKey, mYear, mDistrictShort);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Setup views & listeners
        View view = inflater.inflate(R.layout.expandable_listview_with_spinner, null);
        ExpandableListView listView = (ExpandableListView) view.findViewById(R.id.expandable_list);

        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress);
        mBinder.expandableList = listView;
        mBinder.progressBar = progressBar;
        listView.setSelector(R.drawable.transparent);
        return view;
    }

    @Override
    protected void inject() {
        mComponent.inject(this);
    }

    @Override
    protected Observable<DistrictTeam> getObservable() {
        return mDatafeed.fetchTeamAtDistrictRankings(mTeamKey, mDistrictShort, mYear);
    }

    @Override
    protected String getDatafeedTag() {
        return mDatafeedTag;
    }
}
