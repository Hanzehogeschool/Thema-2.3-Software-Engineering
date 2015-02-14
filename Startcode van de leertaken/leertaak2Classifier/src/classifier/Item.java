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

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an item that can be classified.
 * (Note that Category is not implemented as figure 4.3 of the book suggests.)
 */
public class Item {
  private Map<String, Feature> features = new HashMap<String, Feature>();
  private String name;

  /**
   * constructor of item
   * @param name The name of the item
   * @param features An array of the features this item has
   */
  public Item(String name, Feature[] features){
    this.name = name;
    // The array is converted to a Hashmap for ease of searching.
    for (int i = 0; i < features.length; i++) {
		this.features.put(features[i].getName(), features[i]);
	}
  }

  /**
   * Gets this items value of a specific feature.
   * @param featureName A String which matches a featurename.
   * @return A String value of the feature.
   */
  public String getFeatureValue(String featureName){
  	Feature feature = (Feature) features.get(featureName);
  	if(feature!=null){
  		return feature.getValue();
  	}else{
  		return null;
  	}
  }

  public void setFeatureValue(String featureName, String newValue) {
	Feature feature = (Feature) features.get(featureName);
	if(feature!=null){
		feature.setValue(newValue);
	}
  }  
}
