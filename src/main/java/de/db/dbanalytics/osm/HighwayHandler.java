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
import gnu.trove.list.TLongList;

import java.util.Map;

/**
 * @author jillenberger
 */
public class HighwayHandler implements OsmWayHandler {


    private int getDefaultLanes(String level) {
        return 1;
    }

    private double getDefaultFreespeed(String level) {
        return 0;
    }

    private double getDefaultCapacity(String level) {
        return 0;
    }

    private double calculateLenght(Long from, Long to) {
        return 0;
    }

    public void handle(OsmWay way, NetworkBuilder builder) {
        Map<String, String> tags = OsmModelUtil.getTagsAsMap(way);
        /*
        Get highway level and return if no highway tag present.
         */
        String level = tags.get(OsmTags.KEY_HIGHWAY);
        if(level == null) return;
        /*
        Check access.
         */
        if(OsmTags.VALUE_NO.equalsIgnoreCase(tags.get(OsmTags.KEY_ACCESS))) return;
        /*
        Infere one-way situation.
         */
        int oneway = 0;
        /*
        If a roundabout, oneway=yes is implied.
         */
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
        /*
        Evaluate maxspeed tag.
         */
        double freespeed = getDefaultFreespeed(level);
        String maxspeedValue = tags.get(OsmTags.KEY_MAXSPEED);
        if(maxspeedValue != null) {
            String tokens[] = maxspeedValue.split("\\s*");
            if(tokens.length > 0) {
                try {
                    freespeed = Double.parseDouble(tokens[0]);
                } catch (NumberFormatException e) {
                    // do nothing
                }
            }
            // TODO: unit parsing, for now assume km/h
            freespeed = freespeed * 3.6;
        }
        /*
        Evaluate lanes tag.
         */
        int lanes = getDefaultLanes(level);
        String lanesValue = tags.get(OsmTags.KEY_LANES);
        if(lanesValue != null) {
            try {
                lanes = (int)Double.parseDouble(lanesValue);
            } catch (NumberFormatException e) {

            }
        }
        /*
        Lanes specifies the total number of line for both direction. If no one-way distribute lanes over both directions.
         */
        if(oneway == 0) {
            lanes = Math.max(1, lanes/2);
        }
        /*
        Calculate capacity.
         */
        double capacity = getDefaultCapacity(level) * lanes;
        /*
        Loop through nodes and create links.
         */
        TLongList nodes = OsmModelUtil.nodesAsList(way);
        if(oneway == -1) {
            nodes.reverse();
        }

        Long fromNode = nodes.get(0);
        for(int i = 1; i < nodes.size(); i++) {
            Long toNode = nodes.get(i);

            double lenght = calculateLenght(fromNode, toNode);

            builder.addLink(fromNode, toNode, way.getId(), lanes, freespeed, capacity);
            if(oneway != 0) builder.addLink(toNode, fromNode, way.getId(), lanes, freespeed, capacity);
        }
    }
}
