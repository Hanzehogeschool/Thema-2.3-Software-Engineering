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

public class FixedPointFormat
extends Format {

  static final int MAX_PRECISION = 10;  // max # of number after point

  public String getName() { return "fixed"; }

  String toString(Rational number, Base base) {
    double value = (double)number.numerator / (double)number.denominator;
    String result = base.toString(Math.abs(value));
    if(result.equals("0")) result += ".0";
    if(value<0) result = "-" + result;
    return result;
  }

  public Rational parse(String number, Base base) {
    return new Rational(base.parse(number));
  }
}