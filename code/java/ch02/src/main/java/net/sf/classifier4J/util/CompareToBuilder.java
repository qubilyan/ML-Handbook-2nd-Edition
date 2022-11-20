
/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2002-2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowledgement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgement may appear in the software itself,
 *    if and wherever such third-party acknowledgements normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */
package net.sf.classifier4J.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Comparator;

/** 
 * Assists in implementing {@link Comparable#compareTo(Object)} methods.
 *
 * It is consistent with <code>equals(Object)</code> and
 * <code>hashcode()</code> built with {@link EqualsBuilder} and
 * {@link HashCodeBuilder}.</p>
 *
 * <p>Two Objects that compare equal using <code>equals(Object)</code> should normally
 * also compare equal using <code>compareTo(Object)</code>.</p>
 *
 * <p>All relevant fields should be included in the calculation of the
 * comparison. Derived fields may be ignored. The same fields, in the same
 * order, should be used in both <code>compareTo(Object)</code> and
 * <code>equals(Object)</code>.</p>
 *
 * <p>To use this class write code as follows:</p>
 *
 * <pre>
 * public class MyClass {
 *   String field1;
 *   int field2;
 *   boolean field3;
 *
 *   ...
 *
 *   public int compareTo(Object o) {
 *     MyClass myClass = (MyClass) o;
 *     return new CompareToBuilder()
 *       .appendSuper(super.compareTo(o)
 *       .append(this.field1, myClass.field1)
 *       .append(this.field2, myClass.field2)
 *       .append(this.field3, myClass.field3)
 *       .toComparison();
 *   }
 * }
 * </pre>
 *
 * <p>Alternatively, there is a method {@link #reflectionCompare reflectionCompare} that uses
 * reflection to determine the fields to append. Because fields can be private,
 * <code>reflectionCompare</code> uses {@link AccessibleObject#setAccessible(boolean)} to
 * bypass normal access control checks. This will fail under a security manager,
 * unless the appropriate permissions are set up correctly. It is also
 * slower than appending explicitly.</p>
 *
 * <p>A typical implementation of <code>compareTo(Object)</code> using
 * <code>reflectionCompare</code> looks like:</p>

 * <pre>
 * public int compareTo(Object o) {
 *   return CompareToBuilder.reflectionCompare(this, o);
 * }
 * </pre>
 *
 * @see Comparable
 * @see Object#equals(Object)
 * @see Object#hashCode()
 * @see EqualsBuilder
 * @see HashCodeBuilder
 * @author <a href="mailto:steve.downey@netfolio.com">Steve Downey</a>
 * @author Stephen Colebourne
 * @author Gary Gregory
 * @author Pete Gieser
 * @since 1.0
 * @version $Id: CompareToBuilder.java,v 1.1 2005/02/04 05:30:28 nicklothian Exp $
 */
public class CompareToBuilder {
    
    /**
     * Current state of the comparison as appended fields are checked.
     */
    private int comparison;

    /**
     * <p>Constructor for CompareToBuilder.</p>
     *
     * <p>Starts off assuming that the objects are equal. Multiple calls are 
     * then made to the various append methods, followed by a call to 
     * {@link #toComparison} to get the result.</p>
     */
    public CompareToBuilder() {
        super();
        comparison = 0;
    }

    //-----------------------------------------------------------------------
    /** 
     * <p>Compares two <code>Object</code>s via reflection.</p>
     *
     * <p>Fields can be private, thus <code>AccessibleObject.setAccessible</code>
     * is used to bypass normal access control checks. This will fail under a 
     * security manager unless the appropriate permissions are set.</p>
     *
     * <ul>
     * <li>Static fields will not be compared</li>
     * <li>Transient members will be not be compared, as they are likely derived
     *     fields</li>
     * <li>Superclass fields will be compared</li>
     * </ul>
     *
     * <p>If both <code>lhs</code> and <code>rhs</code> are <code>null</code>,
     * they are considered equal.</p>
     *
     * @param lhs  left-hand object
     * @param rhs  right-hand object
     * @return a negative integer, zero, or a positive integer as <code>lhs</code>
     *  is less than, equal to, or greater than <code>rhs</code>
     * @throws NullPointerException  if either (but not both) parameters are
     *  <code>null</code>
     * @throws ClassCastException  if <code>rhs</code> is not assignment-compatible
     *  with <code>lhs</code>
     */
    public static int reflectionCompare(Object lhs, Object rhs) {
        return reflectionCompare(lhs, rhs, false, null);
    }

    /**
     * <p>Compares two <code>Object</code>s via reflection.</p>
     *
     * <p>Fields can be private, thus <code>AccessibleObject.setAccessible</code>
     * is used to bypass normal access control checks. This will fail under a 
     * security manager unless the appropriate permissions are set.</p>
     *
     * <ul>
     * <li>Static fields will not be compared</li>
     * <li>If <code>compareTransients</code> is <code>true</code>,
     *     compares transient members.  Otherwise ignores them, as they
     *     are likely derived fields.</li>
     * <li>Superclass fields will be compared</li>
     * </ul>
     *
     * <p>If both <code>lhs</code> and <code>rhs</code> are <code>null</code>,
     * they are considered equal.</p>
     *
     * @param lhs  left-hand object
     * @param rhs  right-hand object
     * @param compareTransients  whether to compare transient fields
     * @return a negative integer, zero, or a positive integer as <code>lhs</code>
     *  is less than, equal to, or greater than <code>rhs</code>
     * @throws NullPointerException  if either <code>lhs</code> or <code>rhs</code>
     *  (but not both) is <code>null</code>
     * @throws ClassCastException  if <code>rhs</code> is not assignment-compatible
     *  with <code>lhs</code>
     */
    public static int reflectionCompare(Object lhs, Object rhs, boolean compareTransients) {
        return reflectionCompare(lhs, rhs, compareTransients, null);
    }

    /**
     * <p>Compares two <code>Object</code>s via reflection.</p>
     *
     * <p>Fields can be private, thus <code>AccessibleObject.setAccessible</code>
     * is used to bypass normal access control checks. This will fail under a 
     * security manager unless the appropriate permissions are set.</p>
     *
     * <ul>
     * <li>Static fields will not be compared</li>
     * <li>If the <code>compareTransients</code> is <code>true</code>,
     *     compares transient members.  Otherwise ignores them, as they
     *     are likely derived fields.</li>
     * <li>Compares superclass fields up to and including <code>reflectUpToClass</code>.
     *     If <code>reflectUpToClass</code> is <code>null</code>, compares all superclass fields.</li>
     * </ul>
     *
     * <p>If both <code>lhs</code> and <code>rhs</code> are <code>null</code>,
     * they are considered equal.</p>
     *
     * @param lhs  left-hand object
     * @param rhs  right-hand object
     * @param compareTransients  whether to compare transient fields
     * @param reflectUpToClass  last superclass for which fields are compared
     * @return a negative integer, zero, or a positive integer as <code>lhs</code>
     *  is less than, equal to, or greater than <code>rhs</code>
     * @throws NullPointerException  if either <code>lhs</code> or <code>rhs</code>
     *  (but not both) is <code>null</code>
     * @throws ClassCastException  if <code>rhs</code> is not assignment-compatible
     *  with <code>lhs</code>
     * @since 2.0
     */
    public static int reflectionCompare(Object lhs, Object rhs, boolean compareTransients, Class reflectUpToClass) {
        if (lhs == rhs) {
            return 0;
        }
        if (lhs == null || rhs == null) {
            throw new NullPointerException();
        }
        Class lhsClazz = lhs.getClass();
        if (!lhsClazz.isInstance(rhs)) {
            throw new ClassCastException();
        }
        CompareToBuilder compareToBuilder = new CompareToBuilder();
        reflectionAppend(lhs, rhs, lhsClazz, compareToBuilder, compareTransients);