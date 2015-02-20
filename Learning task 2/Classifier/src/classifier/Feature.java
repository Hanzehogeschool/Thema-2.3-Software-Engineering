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

/**
 * This class describes the name of a feature ("Airco") 
 * and the value of the feature ("No"). 
 * The FeatureType describes the possible values this feature can have.  
 * @author J.Balje: added comments
 *
 */
public class Feature {

  private String name;
  private String value;
  private FeatureType type; 

  public String getName() { return name; }
  public String getValue() { return value; }

  public void setValue(String newValue){
  	if(type.isAllowed(newValue)){
  		value = newValue;
  	}else
      throw new IllegalArgumentException("value '" + newValue +
                                    "' not valid for attribute " + name);
  }

  public Feature(String name, String value, FeatureType type){
    this.name = name;
    this.type = type;
    setValue(value);
  }

  public String toString(){
    return name + "=" + value;
  }

  public FeatureType type() {
  	return type;
  }
}
