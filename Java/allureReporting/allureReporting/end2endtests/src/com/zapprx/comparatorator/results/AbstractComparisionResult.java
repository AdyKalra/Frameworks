package com.zapprx.comparatorator.results;

import com.zapprx.comparatorator.ComparisonResult;
import com.zapprx.comparatorator.results.MismatchResult.MismatchValue;

public abstract class AbstractComparisionResult implements ComparisonResult {
    public static final ComparisonResult SUCCEEDED = new SuccessResult();
    private final ResultCode code;
    private final MismatchValue mismatch;
    private final Exception error;

    public static enum ResultCode {
        SUCCESS, MISMATCH, ERROR;
    }

    private static class SuccessResult extends AbstractComparisionResult {
        SuccessResult() {
            super(ResultCode.SUCCESS, null, null);
        }

        @Override
        public String getMessage() {
            return "Documents matched.";
        }
    }

    protected AbstractComparisionResult(ResultCode code, MismatchValue mismatch,
            Exception error) {
        super();
        this.code = code;
        this.mismatch = mismatch;
        this.error = error;
    }

    /* (non-Javadoc)
     * @see com.zapprx.comparatorator.results.Result#getCode()
     */
    @Override
    public ResultCode getCode() {
        return code;
    }

    /* (non-Javadoc)
     * @see com.zapprx.comparatorator.results.Result#getMismatch()
     */
    @Override
    public MismatchValue getMismatch() {
        return mismatch;
    }

    /* (non-Javadoc)
     * @see com.zapprx.comparatorator.results.Result#getError()
     */
    @Override
    public Exception getError() {
        return error;
    }

    /* (non-Javadoc)
     * @see com.zapprx.comparatorator.results.Result#succeded()
     */
    @Override
    public boolean succeded() {
        return code == ResultCode.SUCCESS;
    }

    /* (non-Javadoc)
     * @see com.zapprx.comparatorator.results.Result#mismatch()
     */
    @Override
    public boolean mismatch() {
        return code == ResultCode.MISMATCH;
    }

    /* (non-Javadoc)
     * @see com.zapprx.comparatorator.results.Result#error()
     */
    @Override
    public boolean error() {
        return code == ResultCode.ERROR;
    }

    /* (non-Javadoc)
     * @see com.zapprx.comparatorator.results.Result#getMessage()
     */
    @Override
    public abstract String getMessage();
}
