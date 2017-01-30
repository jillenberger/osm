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

/**
 * @author jillenberger
 */
public class ValueUtils {

    public static boolean evaluateYes(String value) {
        if(OsmTags.VALUE_YES.equalsIgnoreCase(value) ||
                OsmTags.VALUE_TRUE.equalsIgnoreCase(value) ||
                OsmTags.VALUE_ONE.equalsIgnoreCase(value)) {
            return true;
        }

        return false;
    }

    public static boolean evaluteNo(String value) {
        if(OsmTags.VALUE_NO.equalsIgnoreCase(value) ||
                OsmTags.VALUE_FALSE.equalsIgnoreCase(value) ||
                OsmTags.VALUE_ZERO.equalsIgnoreCase(value)) {
            return true;
        }

        return false;
    }
}
