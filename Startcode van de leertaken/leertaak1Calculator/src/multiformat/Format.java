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
package multiformat;


public abstract class Format {
  // public members
  public abstract String getName();

  // package visible member
  /**
   * Translate the internal Rational to a String representation
   * using the correct Format and Base.
   */
  abstract String toString(Rational p_number, Base p_base);
  
  /**
   * Translate a string representation in a specific format and base to
   * the internal Rational representation.
   */
  abstract Rational parse(String p_number, Base p_base) throws FormatException;

}