package com.thebluealliance.androidclient.renderers;

import android.support.annotation.Nullable;

import com.thebluealliance.androidclient.helpers.ModelType;
import com.thebluealliance.androidclient.listitems.DistrictTeamListElement;
import com.thebluealliance.androidclient.listitems.ListItem;
import com.thebluealliance.androidclient.models.DistrictPointBreakdown;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DistrictPointBreakdownRenderer implements ModelRenderer<DistrictPointBreakdown, Void> {

    @Inject
    public DistrictPointBreakdownRenderer() {

    }

    @Override
    public @Nullable ListItem renderFromKey(String key, ModelType type) {
        /* Not used */
        return null;
    }

    @Override
    public @Nullable DistrictTeamListElement renderFromModel(
      DistrictPointBreakdown breakdown,
      Void aVoid) {
        return new DistrictTeamListElement(
          breakdown.getTeamKey(),
          breakdown.getDistrictKey(),
          breakdown.getTeamName(),
          breakdown.getRank(),
          breakdown.getTotalPoints());
    }
}
