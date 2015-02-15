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

public class FloatingPointFormat extends Format {

  public String getName() { return "float"; }

  public String toString(Rational number, Base base) {
    double value = number.numerator / number.denominator;
    double baseNum = base.getBase();
    if(value == 0.0){
      return "0.0*10^1";
    }else{
      double power = Math.floor(Math.log(Math.abs(value))/Math.log(baseNum));
      double mantissa = Math.abs(value) / Math.pow(baseNum,power);
      String result="";
      result = base.toString(mantissa) + "*10^" 
      			+ base.toString(power);
      if(value<0){
        result = "-" + result;
      }
      return result;
    }
  }

  public Rational parse(String number, Base base) throws FormatException {
    int indexMul = number.indexOf('*');
    int indexPow = number.indexOf('^');
    if(indexMul <= 0 || indexPow <= 0) {
      throw new FormatException("Error! Not a floating point format");
    }
    double mantissa = base.parse(number.substring(0,indexMul));
    double power = base.parse(number.substring(indexPow+1));
    double value = mantissa*Math.pow(base.getBase(),power);
    return new Rational(value);
  }
}