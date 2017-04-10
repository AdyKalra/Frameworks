package com.zapprx.comparatorator;

import com.zapprx.comparatorator.results.AbstractComparisionResult.ResultCode;
import com.zapprx.comparatorator.results.MismatchResult.MismatchValue;

/**
 * @author ejw
 *
 *         An abstract comparision result type that provides information about
 *         whether documents compared as the same, different or whether an error
 *         was encountered during processing
 */
public interface ComparisonResult {

    /**
     * @return the type of result, which could include ERROR, MISMATCH or
     *         SUCCESS
     */
    public abstract ResultCode getCode();

    /**
     * @return a data structure indicating the type of mismatch, including the
     *         field name, the value we were expecting and the value actually
     *         seen, or null. Only present when mismatch is true.
     *
     */
    public abstract MismatchValue getMismatch();

    /**
     * @return any exception thrown during processing, or null if no error. Only
     *         present when error() is true
     */
    public abstract Exception getError();

    /**
     * @return return true if this result indicates that the documents matched
     *         without error
     */
    public abstract boolean succeded();

    /**
     * @return true if this result indicates a document mismatch
     */
    public abstract boolean mismatch();

    /**
     * @return true if there was an error encountered when matching the
     *         documents
     */
    public abstract boolean error();

    /**
     * @return a human readable version of the result in the form of a useful,
     *         actionable string
     */
    public abstract String getMessage();

}