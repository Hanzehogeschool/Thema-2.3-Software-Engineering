/*
 * (C) Copyright 2005 Davide Brugali, Marco Torchiano
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307  USA
 */
package classifier;

import java.util.Collection;
import java.util.TreeSet;
/**
 * This class describes a set of possible values that are allowed
 * for a feature. Examples: Yes/No, High/Low, 1/2/4, ...
 * @author J.Balje - Added comments
 *
 */
public class FeatureType {

    private TreeSet<String> allowed = new TreeSet<String>();
    private String name;
        
	public FeatureType(String name, String[] allowedValues) {
		this.name = name;
		for (int i = 0; i < allowedValues.length; i++) {
			allowed.add(allowedValues[i]);
		}
	}
	
	public boolean isAllowed(String value) {
		return allowed.contains(value);
	}

	public Collection<String> allowedValues() {
		return allowed;
	}

}
