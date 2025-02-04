
/*
 * ====================================================================
 * 
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2003 Nick Lothian. All rights reserved.
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
 *    any, must include the following acknowlegement:  
 *       "This product includes software developed by the 
 *        developers of Classifier4J (http://classifier4j.sf.net/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The name "Classifier4J" must not be used to endorse or promote 
 *    products derived from this software without prior written 
 *    permission. For written permission, please contact   
 *    http://sourceforge.net/users/nicklothian/.
 *
 * 5. Products derived from this software may not be called 
 *    "Classifier4J", nor may "Classifier4J" appear in their names 
 *    without prior written permission. For written permission, please 
 *    contact http://sourceforge.net/users/nicklothian/.
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
 */
package net.sf.classifier4J;

/**
 * @author Nick Lothian
 * @author Peter Leschev
 */
public interface ICategorisedClassifier extends IClassifier {

    public static final String DEFAULT_CATEGORY = "DEFAULT";

    /**
     *
     * Function to determine the probability string matches a criteria for a given
     * category.
     * 
     * @param category the category to check against
     * @param input the string to classify
     * @return the likelyhood that this string is a match for this net.sf.classifier4J. 1 means 100% likely.
         *
         * @throws ClassifierException If a fatal problem occurs. For example,
         *                             the database is unavailable.
     */
    public double classify(String category, String input) throws ClassifierException;

    /**
     * 
     * Function to determine if a string matches a criteria for a given category
     * 
     * @param category the category to check against 
     * @param input the string to classify
     * @return true if the input string has a probability >= the cutoff probability of 
     * matching
         *
         * @throws ClassifierException If a fatal problem occurs. For example,
         *                             the database is unavailable.
     */
    public boolean isMatch(String category, String input) throws ClassifierException;

}