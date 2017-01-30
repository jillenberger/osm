package de.db.dbanalytics.osm;/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2017 by the members listed in the COPYING,       *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.model.util.OsmModelUtil;

import java.util.Map;

/**
 * @author jillenberger
 */
public class HighwayHandler implements OsmWayHandler {



    public void handle(OsmWay way, NetworkBuilder builder) {
        Map<String, String> tags = OsmModelUtil.getTagsAsMap(way);
        /*
        Check access.
         */
        if(OsmTags.VALUE_NO.equalsIgnoreCase(tags.get(OsmTags.KEY_ACCESS))) return;
        /*
        Infere one-way situation.
         */
        /*
        If a roundabout, oneway=yes is implied.
         */
        int oneway = 0;
        if(OsmTags.VALUE_ROUNDABOUT.equalsIgnoreCase(tags.get(OsmTags.KEY_JUNCTION))) {
            oneway = 1;
        }
        /*
        Evaluate the one-way tag. Default is oneway=no; Value "reversible" is ignored, that is treated as oneway=no.
         */
        String onewayValue = tags.get(OsmTags.KEY_ONEWAY);
        if(onewayValue != null) {
            if (ValueUtils.evaluateYes(onewayValue)) oneway = 1;
            else if(ValueUtils.evaluteNo(onewayValue)) oneway = 0;
            else if(OsmTags.VALUE_MINUSONE.equalsIgnoreCase(onewayValue)) oneway = -1;
        }
    }
}
